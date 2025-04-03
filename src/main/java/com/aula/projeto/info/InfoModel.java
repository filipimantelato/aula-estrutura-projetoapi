package com.aula.projeto.info;

import com.aula.projeto.user.UserModel;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "tb_info") //nome da tabela no banco h2
public class InfoModel {

    public InfoModel() {
    }

    @Id
    @GeneratedValue(generator = "UUID") //igual ao auto_increment, gera valor aleatorio para o id, sem repetir
    private UUID id;

    private String username;
    private int idade;
    private int altura;

    @Nonnull //nao nulo
    private String nacionalidade;

    @Column(name = "endereco", length = 255) //coluna com nome endereco e tamanho 255 max
    private String endereco;
    private UUID idUser;

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

}
