package com.sga.sol.configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	protected String getClientIpAddr(HttpServletRequest req) {
		String ip = req.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        return ip;
	}

//	private final PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();

//	String path = request.getRequestURL().toString();
//	String[] convPath = path.split("/");
//	String ip = convPath[2].split(":")[0];
	// 172.19.11.108
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String ip = getClientIpAddr(request);
		boolean check = ip.equals("172.19.11.107");// 외부 접근 ip라면(예시 ip 입니다.)
		HttpSession session = request.getSession();
		if (check) {
			if (session == null || session.getAttribute(SessionConst.LOGIN_User) == null) {
				response.sendRedirect("/login?redirectURL=" + request.getRequestURI());
				return false;
			}
			return true;
		}
		return true;
	}

}