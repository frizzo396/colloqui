jQuery(document).ready(function($){
	
	   $('.av-popup').on('click', function (event) {
			event.preventDefault();
			$('#availability-modal').addClass('is-visible');  	
    	});
    	
    	//Approvazione disponibilità
    	 $('.approve-popup').on('click', function (event) {
			event.preventDefault();
			$('#approve-modal').addClass('is-visible');
    	});
	
});	

//Inserimento disponibilità per svolgere un colloquio
$(document).ready(function (){
	var $form= $('#insertAvailability');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){
					// PER TOGLIERE ROTELLINA SPINNER - START
					var submitBtn = document.getElementById('btn_submit');
					setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); 
					           showToast("Operazione avvenuta con successo", "SUCCESS", 3000); 
					           setTimeout(function(){RedirectInProgress()}, 1500);}, 2000);
					// PER TOGLIERE ROTELLINA SPINNER - END					
				
					// showToast("Operazione avvenuta con successo", "SUCCESS", 3000);
					// setTimeout(function(){RedirectInProgress()}, 1500);
				}
				else {
					// PER TOGLIERE ROTELLINA SPINNER - START
					var submitBtn = document.getElementById('btn_submit');
					setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast(response.error, "ERROR", 3000);}, 2000);
					// PER TOGLIERE ROTELLINA SPINNER - END						
					
					//showToast(response.error, "ERROR", 3000);
				}

		}, 'json');
		return false;
	});
});


//Approvazione data colloquio
$(document).ready(function (){
	var $form= $('#approveAvailability');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){
					// PER TOGLIERE ROTELLINA SPINNER - START					
					var submitBtn = document.getElementById('btn_approve_submit');
					setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); 
					           showToast("Operazione avvenuta con successo", "SUCCESS", 3000); 
					           setTimeout(function(){RedirectAssigned()}, 1500);}, 2000);
					// PER TOGLIERE ROTELLINA SPINNER - END
					
					// showToast("Operazione avvenuta con successo", "SUCCESS", 3000);
					// setTimeout(function(){RedirectAssigned()}, 1500);
				}
				else {
					// PER TOGLIERE ROTELLINA SPINNER - START					
					var submitBtn = document.getElementById('btn_approve_submit');
					setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast(response.error, "ERROR", 3000);}, 2000);
					// PER TOGLIERE ROTELLINA SPINNER - END	
										
					// showToast(response.error, "ERROR", 3000);
				}

		}, 'json');
		return false;
	});
});


//Creazione modale inserimento disponibilità
function createAvailablityModal(interviewId){
	document.getElementById("interviewIdHid").value = interviewId;	
}

//Creazione modale approvazione disponibilità
function createApproveAvailablityModal(interviewId, dateInterviewList){
	document.getElementById("approvedIntId").value = interviewId;	
	let datesDropDown = document.getElementById("approvedDate");
	var obj = JSON.parse(dateInterviewList);	
	let dateList = obj.availabilityDates;
	
	for (var i = 0; i < dateList.length; i++) { 
			actDate = dateList[i];		
			datesDropDown.add(new Option(actDate, actDate)) ;
	 }
}