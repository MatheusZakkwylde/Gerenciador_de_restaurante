
    function multiplica() {
        var Resultado = getValor('quantidade') * getValor('precoUnitario');
        
        id('pagarFornecedor').value = Resultado;
    }

    function getValor(valorPassado) {
        var valor = document.getElementById(valorPassado).value;
        return parseFloat(valor);
    }

    function id(Resultado) {
        return document.getElementById(Resultado);
    }
