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
  <h:panelGroup rendered="#{not empty providerController.currentUser}">
    <h2>Welcome, #{providerController.currentUser.firstName}!</h2>
    <h:commandLink value="Change Password" action="change-password.jsf"/><br/>
    <h:commandLink value="Logout" action="#{providerController.logout}"/>
  </h:panelGroup>

  <h:messages globalOnly="true" layout="table"/>
</body>
</html>
</f:view>