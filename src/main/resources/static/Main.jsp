<!DOCTYPE html>
<html xmlns:th="https://thymeleaf.org">
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css"/>
<head>
    <meta charset="UTF-8">
    <title>Main</title>
</head>
<body class="p-3 w-50">
<div class="p-1 m-1">
    응시자 : <span th:text="${userInfo?.userId}"></span><br>
    레벨 : <span th:text="${userInfo?.userLevel}"></span><br>
    <p id="timer"></p>
</div><hr><br>

<h5>주관식</h5>
<th:block th:each="sq, sqq: ${sQuestion}" class="p-1 m-3">
    <span th:text="${sqq.count}"></span>. <label th:text="${sq.sqQuestion}" class="mb-3"></label>
    <div th:if="${not #strings.isEmpty(sq.sqImg)}">
        <img th:src="${sq.sqImg}" class="w-50 p-1"/>
    </div>
    <p><input type="text" th:id="${sqq.count}" th:seq="${sq.sqSeq}" class="p-1 w-75"/></p>
</th:block>

<br><hr><br>

<h5>프로그래밍</h5>
<th:block th:each="pq, pqq: ${pQuestion}" class="p-1 m-3">
    <th:block th:each="pqr, pqrr: ${pQResult}">
        <div th:if="${pq.pqSeq == pqr.pQuestion.pqSeq}">
            <span th:text="${pqq.count}"></span>. <label th:text="${pq.pqQuestion}" class="mb-3"></label>
            <p><textarea type="text" class="p-1 w-100" rows="10"
                         onkeydown="if(event.keyCode===9){var v=this.value,s=this.selectionStart,e=this.selectionEnd;this.value=v.substring(0, s)+'\t'+v.substring(e);this.selectionStart=this.selectionEnd=s+1;return false;}"
                         th:id="|pq_${pqq.count}|" th:seq="${pq.pqSeq}" th:text="${pqr.pqResult}">
            </textarea></p>
            <input type="button" class="btn btn-outline-success" th:id="|savePQ_${pqq.count}|" value="답안 등록"/><br><br>
        </div>
    </th:block>
</th:block>

<br><hr><br>

<h5>객관식</h5>
<th:block th:each="q, qq:${question}" class="p-1 m-3">
    <span th:text="${qq.count}"></span>. <label th:text="${q.mcqQuestion}" class="mb-3"></label>
    <p><input type="radio" th:name="${qq.count}" th:seq="${q.mcqSeq}" th:text="| &#9312; ${q.mcqOption1}|" value="1" class="p-1"/></p>
    <p><input type="radio" th:name="${qq.count}" th:seq="${q.mcqSeq}" th:text="| &#9313; ${q.mcqOption2}|" value="2" class="p-1"/></p>
    <p><input type="radio" th:name="${qq.count}" th:seq="${q.mcqSeq}" th:text="| &#9314; ${q.mcqOption3}|" value="3" class="p-1"/></p>
    <p><input type="radio" th:name="${qq.count}" th:seq="${q.mcqSeq}" th:text="| &#9315; ${q.mcqOption4}|" value="4" class="p-1"/></p>
    <p><input type="radio" th:name="${qq.count}" th:seq="${q.mcqSeq}" th:text="| &#9316; ${q.mcqOption5}|" value="5" class="p-1"/></p>
</th:block>

<br>

<input type="button" class="btn btn-outline-primary" id="submit" value="제출하기"/>

<script th:inline="javascript">
    $(document).ready(function () {
        function submitAction(status){
            //객관식 문제
            let qSize = [[${question}]].length;
            let qString ="";

            for(let q=1; q<=qSize; q++){
                let question = document.getElementsByName(q.toString());
                let concatStr = "";

                let n=0;
                while(n<5){
                    let checkSeq = question[n].getAttribute('seq');     //문제 번호
                    let qValue = question[n].getAttribute('value');     //답안 번호
                    let check = question[n].checked;                    //체크 여부

                    if(check) concatStr = "\""+checkSeq+"\":\""+qValue+"\",";
                    n++;
                }

                qString = qString.concat(concatStr);
            }

            //주관식 문제
            let sqSize = [[${sQuestion}]].length;
            let sqString = "";

            for(let sq=1; sq<=sqSize; sq++){
                let sqSeq = document.getElementById(sq.toString()).getAttribute('seq');
                let sQuestion = document.getElementById(sq.toString()).value;
                sqString = sqString.concat("\""+sqSeq+"\":\""+sQuestion+"\",");
            }


            //프로그래밍 문제
            let pqSize = [[${pQuestion}]].length;
            let pqString = "";

            for(let pq=1; pq<=pqSize; pq++){
                let pqSeqStr = "pq_"+pq;
                let pqSeq = document.getElementById(pqSeqStr).getAttribute('seq');
                let pqResult = document.getElementById(pqSeqStr).value;
                pqString = pqString.concat(pqSeq+":"+pqResult+",,");
            }


            qString = qString.substring(0, qString.lastIndexOf(","));
            sqString = sqString.substring(0, sqString.lastIndexOf(","));
            pqString = pqString.substring(0, pqString.lastIndexOf(",,"));

            $.ajax({
                type: "POST"
                , url: "/submitQuestion"
                , data: {
                    "mcqResult": "{"+qString+"}"
                    , "sqResult": "{"+sqString+"}"
                    , "pqResult": pqString+""
                }
                , success: function (data){
                    if(status==0) alert("시험시간이 종료되었습니다. 입력한 답안만 제출됩니다.");
                    else alert("제출되었습니다.");

                    window.location.href="/finish";
                }
            });
        }

        $("#submit").click(function (){
            submitAction(1);
        });

        //프로그래밍 문제
        $("#savePQ_1").click(function () {
            savePQToggle(1);
            if($(this).val() == "답안 등록")
                return;

            let pqSeq = document.getElementById("pq_1").getAttribute('seq');
            let pqResult = document.getElementById("pq_1").value;

            $.ajax({
                type: "POST"
                , url: "/savePQ_1"
                , data: {
                    "pqSeq" : pqSeq
                    , "pqResult" : pqResult
                }
                , success: function (data){
                    alert("임시저장 되었습니다.");
                }
            });
        });

        $("#savePQ_2").click(function () {
            savePQToggle(2);
            if($(this).val() == "답안 등록")
                return;

            let pqSeq = document.getElementById("pq_2").getAttribute('seq');
            let pqResult = document.getElementById("pq_2").value;

            $.ajax({
                type: "POST"
                , url: "/savePQ_2"
                , data: {
                    "pqSeq" : pqSeq
                    , "pqResult" : pqResult
                }
                , success: function (data){
                    alert("임시저장 되었습니다.");
                }
            });
        });

        function savePQToggle(num){
            let question = $("#pq_"+num);
            let saveBtn = $("#savePQ_"+num);

            if(saveBtn.hasClass("btn-outline-success")){
                saveBtn.removeClass("btn-outline-success");
                saveBtn.addClass("btn-success");
                question.attr("disabled", true);
                saveBtn.val("답안 수정");
            } else {
                saveBtn.removeClass("btn-success");
                saveBtn.addClass("btn-outline-success");
                question.attr("disabled", false);
                saveBtn.val("답안 등록");
            }
        }

        let now = new Date();
        let startDateNum = Date.parse(now)/1000;

        let end = new Date([[${timer}]]);
        let endDateNum = Date.parse(end)/1000;
        let time = endDateNum-startDateNum;

        let x = setInterval(function () {
            let min = parseInt(time/60);
            let sec = time%60;
            document.getElementById("timer").innerHTML = "<span>남은 시간 : "+min+"분 "+sec+"초</span>";
            time--;

            if(min == 0 && sec == 0){
                clearInterval(x);
                submitAction(0);
            }
        },1000);
    });
</script>
</body>
</html>