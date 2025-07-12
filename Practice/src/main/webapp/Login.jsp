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

  <h:form>
    <h:panelGrid columns="1" cellpadding="5">
      <h:outputLabel value="Username or Email:"/>
      <h:inputText value="#{providerController.loginOrEmail}" required="true"/>

      <h:outputLabel value="Password:"/>
      <h:inputSecret value="#{providerController.loginPassword}" required="true"/>

      <h:commandButton value="Login" action="#{providerController.login}"/>

      <h:commandLink value="Sign Up" action="signup.jsf"/>
      <h:commandLink value="Forgot Password?" action="forgot-password.jsf"/>
    </h:panelGrid>

    <h:messages globalOnly="true" layout="table"/>
  </h:form>
  </body>
</f:view>

</html>
