package org.titantech.titantools;

public class GESystemException extends RuntimeException {
    private static final long serialVersionUID = 2933528663325902249L;
    private String message;
    private Exception innerEx;

    public GESystemException(String message, Exception innerEx) {
        this.message = message;
        this.innerEx = innerEx;
    }

    public GESystemException(Exception innerEx) {
        this.innerEx = innerEx;
    }

    public GESystemException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Exception getInnerEx() {
        return innerEx;
    }
}