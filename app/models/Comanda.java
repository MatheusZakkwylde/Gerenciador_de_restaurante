package models;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import play.db.jpa.Model;

@Entity
public class Comanda extends Model {
    
    public int numero;
    public String cliente;
    public String senha;
    public float total;
    public int status;

    @Temporal(TemporalType.DATE)
    public Date data;
    
    @OneToMany(mappedBy = "comanda")
    public List<Pedido> pedidos;
}
