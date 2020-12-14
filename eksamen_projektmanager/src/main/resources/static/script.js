document.addEventListener("DOMContentLoaded", burgerMenu);
var burger = document.getElementById("myTopnav");

function burgerMenu() {
    if (burger.className === "topnav") {
        burger.className += " responsive";
    } else {
        burger.className = "topnav";
    }
}

function subProjectAdd() {
    alert("Dit nye delprojekt er nu tilføjet. \r\nTryk OK for at opdatere.");
}

function taskAdd() {
    alert("Din nye opgave er nu tilføjet. \r\nTryk OK for at opdatere.");
}

var x = document.getElementById("subtext").value;

function subProjectDelete() {
    alert("Delprojekt " + x + " er nu slettet. \r\nTryk OK for at opdatere.");
}