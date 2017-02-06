package org.titantech.titantools.dao.bean;


public class VOFieldToColumnMappingDetails extends VODetails {
    public String dbColumnName = null;
    public boolean isPK = false;
    public boolean isSerial = false;
    public int dbColumnType = -1;
    public String dbColumnTypeName = null;
}