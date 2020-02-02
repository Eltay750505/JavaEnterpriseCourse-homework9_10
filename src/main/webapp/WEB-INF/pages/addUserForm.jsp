<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>AddNewUser</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/addUser">
    userName:<input name="userName" type="text" maxlength="40"><br>
    password:<input name="password" type="text" maxlength="40"><br>
    age:<input name="age" type="number" maxlength="40"><br>
    active:<input name="active" type="text" maxlength="40">Should be true or false<br>
    address:<input name="address" type="text" maxlength="100"><br>
    telephone:<input name="telephone" maxlength="40"
                     type="tel" pattern="[0-9]{3}-[0-9]{2}-[0-9]{2}" required><span>Format: 123-45-67</span><br>
    <input type="submit" value="Add">
</form>
</body>
</html>
