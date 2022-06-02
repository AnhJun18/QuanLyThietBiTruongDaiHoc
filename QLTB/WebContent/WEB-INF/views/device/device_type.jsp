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
		<div class="text">Loại Thiết Bị</div>
		<div id="toast" class="${message}"></div>
		
		
		<div class="container">
			<button class="btn-add btn btn-primary ms-lg-5" onclick="showForm()">
				<i class="fa-solid fa-plus"></i> Thêm
			</button>

			<div class="d-flex justify-content-center align-items-center mt-4  ">
				<div class="row col-lg-10">
					<table class="table" id="tb1">
						<thead>
							<tr>
								<th scope="col">STT</th>
								<th scope="col">Mã Loại</th>
								<th scope="col">Tên Loại</th>
								<th scope="col">Sửa</th>
								<th scope="col">Xóa</th>
							</tr>

						</thead>
						<tbody>
							<c:forEach items="${DsLoaiTB}" var="pd" varStatus="i">
								<tr>
									<td>${ i.index+1}</td>
									<td>${pd.maLoai}</td>
									<td>${pd.tenLoai }</td>
									<td><i class="fa-solid fa-pen-to-square editbtn" style="cursor: pointer;padding: 5px 10px;"
											> </i></a></td>
		
									<td><a href="device_type/index/${pd.maLoai }.htm?linkDelete"
										onclick="return confirm('Xác nhận xóa?') ? true : false;" style="padding: 5px 10px;">
											<i class="fa-solid fa-trash-can"></i>
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>


				</div>
			</div>

		</div>


		<%-- <%@include file="/WEB-INF/views/include/footer.jsp" %> --%>
	</section>
	<!-- Modal -->
	<div class="modal js-modal">
		<div class="modal-container js-modal-contain">
			<div class="modal-close js-modal-close">
				<i class="fa-solid fa-xmark"></i>
			</div>

			<header class="modal-header">
				<i class="ti-bag modal-heading-icon"></i> Thêm Loại TB
			</header>
			<div class="modal-body">
				<form:form modelAttribute="LoaiTB" method="post">
					<label for="quantity-ticket" class="modal-lable"> <i
						class="ti-shopping-cart"></i> Mã Loại
					</label>
					<form:input path="maLoai" type="text" class="modal-ip" id="ip-maloai" required="required"
						placeholder="Vui Lòng Nhập Mã Loại"
						 />
					<label  class="modal-lable"> <i
						class="ti-user"></i> Tên Loại
					</label>
					<form:input path="tenLoai" type="text" class="modal-ip" id="ip-tenloai"  required="required"
						placeholder="Vui Lòng Nhập Tên Loại" />
					<button id="btnsave" class="btn-modal">THÊM</button>
				</form:form>


				<footer class="modal-footer"> </footer>
			</div>

		</div>
	</div>

</body>
<%@include file="/WEB-INF/views/include/foot.jsp"%>
<script>

// toast thông báo
var tb = document.getElementById('toast');
if (tb.classList.contains('success')) {
	showtoast({
		title : "Thành công!",
		message : "'${myparam}' loại thiết bị thành công.",
		type : "success",
		duration : 3000
	});
} else if (tb.classList.contains('error')) {
	showtoast({
		title : "Lỗi!",
		message : "${myparam} loại thiết bị thất bại.",
		type : "error",
		duration : 3000
	});

}


function showForm() {
	document.getElementById("btnsave").setAttribute('name', 'btnadd')
	var elements = document.getElementsByClassName("modal-ip");
	 document.getElementById("ip-maloai").removeAttribute('readonly')
	Array.prototype.forEach.call(elements, function(el) {
		el.value = null
	});
	modal.classList.add('open')
}
// load dữ liệu lên modal khi ấn edit
var editbtn = document.getElementsByClassName("editbtn");
    	var table = document.getElementById('tb1');

for (let index = 0; index < editbtn.length; index++) {
	editbtn[index].onclick = function()
    {
		
    	const modal = document.querySelector('.js-modal')
    	modal.classList.add('open')
    	document.getElementById("btnsave").setAttribute('name', 'btn-save-edit')
        document.getElementById("ip-maloai").value = table.rows[index+1].cells[1].innerHTML
        document.getElementById("ip-maloai").setAttribute('readonly',true)
        document.getElementById("ip-tenloai").value = table.rows[index+1].cells[2].innerHTML
   };
    	}


	
</script>
</html>