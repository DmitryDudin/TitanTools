package org.titantech.titantools.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.titantech.titantools.GESystemException;
import org.titantech.titantools.dao.DAOAppException;
import org.titantech.titantools.dao.DAOFactory;
import org.titantech.titantools.dao.DAOSysException;
import org.titantech.titantools.dao.VOGeneratorDAO;


public class VODAOGenerator extends GeneratorBase {

    public static void main(String[] args) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("/home/kirill/dev/TitanTools/INPUT/tables.txt")));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                String tableName = cols[0];
                String objectName = cols[1];
                GeneratorBase.DATABASE_TABLE_NAME = tableName;

                GeneratorBase.VO_JAVA_CLASS_NAME = objectName;
                GeneratorBase.DAO_JAVA_INTERFACE_NAME = objectName + "DAO";
                GeneratorBase.DAO_JAVA_CLASS_NAME = "PG" + objectName + "DAO";

                VODAOGenerator gen = new VODAOGenerator();
                gen.generateVOAndDAO(null);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTableAndClassName(String tableName, String className) {
        try {
            GeneratorBase.DATABASE_TABLE_NAME = tableName;
            GeneratorBase.VO_JAVA_CLASS_NAME = className;
            GeneratorBase.DAO_JAVA_INTERFACE_NAME = className + "DAO";
            GeneratorBase.DAO_JAVA_CLASS_NAME = "PG" + className + "DAO";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateVOAndDAO(List columns) {
        if (columns == null) {
            columns = getTableMetaData(VODAOGenerator.DATABASE_TABLE_NAME);
        }
        VOGenerator voGenerator = new VOGenerator();
        String voStr = voGenerator.generateVO4DAO(columns);
        GeneratorUtilities.writeJavaClass(GeneratorBase.JAVA_SRC_DIR,
                voStr, VO_JAVA_PACKAGE_NAME, VO_JAVA_CLASS_NAME);

        DAOInterfaceGenerator daoInterfaceGenerator = new DAOInterfaceGenerator();
        String daoInterfaceStr = daoInterfaceGenerator.generateDAOInterface();
        GeneratorUtilities.writeJavaClass(GeneratorBase.JAVA_SRC_DIR,
                daoInterfaceStr, DAO_JAVA_PACKAGE_NAME, DAO_JAVA_INTERFACE_NAME);

        DAOGenerator daoGenerator = new DAOGenerator();
        String daoClassStr = daoGenerator.generateDAOClass(columns);
        GeneratorUtilities.writeJavaClass(GeneratorBase.JAVA_SRC_DIR,
                daoClassStr, DAO_JAVA_PACKAGE_NAME, DAO_JAVA_CLASS_NAME);

        //DelegateGenerator delegateGenerator = new DelegateGenerator();
        //String delegateClassStr = delegateGenerator.generateDelegateClass();
        //GeneratorUtilities.writeJavaClass(delegateClassStr, DELEGATE_JAVA_PACKAGE_NAME, DELEGATE_JAVA_CLASS_NAME);
    }

    private List getTableMetaData(String tableName) {
        DAOFactory daoFactory = getDAOFactory();
        try {
            VOGeneratorDAO dao = daoFactory.getVOGeneratorDAO();
            return dao.getTableMetaData(tableName);
        } catch (DAOAppException e) {
            e.printStackTrace();
            throw new GESystemException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GESystemException(e.getMessage());
        } finally {
            finalizeDAOFactory(daoFactory);
        }
    }

    protected DAOFactory getDAOFactory() {
        return DAOFactory.getDAOFactory(DAOFactory.POSTGRES);
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
}