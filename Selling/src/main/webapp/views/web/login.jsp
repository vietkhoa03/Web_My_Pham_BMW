<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglist.jsp"%>
<!DOCTYPE html>
<html lang="fr">
<body>
	<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
	<!-- Google fonts-->
	<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700"
		rel="stylesheet" type="text/css" />
	<link
		href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic"
		rel="stylesheet" type="text/css" />
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
		rel="stylesheet">
	<link
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
		rel="stylesheet">
	<!-- Core theme CSS (includes Bootstrap)-->
	<link href='<c:url value="/stylecss/base/basecss.css" />'
		rel="stylesheet" type="text/css">
	<link href='<c:url value="/css/bootstrap.css" />' rel="stylesheet"
		type="text/css">

	<!-- Favicon-->
	<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
	<!-- Google fonts-->
	<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700"
		rel="stylesheet" type="text/css" />
	<link
		href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic"
		rel="stylesheet" type="text/css" />

	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
		rel="stylesheet"
		integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
		crossorigin="anonymous">
	<link rel="stylesheet"
		href="fonts/material-icon/css/material-design-iconic-font.min.css">
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/templates/web/css/main.css">

	<div class="bg__img">
		<div class="container d-flex justify-content-center w-200 min-vh-100"
			style="padding-top: 100px;">

			<!-- login -->

			<div class="row border rounded-5 p-3 bg-white shadow edit"
				style="width: 900px; height: fit-content;">

				<!-- 		left box -->
				<div
					class="col-md-6 rounded-4 d-flex justify-content-center align-items-center flex-column left-box"
					style="background: #908d8d">
					<div class="featured-image md-3">
						<img
							src="${pageContext.request.contextPath}/templates/web/images/Oriflame_logo.jpg"
							class="img-fluid" , style="width: 250px;">
					</div>
					<p class="text-white fs-2 style="font-family: "Poppins"></p>
				</div>

				<!-- right-box -->
				<div class="col-md-6 right-box">
					<div class="row align-items-center">
						<div class="header-text mb-4 text-center">
							<h1 style="font-size: 2rem;">SIGN-IN</h1>
						</div>
						<div class="input-group mb-3">
							<input type="text"
								class="form-control form-control-lg bg-light fs-6"
								placeholder="Phone or Email address">
						</div>
						<div class="input-group mb-4">
							<input type="password"
								class="form-control form-control-lg bg-light fs-6"
								placeholder="Password">
						</div>
						<div class="input-group mb-5 d-flex justify-content-between">
							<div class="form-check">
								<input type="checkbox" class="form-check-input" id="formCheck">
								<label for="formCheck" class="form-check-label text-secondary"><small>Remember
										me</small></label>
							</div>
							<div class="forgot">
								<small><a href="#">Forgot Password?</a></small>
							</div>
						</div>
					</div>
					<div class="input-group mb-3">
						<button class="btn btn-lg btn-primary w-100 fs-6">Login</button>
					</div>
					<div class="input-group mb-5">
						<button class="btn btn-lg btn-light w-100 fs-6">
							<img
								src="${pageContext.request.contextPath}/templates/web/images/google.png"
								style="width: 20px" class="me-2"><small>Sign In
								with Google</small>
						</button>
					</div>
					<div class="row text-center ">
						<small>Don't have account? <a href="register">Sign
								Up</a></small>

					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>