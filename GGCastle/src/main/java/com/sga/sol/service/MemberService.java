package com.sga.sol.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sga.sol.dto.MemberDTO;
import com.sga.sol.repository.MemberRepository;

@Service
public class MemberService {
	
	@Inject
	private MemberRepository memberRepository;
	@Inject
	private BCryptPasswordEncoder encoder;
	
	public void joinMember(MemberDTO member) {
		member.setRole("user");
		member.setApiKey(encode(member.getApiKey()));
		member.setPw(encoder.encode(member.getPw()));
		memberRepository.joinMember(member);
	}
	
	public MemberDTO checkLoginId(String loginId) {
		return memberRepository.checkLoginId(loginId);
	}
	
	public boolean checkLoginPassword(String userPassword, String inputPassword) {
		return encoder.matches(inputPassword, userPassword);
	}
	
	public String encode(String apiKey) {
		return apiKey + String.valueOf((int)(Math.random()*10));
	}
	
	public String decode(String apiKey) {
		return apiKey.substring(0, apiKey.length()-1);
	}
	
	public void authY(String memberId) {
		memberRepository.authY(memberId);
	}
	
	public void authN(String memberId) {
		memberRepository.authN(memberId);
	}
	
	public boolean getAuthYN(String memberId) {
		return memberRepository.getAuthYN(memberId);
	}
	
	public void updateProfile(MemberDTO member) {
		member.setPw(encoder.encode(member.getPw()));
		memberRepository.updateProfile(member);
	}
	
	public String getIpAddress() {
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) ip = req.getRemoteAddr();
		return ip;
	}
	
	public Timestamp getTimeNow() {
		return Timestamp.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	}
}
