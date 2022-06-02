var header = document.getElementById('header'),
			  menubtn = document.getElementById('menu-btn'),
			  headerContain = document.getElementById('sidebar-id'),
			  hightHead = headerContain.clientHeight,
			  subNavDoiPass=document.getElementById('change-pass');
menubtn.onclick = function() {
	var isClose = headerContain.clientHeight === hightHead;
	console.log(isClose + ' ' + headerContain.clientHeight + ' ||' + hightHead);
	if (isClose) {
		headerContain.style.height = 'auto';
		 subNavDoiPass.style.display='block';
	}
	else {
		headerContain.style.height = hightHead + 'px';
		subNavDoiPass.style.display='none';
	}
}
// Dark/Light Mode
const body = document.querySelector('body'),
	sidebar = body.querySelector('nav'),
	toggle = body.querySelector(".toggle"),
	modeSwitch = body.querySelector(".toggle-switch"),
	modeText = body.querySelector(".mode-text"),
	currentTheme = localStorage.getItem("theme"),
	topbar = body.querySelector('.topbar');

// nếu chế độ được lưu trong localStorage là "dark"
if (currentTheme == "dark") {
	// thì sẽ dùng .dark-theme class
	body.classList.add("dark");
}

toggle.addEventListener("click", () => {
	sidebar.classList.toggle("close");
	topbar.classList.toggle("close");
})



let theme = "light";
modeSwitch.addEventListener("click", () => {
	body.classList.toggle("dark");

	if (body.classList.contains("dark")) {
		modeText.innerText = "Light mode";
		theme = "dark";
	} else {
		modeText.innerText = "Dark mode";

	}
	localStorage.setItem("theme", theme);
});

// Bật tắt Modal Update Pass
const modalChangePass = document.getElementById('js-modal-ed'),
	modalChangePassContain = document.querySelector('.js-modal-ed-contain'),
	closeChangePass = document.querySelector('.js-modal-ed-close')
closeChangePass.addEventListener('click', hideChangePass)
modalChangePass.addEventListener('click', hideChangePass)
modalChangePassContain.addEventListener('click', function(even) {
	even.stopPropagation()
});
function showFormChangePass() {
	modalChangePass.classList.toggle('open')
}
function hideChangePass() {
	modalChangePass.classList.remove('open')
}


// Bật tắt Modal
const modal = document.querySelector('.js-modal')
const modalContain = document.querySelector('.js-modal-contain')
const close = document.querySelector('.js-modal-close')


function showBuyTicket() {
	document.getElementById("btnsave").setAttribute('name', 'btnadd')
	var elements = document.getElementsByClassName("modal-ip");
	Array.prototype.forEach.call(elements, function(el) {
		el.value = null
	});
	modal.classList.add('open')
}

function hideBuyTicket() {
	modal.classList.remove('open')
}

close.addEventListener('click', hideBuyTicket)
modal.addEventListener('click', hideBuyTicket)
modalContain.addEventListener('click', function(even) {
	even.stopPropagation()
});




// Toast function
function showtoast({ title = "", message = "", type = "info", duration = 3000 }) {
	const main = document.getElementById("toast");
	if (main) {
		const toast = document.createElement("div");

		// Auto remove toast
		const autoRemoveId = setTimeout(function() {
			main.removeChild(toast);
		}, duration + 1000);

		// Remove toast when clicked
		toast.onclick = function(e) {
			if (e.target.closest(".toast__close")) {
				main.removeChild(toast);
				clearTimeout(autoRemoveId);
			}
		};

		const icons = {
			success: "fas fa-check-circle",
			info: "fas fa-info-circle",
			warning: "fas fa-exclamation-circle",
			error: "fas fa-exclamation-circle"
		};



		const icon = icons[type];
		const delay = (duration / 1000).toFixed(2);

		toast.classList.add("toast-mess", `toast--${type}`);
		toast.style.animation = `slideInLeft ease .3s, fadeOut linear 1s ${delay}s forwards`;

		toast.innerHTML = `
                      <div class="toast__icon">
                          <i class="${icon}"></i>
                      </div>
                      <div class="toast__body">
                          <h3 class="toast__title">${title}</h3>
                          <p class="toast__msg">${message}</p>
                      </div>
                      <div class="toast__close">
                          <i class="fas fa-times"></i>
                      </div>
                  `;
		main.appendChild(toast);
	}

}

