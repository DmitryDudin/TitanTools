package org.titantech.titantools.web.form;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean;

public class GeneratorForm extends ActionForm {
    private static final long serialVersionUID = 6556158254639716961L;

    private String actionName = null;
    private String downloadFileName = null;

    private String selectedTableName = null;

    private String baseName = "Expense Summary";

    private String attributes =
            "DBTableName=expense_summary\n" +
                    "DAOInterfaceName=ExpenseSummaryDAO\n" +
                    "DAOImplementationName=PGExpenseSummaryDAO\n" +
                    "DAOVOName=ExpenseSummary\n" +
                    "DelegateName=ExpenseSummaryDelegate\n" +
                    "FilterVOName=ExpenseSummaryFilter\n" +
                    "StrutsActionName=ManageExpenseSummaryAction\n" +
                    "SearchPageStrutsFormName=ManageExpenseSummaryForm\n" +
                    "FormBeanVOName=ExpenseSummaryVO\n" +
                    "SearchTableCaption=Expense Summary Search\n" +
                    "AddRecordButtonDisplayText=Add New Expense Summary\n" +
                    "AddSingleRecordAction=editExpenseSummary\n" +
                    "JSPFileNameNoExtension=manageExpenseSummaries\n" +
                    "EditJSPFileNameNoExtension=editExpenseSummary\n" +
                    "EditTableCaption=Expense Summary\n" +
                    "PermissionResourceConstantName=RESOURCE_ACCOUNTING_DATA_PAGE\n" +
                    "EditPageStrutsFormName=EditExpenseSummaryForm\n" +
                    "EditActionName=EditExpenseSummaryAction\n";
    private String fields =
            "field.1=record_id,NUMERIC,filterable:true,sortable:true,wildcards:true,range:false,javatype:Long,mandatory:false,primary:true,serial:false\n" +
                    "field.2=store_id,NUMERIC,filterable:false,sortable:false,wildcards:false,range:false,javatype:Integer,mandatory:false,primary:false,serial:false\n" +
                    "field.3=rent,NUMERIC,filterable:true,sortable:true,wildcards:false,range:true,javatype:BigDecimal,mandatory:false,primary:false,serial:false\n" +
                    "field.4=salary,NUMERIC,filterable:true,sortable:true,wildcards:false,range:true,javatype:BigDecimal,mandatory:false,primary:false,serial:false\n" +
                    "field.5=bonuses,NUMERIC,filterable:true,sortable:true,wildcards:false,range:true,javatype:BigDecimal,mandatory:false,primary:false,serial:false\n" +
                    "field.6=taxes_on_salary_bonuses,NUMERIC,filterable:true,sortable:true,wildcards:false,range:true,javatype:BigDecimal,mandatory:false,primary:false,serial:false\n" +
                    "field.7=utilities_and_other_costs,NUMERIC,filterable:true,sortable:true,wildcards:false,range:true,javatype:BigDecimal,mandatory:false,primary:false,serial:false\n" +
                    "field.8=total_cost,NUMERIC,filterable:true,sortable:true,wildcards:false,range:true,javatype:BigDecimal,mandatory:false,primary:false,serial:false\n" +
                    "field.9=from_date,TIMESTAMP,filterable:false,sortable:true,wildcards:false,range:true,javatype:Date,mandatory:false,primary:false,serial:false\n" +
                    "field.10=to_date,TIMESTAMP,filterable:false,sortable:true,wildcards:false,range:true,javatype:Date,mandatory:false,primary:false,serial:false\n" +
                    "field.11=create_date,TIMESTAMP,filterable:false,sortable:true,wildcards:false,range:true,javatype:Date,mandatory:false,primary:false,serial:false\n";
    private String text = attributes + fields;

    private String basePackageNamePrefix = "com";

    private List<SearchPageGeneratorInputBean> inputBeans = null;
    private int totalFields = 0;


    private List<LabelValueBean> dbFieldTypes = new ArrayList<LabelValueBean>();

    public void populateDBFields() {
        dbFieldTypes.clear();
        dbFieldTypes.add(new LabelValueBean("Text", "TEXT"));
        dbFieldTypes.add(new LabelValueBean("Numeric", "NUMERIC"));
        dbFieldTypes.add(new LabelValueBean("Timestamp", "TIMESTAMP"));
        dbFieldTypes.add(new LabelValueBean("VarChar", "VARCHAR"));
        dbFieldTypes.add(new LabelValueBean("Date", "DATE"));
        dbFieldTypes.add(new LabelValueBean("Integer", "INTEGER"));
        dbFieldTypes.add(new LabelValueBean("Character", "CHAR"));
        dbFieldTypes.add(new LabelValueBean("Time", "TIME"));
        dbFieldTypes.add(new LabelValueBean("BigInteger", "BIGINT"));
        dbFieldTypes.add(new LabelValueBean("Double", "DOUBLE"));
        dbFieldTypes.add(new LabelValueBean("Float", "FLOAT"));
        dbFieldTypes.add(new LabelValueBean("Decimal", "DECIMAL"));
        dbFieldTypes.add(new LabelValueBean("SmallInt", "SMALLINT"));
        dbFieldTypes.add(new LabelValueBean("TinyInt", "TINYINT"));
    }

    private List<LabelValueBean> javaFieldTypes = new ArrayList<LabelValueBean>();

    public void populateJavaFields() {
        javaFieldTypes.clear();
        javaFieldTypes.add(new LabelValueBean("String", "String"));
        javaFieldTypes.add(new LabelValueBean("Long", "Long"));
        javaFieldTypes.add(new LabelValueBean("Date", "Date"));
        javaFieldTypes.add(new LabelValueBean("Integer", "Integer"));
        javaFieldTypes.add(new LabelValueBean("BigDecimal", "BigDecimal"));
        javaFieldTypes.add(new LabelValueBean("Double", "Double"));
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ActionErrors errors = new ActionErrors();
        return errors;
    }

    public String getBasePackageNamePrefix() {
        return basePackageNamePrefix;
    }

    public void setBasePackageNamePrefix(String basePackageNamePrefix) {
        this.basePackageNamePrefix = basePackageNamePrefix;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDownloadFileName() {
        return downloadFileName;
    }

    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
        text = attributes + fields;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
        text = attributes + fields;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public List<SearchPageGeneratorInputBean> getInputBeans() {
        return inputBeans;
    }

    public void setInputBeans(List<SearchPageGeneratorInputBean> inputBeans) {
        this.inputBeans = inputBeans;
    }

    public List<LabelValueBean> getDbFieldTypes() {
        return dbFieldTypes;
    }

    public void setDbFieldTypes(List<LabelValueBean> dbFieldTypes) {
        this.dbFieldTypes = dbFieldTypes;
    }

    public int getTotalFields() {
        return totalFields;
    }

    public void setTotalFields(int totalFields) {
        this.totalFields = totalFields;
    }

    public List<LabelValueBean> getJavaFieldTypes() {
        return javaFieldTypes;
    }

    public void setJavaFieldTypes(List<LabelValueBean> javaFieldTypes) {
        this.javaFieldTypes = javaFieldTypes;
    }

}