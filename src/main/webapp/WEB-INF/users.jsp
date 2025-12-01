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
        <button type="button" onclick="addCurso()">Adicionar</button>
        <button type="button" onclick="removeCurso()">Remover</button>
        <ul id="cursosSelecionados"></ul>
        <input type="hidden" name="cursoIds" id="cursoIds" />
        <br/><br/>

        <!-- Atividades -->
        <label>Atividades / Unidades Curriculares:</label><br/>
        <select id="atividadeSelect">
            <c:forEach var="a" items="${atividades}">
                <option value="${a.id}">${a.nome}</option>
            </c:forEach>
        </select>
        <button type="button" onclick="addAtividade()">Adicionar</button>
        <button type="button" onclick="removeAtividade()">Remover</button>
        <ul id="atividadesSelecionadas"></ul>
        <input type="hidden" name="atividadeIds" id="atividadeIds" />
        <br/><br/>

        <input type="submit" value="Save"/>
    </form>

    <h2>User List</h2>
    <table border="1">
        <tr><th>ID</th><th>Nome</th><th>Email</th><th>Ações</th></tr>
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
  const cursosSelecionados = new Set();
  const atividadesSelecionadas = new Set();

  function updateHiddenInputs() {
    document.getElementById('cursoIds').value = Array.from(cursosSelecionados).join(',');
    document.getElementById('atividadeIds').value = Array.from(atividadesSelecionadas).join(',');
  }

  function renderList(set, listId, selectId) {
    const ul = document.getElementById(listId);
    ul.innerHTML = '';
    set.forEach(id => {
      const text = document.querySelector(`#${selectId} option[value='${id}']`).textContent;
      const li = document.createElement('li');
      li.textContent = text + ` (ID: ${id})`;
      ul.appendChild(li);
    });
  }

  function addCurso() {
    const id = document.getElementById('cursoSelect').value;
    if (id) { cursosSelecionados.add(id); renderList(cursosSelecionados,'cursosSelecionados','cursoSelect'); updateHiddenInputs(); }
  }
  function removeCurso() {
    const id = document.getElementById('cursoSelect').value;
    if (id) { cursosSelecionados.delete(id); renderList(cursosSelecionados,'cursosSelecionados','cursoSelect'); updateHiddenInputs(); }
  }
  function addAtividade() {
    const id = document.getElementById('atividadeSelect').value;
    if (id) { atividadesSelecionadas.add(id); renderList(atividadesSelecionadas,'atividadesSelecionadas','atividadeSelect'); updateHiddenInputs(); }
  }
  function removeAtividade() {
    const id = document.getElementById('atividadeSelect').value;
    if (id) { atividadesSelecionadas.delete(id); renderList(atividadesSelecionadas,'atividadesSelecionadas','atividadeSelect'); updateHiddenInputs(); }
  }

  window.addEventListener('load', () => {
    <c:if test="${not empty cursosIds}">
      ${cursosIds}.forEach(id => cursosSelecionados.add(id));
      renderList(cursosSelecionados,'cursosSelecionados','cursoSelect');
      updateHiddenInputs();
    </c:if>
    <c:if test="${not empty atividadesIds}">
      ${atividadesIds}.forEach(id => atividadesSelecionadas.add(id));
      renderList(atividadesSelecionadas,'atividadesSelecionadas','atividadeSelect');
      updateHiddenInputs();
    </c:if>
  });
</script>
</body>
</html>