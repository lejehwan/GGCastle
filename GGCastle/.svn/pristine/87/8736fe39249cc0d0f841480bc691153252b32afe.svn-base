package com.sga.sol.repository;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.sga.sol.dto.MemberDTO;

@Repository
public class MemberRepository {
	
	@Inject
	private SqlSession sqlSession;
	
	private static final String Namespace = "com.sga.sol.mapper.memberMapper";
	
	public void joinMember(MemberDTO member) {
		sqlSession.insert(Namespace+".joinMember", member);
	}

	public MemberDTO checkLoginId(String loginId) {
		return sqlSession.selectOne(Namespace+".checkLoginId", loginId);
	}
	
	public void authY(String memberId) {
		sqlSession.update(Namespace+".authY", memberId);
	}
	
	public void authN(String memberId) {
		sqlSession.update(Namespace+".authN", memberId);
	}
	
	public boolean getAuthYN(String memberId) {
		return sqlSession.selectOne(Namespace+".getAuthYN",memberId);
	}
	
	public void changeAuth(String memberId) {
		sqlSession.update(Namespace+".changeAuth",memberId);
	}
	
	public void updateProfile(MemberDTO member) {
		sqlSession.update(Namespace+".updateProfile", member);
	}

}
