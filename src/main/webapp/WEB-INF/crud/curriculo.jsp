<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>Cadastro de Currículo</title>
</head>

<body>

    <h1>Cadastro de Currículo</h1>

    <!-- FORMULÁRIO -->
    <form action="curriculos" method="post">
        <input type="hidden" name="id" value="${curriculoEditar.id}" />

        <label>Curso:</label><br/>
        <select name="cursoId" required>
            <option value="">-- selecione --</option>
            <c:forEach var="cur" items="${cursos}">
                <option value="${cur.id}" 
                    <c:if test="${curriculoEditar != null && curriculoEditar.curso.id == cur.id}">selected</c:if>>
                    ${cur.nome}
                </option>
            </c:forEach>
        </select>
        <br/><br/>

        <label>Período:</label><br/>
        <input type="number" name="periodo" min="1" value="${curriculoEditar.periodo}" required/>
        <br/><br/>

        <input type="submit" value="Salvar Currículo"/>
    </form>

    <hr/>

    <!-- LISTAGEM -->
    <h2>Currículos Existentes</h2>

    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th><th>Curso</th><th>Período</th><th>Ações</th>
        </tr>

        <c:forEach var="c" items="${curriculos}">
            <tr>
                <td>${c.id}</td>
                <td>${c.curso.nome}</td>
                <td>${c.periodo}</td>
                <td>
                    <a href="curriculos?acao=editar&id=${c.id}">Editar</a> |
                    <a href="curriculos?acao=deletar&id=${c.id}" 
                       onclick="return confirm('Deseja realmente excluir este currículo?')">Excluir</a>
                </td>
            </tr>
        </c:forEach>

    </table>

</body>
</html>