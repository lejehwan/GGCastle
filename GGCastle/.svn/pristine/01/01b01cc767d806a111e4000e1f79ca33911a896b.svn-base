package com.sga.sol.repository;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.sga.sol.dto.GetChartDateDTO;
import com.sga.sol.dto.GetChartUserDTO;
import com.sga.sol.dto.MemberDTO;

@Repository
public class AdminRepository {

	@Inject
	private SqlSession sqlSession;
	
	private static final String Namespace = "com.sga.sol.mapper.adminMapper";
	
	public List<GetChartUserDTO> getFailUser() {
		return sqlSession.selectList(Namespace+".getFailUser");
	}
	
	public List<GetChartUserDTO> getSuccessUser(){
		return sqlSession.selectList(Namespace+".getSuccessUser");
	}
	
	public List<GetChartDateDTO> getAuthCntByDay(){
		return sqlSession.selectList(Namespace+".getAuthCntByDay");
	}
	
	public void updateAPIKey(MemberDTO member) {
		sqlSession.update(Namespace+".updateAPIKey", member);
	}
	
	public void userLockOnOff(String loginId) {
		sqlSession.update(Namespace+".userLockOnOff", loginId);
	}
	
	public boolean currentUserLock(String loginId) {
		return sqlSession.selectOne(Namespace+".currentUserLock", loginId);
	}
}
