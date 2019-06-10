$(document).ready(function(){
    $('#submit1').click(function(){
    	var userData = new XMLHttpRequest();
        var url = "https://tandir.herokuapp.com/signin";
        userData.open('POST', url, true);
    	userData.setRequestHeader('Content-Type', 'application/json');
    	var data = {};
        data.user_name = document.getElementById('login_username').value;
        data.password = document.getElementById('login_password').value;
    	var json_data = JSON.stringify(data);

    	userData.onload = function () {
    		
    		if (userData.readyState == 4 && userData.status == 200) {
    	        var response = JSON.parse(userData.responseText);
    	        if(response.status == 100){
    	        	window.open("OpeningPage.html","_self");
    	        }
    	        else{
    	        	alert("hatali giris");
    	        	window.open("index.html","_self");
    	        }
    	    }	
    	}
    	userData.send(json_data);
    });
});

$(document).ready(function(){
    $('#submit2').click(function(){
    	var userData = new XMLHttpRequest();
        var url = "https://tandir.herokuapp.com/signup";
    	userData.open('POST', url, true);
    	userData.setRequestHeader('Content-Type', 'application/json');
    	var data = {};
        data.user_name = document.getElementById('signup_username').value;
        data.password = document.getElementById('signup_password').value;
    	var json_data = JSON.stringify(data);
    	userData.onload = function () {
    		
    		if (userData.readyState == 4 && userData.status == 200) {
    	        var response = JSON.parse(userData.responseText);
    	        if(response.status == 102){
    	        	window.open("index.html","_self");
    	        }
    	        else{
    	        	alert("hatali kayit");
    	        	window.open("index.html","_self");
    	        }
    	    }  		
    	}
    	userData.send(json_data);
    });
});