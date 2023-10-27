<!DOCTYPE html>
<html xmlns:th="https://thymeleaf.org">
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<link href="assets/dist/css/bootstrap.min.css" rel="stylesheet"> <!-- Bootstrap core CSS -->
<link href="assets/dist/css/score.css" rel="stylesheet"> <!-- Custom styles for this template -->
<head>
    <meta charset="UTF-8">
    <title>Dev</title>
    <style>
        pre {
            white-space: pre-wrap;
        }
    </style>
</head>
<body>
<div class="card div-score">
    <h5>프로그래밍 문제</h5>
    <div class="card-body">
        <th:block th:each="r: ${question}" class="p-1 m-3">
            <label th:text="${r.pqQuestion}" class="mb-3"></label>
            <div th:unless="${#strings.isEmpty(r.pqComment1)}">
                <label>조건 및 추가설명</label>
                <pre class="p-3" style="border: solid 1px;" th:text="${r.pqComment1}"></pre>
            </div>
            <div th:unless="${#strings.isEmpty(r.pqComment2)}">
                <label>입출력 설명</label>
                <pre class="p-3" style="border: solid 1px;" th:text="${r.pqComment2}"></pre>
            </div>
            <div class="row">
                <div class="col" th:unless="${#strings.isEmpty(r.pqExInput)}">
                    <lable>입력 예제</lable>
                    <pre class="p-3" style="border: solid 1px;" th:text="${r.pqExInput}"></pre>
                </div>
                <div class="col" th:unless="${#strings.isEmpty(r.pqExOutput)}">
                    <lable>출력 예제</lable>
                    <pre class="p-3" style="border: solid 1px;" th:text="${r.pqExOutput}"></pre>
                </div>
            </div>
            <hr>
        </th:block>
    </div>

    <hr>
    <h5>응시 완료된 학생들의 프로그래밍 답안</h5>
    <div class="card-body">
        <table class="table table-bordered">
            <th:block th:block th:each="r : ${result}">
            <tr>
                <td class="col-1 font-weight-bold">문제</td>
                <td th:text="${r.pQuestion.pqQuestion}"></td>
            </tr>
            <tr>
                <td class="col-1 font-weight-bold">학생정보</td>
                <td>
                    <span th:text="${r.user.userId}"></span>,
                    <span th:text="${r.user.userName}"></span>,
                    <span th:text="${r.user.userLevel}"></span>
                </td>
            </tr>
            <tr>
                <td class="col-1 font-weight-bold">답안</td>
                <td>
                    <textarea type="text" class="p-1 w-100" rows="10" readonly
                              onkeydown="if(event.keyCode===9){var v=this.value,s=this.selectionStart,e=this.selectionEnd;this.value=v.substring(0, s)+'\t'+v.substring(e);this.selectionStart=this.selectionEnd=s+1;return false;}"
                              th:text="${r.pqResult}">
                    </textarea>
                </td>
            </tr>
            </th:block>
        </table>
    </div>
</div>
</body>
</html>