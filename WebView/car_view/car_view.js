import {getBackendUrl} from "../js/configuration.js";
import {clearElementChildren, createButtonCell, createTextCell} from "../js/dom_utils.js";

window.addEventListener('load', ()=>{
    fetchAndDisplayCarDetails();
})

function fetchAndDisplayCarDetails(){
    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayCarDetails(JSON.parse(this.responseText));
        }
    };

    const urlParams = new URLSearchParams(window.location.search);
    xhttp.open("GET", getBackendUrl() + "/api/cars/" + urlParams.get('car'), true);
    xhttp.send();
}

function displayCarDetails(car){
    const urlParams = new URLSearchParams(window.location.search);
    document.getElementById('vin').innerText += car.vin;
    document.getElementById('color').innerText += car.color;
    document.getElementById('model').innerText += urlParams.get('model');
}