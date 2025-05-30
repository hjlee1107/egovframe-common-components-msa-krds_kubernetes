package egovframework.com.uss.sam.ipm.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.sam.ipm.service.EgovIndvdlInfoPolicyService;
import egovframework.com.uss.sam.ipm.service.IndvdlInfoPolicy;
import egovframework.com.utl.fcc.service.EgovStringUtil;

/**
 * 개인정보보호정책를 처리하는 Controller Class 구현
 * 
 * @author 공통서비스 장동한
 * @since 2009.07.03
 * @version 1.0
 * @see
 * 
 *      <pre>
 * &lt;&lt; 개정이력(Modification Information) &gt;&gt;
 *
 *   수정일          수정자       수정내용
 *  -----------    --------    ---------------------------
 *   2009.07.03     장동한		최초 생성
 *   2011.08.26     정진오		IncludedInfo annotation 추가
 *   2024.10.29     권태성		등록 /수정 화면과 처리 로직 분리
 *
 *      </pre>
 */

@Controller
public class EgovIndvdlInfoPolicyController {

	@Autowired
	private DefaultBeanValidator beanValidator;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	/** egovOnlinePollService */
	@Resource(name = "egovIndvdlInfoPolicyService")
	private EgovIndvdlInfoPolicyService egovIndvdlInfoPolicyService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/**
	 * 개인정보보호정책 목록을 조회한다.
	 * 
	 * @param searchVO
	 * @param commandMap
	 * @param indvdlInfoPolicy
	 * @param model
	 * @return "egovframework/com/uss/sam/ipm/EgovOnlinePollList"
	 * @throws Exception
	 */
	@IncludedInfo(name = "개인정보보호정책확인", order = 510, gid = 50)
	@RequestMapping(value = "/uss/sam/ipm/listIndvdlInfoPolicy.do")
	public String egovIndvdlInfoPolicyList(@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap, IndvdlInfoPolicy indvdlInfoPolicy, ModelMap model) throws Exception {

//        String sSearchMode = commandMap.get("searchMode") == null ? "" : (String) commandMap.get("searchMode");

		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<EgovMap> resultList = egovIndvdlInfoPolicyService.selectIndvdlInfoPolicyList(searchVO);
		model.addAttribute("resultList", resultList);

		model.addAttribute("searchKeyword",
				commandMap.get("searchKeyword") == null ? "" : (String) commandMap.get("searchKeyword"));
		model.addAttribute("searchCondition",
				commandMap.get("searchCondition") == null ? "" : (String) commandMap.get("searchCondition"));

		int totCnt = egovIndvdlInfoPolicyService.selectIndvdlInfoPolicyListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "egovframework/com/uss/sam/ipm/EgovIndvdlInfoPolicyList";
	}

	/**
	 * 개인정보보호정책 목록을 상세조회 조회한다.
	 * 
	 * @param searchVO
	 * @param indvdlInfoPolicy
	 * @param commandMap
	 * @param model
	 * @return "/uss/sam/ipm/EgovOnlinePollDetail"
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/sam/ipm/detailIndvdlInfoPolicy.do")
	public String egovIndvdlInfoPolicyDetail(@ModelAttribute("searchVO") ComDefaultVO searchVO,
			IndvdlInfoPolicy indvdlInfoPolicy, @RequestParam Map<?, ?> commandMap, ModelMap model) throws Exception {

		String sLocationUrl = "egovframework/com/uss/sam/ipm/EgovIndvdlInfoPolicyDetail";

		String sCmd = commandMap.get("cmd") == null ? "" : (String) commandMap.get("cmd");

		if (sCmd.equals("del")) {
			egovIndvdlInfoPolicyService.deleteIndvdlInfoPolicy(indvdlInfoPolicy);
			sLocationUrl = "forward:/uss/sam/ipm/listIndvdlInfoPolicy.do";
		} else {
			IndvdlInfoPolicy indvdlInfoPolicyVO = egovIndvdlInfoPolicyService
					.selectIndvdlInfoPolicyDetail(indvdlInfoPolicy);
			model.addAttribute("indvdlInfoPolicy", indvdlInfoPolicyVO);
		}

		return sLocationUrl;
	}

	/**
	 * 개인정보보호정책 수정화면
	 * 
	 * @param searchVO
	 * @param indvdlInfoPolicy
	 * @param model
	 * @return "/uss/sam/ipm/EgovOnlinePollUpdt"
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/sam/ipm/updtIndvdlInfoPolicyView.do")
	public String egovIndvdlInfoPolicyModify(@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@ModelAttribute("indvdlInfoPolicy") IndvdlInfoPolicy indvdlInfoPolicy, ModelMap model) throws Exception {
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "redirect:/uat/uia/egovLoginUsr.do";
		}

		IndvdlInfoPolicy indvdlInfoPolicyVO = egovIndvdlInfoPolicyService.selectIndvdlInfoPolicyDetail(indvdlInfoPolicy);
		model.addAttribute("indvdlInfoPolicy", indvdlInfoPolicyVO);

		return "egovframework/com/uss/sam/ipm/EgovIndvdlInfoPolicyUpdt";
	}
	
	/**
	 * 개인정보보호정책를 수정한다.
	 * 
	 * @param searchVO
	 * @param commandMap
	 * @param indvdlInfoPolicy
	 * @param bindingResult
	 * @param model
	 * @return "redirect:/uss/sam/ipm/listIndvdlInfoPolicy.do"
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/sam/ipm/updtIndvdlInfoPolicy.do")
	public String egovIndvdlInfoPolicyModify(@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap, @ModelAttribute("indvdlInfoPolicy") IndvdlInfoPolicy indvdlInfoPolicy,
			BindingResult bindingResult, ModelMap model) throws Exception {
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "redirect:/uat/uia/egovLoginUsr.do";
		}

		// 서버 validate 체크
		beanValidator.validate(indvdlInfoPolicy, bindingResult);
		if (bindingResult.hasErrors()) {
			return "egovframework/com/uss/sam/ipm/EgovIndvdlInfoPolicyUpdt";
		}

		// 로그인 객체 선언
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		String uniqId = (loginVO == null ? "" : EgovStringUtil.isNullToString(loginVO.getUniqId()));

		// 아이디 설정
		indvdlInfoPolicy.setFrstRegisterId(uniqId);
		indvdlInfoPolicy.setLastUpdusrId(uniqId);

		egovIndvdlInfoPolicyService.updateIndvdlInfoPolicy(indvdlInfoPolicy);

		return "redirect:/uss/sam/ipm/listIndvdlInfoPolicy.do";
	}
	
	/**
	 * 개인정보보호정책 등록화면
	 * 
	 * @param searchVO
	 * @param indvdlInfoPolicy
	 * @param model
	 * @return "/uss/sam/ipm/EgovOnlinePollRegist"
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/sam/ipm/registIndvdlInfoPolicyView.do")
	public String egovIndvdlInfoPolicyRegist(@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@ModelAttribute("indvdlInfoPolicy") IndvdlInfoPolicy indvdlInfoPolicy,
			ModelMap model) throws Exception {
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "redirect:/uat/uia/egovLoginUsr.do";
		}

		return "egovframework/com/uss/sam/ipm/EgovIndvdlInfoPolicyRegist";
	}
	
	
	/**
	 * 개인정보보호정책를 등록한다.
	 * 
	 * @param searchVO
	 * @param commandMap
	 * @param indvdlInfoPolicy
	 * @param bindingResult
	 * @param model
	 * @return "/uss/sam/ipm/EgovOnlinePollRegist"
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/sam/ipm/registIndvdlInfoPolicy.do")
	public String egovIndvdlInfoPolicyRegist(@ModelAttribute("searchVO") ComDefaultVO searchVO,
			@RequestParam Map<?, ?> commandMap, @ModelAttribute("indvdlInfoPolicy") IndvdlInfoPolicy indvdlInfoPolicy,
			BindingResult bindingResult, ModelMap model) throws Exception {
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "redirect:/uat/uia/egovLoginUsr.do";
		}

		// 서버 validate 체크
		beanValidator.validate(indvdlInfoPolicy, bindingResult);
		if (bindingResult.hasErrors()) {
			return "egovframework/com/uss/sam/ipm/EgovIndvdlInfoPolicyRegist";
		}

		// 로그인 객체 선언
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		String uniqId = (loginVO == null ? "" : EgovStringUtil.isNullToString(loginVO.getUniqId()));

		// 아이디 설정
		indvdlInfoPolicy.setFrstRegisterId(uniqId);
		indvdlInfoPolicy.setLastUpdusrId(uniqId);

		// 저장
		egovIndvdlInfoPolicyService.insertIndvdlInfoPolicy(indvdlInfoPolicy);

		return "forward:/uss/sam/ipm/listIndvdlInfoPolicy.do";
	}
	

}