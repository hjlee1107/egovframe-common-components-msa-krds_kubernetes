package egovframework.com.cop.smt.sim.service.impl;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Repository;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.cop.smt.sim.service.IndvdlSchdulManageVO;
/**
 * 일정관리를 처리하는 Dao Class 구현
 * @author 공통서비스 장동한
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  장동한          최초 생성
 *   2016.08.01  장동한          표준프레임워크 v3.6 개선
 *
 * </pre>
 */
@Repository("indvdlSchdulManageDao")
public class IndvdlSchdulManageDao extends EgovComAbstractDAO {


    /**
	 * 메인페이지/일정관리조회 목록을 Map(map)형식으로 조회한다.
	 * @param Map(map) - 조회할 정보가 담긴 VO
	 * @return List
	 * @throws Exception
	 */
	public List<EgovMap> selectIndvdlSchdulManageMainList(Map<String, String> map) throws Exception{
		 return selectList("IndvdlSchdulManage.selectIndvdlSchdulManageMainList", map);
	}

    /**
	 * 일정 목록을 Map(map)형식으로 조회한다.
	 * @param Map(map) - 조회할 정보가 담긴 VO
	 * @return List
	 * @throws Exception
	 */
	public List<EgovMap> selectIndvdlSchdulManageRetrieve(Map<String, String> map) throws Exception{
		 return selectList("IndvdlSchdulManage.selectIndvdlSchdulManageRetrieve", map);
	}


    /**
	 * 일정 목록을 VO(model)형식으로 조회한다.
	 * @param indvdlSchdulManageVO - 조회할 정보가 담긴 VO
	 * @return IndvdlSchdulManageVO
	 * @throws Exception
	 */
	public IndvdlSchdulManageVO selectIndvdlSchdulManageDetailVO(IndvdlSchdulManageVO indvdlSchdulManageVO) throws Exception{
		return (IndvdlSchdulManageVO)selectOne("IndvdlSchdulManage.selectIndvdlSchdulManageDetailVO", indvdlSchdulManageVO);
	}

    /**
	 * 일정 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return List
	 * @throws Exception
	 */
	public List<IndvdlSchdulManageVO> selectIndvdlSchdulManageList(ComDefaultVO searchVO) throws Exception{
		return selectList("IndvdlSchdulManage.selectIndvdlSchdulManage", searchVO);
	}

    /**
	 * 일정를(을) 상세조회 한다.
	 * @param indvdlSchdulManageVO - 일정 정보 담김 VO
	 * @return List
	 * @throws Exception
	 */
	public List<IndvdlSchdulManageVO> selectIndvdlSchdulManageDetail(IndvdlSchdulManageVO indvdlSchdulManageVO) throws Exception{
		return selectList("IndvdlSchdulManage.selectIndvdlSchdulManageDetail", indvdlSchdulManageVO);
	}

    /**
	 * 일정를(을) 목록 전체 건수를(을) 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return int
	 * @throws Exception
	 */
	public int selectIndvdlSchdulManageListCnt(ComDefaultVO searchVO) throws Exception{
		return (Integer)selectOne("IndvdlSchdulManage.selectIndvdlSchdulManageCnt", searchVO);
	}

    /**
	 * 일정를(을) 등록한다.
	 * @param qindvdlSchdulManageVO - 일정 정보 담김 VO
	 * @throws Exception
	 */
	public void insertIndvdlSchdulManage(IndvdlSchdulManageVO indvdlSchdulManageVO) throws Exception{
		insert("IndvdlSchdulManage.insertIndvdlSchdulManage", indvdlSchdulManageVO);
	}

    /**
	 * 일정를(을) 수정한다.
	 * @param indvdlSchdulManageVO - 일정 정보 담김 VO
	 * @throws Exception
	 */
	public void updateIndvdlSchdulManage(IndvdlSchdulManageVO indvdlSchdulManageVO) throws Exception{
		insert("IndvdlSchdulManage.updateIndvdlSchdulManage", indvdlSchdulManageVO);
	}

    /**
	 * 일정를(을) 삭제한다.
	 * @param indvdlSchdulManageVO - 일정 정보 담김 VO
	 * @throws Exception
	 */
	public void deleteIndvdlSchdulManage(IndvdlSchdulManageVO indvdlSchdulManageVO) throws Exception{
		// 일지 삭제
		delete("IndvdlSchdulManage.deleteDiaryManage", indvdlSchdulManageVO);
		// 일정관리 삭제
		delete("IndvdlSchdulManage.deleteIndvdlSchdulManage", indvdlSchdulManageVO);
	}
}
