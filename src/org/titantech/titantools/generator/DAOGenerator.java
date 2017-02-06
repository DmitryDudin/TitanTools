package org.titantech.titantools.generator;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean;
import org.titantech.titantools.dao.bean.VOFieldToColumnMappingDetails;

public class DAOGenerator extends GeneratorBase {

    public String generateDAOClass(List<VOFieldToColumnMappingDetails> columns) {
        return generateDAOClass(columns, null, null);
    }

    public String generateDAOClass(List<VOFieldToColumnMappingDetails> columns, List inputBeans, String filterVOName) {
        StringBuffer contents = new StringBuffer();
        contents.append(generateDAOClassFileHeader(filterVOName));
        contents.append(generateDAOClassMembers());
        contents.append(generateDAOClassDefaultConstructor());
        contents.append(generateDAOClassDefaultMethods(columns));
        if (filterVOName != null) {
            contents.append(generateDAOClassMethodSelectRecordsByFilter(inputBeans, filterVOName));
        }
        contents.append(generateClassOrInterfaceFooter());
        String str = contents.toString();
        System.out.println(str);
        return str;
    }

    private StringBuffer generateDAOClassMembers() {
        StringBuffer members = new StringBuffer();
        members.append(TAB).append(JAVA_KEYWORD_PRIVATE).append(SPACE)
                .append(PG_DAO_FACTORY_CLASS_NAME).append(SPACE)
                .append(PG_DAO_FACTORY_MEMBER_NAME).append(EQUAL)
                .append(NULL).append(SEMICOLON).append(NEW_LINE);
        return members;
    }

    private StringBuffer generateDAOClassDefaultConstructor() {
        StringBuffer constructor = new StringBuffer();
        constructor.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE)
                .append(DAO_JAVA_CLASS_NAME).append(OPEN_BRACKET)
                .append(PG_DAO_FACTORY_CLASS_NAME).append(SPACE)
                .append(PG_DAO_FACTORY_MEMBER_NAME).append(CLOSE_BRACKET)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);
        constructor.append(TAB).append(TAB).append(JAVA_KEYWORD_THIS).append(PERIOD)
                .append(PG_DAO_FACTORY_MEMBER_NAME).append(SPACE).append(EQUAL)
                .append(SPACE).append(PG_DAO_FACTORY_MEMBER_NAME).append(SEMICOLON).append(NEW_LINE);
        constructor.append(TAB).append(CLOSE_BRACE).append(DBL_NEW_LINE);
        return constructor;
    }

    private StringBuffer generateDAOClassFileHeader(String filterVOName) {
        StringBuffer header = new StringBuffer();
        header.append(JAVA_KEYWORD_PACKAGE).append(SPACE).append(
                DAO_JAVA_PACKAGE_NAME).append(SEMICOLON).append(DBL_NEW_LINE);
        Iterator iter = DAO_CLASS_IMPORTS.iterator();
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
                JAVA_KEYWORD_CLASS).append(SPACE).append(DAO_JAVA_CLASS_NAME)
                .append(SPACE);
        if (!DAO_CLASS_EXTENDS.isEmpty()) {
            header.append(JAVA_KEYWORD_EXTENDS).append(SPACE);
            iter = DAO_CLASS_EXTENDS.iterator();
            while (iter.hasNext()) {
                header.append((String) iter.next());
                if (iter.hasNext()) {
                    header.append(COMMA).append(SPACE);
                }
            }
        }
        header.append(SPACE);
        header.append(JAVA_KEYWORD_IMPLEMENTS).append(SPACE).append(DAO_JAVA_INTERFACE_NAME)
                .append(SPACE).append(OPEN_BRACE).append(DBL_NEW_LINE);
        return header;
    }

    private StringBuffer generateDAOClassDefaultMethods(List<VOFieldToColumnMappingDetails> columns) {
        StringBuffer sb = new StringBuffer();
        sb.append(generateDAOClassMethodSelectAllRecords());
        sb.append(generateDAOClassMethodSelectRecordByPK(columns));
        sb.append(generateDAOClassMethodInsertRecordAndPK(columns));
        sb.append(generateDAOClassMethodUpdateRecordByPK(columns));
        sb.append(generateDAOClassMethodDeleteRecordByPK(columns));
        return sb;
    }

    private StringBuffer generateDAOClassMethodSelectRecordByPK(List<VOFieldToColumnMappingDetails> columns) {
        StringBuffer method = new StringBuffer();
        //	public Converter getConverter(String acctNumber) throws DAOAppException {
        //public House getHouse(
        method.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE)
                .append(VO_JAVA_CLASS_NAME).append(SPACE).append(GET_PREFIX).append(VO_JAVA_CLASS_NAME)
                .append(OPEN_BRACKET);

        Iterator iter = columns.iterator();
        while (iter.hasNext()) {
            VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter.next();
            if (detail.isPK) {
                String typeName = getTypeName(detail);
                typeName = getShortJavaTypeNameBeforeFirstPeriod(typeName);
                method.append(typeName).append(SPACE).append(detail.javaFieldName).append(COMMA);
            }
        }
        method.deleteCharAt(method.length() - 1);

        method.append(CLOSE_BRACKET).append(SPACE)
                .append(JAVA_KEYWORD_THROWS).append(SPACE).append(DAO_APP_EXCEPTION_CLASS_NAME)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //		PreparedStatement ps = null;
        method.append(TAB).append(TAB).append(JAVA_LIB_SQL_PREPARED_STATEMENT)
                .append(SPACE).append(DAO_MEMBER_NAME_PS).append(SPACE).append(EQUAL).append(SPACE)
                .append(NULL).append(SEMICOLON).append(NEW_LINE);

        //		ResultSet rs = null;
        method.append(TAB).append(TAB).append(JAVA_LIB_SQL_RESULT_SET)
                .append(SPACE).append(DAO_MEMBER_NAME_RS).append(SPACE).append(EQUAL).append(SPACE)
                .append(NULL).append(SEMICOLON).append(NEW_LINE);

        //		try {
        method.append(TAB).append(TAB).append(JAVA_KEYWORD_TRY)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //			Connection connection = oraDAOFactory.getConnection();
        method.append(TAB).append(TAB).append(TAB).append(JAVA_LIB_SQL_CONNECTION)
                .append(SPACE).append(DAO_MEMBER_NAME_CONNECTION).append(SPACE).append(EQUAL)
                .append(SPACE).append(PG_DAO_FACTORY_MEMBER_NAME).append(PERIOD).append(DAO_METHOD_NAME_GETCONNECTION)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //			String sql = "SELECT * FROM CONVERTER WHERE PK=?";
        method.append(TAB).append(TAB).append(TAB).append(JAVA_LIB_STRING)
                .append(SPACE).append(DAO_MEMBER_NAME_SQL).append(SPACE).append(EQUAL)
                .append(SPACE).append(DOUBLE_QUOTE).append(DB_SQL_SELECT).append(SPACE)
                .append(ASTERISK).append(SPACE).append(DB_SQL_FROM).append(SPACE)
                .append(DATABASE_TABLE_NAME).append(SPACE).append(DB_SQL_WHERE);

        StringBuffer pkValues = new StringBuffer();
        StringBuffer pks = new StringBuffer();
        //DB_PK_COLUMN_SET
        iter = columns.iterator();
        while (iter.hasNext()) {
            VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter.next();
            if (detail.isPK) {
                pks.append(SPACE).append(detail.dbColumnName).append(EQUAL).append(QUESTION);
                if (iter.hasNext()) {
                    pks.append(SPACE).append(DB_SQL_AND);
                }

                //i = setSQLParameter(ps, i, reportRecord.getField1(), java.sql.Types.VARCHAR);

                pkValues.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_I).append(SPACE)
                        .append(EQUAL).append(SPACE).append(DAO_METHOD_NAME_SETSQLPARAMETERS).append(OPEN_BRACKET)
                        .append(DAO_MEMBER_NAME_PS).append(COMMA).append(SPACE)
                        .append(DAO_MEMBER_NAME_I).append(COMMA).append(SPACE).append(detail.javaFieldName)
                        .append(COMMA).append(SPACE).append(detail.dbColumnTypeName)
                        .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

                //pkValues.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_PS).append(PERIOD)
                //	.append(DAO_METHOD_NAME_SETSTRING).append(OPEN_BRACKET)
                //	.append(DAO_MEMBER_NAME_I).append(PLUS).append(PLUS).append(COMMA).append(SPACE)
                //	.append(DAO_MEMBER_NAME_VO).append(PERIOD).append(detail.javaGetterMethod).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                //	.append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

            }
        }

        String pksStr = pks.toString();
        if (pksStr.endsWith(DB_SQL_AND)) {
            pksStr = pksStr.substring(0, pksStr.length() - 3);
        }

        method.append(pksStr);

        method.append(DOUBLE_QUOTE).append(SEMICOLON).append(NEW_LINE);

        //ps = connection.prepareStatement(sql);
        method.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_PS)
                .append(SPACE).append(EQUAL).append(SPACE)
                .append(DAO_MEMBER_NAME_CONNECTION).append(PERIOD).append(DAO_METHOD_NAME_PREPARESTATEMENT)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_SQL).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        if (pkValues.length() > 0) {
            // int i = 1;
            method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_INT).append(SPACE)
                    .append(DAO_MEMBER_NAME_I).append(SPACE).append(EQUAL).append(SPACE).append(ONE).append(SEMICOLON).append(NEW_LINE);
        }
        //ps.setString(1, acctNumber);
        method.append(pkValues);

        //rs = ps.executeQuery();
        method.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_RS).append(SPACE)
                .append(EQUAL).append(SPACE).append(DAO_MEMBER_NAME_PS).append(PERIOD)
                .append(DAO_METHOD_NAME_EXECUTEQUERY).append(OPEN_BRACKET)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //if (rs.next()) {
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(SPACE)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_RS).append(PERIOD).append(DAO_METHOD_NAME_NEXT)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(CLOSE_BRACKET).append(SPACE)
                .append(OPEN_BRACE).append(NEW_LINE);

        //return (Converter)populateValueObject(rs, Converter.class, null);
        method.append(TAB).append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_RETURN).append(SPACE)
                .append(OPEN_BRACKET).append(VO_JAVA_CLASS_NAME).append(CLOSE_BRACKET)
                .append(DAO_METHOD_NAME_POPULATEVALUEOBJECT).append(OPEN_BRACKET)
                .append(DAO_MEMBER_NAME_RS).append(COMMA).append(SPACE)
                .append(VO_JAVA_CLASS_NAME).append(PERIOD).append(JAVA_KEYWORD_CLASS)
                //.append(COMMA).append(SPACE).append(NULL)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        // }
        method.append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

        // return null;
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_RETURN).append(SPACE).append(NULL).append(SEMICOLON).append(NEW_LINE);

        //} catch (SQLException e) {
        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH)
                .append(SPACE).append(OPEN_BRACKET).append(JAVA_LIB_SQL_SQLEXCEPTION).append(SPACE)
                .append(DAO_MEMBER_NAME_E).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //throw new DAOSysException(e.getMessage());
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE)
                .append(JAVA_KEYWORD_NEW).append(SPACE).append(JAVA_DAO_SYS_EXCEPTION)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_E)
                .append(PERIOD).append(DAO_METHOD_NAME_GETMESSAGE).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //} catch (ParseException e) {
        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH)
                .append(SPACE).append(OPEN_BRACKET).append(JAVA_TEXT_PARSEEXCEPTION).append(SPACE)
                .append(DAO_MEMBER_NAME_E).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //throw new DAOSysException(e.getMessage());
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE)
                .append(JAVA_KEYWORD_NEW).append(SPACE).append(JAVA_DAO_SYS_EXCEPTION)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_E)
                .append(PERIOD).append(DAO_METHOD_NAME_GETMESSAGE).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //} finally {
        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_FINALLY)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //finalize(rs, ps);
        method.append(TAB).append(TAB).append(TAB).append(DAO_METHOD_NAME_FINALIZE)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_RS).append(COMMA).append(SPACE)
                .append(DAO_MEMBER_NAME_PS).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        method.append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        return method;
    }

	/*
    public void addConverter(Converter vo) throws DAOAppException {
		String sql = "INSERT INTO DATABASE_TABLE_NAME (PLUGIN_ID, RECORD_ID, CREATED_DATE) VALUES (?,?,SYSDATE)";
		PreparedStatement ps = null;
		try {
			Connection connection = oraDAOFactory.getConnection();
			ps = connection.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, caseTypeId.intValue());
			ps.setString(i++, orderId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOSysException(e.getMessage());
		} finally {
			finalize(null, ps);
		}
		return sequence;
	}


	 */

    private StringBuffer generateDAOClassMethodInsertRecordAndPK(List columns) {
        StringBuffer method = new StringBuffer();

        //public void addConverter(Converter vo) throws DAOAppException {
        method.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE)
                .append(JAVA_KEYWORD_VOID).append(SPACE).append(ADD_PREFIX).append(VO_JAVA_CLASS_NAME)
                .append(OPEN_BRACKET).append(VO_JAVA_CLASS_NAME).append(SPACE)
                .append(DAO_MEMBER_NAME_VO).append(CLOSE_BRACKET).append(SPACE)
                .append(JAVA_KEYWORD_THROWS).append(SPACE).append(DAO_APP_EXCEPTION_CLASS_NAME)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //			String sql = "INSERT INTO CONVERTER (FIELD, FIELD) VALUES (1,2)";
        method.append(TAB).append(TAB).append(JAVA_LIB_STRING)
                .append(SPACE).append(DAO_MEMBER_NAME_SQL).append(SPACE).append(EQUAL)
                .append(SPACE).append(DOUBLE_QUOTE)

                .append(DB_SQL_INSERT).append(SPACE).append(DB_SQL_INTO)
                .append(SPACE).append(DATABASE_TABLE_NAME).append(SPACE)
                .append(OPEN_BRACKET);

        StringBuffer values = new StringBuffer();
        Iterator iter = columns.iterator();
        while (iter.hasNext()) {
            VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter.next();
            if (!detail.isSerial) {
                method.append(detail.dbColumnName);
                values.append(QUESTION);
                if (iter.hasNext()) {
                    method.append(COMMA);
                    values.append(COMMA);
                }
            }
        }

        method.append(CLOSE_BRACKET).append(SPACE).append(DB_SQL_VALUES).append(SPACE).append(OPEN_BRACKET).append(values)
                .append(CLOSE_BRACKET).append(DOUBLE_QUOTE).append(SEMICOLON).append(NEW_LINE);


        //		PreparedStatement ps = null;
        method.append(TAB).append(TAB).append(JAVA_LIB_SQL_PREPARED_STATEMENT)
                .append(SPACE).append(DAO_MEMBER_NAME_PS).append(SPACE).append(EQUAL).append(SPACE)
                .append(NULL).append(SEMICOLON).append(NEW_LINE);

        //		try {
        method.append(TAB).append(TAB).append(JAVA_KEYWORD_TRY)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);


        //			Connection connection = oraDAOFactory.getConnection();
        method.append(TAB).append(TAB).append(TAB).append(JAVA_LIB_SQL_CONNECTION)
                .append(SPACE).append(DAO_MEMBER_NAME_CONNECTION).append(SPACE).append(EQUAL)
                .append(SPACE).append(PG_DAO_FACTORY_MEMBER_NAME).append(PERIOD).append(DAO_METHOD_NAME_GETCONNECTION)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);


        //ps = connection.prepareStatement(sql);
        method.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_PS)
                .append(SPACE).append(EQUAL).append(SPACE)
                .append(DAO_MEMBER_NAME_CONNECTION).append(PERIOD).append(DAO_METHOD_NAME_PREPARESTATEMENT)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_SQL).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        // int i = 1;
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_INT).append(SPACE)
                .append(DAO_MEMBER_NAME_I).append(SPACE).append(EQUAL).append(SPACE).append(ONE).append(SEMICOLON).append(NEW_LINE);

        iter = columns.iterator();
        while (iter.hasNext()) {
            VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter.next();
            //			ps.setString(i++, vo.getWhatever());
            if (!detail.isSerial) {
                method.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_I).append(SPACE)
                        .append(EQUAL).append(SPACE).append(DAO_METHOD_NAME_SETSQLPARAMETERS).append(OPEN_BRACKET)
                        .append(DAO_MEMBER_NAME_PS).append(COMMA).append(SPACE)
                        .append(DAO_MEMBER_NAME_I).append(COMMA).append(SPACE)
                        .append(DAO_MEMBER_NAME_VO).append(PERIOD).append(detail.javaGetterMethod).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                        .append(COMMA).append(SPACE).append(detail.dbColumnTypeName)
                        .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
            }
            //method.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_PS).append(PERIOD)
            //	.append(DAO_METHOD_NAME_SETSTRING).append(OPEN_BRACKET)
            //	.append(DAO_MEMBER_NAME_I).append(PLUS).append(PLUS).append(COMMA).append(SPACE)
            //	.append(DAO_MEMBER_NAME_VO).append(PERIOD).append(detail.javaGetterMethod).append(OPEN_BRACKET).append(CLOSE_BRACKET)
            //	.append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
        }

//!!
        //rs = ps.executeUpdate();
        method.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_PS).append(PERIOD)
                .append(DAO_METHOD_NAME_EXECUTEUPDATE).append(OPEN_BRACKET)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //} catch (SQLException e) {
        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH)
                .append(SPACE).append(OPEN_BRACKET).append(JAVA_LIB_SQL_SQLEXCEPTION).append(SPACE)
                .append(DAO_MEMBER_NAME_E).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //throw new DAOSysException(e.getMessage());
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE)
                .append(JAVA_KEYWORD_NEW).append(SPACE).append(JAVA_DAO_SYS_EXCEPTION)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_E)
                .append(PERIOD).append(DAO_METHOD_NAME_GETMESSAGE).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //} catch (ParseException e) {
        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH)
                .append(SPACE).append(OPEN_BRACKET).append(JAVA_TEXT_PARSEEXCEPTION).append(SPACE)
                .append(DAO_MEMBER_NAME_E).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //throw new DAOSysException(e.getMessage());
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE)
                .append(JAVA_KEYWORD_NEW).append(SPACE).append(JAVA_DAO_SYS_EXCEPTION)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_E)
                .append(PERIOD).append(DAO_METHOD_NAME_GETMESSAGE).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //} finally {
        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_FINALLY)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //finalize(null, ps);
        method.append(TAB).append(TAB).append(TAB).append(DAO_METHOD_NAME_FINALIZE)
                .append(OPEN_BRACKET).append(NULL).append(COMMA).append(SPACE)
                .append(DAO_MEMBER_NAME_PS).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        method.append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

        return method;
    }


    private StringBuffer generateDAOClassMethodUpdateRecordByPK(List columns) {
        StringBuffer method = new StringBuffer();

        //public void updateConverter(Converter vo) throws DAOAppException {
        method.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE)
                .append(JAVA_KEYWORD_VOID).append(SPACE).append(UPDATE_PREFIX).append(VO_JAVA_CLASS_NAME)
                .append(OPEN_BRACKET).append(VO_JAVA_CLASS_NAME).append(SPACE)
                .append(DAO_MEMBER_NAME_VO).append(CLOSE_BRACKET).append(SPACE)
                .append(JAVA_KEYWORD_THROWS).append(SPACE).append(DAO_APP_EXCEPTION_CLASS_NAME)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //			String sql = "UPDATE CONVERTER SET FIELD=?, FIELD=? WHERE PK=?";
        method.append(TAB).append(TAB).append(JAVA_LIB_STRING)
                .append(SPACE).append(DAO_MEMBER_NAME_SQL).append(SPACE).append(EQUAL)
                .append(SPACE).append(DOUBLE_QUOTE)

                .append(DB_SQL_UPDATE).append(SPACE).append(DATABASE_TABLE_NAME).append(SPACE).append(DB_SQL_SET).append(SPACE);

        StringBuffer pks = new StringBuffer();
        Iterator iter = columns.iterator();
        while (iter.hasNext()) {
            VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter.next();
            if (!detail.isPK) {
                method.append(detail.dbColumnName).append(EQUAL).append(QUESTION);
                if (iter.hasNext()) {
                    method.append(COMMA).append(SPACE);
                }
            } else {
                pks.append(detail.dbColumnName).append(EQUAL).append(QUESTION);
                if (iter.hasNext()) {
                    pks.append(SPACE).append(DB_SQL_AND).append(SPACE);
                }
            }
        }

        String pksStr = pks.toString();
        if (pksStr.endsWith(DB_SQL_AND + SPACE)) {
            pksStr = pksStr.substring(0, pksStr.length() - 4);
        }

        method.append(SPACE).append(DB_SQL_WHERE).append(SPACE).append(pksStr)
                .append(DOUBLE_QUOTE).append(SEMICOLON).append(NEW_LINE);


        //		PreparedStatement ps = null;
        method.append(TAB).append(TAB).append(JAVA_LIB_SQL_PREPARED_STATEMENT)
                .append(SPACE).append(DAO_MEMBER_NAME_PS).append(SPACE).append(EQUAL).append(SPACE)
                .append(NULL).append(SEMICOLON).append(NEW_LINE);

        //		try {
        method.append(TAB).append(TAB).append(JAVA_KEYWORD_TRY)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //			Connection connection = oraDAOFactory.getConnection();
        method.append(TAB).append(TAB).append(TAB).append(JAVA_LIB_SQL_CONNECTION)
                .append(SPACE).append(DAO_MEMBER_NAME_CONNECTION).append(SPACE).append(EQUAL)
                .append(SPACE).append(PG_DAO_FACTORY_MEMBER_NAME).append(PERIOD).append(DAO_METHOD_NAME_GETCONNECTION)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //ps = connection.prepareStatement(sql);
        method.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_PS)
                .append(SPACE).append(EQUAL).append(SPACE)
                .append(DAO_MEMBER_NAME_CONNECTION).append(PERIOD).append(DAO_METHOD_NAME_PREPARESTATEMENT)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_SQL).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        // int i = 1;
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_INT).append(SPACE)
                .append(DAO_MEMBER_NAME_I).append(SPACE).append(EQUAL).append(SPACE).append(ONE).append(SEMICOLON).append(NEW_LINE);

        StringBuffer pk = new StringBuffer();
        iter = columns.iterator();
        while (iter.hasNext()) {
            VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter.next();
            if (!detail.isPK) {
                //			ps.setString(i++, vo.getWhatever());

                method.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_I).append(SPACE)
                        .append(EQUAL).append(SPACE).append(DAO_METHOD_NAME_SETSQLPARAMETERS).append(OPEN_BRACKET)
                        .append(DAO_MEMBER_NAME_PS).append(COMMA).append(SPACE)
                        .append(DAO_MEMBER_NAME_I).append(COMMA).append(SPACE)
                        .append(DAO_MEMBER_NAME_VO).append(PERIOD).append(detail.javaGetterMethod).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                        .append(COMMA).append(SPACE).append(detail.dbColumnTypeName)
                        .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);


                //method.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_PS).append(PERIOD)
                //	.append(DAO_METHOD_NAME_SETSTRING).append(OPEN_BRACKET)
                //	.append(DAO_MEMBER_NAME_I).append(PLUS).append(PLUS).append(COMMA).append(SPACE)
                //	.append(DAO_MEMBER_NAME_VO).append(PERIOD).append(detail.javaGetterMethod).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                //	.append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
            } else {

                pk.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_I).append(SPACE)
                        .append(EQUAL).append(SPACE).append(DAO_METHOD_NAME_SETSQLPARAMETERS).append(OPEN_BRACKET)
                        .append(DAO_MEMBER_NAME_PS).append(COMMA).append(SPACE)
                        .append(DAO_MEMBER_NAME_I).append(COMMA).append(SPACE)
                        .append(DAO_MEMBER_NAME_VO).append(PERIOD).append(detail.javaGetterMethod).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                        .append(COMMA).append(SPACE).append(detail.dbColumnTypeName)
                        .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

                //pk.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_PS).append(PERIOD)
                //	.append(DAO_METHOD_NAME_SETSTRING).append(OPEN_BRACKET)
                //	.append(DAO_MEMBER_NAME_I).append(PLUS).append(PLUS).append(COMMA).append(SPACE)
                //	.append(DAO_MEMBER_NAME_VO).append(PERIOD).append(detail.javaGetterMethod).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                //	.append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
            }
        }

        // ps.setString(i++, orderId);
        method.append(pk);

//!!
        //rs = ps.executeUpdate();
        method.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_PS).append(PERIOD)
                .append(DAO_METHOD_NAME_EXECUTEUPDATE).append(OPEN_BRACKET)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //} catch (SQLException e) {
        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH)
                .append(SPACE).append(OPEN_BRACKET).append(JAVA_LIB_SQL_SQLEXCEPTION).append(SPACE)
                .append(DAO_MEMBER_NAME_E).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //throw new DAOSysException(e.getMessage());
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE)
                .append(JAVA_KEYWORD_NEW).append(SPACE).append(JAVA_DAO_SYS_EXCEPTION)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_E)
                .append(PERIOD).append(DAO_METHOD_NAME_GETMESSAGE).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //} catch (ParseException e) {
        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH)
                .append(SPACE).append(OPEN_BRACKET).append(JAVA_TEXT_PARSEEXCEPTION).append(SPACE)
                .append(DAO_MEMBER_NAME_E).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //throw new DAOSysException(e.getMessage());
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE)
                .append(JAVA_KEYWORD_NEW).append(SPACE).append(JAVA_DAO_SYS_EXCEPTION)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_E)
                .append(PERIOD).append(DAO_METHOD_NAME_GETMESSAGE).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //} finally {
        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_FINALLY)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //finalize(null, ps);
        method.append(TAB).append(TAB).append(TAB).append(DAO_METHOD_NAME_FINALIZE)
                .append(OPEN_BRACKET).append(NULL).append(COMMA).append(SPACE)
                .append(DAO_MEMBER_NAME_PS).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        method.append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

        return method;
    }


    private StringBuffer generateDAOClassMethodDeleteRecordByPK(List columns) {
        StringBuffer method = new StringBuffer();

        //public void deleteConverter(Converter vo) throws DAOAppException {
        method.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE)
                .append(JAVA_KEYWORD_VOID).append(SPACE).append(DELETE_PREFIX).append(VO_JAVA_CLASS_NAME)
                .append(OPEN_BRACKET).append(VO_JAVA_CLASS_NAME).append(SPACE)
                .append(DAO_MEMBER_NAME_VO).append(CLOSE_BRACKET).append(SPACE)
                .append(JAVA_KEYWORD_THROWS).append(SPACE).append(DAO_APP_EXCEPTION_CLASS_NAME)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //			String sql = "DELETE CONVERTER WHERE PK=?";
        method.append(TAB).append(TAB).append(JAVA_LIB_STRING)
                .append(SPACE).append(DAO_MEMBER_NAME_SQL).append(SPACE).append(EQUAL)
                .append(SPACE).append(DOUBLE_QUOTE)
                .append(DB_SQL_DELETE).append(SPACE).append(DB_SQL_FROM).append(SPACE).append(DATABASE_TABLE_NAME).append(SPACE).append(DB_SQL_WHERE).append(SPACE);

        StringBuffer pkValues = new StringBuffer();
        StringBuffer pks = new StringBuffer();
        Iterator iter = columns.iterator();
        while (iter.hasNext()) {
            VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter.next();
            if (detail.isPK) {
                pks.append(detail.dbColumnName).append(EQUAL).append(QUESTION);
                if (iter.hasNext()) {
                    pks.append(SPACE).append(DB_SQL_AND).append(SPACE);
                }

                pkValues.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_I).append(SPACE)
                        .append(EQUAL).append(SPACE).append(DAO_METHOD_NAME_SETSQLPARAMETERS).append(OPEN_BRACKET)
                        .append(DAO_MEMBER_NAME_PS).append(COMMA).append(SPACE)
                        .append(DAO_MEMBER_NAME_I).append(COMMA).append(SPACE)
                        .append(DAO_MEMBER_NAME_VO).append(PERIOD).append(detail.javaGetterMethod).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                        .append(COMMA).append(SPACE).append(detail.dbColumnTypeName)
                        .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

                //pkValues.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_PS).append(PERIOD)
                //	.append(DAO_METHOD_NAME_SETSTRING).append(OPEN_BRACKET)
                //	.append(DAO_MEMBER_NAME_I).append(PLUS).append(PLUS).append(COMMA).append(SPACE)
                //	.append(DAO_MEMBER_NAME_VO).append(PERIOD).append(detail.javaGetterMethod).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                //	.append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

            }
        }
        String pksStr = pks.toString();
        if (pksStr.endsWith(DB_SQL_AND + SPACE)) {
            pksStr = pksStr.substring(0, pksStr.length() - 4);
        }

        method.append(SPACE).append(pksStr)
                .append(DOUBLE_QUOTE).append(SEMICOLON).append(NEW_LINE);


        //		PreparedStatement ps = null;
        method.append(TAB).append(TAB).append(JAVA_LIB_SQL_PREPARED_STATEMENT)
                .append(SPACE).append(DAO_MEMBER_NAME_PS).append(SPACE).append(EQUAL).append(SPACE)
                .append(NULL).append(SEMICOLON).append(NEW_LINE);

        //		try {
        method.append(TAB).append(TAB).append(JAVA_KEYWORD_TRY)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //			Connection connection = oraDAOFactory.getConnection();
        method.append(TAB).append(TAB).append(TAB).append(JAVA_LIB_SQL_CONNECTION)
                .append(SPACE).append(DAO_MEMBER_NAME_CONNECTION).append(SPACE).append(EQUAL)
                .append(SPACE).append(PG_DAO_FACTORY_MEMBER_NAME).append(PERIOD).append(DAO_METHOD_NAME_GETCONNECTION)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //ps = connection.prepareStatement(sql);
        method.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_PS)
                .append(SPACE).append(EQUAL).append(SPACE)
                .append(DAO_MEMBER_NAME_CONNECTION).append(PERIOD).append(DAO_METHOD_NAME_PREPARESTATEMENT)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_SQL).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        // int i = 1;
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_INT).append(SPACE)
                .append(DAO_MEMBER_NAME_I).append(SPACE).append(EQUAL).append(SPACE).append(ONE).append(SEMICOLON).append(NEW_LINE);

        // ps.setString(1, orderId);
        method.append(pkValues);

//!!
        //rs = ps.executeUpdate();
        method.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_PS).append(PERIOD)
                .append(DAO_METHOD_NAME_EXECUTEUPDATE).append(OPEN_BRACKET)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //} catch (SQLException e) {
        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH)
                .append(SPACE).append(OPEN_BRACKET).append(JAVA_LIB_SQL_SQLEXCEPTION).append(SPACE)
                .append(DAO_MEMBER_NAME_E).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //throw new DAOSysException(e.getMessage());
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE)
                .append(JAVA_KEYWORD_NEW).append(SPACE).append(JAVA_DAO_SYS_EXCEPTION)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_E)
                .append(PERIOD).append(DAO_METHOD_NAME_GETMESSAGE).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //} catch (ParseException e e) {
        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH)
                .append(SPACE).append(OPEN_BRACKET).append(JAVA_TEXT_PARSEEXCEPTION).append(SPACE)
                .append(DAO_MEMBER_NAME_E).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //throw new DAOSysException(e.getMessage());
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE)
                .append(JAVA_KEYWORD_NEW).append(SPACE).append(JAVA_DAO_SYS_EXCEPTION)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_E)
                .append(PERIOD).append(DAO_METHOD_NAME_GETMESSAGE).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //} finally {
        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_FINALLY)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //finalize(null, ps);
        method.append(TAB).append(TAB).append(TAB).append(DAO_METHOD_NAME_FINALIZE)
                .append(OPEN_BRACKET).append(NULL).append(COMMA).append(SPACE)
                .append(DAO_MEMBER_NAME_PS).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        method.append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

        return method;
    }

	/*
    public List getConverter(String acctNumber) throws DAOAppException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			Connection connection = oraDAOFactory.getConnection();
			String sql = "SELECT * FROM CONVERTER";
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Converter vo = (Converter)populateValueObject(rs, Converter.class, null);
				list.add(vo);
			}
			return list;
		} catch (SQLException e) {
			throw new DAOSysException(e.getMessage());
		} finally {
			finalize(rs, ps);
		}
	}


	*/

    private StringBuffer generateDAOClassMethodSelectAllRecords() {
        StringBuffer method = new StringBuffer();
        //public List getVO_JAVA_CLASS_NAMEs() throws DAO_APP_EXCEPTION_CLASS_NAME {
        method.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE)
                .append(JAVA_LIB_UTIL_LIST).append(SPACE).append(GET_PREFIX).append(VO_JAVA_CLASS_NAME)
                .append(PLURAL_SUFFIX).append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SPACE)
                .append(JAVA_KEYWORD_THROWS).append(SPACE).append(DAO_APP_EXCEPTION_CLASS_NAME)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);


        method.append(TAB).append(TAB).append(JAVA_LIB_SQL_PREPARED_STATEMENT)
                .append(SPACE).append(DAO_MEMBER_NAME_PS).append(SPACE).append(EQUAL).append(SPACE)
                .append(NULL).append(SEMICOLON).append(NEW_LINE);


        method.append(TAB).append(TAB).append(JAVA_LIB_SQL_RESULT_SET)
                .append(SPACE).append(DAO_MEMBER_NAME_RS).append(SPACE).append(EQUAL).append(SPACE)
                .append(NULL).append(SEMICOLON).append(NEW_LINE);

        method.append(TAB).append(TAB).append(JAVA_LIB_UTIL_LIST)
                .append(SPACE).append(DAO_MEMBER_NAME_LIST).append(SPACE).append(EQUAL)
                .append(SPACE).append(JAVA_KEYWORD_NEW).append(SPACE)
                .append(JAVA_LIB_UTIL_ARRAYLIST).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(SEMICOLON).append(NEW_LINE);

        method.append(TAB).append(TAB).append(JAVA_KEYWORD_TRY)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        method.append(TAB).append(TAB).append(TAB).append(JAVA_LIB_SQL_CONNECTION)
                .append(SPACE).append(DAO_MEMBER_NAME_CONNECTION).append(SPACE).append(EQUAL)
                .append(SPACE).append(PG_DAO_FACTORY_MEMBER_NAME).append(PERIOD).append(DAO_METHOD_NAME_GETCONNECTION)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        method.append(TAB).append(TAB).append(TAB).append(JAVA_LIB_STRING)
                .append(SPACE).append(DAO_MEMBER_NAME_SQL).append(SPACE).append(EQUAL)
                .append(SPACE).append(DOUBLE_QUOTE).append(DB_SQL_SELECT).append(SPACE)
                .append(ASTERISK).append(SPACE).append(DB_SQL_FROM).append(SPACE)
                .append(DATABASE_TABLE_NAME).append(DOUBLE_QUOTE).append(SEMICOLON).append(NEW_LINE);

        //ps = connection.prepareStatement(sql);
        method.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_PS)
                .append(SPACE).append(EQUAL).append(SPACE)
                .append(DAO_MEMBER_NAME_CONNECTION).append(PERIOD).append(DAO_METHOD_NAME_PREPARESTATEMENT)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_SQL).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //rs = ps.executeQuery();
        method.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_RS).append(SPACE)
                .append(EQUAL).append(SPACE).append(DAO_MEMBER_NAME_PS).append(PERIOD)
                .append(DAO_METHOD_NAME_EXECUTEQUERY).append(OPEN_BRACKET)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //while (rs.next()) {
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_WHILE).append(SPACE)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_RS).append(PERIOD).append(DAO_METHOD_NAME_NEXT)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(CLOSE_BRACKET).append(SPACE)
                .append(OPEN_BRACE).append(NEW_LINE);

        //Converter vo = (Converter)populateValueObject(rs, Converter.class, null);
        method.append(TAB).append(TAB).append(TAB).append(TAB).append(VO_JAVA_CLASS_NAME).append(SPACE)
                .append(DAO_MEMBER_NAME_VO).append(SPACE).append(EQUAL).append(SPACE)
                .append(OPEN_BRACKET).append(VO_JAVA_CLASS_NAME).append(CLOSE_BRACKET)
                .append(DAO_METHOD_NAME_POPULATEVALUEOBJECT).append(OPEN_BRACKET)
                .append(DAO_MEMBER_NAME_RS).append(COMMA).append(SPACE)
                .append(VO_JAVA_CLASS_NAME).append(PERIOD).append(JAVA_KEYWORD_CLASS)
                //.append(COMMA).append(SPACE).append(NULL)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //list.add(vo);
        method.append(TAB).append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_LIST).append(PERIOD)
                .append(DAO_METHOD_NAME_ADD).append(OPEN_BRACKET).append(DAO_MEMBER_NAME_VO)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        method.append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

        // return list;
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_RETURN)
                .append(SPACE).append(DAO_MEMBER_NAME_LIST).append(SEMICOLON).append(NEW_LINE);

        //} catch (SQLException e) {
        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH)
                .append(SPACE).append(OPEN_BRACKET).append(JAVA_LIB_SQL_SQLEXCEPTION).append(SPACE)
                .append(DAO_MEMBER_NAME_E).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //throw new DAOSysException(e.getMessage());
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE)
                .append(JAVA_KEYWORD_NEW).append(SPACE).append(JAVA_DAO_SYS_EXCEPTION)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_E)
                .append(PERIOD).append(DAO_METHOD_NAME_GETMESSAGE).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //} finally {
        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_FINALLY)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //finalize(rs, ps);
        method.append(TAB).append(TAB).append(TAB).append(DAO_METHOD_NAME_FINALIZE)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_RS).append(COMMA).append(SPACE)
                .append(DAO_MEMBER_NAME_PS).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        method.append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        return method;
    }

//	public static void main(String[] args) {
//		DAOGenerator g = new DAOGenerator();
//		g.generateDAOClassMethodSelectRecordsByFilter(null, "select type_name, description from label_type $WHERE$ $ORDER$", null);
//		g.generateDAOClassMethodSelectRecordsByFilter(null, "select type_name, description from label_type WHERE $WHERE$ $ORDER$", null);
//		g.generateDAOClassMethodSelectRecordsByFilter(null, "select type_name, description from label_type $WHERE$ order by $ORDER$", null);
//		g.generateDAOClassMethodSelectRecordsByFilter(null, "select type_name, description from label_type where $WHERE$ order by $ORDER$", null);
//		g.generateDAOClassMethodSelectRecordsByFilter(null, "select type_name, description from label_type  order by $ORDER$", null);
//		g.generateDAOClassMethodSelectRecordsByFilter(null, "select type_name, description from label_type where $WHERE$ ", null);
//		g.generateDAOClassMethodSelectRecordsByFilter(null, "select type_name, description from label_type where a=a $WHERE$ order by field1 $ORDER$", null);
//		g.generateDAOClassMethodSelectRecordsByFilter(null, "select type_name, description from label_type where a=a $WHERE$ b=b order by field1 $ORDER$, field 2", null);
//	}

    private StringBuffer generateDAOClassMethodSelectRecordsByFilter(List columns, String filterVOName) {
        // example:
        // select type_name, description from label_type $WHERE$ $ORDER$

        StringBuffer filterWhereMethodSB = new StringBuffer();
        StringBuffer filterOrderMethodSB = new StringBuffer();
        StringBuffer filterSetSQLMethodSB = new StringBuffer();

        StringBuffer method = new StringBuffer();

        //	public List getConverter(SomeFilterVO filter) throws DAOAppException {
        method.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE)
                .append(JAVA_LIB_UTIL_LIST).append(SPACE).append(GET_PREFIX).append(VO_JAVA_CLASS_NAME)
                .append(OPEN_BRACKET)
                .append(filterVOName).append(SPACE).append(FIELD_NAME_FILTER)
                .append(CLOSE_BRACKET).append(SPACE)
                .append(JAVA_KEYWORD_THROWS).append(SPACE).append(DAO_APP_EXCEPTION_CLASS_NAME)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //		PreparedStatement ps = null;
        method.append(TAB).append(TAB).append(JAVA_LIB_SQL_PREPARED_STATEMENT)
                .append(SPACE).append(DAO_MEMBER_NAME_PS).append(SPACE).append(EQUAL).append(SPACE)
                .append(NULL).append(SEMICOLON).append(NEW_LINE);

        //		ResultSet rs = null;
        method.append(TAB).append(TAB).append(JAVA_LIB_SQL_RESULT_SET)
                .append(SPACE).append(DAO_MEMBER_NAME_RS).append(SPACE).append(EQUAL).append(SPACE)
                .append(NULL).append(SEMICOLON).append(NEW_LINE);

        method.append("		List list = new ArrayList();").append(NEW_LINE);
        //		try {
        method.append(TAB).append(TAB).append(JAVA_KEYWORD_TRY)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        method.append("			StringBuffer sb = new StringBuffer();").append(NEW_LINE);
        method.append("			sb.append(\"").append("SELECT * FROM " + DATABASE_TABLE_NAME).append("\");").append(NEW_LINE);
        method.append("			sb.append(getFilterWhereClause(filter));").append(NEW_LINE);

        filterWhereMethodSB.append(generateFilterWhereMethod(columns, filterVOName));
        filterSetSQLMethodSB.append(generateFilterSetSQLMethod(columns, filterVOName));

        if (isOrderable(columns)) {
            method.append("			sb.append(getFilterOrderingClause(filter));").append(NEW_LINE);
            filterOrderMethodSB.append(generateFilterOrderMethod(columns, filterVOName));
        }

        //			Connection connection = pgDAOFactory.getConnection();
        method.append(TAB).append(TAB).append(TAB).append(JAVA_LIB_SQL_CONNECTION)
                .append(SPACE).append(DAO_MEMBER_NAME_CONNECTION).append(SPACE).append(EQUAL)
                .append(SPACE).append(PG_DAO_FACTORY_MEMBER_NAME).append(PERIOD).append(DAO_METHOD_NAME_GETCONNECTION)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //			.append(b);

        //		String sql = sb.toString();
        method.append(TAB).append(TAB).append(TAB).append(JAVA_LIB_STRING).append(SPACE).append(DAO_MEMBER_NAME_SQL)
                .append(SPACE).append(EQUAL).append(SPACE)
                .append("sb").append(PERIOD).append(METHOD_NAME_TOSTRING)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //ps = connection.prepareStatement(sql);
        method.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_PS)
                .append(SPACE).append(EQUAL).append(SPACE)
                .append(DAO_MEMBER_NAME_CONNECTION).append(PERIOD).append(DAO_METHOD_NAME_PREPARESTATEMENT)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_SQL).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //			setSQLParameters(ps, filter);
        method.append(TAB).append(TAB).append(TAB).append("setSQLParameters")
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_PS).append(COMMA).append(SPACE)
                .append(FIELD_NAME_FILTER).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //rs = ps.executeQuery();
        method.append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_RS).append(SPACE)
                .append(EQUAL).append(SPACE).append(DAO_MEMBER_NAME_PS).append(PERIOD)
                .append(DAO_METHOD_NAME_EXECUTEQUERY).append(OPEN_BRACKET)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //while (rs.next()) {
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_WHILE).append(SPACE)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_RS).append(PERIOD).append(DAO_METHOD_NAME_NEXT)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(CLOSE_BRACKET).append(SPACE)
                .append(OPEN_BRACE).append(NEW_LINE);

        //Converter vo = (Converter)populateValueObject(rs, Converter.class, null);
        method.append(TAB).append(TAB).append(TAB).append(TAB).append(VO_JAVA_CLASS_NAME).append(SPACE)
                .append(DAO_MEMBER_NAME_VO).append(SPACE).append(EQUAL).append(SPACE)
                .append(OPEN_BRACKET).append(VO_JAVA_CLASS_NAME).append(CLOSE_BRACKET)
                .append(DAO_METHOD_NAME_POPULATEVALUEOBJECT).append(OPEN_BRACKET)
                .append(DAO_MEMBER_NAME_RS).append(COMMA).append(SPACE)
                .append(VO_JAVA_CLASS_NAME).append(PERIOD).append(JAVA_KEYWORD_CLASS)
                //.append(COMMA).append(SPACE).append(NULL)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //list.add(vo);
        method.append(TAB).append(TAB).append(TAB).append(TAB).append(DAO_MEMBER_NAME_LIST).append(PERIOD)
                .append(DAO_METHOD_NAME_ADD).append(OPEN_BRACKET).append(DAO_MEMBER_NAME_VO)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        method.append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

        // return list;
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_RETURN)
                .append(SPACE).append(DAO_MEMBER_NAME_LIST).append(SEMICOLON).append(NEW_LINE);

        //} catch (SQLException e) {
        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH)
                .append(SPACE).append(OPEN_BRACKET).append(JAVA_LIB_SQL_SQLEXCEPTION).append(SPACE)
                .append(DAO_MEMBER_NAME_E).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //throw new DAOSysException(e.getMessage());
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE)
                .append(JAVA_KEYWORD_NEW).append(SPACE).append(JAVA_DAO_SYS_EXCEPTION)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_E)
                .append(PERIOD).append(DAO_METHOD_NAME_GETMESSAGE).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
        //} catch (ParseException e) {
        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH)
                .append(SPACE).append(OPEN_BRACKET).append(JAVA_TEXT_PARSEEXCEPTION).append(SPACE)
                .append(DAO_MEMBER_NAME_E).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);
        //throw new DAOSysException(e.getMessage());
        method.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE)
                .append(JAVA_KEYWORD_NEW).append(SPACE).append(JAVA_DAO_SYS_EXCEPTION)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_E)
                .append(PERIOD).append(DAO_METHOD_NAME_GETMESSAGE).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
        //} finally {
        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_FINALLY)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //finalize(rs, ps);
        method.append(TAB).append(TAB).append(TAB).append(DAO_METHOD_NAME_FINALIZE)
                .append(OPEN_BRACKET).append(DAO_MEMBER_NAME_RS).append(COMMA).append(SPACE)
                .append(DAO_MEMBER_NAME_PS).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        method.append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        method.append(TAB).append(CLOSE_BRACE).append(NEW_LINE);


        method.append(NEW_LINE);
        method.append(filterWhereMethodSB);
        method.append(filterOrderMethodSB);
        method.append(filterSetSQLMethodSB);

        return method;
    }

    private boolean isOrderable(List<SearchPageGeneratorInputBean> inputBeans) {
        Iterator<SearchPageGeneratorInputBean> iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (in.sortable) {
                return true;
            }
        }
        return false;
    }

    private StringBuffer generateFilterWhereMethod(List inputBeans, String filterVOName) {
        StringBuffer sb = new StringBuffer();
        sb.append(NEW_LINE);
        sb.append("	private StringBuffer getFilterWhereClause(")
                .append(filterVOName)
                .append(" filter) {").append(NEW_LINE);
        sb.append("		StringBuffer sb = new StringBuffer();").append(NEW_LINE);

        sb.append("		if (").append(NEW_LINE);

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (in.filterable) {
                if (in.range) {
                    String nameFrom = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_RANGE_SUFFIX_FROM;
                    String nameTo = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_RANGE_SUFFIX_TO;
                    sb.append("			filter.").append(nameFrom).append("!=null ||").append(NEW_LINE);
                    sb.append("			filter.").append(nameTo).append("!=null ");
                    sb.append("||");

                    sb.append(NEW_LINE);
                } else {
                    String name = getJavaFieldNameFromDBFieldName(in.dbFieldName);
                    sb.append("			filter.").append(name).append("!=null ");
                    sb.append("||");
                    sb.append(NEW_LINE);
                }
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append(NEW_LINE);
        sb.append("		) {").append(NEW_LINE);
        sb.append("			sb.append(\" WHERE \");").append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);

        sb.append("		boolean mustAndBeUsed = false;").append(NEW_LINE);

        iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (!in.filterable) continue;
            if (in.javaTypeName.equalsIgnoreCase("String")) {
                sb.append(generateStringFilterWhereClause(in));
            } else if (in.javaTypeName.equalsIgnoreCase("Date")) {
                sb.append(generateDateFilterWhereClause(in));
            } else if (in.javaTypeName.equalsIgnoreCase("Long") ||
                    in.javaTypeName.equalsIgnoreCase("Integer") ||
                    in.javaTypeName.equalsIgnoreCase("BigDecimal")) {
                sb.append(generateNumberFilterWhereClause(in));
            }
        }
        sb.append("		return sb;").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        return sb;
    }

    private StringBuffer generateStringFilterWhereClause(SearchPageGeneratorInputBean in) {
        StringBuffer sb = new StringBuffer();
        String name = getJavaFieldNameFromDBFieldName(in.dbFieldName);
        sb.append("		if(filter.")
                .append(name)
                .append(" != null) {").append(NEW_LINE);

        if (in.wildcards) {
            sb.append("			if (mustAndBeUsed) {").append(NEW_LINE);
            sb.append("				sb.append(\" and \");").append(NEW_LINE);
            sb.append("			}").append(NEW_LINE);

            sb.append("			sb.append(\" ")
                    .append(in.dbFieldName)
                    .append(" \");").append(NEW_LINE);
            sb.append("			sb.append(getWildcardWhereClause(filter.")
                    .append(name)
                    .append(", Types.VARCHAR));").append(NEW_LINE);
            sb.append("			mustAndBeUsed = true;").append(NEW_LINE);
        } else {
            sb.append("			if (mustAndBeUsed) {").append(NEW_LINE);
            sb.append("				sb.append(\" and \");").append(NEW_LINE);
            sb.append("			}").append(NEW_LINE);

            sb.append("			sb.append(\" ")
                    .append(in.dbFieldName)
                    .append(" = ?\");").append(NEW_LINE);
            sb.append("			mustAndBeUsed = true;").append(NEW_LINE);
        }
        sb.append("		}").append(NEW_LINE);
        return sb;
    }

    private StringBuffer generateDateFilterWhereClause(SearchPageGeneratorInputBean in) {
        StringBuffer sb = new StringBuffer();

        if (in.range) {
            String nameFrom = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_RANGE_SUFFIX_FROM;
            String nameTo = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_RANGE_SUFFIX_TO;


            sb.append("		if(filter.")
                    .append(nameFrom)
                    .append(" != null) {").append(NEW_LINE);

            sb.append("			if (filter.").append(nameFrom).append("!=null) {").append(NEW_LINE);
            sb.append("				// 2001-02-16  - dates in PSQL").append(NEW_LINE);
            sb.append("				String fromDateStr = AppConstants.PGSQL_DATE.format(filter.").append(nameFrom).append(");").append(NEW_LINE);
            sb.append("				if (mustAndBeUsed) {").append(NEW_LINE);
            sb.append("					sb.append(\" and \");").append(NEW_LINE);
            sb.append("				}").append(NEW_LINE);
            sb.append("				sb.append(\" ").append(in.dbFieldName).append(">'\");").append(NEW_LINE);
            sb.append("				sb.append(fromDateStr);").append(NEW_LINE);
            sb.append("				sb.append(\"' \");").append(NEW_LINE);
            sb.append("				mustAndBeUsed = true;").append(NEW_LINE);
            sb.append("			}").append(NEW_LINE);

            sb.append("		}").append(NEW_LINE);


            sb.append("		if(filter.")
                    .append(nameTo)
                    .append(" != null) {").append(NEW_LINE);

            sb.append("			if (filter.").append(nameTo).append("!=null) {").append(NEW_LINE);
            sb.append("				Calendar cal = Calendar.getInstance();").append(NEW_LINE);
            sb.append("				cal.setTime(filter.").append(nameTo).append(");").append(NEW_LINE);
            sb.append("				cal.add(Calendar.DATE, 1);").append(NEW_LINE);
            sb.append("				Date toDate = cal.getTime();").append(NEW_LINE);
            sb.append("				String toDateStr = AppConstants.PGSQL_DATE.format(toDate);").append(NEW_LINE);
            sb.append("				if (mustAndBeUsed) {").append(NEW_LINE);
            sb.append("					sb.append(\" and \");").append(NEW_LINE);
            sb.append("				}").append(NEW_LINE);
            sb.append("				sb.append(\" ").append(in.dbFieldName).append("<'\");").append(NEW_LINE);
            sb.append("				sb.append(toDateStr);").append(NEW_LINE);
            sb.append("				sb.append(\"' \");").append(NEW_LINE);
            sb.append("				mustAndBeUsed = true;").append(NEW_LINE);
            sb.append("			}").append(NEW_LINE);

            sb.append("		}").append(NEW_LINE);
        } else {
            String name = getJavaFieldNameFromDBFieldName(in.dbFieldName);

            sb.append("		if(filter.")
                    .append(name)
                    .append(" != null) {").append(NEW_LINE);

            sb.append("		}").append(NEW_LINE);
        }

        return sb;
    }

    private StringBuffer generateNumberFilterWhereClause(SearchPageGeneratorInputBean in) {
        StringBuffer sb = new StringBuffer();

        if (in.range) {
            String nameFrom = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_RANGE_SUFFIX_FROM;
            String nameTo = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_RANGE_SUFFIX_TO;


            sb.append("		if(filter.")
                    .append(nameFrom)
                    .append(" != null) {").append(NEW_LINE);

            sb.append("			if (filter.").append(nameFrom).append("!=null) {").append(NEW_LINE);
            sb.append("				if (mustAndBeUsed) {").append(NEW_LINE);
            sb.append("					sb.append(\" and \");").append(NEW_LINE);
            sb.append("				}").append(NEW_LINE);
            sb.append("				sb.append(\" ").append(in.dbFieldName).append(">=? \");").append(NEW_LINE);
            sb.append("				mustAndBeUsed = true;").append(NEW_LINE);
            sb.append("			}").append(NEW_LINE);

            sb.append("		}").append(NEW_LINE);


            sb.append("		if(filter.")
                    .append(nameTo)
                    .append(" != null) {").append(NEW_LINE);

            sb.append("			if (filter.").append(nameTo).append("!=null) {").append(NEW_LINE);
            sb.append("				if (mustAndBeUsed) {").append(NEW_LINE);
            sb.append("					sb.append(\" and \");").append(NEW_LINE);
            sb.append("				}").append(NEW_LINE);
            sb.append("				sb.append(\" ").append(in.dbFieldName).append("<=? \");").append(NEW_LINE);
            sb.append("				mustAndBeUsed = true;").append(NEW_LINE);
            sb.append("			}").append(NEW_LINE);

            sb.append("		}").append(NEW_LINE);
        } else {
            String name = getJavaFieldNameFromDBFieldName(in.dbFieldName);

            sb.append("		if(filter.")
                    .append(name)
                    .append(" != null) {").append(NEW_LINE);

            sb.append("			if (filter.").append(name).append("!=null) {").append(NEW_LINE);
            sb.append("				if (mustAndBeUsed) {").append(NEW_LINE);
            sb.append("					sb.append(\" and \");").append(NEW_LINE);
            sb.append("				}").append(NEW_LINE);
            sb.append("				sb.append(\" ").append(in.dbFieldName).append("=? \");").append(NEW_LINE);
            sb.append("				mustAndBeUsed = true;").append(NEW_LINE);
            sb.append("			}").append(NEW_LINE);

            sb.append("		}").append(NEW_LINE);
        }

        return sb;
    }


    private StringBuffer generateFilterSetSQLMethod(List inputBeans, String filterVOName) {
        StringBuffer sb = new StringBuffer();
        sb.append("	private void setSQLParameters(PreparedStatement ps, ")
                .append(filterVOName)
                .append(" filter) throws SQLException, ParseException {").append(NEW_LINE);
        sb.append("		int i = 1;").append(NEW_LINE);

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (!in.filterable) continue;
            if (in.javaTypeName.equalsIgnoreCase("String")) {
                sb.append(generateStringFilterSQLParameters(in));
//			} else if (in.javaTypeName.equalsIgnoreCase("Date")) {
//				sb.append(generateDateFilterSQLParameters(in));
            } else if (in.javaTypeName.equalsIgnoreCase("Long") ||
                    in.javaTypeName.equalsIgnoreCase("Integer") ||
                    in.javaTypeName.equalsIgnoreCase("BigDecimal")) {
                sb.append(generateNumberFilterSQLParameters(in));
            }
        }

        sb.append("	}").append(NEW_LINE);
        return sb;
    }

    private StringBuffer generateStringFilterSQLParameters(SearchPageGeneratorInputBean in) {
        StringBuffer sb = new StringBuffer();
        String name = getJavaFieldNameFromDBFieldName(in.dbFieldName);
        sb.append("		if(filter.").append(name).append(" != null) {").append(NEW_LINE);
        if (in.wildcards) {
            sb.append("			i = setWildcardSQLParameter(ps, i, filter.").append(name).append(", Types.VARCHAR);").append(NEW_LINE);
        } else {
            sb.append("			i = setSQLParameter(ps, i, filter.").append(name).append(", Types.VARCHAR);").append(NEW_LINE);
        }
        sb.append("		}").append(NEW_LINE);
        return sb;
    }

    //	private StringBuffer generateDateFilterSQLParameters(SearchPageGeneratorInputBean in) {
//		StringBuffer sb = new StringBuffer();
//		String name = getJavaFieldNameFromDBFieldName(in.dbFieldName);
//		sb.append("		if(filter.").append(name).append(" != null) {").append(NEW_LINE);
//		sb.append("			i = setWildcardSQLParameter(ps, i, filter.").append(name).append(", Types.DATE);").append(NEW_LINE);
//		sb.append("		}").append(NEW_LINE);
//		return sb;
//	}
    private StringBuffer generateNumberFilterSQLParameters(SearchPageGeneratorInputBean in) {
        StringBuffer sb = new StringBuffer();

        if (in.range) {
            String nameFrom = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_RANGE_SUFFIX_FROM;
            String nameTo = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_RANGE_SUFFIX_TO;
            sb.append("		if(filter.").append(nameFrom).append(" != null) {").append(NEW_LINE);
            sb.append("			i = setSQLParameter(ps, i, filter.").append(nameFrom).append(", Types.NUMERIC);").append(NEW_LINE);
            sb.append("		}").append(NEW_LINE);
            sb.append("		if(filter.").append(nameTo).append(" != null) {").append(NEW_LINE);
            sb.append("			i = setSQLParameter(ps, i, filter.").append(nameTo).append(", Types.NUMERIC);").append(NEW_LINE);
            sb.append("		}").append(NEW_LINE);
        } else {
            String name = getJavaFieldNameFromDBFieldName(in.dbFieldName);
            sb.append("		if(filter.").append(name).append(" != null) {").append(NEW_LINE);
            sb.append("			i = setSQLParameter(ps, i, filter.").append(name).append(", Types.NUMERIC);").append(NEW_LINE);
            sb.append("		}").append(NEW_LINE);
        }
        return sb;
    }

    private StringBuffer generateFilterOrderMethod(List inputBeans, String filterVOName) {
        StringBuffer sb = new StringBuffer();
        // TODO Auto-generated method stub
        //getFilterOrderingClause

        sb.append(NEW_LINE);
        sb.append("	private StringBuffer getFilterOrderingClause(")
                .append(filterVOName)
                .append(" filter) {").append(NEW_LINE);

        sb.append("		if (!filter.isOrderingRequired()) {").append(NEW_LINE);
        sb.append("			return new StringBuffer();").append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);

        sb.append("		StringBuffer sb = new StringBuffer();").append(NEW_LINE);
        sb.append("		sb.append(\" order by \");").append(NEW_LINE);
        sb.append("		for(int orderIndex = 0; orderIndex < 20; orderIndex++) {").append(NEW_LINE);

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (in.sortable) {
                String nameOrder = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_SUFFIX_ORDER;
                String nameOrdering = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_SUFFIX_ORDERING;
                sb.append("			if(filter.").append(nameOrder).append(" != null && filter.").append(nameOrdering).append(" == orderIndex) {").append(NEW_LINE);
                sb.append("				sb.append(\" ").append(in.dbFieldName).append(" \");").append(NEW_LINE);
                sb.append("				sb.append(filter." + nameOrder + ");").append(NEW_LINE);
                sb.append("				sb.append(\",\");").append(NEW_LINE);
                sb.append("			}").append(NEW_LINE);

            }
        }
        sb.append("		}").append(NEW_LINE);
        sb.append("		if (sb.length()>0 && sb.charAt(sb.length()-1)==',') {").append(NEW_LINE);
        sb.append("			sb.deleteCharAt(sb.length()-1);").append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);
        sb.append("		return sb;").append(NEW_LINE);

        sb.append("	}").append(NEW_LINE);
        return sb;
    }
}