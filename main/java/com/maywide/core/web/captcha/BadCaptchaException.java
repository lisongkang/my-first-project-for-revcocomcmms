package com.maywide.core.web.captcha;

public class BadCaptchaException extends RuntimeException{

    public BadCaptchaException(String message, Throwable e) {
        super(message, e);
    }

    public BadCaptchaException(String message) {
        super(message);
    }

}
