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
        <span><b>학번 : </b><span th:text="${id}"></span></span>
    </div>
    <hr>
    <div class="card-body">
        <div class="mb-5">
            <h5>객관식 채점 결과</h5>
            <div class="float-right my-2">
                <span>객관식 점수 : </span>
                <input type="text" disabled value="100"/>
            </div>
            <table class="table table-bordered">
                <th:block th:each="mcqr : ${mcQResult}">
                    <tr>
                        <td class="col-1 font-weight-bold">문제</td>
                        <td th:text="${mcqr.key}"></td>
                    </tr>
                    <tr>
                        <td class="col-1 font-weight-bold">학생 답안</td>
                        <td th:text="${mcqr.value}"></td>
                    </tr>
                </th:block>
            </table>
        </div>
        <hr>
        <div class="mb-5">
            <h5>프로그래밍 문제 답안</h5>
            <div class="float-right my-2">
                <input type="number" id="scorePq" placeholder="프로그래밍 점수 부여"/>
                <input type="button" id="scorePqBtn" value="점수 등록"/>
            </div>
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
        <hr>
        <div class="mb-5">
            <h5>주관식 문제 답안</h5>
            <div class="float-right my-2">
                <input type="number" id="scoreSq" placeholder="주관식 점수 부여"/>
                <input type="button" id="scoreSqBtn" value="점수 등록"/>
            </div>
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

        $("#scorePqBtn").click(function () {
            let scorePq = $("#scorePq").val();
            if(scorePq.length==0){
                alert("해당 답안의 점수를 입력해주세요.");
                return;
            }

            btnToggle("scorePq");
            $.ajax({
                type: "POST"
                , url: "/insertScorePq"
                , data: {
                    "scorePq": scorePq
                }
            })
        });

        $("#scoreSqBtn").click(function () {
            let scoreSq = $("#scoreSq").val();
            if(scoreSq.length==0){
                alert("해당 답안의 점수를 입력해주세요.");
                return;
            }

            btnToggle("scoreSq");
            $.ajax({
                type: "POST"
                , url: "/insertScoreSq"
                , data: {
                    "scoreSq": scoreSq
                }
            })
        });

        function btnToggle(id) {
            let score = $("#" + id);
            let btn = $("#" + id+"Btn");

            if(score.attr("disabled")) {
                score.attr("disabled", false);
                btn.val("점수 등록");
            } else {
                score.attr("disabled", true);
                btn.val("점수 수정");
            }
        }
    });
</script>
</body>
</html>