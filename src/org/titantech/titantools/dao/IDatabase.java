package org.titantech.titantools.dao;

public interface IDatabase {
    public void setDBConnectionParameters(String serverName, String databaseName, String user, String password, Integer port);
}