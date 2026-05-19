/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.model.dao;

import fei.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author feispamoreira
 */

public class UsuarioDao {
    private Connection comn;

    public UsuarioDao(Connection comn) {
        this.comn = comn;
    } 
    
    /**
  * Essa funcao adiciona o usuario na tabela de usuarios
  */
    
    public void criarConta(Usuario usuario) {
        String sqlVer = "SELECT 1 FROM usuarios WHERE usuario = ?";
        String sqlIns = "INSERT INTO usuarios(usuario, senha) VALUES (?, ?)";

        if (this.comn == null) {
            System.out.println("Erro: A conexão 'comn' não foi inicializada.");
            return;
        }

        try (PreparedStatement stmtVer = comn.prepareStatement(sqlVer)) {
            stmtVer.setString(1, usuario.getUsuario());

            try (ResultSet rs = stmtVer.executeQuery()) {
                if (!rs.next()) {
                    try (PreparedStatement stmtIns = comn.
                            prepareStatement(sqlIns)) {
                        stmtIns.setString(1, usuario.getUsuario());
                        stmtIns.setString(2, usuario.getSenha());
                        stmtIns.execute();
                        JOptionPane.showMessageDialog(null,
                                "Usuário cadastrado com sucesso!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Esse nome de usuário já existe!");
                }
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(null, "Erro no banco de dados: " 
                    + s.getMessage());
        }
    }
    
    /**
  * Essa funcao consulta o login do usuario na tabela de usuarios
  */
    
    public Usuario consultarLogin(Usuario usuario) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ?";

        try (PreparedStatement statement = comn.prepareStatement(sql)) {
            statement.setString(1, usuario.getUsuario());
            statement.setString(2, usuario.getSenha());

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Usuario logado = new Usuario();
                    logado.setId(rs.getInt("id"));
                    logado.setUsuario(rs.getString("usuario"));
                    return logado;
                }
            }
        }
        return null;
    }
}
