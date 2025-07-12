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
      <h:outputLabel for="new" value="New Password:"/>
      <h:inputSecret id="new"
                     value="#{providerController.resetNewPassword}"
                     required="true"/>

      <h:outputLabel for="conf" value="Confirm Password:"/>
      <h:inputSecret id="conf"
                     value="#{providerController.resetNewPasswordConfirm}"
                     required="true"/>

      <h:outputText/>
      <h:commandButton value="Reset" action="#{providerController.completeForgotPassword}"/>

      <h:outputText/>
      <h:commandLink value="Back to Login" action="login.jsf"/>
    </h:panelGrid>
    <h:messages globalOnly="true" layout="table"/>
  </h:form>
  </body>
  </html>
</f:view>
