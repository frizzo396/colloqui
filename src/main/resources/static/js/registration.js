jQuery(document).ready(function ($) {
	

});

//Salvataggio intervistatore
$(document).ready(function (){
	var $form= $('#registerUser');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){
					// PER TOGLIERE ROTELLINA SPINNER - START
					var submitBtn = document.getElementById('btn_register_submit');
					setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); 
					           showToast("Operazione avvenuta con successo", "SUCCESS", 3000); 
					           setTimeout(function(){RedirectHome()}, 1700);}, 2000);
					// PER TOGLIERE ROTELLINA SPINNER - END				
				
					// showToast("Operazione avvenuta con successo", "SUCCESS", 3000);
					// setTimeout(function(){RedirectHome()}, 1700);
			} else if(response.body != null && response.error != null){ //Se user già presente, posso solo aggiornare il flag
				var body = response.body;
				document.getElementById("registerEntId").value = body.enterpriseId;
				document.getElementById("registerMail").value = body.mail;
				document.getElementById('registerEntId').readOnly = true;
				document.getElementById('registerMail').readOnly = true;
				
				// PER TOGLIERE ROTELLINA SPINNER - START
				var submitBtn = document.getElementById('btn_register_submit');
				setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast(response.error, "ERROR", 3000);}, 2000);
				// PER TOGLIERE ROTELLINA SPINNER - END				
				
				// showToast(response.error, "ERROR", 3000);
			} else {
				
				// PER TOGLIERE ROTELLINA SPINNER - START
				var submitBtn = document.getElementById('btn_register_submit');
				setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast(response.error, "ERROR", 3000);}, 2000);
				// PER TOGLIERE ROTELLINA SPINNER - END
								
				// showToast(response.error, "ERROR", 3000);
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
					// PER TOGLIERE ROTELLINA SPINNER - START
					var submitBtn = document.getElementById('btn_modify_submit');
					setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); 
					           showToast("Operazione avvenuta con successo", "SUCCESS", 3000); 
					           setTimeout(function(){RedirectUsers()}, 1700);}, 2000);
					// PER TOGLIERE ROTELLINA SPINNER - END			
								
					// showToast("Operazione avvenuta con successo", "SUCCESS", 3000);
					// setTimeout(function(){RedirectUsers()}, 1700);
			} else {
				
				// PER TOGLIERE ROTELLINA SPINNER - START
				var submitBtn = document.getElementById('btn_modify_submit');
				setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast(response.error, "ERROR", 3000);}, 2000);
				// PER TOGLIERE ROTELLINA SPINNER - END				
				
				// showToast(response.error, "ERROR", 3000);
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