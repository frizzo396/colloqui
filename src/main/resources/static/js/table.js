jQuery(document).ready(function ($) {
	pagination();
});
  
  
function pagination(){
    $('table.paginated').each(function () {
        var $table = $(this);
        var tablePagRow = $(".table-row");
        var tableColumnPag = $("#tableFormColumn");
        var itemsPerPage = 4;
        var currentPage = 0;
        var pages = Math.ceil($table.find("tr:not(:has(th))").length / itemsPerPage);
        $table.bind('repaginate', function () {
            if (pages > 1) {
                var pager;
                var pagerTest = $(".pager")[0];
                if (pagerTest) {
                   pagerTest.innerHTML  = ' ';
                    pager = pagerTest;
                }
                else
                    pager = $('<div class="pager" style="padding-top: 30px; direction:ltr; color:black; padding-bottom: 30px;" align="center"></div>');

                $('<a style="font-size:12px;"> « Prev&nbsp;&nbsp;&nbsp;&nbsp; </a>').bind('click', function () {
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

                $('<span style="font-size: 12px" >Page '+(currentPage+1)+' of '+pages+'</span>').appendTo(pager);

                $('<a style="font-size:12px"> &nbsp;&nbsp;&nbsp;&nbsp;Next » </a>').bind('click', function () {
                    if (currentPage < pages - 1)
                        currentPage++;
                    $table.trigger('repaginate');
                }).appendTo(pager);
							
				 if (!pagerTest){	
					pager.insertAfter(tablePagRow);
				}
                
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
                
                //Border radius ultima riga tabella
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

}