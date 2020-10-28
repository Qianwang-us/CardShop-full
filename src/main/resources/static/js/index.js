
// commuicate with server to update product display when sort type changes
// let selectElement = document.getElementById("sort-type");
//
// selectElement.addEventListener('change', (event) => {
//   console.log(event.target.value);
// });

// commuinicate with server to get next Holiday
let viewBtn = document.getElementById("view-btn");

viewBtn.addEventListener('click', (event) => {
  $.ajax({
          url: "http://localhost:8080/api/days_to_christmas"
      }).then(function(data) {
         $('#days').append(data);
      });
});

// $(document).ready(function() {
//     $.ajax({
//         url: "http://rest-service.guides.spring.io/greeting"
//     }).then(function(data) {
//        $('.greeting-id').append(data.id);
//        $('.greeting-content').append(data.content);
//     });
// });
// $.ajax({
//             url: "/tester.mvc",
//             type: 'GET',
//             dataType: 'json',
//             success: function(value, data){
//                 console.log(data);
//             }
//         });

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
