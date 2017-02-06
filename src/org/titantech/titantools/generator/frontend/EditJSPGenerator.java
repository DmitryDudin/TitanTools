package org.titantech.titantools.generator.frontend;

import java.util.Iterator;
import java.util.List;

import org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean;
import org.titantech.titantools.generator.GeneratorBase;
import org.titantech.titantools.generator.GeneratorUtilities;

public class EditJSPGenerator extends GeneratorBase {


    public String generateStrutsJSP(String jspFileNameNoExtension,
                                    String strutsFormName, List inputBeans,
                                    String editTableCaption, String actionName,
                                    String permissionResourceName,
                                    String searchActionName) {
        //	String searchTableCaption = "Client Discount Card Search";
        //	String actionName = "a-c-t-i-o-n--n-a-m-e";
        //	String addRecordButtonValue = "Add New Record Button";
        //	String addSingleRecordAction = "editListRecord";
        StringBuffer contents = new StringBuffer();
        contents.append(generateJSPFileHeader(strutsFormName, permissionResourceName));
        contents.append(generateJavaScript());
        contents.append(generateTableCaptionScript(strutsFormName, inputBeans, editTableCaption));
        contents.append(generateStartBodyAndForm(inputBeans, lowerFirstChar(strutsFormName), actionName,
                jspFileNameNoExtension));

        contents.append(generateDataTable(inputBeans, editTableCaption, lowerFirstChar(strutsFormName), searchActionName, permissionResourceName));

        String str = contents.toString();
        System.out.println(str);

        StringBuffer strutsConfigXMLContents = new StringBuffer();
        strutsConfigXMLContents.append(generateStrutsConfigXMLContents(jspFileNameNoExtension,
                strutsFormName, actionName, searchActionName));
        GeneratorUtilities.writeTXTFile(GeneratorBase.JAVA_SRC_DIR, strutsConfigXMLContents.toString(), jspFileNameNoExtension + "-struts-config-content");
        System.out.println(strutsConfigXMLContents.toString());
        return str;
    }


    private StringBuffer generateTableCaptionScript(String formName, List inputBeans,
                                                    String editTableCaption) {
        Iterator iter = inputBeans.iterator();
        String keyGetterMethodName = null;
        if (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            keyGetterMethodName = getJavaGetNameFromDBFieldName(in.dbFieldName);
        }

        StringBuffer sb = new StringBuffer();
        sb.append("<%").append(NEW_LINE);
        sb.append("String sectionName = \"Edit ");
        sb.append(editTableCaption);
        sb.append("\";").append(NEW_LINE);
        sb.append("Object key = ")
                .append(lowerFirstChar(formName))
                .append(".")
                .append(keyGetterMethodName)
                .append("();").append(NEW_LINE);
        sb.append("String pageAction = \"Edit Existing\";").append(NEW_LINE);
        sb.append("if (key==null) {").append(NEW_LINE);
        sb.append("	pageAction = \"Add New\";").append(NEW_LINE);
        sb.append("}").append(NEW_LINE);
        sb.append("%>").append(NEW_LINE);

        return sb;
    }


    private StringBuffer generateJSPFileHeader(String strutsFormName, String permissionResourceName) {
        StringBuffer sb = new StringBuffer();
        sb.append("<%@ page contentType=\"text/html; charset=UTF-8\" pageEncoding=\"ISO-8859-1\" %>").append(NEW_LINE);
        sb.append("<%@taglib uri=\"/WEB-INF/struts-html.tld\" prefix=\"html\"%>").append(NEW_LINE);
        sb.append("<%@taglib uri=\"/WEB-INF/struts-bean.tld\" prefix=\"bean\"%>").append(NEW_LINE);
        sb.append("<%@taglib uri=\"/WEB-INF/struts-logic.tld\" prefix=\"logic\"%>").append(NEW_LINE);
        sb.append("<%@taglib uri=\"/WEB-INF/titan-console.tld\" prefix=\"tc\"%>").append(NEW_LINE);
        sb.append("<%@ page import=\"");
        sb.append(PACKAGE_NAME_PREFIX);
        sb.append(".util.PermissionValidator\"%>").append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append("<tc:permission resourceName=\"<%=PermissionValidator.")
                .append(permissionResourceName)
                .append("%>\" actions=\"!r|!c|!u|!d\">").append(NEW_LINE);
        sb.append("<jsp:forward page=\"accessDenied.jsp\" />").append(NEW_LINE);
        sb.append("</tc:permission>").append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append("<jsp:useBean id=\"");
        sb.append(lowerFirstChar(strutsFormName));
        sb.append("\" scope=\"request\" class=\"").append(STRUTS_FORM_JAVA_PACKAGE_NAME).append(PERIOD)
                .append(strutsFormName)
                .append("\" />").append(NEW_LINE);
        sb.append(NEW_LINE);

        sb.append("<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" />").append(NEW_LINE);
        sb.append("<script language=\"javascript1.2\" src=\"scripts/datepicker.js\" type=\"text/javascript\"></script>").append(NEW_LINE);
        sb.append(NEW_LINE);
        return sb;
    }

    private StringBuffer generateJavaScript() {
        StringBuffer sb = new StringBuffer();

        sb.append("<script language=\"javascript1.2\">").append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append("function doPageSubmit(strutsAction) {").append(NEW_LINE);
        sb.append("	document.forms[0].");
        sb.append(FIELD_NAME_ACTION_NAME);
        sb.append(".value = strutsAction;").append(NEW_LINE);
        sb.append("	//document.forms[0].nextFromPosition.value = nextFromPos;").append(NEW_LINE);
        sb.append("	document.forms[0].submit();").append(NEW_LINE);
        sb.append("}").append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append("</script>").append(NEW_LINE);
        return sb;
    }

    private StringBuffer generateStartBodyAndForm(List inputBeans, String strutsFormName, String actionName,
                                                  String jspFileNameNoExtension) {
        StringBuffer sb = new StringBuffer();

        sb.append("<body>").append(NEW_LINE);
        sb.append("<%@ include file=\"header.jsp\" %>").append(NEW_LINE);
        sb.append("<BR>").append(NEW_LINE);
        sb.append("<html:form action=\"")
                .append(lowerFirstChar(jspFileNameNoExtension))
                .append("\">").append(NEW_LINE);
        sb.append("<html:hidden property=\"");
        sb.append(FIELD_NAME_ACTION_NAME);
        sb.append("\" name=\"");
        sb.append(lowerFirstChar(strutsFormName));
        sb.append("\"/>").append(NEW_LINE);
        sb.append("<html:hidden property=\"");
        sb.append(FIELD_NAME_ACTION_TOKEN);
        sb.append("\" name=\"");
        sb.append(lowerFirstChar(strutsFormName));
        sb.append("\"/>").append(NEW_LINE);
        sb.append(NEW_LINE);
        return sb;
    }

    private StringBuffer generateDataTable(List inputBeans, String editTableCaption, String strutsFormName, String searchActionName, String permissionResourceName) {
        StringBuffer sb = new StringBuffer();


        sb.append(NEW_LINE);
        sb.append("<table border=\"0\" style=\"width:1000px\">").append(NEW_LINE);
        sb.append("<tr><td colspan=\"2\" align=\"center\"><EM><%=pageAction%> ")
                .append(editTableCaption)
                .append("</EM></td><td colspan=\"2\"></td></tr>").append(NEW_LINE);

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (!in.serial) {
                String displayName = getDisplayFieldNameFromDBFieldName(in.dbFieldName);
                String fieldName = getJavaFieldNameFromDBFieldName(in.dbFieldName);

                sb.append("<tr><td class=\"input-header\">")
                        .append(displayName)
                        .append("</td>")
                        .append("<td class=\"input-data\"><html:text   name=\"")
                        .append(strutsFormName)
                        .append("\"  property=\"")
                        .append(fieldName)
                        .append("\"    maxlength=\"100\" styleClass=\"long-text-field\" /></td><td></td><td>")
                        .append("<html:errors property=\"")
                        .append(fieldName)
                        .append("\"/></td></tr>").append(NEW_LINE);
            }
        }
        sb.append("</table>").append(NEW_LINE);

        sb.append("<BR>").append(NEW_LINE);
        sb.append("<BR>").append(NEW_LINE);
        sb.append("<table width=\"500\"><tr>");

        sb.append("<tc:permission resourceName=\"<%=PermissionValidator.")
                .append(permissionResourceName)
                .append("%>\" actions=\"c:u\">").append(NEW_LINE);

        sb.append("<td><input type=\"button\" onclick=\"doPageSubmit('SaveAndExit')\" name=\"subButton\" value=\"Save and Exit\"/></td>")
                .append("<td><input type=\"button\" onclick=\"doPageSubmit('SaveAndAddAnotherNew')\" name=\"subButton\" value=\"Save and Add Another ")
                .append(editTableCaption)
                .append("\"/></td>");

        sb.append("</tc:permission>").append(NEW_LINE);

        sb.append("<td><input type=\"button\" onclick=\"document.location='")
                .append(searchActionName)
                .append(".do?actionName=open'\" name=\"subButton\" value=\"Cancel\"/></td></tr></table>");

        sb.append(NEW_LINE);
        sb.append("</html:form>").append(NEW_LINE);
        sb.append("</body>").append(NEW_LINE);
        sb.append("</html>").append(NEW_LINE);
        return sb;
    }


    private StringBuffer generateStrutsConfigXMLContents(String jspFileNameNoExtension,
                                                         String strutsFormName, String actionName, String searchActionName) {
        String jspFileNameWithSuffix = jspFileNameNoExtension + PERIOD + JSP_FILE_EXTENSION;
        String formName = lowerFirstChar(strutsFormName);
        StringBuffer sb = new StringBuffer();
        sb.append("    <form-bean name=\"")
                .append(formName)
                .append("\" type=\"")
                .append(STRUTS_FORM_JAVA_PACKAGE_NAME)
                .append(PERIOD)
                .append(strutsFormName)
                .append("\" />")
                .append(NEW_LINE);

        sb.append("	    <action name=\"")
                .append(formName)
                .append("\"").append(NEW_LINE);
        sb.append("	    	type=\"")
                .append(STRUTS_ACTION_JAVA_PACKAGE_NAME)
                .append(PERIOD)
                .append(actionName)
                .append("\"").append(NEW_LINE);
        sb.append("	    	validate=\"false\"").append(NEW_LINE);
        sb.append("	    	scope=\"request\"").append(NEW_LINE);
        sb.append("	    	path=\"/")
                .append(jspFileNameNoExtension)
                .append("\"").append(NEW_LINE);
        sb.append("	    	input=\"/")
                .append(jspFileNameWithSuffix)
                .append("\">").append(NEW_LINE);
        sb.append("		<forward name=\"success\" path=\"/")
                .append(jspFileNameWithSuffix)
                .append("\" />").append(NEW_LINE);
        sb.append("		<forward name=\"error\" path=\"/applicationError.jsp\" />").append(NEW_LINE);
        sb.append("		<forward name=\"business-error\" path=\"/")
                .append(jspFileNameWithSuffix)
                .append("\" />").append(NEW_LINE);

        sb.append("	        <forward name=\"")
                .append(searchActionName)
                .append("\" path=\"/")
                .append(searchActionName)
                .append(".do?actionName=open\" />").append(NEW_LINE);

        sb.append("	    </action>").append(NEW_LINE);


        return sb;
    }

}