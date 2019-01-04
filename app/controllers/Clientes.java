package controllers;

import static controllers.Cardapios.EditarPrato;
import java.io.File;
import java.util.List;
import models.Comanda;
import models.Pedido;
import models.Prato;
import play.Play;
import play.mvc.Controller;

public class Clientes extends Controller {

    public static void cliente() {
        render();
    }

    public static void autenticacao(String senha) {
        /*verifica se existe dados*/
        Comanda comandas = Comanda.find("senha = ?", senha).first();

        if (comandas == null) {

            flash.error("Senha incorreta!");
            params.flash();
            cliente();
        } else {
            session.put("senhaCliente", senha);
            comanda(senha);
        }
    }

    public static void comanda(String busca) {
        List<Comanda> comandas = Comanda.find("senha like ? ", "%" + busca + "%").fetch();
        renderTemplate("Clientes/comanda.html", comandas);
    }
    
    public static void comandaCadapio() {
        List<Comanda> comandas = Comanda.find("senha like ? ", "%" + session.get("senhaCliente") + "%").fetch();
        List<Pedido> pedidos = Pedido.findAll();
        renderTemplate("Clientes/comanda.html", comandas, pedidos);
    }
    
      public static void logout() {
        session.remove("senhaCliente");
        cliente();
    }
}
