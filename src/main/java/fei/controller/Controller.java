/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.controller;

import fei.view.*;
import fei.model.*;
import fei.model.dao.Conexao;
import java.sql.Connection;
import fei.model.dao.UsuarioDao;
import fei.model.dao.MidiaDao;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author feispamoreira
 */

public class Controller {

    private JFrameLogin viewLogin;
    private JFrameCriarConta viewCriarConta;
    private JFrameMidia viewMidia;
    private UsuarioDao usuarioDao;
    private Usuario modelUsuario;
    private MidiaDao midiaDao;
    private Connection connection;
    private String menuAtivo = "todos";

     /**
  * Construtores
  */
    
    public Controller(JFrameCriarConta viewCriarConta, Usuario modelUsuario)
            throws SQLException {
        this.viewCriarConta = viewCriarConta;
        this.modelUsuario = modelUsuario;
        this.prepararDao();
    }

    public Controller(JFrameLogin viewLogin, Usuario modelUsuario)
            throws SQLException {
        this.viewLogin = viewLogin;
        this.modelUsuario = modelUsuario;
        this.prepararDao();
    }

    public Controller(JFrameMidia viewMidia, Usuario modelUsuario)
            throws SQLException {
        this.viewMidia = viewMidia;
        this.modelUsuario = modelUsuario;
        this.prepararDao();
    }

    
    /**
  * GETTERS E SETTERS
  */
    
    public JFrameLogin getViewLogin() {
        return viewLogin;
    }

    public JFrameMidia getViewMidia() {
        return viewMidia;
    }

    public Usuario getModel() {
        return this.modelUsuario;
    }

    public JFrameCriarConta getView() {
        return this.viewCriarConta;
    }

    public void setViewLogin(JFrameLogin viewLogin) {
        this.viewLogin = viewLogin;
    }

    public void setViewCriarConta(JFrameCriarConta viewCriarConta) {
        this.viewCriarConta = viewCriarConta;
    }

    public void setViewMidia(JFrameMidia viewMidia) {
        this.viewMidia = viewMidia;
    }

    public void setModelUsuario(Usuario modelUsuario) {
        this.modelUsuario = modelUsuario;
    }

     /**
  * Essa função prepara a conexao com o banco de dados
  */
    
    public void prepararDao() throws SQLException {
        try {
            this.connection = new Conexao().getConnection();
            this.usuarioDao = new UsuarioDao(connection);
            this.midiaDao = new MidiaDao(this.connection);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Banco de Dados Offline. Erro na conexão com o servidor"
                            + " PostgreSQL!");
        }
    }
    
    /**
  * Essa funcao cria a conta do usuario 
  */
    
    public void criarConta() {
        String user = this.viewCriarConta.getUsuarioLabelCriarConta().getText();
        String pass = this.viewCriarConta.getSenhaLabelCriarConta().getText();

        Usuario u = new Usuario(user, pass);
        
        // Verifica se os campos foram preenchidos
        if (user.length() >= 4 && pass.length() >= 4) {
            try {
                Usuario.getListaUsuarios().add(u);
                this.usuarioDao.criarConta(u);
                
                JFrameMenu proximaTela = new JFrameMenu();
                proximaTela.setVisible(true);
                // Salva o novo usuario
                this.viewCriarConta.dispose(); 
            } catch (NumberFormatException e) {
                this.viewCriarConta.getMsgErro().setForeground(new
                    java.awt.Color(255, 0, 0));
                this.viewCriarConta.getMsgErro().setText("Os dois campos devem"
                        + " ser preenchidos!");
            }
        } else {
            this.viewCriarConta.getMsgErro().setForeground(new
                java.awt.Color(255, 0, 0));
            this.viewCriarConta.getMsgErro().setText("Os dois campos devem ter"
                    + " mais que 3 caracteres!");
        }
    }

     /**
  * Essa funcao realiza login do usuario
  */
    
    public void login() {
        String user = this.viewLogin.getUsuarioLabelLogin().getText();
        String pass = this.viewLogin.getSenhaLabelLogin().getText();

        Usuario u = new Usuario(user, pass);
        
        // Verifica se os campos foram preenchidos
        if (user.length() >= 4 && pass.length() >= 4) {
            try {
                Usuario usuarioAutenticado = usuarioDao.consultarLogin(u);

                // Verifica a autenticacao do usuario
                if (usuarioAutenticado != null) {
                    Sessao.idUsuarioLogado = usuarioAutenticado.getId();

                    Sessao.getInstance().setUsuario(usuarioAutenticado);

                    this.viewLogin.getMsgErro().setForeground(new
                        java.awt.Color(0, 255, 0));
                    this.viewLogin.getMsgErro().setText("Login realizado com"
                            + " sucesso!");
                    JOptionPane.showMessageDialog(null,
                        "Login realizado com sucesso!");

                    JFrameMidia proximaTela = new JFrameMidia();
                    proximaTela.setVisible(true);
                    this.viewLogin.dispose();
                } else {
                    this.viewLogin.getMsgErro().setForeground(new
                        java.awt.Color(255, 0, 0));
                    this.viewLogin.getMsgErro().setText("Login ou senha"
                        + " incorretos!");
                }
            } catch (NumberFormatException e) {
                this.viewLogin.getMsgErro().setForeground(new
                    java.awt.Color(255, 0, 0));
                this.viewLogin.getMsgErro().setText("Os dois campos devem ser"
                    + " preenchidos!");
            } catch (SQLException e) {
                this.viewLogin.getMsgErro().setForeground(new
                    java.awt.Color(255, 0, 0));
                this.viewLogin.getMsgErro().setText("Erro na conexao com o"
                    + " Banco de Dados");
            }
        } else {
            this.viewLogin.getMsgErro().setForeground(new
                java.awt.Color(255, 0, 0));
            this.viewLogin.getMsgErro().setText("Os dois campos devem ter mais"
                + " que 3 caracteres!");
        }
    }

     /**
  * Essa funcao faz logout do usuario
  */
    
    public void logout() {
        Sessao.idUsuarioLogado = 0;

        Sessao.getInstance().encerrarSessao();

        // Limpa as midias exibidas
        if (this.midiasExibidas != null) {
            this.midiasExibidas.clear();
        }

        viewMidia.dispose();
        viewLogin.setVisible(true);
    }
    
    // Arrays com as midias
    private ArrayList<Midia> midiasExibidas = new ArrayList<>();
    private ArrayList<Midia> cacheMidias;

     /**
  * Essa funcao inicia o cache de ArrayLists
  */
    
    public void inicializarCache() {
        try {
            this.cacheMidias = this.midiaDao.buscarTodasAsMidias();
            System.out.println("Tamanho do cache: " + cacheMidias.size());
        } catch (SQLException e) {
            System.out.println("Erro ao carregar cache: " + e.getMessage());
        }
    }

     /**
  * Essa funcao limpa o painel de detalhes
  */
    
    public void limparPainelDetalhes() {
        // Limpa o painel, deixando-o em branco
        viewMidia.getLblCapaValor().setIcon(null);
        viewMidia.getLblCapaValor().setText("");

        viewMidia.getLblTituloValor().setText("");
        viewMidia.getLblAnoValor().setText("");
        viewMidia.getLblDuracaoValor().setText("");
        viewMidia.getLblGeneroValor().setText("");
        viewMidia.getLblClassificacaoValor().setText("");

        viewMidia.getLblExtraValor().setText("");
        viewMidia.getButtonLike().setVisible(false);
        viewMidia.getButtonDeslike().setVisible(false);
        viewMidia.getButtonLike().setEnabled(false);
        viewMidia.getButtonDeslike().setEnabled(false);
        viewMidia.getButtonFavoritar().setVisible(false);
        viewMidia.getButtonFavoritar().setEnabled(false);
        
        //Atualiza o frame
        viewMidia.repaint();
    }

     /**
  * Essa funcao exibe os detalhes da midia selecionada
  */
    
    public void exibirDetalhes() {
        int linhaSelecionada = viewMidia.getTabelaMidias().getSelectedRow();

        if (linhaSelecionada == -1) {
            limparPainelDetalhes();
            return;
        }

        viewMidia.getjPanel3().revalidate();
        viewMidia.getjPanel3().repaint();

        Midia selecionada = midiasExibidas.get(linhaSelecionada);
        String tipo = selecionada.getTipo();

        // Altera a visibilidade e o uso dos botões caso o painel esteja em 
        // favoritos ou reacoes
        if (this.menuAtivo.equals("favoritos") ||
                this.menuAtivo.equals("reacoes")){
            viewMidia.getButtonLike().setVisible(false);
            viewMidia.getButtonDeslike().setVisible(false);
            viewMidia.getButtonLike().setEnabled(false);
            viewMidia.getButtonDeslike().setEnabled(false);
            viewMidia.getButtonFavoritar().setVisible(false);
            viewMidia.getButtonFavoritar().setEnabled(false);
        } else {
            viewMidia.getButtonLike().setVisible(true);
            viewMidia.getButtonDeslike().setVisible(true);
            viewMidia.getButtonLike().setEnabled(true);
            viewMidia.getButtonDeslike().setEnabled(true);
            viewMidia.getButtonFavoritar().setVisible(true);
            viewMidia.getButtonFavoritar().setEnabled(true);
            try {
                boolean favorito = midiaDao.eFavorito(selecionada.getId());

                if (favorito) {
                    viewMidia.getButtonFavoritar().setText("★ Favoritado");
                    viewMidia.getButtonFavoritar().setForeground(Color.YELLOW);
                } else {
                    viewMidia.getButtonFavoritar().setText("☆ Favoritar");
                    viewMidia.getButtonFavoritar().setForeground(Color.WHITE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        // Mostra as capas dos albuns
        viewMidia.getLblCapaValor().setIcon(null);
        viewMidia.getLblCapaValor().setText("Carregando...");

        String nomeBusca = selecionada.getTipo().equalsIgnoreCase("Musica")
                ? selecionada.getAlbum()
                : selecionada.getTitulo();

        nomeBusca = nomeBusca.replace(":", "");

        // Abre o local na pasta raiz das capas de midia
        String caminhoParaImagem = "capas/" + nomeBusca + ".jpg";
        java.io.File arquivoImg = new java.io.File(caminhoParaImagem);

        java.awt.Color textoBranco = java.awt.Color.WHITE;

        if (arquivoImg.exists()) {
            try {
                java.awt.image.BufferedImage imgFull = javax.imageio.ImageIO.
                        read(arquivoImg);

                if (imgFull != null) {
                    // Ajusta diferentes tamanhos para musicas e series/filmes
                    int largura = tipo.equalsIgnoreCase("Musica") ? 200 : 150;
                    int altura = tipo.equalsIgnoreCase("Musica") ? 200 : 225;

                    java.awt.Image imgRedimensionada = imgFull.
                        getScaledInstance(largura, altura,
                            java.awt.Image.SCALE_SMOOTH);

                    viewMidia.getLblCapaValor().setIcon(new
                        javax.swing.ImageIcon(imgRedimensionada));
                    viewMidia.getLblCapaValor().setText("");

                    viewMidia.getPainelDetalhes().revalidate();
                    viewMidia.getPainelDetalhes().repaint();
                }
            } catch (java.io.IOException e) {
                System.err.println("Erro técnico ao ler ficheiro: "
                    + e.getMessage());
            }
        } else {
            viewMidia.getLblCapaValor().setIcon(null);
            viewMidia.getLblCapaValor().setForeground(textoBranco);
            viewMidia.getLblCapaValor().setText("Capa não encontrada");
            System.out.println("Aviso: Arquivo ausente em "
                + caminhoParaImagem);
        }

        // Altera os textos principais do painel
        viewMidia.getLblTituloValor().setText(selecionada.getTitulo());
        viewMidia.getLblAnoValor().setText("Ano: " + selecionada.getAno());
        viewMidia.getLblDuracaoValor().setText("Duração: "
            + selecionada.getDuracao());
        viewMidia.getLblGeneroValor().setText("Gênero: "
            + selecionada.getGenero());
        viewMidia.getLblClassificacaoValor().setText("Classificacao: "
            + selecionada.getClassificacao());

        String textoExtra = "";

        // Altera os textos extras do painel
        if (tipo.equalsIgnoreCase("Filme")) {
            textoExtra = "Diretor: " + selecionada.getDiretor();
        } else if (tipo.equalsIgnoreCase("Musica")) {
            textoExtra = "Artista: " + selecionada.getArtista()
                + "  | Álbum: " + selecionada.getAlbum();
        } else if (tipo.equalsIgnoreCase("Serie")) {
            textoExtra = "Temporadas: " + selecionada.getTemporadas();
        }

        try {
            int reacao = midiaDao.buscarReacaoUsuario(selecionada.getId());

            // Pinta a fonte dos botões (Verde para Like, Vermelho para Deslike,
            // Branco para neutro)
            viewMidia.getButtonLike().setForeground(reacao == 1 ? 
                Color.GREEN : Color.WHITE);
            viewMidia.getButtonDeslike().setForeground(reacao == -1 ?
                Color.RED : Color.WHITE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // Verifica se é favorito para mudar a cor do botão
            boolean favorito = midiaDao.eFavorito(selecionada.getId());
            // Amarelo para favorito
            viewMidia.getButtonFavoritar().setForeground(favorito ?
                Color.YELLOW : Color.WHITE);
            viewMidia.getButtonFavoritar().setText(favorito ?
                "★ Favorito" : "☆ Favoritar");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        viewMidia.getLblExtraValor().setText(textoExtra);
        viewMidia.revalidate();
        viewMidia.repaint();
    }

     /**
  * Essa funcao salva a reacao do usuario
  */
    
    public void processarReacao(int valorDesejado) {
        int linha = viewMidia.getTabelaMidias().getSelectedRow();

        // Verifica o id da midia selecionada na tabela
        if (linha >= 0) {
            Midia selecionada = midiasExibidas.get(linha);

            int idMidia = selecionada.getId();

            int reacaoAtual = midiaDao.buscarReacaoUsuario(idMidia);

            int valorAFaturar;
            if (reacaoAtual == valorDesejado) {
                valorAFaturar = 0;
            } else {
                valorAFaturar = valorDesejado;
            }

            // Salva o id no banco de dados com a alteracao
            midiaDao.salvarReacao(idMidia, valorAFaturar);
            exibirDetalhes();

            if (this.menuAtivo.equalsIgnoreCase("reacoes")) {
                listarReacoes();
            }
        }
    }

     /**
  * Essa funcao lista as reacoes do usuario
  */
    
    public void listarReacoes() {
        this.menuAtivo = "reacoes";
        destacarMenu("reacoes");
        
        try {
            this.midiasExibidas = midiaDao.buscarReacoes();

            DefaultTableModel modelo = (DefaultTableModel)
                viewMidia.getTabelaMidias().getModel();
            modelo.setRowCount(0);

            // Colunas da tabela do filtro Reacoes
            modelo.setColumnIdentifiers(new
                String[]{"Título", "Tipo", "Reação"});

            for (Midia m : midiasExibidas) {
                String txtReacao = (m.getReacoes() == 1) ?
                    "👍 Like" : "👎 Deslike";
                modelo.addRow(new Object[]{
                    m.getTitulo(),
                    m.getTipo(),
                    txtReacao
                });
            }

            limparPainelDetalhes();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(viewMidia,
                "Erro ao carregar reações: " + e.getMessage());
        }
    }

     /**
  * Essa funcao filtra os itens da tabela
  */
    
    public void filtrarPorTitulo() {
        this.resetarCoresMenus();
        viewMidia.repaint();

        String termo = viewMidia.getLblBusca().getText();

        if (termo == null || termo.trim().isEmpty()) {
            inicializarCache();
            return;
        }

        // Verifica o ArrayList com as midias do banco de dados
        ArrayList<Midia> resultados = midiaDao.buscarPorTitulo(termo);

        var modelo = (DefaultTableModel) viewMidia.getTabelaMidias().getModel();
        //Limpa o painel antes de exibir
        midiasExibidas.clear();

        viewMidia.repaint();
        String[] colunas;
        colunas = new String[]{"Titulo", "Tipo", "Ano"};
        modelo.setDataVector(new Object[][][]{}, colunas);
        
        this.midiasExibidas = resultados;

        limparPainelDetalhes();

        atualizarTabelaVisual();
    }

     /**
  * Essa funcao atualiza a tabela
  */
    
    private void atualizarTabelaVisual() {
        DefaultTableModel modelo = (DefaultTableModel)
            viewMidia.getTabelaMidias().getModel();
        modelo.setNumRows(0);

        // Adiciona os itens do ArrayList na tabela
        for (Midia m : midiasExibidas) {
            modelo.addRow(new Object[]{
                m.getTitulo(),
                m.getTipo(),
                m.getAno(),
                m.getGenero()
            });
        }

    }

     /**
  * Essa funcao filtra os itens de acordo com o menu selecionado
  */
    
    public void filtrarPorTipo(String tipoDesejado) {
        this.menuAtivo = tipoDesejado.toLowerCase();
        System.out.println("Filtrando por: " + tipoDesejado);
        var modelo = (DefaultTableModel) viewMidia.getTabelaMidias().getModel();
        midiasExibidas.clear();

        this.destacarMenu(tipoDesejado);
        viewMidia.repaint();

        
        String[] colunas;
        if (tipoDesejado.equalsIgnoreCase("Filme")) {
            colunas = new String[]{"Título", "Diretor", "Ano"};
        } else if (tipoDesejado.equalsIgnoreCase("Serie")) {
            colunas = new String[]{"Título", "Temporadas", "Ano"};
        } else {
            colunas = new String[]{"Título", "Artista", "Ano"};
        }

        modelo.setDataVector(new Object[][]{}, colunas);
        for (Midia m : cacheMidias) {
            if (m.getTipo().equalsIgnoreCase(tipoDesejado)) {
                midiasExibidas.add(m);
                // Altera os nomes das colunas da tabela de acordo com o filtro
                Object[] linha;
                if (tipoDesejado.equalsIgnoreCase("Filme")) {
                    linha = new Object[]{m.getTitulo(), m.getDiretor(),
                        m.getAno()};
                } else if (tipoDesejado.equalsIgnoreCase("Serie")) {
                    linha = new Object[]{m.getTitulo(), m.getTemporadas(),
                        m.getAno()};
                } else {
                    linha = new Object[]{m.getTitulo(), m.getArtista(),
                        m.getAno()};
                }
                modelo.addRow(linha);
            }
        }
        limparPainelDetalhes();
    }

     /**
  * Essa funcao colore o menu
  */
    
    public void destacarMenu(String tipoDesejado) {
        resetarCoresMenus();

        // Altera as cores do menu de acordo com o clique do mouse
        switch (tipoDesejado.toLowerCase()) {
            case "filme":
                pintarComponente(viewMidia.getMenuFilme(), new
                    java.awt.Color(255, 0, 0));
                break;
            case "serie":
                pintarComponente(viewMidia.getMenuSerie(), new
                    java.awt.Color(255, 0, 0));
                break;
            case "musica":
                pintarComponente(viewMidia.getMenuMusica(), new
                    java.awt.Color(255, 0, 0));
                break;
            case "reacoes":
                pintarComponente(viewMidia.getMenuReacoes(), new
                    Color(255, 0, 0));
                break;
            case "favoritos":
                pintarComponente(viewMidia.getMenuFavoritar(), new
                    Color(255, 0, 0));
                break;
        }
    }
    
     /**
  * Essa funcao reseta as cores do menu
  */

    private void resetarCoresMenus() {
        // Reseta as cores ao trocar para outro menu
        viewMidia.getMenuFilme().setOpaque(false);
        viewMidia.getMenuSerie().setOpaque(false);
        viewMidia.getMenuMusica().setOpaque(false);
        viewMidia.getMenuReacoes().setOpaque(false);
        viewMidia.getMenuFavoritar().setOpaque(false);
    }

     /**
  * Essa funcao pinta os componentes do menu
  */
    
    private void pintarComponente(javax.swing.JComponent comp,
            java.awt.Color cor) {
        // Pinta o menu selecionado
        comp.setOpaque(true);
        comp.setBackground(cor);
        comp.repaint();
    }
    
     /**
  * Essa funcao lista os favoritos na tabela
  */
    
    public void listarFavoritos() {
        this.menuAtivo = "favoritos";
        destacarMenu("favoritos");

        limparPainelDetalhes();

        try {
            this.midiasExibidas = midiaDao.buscarMeusFavoritos();

            DefaultTableModel modelo = (DefaultTableModel)
                viewMidia.getTabelaMidias().getModel();
            modelo.setRowCount(0);
            // Altera o nome das colunas da tabela
            modelo.setColumnIdentifiers(new
                String[]{"Título", "Tipo", "Status"});

            // Lista os favoritos filtrados do ArrayList
            for (Midia m : midiasExibidas) {
                modelo.addRow(new Object[]{m.getTitulo(), m.getTipo(),
                    "⭐ Favorito"});
            }

            limparPainelDetalhes();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     /**
  * Essa funcao salva o favorito do usuario
  */
    
    public void processarFavorito() {
        int linha = viewMidia.getTabelaMidias().getSelectedRow();
        if (linha >= 0) {
            Midia selecionada = midiasExibidas.get(linha);
            try {
                // Salva o id do favorito no banco de dados
                midiaDao.alternarFavorito(selecionada.getId());
                exibirDetalhes();

                if (this.menuAtivo.equals("favoritos")) {
                    listarFavoritos();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
}
