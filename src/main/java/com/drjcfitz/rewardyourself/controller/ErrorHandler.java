package com.drjcfitz.rewardyourself.controller;


import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.HttpStatusException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {
	private static Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	
	/*@ExceptionHandler(HttpStatusException.class)
	public void handleDatabaseException(HttpStatusException ex) {
		ex.printStackTrace();
		logger.log(Level.WARNING, "Error parsing merchant site data");
	}*/


}
