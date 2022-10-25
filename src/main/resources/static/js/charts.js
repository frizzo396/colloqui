jQuery(document).ready(function ($) {
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

$(window).resize(function(){
drawChart();
});


});



function drawChart() {
  drawTotalChart();
  drawMonthChart();
}



function drawTotalChart() {
	var inProgress = parseFloat($("#inProgressNumber").text());
  var completed = parseFloat($("#completedNumber").text());
  var totalInterviews = inProgress+completed;
  
  
  var data = google.visualization.arrayToDataTable([
  ['Task', 'Hours per Day'],
  ['In progress', inProgress],
  ['Completed', completed]
]);

  // Optional; add a title and set the width and height of the chart
  var options = {
		'title':'Total interviews: '+totalInterviews/*, 
  		//'width':450, 
  		//'height':300*/,
  		legend: {
        	textStyle: { color: '#1429eb' },
        	position: "bottom"
		}, 
		//chartArea: {'width': '100%', 'height': '80%'},
		titleTextStyle: {
    		color: '#1429eb'
		},
  		'colors': ['#bac', '#1429eb']
  };

  // Display the chart inside the <div> element with id="piechart"
  var chart = new google.visualization.PieChart(document.getElementById('totalInterviewsChart'));
  chart.draw(data, options);
}



function drawMonthChart() {
  var inProgress = parseFloat($("#inProgressMonthNumber").text());
  var completed = parseFloat($("#completedMonthNumber").text());
  var totalInterviews = inProgress+completed;
  
  
  var data = google.visualization.arrayToDataTable([
  ['Task', 'Hours per Day'],
  ['In progress', inProgress],
  ['Completed', completed]
]);

  // Optional; add a title and set the width and height of the chart
  var options = {
		'title':'Month interviews: '+totalInterviews/*, 
  		'width':450, 
  		'height':300,*/,
  		legend: {
        	textStyle: { color: '#1429eb' },
			position: "bottom"
		}, 
		//chartArea: {'width': '100%', 'height': '80%'},
		titleTextStyle: {
    		color: '#1429eb'
		},
  		'colors': ['#bac', '#1429eb']
  };

  // Display the chart inside the <div> element with id="piechart"
  var chart = new google.visualization.PieChart(document.getElementById('monthInterviewsChart'));
  chart.draw(data, options);
}
