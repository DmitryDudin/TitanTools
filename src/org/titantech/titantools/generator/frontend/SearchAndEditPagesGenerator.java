package org.titantech.titantools.generator.frontend;

import org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean;
import org.titantech.titantools.dao.bean.TableBean;
import org.titantech.titantools.dao.bean.VOFieldToColumnMappingDetails;
import org.titantech.titantools.delegate.DatabaseMetadataDelegate;
import org.titantech.titantools.generator.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//SQL=select type_name, description from label_type
//total.fields=2
//field.1=type_name,varchar(100),filterable:true,sortable:true,wildcards:true
//field.2=description,varchar(100),filterable:true,sortable:true,wildcards:true

public class SearchAndEditPagesGenerator extends GeneratorBase {
    public static void main(String[] args) {
        SearchAndEditPagesGenerator spg = new SearchAndEditPagesGenerator();
        spg.generate(new File("/home/kirill/dev/TitanTools/INPUT/donor-input.txt"), null);
    }

    public List inputBeans = new ArrayList();
    public int totalFields = -1;

    public void parseFields(String inputFields) {
        try {
            BufferedReader br = null;
            br = new BufferedReader(new StringReader(inputFields));
            String line = null;
            int i = 0;
            while ((line = br.readLine()) != null) {
                int idx = line.indexOf("=");
                if (idx == -1 || line.length() < idx + 2) continue;
                String lineKey = line.substring(0, idx);
                String lineValue = line.substring(idx + 1);
                if (lineKey.startsWith("field.")) {
                    SearchPageGeneratorInputBean spgib = new SearchPageGeneratorInputBean();
                    String[] fields = line.split(",");
                    spgib.fieldId = ++i;
                    spgib.dbFieldName = fields[0].substring(lineKey.length() + 1);
                    setDbType(spgib, fields[1]);
                    spgib.filterable = (new Boolean(fields[2].substring(11))).booleanValue();
                    spgib.sortable = (new Boolean(fields[3].substring(9))).booleanValue();
                    spgib.wildcards = (new Boolean(fields[4].substring(10))).booleanValue();
                    spgib.range = (new Boolean(fields[5].substring(6))).booleanValue();
                    spgib.javaTypeName = fields[6].substring(9).trim();
                    spgib.mandatory = (new Boolean(fields[7].substring(10).trim())).booleanValue();
                    spgib.primary = (new Boolean(fields[8].substring(8).trim())).booleanValue();
                    spgib.serial = (new Boolean(fields[9].substring(7).trim())).booleanValue();
                    inputBeans.add(spgib);
                }
            }
            totalFields = i;
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TableBean getTableBean(String tableName, String databaseSchema) {
        DatabaseMetadataDelegate delegate = new DatabaseMetadataDelegate();
        TableBean table = new TableBean();
        table.setSpgInputBeanList(delegate.getInputBeanList(tableName, databaseSchema));

        return table;
    }

    public void generate(File file, String inputString) {
        inputBeans.clear();
        String dbTableName = null;
        String daoInterfaceName = null;
        String daoImplementationName = null;
        String daoVOName = null;
        String delegateName = null;
        String filterVOName = null;
        String strutsActionName = null;
        String searchPageStrutsFormName = null;
        String formBeanVOName = null;
        String jspName = null;
        String searchTableCaption = null;
        String addRecordButtonValue = null;
        String addSingleRecordAction = null;
        String jspFileNameNoExtension = null;
        String editJspFileNameNoExtension = null;
        String permissionResourceName = null;
        String editPageStrutsFormName = null;
        String editTableCaption = null;
        String editActionName = null;

        try {
//			BufferedReader br = new BufferedReader(new FileReader(new File("E:/dev/PostgreSQLTools/search-page-generator-input.txt")));
//			BufferedReader br = new BufferedReader(new FileReader(new File("E:/dev/PostgreSQLTools/label-type-page-generator-input.txt")));
//			BufferedReader br = new BufferedReader(new FileReader(new File("E:/dev/PostgreSQLTools/master-label-page-generator-input.txt")));
//			BufferedReader br = new BufferedReader(new FileReader(new File("E:/dev/PostgreSQLTools/master-product-page-generator-input.txt")));
//			BufferedReader br = new BufferedReader(new FileReader(new File("E:/dev/PostgreSQLTools/product-page-generator-input.txt")));

//			BufferedReader br = new BufferedReader(new FileReader(new File("E:/dev/PostgreSQLTools/bank-page-generator-input.txt")));
            //BufferedReader br = new BufferedReader(new FileReader(new File("E:/dev/PostgreSQLTools/supplier-page-generator-input.txt")));
            //BufferedReader br = new BufferedReader(new FileReader(new File("E:/dev/PostgreSQLTools/report-log-page-generator-input.txt")));
            //BufferedReader br = new BufferedReader(new FileReader(new File("E:/dev/PostgreSQLTools/subsupplier-page-generator-input.txt")));

            //BufferedReader br = new BufferedReader(new FileReader(new File("E:/dev/PostgreSQLTools/incoming-order-page-generator-input.txt")));
            //BufferedReader br = new BufferedReader(new FileReader(new File("E:/dev/PostgreSQLTools/incoming-order-item-page-generator-input.txt")));
            //BufferedReader br = new BufferedReader(new FileReader(new File("E:/dev/PostgreSQLTools/product-repricing-page-generator-input.txt")));
            //BufferedReader br = new BufferedReader(new FileReader(new File("E:/dev/PostgreSQLTools/product-repricing-item-page-generator-input.txt")));
            //BufferedReader br = new BufferedReader(new FileReader(new File("E:/dev/PostgreSQLTools/document-tracking-page-generator-input.txt")));

            //	BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/returning-order-page-generator-input.txt")));
            //	BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/returning-order-item-page-generator-input.txt")));


            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/review-control-page-generator-input.txt")));

            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/outofstore-incoming-order-page-generator-input.txt")));
            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/equipment-pos-page-generator-input.txt")));

            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/inventorization-page-generator-input.txt")));
            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/inventorization-item-page-generator-input.txt")));

            ///BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/writeoff-page-generator-input.txt")));
            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/writeoff-item-page-generator-input.txt")));

            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/movingin-order-page-generator-input.txt")));
            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/movingin-order-item-page-generator-input.txt")));
            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/movingout-order-page-generator-input.txt")));
            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/movingout-order-item-page-generator-input.txt")));

            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/acluser-page-generator-input.txt")));
            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/HYPERION/patient-page-generator-input.txt")));
            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/HYPERION/patient-image-file-generator-input.txt")));
            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/HYPERION/patient-image-annotations-generator-input.txt")));

            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/fixed-discount-rate-schedule.txt")));

            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/product-list.txt")));


            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/schedule-price-change.txt")));

            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/supplier-rule.txt")));

            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/organization-page-generator-input.txt")));

            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/store-page-generator-input.txt")));

            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/web-product-schedule.txt")));

            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/expense-summary.txt")));

            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/alert-settings.txt")));

            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/supplier-settings.txt")));

            //BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/mp-price-change-history-input.txt")));

//			BufferedReader br = new BufferedReader(new FileReader(new File("/home/roman/dev/TitanTools/INPUT/gift-card-page-generator-input.txt")));

            BufferedReader br = null;
            if (file != null) {
                br = new BufferedReader(new FileReader(file));
            } else if (inputString != null) {
                br = new BufferedReader(new StringReader(inputString));
            }

            String line = null;
            while ((line = br.readLine()) != null) {
                int idx = line.indexOf("=");
                if (idx == -1 || line.length() < idx + 2) continue;
                String lineKey = line.substring(0, idx);
                String lineValue = line.substring(idx + 1);
                System.out.println(lineKey + " : " + lineValue);
                if (lineKey.equals("DBTableName")) {
                    dbTableName = lineValue;
                } else if (lineKey.equals("DAOInterfaceName")) {
                    daoInterfaceName = lineValue;
                } else if (lineKey.equals("DAOImplementationName")) {
                    daoImplementationName = lineValue;
                } else if (lineKey.equals("DAOVOName")) {
                    daoVOName = lineValue;
                } else if (lineKey.equals("DelegateName")) {
                    delegateName = lineValue;
                } else if (lineKey.equals("FilterVOName")) {
                    filterVOName = lineValue;
                } else if (lineKey.equals("StrutsActionName")) {
                    strutsActionName = lineValue;
                } else if (lineKey.equals("SearchPageStrutsFormName")) {
                    searchPageStrutsFormName = lineValue;
                } else if (lineKey.equals("FormBeanVOName")) {
                    formBeanVOName = lineValue;
                } else if (lineKey.equals("SearchTableCaption")) {
                    searchTableCaption = lineValue;
                } else if (lineKey.equals("AddRecordButtonDisplayText")) {
                    addRecordButtonValue = lineValue;
                } else if (lineKey.equals("AddSingleRecordAction")) {
                    addSingleRecordAction = lineValue;
                } else if (lineKey.equals("JSPFileNameNoExtension")) {
                    jspFileNameNoExtension = lineValue;
                } else if (lineKey.equals("EditJSPFileNameNoExtension")) {
                    editJspFileNameNoExtension = lineValue;
                } else if (lineKey.equals("PermissionResourceConstantName")) {
                    permissionResourceName = lineValue;
                } else if (lineKey.equals("EditPageStrutsFormName")) {
                    editPageStrutsFormName = lineValue;
                } else if (lineKey.equals("EditTableCaption")) {
                    editTableCaption = lineValue;
                } else if (lineKey.equals("EditActionName")) {
                    editActionName = lineValue;
                } else if (lineKey.equals("total.fields")) {
                    totalFields = (new Integer(lineValue)).intValue();
                } else if (lineKey.startsWith("field.")) {
                    SearchPageGeneratorInputBean spgib = new SearchPageGeneratorInputBean();
                    String[] fields = line.split(",");
                    spgib.fieldId = (new Integer(lineKey.substring(6))).intValue();
                    spgib.dbFieldName = fields[0].substring(lineKey.length() + 1);
                    setDbType(spgib, fields[1]);
                    spgib.filterable = (new Boolean(fields[2].substring(11))).booleanValue();
                    spgib.sortable = (new Boolean(fields[3].substring(9))).booleanValue();
                    spgib.wildcards = (new Boolean(fields[4].substring(10))).booleanValue();
                    spgib.range = (new Boolean(fields[5].substring(6))).booleanValue();
                    spgib.javaTypeName = fields[6].substring(9).trim();
                    spgib.mandatory = (new Boolean(fields[7].substring(10).trim())).booleanValue();
                    spgib.primary = (new Boolean(fields[8].substring(8).trim())).booleanValue();
                    spgib.serial = (new Boolean(fields[9].substring(7).trim())).booleanValue();
                    inputBeans.add(spgib);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        List<VOFieldToColumnMappingDetails> dbColumns = new ArrayList();
        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            VOFieldToColumnMappingDetails details = new VOFieldToColumnMappingDetails();
            details.dbColumnName = in.dbFieldName;
            details.dbColumnType = in.dbFieldType;
            details.isPK = in.primary;
            details.isSerial = in.serial;
            dbColumns.add(details);
        }
        GeneratorBase.DATABASE_TABLE_NAME = dbTableName;
        GeneratorBase.VO_JAVA_CLASS_NAME = daoVOName;
        GeneratorBase.DAO_JAVA_INTERFACE_NAME = daoInterfaceName;
        GeneratorBase.DAO_JAVA_CLASS_NAME = daoImplementationName;

        VOGenerator voGenerator = new VOGenerator();
        String voStr = voGenerator.generateVO4DAO(dbColumns);
        GeneratorUtilities.writeJavaClass(GeneratorBase.JAVA_SRC_DIR,
                voStr, VO_JAVA_PACKAGE_NAME, VO_JAVA_CLASS_NAME);

        DAOInterfaceGenerator daoInterfaceGenerator = new DAOInterfaceGenerator();
        String daoInterfaceStr = daoInterfaceGenerator.generateDAOInterface(filterVOName, dbColumns);
        GeneratorUtilities.writeJavaClass(GeneratorBase.JAVA_SRC_DIR,
                daoInterfaceStr, DAO_JAVA_PACKAGE_NAME, DAO_JAVA_INTERFACE_NAME);

        DAOGenerator daoGenerator = new DAOGenerator();
        String daoClassStr = daoGenerator.generateDAOClass(dbColumns, inputBeans, filterVOName);
        GeneratorUtilities.writeJavaClass(GeneratorBase.JAVA_SRC_DIR,
                daoClassStr, DAO_JAVA_PACKAGE_NAME, DAO_JAVA_CLASS_NAME);

        FilterBeanGenerator fbGenerator = new FilterBeanGenerator();
        String fbClassString = fbGenerator.generateFilterClass(filterVOName, inputBeans, dbColumns);
        GeneratorUtilities.writeJavaClass(GeneratorBase.JAVA_SRC_DIR,
                fbClassString, FILTER_BEAN_JAVA_PACKAGE_NAME, filterVOName);

        SearchPageFormBeanVOGenerator fbVOGenerator = new SearchPageFormBeanVOGenerator();
        String fbVOString = fbVOGenerator.generateFormBeanVOClass(formBeanVOName, inputBeans, dbColumns);
        GeneratorUtilities.writeJavaClass(GeneratorBase.JAVA_SRC_DIR,
                fbVOString, WEB_BEAN_JAVA_PACKAGE_NAME, formBeanVOName);

        SearchPageStrutsFormGenerator spsfGenerator = new SearchPageStrutsFormGenerator();
        String spsfString = spsfGenerator.generateStrutsFormClass(searchPageStrutsFormName,
                daoVOName, formBeanVOName, filterVOName, inputBeans);
        GeneratorUtilities.writeJavaClass(GeneratorBase.JAVA_SRC_DIR,
                spsfString, STRUTS_FORM_JAVA_PACKAGE_NAME, searchPageStrutsFormName);


        JSPGenerator jspGenerator = new JSPGenerator();
        String jspString = jspGenerator.generateStrutsJSP(jspFileNameNoExtension,
                searchPageStrutsFormName, daoVOName, formBeanVOName, filterVOName, inputBeans,
                searchTableCaption, strutsActionName, addRecordButtonValue, addSingleRecordAction, permissionResourceName);
        GeneratorUtilities.writeJSPFile(GeneratorBase.JAVA_SRC_DIR,
                jspString, jspFileNameNoExtension);

        EditJSPGenerator ejspGenerator = new EditJSPGenerator();
        String ejspString = ejspGenerator.generateStrutsJSP(editJspFileNameNoExtension,
                editPageStrutsFormName, inputBeans, editTableCaption,
                editActionName, permissionResourceName, jspFileNameNoExtension);
        GeneratorUtilities.writeJSPFile(GeneratorBase.JAVA_SRC_DIR,
                ejspString, editJspFileNameNoExtension);


        EditPageStrutsFormGenerator epsfGenerator = new EditPageStrutsFormGenerator();
        String epsfString = epsfGenerator.generateStrutsFormClass(editPageStrutsFormName, daoVOName, inputBeans);
        GeneratorUtilities.writeJavaClass(GeneratorBase.JAVA_SRC_DIR,
                epsfString, STRUTS_FORM_JAVA_PACKAGE_NAME, editPageStrutsFormName);


        EditPageStrutsActionGenerator epsaGenerator = new EditPageStrutsActionGenerator();
        String epsaString = epsaGenerator.generateStrutsFormClass(editActionName, editPageStrutsFormName, delegateName, daoVOName, jspFileNameNoExtension, permissionResourceName, dbColumns);
        GeneratorUtilities.writeJavaClass(GeneratorBase.JAVA_SRC_DIR,
                epsaString, STRUTS_ACTION_JAVA_PACKAGE_NAME, editActionName);

        SearchPageStrutsActionGenerator spsaGenerator = new SearchPageStrutsActionGenerator();
        String spsaString = spsaGenerator.generateStrutsFormClass(strutsActionName, searchPageStrutsFormName, delegateName,
                daoVOName, jspFileNameNoExtension, addSingleRecordAction, permissionResourceName);
        GeneratorUtilities.writeJavaClass(GeneratorBase.JAVA_SRC_DIR,
                spsaString, STRUTS_ACTION_JAVA_PACKAGE_NAME, strutsActionName);

        DelegateGenerator delegateGenerator = new DelegateGenerator();
        String delegateString = delegateGenerator.generateDelegateClass(delegateName, daoVOName, daoInterfaceName, filterVOName, dbColumns);
        GeneratorUtilities.writeJavaClass(GeneratorBase.JAVA_SRC_DIR,
                delegateString, DELEGATE_JAVA_PACKAGE_NAME, delegateName);

    }

    private void setDbType(SearchPageGeneratorInputBean spgib, String str) {
        str = str.trim();
        spgib.dbFieldTypeStr = str;
        int idx1 = str.indexOf("(");
        int idx2 = str.indexOf(")");
        String lengthStr = null;
        String typeStr = str;
        if (idx1 > -1 && idx2 > -1 && idx2 > idx1) {
            lengthStr = str.substring(idx1 + 1, idx2).trim();
            typeStr = typeStr.substring(0, idx1).trim();
            int idx3 = lengthStr.indexOf(".");
            if (idx3 == -1) {
                spgib.dbFieldLength = (new Integer(lengthStr)).intValue();
            } else {
                // numeric decimal of some sort
                spgib.dbFieldLength = (new Integer(lengthStr.substring(0, idx3))).intValue();
            }
        }
        spgib.dbFieldType = getSQLType(typeStr);
    }

    public String generatePreview(String inputString) {
        StringBuffer previewFiles = new StringBuffer();

        inputBeans.clear();
        String dbTableName = null;
        String daoInterfaceName = null;
        String daoImplementationName = null;
        String daoVOName = null;
        String delegateName = null;
        String filterVOName = null;
        String strutsActionName = null;
        String searchPageStrutsFormName = null;
        String formBeanVOName = null;
        String jspName = null;
        String searchTableCaption = null;
        String addRecordButtonValue = null;
        String addSingleRecordAction = null;
        String jspFileNameNoExtension = null;
        String editJspFileNameNoExtension = null;
        String permissionResourceName = null;
        String editPageStrutsFormName = null;
        String editTableCaption = null;
        String editActionName = null;

        try {
            BufferedReader br = null;
            if (inputString != null) {
                br = new BufferedReader(new StringReader(inputString));
            }

            String line = null;
            while ((line = br.readLine()) != null) {
                int idx = line.indexOf("=");
                if (idx == -1 || line.length() < idx + 2) continue;
                String lineKey = line.substring(0, idx);
                String lineValue = line.substring(idx + 1);
                System.out.println(lineKey + " : " + lineValue);
                if (lineKey.equals("DBTableName")) {
                    dbTableName = lineValue;
                } else if (lineKey.equals("DAOInterfaceName")) {
                    daoInterfaceName = lineValue;
                } else if (lineKey.equals("DAOImplementationName")) {
                    daoImplementationName = lineValue;
                } else if (lineKey.equals("DAOVOName")) {
                    daoVOName = lineValue;
                } else if (lineKey.equals("DelegateName")) {
                    delegateName = lineValue;
                } else if (lineKey.equals("FilterVOName")) {
                    filterVOName = lineValue;
                } else if (lineKey.equals("StrutsActionName")) {
                    strutsActionName = lineValue;
                } else if (lineKey.equals("SearchPageStrutsFormName")) {
                    searchPageStrutsFormName = lineValue;
                } else if (lineKey.equals("FormBeanVOName")) {
                    formBeanVOName = lineValue;
                } else if (lineKey.equals("SearchTableCaption")) {
                    searchTableCaption = lineValue;
                } else if (lineKey.equals("AddRecordButtonDisplayText")) {
                    addRecordButtonValue = lineValue;
                } else if (lineKey.equals("AddSingleRecordAction")) {
                    addSingleRecordAction = lineValue;
                } else if (lineKey.equals("JSPFileNameNoExtension")) {
                    jspFileNameNoExtension = lineValue;
                } else if (lineKey.equals("EditJSPFileNameNoExtension")) {
                    editJspFileNameNoExtension = lineValue;
                } else if (lineKey.equals("PermissionResourceConstantName")) {
                    permissionResourceName = lineValue;
                } else if (lineKey.equals("EditPageStrutsFormName")) {
                    editPageStrutsFormName = lineValue;
                } else if (lineKey.equals("EditTableCaption")) {
                    editTableCaption = lineValue;
                } else if (lineKey.equals("EditActionName")) {
                    editActionName = lineValue;
                } else if (lineKey.equals("total.fields")) {
                    totalFields = (new Integer(lineValue)).intValue();
                } else if (lineKey.startsWith("field.")) {
                    SearchPageGeneratorInputBean spgib = new SearchPageGeneratorInputBean();
                    String[] fields = line.split(",");
                    spgib.fieldId = (new Integer(lineKey.substring(6))).intValue();
                    spgib.dbFieldName = fields[0].substring(lineKey.length() + 1);
                    setDbType(spgib, fields[1]);
                    spgib.filterable = (new Boolean(fields[2].substring(11))).booleanValue();
                    spgib.sortable = (new Boolean(fields[3].substring(9))).booleanValue();
                    spgib.wildcards = (new Boolean(fields[4].substring(10))).booleanValue();
                    spgib.range = (new Boolean(fields[5].substring(6))).booleanValue();
                    spgib.javaTypeName = fields[6].substring(9).trim();
                    spgib.mandatory = (new Boolean(fields[7].substring(10).trim())).booleanValue();
                    spgib.primary = (new Boolean(fields[8].substring(8).trim())).booleanValue();
                    spgib.serial = (new Boolean(fields[9].substring(7).trim())).booleanValue();
                    inputBeans.add(spgib);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        List<VOFieldToColumnMappingDetails> dbColumns = new ArrayList();
        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            VOFieldToColumnMappingDetails details = new VOFieldToColumnMappingDetails();
            details.dbColumnName = in.dbFieldName;
            details.dbColumnType = in.dbFieldType;
            details.isPK = in.primary;
            details.isSerial = in.serial;
            dbColumns.add(details);
        }
        GeneratorBase.DATABASE_TABLE_NAME = dbTableName;
        GeneratorBase.VO_JAVA_CLASS_NAME = daoVOName;
        GeneratorBase.DAO_JAVA_INTERFACE_NAME = daoInterfaceName;
        GeneratorBase.DAO_JAVA_CLASS_NAME = daoImplementationName;

        VOGenerator voGenerator = new VOGenerator();
        String voStr = voGenerator.generateVO4DAO(dbColumns);
        String javaClassName = VO_JAVA_CLASS_NAME + GeneratorBase.PERIOD
                + GeneratorBase.JAVA_CLASS_EXTENSION + GeneratorBase.DBL_NEW_LINE;
        previewFiles.append(javaClassName)
                .append(voStr)
                .append(GeneratorBase.PREVIEW_FILE_DELIMITER);

        DAOInterfaceGenerator daoInterfaceGenerator = new DAOInterfaceGenerator();
        String daoInterfaceStr = daoInterfaceGenerator.generateDAOInterface(filterVOName, dbColumns);
        String daoJavaInterfaceName = DAO_JAVA_INTERFACE_NAME + GeneratorBase.PERIOD +
                GeneratorBase.JAVA_CLASS_EXTENSION + GeneratorBase.DBL_NEW_LINE;
        previewFiles.append(daoJavaInterfaceName)
                .append(daoInterfaceStr)
                .append(GeneratorBase.PREVIEW_FILE_DELIMITER);

        DAOGenerator daoGenerator = new DAOGenerator();
        String daoClassStr = daoGenerator.generateDAOClass(dbColumns, inputBeans, filterVOName);
        String daoJavaClassName = DAO_JAVA_CLASS_NAME + GeneratorBase.PERIOD +
                GeneratorBase.JAVA_CLASS_EXTENSION + GeneratorBase.DBL_NEW_LINE;
        previewFiles.append(daoJavaClassName)
                .append(daoClassStr)
                .append(GeneratorBase.PREVIEW_FILE_DELIMITER);


        FilterBeanGenerator fbGenerator = new FilterBeanGenerator();
        String fbClassString = fbGenerator.generateFilterClass(filterVOName, inputBeans, dbColumns);
        String javaFilterVOName = filterVOName + GeneratorBase.PERIOD +
                GeneratorBase.JAVA_CLASS_EXTENSION + GeneratorBase.DBL_NEW_LINE;
        previewFiles.append(javaFilterVOName)
                .append(fbClassString)
                .append(GeneratorBase.PREVIEW_FILE_DELIMITER);

        SearchPageFormBeanVOGenerator fbVOGenerator = new SearchPageFormBeanVOGenerator();
        String fbVOString = fbVOGenerator.generateFormBeanVOClass(formBeanVOName, inputBeans, dbColumns);
        String javaFormBeanVOName = formBeanVOName + GeneratorBase.PERIOD +
                GeneratorBase.JAVA_CLASS_EXTENSION + GeneratorBase.DBL_NEW_LINE;
        previewFiles.append(javaFormBeanVOName)
                .append(fbVOString)
                .append(GeneratorBase.PREVIEW_FILE_DELIMITER);

        SearchPageStrutsFormGenerator spsfGenerator = new SearchPageStrutsFormGenerator();
        String spsfString = spsfGenerator.generateStrutsFormClass(searchPageStrutsFormName,
                daoVOName, formBeanVOName, filterVOName, inputBeans);
        String javaSearchPageStrutsFormName = searchPageStrutsFormName + GeneratorBase.PERIOD +
                GeneratorBase.JAVA_CLASS_EXTENSION + GeneratorBase.DBL_NEW_LINE;
        previewFiles.append(javaSearchPageStrutsFormName)
                .append(spsfString)
                .append(GeneratorBase.PREVIEW_FILE_DELIMITER);

        JSPGenerator jspGenerator = new JSPGenerator();
        String jspString = jspGenerator.generateStrutsJSP(jspFileNameNoExtension,
                searchPageStrutsFormName, daoVOName, formBeanVOName, filterVOName, inputBeans,
                searchTableCaption, strutsActionName, addRecordButtonValue,
                addSingleRecordAction, permissionResourceName);
        String fullJspFileNameNoExtension = jspFileNameNoExtension + GeneratorBase.PERIOD +
                GeneratorBase.JSP_FILE_EXTENSION + GeneratorBase.DBL_NEW_LINE;
        previewFiles.append(fullJspFileNameNoExtension)
                .append(jspString)
                .append(GeneratorBase.PREVIEW_FILE_DELIMITER);

        EditJSPGenerator ejspGenerator = new EditJSPGenerator();
        String ejspString = ejspGenerator.generateStrutsJSP(editJspFileNameNoExtension,
                editPageStrutsFormName, inputBeans, editTableCaption,
                editActionName, permissionResourceName, jspFileNameNoExtension);
        String fullEditJspFileNameNoExtension = editJspFileNameNoExtension + GeneratorBase.PERIOD +
                GeneratorBase.JSP_FILE_EXTENSION + GeneratorBase.DBL_NEW_LINE;
        previewFiles.append(fullEditJspFileNameNoExtension)
                .append(ejspString)
                .append(GeneratorBase.PREVIEW_FILE_DELIMITER);

        EditPageStrutsFormGenerator epsfGenerator = new EditPageStrutsFormGenerator();
        String epsfString = epsfGenerator.generateStrutsFormClass(editPageStrutsFormName, daoVOName, inputBeans);
        String javaClassEditPageStrutsFormName = editPageStrutsFormName + GeneratorBase.PERIOD +
                GeneratorBase.JAVA_CLASS_EXTENSION + GeneratorBase.DBL_NEW_LINE;
        previewFiles.append(javaClassEditPageStrutsFormName)
                .append(epsfString)
                .append(GeneratorBase.PREVIEW_FILE_DELIMITER);

        EditPageStrutsActionGenerator epsaGenerator = new EditPageStrutsActionGenerator();
        String epsaString = epsaGenerator.generateStrutsFormClass(editActionName, editPageStrutsFormName,
                delegateName, daoVOName, jspFileNameNoExtension, permissionResourceName, dbColumns);
        String javaClassEditActionName = editActionName + GeneratorBase.PERIOD +
                GeneratorBase.JAVA_CLASS_EXTENSION + GeneratorBase.DBL_NEW_LINE;
        previewFiles.append(javaClassEditActionName)
                .append(epsaString)
                .append(GeneratorBase.PREVIEW_FILE_DELIMITER);

        SearchPageStrutsActionGenerator spsaGenerator = new SearchPageStrutsActionGenerator();
        String spsaString = spsaGenerator.generateStrutsFormClass(strutsActionName, searchPageStrutsFormName, delegateName,
                daoVOName, jspFileNameNoExtension, addSingleRecordAction, permissionResourceName);
        String javaClassStrutsActionName = strutsActionName + GeneratorBase.PERIOD +
                GeneratorBase.JAVA_CLASS_EXTENSION + GeneratorBase.DBL_NEW_LINE;
        previewFiles.append(javaClassStrutsActionName)
                .append(spsaString)
                .append(GeneratorBase.PREVIEW_FILE_DELIMITER);

        DelegateGenerator delegateGenerator = new DelegateGenerator();
        String delegateString = delegateGenerator.generateDelegateClass(delegateName, daoVOName, daoInterfaceName,
                filterVOName, dbColumns);
        String javaClassDelegateName = delegateName + GeneratorBase.PERIOD +
                GeneratorBase.JAVA_CLASS_EXTENSION + GeneratorBase.DBL_NEW_LINE;
        previewFiles.append(javaClassDelegateName)
                .append(delegateString)
                .append(GeneratorBase.PREVIEW_FILE_DELIMITER);

        return previewFiles.toString();
    }
}