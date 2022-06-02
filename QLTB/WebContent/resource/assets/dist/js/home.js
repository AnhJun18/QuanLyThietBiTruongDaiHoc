
// Bật Tắt Modal 2
  const modal2 = document.querySelector('.js-modal-2')
            const modalContain2 = document.querySelector('.js-modal-contain-2')
            const close2 = document.querySelector('.js-modal-close-2')
			function hideModal2() {
				modal2.classList.remove('open')
			};
			
			close2.addEventListener('click', hideModal2 )
			modal2.addEventListener('click', hideModal2 )
			modalContain2.addEventListener('click', function(even) {
				even.stopPropagation()
			});

  const modal3 = document.querySelector('.js-modal-3')
            const modalContain3 = document.querySelector('.js-modal-contain-3')
            const close3 = document.querySelector('.js-modal-close-3')
			function hideModal3() {
				modal3.classList.remove('open')
			};
			
			close3.addEventListener('click', hideModal3 )
			modal3.addEventListener('click', hideModal3 )
			modalContain3.addEventListener('click', function(even) {
				even.stopPropagation()
			});

 // Spinner
var popoverTriggerList = [].slice.call(document
	.querySelectorAll('[data-bs-toggle="popover"]'))
var popoverList = popoverTriggerList.map(function(popoverTriggerEl) {
	return new bootstrap.Popover(popoverTriggerEl)
})
$('input.input-qty').each(function() {
	var $this = $(this),
		qty = $this.parent().find('.is-form'),
		min = Number($this.attr('min')),
		max = Number($this.attr('max'))
	if (min == 0) {
		var d = 0
	} else d = min
	$(qty).on('click', function() {
		if ($(this).hasClass('minus')) {
			if (d > min) d += -1
		} else if ($(this).hasClass('plus')) {
			var x = Number($this.val()) + 1
			if (x <= max) d += 1
		}
		$this.attr('value', d).val(d)
	})
})





const search_room = document.getElementById("search-room")
function timphong() {
	let input = search_room.value.toUpperCase();
	for (let name of document.getElementsByClassName("room")) {
		if (name.getAttribute("id").includes(input))
			name.style.display = 'flex';
		else
			name.style.display = 'none';

	}
}


