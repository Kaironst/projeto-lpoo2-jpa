<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Users</title></head>
<body>
    <h2>User List</h2>
    <table border="1">
        <tr><th>ID</th><th>Name</th><th>Email</th></tr>
        <c:forEach var="u" items="${users}">
            <tr>
                <td>${u.id}</td>
                <td>${u.name}</td>
                <td>${u.email}</td>
            </tr>
        </c:forEach>
    </table>

    <h3>Add new user</h3>
    <form method="post" action="users">
        Name: <input type="text" name="name"/><br/>
        Email: <input type="text" name="email"/><br/>
        <input type="submit" value="Save"/>
    </form>
</body>
</html>
