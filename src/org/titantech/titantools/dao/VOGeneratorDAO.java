package org.titantech.titantools.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean;


public interface VOGeneratorDAO {
    public List getTableMetaData(String tableName) throws DAOAppException;

    public List getDatabaseTables() throws DAOAppException;

    public List getListOfSPGInputBeans(String tableName) throws DAOAppException;
}