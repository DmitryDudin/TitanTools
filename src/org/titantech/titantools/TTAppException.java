package org.titantech.titantools;

public class TTAppException extends Exception {
    private static final long serialVersionUID = 5415248000713941020L;

    public TTAppException(String msg) {
        super(msg);
    }

    public TTAppException() {
        super();
    }
}