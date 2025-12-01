<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>Resolver Avaliação ${avaliacao.id}</title>
</head>
<body>
    
  <jsp:include page="/WEB-INF/navbar.jsp" />
  <h1>Resolver Avaliação #${avaliacao.id}</h1>

  <form action="resolver-avaliacao" method="post">
    <input type="hidden" name="avaliacaoId" value="${avaliacao.id}" />

    <c:forEach var="questao" items="${avaliacao.questoes}" varStatus="qs">
      <div style="border: 1px solid #ccc; margin-bottom: 12px; padding: 10px;">
        <h3>Questão ${qs.index + 1} - ${questao.tipo}</h3>
        <p>${questao.enunciado}</p>

        <c:choose>
          <c:when test="${questao.tipo == 'DISCURSIVA'}">
            <textarea name="resposta[${questao.id}]" rows="4" cols="70"></textarea>
          </c:when>
          <c:when test="${questao.tipo == 'OBJETIVA_UNICA'}">
            <c:forEach var="alt" items="${questao.alternativas}">
              <div>
                <input type="radio" name="resposta[${questao.id}]" value="${alt.id}" />
                <label>${alt.enunciado}</label>
              </div>
            </c:forEach>
          </c:when>
          <c:when test="${questao.tipo == 'OBJETIVA_MULTIPLA'}">
            <c:forEach var="alt" items="${questao.alternativas}">
              <div>
                <input type="checkbox" name="resposta[${questao.id}]" value="${alt.id}" />
                <label>${alt.enunciado}</label>
              </div>
            </c:forEach>
          </c:when>
        </c:choose>
      </div>
    </c:forEach>

    <button type="submit">Enviar Respostas</button>
  </form>

</body>
</html>
