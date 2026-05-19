/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.model;

import java.util.ArrayList;

/**
 *
 * @author aless
 */

public class Usuario{
    private int id;
    private String usuario;
    private String senha;
    
    private static ArrayList <Usuario> listaUsuarios = new ArrayList<>();
    
     /**
  * Construtores
  */
    
    public Usuario(){}

    public Usuario(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

     /**
  * GETTERS E SETTERS
  */
    
    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public static ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
    
    public static void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        Usuario.listaUsuarios = listaUsuarios;
    }
}