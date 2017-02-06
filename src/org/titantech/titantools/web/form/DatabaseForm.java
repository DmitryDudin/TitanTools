package org.titantech.titantools.web.form;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.titantech.titantools.dao.bean.TableBean;

public class DatabaseForm extends ActionForm {

    private static final long serialVersionUID = 3995785521141005104L;
    private static Class CLAZZ = DatabaseForm.class;
    private static Logger logger = Logger.getLogger(CLAZZ);

    private String actionName = null;
    private String downloadFileName = null;


    private String serverName = "localhost";
    private String databaseName = null;
    private String databaseUser = null;
    private String databasePassword = null;
    private Integer databasePort = 5432;
    private List<TableBean> listOfTableBeans = null;
    private Map dbTableMap = null;

    // selected table index
    private String selectedTable = null;
    private String selectedTableName = null;
    private String selectedClassName = null;

    public void mapDatabaseMetadata(Map dbTableMap) {
        // TODO MAP THE META DATA FOR FRONTEND

        listOfTableBeans = new ArrayList();

        Iterator iter = dbTableMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            TableBean table = new TableBean();

            table.setTableName((String) entry.getKey());
            table.setSpgInputBeanList((List) entry.getValue());

            listOfTableBeans.add(table);
        }
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public Integer getDatabasePort() {
        return databasePort;
    }

    public void setDatabasePort(Integer databasePort) {
        this.databasePort = databasePort;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Map getDbTableMap() {
        return dbTableMap;
    }

    public void setDbTableMap(Map dbTableMap) {
        this.dbTableMap = dbTableMap;
    }

    public List<TableBean> getListOfTableBeans() {
        return listOfTableBeans;
    }

    public String getSelectedTable() {
        return selectedTable;
    }

    public void setSelectedTable(String selectedTable) {
        this.selectedTable = selectedTable;
    }

    public String getSelectedClassName() {
        return selectedClassName;
    }

    public void setSelectedClassName(String selectedClassName) {
        this.selectedClassName = selectedClassName;
    }

    public String getDownloadFileName() {
        return downloadFileName;
    }

    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }

    public String getSelectedTableName() {
        return selectedTableName;
    }

    public void setSelectedTableName(String selectedTableName) {
        this.selectedTableName = selectedTableName;
    }
}
