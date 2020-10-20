let previewEle = document.querySelector(".preview-btn");
previewEle.onclick = preview;

function preview(){
  //console.log("inside preview");
  let top = document.getElementById("custom-top").value;
  let body = document.getElementById("custom-body").value;
  let bottom = document.getElementById("custom-bottom").value;
  //let msg= document.getElementById("message");

  // if(!top ||!body || !bottom){
  //   console.log("some are empty");
  //   msg.innerHTML="Please fill in all three custom fields.";
  //   msg.style.display = "block";
  //   return;
  // }
 // console.log("all filled");
 //  msg.style.display = "none";
  let template_top = document.querySelector(".top");
  let template_body = document.querySelector(".body");
  let template_bottom = document.querySelector(".bottom");
  template_top.textContent = top;
  template_body.textContent = body;
  template_bottom.textContent = bottom;

  /*let text_color = document.querySelector("input[name='textColor']:checked").value;
  let text_font = document.querySelector("input[name='font']:checked").value;*/
  
  let text_color = document.querySelector("#text-color input[type='radio']:checked").value;
  let text_font = document.querySelector("#text-font input[type='radio']:checked").value;
  
  console.log("text_color: "+text_color);
  console.log("text_font: "+text_font);
  
  template_top.style.color = text_color;
  template_body.style.color = text_color;
  template_bottom.style.color = text_color;

  let text_real_font = text_font;
  if(text_font == "Elegant"){
    text_real_font = "Petit Formal Script";
  }else if(text_font == "Cute"){
    text_real_font = "Grandstander";
  }else if(text_font == "Lively"){
    text_real_font = "Dancing Script";
  }

  template_top.style.fontFamily = text_real_font;
  template_body.style.fontFamily = text_real_font;
  template_bottom.style.fontFamily = text_real_font;
}

let heart = document.querySelector(".product-description i");

  heart.addEventListener("click", function(event){
    // add to favoriate list
    console.log(event.target);
    event.target.classList.toggle("far");
    event.target.classList.toggle("fas");

  });
