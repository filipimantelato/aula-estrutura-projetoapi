package com.aula.projeto.info;

import com.aula.projeto.user.UserModel;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "tb_info")
public class InfoModel {

    public InfoModel() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    @Nonnull
    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(@Nonnull String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    @Id
    @GeneratedValue(generator = "UUID") //igual ao auto_increment
    private UUID id;

    private int idade;
    private int altura;

    @Nonnull
    private String nacionalidade;
}
