/*Do not need funtion, will be removed later*/

getOrderTotal();

function getOrderTotal(){
  let total = 0;
  let quantityArray = document.getElementsByName("quantity");
  let priceArray = document.getElementsByClassName("price");

  if(priceArray.length == 0){
    let main = document.getElementsByTagName("main")[0];
    main.style.display = "None";
    let message = document.createElement("div");
    message.textContent = "No items to checkout now, please continue shopping."
    document.body.appendChild(message);
    return;
  }

  for (let i = 0; i< priceArray.length; i++){
    let price = priceArray[i].textContent;
    let quantity = quantityArray[i].value;
    //console.log("price"+price);
    //console.log("quantity"+quantity);
    total += parseFloat(price)* parseInt(quantity);
  }

  let totalPrice = document.getElementById("total-price");
  totalPrice.textContent= Math.round(total*100)/100;


  let shipping = document.getElementById("shipping").textContent;
  shipping = parseFloat(shipping);
  //console.log("shipping"+shipping);
  let tax = document.getElementById("tax").textContent;
  tax = parseFloat(tax);
  //console.log("tax"+tax);
  total = total + shipping + tax;
  total = Math.round(total*100)/100;

  let payment = document.getElementById("payment");
  payment.textContent = total;
}


//orer total update when quantity of item changes
let quantityArray = document.getElementsByName("quantity");
for (let i = 0; i< quantityArray.length; i++){
  quantityArray[i].addEventListener("change", (event)=>{getOrderTotal();});
}

//delete the item from the page when delete is clicked
let deleteArray = document.querySelectorAll(".delete");
for (let i = 0; i< deleteArray.length; i++){
  deleteArray[i].addEventListener("click", (event)=>{
    deleteItem(event);
    getOrderTotal();
  });

}

function deleteItem(event){
  let itemDeleted = event.target.parentElement.parentElement;

  itemDeleted.remove();
}
