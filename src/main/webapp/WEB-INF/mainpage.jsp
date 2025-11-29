<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head><title>Users</title></head>
<body>
    <h2>User List</h2>
    <table border="1">
        <tr><th>ID</th><th>Name</th><th>Email</th><th>CPF</th><th>Periodo</th><th>Tipo</th></tr>
        <c:forEach var="u" items="${users}">
            <tr>
                <td>${u.id}</td>
                <td>${u.nome}</td>
                <td>${u.email}</td>
                <td>${u.cpf}</td>
                <td>${u.periodo}</td>
                <td>${u.tipo.nome}</td>
            </tr>
        </c:forEach>
    </table>

    <h3>Add new user</h3>
    <form method="post" action="users">
        Nome: <input type="text" name="nome"/><br/>
        Email: <input type="text" name="email"/><br/>
        CPF: <input type="number" name="cpf"/><br/>
        Periodo: <input type="number" name="periodo"/><br/>
        
        Tipo:
        <select name="tipoPessoa">
            <c:forEach var="tp" items="${tipos}">
                <option value="${tp.id}">${tp.nome}</option>
            </c:forEach>
        </select><br/>
        
        Senha: <input type="password" name="senha"/><br/>
        <input type="submit" value="Save"/>
    </form>
</body>
</html>
