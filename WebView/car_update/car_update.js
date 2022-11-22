import {getBackendUrl} from "../js/configuration.js";

window.addEventListener('load', ()=>{
    const carForm = document.getElementById('carForm');
    carForm.addEventListener('submit', event => editCar(event));

    fetchAndDisplaySingleCar();
})

function fetchAndDisplaySingleCar(){
    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displaySingleCar(JSON.parse(this.responseText));
        }
    };

    const urlParams = new URLSearchParams(window.location.search);
    xhttp.open("GET", getBackendUrl() + "/api/cars/" + urlParams.get('car'), true);
    xhttp.send();
}

function displaySingleCar(car){
    document.getElementById('carVinHeader').innerText = car.vin;
    document.getElementById('color').value = car.color;
}

function editCar(event){
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 201){
            fetchAndDisplaySingleCar();
        }
    };

    const urlParams = new URLSearchParams(window.location.search);

    xhttp.open("PUT", getBackendUrl() + '/api/cars/' + urlParams.get('car'), true);

    const request = {
        'vin': urlParams.get('car'),
        'color': document.getElementById('color').value
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(request));
}