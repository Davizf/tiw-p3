document.querySelector('thead input[type="checkbox"').addEventListener('click', function() {
	document.querySelectorAll('tbody input[type="checkbox"').forEach(elem => elem.checked = this.checked);
});

document.querySelectorAll('.mass-operation').forEach(elem => elem.addEventListener('click', function() {
	let form = document.forms['mass-operation'];
	form['operation'].value = this.id;
	form['keys'].value = [...document.querySelectorAll('tbody input[type="checkbox"]:checked')].map(elem => {
		return elem.parentNode.nextElementSibling.innerText;
	}).join(" ");
	form.submit();
}));