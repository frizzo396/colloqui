function createUploadCVModal(interviewId){
	document.getElementById("uploadCvIntId").value = interviewId;	
	document.getElementById("upload-cv-modal").classList.add("is-visible");
}


function validationUpload(){
	  	let empty = "";
		let interviewId = document.forms["uploadCV"]["uploadCvIntId"].value;
  		let curriculum = document.forms["uploadCV"]["cv"].value;
  		
  		if(interviewId == empty){
			// PER TOGLIERE ROTELLINA SPINNER - START
			var submitBtn = document.getElementById('btn_upload_submit');
			setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast("Colloquio non trovato", "ERROR", 3000);}, 2000);
			// PER TOGLIERE ROTELLINA SPINNER - END	
	
			// showToast("Colloquio non trovato", "ERROR", 3000);
			return false;
		}
		else if(curriculum == empty){
			// PER TOGLIERE ROTELLINA SPINNER - START
			var submitBtn = document.getElementById('btn_upload_submit');
			setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast("Selezionare un file per il caricamento", "ERROR", 3000);}, 2000);
			// PER TOGLIERE ROTELLINA SPINNER - END
						
			// showToast("Selezionare un file per il caricamento", "ERROR", 3000);
			return false;
		}
		else {
			// PER TOGLIERE ROTELLINA SPINNER - START
			var submitBtn = document.getElementById('btn_upload_submit');
			setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); 
			           showToast("Operazione avvenuta con successo", "SUCCESS", 3000);}, 2000);
			// PER TOGLIERE ROTELLINA SPINNER - END				
			
			// showToast("Operazione eseguita con successo", "SUCCESS", 3000);
		}
}