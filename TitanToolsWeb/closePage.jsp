<%@ page contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<head>
    <meta http-equiv="Pragma" content="no-cache">
    <!-- Pragma content set to no-cache tells the browser not to cache the page This may or may not work in IE -->
    <meta http-equiv="expires" content="0">

    <script>
<%
        String s = (String) request.getAttribute("closeWindowScript");
        if (s != null) {
    %>
<%=s%>
<%
        }
    %>
window.close();



    </script>
</head>
