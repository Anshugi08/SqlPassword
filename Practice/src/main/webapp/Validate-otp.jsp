<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<f:view>
  <h:form>
    <h:panelGrid columns="2" cellpadding="5">
      <h:outputLabel for="otp" value="Enter OTP:"/>
      <h:inputText id="otp"
                   value="#{providerController.otpCode}"
                   required="true"/>

      <h:outputText/>
      <h:commandButton value="Validate" action="#{providerController.validateForgotPasswordOtp}"/>

      <h:outputText/>
      <h:commandLink value="Back to Login" action="login.jsf"/>
    </h:panelGrid>
    <h:messages globalOnly="true" layout="table"/>
  </h:form>
</f:view>
</body>
</html>