// reference http://www.icndb.com/api/


$(document).ready(function(){
  $("button").click(function(){
    getJoke();
  });
});

getJoke();
function getJoke() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      //console.log(this.responseText);
     let jokeObj = JSON.parse(this.responseText);
      $(".joke").text(jokeObj.value.joke);
    }
  };
  xhttp.open("GET", "http://api.icndb.com/jokes/randomm", true);
  xhttp.send();
}
