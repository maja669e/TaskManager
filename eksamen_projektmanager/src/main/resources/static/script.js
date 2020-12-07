document.addEventListener("DOMContentLoaded", burgerMenu);
var burger = document.getElementById("myTopnav");
// Get the modal
var modal = document.getElementById('id01');
var modal = document.getElementById('id02');
var modal = document.getElementById('id03');
var modal = document.getElementById('id04');


function burgerMenu() {
    if (burger.className === "topnav") {
        burger.className += " responsive";
    } else {
        burger.className = "topnav";
    }
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function close(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

