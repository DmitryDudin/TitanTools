package org.titantech.titantools.dao;


public abstract class DAOFactory implements IDatabase {
    // List of DAO types supported by the factory
    public static final int POSTGRES = 1;

    // Integration testing requires usage of
    // com.expressvu.ltm.integration.dao.MockORADAOFactory
    // instead of com.expressvu.ltm.integration.dao.ORADAOFactory
    public static boolean isTesting = false;
    public static Class mockORADAOFactoryClass = null;

    static {
        try {
            mockORADAOFactoryClass =
                    Class.forName("com.belltv.bo.dao.MockORADAOFactory");
            isTesting = true;
        } catch (ClassNotFoundException e) {
        }
    }

    public abstract void startTransaction() throws DAOSysException;

    public abstract void commitTransaction() throws DAOSysException;

    public abstract void rollbackTransaction() throws DAOSysException;

    public abstract boolean isInTransaction() throws DAOSysException;

    public abstract void resetTransaction() throws DAOSysException;

    public abstract void releaseConnections() throws DAOSysException;

    // There will be a method for each DAO that can be
    // created. The concrete factories will have to
    // implement these methods.
    public abstract VOGeneratorDAO getVOGeneratorDAO();

    public static DAOFactory getDAOFactory(int whichFactory) {
        // this is for integration testing with ORA DAOs only
        //if (isTesting && whichFactory==ORACLE) {
        //	//mockORADAOFactory
        //	return (ORADAOFactory)
        //		ClassAndObjectHelper.getObjectInstance(mockORADAOFactoryClass);
        //}

        // actual code
        switch (whichFactory) {
            case POSTGRES:
                return new PGDAOFactory();
            default:
                return null;
        }
    }

    public static DAOFactory getDAOFactory(int whichFactory, String serverName, String databaseName, String user, String password, Integer port) {
        // this is for integration testing with ORA DAOs only
        //if (isTesting && whichFactory==ORACLE) {
        //	//mockORADAOFactory
        //	return (ORADAOFactory)
        //		ClassAndObjectHelper.getObjectInstance(mockORADAOFactoryClass);
        //}

        // actual code
        switch (whichFactory) {
            case POSTGRES:
                DAOFactory factory = new PGDAOFactory();
                factory.setDBConnectionParameters(serverName, databaseName, user, password, port);
                return factory;
            default:
                return null;
        }
    }

}