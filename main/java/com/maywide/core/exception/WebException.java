package com.maywide.core.exception;

public class WebException extends BaseRuntimeException{

    public WebException(String msg) {
        super(msg);
    }

    public WebException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
