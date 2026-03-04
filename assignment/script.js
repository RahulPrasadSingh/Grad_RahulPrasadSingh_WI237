function selectInterest(type){
    let interstSeclect=document.getElementById("interest")
     if(type==="HOME"){
        interstSeclect.value="9%";

     }
     else if(type==="CAR"){
        interstSeclect.value="12%";
        
     }
     else if(type==="PERSONAL"){
        interstSeclect.value="15%";
        
     }
}

function setLimits(type){
   let amount=document.getElementById("amt");
   let duration=document.getElementById("dur");

   if(type === "HOME"){
        amount.min = 500000;          
        duration.max = 30;
    }

else if(type ==="CAR"){
    amount.min=100000;
    duration.max=7;
}
else if(type==="PERSONAL"){
    amount.min=10000;
    duration.max=5;
}
}

// function alertMsg(){

//     let type = document.getElementById("type").value;

//     let amountInput = document.getElementById("amt");
//     let durationInput = document.getElementById("dur");

//     let amt = Number(amountInput.value);
//     let dur = Number(durationInput.value);

//     if(type === "HOME"){

//         if(amt < 500000){
//             alert("Enter Amount greater than 5 Lakh");
//         }

//         if(dur > 30){
//             alert("Duration cannot be more than 30 years");
//         }
//     }

//     else if(type === "CAR"){

//         if(amt < 100000){
//             alert("Enter Amount greater than 1 Lakh");
//         }

//         if(dur > 7){
//             alert("Duration cannot be more than 7 years");
//         }
//     }

//     else if(type === "PERSONAL"){

//         if(amt < 10000){
//             alert("Enter Amount greater than 10,000");
//         }

//         if(dur > 5){
//             alert("Duration cannot be more than 5 years");
//         }
//     }
// }
function calculateEMI(event){

    event.preventDefault(); // prevent page reload

    let amount = Number(document.getElementById("amt").value);
    let duration = Number(document.getElementById("dur").value);
    let interest = document.getElementById("interest").value;

    // remove % symbol
    interest = Number(interest.replace("%",""));

    let monthlyRate = interest / 12 / 100;
    let totalMonths = duration * 12;

    let emi = (amount * monthlyRate * Math.pow(1 + monthlyRate, totalMonths)) /
              (Math.pow(1 + monthlyRate, totalMonths) - 1);

    document.getElementById("emi_amt").innerHTML = 
        "₹ " + emi.toFixed(2);
}