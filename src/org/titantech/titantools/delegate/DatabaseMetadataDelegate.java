package org.titantech.titantools.delegate;

import org.apache.log4j.Logger;
import org.titantech.titantools.GESystemException;
import org.titantech.titantools.TTAppException;
import org.titantech.titantools.dao.DAOFactory;
import org.titantech.titantools.dao.DAOSysException;
import org.titantech.titantools.dao.VOGeneratorDAO;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DatabaseMetadataDelegate {
    private static Class CLAZZ = DatabaseMetadataDelegate.class;
    private static Logger logger = Logger.getLogger(CLAZZ);

    private static String serverName = null;
    private static String databaseName = null;
    private static String user = null;
    private static String password = null;
    private static Integer port = 0;
    private String databaseSchema;

    public Map getDatabaseTablesMap(String serverName, String databaseName, String user,
                                    String password, Integer port, String databaseSchema)
            throws TTAppException {

        setDbInfo(serverName, databaseName, user, password, port, databaseSchema);

        DAOFactory daoFactory = getDAOFactory(serverName, databaseName, user, password, port);
        try {
            VOGeneratorDAO dao = daoFactory.getVOGeneratorDAO();

            Map dbTableMetadataByTableName = new HashMap();

            List dbTableNames = dao.getDatabaseTables(databaseSchema);
            Iterator iter = dbTableNames.iterator();
            while (iter.hasNext()) {
                String tableName = (String) iter.next();
                List spgInputBeanList = dao.getListOfSPGInputBeans(tableName, databaseSchema);
                dbTableMetadataByTableName.put(tableName, spgInputBeanList);
            }

            return dbTableMetadataByTableName;
            //} catch (DAOAppException e) {
            //	e.printStackTrace();
            //	throw new GESystemException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GESystemException(e.getMessage());
        } finally {
            finalizeDAOFactory(daoFactory);
        }
    }

    private void setDbInfo(String serverName, String databaseName,
                           String user, String password, Integer port, String databaseSchema) {
        this.serverName = serverName;
        this.databaseName = databaseName;
        this.user = user;
        this.password = password;
        this.port = port;
        this.databaseSchema = databaseSchema;
    }


    protected DAOFactory getDAOFactory() {
        return DAOFactory.getDAOFactory(DAOFactory.POSTGRES);
    }

    protected DAOFactory getDAOFactory(String serverName, String databaseName,
                                       String user, String password, Integer port) {
        return DAOFactory.getDAOFactory(DAOFactory.POSTGRES, serverName, databaseName, user, password, port);
    }

    protected void finalizeDAOFactory(DAOFactory daoFactory) {
        if (daoFactory != null) {
            try {
                daoFactory.releaseConnections();
            } catch (DAOSysException e) {
                e.printStackTrace();
                throw new GESystemException(e.getMessage());
            }
        }
    }


    public List getInputBeanList(String tableName, String databaseSchema) {

        DAOFactory daoFactory = getDAOFactory(serverName, databaseName, user, password, port);

        try {
            VOGeneratorDAO dao = daoFactory.getVOGeneratorDAO();
            List inputBeanList = dao.getListOfSPGInputBeans(tableName, databaseSchema);

            return inputBeanList;

            //} catch (DAOAppException e) {
            //	e.printStackTrace();
            //	throw new GESystemException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GESystemException(e.getMessage());
        } finally {
            finalizeDAOFactory(daoFactory);
        }
    }

}