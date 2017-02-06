package org.titantech.titantools.dao.bean;

public class VODetails {

    private static String GET_PREFIX = "get";
    private static String SET_PREFIX = "set";

    public String javaFieldName = null;
    public String javaSetterMethod = null;
    public String javaGetterMethod = null;
    public Class javaFieldTypeClass = null;
    public String javaFieldTypeName = null;

    public void populateFormattedFieldSetterGetter(String javaFieldName) {
        if (javaFieldName == null) return;
        String name2 = javaFieldName.substring(0, 1).toUpperCase() + javaFieldName.substring(1);
        this.javaGetterMethod = GET_PREFIX + name2;
        this.javaSetterMethod = SET_PREFIX + name2;
        this.javaFieldName = javaFieldName.substring(0, 1).toLowerCase()
                + javaFieldName.substring(1);
    }
}