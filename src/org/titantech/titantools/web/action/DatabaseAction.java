package org.titantech.titantools.web.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.titantech.titantools.TTAppException;
import org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean;
import org.titantech.titantools.dao.bean.TableBean;
import org.titantech.titantools.dao.bean.VOFieldToColumnMappingDetails;
import org.titantech.titantools.delegate.DatabaseMetadataDelegate;
import org.titantech.titantools.generator.GeneratorBase;
import org.titantech.titantools.generator.VODAOGenerator;
import org.titantech.titantools.util.FileUtil;
import org.titantech.titantools.web.form.DatabaseForm;

public class DatabaseAction extends Action {

    public static String OUTPUT_DIR = "/usr/local/tomcat/TitanTools";
    public static String SOURCE_OUTPUT_DIR = "/usr/local/tomcat/TitanTools/out";

    public ActionForward execute(ActionMapping actionMapping,
                                 ActionForm actionForm, HttpServletRequest servletRequest,
                                 HttpServletResponse servletResponse) {

        DatabaseForm databaseForm = (DatabaseForm) actionForm;
        GeneratorBase.JAVA_SRC_DIR = SOURCE_OUTPUT_DIR;

        mapTables(databaseForm);

//		if ("displayTables".equals(databaseForm.getActionName())) {
//			
//			}
        if ("refresh".equals(databaseForm.getActionName())) {
            if (databaseForm.getSelectedTable() != null) {
                int indexOfSelctedTable = Integer.parseInt(databaseForm.getSelectedTable());
                List listOfTableBeans = databaseForm.getListOfTableBeans();
                TableBean table = (TableBean) listOfTableBeans.get(indexOfSelctedTable);
                databaseForm.setSelectedTableName((String) table.getTableName());
            }
        }

        if ("generateForTable".equals(databaseForm.getActionName())) {


            String selectedTableClassName = databaseForm.getSelectedClassName();

            int indexOfSelctedTable = Integer.parseInt(databaseForm.getSelectedTable());
            List listOfTableBeans = databaseForm.getListOfTableBeans();
            TableBean table = (TableBean) listOfTableBeans.get(indexOfSelctedTable);
            String selectedTableName = (String) table.getTableName();

            List listColumnDetails = mapSPGIBToFieldDetails(table);

            VODAOGenerator gen = new VODAOGenerator();
            gen.setTableAndClassName(selectedTableName, selectedTableClassName);


            File dir = new File(SOURCE_OUTPUT_DIR);
            File dirDest = new File(SOURCE_OUTPUT_DIR + "." + System.currentTimeMillis());
            dir.renameTo(dirDest);
            dir = new File(SOURCE_OUTPUT_DIR);
            dir.mkdir();
            FileUtil.deleteDirectoryContents(SOURCE_OUTPUT_DIR + "/*");

            gen.generateVOAndDAO(listColumnDetails);

            String newFileName = "generated-source" + System.currentTimeMillis() + ".tar.gz";

            String newFilePath = OUTPUT_DIR + "/" + newFileName;
            FileUtil.compressDirectoryContents(newFilePath, GeneratorBase.JAVA_SRC_DIR);

            File f = new File(newFilePath);
            if (f.exists()) {
                databaseForm.setDownloadFileName(newFileName);
            }
        }
        if ("forwardToGenerator".equals(databaseForm.getActionName())) {
            return actionMapping.findForward("generator");
        }

        return actionMapping.findForward("success");
    }


    private List mapSPGIBToFieldDetails(TableBean table) {

        List listColumnDetails = new ArrayList<VOFieldToColumnMappingDetails>();
        List listOfSpgInputBeans = table.getSpgInputBeanList();
        for (Iterator iter = listOfSpgInputBeans.iterator(); iter.hasNext(); ) {
            SearchPageGeneratorInputBean tmpInputBean = (SearchPageGeneratorInputBean) iter.next();
            VOFieldToColumnMappingDetails tmpDetails = new VOFieldToColumnMappingDetails();

            tmpDetails.dbColumnName = tmpInputBean.getDbFieldName();
            tmpDetails.dbColumnType = tmpInputBean.getDbFieldType();
            tmpDetails.dbColumnTypeName = tmpInputBean.getDbFieldTypeStr();
            tmpDetails.isPK = tmpInputBean.isPrimary();
            tmpDetails.isSerial = tmpInputBean.isSerial();
            //TODO add more mapping (????)

            listColumnDetails.add(tmpDetails);
        }
        return listColumnDetails;
    }

    private void mapTables(DatabaseForm databaseForm) {
        DatabaseMetadataDelegate delegate = new DatabaseMetadataDelegate();
        try {
            Map dbMetadata = delegate.getDatabaseTablesMap(databaseForm.getServerName(),
                    databaseForm.getDatabaseName(), databaseForm.getDatabaseUser(),
                    databaseForm.getDatabasePassword(), databaseForm.getDatabasePort());

            databaseForm.setDbTableMap(dbMetadata);
            databaseForm.mapDatabaseMetadata(dbMetadata);
        } catch (TTAppException e) {
            e.printStackTrace(); // ????
        }
    }
}
