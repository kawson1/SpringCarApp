import {getBackendUrl} from "../js/configuration.js";
import {clearElementChildren, createButtonCell} from "../js/dom_utils.js";

window.addEventListener('load', ()=>{
    fetchAndDisplayModelDetails();
    fetchAndDisplayCars();
})

function fetchAndDisplayModelDetails(){
    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayModelDetails(JSON.parse(this.responseText));
        }
    };

    const urlParams = new URLSearchParams(window.location.search);
    xhttp.open("GET", getBackendUrl() + "/api/models/" + urlParams.get('model'), true);
    xhttp.send();
}

function displayModelDetails(model){
    document.getElementById('modelName').innerText += model.name;
    document.getElementById('modelHp').innerText += model.hp;
}

function fetchAndDisplayCars(){
    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayCars(JSON.parse(this.responseText));
        }
    };

    const urlParams = new URLSearchParams(window.location.search);
    xhttp.open("GET", getBackendUrl() + "/api/models/" + urlParams.get('model') + '/cars', true);
    xhttp.send();
}

function displayCars(cars){
    const ul = document.getElementById('carList');
    clearElementChildren(ul)
    cars.cars.forEach(VIN => {
        ul.appendChild(createListElement(VIN));
    })
}

function createListElement(car){
    const li = document.createElement('li');
    li.appendChild(document.createTextNode(car));
    li.appendChild(createButtonCell('Delete', () => deleteCar(car)));
    return li;
}

function deleteCar(car){
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 202){
            fetchAndDisplayCars();
        }
    };

    xhttp.open("DELETE", getBackendUrl() + '/api/cars/' + car, true);
    xhttp.send();
}