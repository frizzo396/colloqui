function openPopupPassword(userId, event) {		
	event.preventDefault();
	$('#change-password-modal').addClass('is-visible');
	
	document.getElementById('userInputEntId').value = document.getElementById("modificaPassword").getAttribute('data-eid');
	document.getElementById('userInputEntId').readOnly = true;

	/* il campo userId in realtà era stato pensato all'inizio, si usa invece come chiave la enterprise id */
	document.getElementById('userInputId').value = userId;
	document.getElementById('userInputId').readOnly = true;
	
	document.getElementById('inputOldPassword').value = "";
	document.getElementById('inputNewPassword').value = "";
	
	var oldPassword = document.getElementById('inputOldPassword');
	var newPassword = document.getElementById('inputNewPassword');
	oldPassword.setAttribute('type', 'password');			
	$("#toggleOldPassword").attr('class', 'bx bx-low-vision icon');
	newPassword.setAttribute('type', 'password');			
	$("#toggleNewPassword").attr('class', 'bx bx-low-vision icon');		
}		

// Quando l'utente clicca sulla CTA, mostra o nasconde la dropdown
function ctaFunctionPassword(idDiv) {			
	// PER CHIUDERE EVENTUALI POPUP MENU APERTI - START
	var dropdowns = document.getElementsByClassName("dropdown-content-hd");
	var i;
	for (i = 0; i < dropdowns.length; i++) {
		var openDropdown = dropdowns[i];			  
		if(!(openDropdown.id == idDiv)) {
			if (openDropdown.classList.contains('show')) {
		    	openDropdown.classList.remove('show');
			}				  				  
		}
	}			
	// PER CHIUDERE EVENTUALI POPUP MENU APERTI - END			
	document.getElementById(idDiv).classList.toggle("show");
}
