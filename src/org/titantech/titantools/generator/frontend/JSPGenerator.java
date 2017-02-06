package org.titantech.titantools.generator.frontend;

import java.util.Iterator;
import java.util.List;

import org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean;
import org.titantech.titantools.generator.GeneratorBase;
import org.titantech.titantools.generator.GeneratorUtilities;

public class JSPGenerator extends GeneratorBase {

    public String generateStrutsJSP(String jspFileNameNoExtension,
                                    String strutsFormName, String daoVOName, String formBeanVOName, String filterVOName, List inputBeans,
                                    String searchTableCaption, String actionName, String addRecordButtonValue, String addSingleRecordAction,
                                    String permissionResourceName) {
        //	String searchTableCaption = "Client Discount Card Search";
        //	String actionName = "a-c-t-i-o-n--n-a-m-e";
        String addRecordButtonName = "addNewRecordButton";
        //	String addRecordButtonValue = "Add New Record Button";
        //	String addSingleRecordAction = "editListRecord";
        StringBuffer contents = new StringBuffer();
        contents.append(generateJSPFileHeader(strutsFormName, searchTableCaption, permissionResourceName));
        contents.append(generateFilterJavaScript(inputBeans));
        contents.append(generateStartBodyAndForm(inputBeans, lowerFirstChar(strutsFormName), actionName,
                jspFileNameNoExtension));
        contents.append(generateDataTable(inputBeans, lowerFirstChar(strutsFormName),
                formBeanVOName, addRecordButtonName, addRecordButtonValue, addSingleRecordAction, permissionResourceName));
        String str = contents.toString();
        System.out.println(str);

        StringBuffer strutsConfigXMLContents = new StringBuffer();

        strutsConfigXMLContents.append(generateStrutsConfigXMLContents(jspFileNameNoExtension,
                strutsFormName, actionName, addSingleRecordAction));
        GeneratorUtilities.writeTXTFile(GeneratorBase.JAVA_SRC_DIR, strutsConfigXMLContents.toString(), jspFileNameNoExtension + "-struts-config-content");
        System.out.println(strutsConfigXMLContents.toString());
        return str;
    }

    private StringBuffer generateJSPFileHeader(String strutsFormName, String searchTableCaption, String permissionResourceName) {
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
                .append("%>\" actions=\"!read\">").append(NEW_LINE);
        sb.append("<jsp:forward page=\"accessDenied.jsp\" />").append(NEW_LINE);
        sb.append("</tc:permission>").append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append("<jsp:useBean id=\"");
        sb.append(lowerFirstChar(strutsFormName));
        sb.append("\" scope=\"request\" class=\"").append(STRUTS_FORM_JAVA_PACKAGE_NAME).append(PERIOD)
                .append(strutsFormName)
                .append("\" />").append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append("<%").append(NEW_LINE);
        sb.append("	String sectionName = \"")
                .append(searchTableCaption)
                .append("\";").append(NEW_LINE);
        sb.append("%>").append(NEW_LINE);
        sb.append(NEW_LINE);

        sb.append("<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" />").append(NEW_LINE);
        sb.append("<link href=\"css/filtering-ordering.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" />").append(NEW_LINE);
        sb.append("<script language=\"javascript1.2\" src=\"scripts/datepicker.js\" type=\"text/javascript\"></script>").append(NEW_LINE);
        sb.append("<script language=\"javascript1.2\" src=\"scripts/filtering-ordering-one-at-a-time.js\" type=\"text/javascript\"></script>").append(NEW_LINE);
        sb.append(NEW_LINE);
        return sb;
    }

    private StringBuffer generateFilterJavaScript(List inputBeans) {
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

        int sortElementsCount = 0;
        int filterElementCount = 0;
        StringBuffer sbSortElementsMapSB = new StringBuffer();
        StringBuffer sbAnchorElementsMapSB = new StringBuffer();
        StringBuffer sbFilterElementsMapSB = new StringBuffer();
        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (in.sortable) {
                String name = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_SUFFIX_ORDER;
                String displayName = getDisplayFieldNameFromDBFieldName(in.dbFieldName);
                String anchorName = name + "AnchorID";

                sbSortElementsMapSB.append("sortElementsMap[\"").append(name).append("\"]=\"").append(displayName).append("\";").append(NEW_LINE);

                sbAnchorElementsMapSB.append("sortAnchorMap[").append(sortElementsCount).append("]=\"").append(anchorName).append("\";").append(NEW_LINE);

                sortElementsCount++;
            }
            if (in.filterable) {
                if (in.range) {
                    String name = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_RANGE_SUFFIX_FROM;
                    sbFilterElementsMapSB.append("filterElementNameMap[").append(filterElementCount).append("]=\"").append(name).append("\";").append(NEW_LINE);
                    filterElementCount++;
                    name = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_RANGE_SUFFIX_TO;
                    sbFilterElementsMapSB.append("filterElementNameMap[").append(filterElementCount).append("]=\"").append(name).append("\";").append(NEW_LINE);
                } else {
                    String name = getJavaFieldNameFromDBFieldName(in.dbFieldName);
                    sbFilterElementsMapSB.append("filterElementNameMap[").append(filterElementCount).append("]=\"").append(name).append("\";").append(NEW_LINE);
                }
                filterElementCount++;
            }
        }
        sb.append("sortElements=").append(sortElementsCount).append(";").append(NEW_LINE);
        sb.append("sortElementsMap = new Array(sortElements);").append(NEW_LINE);
        if (sortElementsCount > 0) {
            sb.append(sbSortElementsMapSB);
        }
        sb.append(NEW_LINE);
        sb.append("sortAnchorMap = new Array(sortElements);").append(NEW_LINE);
        if (sortElementsCount > 0) {
            sb.append(sbAnchorElementsMapSB);
        }

        sb.append(NEW_LINE);
        sb.append("filterElementNameMap = new Array();").append(NEW_LINE);
        if (filterElementCount > 0) {
            sb.append(sbFilterElementsMapSB);
        }
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
        sb.append(NEW_LINE);
        sb.append("<!-- ordering start -->").append(NEW_LINE);
        sb.append("<html:hidden property=\"");
        sb.append(PARAM_NAME_ORDERINGOFORDERELEMENTS);
        sb.append("\" name=\"");
        sb.append(lowerFirstChar(strutsFormName));
        sb.append("\"/>").append(NEW_LINE);
        sb.append(NEW_LINE);

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (in.sortable) {
                String name = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_SUFFIX_ORDER;

                sb.append("<html:hidden property=\"")
                        .append(name).append("\" name=\"");
                sb.append(lowerFirstChar(strutsFormName));
                sb.append("\"/>").append(NEW_LINE);
            }
        }
        sb.append("<!-- ordering end -->").append(NEW_LINE);
        sb.append(NEW_LINE);
        return sb;
    }

    private StringBuffer generateDataTable(List inputBeans, String strutsFormName, String formBeanVOName,
                                           String addRecordButtonName, String addRecordButtonValue, String editSingleRecordAction, String permissionResourceName) {
        StringBuffer sb = new StringBuffer();

        sb.append("<html:errors/>").append(NEW_LINE);
        sb.append(NEW_LINE);
        sb.append("<table border=\"1\" style=\"width:1800px\">").append(NEW_LINE);
        sb.append("<caption><EM><%=sectionName%></EM></caption>").append(NEW_LINE);
        sb.append("	<thead>").append(NEW_LINE);
        sb.append("		<tr>").append(NEW_LINE);
        sb.append("			<td class=\"header\">Row</td>").append(NEW_LINE);

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            String displayName = getDisplayFieldNameFromDBFieldName(in.dbFieldName);
            sb.append("			<td class=\"header\">").append(displayName).append("</td>").append(NEW_LINE);
        }
        sb.append("		</tr>").append(NEW_LINE);
        sb.append("	 	<tr>").append(NEW_LINE);
        sb.append(" 		<td style=\"width:50px\">&nbsp;</td>").append(NEW_LINE);

        int cols = inputBeans.size() + 1; // + 1 for the 'row'.
        iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (in.javaTypeName.equalsIgnoreCase("String")) {
                sb.append(generateStringFilterColumn(in, strutsFormName));
            } else if (in.javaTypeName.equalsIgnoreCase("Date")) {
                sb.append(generateDateFilterColumn(in, strutsFormName));
            } else if (in.javaTypeName.equalsIgnoreCase("Long") ||
                    in.javaTypeName.equalsIgnoreCase("Integer") ||
                    in.javaTypeName.equalsIgnoreCase("BigDecimal")) {
                sb.append(generateNumberFilterColumn(in, strutsFormName));
            }
        }
        sb.append("		</tr>").append(NEW_LINE);
        sb.append("		<tr><td colspan=\"")
                .append(cols)
                .append("\" align=\"center\">").append(NEW_LINE);
        sb.append("		<table style=\"width:400px\" border=\"0\"><tr>").append(NEW_LINE);
        sb.append("			<td><input type=\"button\" onclick=\"doPageSubmit('retrieve')\" name=\"subButton\" value=\"Retrieve\"/></td>").append(NEW_LINE);
        sb.append("			<td><input type=\"button\" onclick=\"clearFiltersAndOrder()\" name=\"clearButton\" value=\"Clear Filters\"/></td>").append(NEW_LINE);
        sb.append("			<td><div id=\"ORDER_DIV\" class=\"ordering\"></div><script>loadOrderingElementsOrder();</script></td>").append(NEW_LINE);
        sb.append("		</tr></table>").append(NEW_LINE);
        sb.append("		</td></tr>").append(NEW_LINE);


        sb.append("		</thead>").append(NEW_LINE);
        sb.append("		<tbody>").append(NEW_LINE);
        sb.append("		<logic:notEmpty name=\"")
                .append(strutsFormName)
                .append("\"  property=\"list\">").append(NEW_LINE);
        sb.append("		<logic:iterate id=\"rec\" name=\"")
                .append(strutsFormName)
                .append("\"  property=\"list\" type=\"");
        sb.append(WEB_BEAN_JAVA_PACKAGE_NAME);
        sb.append(".")
                .append(formBeanVOName)
                .append("\" indexId=\"i\" >").append(NEW_LINE);
        sb.append("		<tr>").append(NEW_LINE);
        sb.append("		<td class=\"data\"><%=(i+1)%></td>").append(NEW_LINE);


        int col = 1;
        iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            String name = getJavaFieldNameFromDBFieldName(in.dbFieldName);
            if (col == 1) {
                sb.append("		<td class=\"data\"><a href=\"")
                        .append(editSingleRecordAction)
                        .append(".do?actionName=open&")
                        .append(name)
                        .append("=<bean:write name=\"rec\"  property=\"")
                        .append(name)
                        .append("\"/>\"><bean:write name=\"rec\"  property=\"")
                        .append(name)
                        .append("\"/></a></td>").append(NEW_LINE);
            } else {
                sb.append("		<td class=\"data\"><bean:write name=\"rec\"  property=\"")
                        .append(name)
                        .append("\"/></td>").append(NEW_LINE);
            }
            col++;
        }

        sb.append("		</tr>").append(NEW_LINE);
        sb.append("		</logic:iterate>").append(NEW_LINE);
        sb.append("		</logic:notEmpty>").append(NEW_LINE);
        sb.append("		</tbody>").append(NEW_LINE);
        sb.append("	</table>").append(NEW_LINE);
        sb.append("	<BR>").append(NEW_LINE);
        sb.append("	").append(NEW_LINE);

        sb.append("<tc:permission resourceName=\"<%=PermissionValidator.").append(permissionResourceName)
                .append("%>\" actions=\"c\">").append(NEW_LINE);

        sb.append("	<input type=\"button\" onclick=\"doPageSubmit('new')\" name=\"")
                .append(addRecordButtonName)
                .append("\" value=\"")
                .append(addRecordButtonValue)
                .append("\"/>").append(NEW_LINE);

        sb.append("</tc:permission>").append(NEW_LINE);


        sb.append("	</html:form>").append(NEW_LINE);
        sb.append("	").append(NEW_LINE);
        sb.append("	</body>").append(NEW_LINE);
        sb.append("	</html>").append(NEW_LINE);
        return sb;
    }

    private StringBuffer generateStringFilterColumn(SearchPageGeneratorInputBean in, String strutsFormName) {
        String name = getJavaFieldNameFromDBFieldName(in.dbFieldName);
        String nameOrder = name + FILTER_BEAN_SUFFIX_ORDER;
        String anchorName = nameOrder + "AnchorID";

        StringBuffer sb = new StringBuffer();
        sb.append("			<td class=\"filter\" style=\"width:120px\"><div class=\"filter\">").append(NEW_LINE);
        sb.append("			<table class=\"filter\">").append(NEW_LINE);
        sb.append("			<tr><td><html:text name=\"")
                .append(strutsFormName)
                .append("\" property=\"")
                .append(name)
                .append("\" styleClass=\"filter\" /></td></tr>").append(NEW_LINE);
        if (in.sortable) {
            sb.append("			<tr><td align=\"center\"><a id=\"")
                    .append(anchorName)
                    .append("\" style=\"cursor:pointer\" onclick=\"updateOrdering(this, document.forms[0].")
                    .append(nameOrder)
                    .append(")\"><bean:write name=\"")
                    .append(strutsFormName)
                    .append("\" property=\"")
                    .append(nameOrder)
                    .append("\"/></a></td></tr>").append(NEW_LINE);
        }
        sb.append("			</table>").append(NEW_LINE);
        sb.append("			</div></td>").append(NEW_LINE);
        return sb;
    }

    private StringBuffer generateDateFilterColumn(SearchPageGeneratorInputBean in, String strutsFormName) {

        StringBuffer sb = new StringBuffer();
        if (in.range) {
            String name = getJavaFieldNameFromDBFieldName(in.dbFieldName);
            String nameFrom = name + FILTER_BEAN_RANGE_SUFFIX_FROM;
            String nameTo = name + FILTER_BEAN_RANGE_SUFFIX_TO;
            String nameOrder = name + FILTER_BEAN_SUFFIX_ORDER;
            String anchorName = nameOrder + "AnchorID";

            sb.append("			<td class=\"filter\"><div class=\"filter\">").append(NEW_LINE);
            sb.append("			<table class=\"filter\">").append(NEW_LINE);
            sb.append("			<tr>").append(NEW_LINE);
            sb.append("				<td>From <a href=\"javascript:show_calendar('forms[0].")
                    .append(nameFrom)
                    .append("');\"><img src=\"images/calendar.gif\" class=\"calendarFilter\" alt=\"Date picker\"></a></td>").append(NEW_LINE);
            sb.append("				<td><html:text name=\"")
                    .append(strutsFormName)
                    .append("\" property=\"")
                    .append(nameFrom)
                    .append("\" onchange=\"testDate(this);\" styleClass=\"filter\" /></td>").append(NEW_LINE);
            sb.append("			</tr>").append(NEW_LINE);
            sb.append("			<tr><td>To <a href=\"javascript:show_calendar('forms[0].")
                    .append(nameTo)
                    .append("');\"><img src=\"images/calendar.gif\" class=\"calendarFilter\" alt=\"Date picker\"></a></td>").append(NEW_LINE);
            sb.append("				<td><html:text name=\"").append(strutsFormName).append("\" property=\"")
                    .append(nameTo)
                    .append("\" onchange=\"testDate(this);\" styleClass=\"filter\" /></td>").append(NEW_LINE);
            sb.append("			</tr>").append(NEW_LINE);
            if (in.sortable) {
                sb.append("			<tr><td colspan=\"2\" align=\"center\"><a id=\"")
                        .append(anchorName)
                        .append("\" style=\"cursor:pointer\" onclick=\"updateOrdering(this, document.forms[0].")
                        .append(nameOrder)
                        .append(")\"><bean:write name=\"").append(strutsFormName).append("\" property=\"")
                        .append(nameOrder)
                        .append("\"/></a></td></tr>").append(NEW_LINE);
            }
            sb.append("			</table>").append(NEW_LINE);
            sb.append("			</div></td>").append(NEW_LINE);

        } else {

        }
        return sb;
    }

    private StringBuffer generateNumberFilterColumn(SearchPageGeneratorInputBean in, String strutsFormName) {
        StringBuffer sb = new StringBuffer();
        String name = getJavaFieldNameFromDBFieldName(in.dbFieldName);
        String nameOrder = name + FILTER_BEAN_SUFFIX_ORDER;
        String anchorName = nameOrder + "AnchorID";
        if (in.range) {
            String nameFrom = name + FILTER_BEAN_RANGE_SUFFIX_FROM;
            String nameTo = name + FILTER_BEAN_RANGE_SUFFIX_TO;

            sb.append("			<td class=\"filter\"><div class=\"filter\">").append(NEW_LINE);
            sb.append("			<table class=\"filter\">").append(NEW_LINE);
            sb.append("			<tr>").append(NEW_LINE);
            sb.append("				<td>From</td>").append(NEW_LINE);
            sb.append("				<td><html:text name=\"").append(strutsFormName).append("\" property=\"")
                    .append(nameFrom)
                    .append("\" styleClass=\"filter\" /></td>").append(NEW_LINE);
            sb.append("			</tr>").append(NEW_LINE);
            sb.append("			<tr><td>To</td>").append(NEW_LINE);
            sb.append("				<td><html:text name=\"").append(strutsFormName).append("\" property=\"")
                    .append(nameTo)
                    .append("\" styleClass=\"filter\" /></td>").append(NEW_LINE);
            sb.append("			</tr>").append(NEW_LINE);
            sb.append("			<tr><td colspan=\"2\" align=\"center\"><a id=\"")
                    .append(anchorName)
                    .append("\" style=\"cursor:pointer\" onclick=\"updateOrdering(this, document.forms[0].")
                    .append(nameOrder)
                    .append(")\"><bean:write name=\"").append(strutsFormName).append("\" property=\"")
                    .append(nameOrder)
                    .append("\"/></a></td></tr>").append(NEW_LINE);
            sb.append("			</table>").append(NEW_LINE);
            sb.append("			</div></td>").append(NEW_LINE);
        } else {

            sb.append("			<td class=\"filter\" style=\"width:120px\"><div class=\"filter\">").append(NEW_LINE);
            sb.append("			<table class=\"filter\">").append(NEW_LINE);
            sb.append("			<tr><td><html:text name=\"").append(strutsFormName).append("\" property=\"")
                    .append(name)
                    .append("\" styleClass=\"filter\" /></td></tr>").append(NEW_LINE);
            if (in.sortable) {
                sb.append("			<tr><td align=\"center\"><a id=\"")
                        .append(anchorName)
                        .append("\" style=\"cursor:pointer\" onclick=\"updateOrdering(this, document.forms[0].")
                        .append(nameOrder)
                        .append(")\"><bean:write name=\"").append(strutsFormName).append("\" property=\"")
                        .append(nameOrder)
                        .append("\"/></a></td></tr>").append(NEW_LINE);
            }
            sb.append("			</table>").append(NEW_LINE);
            sb.append("			</div></td>").append(NEW_LINE);
        }
        return sb;
    }


    private StringBuffer generateStrutsConfigXMLContents(String jspFileNameNoExtension,
                                                         String strutsFormName, String actionName, String addSingleRecordAction) {
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
        sb.append("			<forward name=\"success\" path=\"/")
                .append(jspFileNameWithSuffix)
                .append("\" />").append(NEW_LINE);
        sb.append("			<forward name=\"error\" path=\"/applicationError.jsp\" />").append(NEW_LINE);
        sb.append("			<forward name=\"business-error\" path=\"/")
                .append(jspFileNameWithSuffix)
                .append("\" />").append(NEW_LINE);
        sb.append("	        <forward name=\"")
                .append(addSingleRecordAction)
                .append("\" path=\"/")
                .append(addSingleRecordAction)
                .append(".do\" />").append(NEW_LINE);
        sb.append("	    </action>").append(NEW_LINE);


        return sb;
    }

}