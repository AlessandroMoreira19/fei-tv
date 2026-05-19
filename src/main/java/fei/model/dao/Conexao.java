/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.model.dao;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql. Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author feispamoreira
 */

public class Conexao {
    public Connection getConnection() throws SQLException{
        
       Dotenv dotenv = Dotenv.load();
                
        Connection conexao = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres","postgres",
                dotenv.get("SEHNA_DB"));
                System.out.println("Conexao bem sucedida");
                return conexao;
        }

    Connection conectaBD() {
        throw new UnsupportedOperationException("Not supported yet.");
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}