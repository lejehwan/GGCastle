package com.sga.sol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sga.sol.configuration.SessionConst;
import com.sga.sol.dto.AuthDTO;
import com.sga.sol.dto.MemberDTO;
import com.sga.sol.dto.ResponseDTO;
import com.sga.sol.otp.TOTPTokenService;
import com.sga.sol.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MemberController {
	
	@Inject
	private MemberService memberService;
	@Inject
	private TOTPTokenService tokenService;
	public final int OK = 200;
	public final int UNAUTHORIZED = 401;
	public final int INTERNAL_SERVER_ERROR = 500;
	
	@GetMapping("/join")
	public String joinForm(Model model) {
		model.addAttribute("apiKey",tokenService.generateSecretKey());
		return "join";
	}
	
	@PostMapping(value = "/join")
	public String join(MemberDTO member) {
		memberService.joinMember(member);
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(MemberDTO member, HttpServletRequest request) {
		MemberDTO memberDTO = memberService.checkLoginId(member.getId());
		if (memberDTO == null) {
			return "login";
		}
		boolean check = memberService.checkLoginPassword(memberDTO.getPw(), member.getPw());
		if (check) {
			HttpSession session = request.getSession(true);
			session.setAttribute(SessionConst.LOGIN_User, memberDTO);
			return "redirect:/auth";
		}
		return "login";
	}
	
	@GetMapping("/auth")
	public String certifyForm() {
		return "otp";
	}
	
	@Scheduled(fixedDelay = 1000)
	public void socketInit() {
		try {
			ServerSocket serverSocket = new ServerSocket(8912);// 포트 번호
			while (true) {
				try {
					System.out.println("Server is ready");
					
					Socket clientSocket = serverSocket.accept();
					System.out.println("Client has accepted");

					BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));// 클라이언트 데이터 읽어옴
//					PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());// 클라이언트로 부터 데이터를 보낼 준비
//					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
//					Writer wr = new OutputStreamWriter(clientSocket.getOutputStream());
					DataOutputStream das = new DataOutputStream(clientSocket.getOutputStream());
					
					String readData = br.readLine(); // 클라이언트로부터 읽어온 데이터
					log.info("from Client >>" + readData);
					
					// readData != null
					String[] conData = readData.split(",");
					String memberId = conData[0];
					String authKey = conData[1];
					
					MemberDTO member = memberService.checkLoginId(memberId);
					boolean check = false;
					if (member != null) {
						String decAPIKey = memberService.decode(member.getApiKey());
						check = tokenService.validate(authKey, decAPIKey);
					}
					if (check) {
						memberService.authY(member.getId());
						das.writeInt(OK);
						das.flush();
					}else if (!check) {
						das.writeInt(UNAUTHORIZED);
						das.flush();
					}else{
						das.writeInt(INTERNAL_SERVER_ERROR);
						das.flush();
						clientSocket.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/api/auth/text")
	@ResponseBody
	public ResponseDTO<Integer> certificationByText(@RequestParam String authKey, HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute(SessionConst.LOGIN_User);
		String decAPIKey = memberService.decode(member.getApiKey());
		boolean check = tokenService.validate(authKey, decAPIKey);
		if (check) {
			memberService.authY(member.getId());
			return new ResponseDTO<Integer>(HttpStatus.OK.value(),200);
		}
		request.getSession(false);
		return new ResponseDTO<Integer>(HttpStatus.UNAUTHORIZED.value(),401);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/api/auth/json" ,method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<Integer> certificationByJSON(@RequestBody AuthDTO authKey, HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute(SessionConst.LOGIN_User);
		String decAPIKey = memberService.decode(member.getApiKey());
		boolean check = tokenService.validate(authKey.getAuthKey(),decAPIKey);
		if (check) {
			memberService.authY(member.getId());
			return new ResponseDTO<Integer>(HttpStatus.OK.value(),200);
		}
		request.getSession(false);
		return new ResponseDTO<Integer>(HttpStatus.UNAUTHORIZED.value(),401);
	}
	
	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute(SessionConst.LOGIN_User);
		memberService.authN(member.getId());
		session.invalidate();
		return "redirect:/";
	}
	
}
