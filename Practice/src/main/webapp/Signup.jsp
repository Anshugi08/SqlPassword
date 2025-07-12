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
    <h:panelGrid columns="2" cellpadding="5">
      <h:outputLabel for="first" value="First Name:"/>
      <h:inputText id="first"
                   value="#{providerController.signupProvider.firstName}"
                   required="true"/>

      <h:outputLabel for="last" value="Last Name:"/>
      <h:inputText id="last"
                   value="#{providerController.signupProvider.lastName}"
                   required="true"/>

      <h:outputLabel for="user" value="Username:"/>
      <h:inputText id="user"
                   value="#{providerController.signupProvider.userName}"
                   required="true"/>

      <h:outputLabel for="email" value="Email:"/>
      <h:inputText id="email"
                   value="#{providerController.signupProvider.email}"
                   required="true"/>

      <h:outputLabel for="pwd" value="Password:"/>
      <h:inputSecret id="pwd"
                     value="#{providerController.signupProvider.password}"
                     required="true"/>

      <h:outputText/>
      <h:commandButton value="Sign Up" action="#{providerController.signup}"/>

      <h:outputText/>
      <h:commandLink value="Back to Login" action="login.jsf"/>
    </h:panelGrid>

    <h:messages globalOnly="true" layout="table"/>
  </h:form>


</body>
</html>
</f:view>