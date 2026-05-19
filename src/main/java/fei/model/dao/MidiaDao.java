/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.model.dao;

import fei.model.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
/**
 *
 * @author feispamoreira
 */

public class MidiaDao {
    
    private Connection comn;

    public MidiaDao(Connection comn) {
        this.comn = comn;
    }
    
     /**
  * Essa funcao busca as midias no banco de dados
  */
    
    public ArrayList<Midia> buscarTodasAsMidias() throws SQLException {
        ArrayList<Midia> listaCompleta = new ArrayList<>();
        String sql = "SELECT * FROM catalogo";

        PreparedStatement stmt = comn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while(rs.next()) {
            Midia midia = new Midia(
                rs.getInt("id"), rs.getString("tipo"),
                rs.getString("titulo"), rs.getString("genero"),
                rs.getString("duracao"),rs.getString("temporadas"),
                rs.getString("ano"), rs.getString("album"),
                rs.getString("artista"), rs.getString("diretor"),
                rs.getString("classificacao")
            );
            listaCompleta.add(midia);
        }
        return listaCompleta;
    }
    
     /**
  * Essa funcao carrega as fotos da pasta raíz
  */
    
    public void atualizarCapaNoBanco(String id, byte[] bytesCapa)
            throws SQLException {
        String sql = "UPDATE catalogo SET capa = ? WHERE id = ?";

        try (PreparedStatement stmt = comn.prepareStatement(sql)) {
            stmt.setBytes(1, bytesCapa);
            stmt.setString(2, id);
            stmt.executeUpdate();
        }
    }
    
     /**
  * Essa funcao busca as reacoes do usuario e adiciona no ArrayList
  */
    
    public ArrayList<Midia> buscarReacoes() throws SQLException {
        ArrayList<Midia> lista = new ArrayList<>();
        String sql = "SELECT c.*, r.valor as feedback FROM catalogo c " +
                     "INNER JOIN reacoes r ON c.id = r.id_midia " +
                     "WHERE r.id_usuario = ? AND r.valor != 0 ORDER BY c.titulo";

        try (PreparedStatement stmt = comn.prepareStatement(sql)) {
            stmt.setInt(1, Sessao.idUsuarioLogado);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Midia m = new Midia(
                    rs.getInt("id"), rs.getString("tipo"),
                    rs.getString("titulo"), rs.getString("genero"),
                    rs.getString("duracao"), rs.getString("temporadas"),
                    rs.getString("ano"), rs.getString("album"),
                    rs.getString("artista"), rs.getString("diretor"),
                    rs.getString("classificacao")
                );
                m.setReacoes(rs.getInt("feedback")); 
                lista.add(m);
            }
        }
        return lista;
    }
    
     /**
  * Essa reacao busca a midia de acordo com o titulo
  */
    
    public ArrayList<Midia> buscarPorTitulo(String termo) {
        ArrayList<Midia> lista = new ArrayList<>();
        String sql = "SELECT * FROM catalogo WHERE titulo ILIKE ? ORDER BY tipo,"
                + " titulo";

        try (PreparedStatement stmt = comn.prepareStatement(sql)) {
            stmt.setString(1, termo.trim() + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Midia m = new Midia(
                    rs.getInt("id"), rs.getString("tipo"),
                    rs.getString("titulo"), rs.getString("genero"),
                    rs.getString("duracao"),rs.getString("temporadas"),
                    rs.getString("ano"), rs.getString("album"),
                    rs.getString("artista"), rs.getString("diretor"),
                    rs.getString("classificacao")
                );
                
                m.setId(rs.getInt("id"));
                m.setTipo(rs.getString("tipo"));
                m.setTitulo(rs.getString("titulo"));
                m.setGenero(rs.getString("genero"));
                m.setAno(rs.getString("ano"));
                m.setDuracao(rs.getString("duracao"));
                m.setTemporadas(rs.getString("temporadas"));
                m.setAlbum(rs.getString("album"));
                m.setArtista(rs.getString("artista"));
                m.setDiretor(rs.getString("diretor"));
                m.setClassificacao(rs.getString("classificacao"));

                lista.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Erro na busca SQL: " + e.getMessage());
        }
        return lista;
    }

     /**
  * Essa funcao salva a reacao do usuario
  */
    
    public void salvarReacao(int idMidia, int valor) {
        String sql = "INSERT INTO reacoes (id_usuario, id_midia, valor)"
                + " VALUES (?, ?, ?) ON CONFLICT (id_usuario, id_midia)"
                + " DO UPDATE SET valor = EXCLUDED.valor";

        try (PreparedStatement stmt = comn.prepareStatement(sql)) {
            stmt.setInt(1, Sessao.idUsuarioLogado);
            stmt.setInt(2, idMidia);
            stmt.setInt(3, valor);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao salvar reação: " + e.getMessage());
        }
    }

     /**
  * Essa funcao busca a reacao do usuario
  */
    
    public int buscarReacaoUsuario(int idMidia) {
        String sql = "SELECT valor FROM reacoes WHERE id_usuario = ?"
                + " AND id_midia = ?";
        
        try (PreparedStatement stmt = comn.prepareStatement(sql)) {
            
            stmt.setInt(1, Sessao.idUsuarioLogado);
            stmt.setInt(2, idMidia);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("valor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
  * Essa funcao alterna o favorito do usuario
  */
    
    public void alternarFavorito(int idMidia) throws SQLException {
        String sqlCheck = "SELECT 1 FROM favoritos WHERE id_usuario = ?"
                + " AND id_midia = ?";
        try (PreparedStatement stmt = comn.prepareStatement(sqlCheck)) {
            stmt.setInt(1, Sessao.idUsuarioLogado);
            stmt.setInt(2, idMidia);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String sqlDel = "DELETE FROM favoritos WHERE id_usuario = ?"
                        + " AND id_midia = ?";
                PreparedStatement d = comn.prepareStatement(sqlDel);
                d.setInt(1, Sessao.idUsuarioLogado); d.setInt(2, idMidia);
                d.executeUpdate();
            } else {
                String sqlIns = "INSERT INTO favoritos (id_usuario, id_midia)"
                        + " VALUES (?, ?)";
                PreparedStatement i = comn.prepareStatement(sqlIns);
                i.setInt(1, Sessao.idUsuarioLogado); i.setInt(2, idMidia);
                i.executeUpdate();
            }
        }
    }

    /**
  * Essa funcao verifica os favoritos do usuario
  */
    
    public boolean eFavorito(int idMidia) throws SQLException {
        String sql = "SELECT 1 FROM favoritos WHERE id_usuario = ?"
                + " AND id_midia = ?";
        try (PreparedStatement stmt = comn.prepareStatement(sql)) {
            stmt.setInt(1, Sessao.idUsuarioLogado);
            stmt.setInt(2, idMidia);
            return stmt.executeQuery().next();
        }
    }

    /**
  * Essa funcao busca os favoritos do usuario
  */
    
    public ArrayList<Midia> buscarMeusFavoritos() throws SQLException {
        ArrayList<Midia> lista = new ArrayList<>();
        String sql = "SELECT c.* FROM catalogo c INNER JOIN favoritos f ON"
                + " c.id = f.id_midia WHERE f.id_usuario = ? ORDER BY c.titulo";
        try (PreparedStatement stmt = comn.prepareStatement(sql)) {
            stmt.setInt(1, Sessao.idUsuarioLogado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Midia(rs.getInt("id"), rs.getString("tipo"),
                    rs.getString("titulo"), rs.getString("genero"),
                    rs.getString("duracao"), rs.getString("temporadas"), 
                    rs.getString("ano"), rs.getString("album"),
                    rs.getString("artista"), rs.getString("diretor"),
                    rs.getString("classificacao")));
            }
        }
        return lista;
    } 
}
