package org.titantech.titantools.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.postgresql.Driver;
import org.postgresql.ds.PGSimpleDataSource;
import org.titantech.titantools.AppConstants;
import org.titantech.titantools.TTAppException;

import snaq.db.ConnectionPool;


/**
 * @author roman.mironenko
 * @version 1.0
 * @created 2-Oct-2006
 */
public class PGDAOFactory extends DAOFactory {

    private static Class CLAZZ = PGDAOFactory.class;
    private static Logger logger = Logger.getLogger(CLAZZ);


    private Connection connection;

    public PGDAOFactory() {
        if (DAOFactory.isTesting) {
            setupConnections();
        }
    }

    public PGDAOFactory(Connection connection) {
        this.connection = connection;
    }

    protected void setupConnections() {
        try {
            getConnectionFromHardcodedFactory();
            //getConnectionFromService();
        } catch (SQLException e) {
            logger.fatal(e);
        }
    }

    protected void setupConnections(String serverName, String databaseName, String user, String password, Integer port) {
        try {
            getConnectionFromFactory(serverName, databaseName, user, password, port);
            //getConnectionFromService();
        } catch (SQLException e) {
            logger.fatal(e);
        }
    }

    private void getConnectionFromFactory(String serverName, String databaseName, String user, String password, Integer port) throws SQLException {
        Connection conn = null;
        ConnectionFactory f = new ConnectionFactory(serverName, databaseName, user, password, port);
        conn = f.getConnection();
        conn.setAutoCommit(true);
        setConnection(conn);
    }

    private void getConnectionFromHardcodedFactory() throws SQLException {
        Connection conn = null;
        ConnectionFactory f = new ConnectionFactory();
        conn = f.getConnection();
        conn.setAutoCommit(true);
        setConnection(conn);
    }

    /**
     * Close and release connection.
     *
     * @param conn
     */
    protected void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void releaseConnections() throws DAOSysException {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DAOSysException(e.getMessage());
            }
        }
    }

    protected void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return this.connection;
    }

    private Object userTransaction = null;

    public void startTransaction() {
        userTransaction = new Object();
    }

    public void commitTransaction() {
        userTransaction = null;
    }

    public void rollbackTransaction() {
        userTransaction = null;
    }

    public void resetTransaction() {
        try {
            if (userTransaction != null) {
                rollbackTransaction();
            }
        } catch (Throwable e) {
            logger.error("ORADAOFactory.resetTransaction() : rollback failed : " + e.getMessage());
        }
        userTransaction = null;
        startTransaction();
    }

    public boolean isInTransaction() {
        if (userTransaction == null) {
            return false;
        }
        return true;
    }

    private class ConnectionFactory {
        private PGSimpleDataSource ds = null;

        public ConnectionFactory() {
            ds = setupDataSource();
        }

        public ConnectionFactory(String serverName, String databaseName, String user, String password, Integer port) {
            ds = setupDataSource(serverName, databaseName, user, password, port);
        }

        private PGSimpleDataSource setupDataSource() {
            PGSimpleDataSource ds;
            //String databaseUrl = "jdbc:oracle:thin:@172.22.7.181:1521:OPS";
            //String userId = "PRISMADMIN";
            //String password = "ADMIN";
            //String databaseUrl = AppConstants.PA_RDBMS_URL;
            //String userId = AppConstants.PA_RDBMS_USER;
            //String password = AppConstants.PA_RDBMS_PASSWORD;
            //String databaseUrl = "jdbc:postgresql://192.168.2.110/titan";
            //String userId = "titanappuser";
            //String password = "TITAN!7";
            // c = DriverManager.getConnection("jdbc:postgresql://192.168.2.110/titan", "titanappuser", "TITAN!7");

//			logger.debug("databaseUrl=" + databaseUrl);
//			logger.debug("userId=" + userId);
//			logger.debug("password=" + password);

            ds = new PGSimpleDataSource();
            ds.setServerName("localhost");

            //ds.setServerName("192.168.178.37");
            //ds.setServerName("10.250.1.2");
//			ds.setServerName("192.168.2.122");
            //ds.setDatabaseName("phobos");
            //ds.setUser("phobosappuser");
            //ds.setPassword("6phOBO5^");


//			ds.setDatabaseName("titan");
//			ds.setUser("titanappuser");
//			ds.setPassword("TITAN!7");

            ds.setDatabaseName("test");
            ds.setUser("testuser");
            ds.setPassword("TEST123");
            ds.setPortNumber(0);

//			ds.setDatabaseName("hyperion");
//			ds.setUser("hyperionappuser");
//			ds.setPassword("Hyper37!");
//			ds.setPortNumber(0);
            /*
            try {
				//ds.setURL(databaseUrl);

				//StringTokenizer tokenz = new StringTokenizer(databaseUrl, ":");
				//tokenz.nextToken(); // jdbc
				//tokenz.nextToken(); //oracle
				//ds.setDriverType(tokenz.nextToken());
				//ds.setServerName(tokenz.nextToken().substring(1));
				//ds.setPortNumber(Integer.parseInt(tokenz.nextToken()));
				//ds.setDatabaseName( tokenz.nextToken() );
				//ds.setUser(userId);
				//ds.setPassword(password);
			} catch (SQLException e) {
				throw new IllegalStateException("Cannot configure datasource."
						+" Url:" + databaseUrl
						+" userid:" + userId
						+" password:" + password
						+" Exception:" + e.getMessage());
			}
			*/
            return ds;
        }

        private PGSimpleDataSource setupDataSource(String serverName, String databaseName, String user, String password, Integer port) {
            PGSimpleDataSource ds;
            ds = new PGSimpleDataSource();
            ds.setServerName(serverName);
            ds.setDatabaseName(databaseName);
            ds.setUser(user);
            ds.setPassword(password);
            ds.setPortNumber(port.intValue());
            return ds;
        }

        public Connection getConnection() throws SQLException {
            Connection conn = null;
            conn = ds.getConnection();
            conn.setAutoCommit(true);
            return conn;
        }

        public PGSimpleDataSource getDataSource() {
            return ds;
        }

        public PGSimpleDataSource getNewDataSource() {
            return setupDataSource();
        }
    }

    // DAO Implementation getters
    public VOGeneratorDAO getVOGeneratorDAO() {
        return new PGVOGeneratorDAO(this);
    }

    private void getConnectionFromPool() throws SQLException {
        Connection con = null;
        long timeout = AppConstants.DB_POOL_GET_CONNECTION_TIMEOUT_MS;  // 3 second timeout
        con = pool.getConnection(timeout);
        if (con != null) {
            con.setAutoCommit(false);
            setConnection(con);
        } else {
            throw new RuntimeException("Timed out trying to retrieve connection from pool.");
        }
    }

    private static ConnectionPool pool = null;

    public static void initializeConnectionPool() throws TTAppException {
        try {
            Class c = Class.forName(AppConstants.DB_DRIVER_NAME);
            Driver driver = (Driver) c.newInstance();
            DriverManager.registerDriver(driver);
            pool = new ConnectionPool(AppConstants.DB_CONNECTION_POOL_NAME,
                    AppConstants.DB_POOL_MIN_CONNECTIONS_IN_POOL,
                    AppConstants.DB_POOL_MAX_CONNECTIONS_IN_POOL,
                    AppConstants.DB_POOL_MAX_POOL_SIZE,
                    AppConstants.DB_POOL_IDLE_CONNECTION_TIMEOUT_SECONDS,
                    AppConstants.DB_URL,
                    AppConstants.DB_USER_NAME,
                    AppConstants.DB_USER_PASS);
            pool.setCaching(AppConstants.DB_POOL_CACHE_STATEMENTS);
        } catch (ClassNotFoundException e) {
            logger.fatal(e.getMessage());
            throw new TTAppException("Cannot initialize connection pool: " + e.getMessage());
        } catch (InstantiationException e) {
            logger.fatal(e.getMessage());
            throw new TTAppException("Cannot initialize connection pool: " + e.getMessage());
        } catch (IllegalAccessException e) {
            logger.fatal(e.getMessage());
            throw new TTAppException("Cannot initialize connection pool: " + e.getMessage());
        } catch (SQLException e) {
            logger.fatal(e.getMessage());
            throw new TTAppException("Cannot initialize connection pool: " + e.getMessage());
        }
    }

    public static void deactivateConnectionPool() {
        if (pool != null) pool.release();
    }

    @Override
    public void setDBConnectionParameters(String serverName,
                                          String databaseName, String user, String password, Integer port) {
        setupConnections(serverName, databaseName, user, password, port);
    }
}


/*


        <!--Context className="org.apache.catalina.core.StandardContext" cachingAllowed="true" charsetMapperClass="org.apache.catalina.util.CharsetMapper" cookies="true" crossContext="false" debug="0" displayName="BuildingOutage" docBase="C:/Program Files/Apache Group/Tomcat 4.1/webapps/BuildingOutage" mapperClass="org.apache.catalina.core.StandardContextMapper" path="/BuildingOutage" privileged="false" reloadable="false" swallowOutput="false" useNaming="true" workDir="work\Standalone\localhost\BuildingOutage" wrapperClass="org.apache.catalina.core.StandardWrapper">
          <Environment description="Full path to the BOConfiguration.properties file, must include the file name." name="BOConfigurationPropertiesFileName" override="true" type="java.lang.String" value="C:/dev/BuildingOutageConsole/BOConfiguration.properties"/>
          <Resource auth="Container" description="Data source" name="jdbc/BuildingOutage" scope="Shareable" type="javax.sql.DataSource"/>
	    <ResourceParams name="jdbc/BuildingOutage">
	      <parameter>
	        <name>validationQuery</name>
	        <value>select sysdate from dual</value>
	      </parameter>
	      <parameter>
	        <name>maxWait</name>
	        <value>5000</value>
	      </parameter>
	      <parameter>
	        <name>maxActive</name>
	        <value>4</value>
	      </parameter>
	      <parameter>
	        <name>password</name>
	        <value>admin</value>
	      </parameter>
	      <parameter>
	        <name>url</name>
	        <value>jdbc:oracle:thin:@172.22.7.181:1521:OPS</value>
	      </parameter>
	      <parameter>
	        <name>driverClassName</name>
	        <value>oracle.jdbc.driver.OracleDriver</value>
	      </parameter>
	      <parameter>
	        <name>maxIdle</name>
	        <value>2</value>
	      </parameter>
	      <parameter>
	        <name>username</name>
	        <value>prismadmin</value>
	      </parameter>
	    </ResourceParams>
        </Context-->

*/