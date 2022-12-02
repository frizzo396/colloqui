jQuery(document).ready(function ($) {
	

});

//Salvataggio intervistatore
$(document).ready(function (){
	var $form= $('#registerUser');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){
					showToast("Operazione avvenuta con successo", "SUCCESS", 3000);
					setTimeout(function(){RedirectHome()}, 1700);
			} else if(response.body != null && response.error != null){ //Se user già presente, posso solo aggiornare il flag
				var body = response.body;
				document.getElementById("registerEntId").value = body.enterpriseId;
				document.getElementById("registerMail").value = body.mail;
				document.getElementById('registerEntId').readOnly = true;
				document.getElementById('registerMail').readOnly = true;
				showToast(response.error, "ERROR", 3000);
			} else {
				showToast(response.error, "ERROR", 3000);
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