package com.gura.spring03.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/*
[403 FORBIDDEN 응답]
throw new ForbiddenException();
*/
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
	
}
