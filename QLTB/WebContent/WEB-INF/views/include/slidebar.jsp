<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<nav class="sidebar" id="sidebar-id">
	<header id="header">
		<div class="image-text">
			<span class="image"> <img src="resource/images/logo.png"
				alt="">
			</span>

			<div class="text logo-text">
				<span class="name mr-5"> PTITHCM</span> <span class="profession">Quan
					Ly Thiet Bi</span>
			</div>
		</div>
		<i class="fa-solid fa-chevron-right toggle"></i>
		<i class="fa-solid fa-bars menu-icon" id="menu-btn" style="display: none"></i>

	</header>

	<div class="menu-bar">
		<div class="menu" id="menu-contain">

			<ul class="menu-links">
				
				<li class="nav-link"><a
					class=${navigation=='home'?'active':'notactive'}
					href="home/index.htm"> <i class='fa-solid fa-house icon'></i> <span
						class="text nav-text">Trang Chủ</span>
				</a></li>

				<li class="nav-link"><a
					class=${navigation=='device_detail'?'active':'notactive'}
					href="device_detail/index.htm"> <i
						class="fas fa-laptop-house icon"></i><span class="text nav-text">Phòng - Thiết Bị</span>
				</a></li>

				<li class="nav-link"><a
					class=${navigation=='device_type'?'active':"notactive"}
					href="device_type/index.htm"> <i
						class="fa-solid fa-people-roof icon"></i> <span
						class="text nav-text">Loại Thiết Bị</span>
				</a></li>
				<li class="nav-link"><a
					class=${navigation=='device_list'?'active':'notactive' }
					href="device_list/index.htm"><i class="fas fa-hdd icon"></i>
						<span class="text nav-text">Thiết Bị</span>
				</a></li>
				
				<li class="nav-link"><a
					class=${navigation=='creditclass'?'active':'notactive' }
					href="credit_class/index.htm"> <i class="fas fa-atlas icon"></i>
						<span class="text nav-text">Lớp Tín Chỉ</span>
				</a></li>
				<c:if test="${permission=='admin'}">
				<li class="nav-link"><a
					class=${navigation=='bill'?'active':'notactive' }
					href="bill/index.htm"> <i class='fa-solid fa-file-invoice icon'></i>
						<span class="text nav-text">Thống Kê</span>
				</a></li>

					<li class="nav-link"><a
						class=${navigation=='administration'?'active':'notactive' }
						href="administration/admin.htm"><i
							class="fa-solid fa-user-gear icon"></i><span
							class="text nav-text">Quản Trị</span> </a></li>
				</c:if>

			</ul>
		</div>

		<div class="bottom-content">
			<ul>
				<li id="acc-setting"><a><i class="fa-solid fa-user icon"></i>
						<span class="text nav-text">${info}</span></a>
					<div class="sp-nav"></div>
					<ul class="subnav" id="change-pass">
						<li><a onclick="showFormChangePass()">&nbsp Đổi Mật Khẩu</a></li>
					</ul></li>


				<li class=""><a href="logout.htm"> <i
						class='fa-solid fa-arrow-right-from-bracket icon'></i> <span
						class="text nav-text">Đăng Xuất</span></a></li>


				<li class="mode">
					<div class="sun-moon">
						<i class=' fa-solid fa-moon bx  icon moon'></i> <i
							class=' fas fa-sun bx icon sun'></i>
					</div> <span class="mode-text text">Dark mode</span>

					<div class="toggle-switch">
						<span class="switch"></span>
					</div>
				</li>

			</ul>

		</div>
	</div>

</nav>



<!-- Modal -->
<div class="modal" id="js-modal-ed">
	<div class="modal-container js-modal-ed-contain">
		<div class="modal-close js-modal-ed-close">
			<i class="fa-solid fa-xmark"></i>
			
			
		</div>

		<header class="modal-header"> Đổi Mật Khẩu </header>
		<div class="modal-body">
			<form action="administration/changepass.htm" method="post">
				<label class="modal-lable"> Mật Khẩu Hiện Tại </label> <input 
					name="currentpass" type="password" class="modal-ip"
					placeholder="Nhập Mật Khẩu Hiện Tại" /> <label class="modal-lable">
					Mật Khẩu Mới </label> <input name="newpass" type="password" class="modal-ip"
					placeholder="Nhập Mật Khẩu Mới" /> <label class="modal-lable">
					Xác Nhận Mật Khẩu Mới </label> <input name="retypepass" type="password"
					class="modal-ip" placeholder="Nhập Mật Khẩu Mới" />
				<button class="btn-modal">THÊM</button>
			</form>

		</div>


	</div>
</div>
