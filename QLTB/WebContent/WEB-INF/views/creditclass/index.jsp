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
		<div class="text">Lớp Tín Chỉ</div>
		<div id="toast" class="${message}"></div>


		<div class="container">
			<button class="btn-add btn btn-primary" style="margin-left: 9%;"
				onclick="showBuyTicket()">
				<i class="fa-solid fa-plus"></i> THÊM
			</button>


			<jsp:useBean id="pagedListHolder" scope="request"
				type="org.springframework.beans.support.PagedListHolder" />
			<c:url value="credit_class/index.htm" var="pagedLink">
				<c:param name="p" value="~" />
			</c:url>

			<div class="tag-paging">
				<tg:paging pagedLink="${pagedLink}"
					pagedListHolder="${pagedListHolder}" />
			</div>
			<div class="d-flex justify-content-center align-items-center mt-4  ">
				<div class="row col-lg-10">
					<table class="table" id="tb1" style="vertical-align: middle; text-align: center;">
						<thead>
							<tr>
								<th scope="col">STT</th>
								<th scope="col">Mã LTC</th>
								<th scope="col">Niên Khóa</th>
								<th scope="col">Học Kỳ</th>
								<th scope="col" style="width: 240px;">Môn Học</th>
								<th scope="col">Nhóm</th>
							</tr>

						</thead>
						<tbody>
							<c:forEach items="${pagedListHolder.pageList}" var="pd"
								varStatus="i">
								<tr>
									<td>${ i.index+1}</td>
									<td>${pd.maLTC}</td>
									<td>${pd.nienkhoa }</td>
								 	<td>${pd.hocky}</td> 
									<td style="text-align: left;">${pd.monhoc}</td>
									<td>${pd.nhom }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

		</div>


		<%-- <%@include file="/WEB-INF/views/include/footer.jsp" %> --%>
	</section>

	<div class="modal js-modal ${statusmodal }">
		<div class="modal-container js-modal-contain" style="width: 820px;">
			<div class="modal-close js-modal-close">
				<i class="fa-solid fa-xmark"></i>
			</div>
			<header class="modal-header">
				<i class="ti-bag modal-heading-icon"></i> THÊM LỚP TÍN CHỈ
			</header>
			<div class="modal-body">
				<form:form modelAttribute="LopTinChi" method="post">

					<form:input path="maLTC" hidden="true" />
					<label for="quantity-ticket" class="modal-lable"> <i
						class="ti-shopping-cart"></i> Niên Khóa
					</label>
	
					<form:input path="nienkhoa"  required="required" pattern="2[0-9]{3}-2[0-9]{3}" class="modal-ip" oninvalid="setCustomValidity('Vui Lòng nhập đúng format vd: 2022-2023')" 
					onchange="try{setCustomValidity('')}catch(e){}"/>

					<label for="quantity-ticket" class="modal-lable"> <i
						class="ti-shopping-cart"></i> Học Kỳ
					</label>
					<form:input path="hocky" class="modal-ip " required="required" pattern="[1-4]" oninvalid="setCustomValidity('Học kỳ nằm trong khoảng từu 1 đến 4')" 
					onchange="try{setCustomValidity('')}catch(e){}"/>
				
					<label for="quantity-ticket" class="modal-lable"> <i
						class="ti-shopping-cart"></i> Môn Học
					</label>
					<form:input path="monhoc" class="modal-ip " required="required"  />
					<label for="quantity-ticket" class="modal-lable"> <i
						class="ti-shopping-cart"></i> Nhóm
					</label>
					<form:input path="nhom" class="modal-ip mb-5" required="required" pattern="[1-9]" oninvalid="setCustomValidity('Nhóm nằm trong khoảng từu 1 đến 9')" 
					onchange="try{setCustomValidity('')}catch(e){}"/>
			
					<button id="btnsave" name="btnadd" class="btn-modal">THÊM</button>
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
			message : "Thêm lớp tín chỉ thành công.",
			type : "success",
			duration : 3000
		});
	} else if (tb.classList.contains('error')) {
		showtoast({
			title : "Lỗi!",
			message : "${myparam}! </br> Vui lòng thử lại.",
			type : "error",
			duration : 3000
		});

	}
</script>
</html>