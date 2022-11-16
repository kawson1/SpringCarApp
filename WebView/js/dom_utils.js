/**
 * Clears all children of the provided element
 *
 * @param {HTMLElement} element element which we want to clear
 */
export function clearElementChildren(element) {
    while (element.firstChild) {
        element.removeChild(element.firstChild);
    }
}

/**
 * Creates cell in HTML table - return td element
 * @param text - text which will be in cell
 * @returns {HTMLTableCellElement} - td element
 */
export function createTextCell(text) {
    const td = document.createElement('td');
    td.appendChild(document.createTextNode(text));
    return td;
}

export function createButtonCell(text, action) {
    const td = document.createElement('td');
    const button = document.createElement('button');
    button.appendChild(document.createTextNode(text));
    button.addEventListener('click', action);
    td.appendChild(button);
    return td;
}


