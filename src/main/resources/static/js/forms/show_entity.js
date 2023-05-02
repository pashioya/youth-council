const description_input = document.getElementById("description_input");
description_input.style.display = "none";
const show_button = document.getElementById("show_description");
show_button.addEventListener("click", showInput);
function showInput(){
    description_input.style.display = "block";
}

const start = document.getElementById("start");
start.style.display = "none";
const end = document.getElementById("end");
end.style.display = "none";
function showTime(){
    start.style.display = "block";
    end.style.display = "block";
}

const selectTheme = document.getElementById("select_theme");
selectTheme.style.display = "none";
const showButton = document.getElementById("showTheme");
showButton.addEventListener("click", showTheme)
function showTheme(){
    selectTheme.style.display = "block";
}