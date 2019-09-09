package com.maywide.biz.core.pojo;

public class RemoteCallException extends Exception {
	private static final long serialVersionUID = 4486579896184029296L;
    private long errorCode = -1;

    public RemoteCallException(String message, long errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public long getErrorCode() {
        return errorCode;
    }
}
