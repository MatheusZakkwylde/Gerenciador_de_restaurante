
package controllers;

import Enums.Condicao;
import Enums.StatusPedido;
import java.util.List;
import models.Comanda;
import models.Pedido;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Cozinha extends Controller {
    
    public  static void cozinha (){
        List<Comanda> comandas = Comanda.findAll();
        
        boolean temNovoPedido = false;
        
        for(Comanda c: comandas){
            for(Pedido p: c.pedidos){
                if(p.condicao.equals(Condicao.ADICIONADO)){
                    p.condicao = Condicao.CONFIRMADO;
                    p.save();
                    temNovoPedido = true;
                    flash.success("Novo Pedido cadastrado na comanda de numero "+c.numero+" do cliente "+c.cliente);
                    render(comandas,temNovoPedido);
                    break;
                }
            }
        }
          render(comandas,temNovoPedido);
    }
    
    public static void pedidoFeito (Long id){
        Pedido pedido = Pedido.findById(id);
        pedido.status = StatusPedido.FEITO;
        pedido.save();
        cozinha();
    }
}
