/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package fei.feitv;

import fei.model.dao.Conexao;
import fei.view.*;
import java.sql.SQLException;

/**
 *
 * @author aless
 */

public class Main {
    public static void main(String[] args) throws SQLException{
        Conexao conexao = new Conexao();
        conexao.getConnection();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrameMenu telaInicial = new JFrameMenu();
                telaInicial.setVisible(true);
            }
        });
    }
}

