package models;

import Enums.StatusCaixa;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import play.db.jpa.Model;

@Entity
public class Caixa extends Model {
    public float totalInicial;
    public float total;
    
    @Temporal(TemporalType.DATE)
    public Date data;
    
    @Enumerated(EnumType.STRING)
    public StatusCaixa status;
}
