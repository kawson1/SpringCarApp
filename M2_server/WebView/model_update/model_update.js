import {getBackendUrl} from "../js/configuration.js";

window.addEventListener('load', ()=>{
    const modelForm = document.getElementById('modelForm');
    modelForm.addEventListener('submit', event => editModel(event));

    fetchAndDisplaySingleModel();
})

function fetchAndDisplaySingleModel(){
    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displaySingleModel(JSON.parse(this.responseText));
        }
    };

    const urlParams = new URLSearchParams(window.location.search);
    xhttp.open("GET", getBackendUrl() + "/api/models/" + urlParams.get('model'), true);
    xhttp.send();
}

function displaySingleModel(model){
    document.getElementById('modelNameHeader').innerText = model.name;
    document.getElementById('horsePower').value = model.hp;
}

function editModel(event){
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 201){
            fetchAndDisplaySingleModel();
        }
    };

    const urlParams = new URLSearchParams(window.location.search);

    xhttp.open("PUT", getBackendUrl() + '/api/models/' + urlParams.get('model'), true);

    const request = {
        'name': urlParams.get('model'),
        'hp': document.getElementById('horsePower').value
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(request));
}