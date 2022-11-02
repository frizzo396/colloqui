
//Salvataggio colloquio
$(document).ready(function (){
	let arrow = document.querySelectorAll(".arrow");
	for (var i = 0; i < arrow.length; i++) {
	  arrow[i].addEventListener("click", (e)=>{
	 let arrowParent = e.target.parentElement.parentElement;//selecting main parent of arrow
	 arrowParent.classList.toggle("showMenu");
	  });
	}
	

	let sidebarBtn = document.querySelector(".bx-menu");
	
	
	sidebarBtn.addEventListener("click", ()=>{
	let sidebar = document.querySelector(".sidebar");
	if (sidebar.classList.contains('close')) { //sidebar chiusa
		sidebar.classList.toggle("close");
	  	document.getElementById("sidebarColumn").style.width = '20%';
	  	document.getElementById("containerColumn").style.width = '70%';
	  	document.getElementById("containerColumn").style.marginLeft = "0%";
	  	drawChart();
	  	
	} else {
		sidebar.classList.toggle("close");
		document.getElementById("sidebarColumn").style.width = '10%';
	  	document.getElementById("containerColumn").style.width = '75%';
	  	document.getElementById("containerColumn").style.marginLeft = "4.5%";
	  	drawChart();
	}
	});
});

function RedirectHome() {
    const url_string = window.location.href;
    const url = new URL(url_string);
    const enterpriseId = url.searchParams.get("enterpriseId");
   // window.location.href = "/interview-ms/home?enterpriseId=" + enterpriseId;
    window.location.href = "/interview-ms/home";
}

function RedirectInsert() {
    const url_string = window.location.href;
    const url = new URL(url_string);
    const enterpriseId = url.searchParams.get("enterpriseId");
   // window.location.href = "/interview-ms/manage-interview?enterpriseId=" + enterpriseId;
   window.location.href = "/interview-ms/interview/new";
}

function RedirectSearch() {
    const url_string = window.location.href;
    const url = new URL(url_string);
    const enterpriseId = url.searchParams.get("enterpriseId");
   // window.location.href = "/interview-ms/manage-interview?enterpriseId=" + enterpriseId;
   window.location.href = "/interview-ms/interview/search";
}

function RedirectMyInterviews() {
    const url_string = window.location.href;
    const url = new URL(url_string);
    const enterpriseId = url.searchParams.get("enterpriseId");
    //window.location.href = "/interview-ms/interview/completed?enterpriseId=" + enterpriseId;
    window.location.href = "/interview-ms/interview/completed";
}

function RedirectInProgress() {
    const url_string = window.location.href;
    const url = new URL(url_string);
    const enterpriseId = url.searchParams.get("enterpriseId");
    //window.location.href = "/interview-ms/interview/in-progress?enterpriseId=" + enterpriseId;
     window.location.href = "/interview-ms/interview/in-progress";
}
