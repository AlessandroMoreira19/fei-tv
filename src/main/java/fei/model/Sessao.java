/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.model;

/**
 *
 * @author aless
 */

public class Sessao {
    private static Sessao instance;
    public static int idUsuarioLogado;
    private Usuario usuarioLogado;
    
    private Sessao(){}
    
     /**
  * Verifica a sessao do usuario como uma variavel
  */

    public static Sessao getInstance(){
        if (instance == null){
            instance = new Sessao();
        }
        return instance;
    }
    
     /**
  * GETTERS E SETTERS
  */
    
    public void setUsuario(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public Usuario getUsuario() {
        return usuarioLogado;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
    
    public void encerrarSessao() {
        this.usuarioLogado = null;
    }
}
