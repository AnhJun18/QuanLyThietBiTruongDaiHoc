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

<body>
	<%@include file="/WEB-INF/views/include/slidebar.jsp"%>
	<section class="home">
		<div id="toast" class="${message}"></div>

		<div class="text">Quản Trị</div>
		<div class="container mt-2">

			<div class="form-contain row col-10" style="color: var(--text-color);">
				<form:form modelAttribute="NhanVien" method="post">
					<form:input path="maNV" type="hidden" id="ip-maNV" class="ip-form" />
					<div class="row">
						<div class="col-6">
							Họ tên
							<form:input path="tenNV" type="text" class="ip-form disable-ip"  required="required"
								id="ip-tenNV" placeholder="Vui Lòng Nhập Mã Loại"
								readonly="true" />
						</div>
						<div class="col-6">
							CMND
							<form:input path="cmnd" type="text" class="ip-form disable-ip"  required="required"
								id="ip-cmnd" placeholder="Vui Lòng Nhập CMND" readonly="true" />
						</div>
					</div>
					<div class="row">
							<div class="col-6">
								SDT
								<form:input path="sdt" type="text" class="ip-form disable-ip"  required="required"
									id="ip-sdt" placeholder="Vui Lòng Nhập SDT" readonly="true" />
							</div>
							<div class="col-6">
								Email
								<form:input path="email" type="text" class="ip-form disable-ip"  required="required"
									id="ip-Email" placeholder="Vui Lòng Nhập Tên Loại"
									readonly="true" />
							</div>
						</div>
						<label> Tên Tài Khoản </label>
						<form:input path="TaiKhoan.id" type="text"  required="required"
							class="ip-form disable-ip" id="ip-taikhoan"
							placeholder="Vui Lòng Tài Khoản" readonly="true" />
						<button id="btnsave-nv" name="btnadd" class="btn-modal" style="background: gray;" disabled>THÊM</button>
				</form:form>

			</div>

			<button class="btn-add btn btn-primary" onclick="enableForm()">
				<i class="fa-solid fa-plus"></i> THÊM
			</button>

			<div class="d-flex justify-content-center align-items-center mt-2  ">
				<div class="row col-lg-10 contain-table">
					<table class="table" id="tb1">
						<thead>
							<tr>
								<th scope="col">STT</th>
								<th scope="col">Tên</th>
								<th scope="col">CMND</th>
								<th scope="col">SĐT</th>
								<th scope="col">Email</th>
								<th scope="col">Tài Khoản</th>
								<th scope="col">Sửa</th>
								<th scope="col">Reset</th>
							</tr>

						</thead>
						<tbody>
							<c:forEach items="${listNV}" var="pd" varStatus="i">
								<tr id="${pd.maNV }">
									<td>${i.index+1}</td>
									<td>${pd.tenNV}</td>
									<td>${pd.cmnd}</td>
									<td>${pd.sdt}</td>
									<td>${pd.email}</td>
									<td>${pd.taiKhoan.id}</td>
									<td><i class="editbtn fa-solid fa-pen-to-square"></i></td>
									<td><a href="administration/resetpass_id=${pd.maNV}.htm"><i
											class="fas fa-repeat"></i></a></td>

								</tr>

							</c:forEach>
						</tbody>
					</table>


				</div>
			</div>

		</div>
	</section>


</body>
<%@include file="/WEB-INF/views/include/foot.jsp"%>
<style>
.disable-ip {
	cursor: default;
	background-color: #e8e7e7;
	border-color: rgba(118, 118, 118, 0.3);
}

.ip-form {
	border: 1px solid #ccc;
	width: 100%;
	padding: 10px;
	font-size: 15px;
	margin-bottom: 24px;
}

.form-contain {
	margin-left: 8.5%;
	padding: 14px 0px;
	border-style: ridge;
}

.btn-add {
	margin-left: 8.5%;
	margin-top: 24px;
}
</style>
<script>
	// toast thông báo
	var tb = document.getElementById('toast');
	if (tb.classList.contains('success')) {
		showtoast({
			title : "Thành công!",
			message : "'${action}' ",
			type : "success",
			duration : 3000
		});
	} else if (tb.classList.contains('error')) {
		showtoast({
			title : "Lỗi!",
			message : "${action}",
			type : "error",
			duration : 3000
		});

	}
   function enableForm() {
	 let ds=  document.getElementsByClassName("ip-form")
	 for (let t of ds) {
		t.removeAttribute("readonly")
		t.classList.remove("disable-ip")
		t.value = null
	}
	 document.getElementById("btnsave-nv").removeAttribute("disabled")
	document.getElementById("btnsave-nv").style.background='#009688';
	 document.getElementById("btnsave-nv").setAttribute('name','btnadd')
	document.getElementById("btnsave-nv").innerHTML = 'Thêm'
}
	// load dữ liệu lên modal khi ấn edit
	var editbtn = document.getElementsByClassName("editbtn");
	var table = document.getElementById('tb1');

	for (let index = 0; index < editbtn.length; index++) {
		editbtn[index].onclick = function() {
			enableForm();
			document.getElementById("btnsave-nv").setAttribute('name','btn-save-edit')
			document.getElementById("btnsave-nv").innerHTML = 'Lưu Chỉnh Sửa'
			document.getElementById("ip-maNV").value = table.rows[index + 1].getAttribute("id")
			document.getElementById("ip-tenNV").value = table.rows[index + 1].cells[1].innerHTML
			document.getElementById("ip-cmnd").value = table.rows[index + 1].cells[2].innerHTML
			document.getElementById("ip-sdt").value = table.rows[index + 1].cells[3].innerHTML
			document.getElementById("ip-Email").value = table.rows[index + 1].cells[4].innerHTML
			document.getElementById("ip-taikhoan").value = table.rows[index + 1].cells[5].innerHTML
			document.getElementById("ip-taikhoan").setAttribute("readonly","true");
		
		};
	}
</script>
</html>