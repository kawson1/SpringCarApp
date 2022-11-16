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
