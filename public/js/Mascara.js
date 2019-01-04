   $(document).ready(function () {
        $("#cpf").mask("000.000.000-00")

        $("#telefone").mask("(00) 0000-0000")
        
        $("#salario").mask("00.990.999.999")

        $("#dataNascimento").mask("00/00/0000")

        $("#celular").mask("(00) 0000-00009")

        $("#celular").blur(function (event) {
            if ($(this).val().length === 15) {
                $("#celular").mask("(00) 00000-0009")
            } else {
                $("#celular").mask("(00) 0000-00009")
            }
        })
    })


