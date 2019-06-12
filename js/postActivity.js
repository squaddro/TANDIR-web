$(document).ready(function(){
    $('#submit1').click(function(){
    	var userData = new XMLHttpRequest();
        var url = "https://tandir.herokuapp.com/signin";
        userData.open('POST', url, true);
    	userData.setRequestHeader('Content-Type', 'application/json');
    	userData.withCredentials = true;
    	var data = {};
        data.user_name = document.getElementById('login_username').value;
        data.password = document.getElementById('login_password').value;
        var queryString = "?para1=" + document.getElementById('login_username').value; 
    	var json_data = JSON.stringify(data);

    	userData.onload = function () {
    		
    		if (userData.readyState == 4 && userData.status == 200) {
    	        var response = JSON.parse(userData.responseText);
    	        if(response.status == 100){
    	        	alert(response.message);
    	            window.location.href = "OpeningPage.html" + queryString;
    	        }
    	        else{
    	        	alert(response.message);
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
    	        	alert(response.message);
    	        	window.open("index.html","_self");
    	        }
    	        else{
    	        	alert(response.message);
    	        	window.open("index.html","_self");
    	        }
    	    }  		
    	}
    	userData.send(json_data);
    });
});

$(document).ready(function(){
    $('#createRecipe').click(function(){
    	var userData = new XMLHttpRequest();
    	var url = "https://tandir.herokuapp.com/addrecipe";
    	userData.open('POST', url, true);
    	userData.setRequestHeader('Content-Type', 'application/json');
    	var data = {};
        data.recipe_id = null;
        data.recipe_name = document.getElementById('getName').value;
        data.recipe_desc = document.getElementById('getRecipe').value;
        data.user_id = null;
    	var json_data = JSON.stringify(data);
    	userData.onload = function () {
    		
    		if (userData.readyState == 4 && userData.status == 200) {
    	        var response = JSON.parse(userData.responseText);
    	        if(response.status == 106){
                    alert(response.message);    	        	
    	        }
    	        else{
    	        	alert(response.message);
    	        }
    	    }  		
    	}
    	userData.send(json_data);
    });
});