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
<th:block th:each="q:${question}" class="p-1 m-3">
    <span th:text="${q.mcqSeq}"></span>. <label th:text="${q.mcqQuestion}" class="mb-3"></label>
    <p><input type="radio" th:name="${q.mcqSeq}" th:text="${q.mcqOption1}" value="1" class="p-1"/></p>
    <p><input type="radio" th:name="${q.mcqSeq}" th:text="${q.mcqOption2}" value="2" class="p-1"/></p>
    <p><input type="radio" th:name="${q.mcqSeq}" th:text="${q.mcqOption3}" value="3" class="p-1"/></p>
    <p><input type="radio" th:name="${q.mcqSeq}" th:text="${q.mcqOption4}" value="4" class="p-1"/></p>
    <p><input type="radio" th:name="${q.mcqSeq}" th:text="${q.mcqOption5}" value="5" class="p-1"/></p>
</th:block>
<input type="button" class="btn btn-outline-primary" id="summit" value="제출하기"/>

<script th:inline="javascript">
    $(document).ready(function () {
        $("#summit").click(function (){
            let qSize = [[${question.size()}]];
            let qString ="";

            for(let q=1; q<=qSize; q++){
                let qChecked = document.getElementsByName(q.toString());
                for(let c of qChecked){
                    if(c.checked==true) {
                        let concatStr = "\""+q.toString()+"\":\""+c.value+"\"";
                        qString = qString.concat(concatStr);
                    }
                }

                if(q!==qSize)
                    qString = qString.concat(",");
                else
                    qString = "{"+qString+"}";
            }

            $.ajax({
                type: "POST"
                , url: "/summitMCQ"
                , data: {
                    "userSeq":2
                    , "mcqResult": qString
                }
                , success: function (data){
                    alert("제출되었습니다.");
                    window.location.reload();
                }
            });
        });
    });
</script>
</body>
</html>