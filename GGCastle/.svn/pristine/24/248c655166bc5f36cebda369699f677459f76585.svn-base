package com.sga.sol.configuration;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sga.sol.dto.MemberDTO;
import com.sga.sol.service.AdminService;
import com.sga.sol.service.MemberService;

public class AuthInterceptor extends HandlerInterceptorAdapter{

	@Inject
	private MemberService memberService;
	@Inject
	private AdminService adminService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO)session.getAttribute(SessionConst.LOGIN_User);
		boolean check = memberService.getAuthYN(member.getId());
		boolean check2 = adminService.currentUserLock(member.getId());
		if (check && !check2) {
			return true;
		}
		response.sendRedirect("/auth?redirectURL=" + request.getRequestURI());
		return false;
		
	}
	
	
}
