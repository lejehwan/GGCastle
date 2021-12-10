package com.sga.sol;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sga.sol.configuration.SessionConst;
import com.sga.sol.dto.GetChartUserDTO;
import com.sga.sol.dto.MemberDTO;
import com.sga.sol.service.AdminService;
import com.sga.sol.service.MemberService;

@Controller
public class DashBoardController {
	
	@Inject
	private AdminService adminService;
	@Inject
	private MemberService memberService;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request)  {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute(SessionConst.LOGIN_User);
		List<GetChartUserDTO> failUsers = adminService.getFailUser();
		model.addAttribute("member",member);
		model.addAttribute("failUsers", failUsers);
		return "home";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam String searchId, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute(SessionConst.LOGIN_User);
		MemberDTO findMember = memberService.checkLoginId(searchId);
		model.addAttribute("member", member);
		model.addAttribute("findUser", findMember);
		return "searchPage";
	}	
	
	@GetMapping(value = "/member/{memberId}")
	public String profileForm(@PathVariable String memberId, Model model) {
		MemberDTO member = memberService.checkLoginId(memberId);
		model.addAttribute("member",member);
		return "member";
	}
	
	@PostMapping("/member/update/{memberId}")
	public String profileUpdate(@PathVariable String memberId, MemberDTO member) {
		MemberDTO updateMember = MemberDTO.builder()
		.id(memberId)
		.pw(member.getPw())
		.email(member.getEmail())
		.build();
		memberService.updateProfile(updateMember);
		return "redirect:/home";
	}
	
}
