<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng Nhập</title>
<base href="${pageContext.servletContext.contextPath }/" />
<link rel="icon" href="resource/images/logo.png" type="image/x-icon" />
<!-- <link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	rel="stylesheet"> -->
<link href="resource/assets/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="resource/assets/dist/css/login.css" rel="stylesheet">

<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">
<script src="https://kit.fontawesome.com/56d73d7086.js"
	crossorigin="anonymous"></script>
<script src='https://www.google.com/recaptcha/api.js'></script>

</head>
<body>
	<div class="container">
		<div class="container-fluid px-1 px-md-5 px-lg-1 px-xl-5 py-5 mx-auto">
			<div class="card card0 border-0">
				<div class="row d-flex">
					<div class="col-lg-7">
						<div class="card1">
							<div class="row logo-content mt-4 " style="margin-left: 38px">
								<img
									src="https://user-images.githubusercontent.com/81857289/161365448-2d3a897e-8a96-4525-86b5-a7aa0cd7bb4f.png"
									class="logo">
								<div class="logo-text">
									<h5>Học Viện Công Nghệ Bưu Chính Viễn Thông</h5>
									<h5>Cơ sở TP.HCM</h5>
								</div>
							</div>
						</div>
						<div
							class="row px-3 img-bk justify-content-center mt-4 mb-5 border-line">
							<img
								src="https://user-images.githubusercontent.com/81857289/161364965-b845a6d4-d9b6-4510-b344-1f0d0ce8e0ff.png"
								class="image">
						</div>
					</div>

					<div class="col-lg-5">
						<div class="card2 card border-0 px-4 py-4">
							<div class="row mb-1 px-3">
								<div class="logo-sign-in">
									<img
										src="https://www.smarteyeapps.com/demo/educational-bootstrap-5-login-page-tempalte/assets/images/user.png">
								</div>
							</div>
							<div class="row px-3 mb-4">
								<div class="line"></div>
								<small class="or text-center">Sign In</small>
								<div class="line"></div>
							</div>
							<form:form action="index.htm" method="post"
								modelAttribute="account">
								<div class="row px-3 mb-4">
									<label class="mb-1">
										<h6 class="mb-0 text-sm">Tên Đăng Nhập</h6>
									</label>
									<form:input type="text" path="id" placeholder="Nhập Username" />
									<form:errors path="id" cssClass="errors"></form:errors>
								</div>
								<div class="row px-3">
									<label class="mb-1">
										<h6 class="mb-0 text-sm">Mật Khẩu</h6>
									</label>
									<form:input type="password" path="pass"
										placeholder="Nhập Password" />
									<form:errors path="pass" cssClass="errors"></form:errors>
								</div>
								<div class="row px-3 mb-4">
									<!-- 			<div
										class="custom-control custom-checkbox custom-control-inline">
										<input id="chk1" type="checkbox" name="chk"
											class="custom-control-input"> <label for="chk1"
											class="custom-control-label text-sm">Remember me</label>
									</div>
									<a href="#" class="ms-auto mb-0 text-sm">Forgot Password?</a> -->
								</div>

								<div class="g-recaptcha d-flex justify-content-center"
									data-sitekey="6LcyNQMfAAAAANfcuBz9e1G4FIbW755pqOfvY6X_"></div>

								<label class="mb-1">
									<h6 class="text-sm errors">${tb }</h6>
								</label>
								<div class="row mb-3 px-3 d-flex justify-content-center">
									<button type="submit" class="btn-hover color-1 text-center">Login</button>
								</div>
							</form:form>
							<div class="row mb-4 px-3">
								<!-- <small class="font-weight-bold">Don't have an account? <a
									class="text-danger ">Register</a>
								</small> -->
							</div>
						</div>
					</div>
				</div>
				<div class="bg-blue py-4">
					<div class="row px-3">
						<small class="ms-4 ms-sm-5 mb-2 w-auto"
							style="font-size: 80%; font-weight: 400;">Copyright
							&copy; 2019. All rights reserved.</small>
						<div class="social-contact ms-4 ms-sm-auto w-auto">
							<a href="https://uis.ptithcm.edu.vn/"><i
								class="fa-solid fa-globe me-4 "></i></a> <a
								href="https://www.facebook.com/tphcmptit"><i
								class="fa-brands fa-facebook me-4"></i></a> <a
								href="tel:028 3829 7220"><i class="fa-solid fa-phone me-4"></i></a>
							<a href="mailto:pctsv@ptithcm.edu.vn "><i
								class="fa-solid fa-envelope me-4"></i></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>