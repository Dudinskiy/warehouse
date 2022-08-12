

function getProductList() {
    let selectBox = document.getElementById("producers");
    let producerName = selectBox.options[selectBox.selectedIndex].value;

    let xhttp = new XMLHttpRequest();

    xhttp.onload = function () {
        if (xhttp.status == 200) {
            let producersFullDtoResList = JSON.parse(xhttp.response);
            let html = '<option value="">Товар не выбран</option>';
            console.log(producersFullDtoResList.length);
            for (let i = 0; i < producersFullDtoResList.length; i++) {
                html = html + '<option value="' + producersFullDtoResList[i].productName + '">'
                    + producersFullDtoResList[i].productName + '</option>'
            }
            document.getElementById("products").innerHTML = html;
        }
        return false;
    }
    xhttp.open("POST", "http://localhost:8182/warehouse/rest/product/get-by-producer", true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send(JSON.stringify({
        productId: 0, productName: '', producerName: producerName
        , countryName: '', price: 0, productAmount: 0
    }));
}

let elem1 = document.getElementById("producers");
elem1.addEventListener('change', getProductList);