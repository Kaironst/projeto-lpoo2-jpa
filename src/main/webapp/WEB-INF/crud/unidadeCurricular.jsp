<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>Cadastro de Unidade Curricular</title>
</head>

<body>
    <jsp:include page="/WEB-INF/navbar.jsp" />
    <h1>Cadastro de Unidade Curricular</h1>

    <form action="unidades" method="post">
        <input type="hidden" name="id" value="${unidadeEditar.id}" />

        <label>Nome:</label><br/>
        <input type="text" name="nome" value="${unidadeEditar.nome}" required/>
        <br/><br/>

        <label>Descrição:</label><br/>
        <input type="text" name="descricao" value="${unidadeEditar.descricao}" required/>
        <br/><br/>

        <label>Tipo:</label><br/>
        <select name="tipo" required>
            <option value="DISCIPLINA" ${unidadeEditar.tipo == 'DISCIPLINA' ? 'selected' : ''}>DISCIPLINA</option>
            <option value="ATIVIDADE_FORMATIVA" ${unidadeEditar.tipo == 'ATIVIDADE_FORMATIVA' ? 'selected' : ''}>ATIVIDADE FORMATIVA</option>
            <option value="ATIVIDADE_EXTENSAO" ${unidadeEditar.tipo == 'ATIVIDADE_EXTENSAO' ? 'selected' : ''}>ATIVIDADE DE EXTENSÃO</option>
        </select>
        <br/><br/>

        <label>Currículo:</label><br/>
        <select name="curriculoId" required>
            <c:forEach var="c" items="${curriculos}">
                <option value="${c.id}" ${unidadeEditar != null && c.id == unidadeEditar.curriculo.id ? 'selected' : ''}>
                    ${c.curso.nome} - Período ${c.periodo}
                </option>
            </c:forEach>
        </select>
        <br/><br/>

        <input type="submit" value="Salvar Unidade Curricular"/>
    </form>

    <hr/>

    <h2>Unidades Curriculares Existentes</h2>

    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Descrição</th>
            <th>Tipo</th>
            <th>Currículo</th>
            <th>Ações</th>
        </tr>

        <c:forEach var="u" items="${unidades}">
            <tr>
                <td>${u.id}</td>
                <td>${u.nome}</td>
                <td>${u.descricao}</td>
                <td>${u.tipo}</td>
                <td>${u.curriculo.curso.nome} - Período ${u.curriculo.periodo}</td>
                <td>
                    <a href="unidades?acao=editar&id=${u.id}">Editar</a> |
                    <a href="unidades?acao=deletar&id=${u.id}" 
                       onclick="return confirm('Deseja realmente excluir esta unidade curricular?')">Excluir</a>
                </td>
            </tr>
        </c:forEach>

    </table>

</body>
</html>