

jQuery(document).ready(function($){
	$('.cd-popup-trigger').on('click', function(event){
		event.preventDefault();
		$('.cd-popup').addClass('is-visible');
	});

	$('.cd-popup').on('click', function(event){
		if( $(event.target).is('.cd-popup-close') || $(event.target).is('.cd-popup') ) {
			event.preventDefault();
			$(this).removeClass('is-visible');
		}
	});

	$(document).keyup(function(event){
		if(event.which=='27'){
			$('.cd-popup').removeClass('is-visible');
		}
	});

});


//Salvataggio colloquio
$(document).ready(function (){
	var $form= $('#insertInterview');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){
					showToast("Operazione avvenuta con successo, controllare se l'email è stata recapitata correttamente", "SUCCESS", 3000);
					setTimeout(function(){RedirectHome()}, 1700);
				}
				else {
					showToast(response.error, "ERROR", 3000);
				}

		}, 'json');
		return false;
	});
});

//Salvataggio intervistatore
$(document).ready(function (){
	var $form= $('#registerUser');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){
					showToast("Operazione avvenuta con successo", "SUCCESS");
					setTimeout(function(){RedirectHome()}, 1700);
				}
				else {
					showToast(response.error, "ERROR");
				}

		}, 'json');
		return false;
	});
});


//Salvataggio feedback motivazionale colloquio
$(document).ready(function (){
	var $form= $('#insertMotivationalFeedback');
	$form.submit(function (e) {
		e.preventDefault(e);
			$.post($(this).attr('action'), $(this).serialize(), function (response){
				if(response.error == null){
					showToast("Operazione avvenuta con successo", "SUCCESS", 3000);
					setTimeout(function(){RedirectHome()}, 1700);
				}
				else {
					showToast(response.error, "ERROR", 3000);
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
					showToast("Operazione avvenuta con successo", "SUCCESS", 3000);
					setTimeout(function(){RedirectHome()}, 1700);
				}
			else {
					showToast(response.error, "ERROR", 3000);
			}
		}, 'json');
		return false;
	});
});

function validationSearch() {	
  let empty = "";
  let candidateName = document.forms["searchInterview"]["candidateNameId"].value;
  let candidateSurname = document.forms["searchInterview"]["candidateSurnameId"].value;
  let candidateType = document.forms["searchInterview"]["candidateType"].value;
  let interviewType = document.forms["searchInterview"]["interviewType2"].value;
  let enterpriseId = document.forms["searchInterview"]["enterpriseIdS"].value;
  let site = document.forms["searchInterview"]["site"].value;
  let firstDate = document.forms["searchInterview"]["firstDate"].value;
  let secondDate = document.forms["searchInterview"]["secondDate"].value;
  
 if(candidateName == empty &&  candidateSurname == empty &&  candidateType == empty &&  interviewType == empty &&  
 	enterpriseId  == empty &&  site == empty && firstDate == empty &&  secondDate == empty) {
		showToast("Almeno un campo deve essere valorizzato", "ERROR", 3000);
		return false;
	} else {
		showToast("Operazione avvenuta con successo", "SUCCESS", 2000);
		window.setTimeout(function () { document.getElementById('searchInterview').submit(); }, 400); 
		return false;
	}

function resetSearchTable() {
	document.getElementById("searchTableContainer").style.display = 'none';
}

function showToast(message, state, delay) {
	var snackB = document.getElementById("snackbar");
	var iconColumn = document.getElementById("snackbarIconColumn");
	var textColumn = document.getElementById("snackbarTextColumn");
	snackB.className = "show";
	if(state === "SUCCESS"){
		snackB.style.backgroundColor="#008000";
		iconColumn.innerHTML = "<em id='successIcon' class='fa-solid fa-circle-check'></em>";
		textColumn.innerHTML = message;
	} else if (state === "ERROR") { 
		snackB.style.backgroundColor="#b20000";
		iconColumn.innerHTML = "<em id='errorIcon' class='fa-solid fa-circle-exclamation'></em>";
		textColumn.innerHTML = message;
	} else if(state === "WARNING"){
		snackB.style.backgroundColor="#FFCC00";
		iconColumn.innerHTML  = "<em id='warningIcon' class='fa-solid fa-circle-exclamation'></em>";
		textColumn.innerHTML = message;
	}
	setTimeout(function(){ snackB.className = snackB.className.replace("show", ""); }, delay);
}

//Paginazione tabella
function pagination(){
	$('table.paginated').each(function () {
		var $table = $(this);
		var itemsPerPage = 4;
		var currentPage = 0;
		var pages = Math.ceil($table.find("tr:not(:has(th))").length / itemsPerPage);
		$table.bind('repaginate', function () {
			if (pages > 1) {
				var pager;
				if ($table.next().hasClass("pager"))
					pager = $table.next().empty();  else
					pager = $('<div class="pager" style="padding: 30px; direction:ltr;" align="center"></div>');

				$('<a id="prev" style="font-size:14px; cursor:pointer;"> « Prev&nbsp;&nbsp;&nbsp;&nbsp; </a>').bind('click', function () {
					if (currentPage > 0)
						currentPage--;
					$table.trigger('repaginate');
				}).appendTo(pager);

				var startPager = currentPage > 2 ? currentPage - 2 : 0;
				var endPager = startPager > 0 ? currentPage + 3 : 5;
				if (endPager > pages) {
					endPager = pages;
					startPager = pages - 5;    if (startPager < 0)
						startPager = 0;
				}

				$('<span id="page" style="font-size: 14px" >Page '+(currentPage+1)+' of '+pages+'</span>').appendTo(pager);

				$('<a id="next" style="font-size:14px; cursor:pointer;"> &nbsp;&nbsp;&nbsp;&nbsp;Next » </a>').bind('click', function () {
					if (currentPage < pages - 1)
						currentPage++;
					$table.trigger('repaginate');
				}).appendTo(pager);

				if (!$table.next().hasClass("pager"))
					pager.insertAfter($table);
				//pager.insertBefore($table);

			}// end $table.bind('repaginate', function () { ...

			$table.find(
				'tbody tr:not(:has(th))').hide().slice(currentPage * itemsPerPage, (currentPage + 1) * itemsPerPage).show();
		});

		$table.trigger('repaginate');
	});

}

