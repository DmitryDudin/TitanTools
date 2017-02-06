package org.titantech.titantools.web.action;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.omg.CORBA.Request;
import org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean;
import org.titantech.titantools.dao.bean.TableBean;
import org.titantech.titantools.generator.GeneratorBase;
import org.titantech.titantools.generator.frontend.SearchAndEditPagesGenerator;
import org.titantech.titantools.util.FileUtil;
import org.titantech.titantools.web.form.GeneratorForm;

public class GeneratorAction extends Action {

    private static Class CLAZZ = GeneratorAction.class;
    private static Logger logger = Logger.getLogger(CLAZZ);

    public static String OUTPUT_DIR = "/usr/local/tomcat/TitanTools";
    public static String SOURCE_OUTPUT_DIR = "/usr/local/tomcat/TitanTools/out";


    public ActionForward execute(ActionMapping actionMapping,
                                 ActionForm actionForm, HttpServletRequest servletRequest,
                                 HttpServletResponse servletResponse) {

        HttpSession session = servletRequest.getSession(false);
        String an = servletRequest.getParameter("actionName");
        GeneratorForm form = (GeneratorForm) actionForm;
        if (an == null || an.length() == 0) {
            an = form.getActionName();
        }

        GeneratorBase.JAVA_SRC_DIR = SOURCE_OUTPUT_DIR;

        ActionErrors actionErrors = form.validate(actionMapping, servletRequest);
        if (!actionErrors.isEmpty()) {
            this.saveErrors(servletRequest, actionErrors);
            return actionMapping.findForward("error");
        } else {
            try {
                SearchAndEditPagesGenerator spg = new SearchAndEditPagesGenerator();
                spg.parseFields(form.getFields());
                form.setInputBeans(spg.inputBeans);
                form.setTotalFields(spg.totalFields);
                form.populateDBFields();
                form.populateJavaFields();

                if ("generate".equals(form.getActionName())) {
                    if (form.getText() == null || form.getText().length() == 0) {
                        return actionMapping.findForward("success");
                    }
                    File dir = new File(SOURCE_OUTPUT_DIR);
                    File dirDest = new File(SOURCE_OUTPUT_DIR + "." + System.currentTimeMillis());
                    dir.renameTo(dirDest);
                    dir = new File(SOURCE_OUTPUT_DIR);
                    dir.mkdir();

                    FileUtil.deleteDirectoryContents(SOURCE_OUTPUT_DIR + "/*");

                    GeneratorBase.PACKAGE_NAME_PREFIX = form.getBasePackageNamePrefix();
                    GeneratorBase.reset();
                    GeneratorBase.resetImports();

                    //GeneratorBase.JAVA_SRC_DIR = "";
                    spg.generate(null, form.getText());

                    String newFileName = "generated-source" + System.currentTimeMillis() + ".tar.gz";

                    String newFilePath = OUTPUT_DIR + "/" + newFileName;
                    FileUtil.compressDirectoryContents(newFilePath, GeneratorBase.JAVA_SRC_DIR);

                    //FileUtil.createZipArchive(GeneratorBase.JAVA_SRC_DIR, newFileName);
                    File f = new File(newFilePath);
                    if (f.exists()) {
                        form.setDownloadFileName(newFileName);
                    }
                }

                if ("fillForm".equals(form.getActionName())) {
                    TableBean table = spg.getTableBean(servletRequest.getParameter("selectedTableName"),
                            servletRequest.getParameter("databaseSchema"));
                    List inputBeans = table.getSpgInputBeanList();

                    form.setInputBeans(inputBeans);
                    form.setTotalFields(inputBeans.size());
                    form.populateDBFields();
                    form.populateJavaFields();


                    StringBuilder sb = new StringBuilder();
                    int idx = 1;
                    for (Iterator iter = inputBeans.iterator(); iter.hasNext(); ) {
                        SearchPageGeneratorInputBean tmpBean = (SearchPageGeneratorInputBean) iter.next();
                        sb.append("field." + idx + "=" + tmpBean.getDbFieldName() + ",");
                        sb.append(tmpBean.getDbFieldTypeStr() + ",");
                        sb.append("filterable:" + String.valueOf(tmpBean.isFilterable()) + ",");
                        sb.append("sortable:" + String.valueOf(tmpBean.isSortable()) + ",");
                        sb.append("wildcards:" + String.valueOf(tmpBean.isPrimary()) + ",");
                        sb.append("range:" + String.valueOf(tmpBean.isRange()) + ",");
                        sb.append("javatype:" + "" + ","); //??
                        sb.append("mandatory:" + String.valueOf(tmpBean.isMandatory()) + ",");
                        sb.append("primary:" + String.valueOf(tmpBean.isPrimary()) + ",");
                        sb.append("serial:" + String.valueOf(tmpBean.isSerial()) + "\n");

                        idx++;
                    }
                    String fields = sb.toString();
                    form.setFields(fields);

                    form.setBaseName(servletRequest.getParameter("baseName"));

                    //temporary fix to "Update Table"
                    SearchAndEditPagesGenerator spg_2 = new SearchAndEditPagesGenerator();
                    spg_2.parseFields(form.getFields());
                    form.setInputBeans(spg_2.inputBeans);
                    form.setTotalFields(spg_2.totalFields);
                    form.populateDBFields();
                    form.populateJavaFields();

                }

            } catch (Exception e) {
                logger.error("GeneratorAction.execute error: " + e.getMessage());
                ActionErrors errors = new ActionErrors();
                //errors.add("editBank", new ActionError("error.editBank", "error.editBank"));
                this.saveErrors(servletRequest, errors);
                return actionMapping.findForward("error");
            }
        }

        return actionMapping.findForward("success");
    }
}