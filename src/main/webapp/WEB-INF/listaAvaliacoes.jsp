<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>Lista de Avaliações</title>
</head>
<body>
    <jsp:include page="/WEB-INF/navbar.jsp" />
  <h1>Lista de Avaliações</h1>

  <table border="1" cellpadding="6" cellspacing="0">
    <thead>
      <tr>
        <th>ID</th>
        <th>Avaliação Aberta?</th>
        <th>Anônima?</th>
        <th>Qtd. Questões</th>
        <th>Ações</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="av" items="${avaliacoes}">
    <c:if test="${av.aberta}">
        <tr>
          <td>${av.id}</td>
          <td><c:choose>
                <c:when test="${av.aberta}">Sim</c:when>
                <c:otherwise>Não</c:otherwise>
              </c:choose></td>
          <td><c:choose>
                <c:when test="${av.anon}">Sim</c:when>
                <c:otherwise>Não</c:otherwise>
              </c:choose></td>
          <td><c:out value="${av.questoes != null ? fn:length(av.questoes) : 0 }"/></td>
          <td>
            <form action="resolver-avaliacao" method="get" style="display:inline;">
              <input type="hidden" name="id" value="${av.id}" />
              <button type="submit">Resolver</button>
            </form>
          </td>
        </tr>
    </c:if>
</c:forEach>
    </tbody>
  </table>

</body>
</html>
