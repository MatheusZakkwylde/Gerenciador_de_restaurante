package controllers;

import play.mvc.Before;
import play.mvc.Controller;

public class Seguranca extends Controller {
    //@Before indica que estou chamando esse metodo primeiro que verifica se estou logado ou não.
    @Before
    public static void autenticar() {
        if (session.get("Usuario") == null && session.get("adiministrador") == null) {
            flash.error("Entre com seu imail e login");
            Logins.login();
        }
    }
}
