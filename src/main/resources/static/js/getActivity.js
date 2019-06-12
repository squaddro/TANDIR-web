$(document).ready(function(){
    $('#viewRecipes').click(function(){
    	
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
    		  console.log("girildi");
    		  
              console.log(data);
              for(var i=0; i<data.recipes.length; i++){
            	  var request2 = new XMLHttpRequest();
            	  var recId = data.recipes[i];
                  var recipe = "https://tandir.herokuapp.com/recipe/" + recId;
                  request2.open('GET', recipe, true);
              	  request2.withCredentials = true;
                  request2.onload = function() {
                	  var deneme= { "recipe_name" : "ilk tarif", "recipe_desc" : "domatesler dogranir." };
                	  var data2 = JSON.parse(this.response);
                	  if (request2.status==200) {
                		  document.getElementById("writeRecipeName").innerHTML = data2.recipe_name;
                		  document.getElementById("writeRecipeName").innerHTML = data2.recipe_desc;
                	  }
                	  else{
                		  alert("Error on view recipes.");
                	  }
                  }
              	request2.send();
           	  
              }
  	        window.location.href = "ViewRecipes.html" + queryString2;

    	  } else {
    	     alert('error');
    	  }
    	}

    	request.send();
    });
});