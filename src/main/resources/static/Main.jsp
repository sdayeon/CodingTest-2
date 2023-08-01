<!DOCTYPE html>
<html xmlns:th="https://thymeleaf.org">
<html xmlns:c="http://java.sun.com/jsp/jstl/core">
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css"/>
<head>
    <meta charset="UTF-8">
    <title>Main</title>
</head>
<body class="p-3 w-50">
<div th:each="q : ${question}" class="p-1 m-3">
    <label th:text="*{q.mcqQuestion}" class="mb-3"></label>
    <p><input type="radio" th:name="*{q.mcqId}" th:text="*{q.mcqOption1}" class="p-1"/></p>
    <p><input type="radio" th:name="*{q.mcqId}" th:text="*{q.mcqOption2}" class="p-1"/></p>
    <p><input type="radio" th:name="*{q.mcqId}" th:text="*{q.mcqOption3}" class="p-1"/></p>
    <p><input type="radio" th:name="*{q.mcqId}" th:text="*{q.mcqOption4}" class="p-1"/></p>
    <p><input type="radio" th:name="*{q.mcqId}" th:text="*{q.mcqOption5}" class="p-1"/></p>
</div>
</body>
</html>