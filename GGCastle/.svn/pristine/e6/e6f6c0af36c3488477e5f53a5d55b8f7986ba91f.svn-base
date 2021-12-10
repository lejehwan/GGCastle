package com.sga.sol.configuration;

import java.sql.Timestamp;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sga.sol.dto.MemberDTO;
import com.sga.sol.service.MemberService;

public class CommonInterceptor extends HandlerInterceptorAdapter{
     
	private static final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);
	@Inject
	private MemberService memberService;
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
		String ip = memberService.getIpAddress();// 클라이언트 ip 가져오기
		Timestamp Time = memberService.getTimeNow();// 현재 시간 가져오기
        if (logger.isDebugEnabled()) {
        	logger.debug("===================       START       ===================");
        	logger.debug(" getURI \t:	" + request.getRequestURI());
        	logger.debug(" getURL \t:	" + request.getRequestURL());
        	logger.debug(" getScheme \t:	" + request.getScheme());
        	logger.debug(" getTime \t:	" + Time);
        	logger.debug(" getMethod \t:	" + request.getMethod());
        	logger.debug(" getProtocol \t:	" + request.getProtocol());
        	logger.debug(" getQueryStr \t:	" + request.getQueryString());
        	logger.debug(" isSecure \t:	" + request.isSecure());
        	logger.debug(" getLocale \t:	" + request.getLocale());
        	logger.debug(" getPort \t:	" + request.getRemotePort());
        	logger.debug(" getHost \t:	" + request.getRemoteHost());
        	logger.debug(" getIp \t:	" + ip);
        }
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                logger.debug(" cookieName \t:	" + cookie.getName());
                logger.debug(" cookieVal \t:	" + cookie.getValue());
            }
        }
        if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			logger.debug(" gethm \t:	" + hm);
		}
        
        return super.preHandle(request, response, handler);
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    	logger.debug(" postHandler \t:	" + modelAndView);
        if (logger.isDebugEnabled()) {
        	logger.debug("===================        END        ===================\n");
        }
        
    }

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.debug("===================    afterComp START    ===================");
		logger.debug(" getURI \t:	" + request.getRequestURI());
		logger.debug(" handler \t:	" + handler);
		MemberDTO member = (MemberDTO) request.getAttribute(SessionConst.LOGIN_User);
		if (member != null) {
			logger.debug(" userId \t:	" + member.getId());
			logger.debug(" userPW \t:	" + member.getPw());
			logger.debug(" userKey \t:	" + member.getApiKey());
		}
		logger.debug("===================    afterComp END    ===================");
		
		super.afterCompletion(request, response, handler, ex);
	}
    
    
     
}

