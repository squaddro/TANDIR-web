$('.message a').click(function(){
   $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
});
$('#signOut').click(function(){
		
	var request = new XMLHttpRequest();
    var url = "https://tandir.herokuapp.com/signout" ;
	request.open('GET', url, true);
	request.withCredentials = true;
	request.onload = function() {
		if (request.status==200) {
	        var response = JSON.parse(request.responseText);
	        if(response.status == 115){
	        	alert(response.message);
	        	window.open("index.html","_self");
	        }
		}
	}
	request.send();
});






