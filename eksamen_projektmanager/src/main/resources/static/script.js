document.addEventListener("DOMContentLoaded", burgerMenu);
var burger = document.getElementById("myTopnav");


function burgerMenu() {
    if (burger.className === "topnav") {
        burger.className += " responsive";
    } else {
        burger.className = "topnav";
    }
}

