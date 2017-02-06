<%@ page import="biz.tt.hyperion.i18n.TextConstants" %>
<%@ page import="biz.tt.hyperion.web.tag.ActionConstants" %>
<%
    String userName = (String) session.getAttribute(PermissionValidator.USER_FIRST_LAST_NAME);
    String patientName = (String) session.getAttribute(ActionConstants.PATIENT_NAME_ATTR_NAME);
    Boolean userAuthenticated = (Boolean) session.getAttribute(PermissionValidator.USER_AUTHENTICATED_NAME);
%>
<!-- Add jQuery library -->
<script src="scripts/jquery-1.6.3.min.js"></script>
<!--script type="text/javascript" src="scripts/jquery-1.7.2.min.js"></script-->
<!-- Add mousewheel plugin (this is optional) -->
<script type="text/javascript" src="scripts/jquery.mousewheel-3.0.6.pack.js"></script>
<!-- Add fancyBox main JS and CSS files -->
<script type="text/javascript" src="scripts/jquery.fancybox.js?v=2.0.6"></script>
<link rel="stylesheet" type="text/css" href="css/jquery.fancybox.css?v=2.0.6" media="screen"/>
<!-- Add Button helper (this is optional) -->
<link rel="stylesheet" type="text/css" href="../source/helpers/jquery.fancybox-buttons.css?v=1.0.2"/>
<script type="text/javascript" src="../source/helpers/jquery.fancybox-buttons.js?v=1.0.2"></script>
<!-- Add Thumbnail helper (this is optional) -->
<link rel="stylesheet" type="text/css" href="../source/helpers/jquery.fancybox-thumbs.css?v=1.0.2"/>
<script type="text/javascript" src="../source/helpers/jquery.fancybox-thumbs.js?v=1.0.2"></script>
<!-- Add Media helper (this is optional) -->
<script type="text/javascript" src="../source/helpers/jquery.fancybox-media.js?v=1.0.0"></script>
<script type="text/javascript" language="javascript">
function updatePatientContext(patientName) {
	//alert('updating patient context: ' + patientId);
	document.getElementById("patientContextLink").innerHTML = patientName; 
}
//function showPatientContextSwitchPage() {
//var myRef = window.open('switchPatient.do?actionName=open','mywin','left=0,top=0,fullscreen=yes,toolbar=1,scrollbars=1,Resizable=0');
//}
///// fancybox starts
$(document).ready(function() {
		$("#patientContextLink").fancybox({
			width:"100%",
			helpers: {
				title : {
					type : 'float'
				}
			},
			afterClose: function () {
			}
		});
	});
///// fancybox ends



</script>
<div id="top-bar">
    <div id="topbar-inner">
        <table>
            <tr>
                <td width="300px">
                    <a style="font-size:large" href="/Hyperion/mainMenu.jsp"><%=TextConstants.HYPERION%>
                    </a>
                </td>
                <td width="300px">
                    <h3><%=sectionName%>
                    </h3>
                </td>
                <td width="300px">
                    <div class="user-name"><%=userName%>
                    </div>
                    <!--div class="patient-name"><a id="patientContextLink" href="javascript:showPatientContextSwitchPage()" style="text-decoration:none;color:#0033FF;cursor:hand"><%=patientName%></a></div-->
                    <div class="patient-name"><a class="fancybox fancybox.iframe" id="patientContextLink"
                                                 href="switchPatient.do?actionName=open"
                                                 style="text-decoration:none;color:#0033FF;cursor:hand"><%=patientName%>
                    </a></div>
                    <% if (userAuthenticated != null && userAuthenticated.booleanValue()) { %>
                    <div class="login-link">&nbsp;&nbsp;&nbsp;<a
                            href="login.do?actionName=logout"><%=TextConstants.LOGOUT%>
                    </a>&nbsp;&nbsp;</div>
                    <% } else { %>
                    <div class="login-link">&nbsp;&nbsp;&nbsp;<a
                            href="login.do?actionName=open"><%=TextConstants.LOGIN%>
                    </a>&nbsp;&nbsp;</div>
                    <% } %>
                </td>
            </tr>
        </table>
    </div>
</div>