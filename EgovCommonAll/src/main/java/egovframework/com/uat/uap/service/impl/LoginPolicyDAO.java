/**
 * 개요
 * - 로그인정책에 대한 DAO 클래스를 정의한다.
 * 
 * 상세내용
 * - 로그인정책에 대한 등록, 수정, 삭제, 조회, 반영확인 기능을 제공한다.
 * - 로그인정책의 조회기능은 목록조회, 상세조회로 구분된다.
 * @author lee.m.j
 * @version 1.0
 * @created 03-8-2009 오후 2:08:54
 *   <pre>
 * == 개정이력(Modification Information) ==
 * 
 *  수정일                수정자           수정내용
 *  ----------   --------   ---------------------------
 *  2009.08.03   이문준            최초 생성
 *  2021.02.18   신용호            selectLoginPolicyResult() 삭제
 * </pre>
 */

package egovframework.com.uat.uap.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.uat.uap.service.LoginPolicy;
import egovframework.com.uat.uap.service.LoginPolicyVO;

@Repository("loginPolicyDAO")
public class LoginPolicyDAO extends EgovComAbstractDAO {
	
	/**
	 * 로그인정책 목록을 조회한다.
	 * @param loginPolicyVO - 로그인정책 VO
	 * @return List - 로그인정책 목록
	 */	
	public List<LoginPolicyVO> selectLoginPolicyList(LoginPolicyVO loginPolicyVO) throws Exception {
		return selectList("loginPolicyDAO.selectLoginPolicyList", loginPolicyVO);
	}

	/**
	 * 로그인정책 목록 수를 조회한다.
	 * @param loginPolicyVO - 로그인정책 VO
	 * @return int
	 */
	public int selectLoginPolicyListTotCnt(LoginPolicyVO loginPolicyVO) throws Exception {
		return (Integer)selectOne("loginPolicyDAO.selectLoginPolicyListTotCnt", loginPolicyVO);
	}

	/**
	 * 로그인정책 목록의 상세정보를 조회한다.
	 * @param loginPolicyVO - 로그인정책 VO
	 * @return LoginPolicyVO - 로그인정책 VO
	 */
	public LoginPolicyVO selectLoginPolicy(LoginPolicyVO loginPolicyVO) throws Exception {
		return (LoginPolicyVO)selectOne("loginPolicyDAO.selectLoginPolicy", loginPolicyVO);
	}

	/**
	 * 로그인정책 정보를 신규로 등록한다.
	 * @param loginPolicy - 로그인정책 model
	 */
	public void insertLoginPolicy(LoginPolicy loginPolicy) throws Exception {
        insert("loginPolicyDAO.insertLoginPolicy", loginPolicy);
	}

	/**
	 * 기 등록된 로그인정책 정보를 수정한다.
	 * @param loginPolicy - 로그인정책 model
	 */
	public void updateLoginPolicy(LoginPolicy loginPolicy) throws Exception {
		update("loginPolicyDAO.updateLoginPolicy", loginPolicy);
	}

	/**
	 * 기 등록된 로그인정책 정보를 삭제한다.
	 * @param loginPolicy - 로그인정책 model
	 */
	public void deleteLoginPolicy(LoginPolicy loginPolicy) throws Exception {
		delete("loginPolicyDAO.deleteLoginPolicy", loginPolicy);
	}

}
