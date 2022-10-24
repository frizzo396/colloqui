


jQuery(document).ready(function ($) {
    //open popup Motivational
    $('.cd-popup-trigger').on('click', function (event) {
		event.preventDefault();
		$('.cd-popup').addClass('is-visible');
    	
    });

    //close popup when clicking the esc keyboard button
    $(document).keyup(function (event) {
        if (event.which == '27') {
            $('.cd-popup').removeClass('is-visible');
        }
    });
    
    
    
    //open popup Technical
    $('.cd-popup-trigger2').on('click', function (event) {
		event.preventDefault();
		$('.cd-popup2').addClass('is-visible');
    
    });


    //close popup when clicking the esc keyboard button
    $(document).keyup(function (event) {
        if (event.which == '27') {
            $('.cd-popup2').removeClass('is-visible');
        }
    });

    pagination();

});


function searchDateAlternative() {
    // get the values and convert to date
    const input_startDate = new Date(document.getElementById("date-start").value);
    const input_stopDate = new Date(document.getElementById("date-stop").value);

    var dateInterviews= document.getElementsByClassName("dateInterview");
    var rows= document.getElementsByClassName("tableRows");

    for (var i=0; i<dateInterviews.length; i++){
        var str= dateInterviews[i].innerHTML.substring(6,10).concat("-").concat(dateInterviews[i].innerHTML.substring(0,5));
        var date= new Date(str);
        if (!(date>=input_startDate && date<=input_stopDate)){
            rows[i].style.display= "none"

        }else{
            rows[i].style.display= "table-row"
        }
    }
    //pagination();

}



function closeTechModal(){
    var tech = document.getElementsByClassName("cd-popup2");
    $(tech).removeClass("is-visible");
}

function closeMotivationModal(){
    var motivation= document.getElementsByClassName("cd-popup");
    $(motivation).removeClass("is-visible");
}

function GotoTechnicalFeedback(id) {
    window.location.href = "/interview-ms/feedback/technical?idColloquio=" + id;
}

function GotoMotivationalFeedback(id) {
    window.location.href = "/interview-ms/feedback/motivational?idColloquio="+id;
}

function createMotivationalModal(motivationalInterview){
    var obj = JSON.parse(motivationalInterview);
	document.getElementById('motStanding').value = obj.standing; 
	document.getElementById('motMotivation').value = obj.motivation;
	document.getElementById('motSchool').value = obj.schoolBackground;
	document.getElementById('motEnglish').value = obj.englishLevel;
	document.getElementById('motSkills').value = obj.softSkills;
	document.getElementById('motLogic').value = obj.logicQuestion;
	document.getElementById('motTech').value = obj.techQuestion;
	document.getElementById('motComment').value = obj.comment;
}

function createTechnicalModal(technicalInterview){
    var obj = JSON.parse(technicalInterview);
    document.getElementById('techJava').value = obj.java; 
    document.getElementById('techSql').value = obj.sql; 
    document.getElementById('techHtml').value = obj.htmlCss; 
    document.getElementById('techAngular').value = obj.angular; 
    document.getElementById('techSpring').value = obj.spring;
    document.getElementById('techOther').value = obj.other;  
    document.getElementById('techComment').value = obj.comment; 
}

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
                    pager = $('<div class="pager" style="padding-top: 30px; direction:ltr;" align="center"></div>');

                $('<a style="font-size:14px;"> « Prev&nbsp;&nbsp;&nbsp;&nbsp; </a>').bind('click', function () {
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

                $('<span style="font-size: 14px" >Page '+(currentPage+1)+' of '+pages+'</span>').appendTo(pager);

                $('<a style="font-size:14px"> &nbsp;&nbsp;&nbsp;&nbsp;Next » </a>').bind('click', function () {
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