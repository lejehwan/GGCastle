package com.sga.sol.service;

import java.util.List;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.sga.sol.dto.GetChartDateDTO;
import com.sga.sol.dto.GetChartUserDTO;
import com.sga.sol.dto.MemberDTO;
import com.sga.sol.repository.AdminRepository;

@Service
public class AdminService {

	@Inject
	private AdminRepository adminRepository;
	
	@Inject
	private JavaMailSender mailSender;
	
	public List<GetChartUserDTO> getFailUser(){
		return adminRepository.getFailUser();
	}
	
	public List<GetChartUserDTO> getSuccessUser(){
		return adminRepository.getSuccessUser();
	}
	
	public List<GetChartDateDTO> getAuthCntByDay(){
		return adminRepository.getAuthCntByDay();
	}
	
	public int getAllAuthCount() {
		return adminRepository.getAllAuthCount();
	}
	
	public int getSuccessAuthCount() {
		return adminRepository.getSuccessAuthCount();
	}
	
	public void updateAPIKey(MemberDTO member) {
		adminRepository.updateAPIKey(member);
	}
	
	public void userLockOnOff(String loginId) {
		adminRepository.userLockOnOff(loginId);
	}
	
	public boolean currentUserLock(String loginId) {
		return adminRepository.currentUserLock(loginId);
	}
	
	public void lockOnMailSend(MemberDTO member) {
		String toMail = member.getEmail();
		String content = "당신은 차단 되었습니다";
		mailSend(toMail, content);
	}
	
	public void lockOffMailSend(MemberDTO member) {
		String toMail = member.getEmail();
		String content = "당신의 차단이 해제되었습니다.";
		mailSend(toMail, content);
	}
	
	public void newAPIKey(MemberDTO member, String newAPIKey) {
		String toMail = member.getEmail();
		String content = "당신의 OTP 인증 키가 변경 되었습니다 <br>"
				+ "기존 인증 코드를 내보내기 후 다음 키를 통해 새로 발급받으세요 <br>" 
				+ "<b>" + newAPIKey + "</b>";
		mailSend(toMail, content);
	}
	
	public void mailSend(String toMail, String content) {
		String setFrom = "rdsa2577@gmail.com";
		String title = "GGCastle의 알림";
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content,true);
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
