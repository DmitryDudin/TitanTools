package org.titantech.titantools.dao;


public class DAOSysException extends RuntimeException {
    private static final long serialVersionUID = 7954386301443339695L;

    public DAOSysException(String msg) {
        super(msg);
    }

    public DAOSysException() {
        super();
    }
}
