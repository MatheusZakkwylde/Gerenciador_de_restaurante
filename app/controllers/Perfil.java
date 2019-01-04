package controllers;

import Enums.Cargo;
import java.io.File;
import models.Usuario;
import play.Play;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)

public class Perfil extends Controller {

    public static void perfil() {
        Usuario usuario = Usuario.find("email = ?", session.get("UserImail")).first();
        render(usuario);
    }

    public void editar(Long id) {
        Usuario usuario = Usuario.findById(id);
        Cargo[] cargos = Cargo.values();
        renderTemplate("Perfil/EditarPerfil.html", usuario, cargos);
    }

    public static void salvar(Usuario usuario) {
            usuario.save();
             perfil();
    }

    public static void excluir(Long id) {
        Usuario usuarios = Usuario.findById(id);
        usuarios.delete();
        flash.success("Seu Perfil foi excluido com sucesso! Para acessar novamente faça um novo cadastro.");
        Logins.login();
    }

    public static void foto(Long id) {
        Usuario usuarios = Usuario.findById(id);
        notFoundIfNull(usuarios);
        response.setContentTypeIfNotSet(usuarios.foto.type());
        renderBinary(usuarios.foto.get());
    }

    public static void fotoPadrao() {
        File file = Play.getFile("/public/images/user.png");
        renderBinary(file);
    }

    public static void removerFoto(Long id) {
        Usuario usuario = Usuario.findById(id);
        usuario.foto.getFile().delete();
    }
}
