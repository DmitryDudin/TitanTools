package org.titantech.titantools.dao.bean;

import java.util.List;

import org.apache.log4j.Logger;

public class TableBean {
    private static Class CLAZZ = TableBean.class;
    private static Logger logger = Logger.getLogger(CLAZZ);

    private String tableName = null;
    private List<SearchPageGeneratorInputBean> spgInputBeanList = null;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List getSpgInputBeanList() {
        return spgInputBeanList;
    }

    public void setSpgInputBeanList(List spgInputBeanList) {
        this.spgInputBeanList = spgInputBeanList;
    }


}
