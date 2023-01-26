jQuery(document).ready(function ($) {
	

});

function redirectUsers() {
	window.location.href = "/interview-ms/users";
}


//Salvataggio intervistatore
$(document).ready(function (){
	var $form= $('#registerUser');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){
					var submitBtn = document.getElementById('btn_register_submit');
					setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); 
					           showToast("Operazione avvenuta con successo", "SUCCESS", 3000); 
					           setTimeout(function(){redirectUsers()}, 1700);}, 2000);
					 redirectUsers()
			} else if(response.body != null && response.error != null){ //Se user già presente, posso solo aggiornare il flag
				if(response.error == "EXPIRED"){
						redirectAccess();	
				} else {
					var body = response.body;
					document.getElementById("registerEntId").value = body.enterpriseId;
					document.getElementById("registerMail").value = body.mail;
					document.getElementById('registerEntId').readOnly = true;
					document.getElementById('registerMail').readOnly = true;
					var submitBtn = document.getElementById('btn_register_submit');
					setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast(response.error, "ERROR", 3000);}, 2000);
				}
			} else {
				if(response.error == "EXPIRED"){
					redirectAccess();	
				} else {
					var submitBtn = document.getElementById('btn_register_submit');
					setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); 
					showToast(response.error, "ERROR", 3000);
				 	setTimeout(function(){redirectUsers()}, 1700);}, 2000);
				}				 
			}

		}, 'json');
		return false;
	});
});

// Modifica tipo intervistatore, 1 (Responsabile), 2 (Utente normale)
$(document).ready(function (){
	var $form= $('#modifyUser');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){				
					var submitBtn = document.getElementById('btn_modify_submit');
					setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); 
					           showToast("Operazione avvenuta con successo", "SUCCESS", 3000); 
					           setTimeout(function(){RedirectUsers()}, 1700);}, 2000);
					  // RedirectUsers()
			} else {
				if(response.error == "EXPIRED"){
					redirectAccess();	
				} else {
					var submitBtn = document.getElementById('btn_modify_submit');
					setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast(response.error, "ERROR", 3000);
					setTimeout(function(){RedirectUsers()}, 1700);}, 2000);
				}
			}

		}, 'json');
		return false;
	});
});

// Cambio password intervistatore
$(document).ready(function (){
	var $form= $('#changePasswordUser');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){				
					var submitBtn = document.getElementById('btn_modify_pwd_submit');
					setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); 
					           showToast("Operazione avvenuta con successo", "SUCCESS", 3000); 
					           setTimeout(function(){RedirectHome()}, 1700);}, 2000);
					  // RedirectHome();
			} else {
				if(response.error == "EXPIRED"){
					redirectAccess();	
				} else {
					var submitBtn = document.getElementById('btn_modify_pwd_submit');
					setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); 
					           showToast(response.error, "ERROR", 6000);}, 4000);					
				}
			}

		}, 'json');
		return false;
	});
});

//Richiesta registrazione nuovo utente
$(document).ready(function (){
	var $form= $('#requestRegistration');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){
					showToast("Richiesta inviata con successo", "SUCCESS", 3000);
			}
			else {
				showToast(response.error, "ERROR", 3000);
			}
		}, 'json');
		return false;
	});
});


function getEnterpriseId(){
	var entId = document.getElementById("reqEnterpriseId");
	document.getElementById("reqEntId").value = entId.textContent;	
}