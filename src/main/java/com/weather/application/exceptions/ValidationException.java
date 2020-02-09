package com.weather.application.exceptions;

public class ValidationException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4078409696585137984L;
	private String msg;

    public ValidationException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
