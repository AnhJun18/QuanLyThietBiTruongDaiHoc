<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/head.jsp"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags"%>
<!-- Google Fonts -->
<link href="https://fonts.gstatic.com" rel="preconnect">
<link href="resource/assets/dist/css/bill.css" rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">

<body>
	<%@include file="/WEB-INF/views/include/slidebar.jsp"%>
	<section class="home">
		<div class="text">Thống Kê Phiếu Mượn</div>
		<div id="toast" class="${message}"></div>


		<div class="filter-container container d-flex justify-content-center align-items-center" style="width: 94%">
			<div class="filter-content row mt-4">
				<form class="w-100 filter  d-flex justify-content-between my-3">
					<div class=" col-4 col-sm-4 border border-info px-3" style="color: var(--text-color);">
			    
						<b>Chế độ xem</b>
						<div class="form-check ps-5 ">
							<input class="form-check-input" type="radio"
								name="rdfilte" id="tatca" value="tatca" ${statusRd =='tatca'?'checked':''} > <label
								class="form-check-label" for="flexRadioDefault1"> Tất cả
							</label>
						</div>

						<div class="form-check ps-5 mt-2">
							<input class="form-check-input" type="radio"
								name="rdfilte" id="quahan"  value="quahan" ${statusRd =='quahan'?'checked':''}>
							<label class="form-check-label" for="flexRadioDefault2">
								Quá Hạn </label>
						</div>
					</div>
					<div class=" col-5 col-sm-5 ">
						<div class="row">
							<div class="input-lable w-25" id="inputGroup-sizing-default">Từ
								</div> <input type="date" name="from" class="date-filter w-75" value="${timeFrom }">
						</div>
						<div class="row mt-2">
							<div class="input-lable w-25" id="inputGroup-sizing-default">Đến</div>
							<input type="date" class="date-filter w-75" name="to" value="${timeTo}" max=${timeNow }>
						</div>

					</div>
					<div class="col-2 col-sm-2 d-flex justify-content-center">
						<button type="submit" name="btnfilter"
							class="h-50 w-75 btn btn-primary"
							style="align-self: center;">Lọc</button>
					</div>
			
				</form>
				
				<jsp:useBean id="pagedListHolder" scope="request"
			type="org.springframework.beans.support.PagedListHolder" />
		<c:url value="${link}" var="pagedLink">
			<c:param name="p" value="~" />
		</c:url>
		
		<div class="d-flex justify-content-between align-items-center mt-4 mb-3" >
			
			<form  target="_blank">
			     <button type="submit" name="print" class="fa-solid fa-print btn-print"></button>
			</form>
			<div class="tag-paging" >
				<tg:paging pagedLink="${pagedLink}"
					pagedListHolder="${pagedListHolder}" />
			</div>
		</div>
		     <div class="contain-table" >
				<table class="table" id="tb1" style="vertical-align: middle; text-align: center;">
					<thead>
						<tr>
							<th scope="col">Mã PM</th>
							<th scope="col">Phòng</th>
							<th scope="col" style="width: 240px;">Lớp Học</th>
							<th scope="col">Thời Điểm Mượn</th>
							<th scope="col">Hạn Trả</th>
							<th scope="col">Tình Trạng</th>
							<th scope="col">Chi tiết</th>
						</tr>

					</thead>
					<tbody >
						<c:forEach items="${pagedListHolder.pageList}" var="pd"
							varStatus="i">
							<tr>
								<td>${pd.maPhieuMuon}</td>
								<td>${pd.maPhg.maPhong }</td>
								
								<td>${pd.lopTC.monhoc} nhóm
								${pd.lopTC.nhom} (${pd.lopTC.nienkhoa} hk:${pd.lopTC.hocky})</td>

								<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm"
										value="${pd.thoiDiemMuon }" /></td>
								<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm"
										value="${pd.hanTra }" /></td>
								<td>${pd.thoiDiemTra== null?'Chưa Trả':'Đã Trả' }</td>
								<td><a href="bill/detail_${pd.maPhieuMuon}.htm"><i class="fas fa-info-circle icon"></i></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
</div>


			</div>

		</div>

	</section>
	<!-- Modal -->
	<!-- Modal Chi Tiet Phieu Muon -->
	<div class="modal js-modal ${statusModal }">
		<div class="modal-container js-modal-contain ">
			<div class="modal-close js-modal-close">
				<i class="fa-solid fa-xmark"></i>
			</div>

			<header class="modal-header">
				<i class="ti-bag modal-heading-icon"></i>
				<div>PHIẾU MƯỢN</div>


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
												<strong> Chưa Trả</strong>
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
			</div>

		</div>
	</div>



</body>
<%@include file="/WEB-INF/views/include/foot.jsp"%>
<script>
    
	${thongbao}
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
	/* 
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
	 document.getElementById("ip-tenloai").value = table.rows[index+1].cells[2].innerHTML
	 };
	 }
	 */
</script>
</html>