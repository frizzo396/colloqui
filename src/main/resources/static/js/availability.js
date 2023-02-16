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
    	
    	 //Approvazione disponibilità
    	 $('.reassign-popup').on('click', function (event) {
			event.preventDefault();
			$('#reassign-modal').addClass('is-visible');
    	});
    	 //Rischedulazione
    	 $('.reschedule-popup').on('click', function (event) {
			event.preventDefault();
			$('#rescheduled-modal').addClass('is-visible');
    	});
    	
    	$('#approveDateBtn').on('click', function (event) {
			event.preventDefault();
			$("#avBox1").DateTimePicker();
    	});
    	
    	
    	
    	var dtPick3 = $('<div id="avBox3"></div>');
    	var dtPick4 = $('<div id="avBox4"></div>');
    	var dtPick5 = $('<div id="avBox5"></div>');
    	$('#firstAvDate').hover(function (e) {
			$('#avBox4').remove();
			$('#avBox5').remove();
			$( "#firstDateForm" ).append(dtPick3);
			$("#avBox3").DateTimePicker();
			
    	});
    	$('#secondAvDate').hover(function (e) {
			$('#avBox3').remove();
			$('#avBox5').remove();
			$( "#secondDateForm" ).append(dtPick4);
			$("#avBox4").DateTimePicker();
			
    	});
    	$('#thirdAvDate').hover(function (e) {
			$('#avBox3').remove();
			$('#avBox4').remove();
			$( "#thirdDateForm" ).append(dtPick5);
			$("#avBox5").DateTimePicker();

			
    	});
    	
    	$('#closeAvModal').on('click', function (event) {
			$('#avBox3').remove();
			$('#avBox4').remove();
			$('#avBox5').remove();
			$('#firstAvDate').val('');
			$('#secondAvDate').val('');
			$('#thirdAvDate').val('');
			$('.cd-popup').removeClass('is-visible');
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
				}
				else {
					if(response.error == "EXPIRED"){
						redirectAccess();	
					} else {
						var submitBtn = document.getElementById('btn_submit');
						setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast(response.error, "ERROR", 3000);}, 2000);
					}
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
					var submitBtn = document.getElementById('btn_approve_submit');
					setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); 
					showToast("Operazione avvenuta con successo", "SUCCESS", 3000); 
					setTimeout(function(){RedirectAssigned()}, 1500);}, 2000);
				}
				else {
					if(response.error == "EXPIRED"){
						redirectAccess();	
					} else {			
						var submitBtn = document.getElementById('btn_approve_submit');
						setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast(response.error, "ERROR", 3000);}, 2000);
					}
				}

		}, 'json');
		return false;
	});
});


//rifiuto intervista
$(document).ready(function (){
	var $form= $('#acceptRescheduling');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){
					showToast("Operazione avvenuta con successo", "SUCCESS", 3000);
					setTimeout(function(){RedirectInProgress()}, 1500);
				}
				else {
					if(response.error == "EXPIRED"){
						redirectAccess();	
					} else {
						showToast(response.error, "ERROR", 3000);
					}
				}
		}, 'json');
		return false;
	});
});


//rifiuto intervista
$(document).ready(function (){
	var $form= $('#refuseAvailability');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){
					showToast("Operazione avvenuta con successo", "SUCCESS", 3000);
					setTimeout(function(){RedirectInProgress()}, 1500);
				}
				else {
					if(response.error == "EXPIRED"){
						redirectAccess();	
					} else {
						showToast(response.error, "ERROR", 3000);
					}
				}
		}, 'json');
		return false;
	});
});


//rifiuto intervista
$(document).ready(function (){
	var $form= $('#refuseAvailability2');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){
					showToast("Operazione avvenuta con successo", "SUCCESS", 3000);
					setTimeout(function(){RedirectInProgress()}, 1500);
				}
				else {
					if(response.error == "EXPIRED"){
						redirectAccess();	
					} else {
						showToast(response.error, "ERROR", 3000);
					}
				}
		}, 'json');
		return false;
	});
});


//Riassegnazione colloquio
$(document).ready(function (){
	var $form= $('#reassignInterview');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){
					showToast("Operazione avvenuta con successo", "SUCCESS", 3000);
					setTimeout(function(){RedirectAssigned()}, 1500);
				}
				else {
					if(response.error == "EXPIRED"){
						redirectAccess();	
					} else {
						showToast(response.error, "ERROR", 3000);
					}
				}

		}, 'json');
		return false;
	});
});


//Creazione modale inserimento disponibilità
function createAvailablityModal(interviewId){
	document.getElementById("interviewIdHid").value = interviewId;	
	document.getElementById("interviewIdHid2").value = interviewId;		
}

function createAcceptRescheduledModal(interviewId, rescheduledDate){
	document.getElementById("rescheduleIntId").value = interviewId;
	document.getElementById("rescheduleIntId2").value = interviewId;
	if(rescheduledDate.includes(".")){
		const array = rescheduledDate.split(".");
		document.getElementById("rescheduleIntDate").value = array[0];
	} else {				
		document.getElementById("rescheduleIntDate").value = rescheduledDate;	
	}
	
}


//Creazione modale approvazione disponibilità
function createApproveAvailablityModal(interviewId, dateInterviewList){
	document.getElementById("approvedIntId").value = interviewId;	
	let datesDropDown = document.getElementById("approvedDate");
	datesDropDown.innerHTML = ""; //Clear
	datesDropDown.add(new Option("Nothing selected", "")) ;
	var obj = JSON.parse(dateInterviewList);	
	let dateList = obj.availabilityDates;
	
	for (var i = 0; i < dateList.length; i++) { 
			actDate = dateList[i];		
			datesDropDown.add(new Option(actDate, actDate)) ;
	 }
}

function createReassignModal(interviewId){
	document.getElementById("reassignIntId").value = interviewId;	
}