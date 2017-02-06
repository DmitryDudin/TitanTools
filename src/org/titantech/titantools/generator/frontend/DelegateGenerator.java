package org.titantech.titantools.generator.frontend;

import java.util.Iterator;
import java.util.List;

import org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean;
import org.titantech.titantools.dao.bean.VOFieldToColumnMappingDetails;
import org.titantech.titantools.generator.GeneratorBase;

public class DelegateGenerator extends GeneratorBase {

    public String generateDelegateClass(String delegateName, String daoVOName, String daoInterfaceName, String filterVOName, List<VOFieldToColumnMappingDetails> columns) {
        StringBuffer contents = new StringBuffer();
        contents.append(generateClassFileHeader(delegateName, daoVOName, daoInterfaceName, filterVOName));
        contents.append(generateFields(delegateName));


        contents.append(generateGetListMethod(daoVOName, filterVOName, daoInterfaceName));
        contents.append(generateGetSingleRecordMethod(daoVOName, filterVOName, daoInterfaceName, columns));
        contents.append(generateAddSingleRecordMethod(daoVOName, filterVOName, daoInterfaceName));
        contents.append(generateUpdateSingleRecordMethod(daoVOName, filterVOName, daoInterfaceName));

        contents.append(generateStrutsFormConstantMethods(filterVOName));
        contents.append(generateClassFooter());
        String str = contents.toString();
        System.out.println(str);
        return str;
    }


    private StringBuffer generateClassFileHeader(String delegateName, String daoVOName, String daoInterfaceName, String filterVOName) {
        StringBuffer header = new StringBuffer();
        header.append(JAVA_KEYWORD_PACKAGE).append(SPACE).append(
                DELEGATE_JAVA_PACKAGE_NAME).append(SEMICOLON).append(DBL_NEW_LINE);
        Iterator iter = DELEGATE_CLASS_IMPORTS.iterator();
        while (iter.hasNext()) {
            String imprt = (String) iter.next();
            header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(imprt)
                    .append(SEMICOLON).append(NEW_LINE);
        }
        header.append(NEW_LINE);
        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(PACKAGE_NAME_PREFIX).append(PERIOD).append(APP_CONSTANTS).append(SEMICOLON).append(NEW_LINE);
        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(PACKAGE_NAME_PREFIX).append(PERIOD).append(APP_EXCEPTION).append(SEMICOLON).append(NEW_LINE);

        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(DELEGATE_JAVA_PACKAGE_NAME).append(PERIOD).append(delegateName).append(SEMICOLON).append(NEW_LINE);
        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(DAO_JAVA_PACKAGE_NAME).append(PERIOD).append(daoInterfaceName).append(SEMICOLON).append(NEW_LINE);
        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(FILTER_BEAN_JAVA_PACKAGE_NAME).append(PERIOD).append(filterVOName).append(SEMICOLON).append(NEW_LINE);
        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(VO_JAVA_PACKAGE_NAME).append(PERIOD).append(daoVOName).append(SEMICOLON).append(NEW_LINE);

        header.append(NEW_LINE);
        header.append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(
                JAVA_KEYWORD_CLASS).append(SPACE).append(delegateName)
                .append(SPACE);

        header.append(SPACE).append(OPEN_BRACE).append(DBL_NEW_LINE);

        return header;
    }

    private StringBuffer generateFields(String delegateName) {
        StringBuffer sb = new StringBuffer();

        sb.append(TAB).append(JAVA_KEYWORD_PRIVATE).append(SPACE)
                .append(JAVA_KEYWORD_STATIC).append(SPACE).append(JAVA_LIB_CLASS).append(SPACE)
                .append(FIELD_NAME_CLAZZ).append(SPACE).append(EQUAL).append(SPACE)
                .append(delegateName).append(PERIOD).append(JAVA_KEYWORD_CLASS)
                .append(SEMICOLON).append(NEW_LINE);

        sb.append(TAB).append(JAVA_KEYWORD_PRIVATE).append(SPACE)
                .append(JAVA_KEYWORD_STATIC).append(SPACE).append(APACHE_CLASS_LOGGER).append(SPACE)
                .append(FIELD_NAME_LOGGER).append(SPACE).append(EQUAL).append(SPACE)
                .append(APACHE_CLASS_LOGGER).append(PERIOD).append(METHOD_NAME_GETLOGGER)
                .append(OPEN_BRACKET).append(FIELD_NAME_CLAZZ).append(CLOSE_BRACKET)
                .append(SEMICOLON).append(NEW_LINE).append(NEW_LINE);

        return sb;
    }


    private StringBuffer generateGetListMethod(String daoVOName, String filterVOName, String daoInterfaceName) {
        StringBuffer sb = new StringBuffer();
        //public List getLabelTypes(LabelTypeFilter filter, String ipAddress, String userName, Long userId) throws TCAppException {

        String methodName = GET_PREFIX + daoVOName + PLURAL_SUFFIX;

        sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(JAVA_LIB_UTIL_LIST).append(SPACE)
                .append(methodName).append(OPEN_BRACKET).append(filterVOName)
                .append(SPACE).append(FIELD_NAME_FILTER).append(", String ipAddress, String userName, Long userId");
        sb.append(CLOSE_BRACKET).append(SPACE).append(JAVA_KEYWORD_THROWS)
                .append(SPACE).append(APP_EXCEPTION).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //ReportLog log = null;
        //if (userId!=null) {
        //	log = createReportLogVO(filter, ipAddress, userName, userId, "getFixedDiscountRateSchedules");
        //}
        //long startTime = System.currentTimeMillis();
        sb.append(TAB).append(TAB).append("ReportLog log = null;").append(NEW_LINE);
        sb.append(TAB).append(TAB).append("if (userId!=null) {").append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append("log = createReportLogVO(filter, ipAddress, userName, userId, \"");
        sb.append(methodName).append("\");").append(NEW_LINE);
        sb.append(TAB).append(TAB).append("}").append(NEW_LINE);
        sb.append(TAB).append(TAB).append("long startTime = System.currentTimeMillis();").append(NEW_LINE);

        //DAOFactory daoFactory = getDAOFactory();
        sb.append(TAB).append(TAB).append("DAOFactory").append(SPACE).append("daoFactory").append(SPACE).append(EQUAL)
                .append(SPACE).append("getDAOFactory").append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
        //try {
        sb.append(TAB).append(TAB).append(JAVA_KEYWORD_TRY).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);
        //LabelTypeDAO dao = daoFactory.getLabelTypeDAO();
        sb.append(TAB).append(TAB).append(TAB).append(daoInterfaceName).append(SPACE).append("dao").append(SPACE)
                .append(EQUAL).append(SPACE).append("daoFactory").append(PERIOD).append(GET_PREFIX).append(daoInterfaceName)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
        //return dao.getLabelType(filter);
        //sb.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_RETURN).append(SPACE).append("dao").append(PERIOD)
        //	.append(GET_PREFIX).append(daoVOName).append(OPEN_BRACKET).append("filter").append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //List result = dao.getLabelType(filter);
        sb.append(TAB).append(TAB).append(TAB).append("List result=").append("dao").append(PERIOD)
                .append(GET_PREFIX).append(daoVOName).append(OPEN_BRACKET).append("filter").append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //if (userId!=null) {
        //	addReportLog(startTime, log, "getFixedDiscountRateSchedules");
        //}
        sb.append(TAB).append(TAB).append(TAB).append("if (userId!=null) {").append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append(TAB).append("addReportLog(startTime, log, \"");
        sb.append(methodName).append("\");").append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append("}").append(NEW_LINE);
        //return result;
        sb.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_RETURN).append(SPACE).append("result;").append(NEW_LINE);


        //} catch (Exception e) {
        sb.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH).append(SPACE).append(OPEN_BRACKET)
                .append("Exception").append(SPACE).append("e").append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);
        //logger.error("Exception in getLabelTypes: " + e.toString());
        sb.append(TAB).append(TAB).append(TAB).append("logger").append(PERIOD).append("error").append(OPEN_BRACKET).append(DOUBLE_QUOTE)
                .append("Exception in ").append(GET_PREFIX).append(daoVOName).append(PLURAL_SUFFIX).append(COLON).append(SPACE).append("\" + e.toString());").append(NEW_LINE);

        //if (userId!=null) {
        //	addReportErrorLog(startTime, log, e, "getFixedDiscountRateSchedules");
        //}
        sb.append(TAB).append(TAB).append("if (userId!=null) {").append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append("addReportErrorLog(startTime, log, e, \"");
        sb.append(methodName).append("\");").append(NEW_LINE);
        sb.append(TAB).append(TAB).append("}").append(NEW_LINE);

        //throw new TCAppException(e.getMessage());
        sb.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE).append(JAVA_KEYWORD_NEW).append(SPACE).append(APP_EXCEPTION)
                .append(OPEN_BRACKET).append("e.getMessage());").append(NEW_LINE);

        //} finally {
        sb.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_FINALLY).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        sb.append(TAB).append(TAB).append(TAB).append("finalizeDAOFactory").append(OPEN_BRACKET).append("daoFactory").append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
        sb.append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        sb.append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

        return sb;
    }

    private StringBuffer generateGetSingleRecordMethod(String daoVOName, String filterVOName, String daoInterfaceName, List<VOFieldToColumnMappingDetails> columns) {
        StringBuffer sb = new StringBuffer();

        //public LabelType getLabelType(String vo) throws TCAppException {
        sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(daoVOName).append(SPACE)
                .append(GET_PREFIX).append(daoVOName).append(OPEN_BRACKET);

//			.append(JAVA_LIB_STRING)
//			.append(SPACE).append("vo")

        Iterator iter = columns.iterator();
        while (iter.hasNext()) {
            VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter.next();
            if (detail.isPK) {
                String typeName = getTypeName(detail);
                typeName = getShortJavaTypeNameBeforeFirstPeriod(typeName);
                sb.append(typeName).append(SPACE).append(detail.javaFieldName).append(COMMA);
            }
        }
        sb.deleteCharAt(sb.length() - 1);

        sb.append(CLOSE_BRACKET).append(SPACE).append(JAVA_KEYWORD_THROWS)
                .append(SPACE).append(APP_EXCEPTION).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);
        //DAOFactory daoFactory = getDAOFactory();
        sb.append(TAB).append(TAB).append("DAOFactory").append(SPACE).append("daoFactory").append(SPACE).append(EQUAL)
                .append(SPACE).append("getDAOFactory").append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
        //try {
        sb.append(TAB).append(TAB).append(JAVA_KEYWORD_TRY).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);
        //LabelTypeDAO dao = daoFactory.getLabelTypeDAO();
        sb.append(TAB).append(TAB).append(TAB).append(daoInterfaceName).append(SPACE).append("dao").append(SPACE)
                .append(EQUAL).append(SPACE).append("daoFactory").append(PERIOD).append(GET_PREFIX).append(daoInterfaceName)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //return dao.getLabelType(vo);
        sb.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_RETURN).append(SPACE).append("dao").append(PERIOD)
                .append(GET_PREFIX).append(daoVOName).append(OPEN_BRACKET);

        iter = columns.iterator();
        while (iter.hasNext()) {
            VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter.next();
            if (detail.isPK) {
                sb.append(SPACE).append(detail.javaFieldName).append(COMMA);
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //} catch (Exception e) {
        sb.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH).append(SPACE).append(OPEN_BRACKET)
                .append("Exception").append(SPACE).append("e").append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);
        //logger.error("Exception in getLabelType: " + e.toString());
        sb.append(TAB).append(TAB).append(TAB).append("logger").append(PERIOD).append("error").append(OPEN_BRACKET).append(DOUBLE_QUOTE)
                .append("Exception in ").append(GET_PREFIX).append(daoVOName).append(PLURAL_SUFFIX).append(COLON).append(SPACE).append("\" + e.toString());").append(NEW_LINE);

        //throw new TCAppException(e.getMessage());
        sb.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE).append(JAVA_KEYWORD_NEW).append(SPACE).append(APP_EXCEPTION)
                .append(OPEN_BRACKET).append("e.getMessage());").append(NEW_LINE);

        //} finally {
        sb.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_FINALLY).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        sb.append(TAB).append(TAB).append(TAB).append("finalizeDAOFactory").append(OPEN_BRACKET).append("daoFactory").append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
        sb.append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        sb.append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

        return sb;
    }

    private StringBuffer generateAddSingleRecordMethod(String daoVOName, String filterVOName, String daoInterfaceName) {
        String methodName = "add" + daoVOName;
        StringBuffer sb = new StringBuffer();
        //public void addLabelType(LabelType vo, String ipAddress, String userName, Long userId) throws TCAppException {
        sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(JAVA_KEYWORD_VOID).append(SPACE)
                .append(methodName).append(OPEN_BRACKET).append(daoVOName)
                .append(SPACE).append("vo, String ipAddress, String userName, Long userId");
        sb.append(CLOSE_BRACKET).append(SPACE).append(JAVA_KEYWORD_THROWS)
                .append(SPACE).append(APP_EXCEPTION).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //ReportLog log = null;
        //if (userId!=null) {
        //	log = createReportLogVO(ipAddress, userName, userId, "getFixedDiscountRateSchedules");
        //}
        //long startTime = System.currentTimeMillis();
        sb.append(TAB).append(TAB).append("ReportLog log = null;").append(NEW_LINE);
        sb.append(TAB).append(TAB).append("if (userId!=null) {").append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append("log = createReportLogVO(ipAddress, userName, userId, \"");
        sb.append(methodName).append("\");").append(NEW_LINE);
        sb.append(TAB).append(TAB).append("}").append(NEW_LINE);
        sb.append(TAB).append(TAB).append("long startTime = System.currentTimeMillis();").append(NEW_LINE);


        //DAOFactory daoFactory = getDAOFactory();
        sb.append(TAB).append(TAB).append("DAOFactory").append(SPACE).append("daoFactory").append(SPACE).append(EQUAL)
                .append(SPACE).append("getDAOFactory").append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
        //try {
        sb.append(TAB).append(TAB).append(JAVA_KEYWORD_TRY).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //		if (type == null) {
        sb.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(SPACE).append(OPEN_BRACKET).append("vo").append(SPACE)
                .append(LOGIC_EQUAL).append(SPACE).append(NULL)
                .append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //			throw new TCAppException("addLabelType: Label type is empty.");
        sb.append(TAB).append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE).append(JAVA_KEYWORD_NEW).append(SPACE).append(APP_EXCEPTION)
                .append(OPEN_BRACKET).append(DOUBLE_QUOTE).append("add").append(daoVOName).append(COLON).append(" vo is empty.\");").append(NEW_LINE);
        //		}
        sb.append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

        //LabelTypeDAO dao = daoFactory.getLabelTypeDAO();
        sb.append(TAB).append(TAB).append(TAB).append(daoInterfaceName).append(SPACE).append("dao").append(SPACE)
                .append(EQUAL).append(SPACE).append("daoFactory").append(PERIOD).append(GET_PREFIX).append(daoInterfaceName)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //daoFactory.startTransaction();
        sb.append("			daoFactory.startTransaction();").append(NEW_LINE);
        //dao.addLabelType(type);
        sb.append("			dao.add");
        sb.append(daoVOName);
        sb.append("(vo);").append(NEW_LINE);
        //daoFactory.commitTransaction();
        sb.append("			daoFactory.commitTransaction();").append(NEW_LINE);


        //if (userId!=null) {
        //	log.setParam1("scheduleId: " + scheduleId);
        //	log.setParam2("create");
        //	addReportLog(startTime, log, "addFixedDiscountRateSchedule");
        //}
        sb.append(TAB).append(TAB).append("if (userId!=null) {").append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append("log.setParam1(\"pk ID: \" + pk ID);").append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append("log.setParam2(\"create\");").append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append("addReportLog(startTime, log, \"");
        sb.append(methodName).append("\");").append(NEW_LINE);
        sb.append(TAB).append(TAB).append("}").append(NEW_LINE);


        //} catch (Exception e) {
        sb.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH).append(SPACE).append(OPEN_BRACKET)
                .append("Exception").append(SPACE).append("e").append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);
        //logger.error("Exception in addLabelType: " + e.toString());
        sb.append(TAB).append(TAB).append(TAB).append("logger").append(PERIOD).append("error").append(OPEN_BRACKET).append(DOUBLE_QUOTE)
                .append("Exception in add").append(daoVOName).append(COLON).append(SPACE).append("\" + e.toString());").append(NEW_LINE);
        //rollbackDAOTransaction(daoFactory);
        sb.append(TAB).append(TAB).append(TAB).append("rollbackDAOTransaction").append(OPEN_BRACKET).append("daoFactory")
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //if (userId!=null) {
        //	addReportErrorLog(startTime, log, e, "getFixedDiscountRateSchedules");
        //}
        sb.append(TAB).append(TAB).append("if (userId!=null) {").append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append("addReportErrorLog(startTime, log, e, \"");
        sb.append(methodName).append("\");").append(NEW_LINE);
        sb.append(TAB).append(TAB).append("}").append(NEW_LINE);

        //throw new TCAppException(e.getMessage());
        sb.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE).append(JAVA_KEYWORD_NEW).append(SPACE).append(APP_EXCEPTION)
                .append(OPEN_BRACKET).append("e.getMessage());").append(NEW_LINE);

        //} finally {
        sb.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_FINALLY).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append("finalizeDAOFactory").append(OPEN_BRACKET).append("daoFactory").append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
        sb.append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        sb.append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

        return sb;
    }

    private StringBuffer generateUpdateSingleRecordMethod(String daoVOName, String filterVOName, String daoInterfaceName) {
        String methodName = "update" + daoVOName;
        StringBuffer sb = new StringBuffer();
        //public void updateLabelType(LabelType vo, String ipAddress, String userName, Long userId) throws TCAppException {
        sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(JAVA_KEYWORD_VOID).append(SPACE)
                .append(methodName).append(OPEN_BRACKET).append(daoVOName)
                .append(SPACE).append("vo, String ipAddress, String userName, Long userId").append(CLOSE_BRACKET).append(SPACE).append(JAVA_KEYWORD_THROWS)
                .append(SPACE).append(APP_EXCEPTION).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //ReportLog log = null;
        //if (userId!=null) {
        //	log = createReportLogVO(ipAddress, userName, userId, "getFixedDiscountRateSchedules");
        //}
        //long startTime = System.currentTimeMillis();
        sb.append(TAB).append(TAB).append("ReportLog log = null;").append(NEW_LINE);
        sb.append(TAB).append(TAB).append("if (userId!=null) {").append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append("log = createReportLogVO(ipAddress, userName, userId, \"");
        sb.append(methodName).append("\");").append(NEW_LINE);
        sb.append(TAB).append(TAB).append("}").append(NEW_LINE);
        sb.append(TAB).append(TAB).append("long startTime = System.currentTimeMillis();").append(NEW_LINE);

        //DAOFactory daoFactory = getDAOFactory();
        sb.append(TAB).append(TAB).append("DAOFactory").append(SPACE).append("daoFactory").append(SPACE).append(EQUAL)
                .append(SPACE).append("getDAOFactory").append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
        //try {
        sb.append(TAB).append(TAB).append(JAVA_KEYWORD_TRY).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //		if (type == null) {
        sb.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(SPACE).append(OPEN_BRACKET).append("vo").append(SPACE)
                .append(LOGIC_EQUAL).append(SPACE).append(NULL)
                .append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        //			throw new TCAppException("updateLabelType: Label type is empty.");
        sb.append(TAB).append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE).append(JAVA_KEYWORD_NEW).append(SPACE).append(APP_EXCEPTION)
                .append(OPEN_BRACKET).append(DOUBLE_QUOTE).append("update").append(daoVOName).append(COLON).append(" vo is empty.\");").append(NEW_LINE);
        //		}
        sb.append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

        //LabelTypeDAO dao = daoFactory.getLabelTypeDAO();
        sb.append(TAB).append(TAB).append(TAB).append(daoInterfaceName).append(SPACE).append("dao").append(SPACE)
                .append(EQUAL).append(SPACE).append("daoFactory").append(PERIOD).append(GET_PREFIX).append(daoInterfaceName)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //daoFactory.startTransaction();
        sb.append("			daoFactory.startTransaction();").append(NEW_LINE);
        //dao.updateLabelType(type);
        sb.append("			dao.update");
        sb.append(daoVOName);
        sb.append("(vo);").append(NEW_LINE);
        //daoFactory.commitTransaction();
        sb.append("			daoFactory.commitTransaction();").append(NEW_LINE);


        //if (userId!=null) {
        //	log.setParam1("scheduleId: " + scheduleId);
        //	log.setParam2("update");
        //	addReportLog(startTime, log, "addFixedDiscountRateSchedule");
        //}
        sb.append(TAB).append(TAB).append("if (userId!=null) {").append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append("log.setParam1(\"pk ID: \" + pk ID);").append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append("log.setParam2(\"update\");").append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append("addReportLog(startTime, log, \"");
        sb.append(methodName).append("\");").append(NEW_LINE);
        sb.append(TAB).append(TAB).append("}").append(NEW_LINE);


        //} catch (Exception e) {
        sb.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH).append(SPACE).append(OPEN_BRACKET)
                .append("Exception").append(SPACE).append("e").append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);
        //logger.error("Exception in addLabelType: " + e.toString());
        sb.append(TAB).append(TAB).append(TAB).append("logger").append(PERIOD).append("error").append(OPEN_BRACKET).append(DOUBLE_QUOTE)
                .append("Exception in update").append(daoVOName).append(COLON).append(SPACE).append("\" + e.toString());").append(NEW_LINE);
        //rollbackDAOTransaction(daoFactory);
        sb.append(TAB).append(TAB).append(TAB).append("rollbackDAOTransaction").append(OPEN_BRACKET).append("daoFactory")
                .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        //if (userId!=null) {
        //	addReportErrorLog(startTime, log, e, "getFixedDiscountRateSchedules");
        //}
        sb.append(TAB).append(TAB).append("if (userId!=null) {").append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append("addReportErrorLog(startTime, log, e, \"");
        sb.append(methodName).append("\");").append(NEW_LINE);
        sb.append(TAB).append(TAB).append("}").append(NEW_LINE);

        //throw new TCAppException(e.getMessage());
        sb.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE).append(JAVA_KEYWORD_NEW).append(SPACE).append(APP_EXCEPTION)
                .append(OPEN_BRACKET).append("e.getMessage());").append(NEW_LINE);

        //} finally {
        sb.append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_FINALLY).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append("finalizeDAOFactory").append(OPEN_BRACKET).append("daoFactory").append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
        sb.append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        sb.append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

        return sb;
    }


    protected String generateSimpleResetMethod(List inputBeans) {
        StringBuffer sb = new StringBuffer();

        sb.append(TAB).append(JAVA_KEYWORD_PUBLIC)
                .append(SPACE).append(JAVA_KEYWORD_VOID).append(SPACE).append("reset")
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (in.range) {
                sb.append(TAB).append(TAB).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(FILTER_BEAN_RANGE_SUFFIX_FROM)
                        .append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
                        .append(NEW_LINE);
                sb.append(TAB).append(TAB).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(FILTER_BEAN_RANGE_SUFFIX_TO)
                        .append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
                        .append(NEW_LINE);
            } else {
                sb.append(TAB).append(TAB).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
                        .append(NEW_LINE);
            }
        }
        sb.append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        return sb.toString();
    }

    protected String generateClassFooter() {
        return CLOSE_BRACE;
    }

    protected StringBuffer generateExecuteMethod(String actionName, String formClassName,
                                                 String delegateName, String daoVOName, String manageRecordForwardActionName, String resourcePermissionName) {
        StringBuffer sb = new StringBuffer();
        sb.append("	public ActionForward execute(ActionMapping actionMapping,").append(NEW_LINE);
        sb.append("			ActionForm actionForm, HttpServletRequest servletRequest,").append(NEW_LINE);
        sb.append("			HttpServletResponse servletResponse) {").append(NEW_LINE);

        sb.append("		// Security Check Starts").append(NEW_LINE);
        sb.append("		HttpSession session = servletRequest.getSession(false);").append(NEW_LINE);
        sb.append("		PermissionValidator pv = (PermissionValidator) session").append(NEW_LINE);
        sb.append("				.getAttribute(PermissionValidator.PERMISSION_VALIDATOR_CONSTANT);").append(NEW_LINE);
        sb.append("		if (!(pv!=null && pv.canRead(PermissionValidator.");
        sb.append(resourcePermissionName);
        sb.append("))) {").append(NEW_LINE);
        sb.append("			return actionMapping.findForward(\"accessDenied\");").append(NEW_LINE);
        sb.append("		} // Security Check Ends").append(NEW_LINE);
        sb.append("		String clientIp = servletRequest.getRemoteAddr();").append(NEW_LINE);
        sb.append("		String remoteUser = pv.getUser().getLoginName();").append(NEW_LINE);
        sb.append("		");
        sb.append(formClassName);
        sb.append(" form = (");
        sb.append(formClassName);
        sb.append(") actionForm;").append(NEW_LINE);
        sb.append("		boolean actionTokenValid = isActionTokenValid(form.getActionToken(), session);").append(NEW_LINE);
        sb.append("		form.setActionToken(createAndSetNextValidActionToken(session));").append(NEW_LINE);
        sb.append("		String an = servletRequest.getParameter(ActionConstants.ACTION_NAME);").append(NEW_LINE);
        sb.append("		ActionErrors actionErrors = form.validate(actionMapping, servletRequest);").append(NEW_LINE);
        sb.append("		if(!actionErrors.isEmpty()) {").append(NEW_LINE);
        sb.append("			this.saveErrors(servletRequest, actionErrors);").append(NEW_LINE);
        sb.append("			return actionMapping.findForward(\"business-error\");").append(NEW_LINE);
        sb.append("		} else {").append(NEW_LINE);

        sb.append("			try {").append(NEW_LINE);
        sb.append("				if (ActionConstants.ACTION_NEW.equals(an)) {").append(NEW_LINE);
        sb.append("					// do almost nothing.").append(NEW_LINE);
        sb.append("					form.reset();").append(NEW_LINE);
        sb.append("				}").append(NEW_LINE);
        sb.append("				if (ActionConstants.ACTION_OPEN.equals(an)) {").append(NEW_LINE);
        sb.append("					// do almost nothing.").append(NEW_LINE);
        sb.append("					form.setActionName(ActionConstants.ACTION_RETRIEVE);").append(NEW_LINE);
        sb.append("				}").append(NEW_LINE);
        sb.append("				if (ActionConstants.ACTION_RETRIEVE.equals(form.getActionName())) {").append(NEW_LINE);
        sb.append("					");
        sb.append(delegateName);
        sb.append(" delegate = new ");
        sb.append(delegateName);
        sb.append("();").append(NEW_LINE);
        sb.append("					try {").append(NEW_LINE);
        sb.append("						");
        sb.append(daoVOName);
        sb.append(" vo = delegate.get");
        sb.append(daoVOName);
        sb.append("(typeName);").append(NEW_LINE);
        sb.append("						if (vo==null) {").append(NEW_LINE);
        sb.append("							logger.error(\"cannot retrieve vo, error: \" + typeName);").append(NEW_LINE);
        sb.append("							return actionMapping.findForward(\"error\");").append(NEW_LINE);
        sb.append("						}").append(NEW_LINE);
        sb.append("						form.mapToForm(vo);").append(NEW_LINE);
        sb.append("					} catch (NullPointerException e) {").append(NEW_LINE);
        sb.append("						logger.error(\"execute delegate: error: \" + e.getMessage());").append(NEW_LINE);
        sb.append("						return actionMapping.findForward(\"error\");").append(NEW_LINE);
        sb.append("					}").append(NEW_LINE);
        sb.append("				}").append(NEW_LINE);
        sb.append("				if (actionTokenValid) {").append(NEW_LINE);
        sb.append("					if (ActionConstants.ACTION_SAVE_AND_EXIT.equals(form.getActionName()) ||").append(NEW_LINE);
        sb.append("							ActionConstants.ACTION_SAVE_AND_ADD_ANOTHER_NEW.equals(form.getActionName())) {").append(NEW_LINE);
        sb.append("						");
        sb.append(delegateName);
        sb.append(" delegate = new ");
        sb.append(delegateName);
        sb.append("();").append(NEW_LINE);
        sb.append("						try {").append(NEW_LINE);
        sb.append("							");
        sb.append(daoVOName);
        sb.append(" vo = form.get");
        sb.append(daoVOName);
        sb.append("();").append(NEW_LINE);
        sb.append("							");
        sb.append(daoVOName);
        sb.append(" existingVO = delegate.get");
        sb.append(daoVOName);
        sb.append("(vo.getTypeName());").append(NEW_LINE);
        sb.append("							if (existingVO==null) {").append(NEW_LINE);
        sb.append("								delegate.add");
        sb.append(daoVOName);
        sb.append("(vo);").append(NEW_LINE);
        sb.append("							} else {").append(NEW_LINE);
        sb.append("								delegate.update");
        sb.append(daoVOName);
        sb.append("(vo);").append(NEW_LINE);
        sb.append("							}").append(NEW_LINE);
        sb.append("							logger.info(\"User [\" + remoteUser + \" at \" + clientIp + \"] saves vo: [\" + vo.toString() + \"]\");").append(NEW_LINE);
        sb.append("							if (ActionConstants.ACTION_SAVE_AND_EXIT.equals(form.getActionName())) {").append(NEW_LINE);
        sb.append("								form.reset();").append(NEW_LINE);
        sb.append("								return actionMapping.findForward(\"");
        sb.append(manageRecordForwardActionName);
        sb.append("\");").append(NEW_LINE);
        sb.append("							} else if (ActionConstants.ACTION_SAVE_AND_ADD_ANOTHER_NEW.equals(form.getActionName())) {").append(NEW_LINE);
        sb.append("								form.reset();").append(NEW_LINE);
        sb.append("							}").append(NEW_LINE);
        sb.append("						} catch (Exception e) {").append(NEW_LINE);
        sb.append("							logger.error(\"execute delegate: error: \" + e.getMessage());").append(NEW_LINE);
        sb.append("							return actionMapping.findForward(\"error\");").append(NEW_LINE);
        sb.append("						}").append(NEW_LINE);
        sb.append("					}").append(NEW_LINE);
        sb.append("				} else {").append(NEW_LINE);
        sb.append("					// action token is invalid.").append(NEW_LINE);
        sb.append("				}").append(NEW_LINE);
        sb.append("			} catch(TCAppException e) {").append(NEW_LINE);
        sb.append("				logger.error(\"");
        sb.append(actionName);
        sb.append(".execute error: \" + e.getMessage());").append(NEW_LINE);
        sb.append("				ActionErrors errors = new ActionErrors();").append(NEW_LINE);
        sb.append("				errors.add(\"edit");
        sb.append(daoVOName);
        sb.append("\", new ActionError(\"error.edit");
        sb.append(daoVOName);
        sb.append("\", \"error.edit");
        sb.append(daoVOName);
        sb.append("\"));").append(NEW_LINE);
        sb.append("				this.saveErrors(servletRequest, errors);").append(NEW_LINE);
        sb.append("			}").append(NEW_LINE);


        sb.append("		}").append(NEW_LINE);
        sb.append("		return actionMapping.findForward(\"success\");").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        return sb;
    }

    protected StringBuffer generateStrutsFormConstantMethods(String filterClassName) {
        StringBuffer sb = new StringBuffer();

        sb.append("	protected ReportLog createReportLogVO(").append(filterClassName).append(" filter,").append(NEW_LINE);
        sb.append("		String ipAddress, String userName, Long userId, String reportName) {").append(NEW_LINE);
        sb.append("		ReportLog vo = new ReportLog();").append(NEW_LINE);
        sb.append("		vo.setCreateDate(new Date());").append(NEW_LINE);
        sb.append("		vo.setIpAddress(ipAddress);").append(NEW_LINE);
        sb.append("		vo.setUserName(userName);").append(NEW_LINE);
        sb.append("		if (userId!=null) vo.setUserId(userId.toString());").append(NEW_LINE);
        sb.append("		vo.setReportName(reportName);").append(NEW_LINE);
        sb.append("		//if (filter.scheduleId!=null) vo.setParam1(\"scheduleId: \" + filter.scheduleId);").append(NEW_LINE);
        sb.append("		//if (filter.storeId!=null) vo.setParam2(\"storeId: \" + filter.storeId);").append(NEW_LINE);
        sb.append("		//if (filter.active!=null) vo.setParam3(\"active: \" + filter.active);").append(NEW_LINE);
        sb.append("		return vo;").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        sb.append("	protected ReportLog createReportLogVO(String ipAddress, String userName, Long userId, String reportName) {").append(NEW_LINE);
        sb.append("		ReportLog vo = new ReportLog();").append(NEW_LINE);
        sb.append("		vo.setCreateDate(new Date());").append(NEW_LINE);
        sb.append("		vo.setIpAddress(ipAddress);").append(NEW_LINE);
        sb.append("		vo.setUserName(userName);").append(NEW_LINE);
        sb.append("		if (userId!=null) vo.setUserId(userId.toString());").append(NEW_LINE);
        sb.append("		vo.setReportName(reportName);").append(NEW_LINE);
        sb.append("		return vo;").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        sb.append("	protected static void addReportLog(long startTime, ReportLog vo, String originMethodName) {").append(NEW_LINE);
        sb.append("		try {").append(NEW_LINE);
        sb.append("			long endTime = System.currentTimeMillis();").append(NEW_LINE);
        sb.append("			vo.setRunTimeMilliseconds(new Long(endTime-startTime));").append(NEW_LINE);
        sb.append("			vo.setSuccess(new Character('Y'));").append(NEW_LINE);
        sb.append("			AsyncLogDBExecutionHandler h = new AsyncLogDBExecutionHandler(vo, logger);").append(NEW_LINE);
        sb.append("			new Thread(h).start();").append(NEW_LINE);
        sb.append("		} catch (Exception e) {").append(NEW_LINE);
        sb.append("			logger.error(originMethodName + \": logging error: \" + e.getMessage());").append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        sb.append("	protected static void addReportErrorLog(long startTime, ReportLog vo, Exception e, String originMethodName) {").append(NEW_LINE);
        sb.append("		try {").append(NEW_LINE);
        sb.append("			long endTime = System.currentTimeMillis();").append(NEW_LINE);
        sb.append("			vo.setRunTimeMilliseconds(new Long(endTime-startTime));").append(NEW_LINE);
        sb.append("			vo.setSuccess(new Character('N'));").append(NEW_LINE);
        sb.append("			vo.setErrorMsg(e.getMessage());").append(NEW_LINE);
        sb.append("			AsyncLogDBExecutionHandler h = new AsyncLogDBExecutionHandler(vo, logger);").append(NEW_LINE);
        sb.append("			new Thread(h).start();").append(NEW_LINE);
        sb.append("		} catch (Exception e2) {").append(NEW_LINE);
        sb.append("			logger.error(originMethodName + \": logging error 2: \" + e2.getMessage());").append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        sb.append("	protected DAOFactory getDAOFactory() {").append(NEW_LINE);
        sb.append("		return DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        sb.append("	protected void finalizeDAOFactory(DAOFactory daoFactory) throws TCAppException {").append(NEW_LINE);
        sb.append("		if (daoFactory != null) {").append(NEW_LINE);
        sb.append("			try {").append(NEW_LINE);
        sb.append("				daoFactory.releaseConnections();").append(NEW_LINE);
        sb.append("			} catch (Exception e) {").append(NEW_LINE);
        sb.append("				logger.error(e);").append(NEW_LINE);
        sb.append("				throw new TCAppException(e.getMessage());").append(NEW_LINE);
        sb.append("			}").append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        sb.append("	protected void rollbackDAOTransaction(DAOFactory daoFactory) throws TCAppException {").append(NEW_LINE);
        sb.append("		if(daoFactory != null) {").append(NEW_LINE);
        sb.append("			try {").append(NEW_LINE);
        sb.append("				if (daoFactory.isInTransaction()) {").append(NEW_LINE);
        sb.append("					daoFactory.rollbackTransaction();").append(NEW_LINE);
        sb.append("				}").append(NEW_LINE);
        sb.append("			} catch(Exception e) {").append(NEW_LINE);
        sb.append("				logger.error(e);").append(NEW_LINE);
        sb.append("				throw new TCAppException(e.getMessage());").append(NEW_LINE);
        sb.append("			}").append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);

        return sb;
    }

}