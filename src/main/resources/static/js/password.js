jQuery(document).ready(function($){	
	
	$('#toggleOldPassword').click(function() {
		
		var oldPassword = document.getElementById('inputOldPassword');
		
		if (oldPassword.getAttribute('type') === 'password') {
					
			oldPassword.setAttribute('type', 'text');			
			$("#toggleOldPassword").attr('class', 'bx bx-show icon-color');
					
		} else {			
			oldPassword.setAttribute('type', 'password');			
			$("#toggleOldPassword").attr('class', 'bx bx-low-vision icon');			
		}		
	});
	
	$('#toggleNewPassword').click(function() {
		
		var newPassword = document.getElementById('inputNewPassword');
		
		if (newPassword.getAttribute('type') === 'password') {
					
			newPassword.setAttribute('type', 'text');			
			$("#toggleNewPassword").attr('class', 'bx bx-show icon-color');
					
		} else {			
			newPassword.setAttribute('type', 'password');			
			$("#toggleNewPassword").attr('class', 'bx bx-low-vision icon');			
		}		
	});
	
	$('#toggleAccessPassword').click(function() {
		
		var accessPassword = document.getElementById('accessPassword');
		
		if (accessPassword.getAttribute('type') === 'password') {
					
			accessPassword.setAttribute('type', 'text');			
			$("#toggleAccessPassword").attr('class', 'bx bx-show icon-access-color');
					
		} else {			
			accessPassword.setAttribute('type', 'password');			
			$("#toggleAccessPassword").attr('class', 'bx bx-low-vision icon-access');			
		}		
	});		

});