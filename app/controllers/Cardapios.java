package controllers;

import Enums.TipoPrato;
import java.io.File;
import java.util.List;
import models.Prato;
import play.Play;

import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Cardapios extends Controller {

    public static void cardapio(Prato prato) {
        TipoPrato[] tipos = TipoPrato.values();
        render(prato, tipos);
    }

    public static void salvarPrato(Prato prato) {

        if (prato.tipo.equals(TipoPrato.Almoço)) {
            prato.idTipo = 1;
        }
        if (prato.tipo.equals(TipoPrato.Petisco)) {
            prato.idTipo = 2;
        }
        if (prato.tipo.equals(TipoPrato.Bebidas)) {
            prato.idTipo = 3;
        }
        if (prato.tipo.equals(TipoPrato.Sobremesas)) {
            prato.idTipo = 4;
        }
        prato.save();
        flash.success("Pedido cadastrado  com sucesso!");
        tabelaCardapio();
    }

    public static void tabelaCardapio() {
        List<Prato> pratos = Prato.findAll();
        render(pratos);
    }
    
     public static void cardapioCasa() {
        List<Prato> pratos = Prato.findAll();
        render(pratos);
    }

    public static void EditarPrato(Long id) {
        Prato prato = Prato.findById(id);
        TipoPrato[] tipos = TipoPrato.values();
        renderTemplate("Cardapios/cardapio.html", prato, tipos);
    }

    public static void buscarPratos(String busca) {
        List<Prato> prato = Prato.find("pedido like ? ", "%" + busca + "%").fetch();
        renderTemplate("Cardapio/ListaCardapio.html", prato);
    }

    public static void RemoverPratos(Long id) {
        Prato prato = Prato.findById(id);
        flash.success("Produto removido  com sucesso!");
        prato.delete();
        tabelaCardapio();
    }

    public static void fotoPrato(Long id) {
        Prato prato = Prato.findById(id);
        notFoundIfNull(prato);
        response.setContentTypeIfNotSet(prato.foto.type());
        renderBinary(prato.foto.get());
    }

    public static void fotoPadrao() {
        File file = Play.getFile("/public/images/prato.jpg");
        renderBinary(file);
    }

    public static void removerFoto(Long id) {
        Prato prato = Prato.findById(id);

        if (prato.foto.exists()) {
            prato.foto.getFile().delete();
        }
        EditarPrato(id);
    }

    public static void cardapioCliente() {
        List<Prato> pratos = Prato.findAll();
        render(pratos);
    }
}
