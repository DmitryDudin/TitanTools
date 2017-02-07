<%@ page contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<jsp:useBean id="generatorForm" scope="request" class="org.titantech.titantools.web.form.GeneratorForm"/>

<html>

<head>
    <title>Metacode Generator</title>
    <style>
        table, td, th {
            border-collapse: collapse;
            border: 1px solid #A2A2A2;
            padding: 5px;
        }

        th {
            background-color: #A2A2A2;
        }

        #database {
            position: relative;
            left: 800px;
        }
    </style>
    <script>

var baseName = "Expense Summary";
var baseNameDB = "expense_summary";
var baseNameCamel = "ExpenseSummary";

var classText;

function classTextReset () {
	classText = 
	"DBTableName=" + baseNameDB + "\n" +
	"DAOInterfaceName=" + baseNameCamel + "DAO\n" +
	"DAOImplementationName=PG" + baseNameCamel + "DAO\n" +
	"DAOVOName=" + baseNameCamel + "\n" +
	"DelegateName=" + baseNameCamel + "Delegate\n" +
	"FilterVOName=" + baseNameCamel + "Filter\n" +
	"StrutsActionName=Manage" + baseNameCamel + "Action\n" +
	"SearchPageStrutsFormName=Manage" + baseNameCamel + "Form\n" +
	"FormBeanVOName=" + baseNameCamel + "VO\n" +
	"SearchTableCaption=" + baseName + " Search\n" +
	"AddRecordButtonDisplayText=Add New " + baseName + "\n" +
	"AddSingleRecordAction=edit" + baseNameCamel + "\n" +
	"JSPFileNameNoExtension=manage" + baseNameCamel + "\n" +
	"EditJSPFileNameNoExtension=edit" + baseNameCamel + "\n" +
	"EditTableCaption=" + baseName + "\n" +
	"PermissionResourceConstantName=RESOURCE_ACCOUNTING_DATA_PAGE\n" +
	"EditPageStrutsFormName=Edit" + baseNameCamel + "Form\n" +
	"EditActionName=Edit" + baseNameCamel + "Action\n";
}

function change (newBaseName) {
	baseName = newBaseName;
	baseNameDB = newBaseName.toLowerCase().replace(/ /g, "_");
	baseNameCamel = newBaseName.replace(/ /g, "");
	classTextReset();
	document.getElementById("attributes").value = classText;
}

function changeOnLoad() {
	baseName = document.getElementById("baseName");
	if(baseName != null){
		change(baseName);
	}
}

function doPageSubmit(strutsAction) {
	var packageName = document.getElementById("packageName").value;
	if (packageName == "" || packageName == null) {
		alert("Please enter a Package Name");
	} else {
		document.forms[0].actionName.value = strutsAction;
		document.forms[0].submit();
	}
}

// variable
var totalFields = <bean:write name='generatorForm' property='totalFields'/>;
var fieldText = null;

function updateTable () {
	resetFieldText();
	document.getElementById("fieldText").value = fieldText;
}

function resetFieldText () {
	fieldText = "";
	totalFields = <bean:write name='generatorForm' property='totalFields'/>;
	for (var i = 0; i < totalFields; i ++) {
		var str = (i+1).toString();
		var name = "row" + str + "name";
		var type = "row" + str + "type";
		var filt = "row" + str + "filt";
		var sort = "row" + str + "sort";
		var wild = "row" + str + "wild";
		var rang = "row" + str + "rang";
		var jtyp = "row" + str + "jtyp";
		var mand = "row" + str + "mand";
		var prim = "row" + str + "prim";
		var seri = "row" + str + "seri";
		
		fieldText += "field." + str + 
				"=" + document.getElementById(name).value + 
				"," + document.getElementById(type).value + 
				",filterable:" + document.getElementById(filt).checked + 
				",sortable:" + document.getElementById(sort).checked + 
				",wildcards:" + document.getElementById(wild).checked + 
				",range:" + document.getElementById(rang).checked + 
				",javatype:" + document.getElementById(jtyp).value + 
				",mandatory:" + document.getElementById(mand).checked + 
				",primary:" + document.getElementById(prim).checked + 
				",serial:" + document.getElementById(seri).checked + "\n";
	}
}

function addField () {
	resetFieldText();
	var txt = "field." + totalFields + "=,TEXT,filterable:false,sortable:false,wildcards:false,range:false,javatype:String,mandatory:false,primary:false,serial:false\n"
	fieldText += txt;
	document.getElementById("fieldText").value = fieldText;
	doPageSubmit('parse');
}







    </script>
</head>


<body onload="updateTable();changeOnLoad();">

<fieldset>

    <legend>Metacode Generator</legend>

    <a href="database.jsp" id="database">Database Generator</a>

    <input type="button" value="Add More Fields" onclick="addField()"/>

    <html:form action="/generator" method="post">
        <html:hidden property="actionName" name="generatorForm"/>
        <BR>
        <html:errors/>
        Base package name prefix: <html:text name="generatorForm" property="basePackageNamePrefix"
                                             styleId="packageName"/>
        Base Name: <html:text name="generatorForm" property="baseName" onkeyup="change(this.value)"
                              styleId="baseNameText"/><br/>


        <html:textarea name="generatorForm" property="attributes" rows="21" cols="80" styleId="attributes"/><br/>


        <table id="fieldTable">
            <tr>
                <th>Field Name</th>
                <th>Field Type</th>
                <th>Javatype</th>
                <th>Filterable</th>
                <th>Sortable</th>
                <th>Wildcards</th>
                <th>Range</th>
                <th>Mandatory</th>
                <th>Primary Key</th>
                <th>Serial</th>
            </tr>

            <logic:notEmpty name="generatorForm" property="inputBeans">
                <logic:iterate id="spgib" name="generatorForm" property="inputBeans"
                               type="org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean">
                    <tr>

                        <td><input id='row<bean:write name="spgib" format="#" property="fieldId" />name' type='text'
                                   onkeyup='updateTable()' value='<bean:write name="spgib" property="dbFieldName" />'/>
                        </td>

                        <bean:define id="fieldId" name="spgib" property="fieldId" type="java.lang.Integer"/>
                        <% String typeId = "row" + fieldId.toString() + "type"; %>

                        <td>
                            <html:select name="spgib" property="dbFieldTypeStr" styleId='<%=typeId %>'
                                         onchange='updateTable()'>
                                <html:optionsCollection name="generatorForm" property="dbFieldTypes" label="label"
                                                        value="value"/>
                            </html:select>
                        </td>

                        <% String jtypId = "row" + fieldId.toString() + "jtyp"; %>

                        <td>
                            <html:select name="spgib" property="javaTypeName" styleId='<%=jtypId %>'
                                         onchange='updateTable()'>
                                <html:optionsCollection name="generatorForm" property="javaFieldTypes" label="label"
                                                        value="value"/>
                            </html:select>
                        </td>

                        <% String isChecked = ""; %>

                        <bean:define id="isFilt" name="spgib" property="filterable" type="java.lang.Boolean"/>
                        <%
                            if (isFilt) {
                                isChecked = "checked";
                            } else {
                                isChecked = "";
                            }
                        %>
                        <td><input id='row<bean:write name="spgib" format="#" property="fieldId" />filt' type='checkbox'
                                   onchange='updateTable()' <%= isChecked %>/></td>

                        <bean:define id="isSort" name="spgib" property="sortable" type="java.lang.Boolean"/>
                        <%
                            if (isSort) {
                                isChecked = "checked";
                            } else {
                                isChecked = "";
                            }
                        %>
                        <td><input id='row<bean:write name="spgib" format="#" property="fieldId" />sort' type='checkbox'
                                   onchange='updateTable()' <%= isChecked %>/></td>

                        <bean:define id="isWild" name="spgib" property="wildcards" type="java.lang.Boolean"/>
                        <%
                            if (isWild) {
                                isChecked = "checked";
                            } else {
                                isChecked = "";
                            }
                        %>
                        <td><input id='row<bean:write name="spgib" format="#" property="fieldId" />wild' type='checkbox'
                                   onchange='updateTable()' <%= isChecked %>/></td>

                        <bean:define id="isRang" name="spgib" property="range" type="java.lang.Boolean"/>
                        <%
                            if (isRang) {
                                isChecked = "checked";
                            } else {
                                isChecked = "";
                            }
                        %>
                        <td><input id='row<bean:write name="spgib" format="#" property="fieldId" />rang' type='checkbox'
                                   onchange='updateTable()' <%= isChecked %>/></td>

                        <bean:define id="isMand" name="spgib" property="mandatory" type="java.lang.Boolean"/>
                        <%
                            if (isMand) {
                                isChecked = "checked";
                            } else {
                                isChecked = "";
                            }
                        %>
                        <td><input id='row<bean:write name="spgib" format="#" property="fieldId" />mand' type='checkbox'
                                   onchange='updateTable()' <%= isChecked %>/></td>

                        <bean:define id="isPrim" name="spgib" property="primary" type="java.lang.Boolean"/>
                        <%
                            if (isPrim) {
                                isChecked = "checked";
                            } else {
                                isChecked = "";
                            }
                        %>
                        <td><input id='row<bean:write name="spgib" format="#" property="fieldId" />prim' type='checkbox'
                                   onchange='updateTable()' <%= isChecked %>/></td>

                        <bean:define id="isSeri" name="spgib" property="serial" type="java.lang.Boolean"/>
                        <%
                            if (isSeri) {
                                isChecked = "checked";
                            } else {
                                isChecked = "";
                            }
                        %>
                        <td><input id='row<bean:write name="spgib" format="#" property="fieldId" />seri' type='checkbox'
                                   onchange='updateTable()' <%= isChecked %>/></td>
                    </tr>
                </logic:iterate>
            </logic:notEmpty>

        </table>

        <input type="button" value="Update Table" onclick="doPageSubmit('parse')"/><br/>

        <html:textarea styleId="fieldText" name="generatorForm" property="fields" rows="15" cols="200"/><br/>

        <input type="button" onclick="doPageSubmit('generate')" name="subButton" value="Generate"/>

        <BR><BR>
        <logic:notEmpty name="generatorForm" property="downloadFileName">
            <a href="fs?fn=<bean:write name="generatorForm" property="downloadFileName" />"><bean:write
                    name="generatorForm" property="downloadFileName"/></a>
        </logic:notEmpty>
        <BR><BR>

        <input type="button" onclick="doPageSubmit('preview')" name="previewButton" value="Preview"/>
        <BR><BR>
        <logic:notEmpty name="generatorForm" property="previewFiles">
            <logic:notEmpty name="generatorForm" property="previewFilesRowCount">
                <bean:define name="generatorForm" id="rowCount" property="previewFilesRowCount"
                             type="java.lang.String"/>
                <html:textarea styleId="previewFilesId" name="generatorForm" property="previewFiles"
                               rows="<%= rowCount %>" cols="200" readonly="true"/><br/>
            </logic:notEmpty>
        </logic:notEmpty>
        <BR>
    </html:form>
</fieldset>
</body>
</html>
