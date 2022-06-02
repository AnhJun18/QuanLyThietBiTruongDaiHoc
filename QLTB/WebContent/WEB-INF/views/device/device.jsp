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
		<div class="text">Thiết Bị</div>
		<div id="toast" class="${message}"></div>


		<div class="container">
			<button class="btn-add btn btn-primary" style="margin-left: 9% ;"
				onclick="showBuyTicket()">
				<i class="fa-solid fa-plus"></i> THÊM THIẾT BỊ
			</button>


			<jsp:useBean id="pagedListHolder" scope="request"
				type="org.springframework.beans.support.PagedListHolder" />
			<c:url value="device_list/index.htm" var="pagedLink">
				<c:param name="p" value="~" />
			</c:url>
			
			<div class="tag-paging pt-2"style="margin-right: 9%;">
				<tg:paging pagedLink="${pagedLink}"
					pagedListHolder="${pagedListHolder}" />
			</div>
			<div class="d-flex justify-content-center align-items-center mt-4  ">
				<div class="row col-lg-10">
					<table class="table" id="tb1">
						<thead>
							<tr>
								<th scope="col">STT</th>
								<th scope="col">Mã TB</th>
								<th scope="col">Loại TB</th>
								 <th scope="col">Phòng</th>
								<th scope="col">Tình Trạng</th>
								<th scope="col">Xóa</th>
							</tr>

						</thead>
						<tbody>
							<c:forEach items="${pagedListHolder.pageList}" var="pd" varStatus="i">
								<tr>
									<td>${ i.index+1}</td>
									<td>${pd.maTBi}</td>
									<td>${pd.loaiTBi.tenLoai }</td>
									<td>${pd.phong.maPhong }</td>
									<td>${pd.tinhTrangTB }</td>
									<td>
											<a href="device_list/index/${pd.maTBi}.htm?linkDelete" class="${pd.phong == null?'':'disable-delete' }"
												onclick="return confirm('Xác nhận xóa?') ? true : false;"
												style="padding: 5px 10px;"> <i
												class="fa-solid fa-trash-can "></i>
											</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>


				</div>
			</div>

		</div>


		<%-- <%@include file="/WEB-INF/views/include/footer.jsp" %> --%>
	</section>
	
	<div class="modal js-modal">
		<div class="modal-container js-modal-contain" style="width: 620px;">
			<div class="modal-close js-modal-close">
				<i class="fa-solid fa-xmark"></i>
			</div>
			<header class="modal-header">
				<i class="ti-bag modal-heading-icon"></i> THÊM THIẾT BỊ
			</header>
			<div class="modal-body">
				<form:form modelAttribute="CTThietBi" method="post">
					
					<input path="phong.maPhong" hidden="true" />

					<label for="quantity-ticket" class="modal-lable"> <i
						class="ti-shopping-cart"></i> Loại Thiết Bị
					</label>
					<form:select path="loaiTBi.maLoai" items="${listLoaiTB }"
						itemLabel="tenLoai" itemValue="maLoai"
						class="form-select-lg form-select mb-2" />
					<label for="quantity-ticket" class="modal-lable"> <i
						class="ti-shopping-cart"></i> Số Lượng
					</label>
					<input name="soLuong" type="text" class="modal-ip mb-5" required="required" pattern="[1-9][0-9]{0,1}" oninvalid="setCustomValidity('Số lượng nằm trong khoảng từu 1 đến 99')" 
					onchange="try{setCustomValidity('')}catch(e){}"> 
					<button id="btnsave" name ="btnadd" class="btn-modal">THÊM</button>
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
</script>
</html>