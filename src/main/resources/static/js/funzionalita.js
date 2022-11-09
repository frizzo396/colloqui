

jQuery(document).ready(function($){
	
	$(document).on('click','#closeModal', function(e) {
  		$('.cd-popup').removeClass('is-visible');
  		$('.cd-popup2').removeClass('is-visible');
	});
	
	$("select").change(function() {
		var selectedValue = $(this).val();
		
		if(selectedValue == ""){
				$(this).css('color','#afb0b3');
		} else {
				$(this).css('color','black');
		}
	})
	$(".datePicker").change(function() {
			var selectedValue = $(this).val();
			if(selectedValue == ""){
				$(this).css('color','#afb0b3');
			} else {
					$(this).css('color','black');
			}		
	}) 
		$(".num-input").change(function() {
			var selectedValue = $(this).val();
			if(selectedValue == 0){  
				$(this).attr('style', 'color: #afb0b3 !important');
			} else {
					$(this).attr('style', 'color: black !important');
			}		
	})
	
		$("#cv").change(function() {
			var selectedValue = $(this).val();
			if(selectedValue == ""){
				$(this).css('color','#afb0b3');
			} else {
					$(this).css('color','black');
			}		
	})
	
	$('.clear-button').click(function(){
	    $('.datePicker').css('color','#afb0b3');
	    $('select').css('color','#afb0b3');
	    $('#cv').css('color','#afb0b3');
	    $(".num-input").attr('style', 'color: #afb0b3 !important');
	});
	
	
$('.collapsible').click(function(){
var content = this.nextElementSibling;
	   if (content.style.display === "block") {
      		content.style.display = "none";
      		document.getElementById("collapseIcon").className = "bx bx-plus";
   		 } else {
      	content.style.display = "block";
      	document.getElementById("collapseIcon").className = "bx bx-minus";
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

//Salvataggio intervistatore
$(document).ready(function (){
	var $form= $('#registerUser');
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
}

function resetSearchTable() {
	document.getElementById("searchTableContainer").style.display = 'none';
}

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
					pager = $('<div class="pager" style="padding-top: 30px; direction:ltr; color:black; padding-bottom: 30px;" align="center"></div>');

				$('<a id="prev" style="font-size:12px; cursor:pointer;"> « Prev&nbsp;&nbsp;&nbsp;&nbsp; </a>').bind('click', function () {
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

				$('<span id="page" style="font-size: 12px" >Page '+(currentPage+1)+' of '+pages+'</span>').appendTo(pager);

				$('<a id="next" style="font-size:12px; cursor:pointer;"> &nbsp;&nbsp;&nbsp;&nbsp;Next » </a>').bind('click', function () {
					if (currentPage < pages - 1)
						currentPage++;
					$table.trigger('repaginate');
				}).appendTo(pager);

				if (!$table.next().hasClass("pager"))
					pager.insertAfter($table);
				//pager.insertBefore($table);
			}else {				
				var tableForm = document.getElementById("tableFormColumn");
				var searchForm = document.getElementById("searchFormColumn");
				if(tableForm != null){
					tableForm.style.padding = "40px";
				}
				if(searchForm != null){
					searchForm.style.paddingBottom = "25px";	
				}						
			}

			$table.find(
				'tbody tr:not(:has(th))').hide().slice(currentPage * itemsPerPage, (currentPage + 1) * itemsPerPage).show();
				
				let rows = document.querySelectorAll('.tableRows');
				for (var i = 0; i < rows.length; i++) {
				  if (rows[i].style.display === "none") {
						let lastRow = rows[i-1];
						var cells = lastRow.getElementsByTagName("td");
						var firstCell = cells[0];
						var lastCell = cells[cells.length -1];
						firstCell.style.borderBottomLeftRadius = "5px";
						lastCell.style.borderBottomRightRadius = "5px";						
						break;
				  }
				}
		});

		$table.trigger('repaginate');
	});
	
	

$(document).ready(function (){
	var $form= $('#insertAvailability');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){
					showToast("Operazione avvenuta con successo'", "SUCCESS", 3000);
					setTimeout(function(){RedirectInProgress()}, 1500);
				}
				else {
					showToast(response.error, "ERROR", 3000);
				}

		}, 'json');
		return false;
	});
});




$(document).ready(function (){
	var $form= $('#approveAvailability');
	$form.submit(function (e) {
		e.preventDefault(e);
		$.post($(this).attr('action'), $(this).serialize(), function (response){
			if(response.error == null){
					showToast("Operazione avvenuta con successo'", "SUCCESS", 3000);
					setTimeout(function(){RedirectAssigned()}, 1500);
				}
				else {
					showToast(response.error, "ERROR", 3000);
				}

		}, 'json');
		return false;
	});
});

}