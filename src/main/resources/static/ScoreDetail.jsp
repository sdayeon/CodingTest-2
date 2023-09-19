<!DOCTYPE html>
<html xmlns:th="https://thymeleaf.org">
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<link href="/assets/dist/css/bootstrap.min.css" rel="stylesheet"> <!-- Bootstrap core CSS -->
<link href="/assets/dist/css/scoreDetail.css" rel="stylesheet"> <!-- Custom styles for this template -->
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.5/css/jquery.dataTables.css" /> <!-- Datatable -->
<script src="https://cdn.datatables.net/1.13.5/js/jquery.dataTables.js"></script> <!-- Datatable -->
<head>
    <meta charset="UTF-8">
    <title>Score</title>
</head>
<body>
<div class="card div-score">
    <div class="div-userInfo">
        <h3>2023 코딩역량테스트 채점페이지</h3>
    </div>
    <hr>
    <div class="card-body">
        <span><b>학번 : </b><span th:text="${id}"></span></span>
        <div class="my-5">
            <h5>프로그래밍 문제 답안</h5>
            <table class="table table-bordered">
                <th:block th:each="pqr : ${pQResult}">
                    <tr>
                        <td class="col-1 font-weight-bold">문제</td>
                        <td th:text="${pqr.key}"></td>
                    </tr>
                    <tr>
                        <td class="col-1 font-weight-bold">학생 답안</td>
                        <td th:text="${pqr.value}"></td>
                    </tr>
                </th:block>
            </table>
        </div>
        <div class="my-5">
            <h5>주관식 문제 답안</h5>
            <table class="table table-bordered">
                <th:block th:each="sqr : ${sQResult}">
                    <tr>
                        <td class="col-1 font-weight-bold">문제</td>
                        <td th:text="${sqr.key}"></td>
                    </tr>
                    <tr>
                        <td class="col-1 font-weight-bold">학생 답안</td>
                        <td th:text="${sqr.value}"></td>
                    </tr>
                </th:block>
            </table>
        </div>
    </div>
</div>
<button type="button" class="btn btn-primary my-2" id="backBtn">뒤로 가기</button>
<script th:inline="javascript">
    $(document).ready(function () {
        $("#backBtn").click(function () {
            location.href="/score";
        });
    });
</script>
</body>
</html>