function uploadimg() {
    var oFReader = new FileReader();
    oFReader.readAsDataURL(document.getElementById("upload").files[0]);

    oFReader.onload = function (oFREvent) {
        document.getElementById("img").src = oFREvent.target.result;
    };
}
;

function excluir(valorClick) {

    if (valorClick === 1) {
        document.getElementById("img").src = "/public/images/user.png";
        document.getElementById("upload").value = null;
    }
}

function excluirFoto(valorClick) {

    if (valorClick === 1) {
        document.getElementById("img").src = "/public/images/prato.jpg";
        document.getElementById("upload").value = null;
    }
}

