<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<title>Car</title>
<script>


	function fillPage() {
		var carJson = '${car}'
		var userJson = '${user}'
		var user = jQuery.parseJSON(userJson);
		var car = jQuery.parseJSON(carJson);
		$('#carData').html(fillFeatures(car));
		$('#carousel-inner').html(fillCarousel(car));
		$('#desc').html(fillDescription(car));
		$('#owner').html(fillOwnerInfo(user));
		
	}
	function fillDescription(car) {
		return '<label for="description"><h3>Описание:</h3></label>'
				+ '<p id="description">' + car.description + '</p>'
	}

	function fillCarousel(car) {
		var result = "";
		var ctx = "${pageContext.request.contextPath}";
		if (car.images.length === 0) {
			result = '<div class="carousel-item active">'
					+ '<img src="../upload/photo/default.png" alt="нет изображения" height="500" width="auto"></div>';
		} else if (car.images.length === 1) {
			result = '<div class="carousel-item active">'
					+ '<img src="../upload/photo/' + car.images[i] + '" alt="нет изображения" height="500" width="auto"></div>';
		} else {
			result = '<div class="carousel-item active">'
					+ '<img src="../upload/photo/' + car.images[0] + '" alt="нет изображения" height="500" width="auto"></div>';
			for (var i = 1; i < car.images.length; i++) {
				result += '<div class="carousel-item">'
						+ '<img alt="нет изображения" src="../upload/photo/' + car.images[i] + '" height="500" width="auto"></div>';
			}
		}
		return result;
	}
	function fillFeatures(car) {
		return '<thead><tr class="d-flex"><th class="col-sm-3"><h4>'
				+ car.brand
				+ '</h4></th><th class="col-sm-9"><h4>'
				+ car.model
				+ '</h4></th></tr>'
				+ '<tr class="d-flex"><th class="col-3"><h5>Цена: </h5></th><th class="col-9"><h5>'
				+ car.price
				+ '</h5></th></tr>'
				+ '<tr class="d-flex"><th class="col-3"><h5>Двигатель: </h5></th><th class="col-9"><h5>'
				+ car.engine.volume
				+ ' '
				+ car.engine.type.value
				+ '</h5></th></tr>'
				+ '<tr class="d-flex"><th class="col-3"><h5>Мощность: </h5></th><th class="col-9"><h5>'
				+ car.engine.power
				+ '</h5></th></tr>'
				+ '<tr class="d-flex"><th class="col-3"><h5>КПП: </h5></th><th class="col-9"><h5>'
				+ car.shiftGear.value + '</h5></th></tr></thead>';
		+'<tr class="d-flex"><th class="col-3"><h5>Год: </h5></th><th class="col-9"><h5>'
				+ car.shiftGear.year + '</h5></th></tr></thead>';
		+'<tr class="d-flex"><th class="col-3"><h5>Тип кузова : </h5></th><th class="col-9"><h5>'
				+ car.body.type + '</h5></th></tr></thead>';
	}

	function fillOwnerInfo(user) {
		return '<label for="user"><h3>Контакты:</h3></label>'
				+ '<p id="user"><h4>' + user.name + '</h4><h5>Телефон: '
				+ user.phone + '</h5></p>'
	}

	window.onload = function() {
		fillPage();
	}
</script>
<style>
.carousel-inner img {
	max-height: 500px;
	width: auto;
}

.container {
	background-color: linen;
}

.navbar {
	margin-bottom: 5px;
}

.hidden {
	display: none;
}
</style>
</head>
<body>
	<div class="container">
		<%@ include file = "navbar.jsp" %>
		<div id="photo" class="carousel slide col-8" data-ride="carousel"
			data-interval="false">
			<!-- The slideshow -->
			<div class="carousel-inner" id="carousel-inner"></div>

			<!-- Left and right controls -->
			<a class="carousel-control-prev" href="#photo" data-slide="prev">
				<span class="carousel-control-prev-icon"></span>
			</a> <a class="carousel-control-next" href="#photo" data-slide="next">
				<span class="carousel-control-next-icon"></span>
			</a>
		</div>
		<div class="row">
			<div class="col">
				<label for="carData"><h3>Характеристики:</h3></label>
				<table class="table table-borderless " id="carData"></table>
			</div>
			<div class="col" id="owner"></div>
		</div>
		<div id="desc"></div>
	</div>
</body>
</html>