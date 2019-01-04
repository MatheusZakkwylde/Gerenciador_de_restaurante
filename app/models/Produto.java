package models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import play.db.jpa.Model;

@Entity
public class Produto extends Model {

    public String produto;
    public int quantidade;
    public float precoUnitario;
    public float pagarFornecedor;
    public String informacao;

    @ManyToOne
    public Fornecedor fornecedor;

    @Temporal(TemporalType.DATE)
    public Date dataCompra;
    
    @Temporal(TemporalType.DATE)
    public Date dataPagarFornecedor;
}
