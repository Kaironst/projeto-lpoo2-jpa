<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Users</title>
    <style>
        .item-block { border: 1px solid #ccc; padding: 6px; margin-bottom: 6px; display: flex; align-items: center; justify-content: space-between; }
        .item-text { margin-right: 10px; }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/navbar.jsp" />

<h3>Add/Edit User</h3>
<form method="post" action="users">
    <input type="hidden" name="id" value="${userEditar.id}">
    <label>Nome:</label><br/>
    <input type="text" name="nome" value="${userEditar.nome}" required/><br/><br/>

    <label>Email:</label><br/>
    <input type="email" name="email" value="${userEditar.email}" required/><br/><br/>

    <label>CPF:</label><br/>
    <input type="text" name="cpf" value="${userEditar.cpf}" required/><br/><br/>

    <label>Período:</label><br/>
    <input type="number" name="periodo" min="1" value="${userEditar.periodo}" required/><br/><br/>

    <label>Senha:</label><br/>
    <input type="password" name="senha" value="${userEditar.senha}" required/><br/><br/>

    <label>Tipo de Pessoa:</label><br/>
    <select name="tipoPessoa" required>
        <option value="">-- selecione --</option>
        <c:forEach var="tp" items="${tipos}">
            <option value="${tp.id}" 
                <c:if test="${userEditar.tipo != null && userEditar.tipo.id == tp.id}">selected</c:if>>
                ${tp.nome}
            </option>
        </c:forEach>
    </select>
    <br/><br/>

    <!-- Cursos -->
    <label>Cursos:</label><br/>
    <select id="cursoSelect">
        <c:forEach var="c" items="${cursos}">
            <option value="${c.id}">${c.nome}</option>
        </c:forEach>
    </select>
    <button type="button" onclick="addCursoFromSelect()">Adicionar</button>
    <div id="cursosContainer"></div>

    <!-- Atividades -->
    <label>Atividades / Unidades Curriculares:</label><br/>
    <select id="atividadeSelect">
        <c:forEach var="a" items="${atividades}">
            <option value="${a.id}">${a.nome}</option>
        </c:forEach>
    </select>
    <button type="button" onclick="addAtividadeFromSelect()">Adicionar</button>
    <div id="atividadesContainer"></div>

    <br/><br/>
    <input type="submit" value="Save"/>
</form>

<h2>Lista de Usuários</h2>
<table>
    <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>Email</th>
        <th>Ações</th>
    </tr>
    <c:forEach var="p" items="${users}">
        <tr>
            <td>${p.id}</td>
            <td>${p.nome}</td>
            <td>${p.email}</td>
            <td>
                <a href="users?acao=editar&id=${p.id}">Editar</a> |
                <a href="users?acao=deletar&id=${p.id}" 
                   onclick="return confirm('Tem certeza que deseja excluir?')">Excluir</a>
            </td>
        </tr>
    </c:forEach>
</table>

<script>
let cursoCounter = 0;
let atividadeCounter = 0;

function addCurso(id, nome) {
    console.log('addCurso chamado com:', id, nome);
    const container = document.getElementById('cursosContainer');
    if (!id) return;
    if (container.querySelector(`[data-id='\${id}']`)) return;

    const div = document.createElement('div');
    div.className = 'item-block';
    div.dataset.id = id;

    const span = document.createElement('span');
    console.log('nome:', nome);
    span.className = 'item-text';
    span.textContent = nome;
    div.appendChild(span);
    
    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = `cursoIds[\${cursoCounter}]`;
    input.value = id;
    div.appendChild(input);

    const btn = document.createElement('button');
    btn.type = 'button';
    btn.textContent = 'Remover';
    btn.onclick = () => div.remove();
    div.appendChild(btn);

    container.appendChild(div);
    cursoCounter++;
}

function addAtividade(id, nome) {
    console.log('addAtividade chamado com:', id, nome);
    const container = document.getElementById('atividadesContainer');
    if (!id) return;
    if (container.querySelector(`[data-id='\${id}']`)) return;

    const div = document.createElement('div');
    div.className = 'item-block';
    div.dataset.id = id;

    const span = document.createElement('span');
    span.className = 'item-text';
    span.textContent = nome;
    div.appendChild(span);

    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = `atividadeIds[\${atividadeCounter}]`;
    input.value = id;
    div.appendChild(input);

    const btn = document.createElement('button');
    btn.type = 'button';
    btn.textContent = 'Remover';
    btn.onclick = () => div.remove();
    div.appendChild(btn);

    container.appendChild(div);
    atividadeCounter++;
}

// Adicionar pelo select
function addCursoFromSelect() {
    const select = document.getElementById('cursoSelect');
    const selected = select.options[select.selectedIndex];
    console.log('Selecionado curso:', selected.value, selected.text);
    addCurso(selected.value, selected.text);
}

function addAtividadeFromSelect() {
    const select = document.getElementById('atividadeSelect');
    const selected = select.options[select.selectedIndex];
    console.log('Selecionado atividade', selected.value, selected.text);
    addAtividade(selected.value, selected.text);
}

// Pré-carregar cursos e atividades do usuário editado usando arrays JSON
window.addEventListener('DOMContentLoaded', () => {
    // Pré-carregar cursos
    <c:if test="${not empty userEditar.curso}">
        const cursos = [
        <c:forEach var="c" items="${userEditar.curso}" varStatus="status">
            {id: '${c.id}', nome: '${fn:escapeXml(c.nome)}'}<c:if test="${!status.last}">,</c:if>
        </c:forEach>
        ];
        cursos.forEach(c => addCurso(c.id, c.nome));
    </c:if>

    // Pré-carregar atividades
    <c:if test="${not empty userEditar.atividades}">
        const atividades = [
        <c:forEach var="a" items="${userEditar.atividades}" varStatus="status">
            {id: '${a.id}', nome: '${fn:escapeXml(a.nome)}'}<c:if test="${!status.last}">,</c:if>
        </c:forEach>
        ];
        atividades.forEach(a => addAtividade(a.id, a.nome));
    </c:if>
});
</script>
</body>
</html>
