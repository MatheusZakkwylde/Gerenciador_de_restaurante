package models;

import javax.persistence.Entity;
import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Pessoa extends Model {

    public Blob foto;
    public String nome;
    public String cpf;
    public String email;
    public String telefone;
    public String celular;
    public String cidade;
    public String estado;
    public String bairro;
    public String logradouro;
    public String informacoes;
}
