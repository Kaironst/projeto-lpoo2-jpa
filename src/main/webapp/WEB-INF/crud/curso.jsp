<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>Cadastro de Curso</title>
</head>

<body>
    <jsp:include page="/WEB-INF/navbar.jsp" />
    <h1>Cadastro de Curso</h1>

    <form action="cursos" method="post">
        <input type="hidden" name="id" value="${cursoEditar.id}" />

        <label>Nome:</label><br/>
        <input type="text" name="nome" value="${cursoEditar.nome}" required/>
        <br/><br/>

        <label>Número de Períodos:</label><br/>
        <input type="number" name="numPeriodos" value="${cursoEditar.numPeriodos}" min="1" required/>
        <br/><br/>

        <input type="submit" value="Salvar Curso"/>
    </form>

    <hr/>

    <h2>Cursos Existentes</h2>

    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Número de Períodos</th>
            <th>Ações</th>
        </tr>

        <c:forEach var="c" items="${cursos}">
            <tr>
                <td>${c.id}</td>
                <td>${c.nome}</td>
                <td>${c.numPeriodos}</td>
                <td>
                    <a href="cursos?acao=editar&id=${c.id}">Editar</a> |
                    <a href="cursos?acao=deletar&id=${c.id}" 
                       onclick="return confirm('Deseja realmente excluir este curso?')">Excluir</a>
                </td>
            </tr>
        </c:forEach>

    </table>

</body>
</html>