package org.titantech.titantools.generator;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.titantech.titantools.dao.bean.VODetails;
import org.titantech.titantools.dao.bean.VOFieldToColumnMappingDetails;

public class GeneratorBase {

    protected static final String DB_SQL_SET = "SET";
    protected static final String DB_SQL_SELECT = "SELECT";
    protected static final String DB_SQL_INSERT = "INSERT";
    protected static final String DB_SQL_INTO = "INTO";
    protected static final String DB_SQL_UPDATE = "UPDATE";
    protected static final String DB_SQL_DELETE = "DELETE";
    protected static final String DB_SQL_VALUES = "VALUES";
    protected static final String DB_SQL_FROM = "FROM";
    protected static final String DB_SQL_WHERE = "WHERE";
    protected static final String DB_SQL_AND = "AND";

    protected static final String JAVA_KEYWORD_PACKAGE = "package";
    protected static final String JAVA_KEYWORD_IMPORT = "import";
    protected static final String JAVA_KEYWORD_PUBLIC = "public";
    protected static final String JAVA_KEYWORD_PRIVATE = "private";
    protected static final String JAVA_KEYWORD_PROTECTED = "protected";
    protected static final String JAVA_KEYWORD_CLASS = "class";
    protected static final String JAVA_KEYWORD_CATCH = "catch";
    protected static final String JAVA_KEYWORD_TRY = "try";
    protected static final String JAVA_KEYWORD_NEW = "new";
    protected static final String JAVA_KEYWORD_WHILE = "while";
    protected static final String JAVA_KEYWORD_IF = "if";
    protected static final String JAVA_KEYWORD_ELSE = "else";
    protected static final String JAVA_KEYWORD_INT = "int";
    protected static final String JAVA_KEYWORD_BOOLEAN = "boolean";
    protected static final String JAVA_KEYWORD_INTERFACE = "interface";
    protected static final String JAVA_KEYWORD_FINALLY = "finally";
    protected static final String JAVA_KEYWORD_STATIC = "static";
    protected static final String JAVA_KEYWORD_VOID = "void";
    protected static final String JAVA_KEYWORD_THIS = "this";
    protected static final String JAVA_KEYWORD_RETURN = "return";
    protected static final String JAVA_KEYWORD_EXTENDS = "extends";
    protected static final String JAVA_KEYWORD_IMPLEMENTS = "implements";
    protected static final String JAVA_KEYWORD_THROWS = "throws";
    protected static final String JAVA_KEYWORD_THROW = "throw";
    protected static final String JAVA_KEYWORD_SWITCH = "switch";
    protected static final String JAVA_KEYWORD_NATIVE = "native";
    protected static final String JAVA_KEYWORD_DEFAULT = "default";
    protected static final String ZERO = "0";
    protected static final String ONE = "1";
    protected static final String TWO = "2";
    protected static final String ASTERISK = "*";
    protected static final String PERIOD = ".";
    protected static final String COMMA = ",";
    protected static final String SEMICOLON = ";";
    protected static final String COLON = ":";
    protected static final String EQUAL = "=";
    protected static final String LOGIC_AND = "&&";
    protected static final String LOGIC_OR = "||";
    protected static final String LOGIC_NOT = "!";
    protected static final String LOGIC_NOTEQUAL = "!=";
    protected static final String LOGIC_EQUAL = "==";
    protected static final String LESS = "<";
    protected static final String GREATER = ">";
    protected static final String AMPERSAND = "&";
    protected static final String QUESTION = "?";
    protected static final String SPACE = " ";
    protected static final String NULL = "null";
    protected static final String DOUBLE_QUOTE = "\"";
    protected static final String TAB = "\t";
    protected static final String FORWARD_SLASH = "/";
    protected static final String OPEN_BRACE = "{";
    protected static final String DB_FIELD_NAME_DELIMITER = "_";
    protected static final String CLOSE_BRACE = "}";
    protected static final String OPEN_BRACKET = "(";
    protected static final String CLOSE_BRACKET = ")";
    protected static final String NEW_LINE = "\n";
    protected static final String PLUS = "+";
    protected static final String MINUS = "-";
    protected static final String DBL_NEW_LINE = "\n\n";
    protected static final String GET_PREFIX = "get";
    protected static final String SET_PREFIX = "set";
    protected static final String ADD_PREFIX = "add";
    protected static final String UPDATE_PREFIX = "update";
    protected static final String DELETE_PREFIX = "delete";
    //protected static final String JAVA_SRC_DIR = "C:/dev/Edge/src";
    //protected static final String JAVA_SRC_DIR = "E:/dev/TitanTools/OUTPUT";

    protected static String DATABASE_TABLE_NAME = "ACL_MATRIX";
    protected static final String JAVA_CLASS_EXTENSION = "java";

    protected static final String JSP_FILE_EXTENSION = "jsp";

    protected static final String JAVA_LIB_STRINGWRITER = "StringWriter";
    protected static final String JAVA_LIB_STRING = "String";
    protected static final String JAVA_LIB_CLASS = "Class";
    protected static final String JAVA_LIB_BYTEARRAYINPUTSTREAM = "ByteArrayInputStream";
    protected static final String JAVA_LIB_OBJECT = "Object";
    protected static final String JAVA_LIB_UTIL_LIST = "List";
    protected static final String JAVA_LIB_UTIL_ARRAYLIST = "ArrayList";
    protected static final String JAVA_LIB_SQL_PREPARED_STATEMENT = "PreparedStatement";
    protected static final String JAVA_LIB_SQL_RESULT_SET = "ResultSet";
    protected static final String JAVA_LIB_SQL_CONNECTION = "Connection";
    protected static final String JAVA_LIB_SQL_SQLEXCEPTION = "SQLException";
    protected static final String JAVA_DAO_SYS_EXCEPTION = "DAOSysException";
    protected static final String JAVA_TEXT_PARSEEXCEPTION = "ParseException";

    protected static final String APACHE_CLASS_LOGGER = "Logger";

    protected static final String DAO_APP_EXCEPTION_CLASS_NAME = "DAOAppException";
    protected static final String PLURAL_SUFFIX = "s";

    protected static final String PG_DAO_FACTORY_CLASS_NAME = "PGDAOFactory";
    protected static final String PG_DAO_FACTORY_MEMBER_NAME = "pgDAOFactory";

    //protected static final String PACKAGE_NAME_PREFIX = "ru.scarlettgroup.titanconsole";
    //protected static final String PACKAGE_NAME_PREFIX = "ru.scarlettgroup.titanconsole";


    //protected static final String PACKAGE_NAME_PREFIX = "ru.scarlettgroup.phobosserver";
    //protected static final String PACKAGE_NAME_PREFIX = "biz.tt.hyperion";

    public static String PACKAGE_NAME_PREFIX = "com.trackensure";
    //public static String JAVA_SRC_DIR = "/home/ /dev/TitanTools/OUTPUT";
    public static String JAVA_SRC_DIR = "/home/kirill/dev/TitanTools/OUTPUT";

    public static String VO_JAVA_PACKAGE_NAME = PACKAGE_NAME_PREFIX;
    public static String VO_JAVA_CLASS_NAME = "ACLMatrix";
    public static String DAO_JAVA_PACKAGE_NAME = PACKAGE_NAME_PREFIX + ".integration.dao";
    public static String DAO_JAVA_INTERFACE_NAME = "ACLMatrixDAO";
    public static String DAO_JAVA_CLASS_NAME = "PGACLMatrixDAO";
    public static String DELEGATE_JAVA_PACKAGE_NAME = PACKAGE_NAME_PREFIX + ".delegate";
    public static String DELEGATE_JAVA_CLASS_NAME = "ReferenceDelegate";
    public static String FILTER_BEAN_JAVA_PACKAGE_NAME = PACKAGE_NAME_PREFIX + ".integration.bean";
    public static String WEB_BEAN_JAVA_PACKAGE_NAME = PACKAGE_NAME_PREFIX + ".web.bean";
    public static String STRUTS_FORM_JAVA_PACKAGE_NAME = PACKAGE_NAME_PREFIX + ".web.form";
    public static String STRUTS_ACTION_JAVA_PACKAGE_NAME = PACKAGE_NAME_PREFIX + ".web.action";
    public static String WEB_TAG_JAVA_PACKAGE_NAME = PACKAGE_NAME_PREFIX + ".web.tag";

    public GeneratorBase() {
        reset();
    }

    public static void reset() {
        VO_JAVA_PACKAGE_NAME = PACKAGE_NAME_PREFIX + ".integration.valueobject";
        DAO_JAVA_PACKAGE_NAME = PACKAGE_NAME_PREFIX + ".integration.dao";
        DELEGATE_JAVA_PACKAGE_NAME = PACKAGE_NAME_PREFIX + ".delegate";
        FILTER_BEAN_JAVA_PACKAGE_NAME = PACKAGE_NAME_PREFIX + ".integration.bean";
        WEB_BEAN_JAVA_PACKAGE_NAME = PACKAGE_NAME_PREFIX + ".web.bean";
        STRUTS_FORM_JAVA_PACKAGE_NAME = PACKAGE_NAME_PREFIX + ".web.form";
        STRUTS_ACTION_JAVA_PACKAGE_NAME = PACKAGE_NAME_PREFIX + ".web.action";
        WEB_TAG_JAVA_PACKAGE_NAME = PACKAGE_NAME_PREFIX + ".web.tag";
    }


    protected static final String APP_CONSTANTS = "AppConstants";
    protected static final String APP_EXCEPTION = "TCAppException";
    protected static final String APP_ACTION_CONSTANTS = "ActionConstants";

    protected static final String FIELD_NAME_FILTER = "filter";
    protected static final String FIELD_NAME_CLAZZ = "CLAZZ";
    protected static final String FIELD_NAME_LOGGER = "logger";
    protected static final String FIELD_NAME_LIST = "list";
    protected static final String FIELD_NAME_ACTION_NAME = "actionName";
    protected static final String FIELD_NAME_ACTION_TOKEN = "actionToken";
    protected static final String FIELD_NAME_TC_WEB_DATE_FORMAT = "TC_WEB_DATE_FORMAT";
    protected static final String FIELD_NAME_TC_WEB_DATE_TIME_FORMAT = "TC_WEB_DATE_TIME_FORMAT";

    // protected static final String JAVA_EXTENDS_CLASS_NAME =
    // "RecordMapperValueObject";
    // protected static final String JAVA_IMPLEMENTS_INTERFACE_NAME =
    // "Serializable";
    // addVOField("BO_ID", Types.INTEGER, "setBuildingOutageId", Integer.class,
    // "getBuildingOutageId");
    protected static final String VO_FIELD_MAPPING_METHOD_NAME = "addVOField";
    protected static List VO_CLASS_IMPORTS = new ArrayList();
    protected static List VO_CLASS_EXTENDS = new ArrayList();
    protected static List VO_CLASS_IMPLEMENTS = new ArrayList();

    protected static List DAO_INTERFACE_IMPORTS = new ArrayList();

    protected static List DAO_CLASS_IMPORTS = new ArrayList();
    protected static List DAO_CLASS_EXTENDS = new ArrayList();

    protected static List DB_PK_COLUMN_LIST = new ArrayList();
    protected static Set DB_PK_COLUMN_SET = new HashSet();

    protected static List FILTER_BEAN_CLASS_IMPORTS = new ArrayList();

    protected static List WEB_BEAN_CLASS_IMPORTS = new ArrayList();
    protected static List WEB_BEAN_CLASS_IMPLEMENTS = new ArrayList();

    protected static List STRUTS_FORM_CLASS_IMPORTS = new ArrayList();
    protected static List STRUTS_FORM_CLASS_EXTENDS = new ArrayList();

    public static List STRUTS_ACTION_CLASS_IMPORTS = new ArrayList();
    protected static List STRUTS_ACTION_CLASS_EXTENDS = new ArrayList();

    protected static List DELEGATE_CLASS_IMPORTS = new ArrayList();

    protected static List XMLHANDLER_CLASS_IMPORTS = new ArrayList();

    protected static Set JAVA_KEYWORD_SET = new HashSet();

    public static void resetImports() {
        STRUTS_ACTION_CLASS_IMPORTS.clear();
        DELEGATE_CLASS_IMPORTS.clear();

        STRUTS_ACTION_CLASS_IMPORTS.add("java.io.UnsupportedEncodingException");
        STRUTS_ACTION_CLASS_IMPORTS.add("javax.servlet.http.HttpServletRequest");
        STRUTS_ACTION_CLASS_IMPORTS.add("javax.servlet.http.HttpServletResponse");
        STRUTS_ACTION_CLASS_IMPORTS.add("javax.servlet.http.HttpSession");
        STRUTS_ACTION_CLASS_IMPORTS.add("org.apache.log4j.Logger");
        STRUTS_ACTION_CLASS_IMPORTS.add("org.apache.struts.action.Action");
//		THIS IS DEPRECATED
        STRUTS_ACTION_CLASS_IMPORTS.add("org.apache.struts.action.ActionMessage");
        STRUTS_ACTION_CLASS_IMPORTS.add("org.apache.struts.action.ActionErrors");
        STRUTS_ACTION_CLASS_IMPORTS.add("org.apache.struts.action.ActionForm");
        STRUTS_ACTION_CLASS_IMPORTS.add("org.apache.struts.action.ActionForward");
        STRUTS_ACTION_CLASS_IMPORTS.add("org.apache.struts.action.ActionMapping");
        //STRUTS_ACTION_CLASS_IMPORTS.add("ru.scarlettgroup.titanconsole.util.PermissionValidator");

        STRUTS_ACTION_CLASS_IMPORTS.add(PACKAGE_NAME_PREFIX + ".util.PermissionValidator");
        //STRUTS_ACTION_CLASS_IMPORTS.add("biz.tt.hyperion.util.PermissionValidator");
        //STRUTS_ACTION_CLASS_IMPORTS.add("ru.scarlettgroup.phobosserver.util.PermissionValidator");
        //STRUTS_ACTION_CLASS_IMPORTS.add("ru.scarlettgroup.titanconsole.web.tag.ActionConstants");

//		NEED?
//		STRUTS_ACTION_CLASS_IMPORTS.add(PACKAGE_NAME_PREFIX + ".web.tag.ActionConstants");
        //DELEGATE_CLASS_IMPORTS.add("ru.scarlettgroup.titanconsole.integration.dao.DAOFactory");
        //DELEGATE_CLASS_IMPORTS.add("biz.tt.hyperion.integration.dao.DAOFactory");
        //DELEGATE_CLASS_IMPORTS.add("ru.scarlettgroup.phobosserver.integration.dao.DAOFactory");
        DELEGATE_CLASS_IMPORTS.add("java.util.List");
        DELEGATE_CLASS_IMPORTS.add("org.apache.log4j.Logger");
        DELEGATE_CLASS_IMPORTS.add(PACKAGE_NAME_PREFIX + ".integration.dao.DAOFactory");

        //STRUTS_ACTION_CLASS_IMPORTS.add("biz.tt.hyperion.web.tag.ActionConstants");
        //STRUTS_ACTION_CLASS_IMPORTS.add("ru.scarlettgroup.phobosserver.web.tag.ActionConstants");
        STRUTS_ACTION_CLASS_IMPORTS.add("java.util.List");
    }

    static {
        DB_PK_COLUMN_SET.add("SERVICE_CD");
        DB_PK_COLUMN_SET.add("PRINCIPAL_NO");
        DB_PK_COLUMN_SET.add("AGENT_NO");
        Iterator iter = DB_PK_COLUMN_SET.iterator();
        while (iter.hasNext()) {
            DB_PK_COLUMN_LIST.add((String) iter.next());
        }

        VO_CLASS_IMPORTS.add("java.io.Serializable");
        VO_CLASS_IMPORTS.add("java.sql.Types");
        // JAVA_IMPORTS.add("java.util.ArrayList");
        VO_CLASS_IMPORTS.add("java.util.Date");

        VO_CLASS_EXTENDS.add("RecordMapperValueObject");
        VO_CLASS_IMPLEMENTS.add("Serializable");

        DAO_INTERFACE_IMPORTS.add("java.util.List");

        DAO_CLASS_IMPORTS.add("java.sql.Connection");
        DAO_CLASS_IMPORTS.add("java.sql.PreparedStatement");
        DAO_CLASS_IMPORTS.add("java.sql.ResultSet");
        DAO_CLASS_IMPORTS.add("java.sql.Types");
        DAO_CLASS_IMPORTS.add("java.sql.SQLException");
        DAO_CLASS_IMPORTS.add("java.util.ArrayList");
        DAO_CLASS_IMPORTS.add("java.util.List");
        DAO_CLASS_IMPORTS.add("java.text.ParseException");
        DAO_CLASS_EXTENDS.add("BaseDAO");

        FILTER_BEAN_CLASS_IMPORTS.add("java.io.Serializable");
        FILTER_BEAN_CLASS_IMPORTS.add("java.math.BigDecimal");
        FILTER_BEAN_CLASS_IMPORTS.add("java.util.Date");

        WEB_BEAN_CLASS_IMPORTS.add("java.io.Serializable");
        WEB_BEAN_CLASS_IMPLEMENTS.add("Serializable");


        STRUTS_FORM_CLASS_IMPORTS.add("java.io.UnsupportedEncodingException");
        STRUTS_FORM_CLASS_IMPORTS.add("java.math.BigDecimal");
        STRUTS_FORM_CLASS_IMPORTS.add("java.text.ParseException");
        STRUTS_FORM_CLASS_IMPORTS.add("java.util.ArrayList");
        STRUTS_FORM_CLASS_IMPORTS.add("java.util.Date");
        STRUTS_FORM_CLASS_IMPORTS.add("java.util.Iterator");
        STRUTS_FORM_CLASS_IMPORTS.add("java.util.List");
        STRUTS_FORM_CLASS_IMPORTS.add("java.util.StringTokenizer");
        STRUTS_FORM_CLASS_IMPORTS.add("javax.servlet.http.HttpServletRequest");
        STRUTS_FORM_CLASS_IMPORTS.add("org.apache.log4j.Logger");
        STRUTS_FORM_CLASS_IMPORTS.add("org.apache.struts.action.ActionMessage");
        STRUTS_FORM_CLASS_IMPORTS.add("org.apache.struts.action.ActionErrors");
        STRUTS_FORM_CLASS_IMPORTS.add("org.apache.struts.action.ActionForm");
        STRUTS_FORM_CLASS_IMPORTS.add("org.apache.struts.action.ActionMapping");

        STRUTS_FORM_CLASS_EXTENDS.add("ActionForm");


        STRUTS_ACTION_CLASS_EXTENDS.add("Action");

        //XMLHANDLER_CLASS_IMPORTS.add("java.io.BufferedReader");
        XMLHANDLER_CLASS_IMPORTS.add("java.io.ByteArrayInputStream");
        XMLHANDLER_CLASS_IMPORTS.add("java.util.Iterator");
        //XMLHANDLER_CLASS_IMPORTS.add("java.io.File");
        //XMLHANDLER_CLASS_IMPORTS.add("java.io.FileReader");
        //XMLHANDLER_CLASS_IMPORTS.add("java.io.IOException");
        XMLHANDLER_CLASS_IMPORTS.add("java.io.StringWriter");
        XMLHANDLER_CLASS_IMPORTS.add("java.io.UnsupportedEncodingException");
        XMLHANDLER_CLASS_IMPORTS.add("org.jibx.runtime.BindingDirectory");
        XMLHANDLER_CLASS_IMPORTS.add("org.jibx.runtime.IBindingFactory");
        XMLHANDLER_CLASS_IMPORTS.add("org.jibx.runtime.IMarshallingContext");
        XMLHANDLER_CLASS_IMPORTS.add("org.jibx.runtime.IUnmarshallingContext");
        XMLHANDLER_CLASS_IMPORTS.add("org.jibx.runtime.JiBXException");


        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_PACKAGE);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_IMPORT);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_PUBLIC);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_PROTECTED);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_PRIVATE);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_CLASS);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_CATCH);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_TRY);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_NEW);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_WHILE);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_IF);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_ELSE);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_INT);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_INTERFACE);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_FINALLY);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_STATIC);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_VOID);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_THIS);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_RETURN);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_EXTENDS);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_IMPLEMENTS);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_THROWS);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_THROW);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_SWITCH);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_NATIVE);
        JAVA_KEYWORD_SET.add(JAVA_KEYWORD_DEFAULT);

        resetImports();
    }

    protected static final String DAO_MEMBER_NAME_RS = "rs";
    protected static final String DAO_MEMBER_NAME_PS = "ps";
    protected static final String DAO_MEMBER_NAME_I = "i";
    protected static final String DAO_MEMBER_NAME_LIST = "list";
    protected static final String DAO_MEMBER_NAME_CONNECTION = "connection";
    protected static final String DAO_MEMBER_NAME_SQL = "sql";
    protected static final String DAO_MEMBER_NAME_VO = "vo";
    protected static final String DAO_MEMBER_NAME_E = "e";


    protected static final String DAO_METHOD_NAME_SETSQLPARAMETERS = "setSQLParameter";
    protected static final String DAO_METHOD_NAME_SETSTRING = "setString";
    //protected static final String DAO_METHOD_NAME_GETCONNECTION = "getEdgeConnection";
    protected static final String DAO_METHOD_NAME_GETCONNECTION = "getConnection";
    protected static final String DAO_METHOD_NAME_PREPARESTATEMENT = "prepareStatement";
    protected static final String DAO_METHOD_NAME_EXECUTEQUERY = "executeQuery";
    protected static final String DAO_METHOD_NAME_EXECUTEUPDATE = "executeUpdate";
    protected static final String DAO_METHOD_NAME_NEXT = "next";
    protected static final String DAO_METHOD_NAME_POPULATEVALUEOBJECT = "populateValueObject";
    protected static final String DAO_METHOD_NAME_ADD = "add";
    protected static final String DAO_METHOD_NAME_GETMESSAGE = "getMessage";
    protected static final String DAO_METHOD_NAME_FINALIZE = "finalize";

    protected static final String XMLHANDLER_METHOD_NAME_TOXMLSTRING = "toXMLString";
    protected static final String XMLHANDLER_METHOD_NAME_FROMXMLSTRING = "fromXMLString";

    protected static final String XMLHANDLER_CLASS_NAME = "XMLHandler";
    protected static final String XMLHANDLER_PARAM_NAME_BFACT = "bfact";
    protected static final String XMLHANDLER_PARAM_NAME_TOXML = "toXml";
    protected static final String XMLHANDLER_PARAM_NAME_XMLSTR = "xmlStr";
    protected static final String XMLHANDLER_PARAM_NAME_XMLOUT = "xmlOut";
    protected static final String XMLHANDLER_LIB_IBINDINGFACTORY = "IBindingFactory";
    protected static final String XMLHANDLER_LIB_BINDINGFACTORY = "BindingDirectory";
    protected static final String XMLHANDLER_LIB_JIBXEXCEPTION = "JiBXException";
    protected static final String XMLHANDLER_METHOD_NAME_CREATEMARSHALLINGCONTEXT = "createMarshallingContext";
    protected static final String XMLHANDLER_METHOD_NAME_CREATEUNMARSHALLINGCONTEXT = "createUnmarshallingContext";
    protected static final String XMLHANDLER_METHOD_NAME_MARSHALDOCUMENT = "marshalDocument";
    protected static final String XMLHANDLER_METHOD_NAME_UNMARSHALDOCUMENT = "unmarshalDocument";
    protected static final String XMLHANDLER_METHOD_NAME_VALIDATEXMLSTRUCTURE = "validateXMLStructure";
    protected static final String XMLHANDLER_METHOD_NAME_VALIDATEXMLTREE = "validateXMLTree";
    protected static final String XMLHANDLER_LIB_IMARSHALLINGCONTEXT = "IMarshallingContext";
    protected static final String XMLHANDLER_LIB_IUNMARSHALLINGCONTEXT = "IUnmarshallingContext";
    protected static final String XMLHANDLER_LIB_UNSUPPORTEDENCODINGEXCEPTION = "UnsupportedEncodingException";
    protected static final String XMLHANDLER_LIB_XSDVALIDATIONEXCEPTION = "XSDValidationException";
    protected static final String XMLHANDLER_PARAM_NAME_MCTX = "mctx";
    protected static final String XMLHANDLER_PARAM_NAME_UCTX = "uctx";

    protected static final String XMLHANDLER_LIB_JAVAMATHBIGINTEGER = "java.math.BigInteger";


    protected static final String XMLHANDLER_VALIDATION_ERR_ISINVALID = " is invalid";
    protected static final String XMLHANDLER_VALIDATION_ERR_ISNULL = " is null.  XSD declares that this element is mandatory.";
    protected static final String XMLHANDLER_VALIDATION_ERR_NONNEGATIVEINTEGER = "  XSD declares that this element is a nonNegativeInteger.";
    protected static final String XMLHANDLER_VALIDATION_ERR_NONPOSITIVEINTEGER = "  XSD declares that this element is a nonPositiveInteger.";


    protected static final String METHOD_NAME_GETLOGGER = "getLogger";
    protected static final String METHOD_NAME_PRINTSTACKTRACE = "printStackTrace";
    protected static final String METHOD_NAME_GETFACTORY = "getFactory";
    protected static final String METHOD_NAME_TOSTRING = "toString";
    protected static final String METHOD_NAME_INTVALUE = "intValue";
    protected static final String METHOD_NAME_LONGVALUE = "longValue";
    protected static final String METHOD_NAME_DOUBLEVALUE = "doubleValue";
    protected static final String METHOD_NAME_SETOUTPUT = "setOutput";
    protected static final String METHOD_NAME_GETBYTES = "getBytes";
    protected static final String METHOD_NAME_FORMAT = "format";
    protected static final String METHOD_NAME_PARSE = "parse";

    protected static final String PARAM_NAME_BAIS = "bais";
    protected static final String PARAM_NAME_XML = "xml";
    protected static final String PARAM_NAME_E = "e";
    protected static final String PARAM_NAME_O = "o";
    protected static final String PARAM_NAME_RET = "ret";
    protected static final String PARAM_NAME_STRINGWRITER = "stringWriter";

    protected static final String PARAM_NAME_ORDERINGOFORDERELEMENTS = "orderingOfOrderElements";

    protected static final String FILTER_BEAN_RANGE_SUFFIX_FROM = "From";
    protected static final String FILTER_BEAN_RANGE_SUFFIX_TO = "To";
    protected static final String FILTER_BEAN_SUFFIX_ORDER = "Order";
    protected static final String FILTER_BEAN_SUFFIX_ORDERING = "Ordering";
    protected static final String FILTER_BEAN_METHOD_RESET_ORDERING = "resetOrdering";
    protected static final String FILTER_BEAN_METHOD_IS_ORDERING_REQUIRED = "isOrderingRequired";

    protected String generateClassOrInterfaceFooter() {
        return CLOSE_BRACE;
    }

    protected String getTypeName(VODetails detail) {
        String typeName = detail.javaFieldTypeName;
        if (typeName == null && detail.javaFieldTypeClass != null) {
            //typeName = getShortJavaTypeName(detail.javaFieldTypeClass.getName());
            typeName = detail.javaFieldTypeClass.getName();
        }
        return typeName;
    }

    protected String getShortJavaTypeName(String name) {
        int i = name.lastIndexOf(".");
        if (i == -1)
            return name;
        return name.substring(i + 1);
    }

    protected String getShortJavaTypeNameBeforeFirstPeriod(String name) {
        if (name == null) return "???";
        int i = name.indexOf(".");
        if (i == -1)
            return name;
        return name.substring(0, i);
    }

    protected String getDisplayFieldNameFromDBFieldName(String name) {
        name = name.toLowerCase();
        String[] fields = name.split("_");
        String result = "";
        for (int i = 0; i < fields.length; i++) {
            result += upperFirstChar(fields[i]);
            if (i + 1 < fields.length) {
                result += " ";
            }
        }
        return result;
    }

    protected String getJavaFieldNameFromDBFieldName(String name) {
        name = name.toLowerCase();
        String[] fields = name.split("_");
        String result = "";
        for (int i = 0; i < fields.length; i++) {
            if (i == 0) {
                result += fields[i];
            } else {
                result += upperFirstChar(fields[i]);
            }
        }
        return result;
    }

    protected String getJavaGetNameFromJavaFieldName(String name) {
        return GET_PREFIX + upperFirstChar(name);
    }

    protected String getJavaSetNameFromJavaFieldName(String name) {
        return SET_PREFIX + upperFirstChar(name);
    }

    protected String getJavaGetNameFromDBFieldName(String name) {
        name = name.toLowerCase();
        String[] fields = name.split("_");
        String result = "";
        for (int i = 0; i < fields.length; i++) {
            result += upperFirstChar(fields[i]);
        }
        return GET_PREFIX + result;
    }

    protected String getJavaSetNameFromDBFieldName(String name) {
        name = name.toLowerCase();
        String[] fields = name.split("_");
        String result = "";
        for (int i = 0; i < fields.length; i++) {
            result += upperFirstChar(fields[i]);
        }
        return SET_PREFIX + result;
    }

    protected String lowerFirstChar(String str) {
        String strTmp = null;
        try {
            strTmp = str.substring(0, 1).toLowerCase() + str.substring(1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return strTmp;
    }

    protected String upperFirstChar(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    protected void generateJavaBeanFieldTypeSQLType(
            VOFieldToColumnMappingDetails detail) {
        if (detail.dbColumnType == Types.VARCHAR) {
            detail.dbColumnTypeName = "Types.VARCHAR";
            detail.javaFieldTypeClass = String.class;
            detail.javaFieldTypeName = "String.class";
        } else if (detail.dbColumnType == Types.DATE) {
            detail.dbColumnTypeName = "Types.DATE";
            detail.javaFieldTypeClass = Date.class;
            detail.javaFieldTypeName = "Date.class";
        } else if (detail.dbColumnType == Types.INTEGER) {
            detail.dbColumnTypeName = "Types.INTEGER";
            detail.javaFieldTypeClass = Integer.class;
            detail.javaFieldTypeName = "Integer.class";
        } else if (detail.dbColumnType == Types.CHAR) {
            detail.dbColumnTypeName = "Types.CHAR";
            detail.javaFieldTypeClass = Character.class;
            detail.javaFieldTypeName = "Character.class";
        } else if (detail.dbColumnType == Types.TIMESTAMP) {
            detail.dbColumnTypeName = "Types.TIMESTAMP";
            detail.javaFieldTypeClass = Date.class;
            detail.javaFieldTypeName = "Date.class";
        } else if (detail.dbColumnType == Types.TIME) {
            detail.dbColumnTypeName = "Types.TIME";
            detail.javaFieldTypeClass = Date.class;
            detail.javaFieldTypeName = "Date.class";
        } else if (detail.dbColumnType == Types.NUMERIC) {
            detail.dbColumnTypeName = "Types.NUMERIC";
            detail.javaFieldTypeClass = BigDecimal.class;
            detail.javaFieldTypeName = "BigDecimal.class";
        } else if (detail.dbColumnType == Types.BIGINT) {
            detail.dbColumnTypeName = "Types.BIGINT";
            detail.javaFieldTypeClass = Long.class;
            detail.javaFieldTypeName = "Long.class";
        } else if (detail.dbColumnType == Types.DOUBLE) {
            detail.dbColumnTypeName = "Types.DOUBLE";
            detail.javaFieldTypeClass = Double.class;
            detail.javaFieldTypeName = "Double.class";
        } else if (detail.dbColumnType == Types.FLOAT) {
            detail.dbColumnTypeName = "Types.FLOAT";
            detail.javaFieldTypeClass = Float.class;
            detail.javaFieldTypeName = "Float.class";
        } else if (detail.dbColumnType == Types.DECIMAL) {
            detail.dbColumnTypeName = "Types.DECIMAL";
            detail.javaFieldTypeClass = Double.class;
            detail.javaFieldTypeName = "Double.class";
        } else if (detail.dbColumnType == Types.SMALLINT) {
            detail.dbColumnTypeName = "Types.SMALLINT";
            detail.javaFieldTypeClass = Short.class;
            detail.javaFieldTypeName = "Short.class";
        } else if (detail.dbColumnType == Types.TINYINT) {
            detail.dbColumnTypeName = "Types.TINYINT";
            detail.javaFieldTypeClass = Byte.class;
            detail.javaFieldTypeName = "Byte.class";
        } else if (detail.dbColumnType == Types.BLOB) {
            detail.dbColumnTypeName = "Types.BLOB";
            detail.javaFieldTypeClass = byte[].class;
            detail.javaFieldTypeName = byte[].class.getName();
        } else if (detail.dbColumnType == Types.CLOB) {
            detail.dbColumnTypeName = "Types.CLOB";
            detail.javaFieldTypeClass = char[].class;
            detail.javaFieldTypeName = char[].class.getName();
        }
        /*
         * Types.ARRAY; Types.BINARY; Types.BIT; Types.BLOB; Types.BOOLEAN;
		 * Types.CLOB; Types.DATALINK; Types.STRUCT; Types.VARBINARY;
		 * Types.DISTINCT; Types.JAVA_OBJECT; Types.LONGVARBINARY;
		 * Types.LONGVARCHAR; Types.OTHER; Types.REAL; Types.REF;
		 */
    }

    protected int getSQLType(String typeStr) {
        typeStr = typeStr.toUpperCase();
        if (typeStr.equals("VARCHAR")) {
            return Types.VARCHAR;
        } else if (typeStr.equals("TEXT")) {
            return Types.VARCHAR;
        } else if (typeStr.equals("DATE")) {
            return Types.DATE;
        } else if (typeStr.equals("INTEGER")) {
            return Types.INTEGER;
        } else if (typeStr.equals("CHAR")) {
            return Types.CHAR;

        } else if (typeStr.equals("TIMESTAMP")) {
            return Types.TIMESTAMP;
        } else if (typeStr.equals("TIME")) {
            return Types.TIME;
        } else if (typeStr.equals("NUMERIC")) {
            return Types.NUMERIC;
        } else if (typeStr.equals("BIGINT")) {
            return Types.BIGINT;
        } else if (typeStr.equals("DOUBLE")) {
            return Types.DOUBLE;
        } else if (typeStr.equals("FLOAT")) {
            return Types.FLOAT;

        } else if (typeStr.equals("DECIMAL")) {
            return Types.DECIMAL;
        } else if (typeStr.equals("SMALLINT")) {
            return Types.SMALLINT;
        } else if (typeStr.equals("TINYINT")) {
            return Types.TINYINT;
        } else if (typeStr.equals("BLOB")) {
            return Types.BLOB;
        } else if (typeStr.equals("CLOB")) {
            return Types.CLOB;
        } else if (typeStr.equals("bytea")) {
            return Types.BINARY;
        }
        /*
		 * Types.ARRAY; Types.BINARY; Types.BIT; Types.BLOB; Types.BOOLEAN;
		 * Types.CLOB; Types.DATALINK; Types.STRUCT; Types.VARBINARY;
		 * Types.DISTINCT; Types.JAVA_OBJECT; Types.LONGVARBINARY;
		 * Types.LONGVARCHAR; Types.OTHER; Types.REAL; Types.REF;
		 */
        return -1;
    }

    protected StringBuffer generateJavaClassFileHeader(String className, String packageName,
                                                       List voClassImports, List voClassExtends, List voClassImplements) {
        StringBuffer header = new StringBuffer();
        header.append(JAVA_KEYWORD_PACKAGE).append(SPACE).append(
                packageName).append(SEMICOLON).append(DBL_NEW_LINE);
        Iterator iter = voClassImports.iterator();
        while (iter.hasNext()) {
            String imprt = (String) iter.next();
            header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(imprt)
                    .append(SEMICOLON).append(NEW_LINE);
        }
        header.append(NEW_LINE);
        header.append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(
                JAVA_KEYWORD_CLASS).append(SPACE).append(className)
                .append(SPACE);

        if (!voClassExtends.isEmpty()) {
            header.append(JAVA_KEYWORD_EXTENDS).append(SPACE);
            iter = VO_CLASS_EXTENDS.iterator();
            while (iter.hasNext()) {
                header.append((String) iter.next());
                if (iter.hasNext()) {
                    header.append(COMMA).append(SPACE);
                }
            }
        }
        if (!voClassImplements.isEmpty()) {
            header.append(SPACE).append(JAVA_KEYWORD_IMPLEMENTS).append(SPACE);
            iter = VO_CLASS_IMPLEMENTS.iterator();
            while (iter.hasNext()) {
                header.append((String) iter.next());
                if (iter.hasNext()) {
                    header.append(COMMA).append(SPACE);
                }
            }
        }
        header.append(SPACE).append(OPEN_BRACE).append(DBL_NEW_LINE);
        return header;
    }

    protected String getIndent(int indent) {
        String indentation = "";
        for (int i = 0; i < indent; i++) {
            indentation += TAB;
        }
        return indentation;
    }

    protected List<VOFieldToColumnMappingDetails> getPrimaryKeys(List<VOFieldToColumnMappingDetails> dbColumns) {
        List<VOFieldToColumnMappingDetails> pkColumns = new ArrayList<VOFieldToColumnMappingDetails>();
        Iterator<VOFieldToColumnMappingDetails> iter = dbColumns.iterator();
        while (iter.hasNext()) {
            VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter.next();
            if (detail.isPK) {
                pkColumns.add(detail);
            }
        }

        return pkColumns;
    }

    protected static final String VO_CLASSNAME_TYPE_CONCAT_CHAR = "$";

    protected static final String XML_FILE_EXTENSION = "xml";
    protected static final String JIBX_MAP_FILE_SUFFIX = "JIBXBinding";

    protected static final String TXT_FILE_EXTENSION = "txt";

    protected String SOURCE_JAVA_CLASS_XSDVALIDATIONEXCEPTION =
            "package com.belltv.edge.io.csg.bean;\n" +
                    "\n" +
                    "public class XSDValidationException extends RuntimeException {\n" +
                    "	String msg = null;\n" +
                    "	public XSDValidationException(String msg) {\n" +
                    "		super(msg);\n" +
                    "		this.msg = msg;\n" +
                    "	}\n" +
                    "}";

}