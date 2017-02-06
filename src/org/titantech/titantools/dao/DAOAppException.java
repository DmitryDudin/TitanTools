package org.titantech.titantools.dao;


/*
$RCSfile: DAOAppException.java,v $

Change Summary
==============
$Log: DAOAppException.java,v $
Revision 1.1.1.1  2009/07/09 01:17:03  rmironenko
no message

Revision 1.1.1.1  2008/09/19 23:01:54  rmironenko
no message


*/

public class DAOAppException extends Exception {
    private static final long serialVersionUID = -5661739161941222288L;

    public DAOAppException(String msg) {
        super(msg);
    }

    public DAOAppException() {
        super();
    }
}