package org.titantech.titantools.generator;

import java.util.Iterator;
import java.util.List;

import org.titantech.titantools.dao.bean.VOFieldToColumnMappingDetails;

public class DAOInterfaceGenerator extends GeneratorBase {

    public String generateDAOInterface() {
        return generateDAOInterface(null, null);
    }

    public String generateDAOInterface(String filterVOName, List<VOFieldToColumnMappingDetails> dbColumns) {
        StringBuffer contents = new StringBuffer();
        contents.append(generateDAOInterfaceFileHeader(filterVOName));
        contents.append(generateDAOInterfaceDefaultMethods(dbColumns));
        if (filterVOName != null) {
            // there should be a method of retrieving data with the provided filter type.
            contents.append(generateDAOInterfaceMethodSelectRecordByFilter(filterVOName));
        }
        contents.append(generateClassOrInterfaceFooter());
        String str = contents.toString();
        System.out.println(str);
        return str;
    }

    private StringBuffer generateDAOInterfaceFileHeader(String filterVOName) {
        StringBuffer header = new StringBuffer();
        header.append(JAVA_KEYWORD_PACKAGE).append(SPACE).append(
                DAO_JAVA_PACKAGE_NAME).append(SEMICOLON).append(DBL_NEW_LINE);
        Iterator iter = DAO_INTERFACE_IMPORTS.iterator();
        while (iter.hasNext()) {
            String imprt = (String) iter.next();
            header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(imprt)
                    .append(SEMICOLON).append(NEW_LINE);
        }
        if (filterVOName != null) {
            header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(FILTER_BEAN_JAVA_PACKAGE_NAME)
                    .append(PERIOD).append(filterVOName).append(SEMICOLON).append(NEW_LINE);
        }
        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(VO_JAVA_PACKAGE_NAME).append(PERIOD)
                .append(VO_JAVA_CLASS_NAME).append(SEMICOLON).append(NEW_LINE);

        header.append(NEW_LINE);
        header.append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(
                JAVA_KEYWORD_INTERFACE).append(SPACE).append(DAO_JAVA_INTERFACE_NAME)
                .append(SPACE);

        header.append(SPACE).append(OPEN_BRACE).append(DBL_NEW_LINE);
        return header;
    }


    private StringBuffer generateDAOInterfaceDefaultMethods(List<VOFieldToColumnMappingDetails> dbColumns) {
        StringBuffer sb = new StringBuffer();
        sb.append(generateDAOInterfaceMethodSelectAllRecords());
        if (dbColumns != null) {
            sb.append(generateDAOInterfaceMethodSelectRecordByPK(dbColumns));
        }
        sb.append(generateDAOInterfaceMethodInsertRecordAndPK());
        sb.append(generateDAOInterfaceMethodUpdateRecordByPK());
        sb.append(generateDAOInterfaceMethodDeleteRecordByPK());
        return sb;
    }

    //public Converter getConverter(String acctNumber) throws DAOAppException;
    private StringBuffer generateDAOInterfaceMethodSelectRecordByPK(List<VOFieldToColumnMappingDetails> dbColumns) {


        List<VOFieldToColumnMappingDetails> pkColumns = null;
        if (dbColumns != null) {
            getPrimaryKeys(dbColumns);
            if (pkColumns != null && pkColumns.isEmpty()) {
                pkColumns.add(dbColumns.get(0));
            }
        }


        StringBuffer method = new StringBuffer();
        method.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE)
                .append(VO_JAVA_CLASS_NAME).append(SPACE).append(GET_PREFIX).append(VO_JAVA_CLASS_NAME)
                .append(OPEN_BRACKET);

        if (pkColumns != null) {
            //argument list
            Iterator iter = pkColumns.iterator();
            while (iter.hasNext()) {
                VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter.next();
                String typeName = getTypeName(detail);
                typeName = getShortJavaTypeNameBeforeFirstPeriod(typeName);
                method.append(typeName).append(SPACE).append(detail.javaFieldName).append(COMMA);
            }
            method.deleteCharAt(method.length() - 1);
        }

        method.append(CLOSE_BRACKET).append(SPACE)
                .append(JAVA_KEYWORD_THROWS).append(SPACE).append(DAO_APP_EXCEPTION_CLASS_NAME)
                .append(SEMICOLON).append(NEW_LINE);

        return method;
    }

    //public List getConverter(SomeFilter filter) throws DAOAppException
    private StringBuffer generateDAOInterfaceMethodSelectRecordByFilter(String filterVOName) {
        StringBuffer method = new StringBuffer();
        method.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE)
                .append(JAVA_LIB_UTIL_LIST).append(SPACE).append(GET_PREFIX).append(VO_JAVA_CLASS_NAME)
                .append(OPEN_BRACKET).append(filterVOName).append(SPACE)
                .append(FIELD_NAME_FILTER).append(CLOSE_BRACKET).append(SPACE)
                .append(JAVA_KEYWORD_THROWS).append(SPACE).append(DAO_APP_EXCEPTION_CLASS_NAME)
                .append(SEMICOLON).append(NEW_LINE);
        return method;
    }

    //public void addConverter(Converter vo) throws DAOAppException;
    private StringBuffer generateDAOInterfaceMethodInsertRecordAndPK() {
        StringBuffer method = new StringBuffer();
        method.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE)
                .append(JAVA_KEYWORD_VOID).append(SPACE).append(ADD_PREFIX).append(VO_JAVA_CLASS_NAME)
                .append(OPEN_BRACKET).append(VO_JAVA_CLASS_NAME).append(SPACE)
                .append(DAO_MEMBER_NAME_VO).append(CLOSE_BRACKET).append(SPACE)
                .append(JAVA_KEYWORD_THROWS).append(SPACE).append(DAO_APP_EXCEPTION_CLASS_NAME)
                .append(SEMICOLON).append(NEW_LINE);
        return method;
    }

    //public void updateConverter(Converter vo) throws DAOAppException;
    private StringBuffer generateDAOInterfaceMethodUpdateRecordByPK() {
        StringBuffer method = new StringBuffer();
        method.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE)
                .append(JAVA_KEYWORD_VOID).append(SPACE).append(UPDATE_PREFIX).append(VO_JAVA_CLASS_NAME)
                .append(OPEN_BRACKET).append(VO_JAVA_CLASS_NAME).append(SPACE)
                .append(DAO_MEMBER_NAME_VO).append(CLOSE_BRACKET).append(SPACE)
                .append(JAVA_KEYWORD_THROWS).append(SPACE).append(DAO_APP_EXCEPTION_CLASS_NAME)
                .append(SEMICOLON).append(NEW_LINE);
        return method;
    }

    //public void deleteConverter(Converter vo) throws DAOAppException;
    private StringBuffer generateDAOInterfaceMethodDeleteRecordByPK() {
        StringBuffer method = new StringBuffer();
        method.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE)
                .append(JAVA_KEYWORD_VOID).append(SPACE).append(DELETE_PREFIX).append(VO_JAVA_CLASS_NAME)
                .append(OPEN_BRACKET).append(VO_JAVA_CLASS_NAME).append(SPACE)
                .append(DAO_MEMBER_NAME_VO).append(CLOSE_BRACKET).append(SPACE)
                .append(JAVA_KEYWORD_THROWS).append(SPACE).append(DAO_APP_EXCEPTION_CLASS_NAME)
                .append(SEMICOLON).append(NEW_LINE);
        return method;
    }

    private StringBuffer generateDAOInterfaceMethodSelectAllRecords() {
        StringBuffer method = new StringBuffer();
        method.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE)
                .append(JAVA_LIB_UTIL_LIST).append(SPACE).append(GET_PREFIX).append(VO_JAVA_CLASS_NAME)
                .append(PLURAL_SUFFIX).append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SPACE)
                .append(JAVA_KEYWORD_THROWS).append(SPACE).append(DAO_APP_EXCEPTION_CLASS_NAME)
                .append(SEMICOLON).append(NEW_LINE);
        return method;
    }
}