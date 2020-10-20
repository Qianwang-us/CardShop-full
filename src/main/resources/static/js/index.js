
// commuicate with server to update product display when sort type changes
let selectElement = document.getElementById("sort-type");

selectElement.addEventListener('change', (event) => {
  console.log(event.target.value);
});

// toggle heart to add to favorite list
let hearts = document.querySelectorAll(".favoriate i");
for (let i=0; i<hearts.length; i++){
  hearts[i].addEventListener("click", function(event){
    // add to favoriate list
    console.log(event.target);
    event.target.classList.toggle("far");
    event.target.classList.toggle("fas");

  });
}
