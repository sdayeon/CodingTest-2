<!DOCTYPE html>
<html xmlns:th="https://thymeleaf.org">
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<link href="assets/dist/css/bootstrap.min.css" rel="stylesheet"> <!-- Bootstrap core CSS -->
<link href="assets/dist/css/notice.css" rel="stylesheet"> <!-- Custom styles for this template -->
<head>
    <meta charset="UTF-8">
    <title>Notice</title>
</head>
<body>
<div class="card div-notice">
    <div class="card-body">
        <p>
            - Chrome 브라우저에서 최적화되어있습니다. 모바일 환경은 지원하지 않습니다.<br>
            - 브라우저 창을 닫거나, 페이지 새로고침, 뒤로가기 버튼 클릭 시, 시험이 초기화됩니다.<br>
            - 프로그래밍 문제의 답안은 개별로 저장이 가능합니다.<br>
            - 시험지 하단 '제출하기' 버튼 클릭 시, 시험이 종료되며, 더이상 답안 수정이 불가합니다.<br>
        </p>
        <a class="m-2" href="/">뒤로가기</a>
        <a class="m-2" href="/test">확인했습니다.</a>
    </div>
</div>
<script th:inline="javascript">
</script>
</body>
</html>