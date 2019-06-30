package com.github.duychuongvn.core.otp;

public class OTPGenerationException extends Exception{
    private static final long serialVersionUID = 8959187397110576256L;

    /**
     *
     */
    public OTPGenerationException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public OTPGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public OTPGenerationException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public OTPGenerationException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public OTPGenerationException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }


}
