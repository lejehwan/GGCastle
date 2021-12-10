package com.sga.sol.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CommonExceptionAdvice {

	
	// exception
	@ExceptionHandler(Exception.class)
	public String except(Exception ex, Model model) {
		log.error("Exception ..." + ex.getMessage());
		model.addAttribute("exception", ex);
		return "errorPage";
	}
	
	// 404
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus()
	public String handle404(NoHandlerFoundException ex) {
		log.error(" NoHandlerFoundException \t:	" + ex.getRequestURL());
		log.error(" NoHandlerFoundException \t:	" + ex.getHttpMethod());
		log.error(" NoHandlerFoundException \t:	" + ex.getMessage());
		return "errorPage";
	}
	
	@GetMapping("/errorpage")
	public String errorPage() {
		return "errorPage";
	}
}
