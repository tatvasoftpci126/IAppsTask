package com.tatva.iapps.controller.advice;

import java.util.logging.Level;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tatva.iapps.exception.IAppsException;
import com.tatva.iapps.exception.InvalidXmlException;
import com.tatva.iapps.model.EpaperResponse;

import lombok.extern.java.Log;

@RestControllerAdvice
@Log
public class EpaperControllerAdvice {

	@ExceptionHandler(IAppsException.class)
	public ResponseEntity<Object> exception(Exception ex) {
		log.log(Level.SEVERE, ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(EpaperResponse.builder().responseMessage(ex.getMessage()).build());
	}

	@ExceptionHandler(InvalidXmlException.class)
	public ResponseEntity<Object> invalidXmlFile(Exception ex) {
		log.log(Level.SEVERE, ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(EpaperResponse.builder().responseMessage(ex.getMessage()).build());
	}
	@ExceptionHandler(BindException.class)
	public ResponseEntity<Object> invalidRequest(Exception ex) {
		log.log(Level.SEVERE, ex.getMessage(), ex);
		BindException be = (BindException) ex;
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(EpaperResponse.builder().responseMessage(be.getBindingResult().getAllErrors().stream().map(e->e.getDefaultMessage()).collect(Collectors.joining(","))).build());
	}
	
}