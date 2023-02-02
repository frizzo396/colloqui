jQuery(document).ready(function($){
	
	//Precompilazione
	var entId = document.getElementById("reqEnterpriseId");
	document.getElementById("accessUsername").value = entId.textContent;	
	
	//document.getElementById("accessFormColumn").style.display = 'inline-block !important';
	//document.getElementById("errorPageColumn").style.display = 'none';
	
});	

//Accesso utente
$(document).ready(function (){
	var $form= $('#userAccess');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){
					showToast("Accesso eseguito, attendi il reindirizzamento", "SUCCESS", 3000);
					setTimeout(function(){
						  const url_string = window.location.href;
						  const url = new URL(url_string);
						  const enterpriseId = url.searchParams.get("enterpriseId");
						  window.location.href = "/interview-ms/home";					
					}, 2000);
				}
				else {	
					showToast(response.error, "ERROR", 3000);
					if(response.error == "Utente non registrato"){
						document.getElementById("containerAccess").style.display = 'none';
						document.getElementById("containerReqRegistrationAcc").style.display = 'block';	
					}
					
					// per mostrare link di recupero password
					// setTimeout(function(){document.getElementById("recoverPwdLink").style.display = 'block';}, 1000);
					setTimeout(function(){document.getElementById("recoverPwdLink").style.visibility = 'visible';}, 1000);															
				}	
		}, 'json');
		return false;
	});
});


//Recupero password utente
$(document).ready(function (){
	var $form= $('#recoverUserPassword');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){				
				var submitBtn = document.getElementById('recoverPwdBtn');
				showToast("Nuova password inviata sulla mail inserita", "SUCCESS", 4000);
				setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading");				            
				           const url_string = window.location.href;
				           const url = new URL(url_string);
				           const enterpriseId = url.searchParams.get("enterpriseId");
				           window.location.href = "/interview-ms/access";
				           }, 2000);				
			}
			else {
				var submitBtn = document.getElementById('recoverPwdBtn');
				setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast(response.error, "ERROR", 3000);}, 2000);															
			}	
		}, 'json');
		return false;
	});
});