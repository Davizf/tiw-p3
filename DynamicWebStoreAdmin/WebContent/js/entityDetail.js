(function() {
	document.querySelectorAll('.dataHeaderBar').forEach(elem => {
		elem.addEventListener('click', function() {
			this.nextElementSibling.classList.toggle("hidden");
			this.querySelectorAll('span').forEach(elem => elem.classList.toggle("hidden"));
		});
	});

	let imagen = document.querySelector('#image'); 
	if(imagen != null) {
		imagen.addEventListener('change', function(event) {
			document.querySelector('label[for="image"]').innerHTML = event.target.files[0].name;
		});
	}
	
	let confirm = document.querySelector('#confirm');
	if(confirm != null) {
		
		confirm.addEventListener('click', function() {
			document.querySelector('#mainForm').submit();
		});
	}

	let cancel = document.querySelector('#cancel');
	if(cancel != null) {
		cancel.addEventListener('click', function() {
			let newURL = window.location.href.replace("Edit", "List").replace("Add", "List");
			if(window.location.href.includes("mass")) {
				let aux = window.location.href.split("/");
				aux.pop();
				newURL = aux.join("/");
			}
			window.location.href = newURL;
		});
	}
})();
