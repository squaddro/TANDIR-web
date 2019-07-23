
function setRecipes(btn){
	document.getElementById(btn.id).disabled = true;
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
		
          var recArr = [];
          for(var i=0; i<data.recipes.length; i++){
        	  var recId = data.recipes[i].recipe_id;
              recArr[i]=recId;
          }
          
          var leng = recArr.length;
          var request2 = [], i;
          for( i=0; i<leng; i++){
        	  (function(i){
        	  request2[i] = new XMLHttpRequest();  
              var recipe = "https://tandir.herokuapp.com/recipe/" + recArr[i];
              request2[i].open('GET', recipe, true);
              request2[i].withCredentials = true;
              request2[i].onload = function() {
            	  var data2 = JSON.parse(this.response);
            	  if (request2[i].status==200) {
					  var recipeDiv = '<div class="recipe">' + data2.recipe_name + "<br>" + data2.recipe_desc;
					  for(j=0; j<data2.uris.length; j++){
							recipeDiv += "<br>" + '<img style="height:100px" src="https://tandir.herokuapp.com/imagebin/' + data2.uris[j] + '"></img>';
					  };
					  recipeDiv += "</div>"
            		  document.getElementById("WriteRecipe").innerHTML += recipeDiv;
            	  }
            	  else{
            		  alert("Error on view recipes.");
            	  }
              }
          	request2[i].send();
        	  })(i);
          }
	  } else {
	     alert('error');
	  }
	}
	request.send();
}

$(document).ready(function(){
    $('#viewRecipes').click(function(){
    	var queryString = decodeURIComponent(window.location.search);
    	queryString = queryString.substring(1);
    	var queries = queryString.split("&");
    	var queryString2 = "?" + queries[0]; 
	    window.location.href = "ViewRecipes.html" + queryString2;
    });
});

$(document).ready(function(){
    $('#accountSettings').click(function(){
    	var queryString = decodeURIComponent(window.location.search);
    	queryString = queryString.substring(1);
    	var queries = queryString.split("&");
    	var queryString2 = "?" + queries[0]; 
	    window.location.href = "AccountSettings.html" + queryString2;
    });
});

