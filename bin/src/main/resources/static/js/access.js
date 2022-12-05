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
					document.getElementById("containerAccess").style.display = 'none';
					document.getElementById("containerReqRegistrationAcc").style.display = 'block';	
					//document.getElementById("backImage").style.height = '120.6%';		
					showToast(response.error, "ERROR", 3000);			
				}	
		}, 'json');
		return false;
	});
});