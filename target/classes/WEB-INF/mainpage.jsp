<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Signup Page</title>
    <script>
        function toggleStudentFields() {
            const typeSelect = document.getElementById("type");
            const studentFields = document.getElementById("studentFields");
            if (typeSelect.value === "student") {
                studentFields.style.display = "block";
            } else {
                studentFields.style.display = "none";
            }
        }
    </script>
</head>
<body>
    <h2>Signup Form</h2>

    <form action="signup" method="post">
        <label for="name">Name:</label><br>
        <input type="text" id="name" name="name" value="${param.name}" required><br><br>

        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" value="${param.email}" required><br><br>

        <label for="cpf">CPF:</label><br>
        <input type="text" id="cpf" name="cpf" value="${param.cpf}" required><br><br>

        <label for="type">Type:</label><br>
        <select id="type" name="type" onchange="toggleStudentFields()" required>
            <option value="">-- Select --</option>
            <option value="student" <c:if test="${param.type == 'student'}">selected</c:if>>Student</option>
            <option value="teacher" <c:if test="${param.type == 'teacher'}">selected</c:if>>Teacher</option>
            <option value="staff" <c:if test="${param.type == 'staff'}">selected</c:if>>Staff</option>
        </select><br><br>

        <div id="studentFields" style="display: none;">
            <label for="course">Course:</label><br>
            <input type="text" id="course" name="course" value="${param.course}"><br><br>

            <label for="semester">Semester:</label><br>
            <input type="number" id="semester" name="semester" value="${param.semester}" min="1"><br><br>
        </div>

        <input type="submit" value="Sign Up">
    </form>

    <script>
        // Initialize correct visibility when reloading page with parameters
        toggleStudentFields();
    </script>
</body>
</html>

