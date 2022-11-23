function createUploadCVModal(interviewId){
	document.getElementById("uploadCvIntId").value = interviewId;	
	document.getElementById("upload-cv-modal").classList.add("is-visible");
}


function validationUpload(){
	  	let empty = "";
		let interviewId = document.forms["uploadCV"]["uploadCvIntId"].value;
  		let curriculum = document.forms["uploadCV"]["cv"].value;
  		
  		if(interviewId == empty){
			showToast("Colloquio non trovato", "ERROR", 3000);
			return false;
		}
		else if(curriculum == empty){
			showToast("Selezionare un file per il caricamento", "ERROR", 3000);
			return false;
		}
		else {
			showToast("Operazione eseguita con successo", "SUCCESS", 3000);
		}
}