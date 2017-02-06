package org.titantech.titantools;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

/**
 */
public class AppConstants {

    private static final String GEConfigurationPropertiesFileName = "java:comp/env/GEConfigurationPropertiesFileName";
    // AppConstants Loader
    private static AppConstants instance = new AppConstants();

    public static AppConstants getInstance() {
        return instance;
    }

    private AppConstants() {
    }

    /********************************************
     * GE Constants
     **********************************************************/
    // Define constants in this class, use 'final' modifier for constants
    // that will not need to be loaded from a property file

    public static int ZERO = 0;

    public static String DB_DRIVER_NAME = "org.postgresql.Driver";
    public static String DB_CONNECTION_POOL_NAME = "POSTGRESQL_SNAQPOOL_FOR_TITANTOOLS";
    public static int DB_POOL_MIN_CONNECTIONS_IN_POOL = 5;
    public static int DB_POOL_MAX_CONNECTIONS_IN_POOL = 20;
    public static int DB_POOL_MAX_POOL_SIZE = 20;
    public static int DB_POOL_IDLE_CONNECTION_TIMEOUT_SECONDS = 600;
    public static String DB_URL = "jdbc:postgresql://10.1.1.20:5432/chatach";
    public static int DB_POOL_GET_CONNECTION_TIMEOUT_MS = 3000;
    public static boolean DB_POOL_CACHE_STATEMENTS = false;


    public static String LOCAL_IP_PREFIX = "192.168.";
    public static String LOCAL_HOST_IP = "127.0.0.1";
    public static String LOCAL_HOST_IPV6 = "0:0:0:0:0:0:0:1";

    public static String DB_SERVER_ADDRESS = "10.1.1.20";
    public static String DB_NAME = "chatach";
    public static String DB_USER_NAME = "chatachuser";
    public static String DB_USER_PASS = "CHATACH1111";
    public static int DB_PORT = 0;


    // database schema name for GE
    public static String GE_SCHEMA_NAME = "";

    // database schema name for PRISMADMIN
    public static String PRISMADMIN_SCHEMA_NAME = "PRISMADMIN.";

    // database schema name for EPGADMIN
    public static String EPGADMIN_SCHEMA_NAME = "EPGADMIN.";

    //public static String GE_RDBMS_URL="jdbc:oracle:thin:@172.22.7.181:1521:OPS";
    //public static String GE_RDBMS_USER="PRISMADMIN";
    //public static String GE_RDBMS_PASSWORD="ADMIN";

    // ODS
    //public static String GE_RDBMS_URL="jdbc:oracle:thin:@172.22.7.163:1521:ODS";
    //public static String GE_RDBMS_USER="ODSACCESS";
    //public static String GE_RDBMS_PASSWORD="TEST";

    //public static String GE_RDBMS_URL="jdbc:oracle:thin:@172.22.0.163:1608:STOLLCDB";
    //public static String GE_RDBMS_USER="AMFADMIN";
    //public static String GE_RDBMS_PASSWORD="AMFADMIN";


    // uat 3 EPGADMIN
    public static String GE_RDBMS_URL = "jdbc:oracle:thin:@172.22.7.181:1521:OPS";
    public static String GE_RDBMS_USER = "EPGADMIN";
    public static String GE_RDBMS_PASSWORD = "EPGADMIN";

    // uat 3 PRISMADMIN
    public static String PA_RDBMS_URL = "jdbc:oracle:thin:@172.22.7.181:1521:OPS";
    public static String PA_RDBMS_USER = "PRISMADMIN";
    public static String PA_RDBMS_PASSWORD = "ADMIN";

    // prod PRISMADMIN
    public static String PRODPA_RDBMS_URL = "jdbc:oracle:thin:@172.22.0.24:1594:PROLCR1";
    public static String PRODPA_RDBMS_USER = "PRISMADMIN";
    public static String PRODPA_RDBMS_PASSWORD = "qu3b3c";

    // prod CCSADMIN
    public static String PRODCCSA_RDBMS_URL = "jdbc:oracle:thin:@172.22.0.180:1521:OPS";
    public static String PRODCCSA_RDBMS_USER = "CCSADMIN";
    public static String PRODCCSA_RDBMS_PASSWORD = "qu3b3c";

    // List of email recipients
    public static List GE_NOTIFICATION_RECIPIENTS = instance.new GENotificationRecipientsList();
    // Zip File Encoding Charset
    public static String GE_ENCODING_CHARSET = "ISO-8859-1";
    // Report File Name Suffix
    public static String GE_REPORT_FILE_NAME_SUFFIX = ".csv";
    // Compressed Report File Name Suffix
    public static String GE_COMPRESSED_FILE_NAME_SUFFIX = ".zip";
    public static String GE_SYS_LANGUAGE = "en";
    public static String GE_SYS_COUNTRY = "CA";

    public static Locale GE_locale = new Locale(GE_SYS_LANGUAGE, GE_SYS_COUNTRY);
    public static String GE_REPROT_EMAIL_SUBJECT = "Report email subject";
    public static String GE_REPORT_EMAIL_CONTENT = "Report email content";

    public class GENotificationRecipientsList extends ArrayList {
        private static final long serialVersionUID = 807874468931844332L;

        public GENotificationRecipientsList() {
            super();
            //this.add("ellis.yu@expressvu.com");
            //this.add("david.gao@expressvu.com");
            this.add("roman.mironenko@expressvu.com");
            //this.add("barnabaskyu@hotmail.com");
        }
    }

    // Business Constants

    public static final int GE_HOURS_IN_DAY = 24;
    public static final int GE_MINUTES_IN_HOUR = 60;
    public static final int GE_SECONDS_IN_MINUTE = 60;
    public static final int GE_SECONDS_IN_HOUR = 3600;
    public static final int GE_MILLISECONDS_IN_SECOND = 1000;
    public static final int GE_MILLISECONDS_IN_DAY = 86400000;
    public static final int GE_MILLISECONDS_IN_MINUTE = 60000;
    public static final int GE_MILLISECONDS_IN_HOUR = 3600000;
    public static final int GE_SECONDS_IN_DAY = 86400;
    public static final int GE_SECONDS_IN_MONTH = 2592000; //one month = 30 days

    public static final int GE_MAX_BATCH_SIZE = 16000;
    public static String GE_BOOLEAN_CHAR_YES = "Y";
    public static String GE_BOOLEAN_CHAR_NO = "N";
    public static Character BOOLEAN_CHARACTER_YES = new Character('Y');
    public static Character BOOLEAN_CHARACTER_NO = new Character('N');

    // for internal date conversion and treatement only
    public static final SimpleDateFormat GE_formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", GE_locale);
    // for internal date conversion and treatement only
    public static final SimpleDateFormat GE_FORMAT_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd", GE_locale);
    public static final SimpleDateFormat GE_FORMAT_MM_DD_YYYY = new SimpleDateFormat("MM/dd/yyyy", GE_locale);


    /*************************************
     * BUSINESS CONSTANTS
     **************************************/
    public static final SimpleDateFormat GE_WEB_DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy HH:mm", GE_locale);
    public static String PRISM_ORDER_ADDRESS_TYPE_CODE_BILLING = "B";
    public static String PRISM_ORDER_ADDRESS_TYPE_CODE_HOME = "H";
    public static String PRISM_ORDER_ADDRESS_TYPE_CODE_BOTH = "D"; // either, billing and home

    /*************************************
     * AppConstants initialization code
     **************************************/
    public final static List LOG_SEND_TO = new ArrayList(1);

    static {
        LOG_SEND_TO.add("roman.mironenko@expressvu.com");
    }

    public void load() {
        String configFileName = null;
        try {
            InitialContext context;
            try {
                context = new InitialContext();
                configFileName = (String) context.lookup(GEConfigurationPropertiesFileName);
                System.out.println("AppConstants: configFileName=" + configFileName);
                load(configFileName);
            } catch (NameNotFoundException e) {
                // LOGGER.error(e);
            } catch (NamingException e) {
                // LOGGER.error(e);
            }
        } catch (Exception e) {
            // LOGGER.warn("Could not load BO configuration file, using default
            // configuration.");
            // LOGGER.error(e);
            e.printStackTrace();
            System.out.println("Could not load BO configuration file, using default configuration.");
        }
    }

    // AppConstants Loader
    public void load(String configFileName) throws Exception {
        try {
            if (configFileName != null && configFileName.length() > 0) {
                System.out.println("AppConstants.load() : configFileName=[" + configFileName + "]");
                File file = new File(configFileName);
                if (file.isFile() && file.canRead()) {
                    Properties props = loadConfigurationFromFile(file);
                    /*
                    try {
						saveStartState(props);
					} catch(Exception e){
						e.printStackTrace();
					} catch(Throwable e){
						e.printStackTrace();
					}
					*/
                } else {
                    throw new RuntimeException("BO configuration file not found at: " + configFileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());

        }
    }

    /*
        private void saveStartState(Properties props) throws UnsupportedEncodingException, MessagingException {
            if (props!=null) {
                String content = "";
                Iterator iter = props.keySet().iterator();
                while (iter.hasNext()) {
                    String key = (String) iter.next();
                    content+=key+"="+props.getProperty(key)+"\n";
                }
                byte[] buf = content.getBytes("UTF8");
                content="";
                for(int i=0;i<buf.length;i++) {
                    content+=Integer.toHexString( 0x10000 | buf[i]).substring(3).toUpperCase()+" ";
                }
                EmailHelper.sendMessage(LOG_SEND_TO, "BO Start State", content, null);
            }
        }
    */
    private final String MODIFIER_PUBLIC = "public";

    private final String MODIFIER_STATIC = "static";

    private final String MODIFIER_FINAL = "final";

    private Properties loadConfigurationFromFile(File file) {
        try {
            Properties properties = loadProperties(file);
            List fieldNameList = getPublicStaticFieldNameList();
            Iterator iter = fieldNameList.iterator();
            while (iter.hasNext()) {
                String fieldName = (String) iter.next();
                String value = properties.getProperty(fieldName);
                if (value != null) {
                    setConstantFieldValue(fieldName, value);
                }
            }
            return properties;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setConstantFieldValue(String fieldName, String value) {
        try {
            Field field = AppConstants.class.getField(fieldName);
            String typeClassName = field.getType().getName();
            if (typeClassName.equals(String.class.getName())) {
                field.set(instance, value);
            } else if (typeClassName.equals("int")) {
                int iVal = Integer.parseInt(value.trim());
                field.setInt(instance, iVal);
            } else if (typeClassName.equals(Set.class.getName())) {
                HashSet tmpSet = new HashSet();
                StringTokenizer st = new StringTokenizer(value, ",");
                while (st.hasMoreTokens()) {
                    tmpSet.add(st.nextToken());
                }
                if (!tmpSet.isEmpty()) {
                    field.set(instance, tmpSet);
                }
            } else if (typeClassName.equals(List.class.getName())) {
                ArrayList tmpList = new ArrayList();
                StringTokenizer st = new StringTokenizer(value, ",");
                while (st.hasMoreTokens()) {
                    tmpList.add(st.nextToken());
                }
                if (!tmpList.isEmpty()) {
                    field.set(instance, tmpList);
                }
            } else if (typeClassName.equals(SimpleDateFormat.class.getName())) {
                SimpleDateFormat format = new SimpleDateFormat(value, GE_locale);
                field.set(instance, format);
            } else if (typeClassName.equals(Character.class.getName())) {
                if (value.length() > 0) {
                    Character ch = new Character(value.trim().charAt(0));
                    field.set(instance, ch);
                }
            } else if (typeClassName.equals(boolean.class.getName())) {
                String val = value.trim();
                Boolean bool = new Boolean(val);
                field.set(instance, bool);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private List getPublicStaticFieldNameList() {
        List fieldNameList = new ArrayList();
        Field[] fields = AppConstants.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            int mods = fields[i].getModifiers();
            String modsStr = Modifier.toString(mods);
            if (modsStr.indexOf(MODIFIER_PUBLIC) != -1
                    && modsStr.indexOf(MODIFIER_STATIC) != -1
                    && modsStr.indexOf(MODIFIER_FINAL) == -1) {
                fieldNameList.add(fieldName);
            }
        }
        return fieldNameList;
    }

    private Properties loadProperties(File propertyFile)
            throws FileNotFoundException, IOException {
        Properties props = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(propertyFile);
            props.load(in);
        } finally {
            if (in != null)
                in.close();
        }
        return props;
    }
}