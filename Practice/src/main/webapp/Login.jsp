<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<f:view>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Login</title>
</head>
<body>
  <h:form id="loginForm">
    <h:panelGrid columns="1" cellpadding="5">
      <h:outputLabel for="loginOrEmail" value="Username or Email:"/>
      <h:inputText id="loginOrEmail"
                   value="#{providerController.loginOrEmail}"
                   required="true"/>

      <h:outputLabel for="loginPassword" value="Password:"/>
      <h:inputSecret id="loginPassword"
                     value="#{providerController.loginPassword}"
                     required="true"/>

      <h:commandButton value="Login"
                       action="#{providerController.login}"/>

      <h:commandLink value="Sign Up"
                     action="signup.jsf"/>
      <h:commandLink value="Forgot Password?"
                     action="forgot-password.jsf"/>
    </h:panelGrid>

    <h:messages globalOnly="true" layout="table"/>
  </h:form>
</body>
</html>
</f:view>
