<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<f:view>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

  <h:panelGrid columns="1" cellpadding="10">
    <h:outputText value="An unexpected error occurred." style="color:red;"/>
    <h:commandLink value="Back to Login" action="login.jsf"/>
  </h:panelGrid>

</body>
</html>
</f:view>