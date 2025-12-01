<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>Login</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 30px; }
    form { max-width: 300px; margin: auto; }
    .error { color: red; margin-bottom: 15px; }
  </style>
</head>
<body>
    <jsp:include page="/WEB-INF/navbar.jsp" />
  <h2>Login</h2>

  <c:if test="${not empty erro}">
    <div class="error">${erro}</div>
  </c:if>

  <form action="login" method="post">
    <div>
      <label>Email:</label><br/>
      <input type="email" name="email" required autofocus />
    </div>
    <br/>
    <div>
      <label>Senha:</label><br/>
      <input type="password" name="senha" required />
    </div>
    <br/>
    <button type="submit">Entrar</button>
  </form>

</body>
</html>
