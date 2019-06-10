$(document).ready(function(){
    $('#submit1').click(function(){
    	var request = new XMLHttpRequest()
        var url = "https://tandir.herokuapp.com/greeting";
    	request.open('GET', url, true)
    	request.setRequestHeader('Content-Type', 'application/json');
    	request.onload = function() {
    	  // Begin accessing JSON data here
    	  var data = JSON.parse(this.response)
             alert(data.joke.joke);
    	  if (request.status==200) {
    		 alert(document.getElementById('login_username').value);

    	  } else {
    	     alert('error')
    	  }
    	}

    	request.send()
    });
});