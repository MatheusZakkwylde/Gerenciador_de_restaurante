package models;

import Enums.Condicao;
import Enums.StatusPedido;
import Enums.TipoPrato;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import javax.persistence.ManyToOne;
import play.db.jpa.Model;

@Entity
public class Pedido extends Model {
    public int numero;
    public String garcom;
    public float preco;
    public int quantidade;
    public String informacao;
    
    @Enumerated(EnumType.STRING)
    public TipoPrato tipo;
    
    @Enumerated(EnumType.STRING)
    public StatusPedido status;
    
    @Enumerated(EnumType.STRING)
    public Condicao condicao;
    
    @ManyToOne
    public Prato Pedido;

    @ManyToOne
    public Comanda comanda;
}
