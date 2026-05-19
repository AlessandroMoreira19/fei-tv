/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.model;

/**
 *
 * @author feispamoreira
 */

public class Midia{
    private int id;
    private String tipo;
    private String titulo; // Filmes
    private String ano;
    private String duracao;
    private String genero;
    private String diretor;
    
    private String temporadas; // Séries
    
    private String artista; // Músicas
    private String album;
    private byte[] capa;
    
    private String classificacao;
    private int reacoes;
    
     /**
  * Construtores
  */
    
    public Midia(int id, String tipo, String titulo, String genero,
            String duracao, String temporadas, String ano, String album,
            String artista, String diretor, String classificacao) {
        this.id = id;
        this.tipo = tipo;
        this.titulo = titulo;
        this.genero = genero;
        this.duracao = duracao;
        this.temporadas = temporadas;
        this.ano = ano;
        this.album = album;
        this.artista = artista;
        this.diretor = diretor;
        this.capa = null;
        this.classificacao = classificacao;
    }

     /**
  * GETTERS E SETTERS
  */
    
    public byte[] getCapa() {
        return capa;
    }

    public void setCapa(byte[] capa) {
        this.capa = capa;
    }
           
    public String getTitulo() {
        return titulo;
    }

    public String getAno() {
        return ano;
    }

    public String getDuracao() {
        return duracao;
    }

    public String getGenero() {
        return genero;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String getTemporadas() {
        return temporadas;
    }

    public String getArtista() {
        return artista;
    }

    public String getAlbum() {
        return album;
    }

    public String getTipo() {
        return tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setTemporadas(String temporadas) {
        this.temporadas = temporadas;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void setAlbum(String album) {
        this.album = album;
    } 

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public int getReacoes() {
        return reacoes;
    }

    public void setReacoes(int reacoes) {
        this.reacoes = reacoes;
    }
}
