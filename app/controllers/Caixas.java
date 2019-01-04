package controllers;

import Enums.StatusCaixa;
import java.util.List;
import models.Caixa;
import models.Comanda;
import models.Pedido;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Caixas extends Controller {

    public static void abrirCaixa(Long id) {

        Caixa caixa = Caixa.findById(id);

        if (caixa.status == StatusCaixa.INATIVO) {
            render(caixa);
        } else {
            caixa();
        }
    }

    public static void fechaCaixa(Long id) {
        Caixa caixa = Caixa.findById(id);
        caixa.status = StatusCaixa.INATIVO;
        caixa.save();
        abrirCaixa(id);
    }

    public static void salvarValorCaixa(Long id, Caixa caixa) {
        Caixa bdCaixa = Caixa.findById(id);
        bdCaixa.total = caixa.total;
        bdCaixa.data = caixa.data;
        bdCaixa.status = StatusCaixa.ATIVO;
        bdCaixa.save();
        caixa();
    }

    public static void caixa() {
        render();
    }

    public static void saldo(Caixa caixa) {
        List<Caixa> bdcaixa = Caixa.findAll();
        caixa = bdcaixa.get(0);
        render(caixa);
    }

    public static void comandas() {
        List<Comanda> comandas = Comanda.findAll();
        List<Pedido> pedidos = Pedido.findAll();
        render(comandas, pedidos);
    }

    public static void buscaComandas(String busca) {
        List<Pedido> pedidos = Pedido.findAll();
        List<Comanda> comandas = Comanda.find("cliente like ? ", "%" + busca + "%").fetch();
        renderTemplate("Caixas/comandas.html", comandas);
    }

    public static void Desconto(Long id) {
        Comanda comanda = Comanda.findById(id);
        render(comanda);
    }

    public static void DescontoPedido(float desconto, Long id) {
        Comanda comanda = Comanda.findById(id);
        if (comanda.total > 0 && desconto <= comanda.total) {
            comanda.total = comanda.total - desconto;
            comanda.save();

            flash.success("Desconto Realizado com sucesso!");

        } else {
            flash.error("O valor da comanda é zero ou o valor de desconto é maior que o valor da comanda");
        }
        comandas();
    }

    public static void empenhadas() {
        List<Comanda> comandas = Comanda.findAll();
        render(comandas);
    }

    public static void empenhaComanda(Long id) {
        Comanda comanda = Comanda.findById(id);
        comanda.status = 2;
        comanda.save();
        comandas();
    }

    public static void inativas() {
        List<Comanda> comandas = Comanda.findAll();
        render(comandas);
    }

    public static void inativasComandas(Long id,Long idcaixa) {
        Comanda comanda = Comanda.findById(id);
        Caixa caixa = Caixa.findById(idcaixa);
        caixa.total = caixa.total + comanda.total;
        caixa.save();
        comanda.status = 3;
        comanda.save();
        comandas();
    }

    public static void excluirComanda(Long id) {
        Comanda comanda = Comanda.findById(id);

        List<Pedido> pedidos = Pedido.findAll();
        //Se o numero do pedido for igual o da comanda delete todos aqueles pedidos
        for (int i = 0; i < pedidos.size(); i++) {
            if (comanda.numero == pedidos.get(i).numero) {
                pedidos.get(i).delete();
            }
        }
        comanda.delete();
        flash.success("Comanda Excluida com Sucesso!");
        inativas();
    }
}
