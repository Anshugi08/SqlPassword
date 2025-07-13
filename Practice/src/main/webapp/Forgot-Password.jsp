<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<f:view>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Forgot Password</title>
</head>
<body>
  <h:form id="forgotForm">
    <h:panelGrid columns="2" cellpadding="5">
      <h:outputLabel for="email" value="Your Email:"/>
      <h:inputText id="email"
                   value="#{providerController.forgotEmail}"
                   required="true"/>
      <h:outputText/>
      <h:commandButton value="Send OTP"
                       action="#{providerController.sendForgotPasswordOtp}"/>
      <h:outputText/>
      <h:commandLink value="Back to Login" action="login.jsf"/>
    </h:panelGrid>
    <h:messages globalOnly="true" layout="table"/>
  </h:form>
</body>
</html>
</f:view>
