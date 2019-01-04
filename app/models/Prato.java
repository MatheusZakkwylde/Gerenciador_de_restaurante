package models;

import Enums.TipoPrato;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Prato extends Model {
    public String nome;
    public String acompanhamento;
    public float preco;
    public int quantidade;
    public Blob foto;
    public int idTipo;
    
    @Enumerated(EnumType.STRING)
    public TipoPrato tipo;
}
