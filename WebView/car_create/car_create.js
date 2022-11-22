import {getBackendUrl} from "../js/configuration.js";

window.addEventListener('load', () => {
    const carForm = document.getElementById('carForm');
    carForm.addEventListener('submit', event => createCar(event))
})

function createCar(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 201) {
            const urlParams = new URLSearchParams(window.location.search);
            document.location.href = '../model_view/model_view.html?model=' + urlParams.get('model');
        }
    }

    const urlParams = new URLSearchParams(window.location.search);
    const request = {
        'vin': document.getElementById('VIN').value,
        'color': document.getElementById('color').value,
        'model': urlParams.get('model')
    }
    xhttp.open('POST', getBackendUrl() + '/api/cars/');
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(request));
}