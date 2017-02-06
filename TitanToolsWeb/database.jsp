<%@page import="org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean" %>
<%@page import="org.titantech.titantools.dao.bean.TableBean" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Generate using Tables from Database</title>

    <style>
        table, td, th {
            border-collapse: collapse;
            border: 1px solid #DCDCDC;
            padding: 10px;
        }

        th {
            background-color: #DCDCDC;
            font: 15px cambria;
        }

        h1 {
            font: 25px cambria;
            color: #696969;
        }
    </style>

    <script type="text/javascript">
	function doPageSubmit(strutsAction) {
		document.forms[0].actionName.value = strutsAction;
		document.forms[0].submit();
	}
	
	function forwardToGenerator() {
		window.location.href = "generator.do?selectedTableName=" + document.forms[0].selectedTableName.value +
				"&actionName=fillForm" + "&baseName=" + document.forms[0].selectedClassName.value;
	}
	
// 	function buttonPressed (){
// 		document.getElementById("buttonP").value = "true";
// 	}
	



    </script>
</head>

<body>
<html:form action="/database" method="post">
    <html:hidden property="actionName" name="databaseForm"/>
    <html:hidden property="selectedTableName" name="databaseForm"/>
    <%-- 		<html:hidden property="buttonP" value="false"/> --%>
    <table>
        <tr>
            <td>Server Name</td>
            <td><html:text property="serverName"></html:text></td>
        </tr>
        <tr>
            <td>Port</td>
            <td><html:text property="databasePort"></html:text></td>
        </tr>
        <tr>
            <td>Database Name</td>
            <td><html:text property="databaseName"></html:text></td>
        </tr>
        <tr>
            <td>User</td>
            <td><html:text property="databaseUser"></html:text></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><html:password property="databasePassword"></html:password></td>
        </tr>
        <tr>
            <td><input type="button" onclick="doPageSubmit('')" value="Display Tables"/></td>
        </tr>
    </table>

    <hr>

    <logic:notEmpty name="databaseForm" property="listOfTableBeans">
        <h1>List of Tables</h1>

        <logic:iterate name="databaseForm" property="listOfTableBeans" id="table" indexId="idx"
                       type="org.titantech.titantools.dao.bean.TableBean">
            <html:radio property="selectedTable" value="<%= idx.toString()%>" onchange="doPageSubmit('refresh')"/>

            Table Name: <b><bean:write name="table" property="tableName"/></b><br>
            <table>
                <tr>
                    <th>Field Name</th>
                    <th>Field Type</th>
                </tr>
                <logic:iterate name="table" property="spgInputBeanList" id="spgibId"
                               type="org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean">
                    <tr>
                        <td><bean:write name="spgibId" property="dbFieldName"/></td>
                        <td><bean:write name="spgibId" property="dbFieldTypeStr"/></td>
                    </tr>
                </logic:iterate>
            </table>
            <br>
        </logic:iterate>

        Enter Class Name for selected table<br>
        <html:text property="selectedClassName"></html:text><br>

        <input type="button" onclick="doPageSubmit('generateForTable')" value="Generate"/><br><br>

        <logic:notEmpty name="databaseForm" property="selectedTableName">
            Selected table: <bean:write name="databaseForm" property="selectedTableName"/>
        </logic:notEmpty>
        <input type="button" value="continue(change value...)" onclick="forwardToGenerator()"/>
        <%-- 			<jsp:forward page="generator.do">  --%>
        <%-- 			<jsp:param name="table_name" value="<bean:write name="databaseForm" property="selectedTableName" />" />  --%>
        <%-- 			<jsp:param name="actionName" value="" />  --%>

    </logic:notEmpty>

    <br></br>
    <logic:notEmpty name="databaseForm" property="downloadFileName">
        <a href="fs?fn=<bean:write name="databaseForm" property="downloadFileName" />">
            <bean:write name="databaseForm" property="downloadFileName"/>
        </a>
    </logic:notEmpty>
    <br></br>

</html:form>
</body>
</html>