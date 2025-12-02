<%-- 
    Document   : navbar.jsp
    Created on : 1 de dez. de 2025, 14:19:47
    Author     : kusse
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <nav style="background:#222; padding: 10px;">
    <a href="${pageContext.request.contextPath}/users" style="color:white; margin-right:15px;">
        crud pessoa
    </a>
    <a href="${pageContext.request.contextPath}/crud/tipos" style="color:white; margin-right:15px;">
        crud tipo
    </a>
    
    <a href="${pageContext.request.contextPath}/crud/curriculos" style="color:white; margin-right:15px;">
        crud curriculo
    </a>
    
    <a href="${pageContext.request.contextPath}/crud/unidades" style="color:white; margin-right:15px;">
        crud un. curricular
    </a>
    
    <a href="${pageContext.request.contextPath}/crud/cursos" style="color:white; margin-right:15px;">
        crud cursos
    </a>
    
    <a href="${pageContext.request.contextPath}/lista-avaliacoes" style="color:white; margin-right:15px;">
        lista avaliacoes
    </a>
        
    <a href="${pageContext.request.contextPath}/minhas-avaliacoes" style="color:white; margin-right:15px;">
        minhas avaliacoes
    </a>
    
    <a href="${pageContext.request.contextPath}/nova-avaliacao" style="color:white; margin-right:15px;">
        nova avaliacao
    </a>
    
    <a href="${pageContext.request.contextPath}/login" style="color:white; margin-right:15px;">
        login
    </a>

    <a href="${pageContext.request.contextPath}/minhas-respostas" style="color:white; margin-right:15px;">
        minhas respostas
    </a>
</nav>
</html>
