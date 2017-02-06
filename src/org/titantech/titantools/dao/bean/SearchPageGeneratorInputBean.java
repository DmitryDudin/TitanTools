package org.titantech.titantools.dao.bean;

public class SearchPageGeneratorInputBean {

    public int fieldId = -1;
    public String dbFieldName = null;
    public int dbFieldType = -1;
    public String dbFieldTypeStr = null;
    public int dbFieldLength = -1;
    public boolean filterable = false;
    public boolean sortable = false;
    public boolean wildcards = false;
    public boolean range = false;
    public String javaTypeName = null;
    public boolean mandatory = false;
    public boolean primary = false;
    public boolean serial = false;

    public String getDbFieldName() {
        return dbFieldName;
    }

    public void setDbFieldName(String dbFieldName) {
        this.dbFieldName = dbFieldName;
    }

    public int getDbFieldType() {
        return dbFieldType;
    }

    public void setDbFieldType(int dbFieldType) {
        this.dbFieldType = dbFieldType;
    }

    public String getDbFieldTypeStr() {
        return dbFieldTypeStr;
    }

    public void setDbFieldTypeStr(String dbFieldTypeStr) {
        this.dbFieldTypeStr = dbFieldTypeStr;
    }

    public int getDbFieldLength() {
        return dbFieldLength;
    }

    public void setDbFieldLength(int dbFieldLength) {
        this.dbFieldLength = dbFieldLength;
    }

    public boolean isFilterable() {
        return filterable;
    }

    public void setFilterable(boolean filterable) {
        this.filterable = filterable;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public boolean isWildcards() {
        return wildcards;
    }

    public void setWildcards(boolean wildcards) {
        this.wildcards = wildcards;
    }

    public boolean isRange() {
        return range;
    }

    public void setRange(boolean range) {
        this.range = range;
    }

    public String getJavaTypeName() {
        return javaTypeName;
    }

    public void setJavaTypeName(String javaTypeName) {
        this.javaTypeName = javaTypeName;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public int getFieldId() {
        return fieldId;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isSerial() {
        return serial;
    }

    public void setSerial(boolean serial) {
        this.serial = serial;
    }
}