<!DOCTYPE html>
<html xmlns:th="https://thymeleaf.org">
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<link href="assets/dist/css/bootstrap.min.css" rel="stylesheet"> <!-- Bootstrap core CSS -->
<link href="assets/dist/css/signin.css" rel="stylesheet"> <!-- Custom styles for this template -->

<head>
  <meta charset="UTF-8">
  <title>Login</title>
  <style>
    .bd-placeholder-img {
      font-size: 1.125rem;
      text-anchor: middle;
      -webkit-user-select: none;
      -moz-user-select: none;
      -ms-user-select: none;
      user-select: none;
    }

    @media (min-width: 768px) {
      .bd-placeholder-img-lg {
        font-size: 3.5rem;
      }
    }
  </style>
</head>

<body class="text-center">
  <form class="form-signin" action="/login" method="post">
    <h1 class="h3 mb-3 font-weight-normal">2023-2<br>코딩 테스트</h1>

    <input type="text" id="inputEmail" name="userId" class="form-control" placeholder="ID" value="dy1" required autofocus>
    <input type="password" id="inputPassword" name="userPassword" class="form-control" placeholder="Password" value="11" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
  </form>

  <script th:inline="javascript">
    $(document).ready(function () {
      let errorMsg = [[${error}]];
      if(errorMsg!=null) alert(errorMsg);
    });
  </script>
</body>
</html>