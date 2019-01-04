
package models;

import javax.persistence.Entity;
import play.db.jpa.Model;
@Entity
public class grafico extends Model {
    public float inicialCaixa;
    public float finalCaixa;
    public int vendaComanda;
}
