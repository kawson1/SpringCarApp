import {getBackendUrl} from "../js/configuration.js";
import {clearElementChildren, createButtonCell, createTextCell} from "../js/dom_utils.js";

window.addEventListener('load', ()=>{
    const carImageForm = document.getElementById('carImageForm');
    carImageForm.addEventListener('submit', event=>uploadCarImageAction(event));

    const carImageDownload = document.getElementById('carImageDownload');
    carImageDownload.addEventListener('click', event=>downloadCarImageAction(event));

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

function uploadCarImageAction(event){
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    const urlParams = new URLSearchParams(window.location.search);

    xhttp.open("PUT", getBackendUrl() + '/api/cars/' + urlParams.get('car') + '/upload', true);

    let request = new FormData();
    request.append("image", document.getElementById('car_image').files[0]);
    request.append("author", document.getElementById('author').value);
    request.append("description", document.getElementById('description').value);

    xhttp.send(request);
}

function downloadCarImageAction(event){
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    const urlParams = new URLSearchParams(window.location.search);

    xhttp.open("GET", getBackendUrl() + '/api/cars/' + urlParams.get('car') + '/download', true);
    xhttp.send();
}