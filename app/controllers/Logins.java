package controllers;

import models.Adiministrador;
import models.Usuario;
import play.mvc.Controller;

public class Logins extends Controller {

    public static void login() {
        render();
    }

    public static void autenticacao(String email, String senha) {
        /*verifica se existe dados*/
        Adiministrador adiministrador = Adiministrador.find("email = ? and senha = ?", email, senha).first();
        Usuario usuario = Usuario.find("email = ? and senha = ?", email, senha).first();

        if (adiministrador == null && usuario == null) {

            flash.error("Usuário ou senha inválidos!");
            params.flash();

            login();
        } else {
            
            if(adiministrador !=null){
                session.put("adiministrador",adiministrador.email);
                session.put("Usuario",adiministrador.nome);
            }
            
            
            if (usuario != null) {
                session.put("Usuario", usuario.nome);
                session.put("UserImail",usuario.email);
            }
            Home.home();
        }
    }

    public static void logout() {
        session.clear();
        Start.start();
    }

}
