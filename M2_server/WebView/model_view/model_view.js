import {getBackendUrl} from "../js/configuration.js";
import {clearElementChildren, createButtonCell, createTextCell} from "../js/dom_utils.js";

window.addEventListener('load', ()=>{
    const urlParams = new URLSearchParams(window.location.search);
    const addCarButton = document.getElementById('addCarButton');
    addCarButton.addEventListener('click',
        () => document.location.href = '../car_create/car_create.html?model=' + urlParams.get('model'));
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
    const table = document.getElementById('carsTable');
    clearElementChildren(table);
    cars.cars.forEach(VIN => {
        table.appendChild(createTableRow(VIN));
    })
}

function createTableRow(car) {
    const urlParams = new URLSearchParams(window.location.search);

    let tr = document.createElement('tr');
    tr.appendChild(createTextCell(car));
    tr.appendChild(createButtonCell('delete', () => deleteCar(car)));
    tr.appendChild(createButtonCell('edit', () => document.location.href = '../car_update/car_update.html?car='+car));
    tr.appendChild(createButtonCell('view', () => document.location.href = '../car_view/car_view.html?model='+urlParams.get('model')+'&car='+car));
    return tr;
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