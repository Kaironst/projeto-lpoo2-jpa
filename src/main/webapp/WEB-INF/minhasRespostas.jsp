<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>Minhas Respostas</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 20px; }
    .resposta-block { border: 1px solid #ccc; padding: 12px; margin-bottom: 20px; }
    .questao { margin-bottom: 8px; }
    .nota { font-weight: bold; color: green; }
  </style>
</head>
<body>

  <h1>Minhas Respostas</h1>

  <c:choose>
    <c:when test="${empty respostas}">
      <p>Você ainda não respondeu nenhuma avaliação.</p>
    </c:when>
    <c:otherwise>
      <c:forEach var="resposta" items="${respostas}">
        <div class="resposta-block">
          <h2>Avaliação #${resposta.avaliacao.id}</h2>
          <p><strong>Nota:</strong> <c:out value="${resposta.nota != null ? resposta.nota : 'Ainda não corrigida'}" /></p>

          <c:forEach var="respostaQuestao" items="${resposta.respostaQuestoes}">
            <div class="questao">
              <h4>Questão #${respostaQuestao.questao.id} - ${respostaQuestao.questao.tipo}</h4>
              <p><strong>Enunciado:</strong> ${respostaQuestao.questao.enunciado}</p>

              <p><strong>Resposta:</strong>
                <c:choose>
                  <c:when test="${respostaQuestao.questao.tipo == 'DISCURSIVA'}">
                    <pre>${respostaQuestao.respDiscursiva}</pre>
                  </c:when>
                  <c:when test="${respostaQuestao.questao.tipo == 'OBJETIVA_UNICA' || respostaQuestao.questao.tipo == 'OBJETIVA_MULTIPLA'}">
                    <ul>
                      <c:forEach var="alt" items="${respostaQuestao.respObjetiva}">
                        <li>${alt.enunciado} <c:if test="${alt.correta}">(Correta)</c:if></li>
                      </c:forEach>
                    </ul>
                  </c:when>
                </c:choose>
              </p>

              <p><strong>Correção:</strong> 
                <c:choose>
                  <c:when test="${respostaQuestao.questao.tipo == 'DISCURSIVA'}">
                    <c:out value="${respostaQuestao.correcaoDiscursiva != null ? (respostaQuestao.correcaoDiscursiva ? 'Correta' : 'Incorreta') : 'Não corrigida'}" />
                  </c:when>
                  <c:otherwise>
                    <c:out value="${respostaQuestao.correta ? 'Correta' : 'Incorreta'}" />
                  </c:otherwise>
                </c:choose>
              </p>

            </div>
          </c:forEach>
        </div>
      </c:forEach>
    </c:otherwise>
  </c:choose>

  <a href="lista-avaliacoes">Voltar às avaliações</a>

</body>
</html>
