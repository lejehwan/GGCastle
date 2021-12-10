package com.sga.sol.api;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sga.sol.dto.GetChartDateDTO;
import com.sga.sol.dto.GetChartUserDTO;
import com.sga.sol.dto.MemberDTO;
import com.sga.sol.dto.ResponseDTO;
import com.sga.sol.otp.TOTPTokenService;
import com.sga.sol.service.AdminService;
import com.sga.sol.service.MemberService;

@RestController
public class APIController {

	@Inject
	private AdminService adminService;
	@Inject
	private MemberService memberService;
	@Inject
	private TOTPTokenService tokenService;
	
	
	// 인증 최다 실패자 3명
	@RequestMapping(value = "/getFailUsers", method = RequestMethod.GET)
	public String getFailUsers() throws JsonProcessingException {
		List<GetChartUserDTO> failUsers = adminService.getFailUser();
		return new ObjectMapper().writeValueAsString(failUsers);
	}
	
	// 최다 방문자 10명
	@RequestMapping(value = "/getSuccessUsers", method = RequestMethod.GET)
	public String getSuccessUsers() throws JsonProcessingException {
		List<GetChartUserDTO> successUsers = adminService.getSuccessUser();
		return new ObjectMapper().writeValueAsString(successUsers);
	}
	
	// 날짜 별 인증 횟수
	@RequestMapping(value = "/getAuthCntByDay", method = RequestMethod.GET)
	public String getAuthCntByDay() throws JsonProcessingException {
		List<GetChartDateDTO> cntByDay = adminService.getAuthCntByDay();
		return new ObjectMapper().writeValueAsString(cntByDay);
	}
	
	// 새로운 api key 발급 및 수정
	@RequestMapping(value = "/newAPIKey/{loginId}", method = RequestMethod.PUT)
	public ResponseDTO<Integer> newAPIKey(@PathVariable String loginId) {
		String newAPIKey = tokenService.generateSecretKey();
		MemberDTO member = memberService.checkLoginId(loginId);
		String encodeNewAPIKey = memberService.encode(newAPIKey);
		member.setId(loginId);
		member.setApiKey(encodeNewAPIKey);
		adminService.updateAPIKey(member);
		adminService.newAPIKey(member, newAPIKey);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),200);
	}
	
	// 유저 차단 및 해제
	@RequestMapping(value = "/userLock/{loginId}", method = RequestMethod.PUT)
	public ResponseDTO<Integer> userLock(@PathVariable String loginId) {
		adminService.userLockOnOff(loginId);
		MemberDTO member = memberService.checkLoginId(loginId);
		if (adminService.currentUserLock(loginId)) {
			adminService.lockOnMailSend(member);
		}else {
			adminService.lockOffMailSend(member);
		}
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),200);
	}
	
	
}
