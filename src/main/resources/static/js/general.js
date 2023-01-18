jQuery(document).ready(function($){
	
	//Click su X per chiusura modali
	$(document).on('click','#closeModal', function() {
  		$('.cd-popup').removeClass('is-visible');
  		$('.cd-popup2').removeClass('is-visible');
	});
	
	
	setTimeout(function() {
        $("#yearGraph").trigger('click');        
    },300);
	
	//Cambiamento colori su form - SELECT
	$("select").change(function() {
		var selectedValue = $(this).val();		
		if(selectedValue == ""){
				$(this).css('color','#afb0b3');
		} else {
				$(this).css('color','black');
		}
	})
	
	//Cambiamento colori su form - DATEPICKER
	$(".datePicker").change(function() {
			var selectedValue = $(this).val();
			if(selectedValue == ""){
				$(this).css('color','#afb0b3');
			} else {
					$(this).css('color','black');
			}		
	}) 
	
	//Cambiamento colori su form - INPUT numerico
	$(".num-input").change(function() {
			var selectedValue = $(this).val();
			if(selectedValue == 0){  
				$(this).attr('style', 'color: #afb0b3 !important');
			} else {
					$(this).attr('style', 'color: black !important');
			}		
	})
	
	//Cambiamento colori su form - INPUT FILE
	$("#cv").change(function() {
			var selectedValue = $(this).val();
			if(selectedValue == ""){
				$(this).css('color','#afb0b3');
			} else {
					$(this).css('color','black');
			}		
	})
	
	//Cambiamento colori su form - BOTTONI "reset"
	$('.clear-button').click(function(){
	    $('.datePicker').css('color','#afb0b3');
	    $('select').css('color','#afb0b3');
	    $('#cv').css('color','#afb0b3');
	    $(".num-input").attr('style', 'color: #afb0b3 !important');
	});
	
	$('#logoutIcon').click(function(){
		 window.location.href = "/interview-ms/access";
	});
	//Al click "+" il simbolo diventa "-" e viceversa
	$('.collapsible').click(function(){
	var content = this.nextElementSibling;
		   if (content.style.display === "block") {
	      		content.style.display = "none";
	      		document.getElementById("collapseIcon").className = "bx bx-plus";
	      		
	      		if (document.getElementById("collapseIcon2")) { // IF AGGIUNTA IL 2023-01-13 EVOLUTIVA 1
					document.getElementById("collapseIcon2").className = "bx bx-plus"; // 2022-12-12 aggiunto		
				}	   
	   	   } else {
	      	content.style.display = "block";
	      	document.getElementById("collapseIcon").className = "bx bx-minus";
	      	
	      	if (document.getElementById("collapseIcon2")) { // IF AGGIUNTA IL 2023-01-13 EVOLUTIVA 1	      		
	      		document.getElementById("collapseIcon2").className = "bx bx-minus"; // 2022-12-12 aggiunto
	      	}
	      }
	   });	
		  
	  //Creazione pop-up registrazione utente (raggiungibile da ogni pagina)
	   $('.popup-register').on('click', function (event) {
			event.preventDefault();
			$('#register-modal').addClass('is-visible');
			document.getElementById("registerEntId").value = "";
			document.getElementById("registerMail").value ="";
			document.getElementById('registerEntId').readOnly = false;
			document.getElementById('registerMail').readOnly = false;    
    });
    
    //Chiusura modali cliccando "ESC"
     $(document).keyup(function (event) {
        if (event.which == '27') {
            $('.cd-popup').removeClass('is-visible');
             $('.cd-popup2').removeClass('is-visible');
        }
    })
    
    var uploadError = document.getElementById("uploadError");
    if(uploadError != null){
		rebuildCvModal(uploadError);
	}
   
});

function rebuildCvModal(uploadError){	
	if(uploadError.innerHTML != ""){
		var interviewId = document.getElementById("createdInterview");
		if(interviewId != null){
			createUploadCVModal(+interviewId.innerHTML);
			showToast(uploadError.innerHTML, "ERROR", 3000)
		}
			
	}
	uploadError.innerHTML = "";
}


//Creazione toast di successo-errore-warning
function showToast(message, state, delay) {
	var snackB = document.getElementById("snackbar");
	var textColumn = document.getElementById("snackbarTextColumn");
	snackB.className = "show";
	if(state === "SUCCESS"){
		snackB.style.color="#008000";
		snackB.style.border="2px solid #008000";
		textColumn.innerHTML = message;
	} else if (state === "ERROR") { 
		snackB.style.color="#b20000";
		snackB.style.border="2px solid #b20000";
		textColumn.innerHTML = message;
	} else if(state === "WARNING"){
		snackB.style.color="#FFCC00";
		snackB.style.border="2px solid #FFCC00";
		textColumn.innerHTML = message;
	}
	setTimeout(function(){ snackB.className = snackB.className.replace("show", ""); }, delay);
}


function redirectAccess(){
	showToast("Sessione scaduta", "ERROR", 3000);
	window.setTimeout(function(){
		window.location.href = "/interview-ms/access";		
    	}, 2000);		
}
