package models;

import Enums.Cargo;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Usuario extends Pessoa {

    public String senha;
    public String confSenha;
    @Enumerated(EnumType.STRING)
    public Cargo cargo;
}
