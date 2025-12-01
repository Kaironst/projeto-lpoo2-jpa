<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>Cadastro de Tipo</title>
</head>

<body>
    <jsp:include page="/WEB-INF/navbar.jsp" />
    <h1>Cadastro de Tipo</h1>

    <form action="tipos" method="post">
        <input type="hidden" name="id" value="${tipoEditar.id}" />

        <label>Nome:</label><br/>
        <input type="text" name="nome" value="${tipoEditar.nome}" required/>
        <br/><br/>

        <label>Pode criar Forms:</label><br/>
        <input type="checkbox" name="podecriarforms"
               ${tipoEditar.podeCriarForms ? "checked" : ""}>
        <br/><br/>
        
        <label>Pode responder Forms:</label><br/>
        <input type="checkbox" name="poderesponderforms"
               ${tipoEditar.podeResponderForms ? "checked" : ""}>
        <br/><br/>

        <input type="submit" value="Salvar Tipo"/>
    </form>

    <hr/>

    <h2>Tipos Existentes</h2>

    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Criar Forms</th>
            <th>Responder Forms</th>
        </tr>

        <c:forEach var="t" items="${tipos}">
            <tr>
                <td>${t.id}</td>
                <td>${t.nome}</td>
                <td>${t.podeCriarForms}</td>
                <td>${t.podeResponderForms}</td>
                <td>
                    <a href="tipos?acao=editar&id=${t.id}">Editar</a> |
                    <a href="tipos?acao=deletar&id=${t.id}"
                       onclick="return confirm('Deseja realmente excluir este tipo?')">Excluir</a>
                </td>
            </tr>
        </c:forEach>

    </table>

</body>
</html>