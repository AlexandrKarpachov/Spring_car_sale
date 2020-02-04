<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
  <title>Sign In</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
  		<c:url var="loginUrl" value="/j_spring_security_check"></c:url>
		<form action="${loginUrl}" method="POST">
  			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	  		<h2>Вход :</h2>
	    	<div class="form-group">
	      		<label for="usr">Логин :</label>
	     		<input type="text" class="form-control" id="login"  name="login" placeholder="login" required>
	    	</div>
		    <div class="form-group">
		      	<label for="pwd">Пароль :</label>
		      	<input type="password" class="form-control" id="password" name="password" placeholder="password" required>
		    </div>
	    	<div class="btn-group col-sm-offset-3">
	        	<button type="submit" class="btn btn-success">Войти</button>
	            <a href="${pageContext.request.contextPath}/registration" 
	            				class="btn btn-success" role="button">Регистрация</a>
	    	</div>
  		</form>
	</div>
</body>
</html>
