jQuery(document).ready(function ($) {
	
	//Creazione popup dettaglio feedback motivazionale
    $('.motiv-popup').on('click', function (event) {
		event.preventDefault();
		var textAreas = document.getElementsByClassName("content");
		let collIcon = document.getElementById("collapseIcon");
		if(collIcon != null) {
			collIcon.className = "bx bx-minus";
			Array.prototype.forEach.call(textAreas, function(cont) {
			   	cont.style.display = 'block';
			});
		}
		$('#motiv-modal').addClass('is-visible');
    	
    });
    
    //Creazione popup dettaglio feedback tecnico
    $('.tech-popup').on('click', function (event) {
		event.preventDefault();	
		var textAreas = document.getElementsByClassName("content");
		let collIcon = document.getElementById("collapseIcon2");		
		if(collIcon != null) {
			collIcon.className = "bx bx-minus";
			Array.prototype.forEach.call(textAreas, function(cont) {
			   	cont.style.display = 'block';
			});
		}
		$('#tech-modal').addClass('is-visible');
    
    });  
    
    
	//Salvataggio feedback motivazionale colloquio
	$(document).ready(function (){
		var $form= $('#insertMotivationalFeedback');
		$form.submit(function (e) {
			e.preventDefault(e);
				$.post($(this).attr('action'), $(this).serialize(), function (response){
					if(response.error == null){
						// PER TOGLIERE ROTELLINA SPINNER - START
						var submitBtn = document.getElementById('btn_mot-feed_submit');
						setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); 
						           showToast("Operazione avvenuta con successo", "SUCCESS", 3000); 
						           setTimeout(function(){RedirectHome()}, 1700);}, 2000);
					}
					else {
						if(response.error == "EXPIRED"){
							redirectAccess();	
						} else {
							var submitBtn = document.getElementById('btn_mot-feed_submit');
							setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast(response.error, "ERROR", 3000);}, 2000);
						}
					}
	
			}, 'json');
			return false;
		});
	});
	
	//Salvataggio feedback tecnico colloquio
	$(document).ready(function (){
		var $form= $('#insertTechFeedback');
		$form.submit(function (e) {
			e.preventDefault(e);
			$.post($(this).attr('action'), $(this).serialize(), function (response){
				if(response.error == null){					
						// PER TOGLIERE ROTELLINA SPINNER - START
						var submitBtn = document.getElementById('btn_tech-feed_submit');
						setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); 
						           showToast("Operazione avvenuta con successo", "SUCCESS", 3000); 
						           setTimeout(function(){RedirectHome()}, 1700);}, 2000);
					}
				else {		
						if(response.error == "EXPIRED"){
							redirectAccess();	
						} else {
							var submitBtn = document.getElementById('btn_tech-feed_submit');
							setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast(response.error, "ERROR", 3000);}, 2000);
						}
				}
			}, 'json');
			return false;
		});
	});

	
	
});

function closeTechModal(){
    var tech = document.getElementsByClassName("cd-popup2");
    $(tech).removeClass("is-visible");
}

function closeMotivationModal(){
    var motivation= document.getElementsByClassName("cd-popup");
    $(motivation).removeClass("is-visible");
}

function goToTechnicalFeedback(id) {
    window.location.href = "/interview-ms/feedback/technical?idColloquio=" + id;
}

function goToMotivationalFeedback(id) {
    window.location.href = "/interview-ms/feedback/motivational?idColloquio="+id;
}

function goToMotivationalFeedback() {
	var interviewId = document.getElementById("motModalInterview").value;
    window.location.href = "/interview-ms/feedback/motivational?idColloquio="+interviewId;
}

function goToTechnicalFeedback() {
	var interviewId = document.getElementById("techModalInterview").value;
    window.location.href = "/interview-ms/feedback/technical?idColloquio="+interviewId;
}


function createMotivationalModal(motivationalInterview, interviewId, interviewer, check){
    var obj = JSON.parse(motivationalInterview);   
    document.getElementById('motModalInterview').value = interviewId; 
	document.getElementById('motStanding').value = obj.standing; 
	document.getElementById('motMotivation').value = obj.motivation;
	document.getElementById('motSchool').value = obj.schoolBackground;
	document.getElementById('motEnglish').value = obj.englishLevel;
	document.getElementById('motLogic').value = obj.logicQuestion;
	document.getElementById('motTech').value = obj.techQuestion;
	document.getElementById('motComment').value = obj.comment;
	if(check == true){
		var user = document.getElementById('nickname').textContent;
		if(interviewer != user) {
			document.getElementById('editMotivFeedbackRow').style.setProperty("display", "none", "important");
		} else {
			document.getElementById('editMotivFeedbackRow').style.setProperty("display", "block", "important");
		}
	} else {
		document.getElementById('editMotivFeedbackRow').style.setProperty("display", "block", "important");
	}
	document.getElementById('motComment').style.display = 'block';
	
}


function createTechnicalModal(interviewId, technicalInterview, interviewer, check){
	document.getElementById('techModalInterview').value = interviewId; 
	if(check == true){
		var user = document.getElementById('nickname').textContent;
		if(interviewer != user) {
			document.getElementById('editTechFeedbackRow').style.setProperty("display", "none", "important");
		}else {
			document.getElementById('editTechFeedbackRow').style.setProperty("display", "block", "important");
		}
	} else {
		document.getElementById('editTechFeedbackRow').style.setProperty("display", "block", "important");
	}
    var obj = JSON.parse(technicalInterview);
    
    // FEEDBACK REGISTRATO IN FORMATO JSON NEL CAMPO SCORES - START
    var scoresJson = obj.scores 
    
    document.getElementById("tech_descr_1").textContent = "";
    document.getElementById("tech_score_1").value = "";
    document.getElementById("tech_col_1").style.display = "none";
    
    document.getElementById("tech_descr_2").textContent = "";
    document.getElementById("tech_score_2").value = "";
    document.getElementById("tech_col_2").style.display = "none";
    
    document.getElementById("tech_descr_3").textContent = "";
    document.getElementById("tech_score_3").value = "";
    document.getElementById("tech_col_3").style.display = "none";
    
    document.getElementById("tech_descr_4").textContent = "";
    document.getElementById("tech_score_4").value = "";
    document.getElementById("tech_col_4").style.display = "none";
    
    document.getElementById("tech_descr_5").textContent = "";
    document.getElementById("tech_score_5").value = "";
    document.getElementById("tech_col_5").style.display = "none";
    
    document.getElementById("tech_descr_6").textContent = "";
    document.getElementById("tech_score_6").value = "";
    document.getElementById("tech_col_6").style.display = "none";
    
    document.getElementById("no_score_label").textContent = "";
    document.getElementById("no_score_div").style.display = "none";
    
    if (scoresJson) {
	    const objVect = JSON.parse(obj.scores);
	    
	    if (objVect.length >= 1) {
			for (let i = 0; i < objVect.length; i++) {
			    var num = i + 1;
			    document.getElementById("tech_descr_" + num).textContent = objVect[i].technology;
			    document.getElementById("tech_score_" + num).value = objVect[i].score;
			    document.getElementById("tech_col_" + num).style.display = "block";    
			}				
		}	
    } else {
		document.getElementById("no_score_div").style.display = "block";
		document.getElementById("no_score_label").textContent = "NESSUNO\u00a0SCORE!";
		document.getElementById("no_score_label").style.width = "400px";
	}
    // FEEDBACK REGISTRATO IN FORMATO JSON NEL CAMPO SCORES - END
         
    document.getElementById('techComment').value = obj.comment; 
}