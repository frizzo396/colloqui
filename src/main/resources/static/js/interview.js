jQuery(document).ready(function ($) {
	
	

		$('#openAssignInterviewerModal').on('click', function (event) {
			event.preventDefault();
			$("#avBox2").DateTimePicker();
    	});
	//Salvataggio colloquio
	$(document).ready(function (){
		var $form= $('#insertInterview');
		$form.submit(function (e) {
			e.preventDefault(e);
			$.post($(this).attr('action'), $(this).serialize(), function (response){
				if(response.error == null){					
						var submitBtn = document.getElementById('insertInterviewBtn');
						setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); 
						           showToast("Colloquio inserito con successo", "SUCCESS", 3000); 
						           document.getElementById('insertInterview').reset();
						           createUploadCVModal(response.body.interviewId);}, 2000);
				}
				else {
					if(response.error == "EXPIRED"){
						redirectAccess();	
					} else {
						var submitBtn = document.getElementById('insertInterviewBtn');
						setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast(response.error, "ERROR", 3000);}, 2000);
					}
				}
	
			}, 'json');
			return false;
		});
	});
	
	$(document).ready(function (){
		var $form= $('#assignInterview');
		$form.submit(function (e) {
			e.preventDefault(e);
				$.post($(this).attr('action'), $(this).serialize(), function (response){
					if(response.error == null){
						// PER TOGLIERE ROTELLINA SPINNER - START
						var submitBtn = document.getElementById('btn_assign_submit');
						setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); 
						           showToast("Operazione avvenuta con successo", "SUCCESS", 3000); 
						           setTimeout(function(){RedirectSearchAssigned()}, 1700);}, 2000);
					}
					else {
						if(response.error == "EXPIRED"){
							redirectAccess();	
						} else {
							var submitBtn = document.getElementById('btn_assign_submit');
							setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast(response.error, "ERROR", 3000);}, 2000);
						}
					}
	
			}, 'json');
			return false;
		});
	});
	

});	

//Validazione form ricerca colloquio
function validationSearch() {	
	 	showToast("Operazione avvenuta con successo", "SUCCESS", 2000);
			window.setTimeout(function () { document.getElementById('searchInterview').submit(); }, 400); 
			return false;
}

//Reset risultati ricerca colloquio
function resetSearchTable() {
	document.getElementById("searchTableContainer").style.display = 'none !important';
}


function createInterviewerModal(interviewId){
	document.getElementById("assignIntId").value = interviewId;	
	document.getElementById("assign-modal").classList.add("is-visible");
}

