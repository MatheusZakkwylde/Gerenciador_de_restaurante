package controllers;

import Enums.Condicao;
import Enums.StatusPedido;
import Enums.TipoPrato;
import java.util.List;
import models.Comanda;
import models.Pedido;
import models.Prato;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Comandas extends Controller {

    public static void comanda(Comanda comanda, Pedido pedido) {
        //Lista de comandas para serem listadas nas comandas cadastradas dos clientes
        List<Comanda> comandas = Comanda.findAll();
        //tipo de prato para ser selecionado quando o garçom for realizar um pedido
        TipoPrato[] tipos = TipoPrato.values();
        //lista de pratos cadastrados no cardapio para serem selecionados no pedido
        List<Prato> pratos = Prato.findAll();
        //status do pedido
        StatusPedido[] pedido_status = StatusPedido.values();

        render(comanda, pedido, tipos, pratos, pedido_status, comandas);
    }

    public static void salvarComanda(Comanda comanda) {

        List<Comanda> Comandas = Comanda.findAll();

        // Se o garçom deixa a comanda como zero
        if (comanda.numero == 0) {
            flash.error("O numero zero não é um numero valido!");
            comanda(comanda, null);
        }

        //verifica se a comanda já existe
        for (int i = 0; i < Comandas.size(); i++) {
            if (comanda.numero == Comandas.get(i).numero && Comandas.get(i).status == 1) {
                flash.error("Numero de comanda existente!");
                comanda(comanda, null);
            }
        }

        comanda.status = 1;
        comanda.save();
        flash.success("Comanda Criada com Sucesso");
        comanda(null, null);
    }

    public static void excluirComanda(Long id) {
        Comanda comanda = Comanda.findById(id);

        List<Pedido> pedidos = Pedido.findAll();
        //Se o numero do pedido for igual o da comanda delete todos aqueles pedidos
        for (int i = 0; i < pedidos.size(); i++) {
            if (comanda.numero == pedidos.get(i).numero && comanda.status == 1) {
                pedidos.get(i).delete();
            }
        }
        comanda.delete();
        flash.success("Comanda Excluida com Sucesso!");
        comanda(null, null);
    }

    public static void editarComanda(Long id) {
        Comanda comanda = Comanda.findById(id);
        renderTemplate("Comandas/editarComanda.html", comanda);
    }

    public static void salvarComandaEditada(Comanda comanda) {
        comanda.save();
        flash.success("Comanda editada com sucesso!");
        comanda(null, null);
    }

    public static void salvarPedido(Pedido pedido) {
        List<Prato> Pratos = Prato.findAll();
        List<Comanda> Comandas = Comanda.findAll();

        /* Verifica se o garçom deixou o campo numero como zero */
        if (pedido.numero == 0) {
            flash.error("O campo numero comanda não pode ser zero!");
            comanda(null, pedido);
        }

        for (int a = 0; a < Comandas.size(); a++) {

            if (pedido.numero == Comandas.get(a).numero && Comandas.get(a).status == 1) {
                break;
            } else {
                if (a == Comandas.size() - 1 && Comandas.get(a).status != 1) {
                    flash.error("O numero da comanda não existe!");
                    comanda(null, pedido);
                }
            }
        }

        //verifica se o garçom deixou a quantidade de pedido zerado
        if (pedido.quantidade == 0) {
            flash.error("Insira pelo menos uma quantidade do Pedido a ser Realizado!");
            comanda(null, pedido);
        }

        //se o prato vinhe nulo
        if (pedido.Pedido == null) {
            flash.error("Insira algum Prato!");
            comanda(null, pedido);
        }

        for (int j = 0; j < Pratos.size(); j++) {
            //se o tipo de pedido for igual ao do prato cadastrado no cardapio e seu nome
            if (pedido.tipo.equals(Pratos.get(j).tipo) && pedido.Pedido.nome.equals(Pratos.get(j).nome)) {

                //se aquantidade de pedidos for maior do que a quantidade de pratos cadastrados
                if (pedido.quantidade > Pratos.get(j).quantidade) {
                    flash.error("Temos Apenas " + Pratos.get(j).quantidade + " pratos " + pedido.Pedido.nome + " cadastrados");
                    comanda(null, pedido);
                }

                /*passa o preco de pedidos cadastrados para o pedido do cliente*/
                pedido.preco = pedido.quantidade * Pratos.get(j).preco;

                //pega a comanda que tem o mesmo id do pedido
                Comanda comanda = Comanda.find("numero = ? and status = ?", pedido.numero, 1).first();
                comanda.total = comanda.total + pedido.preco;
                comanda.save();

                //pega esse pedido e relaciona com a comanda
                pedido.comanda = comanda;
                pedido.condicao = Condicao.ADICIONADO;
                pedido.save();

                // decrementando a quantidade de pedido da aquele prato
                Pratos.get(j).quantidade = Pratos.get(j).quantidade - pedido.quantidade;
                Pratos.get(j).save();

                //se quantidade da aquele prato é igual a zero ele acabou
                if (Pratos.get(j).quantidade == 0) {
                    flash.success("O pedido foi cadastrado com Sucesso .O Prato " + Pratos.get(j).nome + " Acabou!");
                    Pratos.get(j).quantidade = 0;
                    Pratos.get(j).save();
                } else {
                    flash.success("O pedido foi cadastrado com Sucesso. Restam apenas " + Pratos.get(j).quantidade + " Pratos de  " + Pratos.get(j).nome);
                }
                break;
            }

            if (pedido.tipo != Pratos.get(j).tipo && pedido.Pedido.id != Pratos.get(j).id) {
                flash.error("O prato do tipo " + pedido.tipo + " de " + pedido.Pedido.nome + " não existe!");
            }
        }

        comanda(null, null);
    }

    public static void RemoverPedido(Long id) {
        Pedido pedido = Pedido.findById(id);
        Comanda comanda = Comanda.find("numero = ?", pedido.numero).first();
        comanda.total = comanda.total - pedido.preco;

        comanda.save();
        pedido.delete();

        flash.success("Pedido removido  com sucesso!");
        comanda(null, null);
    }

    public static void editarPedido(Long id) {

        Pedido pedido = Pedido.findById(id);
        TipoPrato[] tipos = TipoPrato.values();
        List<Prato> pratos = Prato.findAll();
        StatusPedido[] pedido_status = StatusPedido.values();

        renderTemplate("Comandas/editarPedido.html", pedido, tipos, pratos, pedido_status);
    }

    public static void salvarPedidoEditado(Pedido pedido) {
        
        List<Prato> Pratos = Prato.findAll();

        for (int j = 0; j < Pratos.size(); j++) {

            //se o tipo de pedido for igual ao do prato cadastrado no cardapio e seu nome
            if (pedido.tipo.equals(Pratos.get(j).tipo) && pedido.Pedido.nome.equals(Pratos.get(j).nome)) {
                
                //se aquantidade de pedidos for maior do que a quantidade de pratos cadastrados
                if (pedido.quantidade > Pratos.get(j).quantidade) {
                    flash.error("Temos Apenas " + Pratos.get(j).quantidade + " pratos " + pedido.Pedido.nome + " cadastrados");
                    editarPedido(pedido.id);
                }
                
                //verifica a comanda correta e diminuir o valor anterior do pedido para que possa ser somanda nova-
                //mente com a nova quantidade de pedidos
                Comanda comanda = Comanda.find("numero = ?", pedido.numero).first();
                comanda.total = comanda.total - pedido.preco;
                comanda.save();

                /*passa o preco de pedidos cadastrados para o pedido do cliente*/
                pedido.preco = pedido.quantidade * Pratos.get(j).preco;
                pedido.save();
                
                comanda.total = comanda.total + pedido.preco;
                comanda.save();
                break;
            }
        }

        comanda(null, null);
    }
}
