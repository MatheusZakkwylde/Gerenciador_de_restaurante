package controllers;

import Enums.Cargo;
import java.io.File;
import java.util.List;
import models.Fornecedor;
import models.Produto;
import models.Usuario;
import play.Play;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Cadastros extends Controller {

    public static void usuario(Usuario usuarios) {
        Cargo [] cargos = Cargo.values();
        render(usuarios, cargos);
    }

    public static void salvarUsuario(Usuario usuarios) {

        if (usuarios.senha.equals(usuarios.confSenha)) {
            usuarios.save();
            tabelaUsuario();
        } else {
            flash.error("As senhas digitadas são imcompativeis");
            usuarios = null;
            usuario(usuarios);
        }
    }

    public static void tabelaUsuario() {

        List<Usuario> usuarios = Usuario.findAll();
        render(usuarios);
    }

    public static void editarUsuario(Long id) {
        Usuario usuarios = Usuario.findById(id);
        Cargo [] cargos = Cargo.values();
        renderTemplate("Cadastros/usuario.html", usuarios, cargos);
    }

    public static void removerUsuario(Long id) {
        Usuario usuarios = Usuario.findById(id);
        usuarios.delete();
        flash.success("Funcionario removido com sucesso!");
        tabelaUsuario();
    }

    public static void buscaUsuario(String busca) {
        List<Usuario> usuario = Usuario.find("nome like ? or email like ? or cidade like ? ", "%" + busca + "%", "%" + busca + "%", "%" + busca + "%").fetch();
        renderTemplate("Cadastros/tabelaUsuario.html", usuario);
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
            editarUsuario(id);
    }
    //---------------------------------------------------------

    public static void fornecedor(Fornecedor fornecedor) {
        render(fornecedor);
    }

    public static void salvaFornecedor(Fornecedor fornecedor) {
        fornecedor.save();
        flash.success("Fornecedor cadastrado  com sucesso!");
        tabelaFornecedor();
    }

    public static void tabelaFornecedor() {
        List<Fornecedor> fornecedores = Fornecedor.findAll();
        render(fornecedores);
    }

    public static void editarFornecedor(Long id) {
        Fornecedor fornecedor = Fornecedor.findById(id);
        renderTemplate("Cadastros/fornecedor.html", fornecedor);
    }

    public static void removerFornecedor(Long id) {
        Fornecedor fornecedor = Fornecedor.findById(id);
        fornecedor.delete();
        flash.success("Fornecedor removido  com sucesso!");
        tabelaFornecedor();
    }

    public static void buscarFornecedor(String busca) {
        List<Fornecedor> fornecedores = Fornecedor.find("Empresa like ? or nome like ? or cidade like ?", "%" + busca + "%", "%" + busca + "%", "%" + busca + "%").fetch();
        renderTemplate("Cadastros/tabelaFornecedor.html", fornecedores);
    }
//---------------------------------------------------------

    public static void produto(Produto produto) {
        List<Fornecedor> fornecedor = Fornecedor.findAll();
       
        if (fornecedor.size() == 0) {

            flash.error("Não existe Fornecedor Cadastrado!");
            Home.home();
        } else {
            render(produto, fornecedor);
        }
    }

    public static void salvarProduto(Produto produto) {
        produto.save();
        params.flash();
        flash.success("Produto cadastrado  com sucesso!");
        tabelaProduto();
    }

    public static void tabelaProduto() {
        List<Produto> produtos = Produto.findAll();
        render(produtos);
    }

    public static void buscarProduto(String busca) {
        List<Produto> produto = Produto.find("produto like ? ", "%" + busca + "%").fetch();
        flash.success("Click em busca para vizualizar todos os pedidos novamente!");
        renderTemplate("Cadastros/tabelaProduto.html", produto);
    }

    public static void EditarProduto(Long id) {
        List<Fornecedor> fornecedor = Fornecedor.findAll();
        Produto produto = Produto.findById(id);
        renderTemplate("Cadastros/produto.html", produto, fornecedor);
    }

    public static void RemoverProduto(Long id) {
        Produto produto = Produto.findById(id);
        produto.delete();
        flash.success("Produto removido com sucesso!");
        tabelaProduto();
    }
}
