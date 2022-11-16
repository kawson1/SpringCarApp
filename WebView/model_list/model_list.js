import {getBackendUrl} from "../js/configuration.js";
import {clearElementChildren, createTextCell, createButtonCell} from "../js/dom_utils.js"

window.addEventListener('load', () => {
    const modelForm = document.getElementById('modelForm');
    modelForm.addEventListener('submit', event => createModel(event));

    fetchAndDisplayModels();
})

/**
 * Gets all models and then modify DOM tree to display them.
 */
function fetchAndDisplayModels() {
    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayModels(JSON.parse(this.responseText));
        }
    };

    xhttp.open("GET", getBackendUrl() + "/api/models", true);
    xhttp.send();
}

/**
 * Updates the DOM tree to display models
 *
 * @param ({models: string[]} all available models
 */
function displayModels(models) {
    let tableBody = document.getElementById('tableBody');
    clearElementChildren(tableBody);
    models.models.forEach(model => {
        tableBody.appendChild(createTableRow(model));
    })
}

/**
 * Creates row in table - returns tr element
 * @param model {string} - name of model
 */
//TODO: create function which will create fields in row table.
function createTableRow(model) {
    let tr = document.createElement('tr');
    tr.appendChild(createTextCell(model));
    tr.appendChild(createButtonCell('delete', () => deleteModel(model)));
    return tr;
}

function deleteModel(model){
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 202){
            fetchAndDisplayModels();
        }
    };

    xhttp.open("DELETE", getBackendUrl() + '/api/models/' + model, true);
    xhttp.send();
}

function createModel(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 201){
            fetchAndDisplayModels();
        }
    };

    xhttp.open("POST", getBackendUrl() + '/api/models/', true);

    const request = {
        'name': document.getElementById('modelName').value,
        'hp': document.getElementById('horsePower').value
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(request));
}