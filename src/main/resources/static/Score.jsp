<!DOCTYPE html>
<html xmlns:th="https://thymeleaf.org">
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<link href="assets/dist/css/bootstrap.min.css" rel="stylesheet"> <!-- Bootstrap core CSS -->
<link href="assets/dist/css/score.css" rel="stylesheet"> <!-- Custom styles for this template -->
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
        <div id="searchForm" class="float-right">
            <select id="selectFilter" class="p-1 m-1">
                <option value="10">조건없음</option>
                <option value="0">학번</option>
                <option value="1">이름</option>
                <option value="2">전공학과</option>
                <option value="3">레벨</option>
                <option value="4">시험완료일</option>
            </select>
            <input type="text" id="keyword">
            <input type="button" id="searchButton" value="search"/>
        </div>
        <table class="table table-bordered my-2" id="scoreList">
            <thead class="text-center">
            <tr>
                <th>학번</th>
                <th>이름</th>
                <th>전공학과</th>
                <th>레벨</th>
                <th class="text-center">시험 완료일</th>
                <th>답안지 확인</th>
                <th>채점 현황</th>
            </tr>
            </thead>
            <tbody>
            <tr th:each="s:${scoreList}">
                <td th:text="*{s.userId}"></td>
                <td th:text="*{s.userName}"></td>
                <td th:text="*{s.userMajor}"></td>
                <td th:text="*{s.userLevel}"></td>
                <td th:text="*{s.userSubmitDt}" class="text-center"></td>
                <td><a th:href="|/score/*{s.userId}|">확인</a></td>
                <td th:text="*{s.userScoreAll}"></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<script th:inline="javascript">
    $(document).ready(function () {
        let $table = $("#scoreList").DataTable({
            //표시 건수 설정
            lengthMenu: [1,3,5]
            , displayLength:5

            //언어 설정
            , language: {
                info: "_START_ - _END_ (총 _TOTAL_ 명)"
                , infoFiltered : "(전체 _MAX_ 명 중 검색결과)"
                , infoEmpty : "0명"
                , emptyTable : "데이터가 없습니다."
                , zeroRecords: "검색된 데이터가 없습니다."
                , lengthMenu: "_MENU_ 개씩 보기"
                , paginate: {
                    "previous": "이전"
                    , "next": "다음"
                }
            }
        });

        //기본 검색기능 없애고, 커스텀 input/btn 으로 검색기능 대체
        $(".dataTables_filter").remove();
        $("#searchButton").click(function () {
            $table.columns("").search("").draw();
            $table.columns(Number($("#selectFilter").val())).search($("#keyword").val()).draw();
        });
    });
</script>
</body>
</html>