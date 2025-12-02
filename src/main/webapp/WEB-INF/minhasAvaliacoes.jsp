<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Minhas Avaliações</title>
</head>
<body>

<jsp:include page="/WEB-INF/navbar.jsp"/>

<h1>Minhas Avaliações Criadas</h1>

<table border="1" cellpadding="6" cellspacing="0">
    <thead>
        <tr>
            <th>ID</th>
            <th>Aberta?</th>
            <th>Anônima?</th>
            <th>Qtd. Questões</th>
            <th>Ações</th>
        </tr>
    </thead>

    <tbody>
    <c:forEach var="av" items="${avaliacoes}">
        <tr>
            <td>${av.id}</td>

            <td>
                <c:choose>
                    <c:when test="${av.aberta}">Aberta</c:when>
                    <c:otherwise>Fechada</c:otherwise>
                </c:choose>
            </td>

            <td>
                <c:choose>
                    <c:when test="${av.anon}">Sim</c:when>
                    <c:otherwise>Não</c:otherwise>
                </c:choose>
            </td>

            <td>${av.questoes != null ? fn:length(av.questoes) : 0}</td>

            <td>
                <!-- BOTÃO DE TOGGLE ABERTA/FECHADA -->
                <form action="minhas-avaliacoes" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="${av.id}"/>
                    <button type="submit">
                        <c:choose>
                            <c:when test="${av.aberta}">Fechar</c:when>
                            <c:otherwise>Abrir</c:otherwise>
                        </c:choose>
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>