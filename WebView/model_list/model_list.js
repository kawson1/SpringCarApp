let xhttp = new XMLHttpRequest();

xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        let object = JSON.parse(this.responseText);
    }
};

xhttp.open("GET", "http://localhost:8080/api/models", async=true);
xhttp.send();