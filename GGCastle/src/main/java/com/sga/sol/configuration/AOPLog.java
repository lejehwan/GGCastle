package com.sga.sol.configuration;

import java.sql.Timestamp;
import java.util.Arrays;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.sga.sol.dto.MemberDTO;
import com.sga.sol.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@EnableAspectJAutoProxy
@Component
@Aspect
@Slf4j
public class AOPLog {
	
	@Inject
	private MemberService memberService;
//	 ||" + "execution(* com.sga.sol.MemberController.socketInit(..))
	
	@Around("execution(* com.sga.sol.MemberController.certificationByJSON(..)) ||"
			+ "execution(* com.sga.sol.MemberController.certificationByText(..))")
	public Object logging(ProceedingJoinPoint jp) throws Throwable {
		Timestamp Time = memberService.getTimeNow();
		HttpServletRequest request = null;
		for (Object o : jp.getArgs()) {
			if (o instanceof HttpServletRequest) {
				request = (HttpServletRequest) o;
			}
		}
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO)session.getAttribute(SessionConst.LOGIN_User);
		
		Object result = jp.proceed();
		
		boolean check = memberService.getAuthYN(member.getId());
		MDC.put("auth_time", Time.toString());
		MDC.put("userId", member.getId());
		MDC.put("parameter", Arrays.toString(jp.getArgs()));
		MDC.put("auth_yn", String.valueOf(check));
		log.info("create log", MDC.getCopyOfContextMap());
		
		return result;
		
	}
	
}
