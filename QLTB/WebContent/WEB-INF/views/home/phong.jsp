<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/head.jsp"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags"%>
<!-- Google Fonts -->
<!-- Google Fonts -->
<link href="https://fonts.gstatic.com" rel="preconnect">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link href="resource/assets/dist/css/phong.css" rel="stylesheet">
<script src="resource/assets/dist/js/bootstrap.bundle.min.js"></script>
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">

<body>
	<%@include file="/WEB-INF/views/include/slidebar.jsp"%>
	<div class="topbar d-flex align-items-center">
		<div class="box-search">
			<i class="fa-solid fa-magnifying-glass icon"></i> <input type="text"
				placeholder="Nhập phòng..." onkeyup="timphong()" id="search-room">

		</div>

	</div>
	<section class="home mt-5">

		<div class="text py-2">Mượn Trả Thiết Bị</div>

		<div id="toast" class="${typeToast}"></div>

		<div class="container d-flex flex-wrap justify-content-around">
			<c:forEach var="phong" items="${dsPhong }">
				<div id="${phong.maPhong}" class="room card mb-4">
					<div
						class="card-body d-flex flex-column justify-content-between ${phong.trangThai==0 ?'emptys':phong.trangThai==1 ?'buzy':phong.trangThai==2 ?'maintenance' :''}">
						<h5 class="card-title">${phong.maPhong}</h5>
						<div>

							<c:forEach var="ctpm" items="${phong.dspm}">
								<c:if test="${ctpm.thoiDiemTra == null}">
									<div
										style="border-style: groove; border: 0; border-bottom: double;">
										<a class="pmChuaTra card-text" style="text-decoration: none;"
											href="home/bill${ctpm.maPhieuMuon}_${phong.maPhong}.htm"
											phong="${phong.maPhong}" ngaymuon="${ctpm.thoiDiemMuon}"
											hantra="${ctpm.hanTra}" data-bs-toggle="popover"
											data-bs-trigger="hover"
											data-bs-content="Phiếu Mượn :${ctpm.maPhieuMuon} 
						
							 Ngày mượn:${ctpm.thoiDiemMuon}   Hạn Trả: ${ctpm.hanTra}     ">
											<%-- 	PM: ${ctpm.maPhieuMuon} --%>
											<div>${ctpm.lopTC.fullname}</div>
										</a>
									</div>

								</c:if>
							</c:forEach>

						</div>

						<div>
							<c:forEach var="cttb" items="${phong.dsTBChoPhepMuon() }">
								<p class="card-text">${cttb.value}|${cttb.key}</p>


							</c:forEach>
						</div>


						<a href="home/phong/${phong.maPhong }.htm?linkPhong"
							class="btn btn-primary btnLapPhieu ${phong.trangThai==0?'':'disable-link' }">${phong.trangThai==2?'Đang Bảo Trì':'Lập Phiếu'}</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</section>

	<!-- Modal Them Phieu Muon-->
	<div class="modal js-modal ${statusmodal}">
		<div class="modal-container js-modal-contain " style="width: 60%">
			<div class="modal-close js-modal-close">
				<i class="fa-solid fa-xmark"></i>
			</div>

			<header class="modal-header">
				<i class="ti-bag modal-heading-icon"></i>
				<div>Thêm Phiếu Mượn</div>
				<div>
					<p>Phòng - ${roomclicked}</p>
				</div>

			</header>
			<div class="modal-body">
				<form>
					<label class="modal-lable"> Lớp Học </label> <select onchange="checklichsumuon()"
						class="form-select modal-ip" name="loptc"
						aria-label="Disabled select example">
						<c:forEach items="${listLTC}" var="tmp">
							<option value="${tmp.maLTC}">${tmp.monhoc} nhóm
								${tmp.nhom} (${tmp.nienkhoa} hk:${tmp.hocky})</option>
						</c:forEach>

					</select> <label class="modal-lable"> <i class="ti-shopping-cart"></i>Hạn
						Trả
					</label> <input name="hanTra" type="datetime-local" class="modal-ip"
						value="${timeDefault}" min="${timeNow}">
					<button name="btnsave" class="mt-4 btn-modal">THÊM</button>
				</form>


				<!-- 	<footer class="modal-footer"> </footer> -->
			</div>

		</div>
	</div>



	<!-- Modal Chi Tiet Phieu Muon -->
	<div class="modal js-modal-2 ${statusmodal2}">
		<div class="modal-container js-modal-contain-2 ">
			<div class="modal-close js-modal-close-2">
				<i class="fa-solid fa-xmark"></i>
			</div>

			<header class="modal-header">
				<i class="ti-bag modal-heading-icon"></i>
				 <div>Chi Tiết Phiếu Mượn</div>


			</header>
			<div class="modal-body">

				<div class="row mb-3" style="height: 240px">

					<div class="col-sm-6" style="border-style: outset;">
						<h4 class="text-center">Thông tin</h4>
						<p>
							<b>Lớp:</b> ${thongtinPhieuMuon.lopTC.monhoc} nhóm
							${thongtinPhieuMuon.lopTC.nhom}
						</p>
						<p>
							<b>Phòng:</b> ${thongtinPhieuMuon.maPhg.maPhong}
						</p>
						<p>
							Ngày mượn:
							<fmt:formatDate pattern="dd/MM/yyyy HH:mm"
								value="${thongtinPhieuMuon.thoiDiemMuon}"></fmt:formatDate>
						</p>
						<p>
							Hạn Trả:
							<fmt:formatDate pattern="dd/MM/yyyy HH:mm"
								value="${thongtinPhieuMuon.hanTra}"></fmt:formatDate>
						</p>

					</div>

					<div class="col-sm-6 border-end"
						style="overflow: auto; border-style: outset; max-height: 240px;">
						<h4 class="text-center">Danh Sách Mượn</h4>
						<c:forEach var="sv" items="${listSVMuon}">
							<div style="border-style: groove; margin-bottom: 10px; text-align: center;">
								${sv.maSV} ${sv.tenSV }</div>
							<c:forEach var="item"
								items="${thongtinPhieuMuon.listCTPhieuMuon }">
								<c:if test="${sv.maSV== item.svMuon.maSV}">
									<div class=" d-flex flex-row justify-content-between">
										<p>${item.thietBi.loaiTBi.tenLoai}-${ item.thietBi.maTBi}</p>
										<div class="status-bill">
											<c:if test="${ item.trangThai =='3'}">
												<a
													href="home/loss${thongtinPhieuMuon.maPhieuMuon}_${ item.thietBi.maTBi}_${thongtinPhieuMuon.maPhg.maPhong}.htm"
													class="btn-danger text-decoration-none py-1 text-center  px-3 me-2">Mất</a>
												<a
													href="home/pay${thongtinPhieuMuon.maPhieuMuon}_${ item.thietBi.maTBi}_${thongtinPhieuMuon.maPhg.maPhong}.htm"
													class="btn-primary text-decoration-none text-center py-1 px-3 ">Trả</a>
											</c:if>
											<c:if test="${ item.trangThai =='4'}">
												<strong> Đã Trả</strong>
											</c:if>
											<c:if test="${ item.trangThai =='5'}">
												<strong> Đã Mất</strong>
											</c:if>

										</div>

									</div>
								</c:if>
							</c:forEach>

						</c:forEach>


					</div>

				</div>
				<form>
					<div>
						<b class="w-25">Sinh viên mượn</b> <input name="masvmuon"
							class="modal-ip w-75 inputID"  required="required" value="${listSVMuon[0].maSV}"
							placeholder="Nhập sinh Viên">
					</div>

					<div class="d-flex justify-content-around flex-wrap mb-4">

						<c:forEach items="${listTypeTB}" var="ps" varStatus="i">
							<div style="overflow: auto; max-height: 260px; padding: 0 8px;">
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
					<button class="btn btn-primary"
						style="width: 50%; margin-left: 25%; margin-top: 24px;"
						name="savebill" type="submit">Ghi Mượn</button>
				</form>

			</div>

		</div>
	</div>


	<!-- Modal Nhap Thong Tin SV -->
	<div class="modal js-modal-3 ${statusmodal3}">
		<div class="modal-container js-modal-contain-3 ">
			<div class="modal-close js-modal-close-3">
				<i class="fa-solid fa-xmark"></i>
			</div>

			<header class="modal-header">
				<i class="ti-bag modal-heading-icon"></i>
				<div>Thông Tin Sinh Viên</div>


			</header>
			<div class="modal-body">
				<form>
					<label class="modal-lable"> Sinh Viên Mượn </label> <input
						name="masvmuon" id="ip-masv" type="text" class="modal-ip inputID" required="required"
						onchange=checklichsumuon() placeholder="Vui Lòng MSSV"
						value=${SVien } ${SVien==null?'':'readonly'}> <label
						for="ticket-email" class="modal-lable"> Tên Sinh Viên </label> <input
						name="tenSV" type="text" class="modal-ip" required="required"
						placeholder="Vui Lòng Nhâp Tên Sinh Viên"> <label
						class="modal-lable"> <i class="ti-user"></i> Lớp
					</label> <input name="tenLop" type="text" class="modal-ip"
						placeholder="Vui Lòng Nhập Lớp">
					<button class="btn btn-primary"
						style="width: 50%; margin-left: 25%; margin-top: 24px;"
						name="savebill" type="submit">Lưu</button>
				</form>


			</div>

		</div>
	</div>



</body>
<%@include file="/WEB-INF/views/include/foot.jsp"%>
<script src="resource/assets/dist/js/home.js" charset="utf-8"></script>
<script>
${thongbao}
var tb = document.getElementById('toast');
if (tb.classList.contains('success')) {
showtoast({
	title : "Thành công!",
	message : "${action} thành công.",
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
else if (tb.classList.contains('info')){
showtoast({
	title : "Cảnh Báo!",
	message : "${action}",
	type : "warning",
	duration : 3000
});
}


//kiem tra lich su phieu muon chua tra cua sinh vien
const node = document.getElementById('ip-masv')

function checklichsumuon() {
var st = 0;
var dsPhieuMuon = "";
for (let name of document.getElementsByClassName('pmChuaTra')) {
	if (node.value == name.getAttribute("pm")) {
		st++;
		console.log('avv');
	}
}
if (st != 0) {
	 if (confirm("Sinh Vien " + node.value + " có " + st + " phiếu mượn chưa trả" + dsPhieuMuon + "\n Bạn có muốn cho mượn tiếp ko?") == false) {
		hideBuyTicket();

	} 
}

};
//chan viec save form khi an enter
node.addEventListener("keydown", function(event) {
	if (event.key === "Enter") {
		event.preventDefault();
		checklichsumuon();
	}
});

</script>

</html>