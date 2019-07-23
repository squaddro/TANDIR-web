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
    

    $('#submit2').click(function(){
    	var userData = new XMLHttpRequest();
        var url = "https://tandir.herokuapp.com/signup";
    	userData.open('POST', url, true);
    	userData.setRequestHeader('Content-Type', 'application/json');
    	userData.withCredentials = true;
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

    
    $('#createRecipe').click(function(){
    	var userData = new XMLHttpRequest();
    	var url = "https://tandir.herokuapp.com/addrecipe";
    	userData.open('POST', url, true);
    	userData.setRequestHeader('Content-Type', 'application/json');
    	userData.withCredentials = true;
    	var today = new Date();
    	var dd = String(today.getDate()).padStart(2, '0');
    	var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    	var yyyy = today.getFullYear();

    	today = dd + '/' + mm + '/' + yyyy;
    	var data = {};
        data.recipe_id = null;
        data.recipe_name = document.getElementById('getName').value.replace(/\t/g, '');
        data.recipe_desc = document.getElementById('getRecipe').value.replace(/\t/g, '');
        data.tag = document.getElementById('getTag').value.replace(/\t/g, '');
        data.user_id = null;
        data.recipe_date = today;
    	var json_data = JSON.stringify(data);
    	userData.onload = function () {
    		
    		if (userData.readyState == 4 && userData.status == 200) {
    	        var response = JSON.parse(userData.responseText);
    	        if(response.status == 106){
                    alert(response.message);
                    document.getElementById('getName').value = '';
                    document.getElementById('getRecipe').value = '';
                    document.getElementById('getTag').value = '';
                    location.reload();
    	        }
    	        else{
    	        	alert(response.message);
    	        }
    	    }  		
    	}
    	userData.send(json_data);
    });



    $('#deleteRecipe').click(function(){
    	
    	var queryString = decodeURIComponent(window.location.search);
    	queryString = queryString.substring(1);
    	var queries = queryString.split("&");
    	var queryString2 = "?" + queries[0]; 
    	var userId = queries[0].substring(6); 
    	
    	var request = new XMLHttpRequest();
        var url = "https://tandir.herokuapp.com/user/" + userId;
    	request.open('GET', url, true);
    	request.withCredentials = true;
    	request.onload = function() {
    	  // Begin accessing JSON data here
    	  var data = JSON.parse(this.response);
		  
    	  if (request.status==200) {
    		  var rec_id;
              for(var i=0; i<data.recipes.length; i++){
            	  if(document.getElementById('getDeletedRecipe').value.replace(/\t/g, '') === data.recipes[i].recipe_name){
            	    rec_id = data.recipes[i].recipe_id;
            	  }
              }      
              var recipeData = new XMLHttpRequest();
          	var url = "https://tandir.herokuapp.com/deleterecipe";
          	recipeData.open('POST', url, true);
          	recipeData.setRequestHeader('Content-Type', 'application/json');
          	recipeData.withCredentials = true;
          	var data = {};
              data.recipe_id = rec_id;
              data.recipe_name = null;
              data.recipe_desc = null;
              data.user_id = null;
          	var json_data = JSON.stringify(data);
          	recipeData.onload = function () {
          		
          		if (recipeData.readyState == 4 && recipeData.status == 200) {
          	        var response = JSON.parse(recipeData.responseText);
          	        if(response.status == 111){
                          alert(response.message);
                          document.getElementById('getDeletedRecipe').value = '';
                          location.reload();
          	        }
          	        else{
          	        	alert(response.message);
          	        }
          	    }  		
          	}
          	recipeData.send(json_data);
          	
    	  } else {
    	     alert('error');
    	  }
    	}
    	request.send();      
    });
    
    $('#searchRecipe').click(function(){
    	var userData = new XMLHttpRequest();
    	var url = "https://tandir.herokuapp.com/search";
    	userData.open('POST', url, true);
    	userData.setRequestHeader('Content-Type', 'application/json');
    	userData.withCredentials = true;
    	var data = {};
    	data.tag = document.getElementById('searching').value.replace(/\t/g, '');
    	var json_data = JSON.stringify(data);
    	userData.onload = function () {
    		
    		if (userData.readyState == 4 && userData.status == 200) {
    	        var returningData = JSON.parse(userData.response);
    	        console.log(returningData);
      		    document.getElementById("WriteRecipeForSearching").innerHTML += "User: " + returningData[0].user_name + "<br>" + "Recipe Name: " + returningData[0].recipe_name + "<br>" + "Recipe: " + returningData[0].recipe_desc + "<br>" + "tag: " + returningData[0].tag + "<br>" + "<br>";              	        
    	    }  		
    	}
    	userData.send(json_data);
    });
    
    
    $('#updateRecipe').click(function(){
    	
    	var queryString = decodeURIComponent(window.location.search);
    	queryString = queryString.substring(1);
    	var queries = queryString.split("&");
    	var queryString2 = "?" + queries[0]; 
    	var userId = queries[0].substring(6); 
    	
    	var request = new XMLHttpRequest();
        var url = "https://tandir.herokuapp.com/user/" + userId;
    	request.open('GET', url, true);
    	request.withCredentials = true;
    	request.onload = function() {
    	  // Begin accessing JSON data here
    	  var data = JSON.parse(this.response);
		  
    	  if (request.status==200) {
    		  var rec_id;
              for(var i=0; i<data.recipes.length; i++){
            	  if(document.getElementById('updatedRecipeName').value.replace(/\t/g, '') === data.recipes[i].recipe_name){
            	    rec_id = data.recipes[i].recipe_id;
            	  }
              }      
            var recipeData = new XMLHttpRequest();
          	var url = "https://tandir.herokuapp.com/updaterecipe";
          	recipeData.open('POST', url, true);
          	recipeData.setRequestHeader('Content-Type', 'application/json');
          	recipeData.withCredentials = true;
          	var data = {};
            data.recipe_id = rec_id;
            data.recipe_name = null;
            data.recipe_desc = null;
          	var checkedValue = null; 
          	if (document.getElementById('upName').checked) {
  	        	data.recipe_name = document.getElementById('newInfo').value.replace(/\t/g, '');
          	}
          	if (document.getElementById('upDesc').checked) {
  	    		data.recipe_desc = document.getElementById('newInfo').value.replace(/\t/g, '');
          	}
          	
        	var json_data = JSON.stringify(data);

          	recipeData.onload = function () {
          		
          		if (recipeData.readyState == 4 && recipeData.status == 200) {
          	        var response = JSON.parse(recipeData.responseText);
          	        if(response.status == 113){
                          alert(response.message);
                          document.getElementById('newInfo').value = '';
                          document.getElementById('updatedRecipeName').value = '';
                          location.reload();
          	        }
          	        else{
          	        	alert(response.message);
          	        }
          	    }  		
          	}
          	recipeData.send(json_data);
          	
    	  } else {
    	     alert('error');
    	  }
    	}
    	request.send();      
    });
	$('#uploadImage').click(function(){
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

function previewFile() {
	var preview = document.getElementById('previewArea');
	var file = document.querySelector('input[type=file]').files[0];
	var reader = new FileReader();
	var ucons = document.getElementById('uploadConsole');
	ucons.innerHTML = "loading";
	reader.onloadend = function() {
		preview.src = reader.result;
		
		var base64 = reader.result.split(',')[1];
		ucons.innerHTML = "uploading";
		var userData = new XMLHttpRequest();
		var url = "https://tandir.herokuapp.com/upload";
        //var url = "http://localhost:8080/upload";
        userData.open('POST', url, true);
    	userData.setRequestHeader('Content-Type', 'application/json');
    	userData.withCredentials = true;
    	var data = {};
        data.payload = base64;
        
    	var json_data = JSON.stringify(data);

    	userData.onload = function () {
    		ucons.innerHTML = "upload finished";
    		if (userData.readyState == 4 && userData.status == 200) {
				console.log(userData.responseText);
    	        var response = JSON.parse(userData.responseText);
    	        
    	    }	
    	}
    	userData.send(json_data);
	}
	if(file) {
		reader.readAsDataURL(file);
	} else {
		preview.src="";
	}
	
	
}