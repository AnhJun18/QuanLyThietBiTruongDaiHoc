<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/head.jsp"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags"%>
<!-- Google Fonts -->
<link href="https://fonts.gstatic.com" rel="preconnect">

<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<body>
	<%@include file="/WEB-INF/views/include/slidebar.jsp"%>
	<section class="home">
		<div class="text">Phòng - Thiết Bị</div>
		<div id="toast" class="${message}"></div>


		<div class="container">
			<button class="btn-add btn btn-primary" style="margin-left: 9%; background-color:#58bf83b3;"
				onclick="showBuyTicket()">
				<i class="fa-solid fa-plus"></i> THÊM THIẾT BỊ
			</button>

			<button class="btn-add btn btn-danger ms-lg-5 " style="background-color: #58bf83b3;"
				onclick="showFormAddRoom()">
				<i class="fa-solid fa-plus"></i> THÊM PHÒNG
			</button>

			<div class="d-flex justify-content-center align-items-center mt-4  ">
				<div class="row col-lg-10">
					<table class="table mb-0">
						<thead>
							<tr>
								<th style="width: 10%">STT</th>
								<th style="width: 30%">Mã Thiết Bị</th>
								<th style="width: 25%">Loại Thiết Bị</th>
								<th style="width: 15%">Trạng Thái</th>
								<th style="width: 20%">Cập Nhật</th>
							</tr>

						</thead>
					</table>
					<c:forEach items="${listPhong}" var="phong">
						<div class="cate  px-0">
							<div class="cate__title d-flex justify-content-between px-4" style="align-items: center;">
								<div class="" style="width: 20%">${phong.maPhong}</div>
								<i class="text-center cate-icon fa-solid fa-chevron-down"></i>
								<div class="d-flex justify-content-around align-items-center"
									style="width: 40%">
									<c:if test="${ phong.trangThai =='2'}">
										<a href="room/maintenance_id=${phong.maPhong}_completed.htm" style="background: #a42832"
											class=" cate-link btn-danger text-decoration-none py-1 text-center  px-3 me-2">Kết
											thúc bảo trì </a>
									</c:if>
									<c:if test="${ phong.trangThai =='0'}">
										<a href="room/maintena_id=${phong.maPhong}.htm"
										  style="background: #915358"
											class=" cate-link btn-danger text-decoration-none py-1 text-center  px-3 me-2">Bảo
											trì </a>
									</c:if>

								</div>
									<a class="cate-link" href="room/delete_id=${phong.maPhong}.htm"><i
										class="fas fa-trash-alt"></i></a> <a class="cate-link"
										href="room/device_detail=${phong.maPhong}_pdf.htm"
										target="_blank"><i class="fa-solid fa-print"></i></a>
							</div>
							<div class="cate__table contain-table">
								<table class="table">
									<c:forEach items="${phong.dstb}" var="pd" varStatus="i">
										<tr>
											<td style="width: 10%">${i.index+1}</td>
											<td style="width: 30%">${pd.maTBi}</td>
											<td style="width: 25%">${pd.loaiTBi.tenLoai }</td>
											<td style="width: 15%">${pd.tinhTrangTB =='0'?'Sẵn Sàng':pd.tinhTrangTB ==1?'Cho Mượn':pd.tinhTrangTB ==2?'Bảo trì':'Mất'}</td>
											<td style="width: 20%"><c:if
													test="${ pd.tinhTrangTB =='0'}">
													<a href="device_detail/repair_device_id=${pd.maTBi}.htm"
														class="btn-danger text-decoration-none py-1 text-center  px-3 me-2">Sửa</a>
													<a href="device_detail/lost_device_id=${pd.maTBi}.htm"
														class="btn-primary text-decoration-none text-center py-1 px-3 ">Mất</a>
												</c:if> <c:if test="${ pd.tinhTrangTB =='2'}">
													<a
														href="device_detail/cpl_repair_device_id=${pd.maTBi}.htm"
														class="btn-primary text-decoration-none text-center py-1 px-3 ">Xong</a>

												</c:if></td>
										</tr>
									</c:forEach>

								</table>
							</div>
						</div>
					</c:forEach>
				</div>

			</div>




		</div>


	</section>
	<!-- Modal Add Device-->
	<div class="modal js-modal">
		<div class="modal-container js-modal-contain" style="width: 820px;">
			<div class="modal-close js-modal-close">
				<i class="fa-solid fa-xmark"></i>
			</div>
			<header class="modal-header">
				<i class="ti-bag modal-heading-icon"></i> THÊM THIẾT BỊ
			</header>
			<div class="modal-body">
				<form:form modelAttribute="Phong" method="post">
					<label for="quantity-ticket" class="modal-lable"> <i
						class="ti-shopping-cart"></i><h6>Phòng</h6> 
					</label> <form:select path="maPhong" items="${listPhong }"
						itemLabel="maPhong" itemValue="maPhong" required="required"
						class="form-select-lg form-select mb-2" /> <label
						for="quantity-ticket" class="modal-lable mt-3">  <h6>Thiết Bị Trống</h6> 
					</label>
					<div class="d-flex justify-content-around flex-wrap mb-4">
				<c:forEach items="${listTypeTB}" var="ps" varStatus="i">
						<div style="overflow: auto; max-height: 260px ;">
						<b>${ps.tenLoai }</b>
						<c:forEach items="${listTB}" var="pt" varStatus="i">
							<c:if test="${ps.maLoai==pt.loaiTBi.maLoai }">
								<div class="form-check">

									<label class="form-check-label" for="flexCheckDefault">
										${pt.maTBi } </label> <input name="${pt.maTBi }"
										class="form-check-input" type="checkbox" value=""
										id="flexCheckDefault">

								</div>
							</c:if>

						</c:forEach>
						</div>
					</c:forEach>
					</div>
					<button id="btnsave" class="btn-modal">THÊM</button>
				</form:form>

						<footer class="modal-footer"> </footer>
			</div>

		</div>
	</div>


	<!-- Modal Add Room-->
	<div class="modal js-modal-2">
		<div class="modal-container js-modal-contain-2" style="width: 620px;">
			<div class="modal-close js-modal-close-2">
				<i class="fa-solid fa-xmark"></i>
			</div>
			<header class="modal-header"> THÊM PHÒNG </header>
			<div class="modal-body">
				<form action="room/add.htm" method="post">
					<label for="quantity-ticket" class="modal-lable"> <i
						class="ti-shopping-cart"></i> Phòng
					</label> <input name="idroom" type="text" class="modal-ip mb-5 inputID" required="required">
					<button id="btnaddroom" class="btn-modal">THÊM</button>
				</form>


				<footer class="modal-footer"> </footer>
			</div>

		</div>
	</div>

</body>
<style>
.cate__table {
	overflow: auto;
	max-height: 20vh;
}

.cate .cate__title {
	padding: 10px;
	background: var(--primary-color-light);
	cursor: pointer;
	color: var(--text-color);
}

.cate .cate-icon-dow {
	transform: rotate(180deg);
}

.cate .cate__table {
	display: none
}
</style>
<%@include file="/WEB-INF/views/include/foot.jsp"%>
<script>
	${thongbao}
	const modalRoom = document.querySelector('.js-modal-2')
	const modalRoomContain = document.querySelector('.js-modal-contain-2')
	const closeRoom = document.querySelector('.js-modal-close-2')
	function hideModalRoom() {
		modalRoom.classList.remove('open')
	}

	closeRoom.addEventListener('click', hideModalRoom)
	modalRoom.addEventListener('click', hideModalRoom)
	modalRoomContain.addEventListener('click', function(even) {
		even.stopPropagation()
	});
	function showFormAddRoom() {
		var elements = document.getElementsByClassName("modal-ip");
		Array.prototype.forEach.call(elements, function(el) {
			el.value = null
		});
		modalRoom.classList.add('open')
	}
	// toast thông báo
	var tb = document.getElementById('toast');
	if (tb.classList.contains('success')) {
		showtoast({
			title : "Thành công!",
			message : "'${action}'  thành công.",
			type : "success",
			duration : 3000
		});
	} else if (tb.classList.contains('error')) {
		showtoast({
			title : "Lỗi!",
			message : "${action} thất bại.",
			type : "error",
			duration : 3000
		});

	}

	$('.cate').each(function() {
		var $this = $(this);
		$(this).find('.cate__title').click(function() {
			$this.find('.cate__table').slideToggle();
			$this.find('.cate-icon').prop("classList").toggle("cate-icon-dow");
		})
		$(this).find('.cate-link').click(function(even) {
			even.stopPropagation()
		});
	})
</script>
</html>