<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head><title>Users</title></head>
<body>
    <jsp:include page="/WEB-INF/navbar.jsp" />
    
    <h3>Add new user</h3>
    <form method="post" action="users">

    <label>Nome:</label><br/>
    <input type="text" name="nome" value="${userEditar.nome}" required/>
    <br/><br/>

    <label>Email:</label><br/>
    <input type="email" name="email" value="${userEditar.email}" required/>
    <br/><br/>

    <label>CPF:</label><br/>
    <input type="text" name="cpf" value="${userEditar.cpf}" required/>
    <br/><br/>

    <label>Período:</label><br/>
    <input type="number" name="periodo" min="1" value="${userEditar.periodo}" required/>
    <br/><br/>

    <label>Senha:</label><br/>
    <input type="password" name="senha" value="${userEditar.senha}" required/>
    <br/><br/>

    <label>Tipo de Pessoa:</label><br/>
    <select name="tipoPessoa" required>
        <option value="">-- selecione --</option>

        <c:forEach var="tp" items="${tipos}">
            <option value="${tp.id}"
                <c:if test="${userEditar.tipo != null && userEditar.tipo.id == tp.id}">
                    selected
                </c:if>>
                ${tp.nome}
            </option>
        </c:forEach>

    </select>
    <br/><br/>

    <label>Cursos:</label><br/>
    <select name="cursoIds" multiple size="5">
        <c:forEach var="c" items="${cursos}">
            <option value="${c.id}"
                <c:if test="${fn:contains(cursosIds, c.id)}">
                    selected
                </c:if>>
                ${c.nome}
            </option>
        </c:forEach>
    </select>
    <br/><br/>

    <label>Atividades / Unidades Curriculares:</label><br/>
    <select name="atividadeIds" multiple size="5">
        <c:forEach var="a" items="${atividades}">
            <option value="${a.id}"
                <c:if test="${fn:contains(atividadesIds, a.id)}">
                    selected
                </c:if>>
                ${a.nome}
            </option>
        </c:forEach>
    </select>
    <br/><br/>
        <input type="submit" value="Save"/>
    </form>
    
    <h2>User List</h2>
    <table border="1">
    <tr><th>ID</th><th>Nome</th><th>Email</th><th>Ações</th>
    </tr>

    <c:forEach var="p" items="${users}">
        <tr>
            <td>${p.id}</td>
            <td>${p.nome}</td>
            <td>${p.email}</td>
            <td>
                <a href="users?acao=editar&id=${p.id}">Editar</a>
                |
                <a href="users?acao=deletar&id=${p.id}"
                   onclick="return confirm('Tem certeza que deseja excluir?')">
                   Excluir
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
    
</body>
</html>
