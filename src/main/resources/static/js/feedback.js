jQuery(document).ready(function ($) {
	
	//Creazione popup dettaglio feedback motivazionale
    $('.motiv-popup').on('click', function (event) {
		event.preventDefault();
		var textAreas = document.getElementsByClassName("content");
		let collIcon = document.getElementById("collapseIcon");
		if(collIcon != null) {
			collIcon.className = "bx bx-plus";
			Array.prototype.forEach.call(textAreas, function(cont) {
			   	cont.style.display = 'none';
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
			collIcon.className = "bx bx-plus";
			Array.prototype.forEach.call(textAreas, function(cont) {
			   	cont.style.display = 'none';
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
						// PER TOGLIERE ROTELLINA SPINNER - END						
						
						// showToast("Operazione avvenuta con successo", "SUCCESS", 3000);
						// setTimeout(function(){RedirectHome()}, 1700);
					}
					else {
						// PER TOGLIERE ROTELLINA SPINNER - START
						var submitBtn = document.getElementById('btn_mot-feed_submit');
						setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast(response.error, "ERROR", 3000);}, 2000);
						// PER TOGLIERE ROTELLINA SPINNER - END						
						
						// showToast(response.error, "ERROR", 3000);
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
						// PER TOGLIERE ROTELLINA SPINNER - END
											
						// showToast("Operazione avvenuta con successo", "SUCCESS", 3000);
						// setTimeout(function(){RedirectHome()}, 1700);
					}
				else {					
						// PER TOGLIERE ROTELLINA SPINNER - START
						var submitBtn = document.getElementById('btn_tech-feed_submit');
						setTimeout(function(){submitBtn.classList.remove("submit-spinner--loading"); showToast(response.error, "ERROR", 3000);}, 2000);
						// PER TOGLIERE ROTELLINA SPINNER - END
												
						// showToast(response.error, "ERROR", 3000);
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

function createMotivationalModal(motivationalInterview){
    var obj = JSON.parse(motivationalInterview);
	document.getElementById('motStanding').value = obj.standing; 
	document.getElementById('motMotivation').value = obj.motivation;
	document.getElementById('motSchool').value = obj.schoolBackground;
	document.getElementById('motEnglish').value = obj.englishLevel;
	document.getElementById('motLogic').value = obj.logicQuestion;
	document.getElementById('motTech').value = obj.techQuestion;
	document.getElementById('motComment').value = obj.comment;
}

function createTechnicalModal(technicalInterview){
    var obj = JSON.parse(technicalInterview);
    
    // FEEDBACK REGISTRATO IN FORMATO JSON NEL CAMPO SCORES - START
    var scoresJson = obj.scores    
    const objVect = JSON.parse(obj.scores);    
    
	for (let i = 0; i < objVect.length; i++) {
	    var num = i + 1;
	    document.getElementById('tech_descr_' + num).value = objVect[i].technology;
	    document.getElementById('tech_score_' + num).value = objVect[i].score;
	    document.getElementById("tech_col_" + num).style.display = "block";    
	}    
    // FEEDBACK REGISTRATO IN FORMATO JSON NEL CAMPO SCORES - END
         
    document.getElementById('techComment').value = obj.comment; 
}