import {getBackendUrl} from "../js/configuration.js";
import {clearElementChildren} from "../js/dom_utils.js"

/**
 * Gets all models and then modify DOM tree to display them.
 */
function fetchAndDisplayModels() {
    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let object = JSON.parse(this.responseText);
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
    models.forEach(model => {
        tableBody.appendChild(createTableRow(model));
    })
}

//TODO: create function which will create fields in row table.
function createTableRow(model) {
     let row = document.createElement('tr');
     tr.appendChild();
}