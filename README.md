# Projeto FEITV Java

1. Introdução
O projeto FEI TV é uma aplicação desktop robusta desenvolvida para a catalogação, visualização e interação com conteúdos multimédia, incluindo filmes, séries e música. O software foi concebido sob os princípios da engenharia de software moderna, utilizando a linguagem Java para a lógica de aplicação e o PostgreSQL como sistema de gestão de base de dados relacional.

2. Arquitetura e Funcionalidades Java
A aplicação segue o padrão de projeto MVC (Model-View-Controller), o que facilita a manutenção e a expansão do sistema.

2.1. Interface Gráfica (Swing)
A interface foi construída utilizando a biblioteca Java Swing, com foco em IHC (Interação Humano-Computador):
•	Sistema de Login e Sessão: Implementação de um fluxo de autenticação seguro. Ao realizar o login, o sistema captura o ID único do utilizador do banco de dados e armazena-o numa classe de Sessão (Singleton), garantindo que todas as interações subsequentes (como curtidas) sejam vinculadas à conta correta.
•	Navegação Dinâmica: Utilização de eventos de rato para alternar entre diferentes categorias de média. O sistema de menu destaca visualmente a aba ativa através da mudança de cores.
•	Painel de Detalhes Adaptativo: Um painel lateral que carrega dinamicamente informações como título, género, ano, realizador e capas de imagem. Foi implementada uma lógica de revalidate() e repaint() para garantir que a interface se ajuste ao conteúdo sem deixar áreas em branco.
•	Otimização de Scroll: Ajuste do unitIncrement do ScrollPane para proporcionar uma navegação fluida e rápida pela lista de conteúdos.

2.2. Lógica de Interação (Sistemas de Toggle)
Uma das funcionalidades mais avançadas do projeto é a lógica de alternância (Toggle):
•	Reações (Like/Deslike): O sistema permite que o utilizador curta ou descurta um item. Se o utilizador clicar novamente no botão de uma opção já selecionada, a aplicação interpreta a ação como um pedido para "desmarcar", limpando o registo no banco de dados.
•	Favoritos: Funcionalidade independente das reações, onde o utilizador pode criar uma biblioteca personalizada de conteúdos.

3. Arquitetura da Base de Dados (PostgreSQL)
O banco de dados foi estruturado para garantir Integridade Referencial e alta performance em consultas complexas.

3.1. Esquema de Tabelas
Tabela	Principais Atributos	Função
 
usuarios	id, login, senha	Armazena as credenciais de acesso.
catalogo	id, titulo, tipo, genero, ano, capa	Repositório central de todos os conteúdos multimédia.
reacoes	id_usuario, id_midia, valor	Gere os Likes (1), Deslikes (-1) e Neutros (0).
favoritos	id_usuario, id_midia	Associação de N-para-N para itens marcados como favoritos.


3.2. Restrições e Segurança (Constraints)
Para prevenir a inserção de dados inconsistentes, foram aplicadas Check Constraints (como a que limita os valores de reação a 1, -1 ou 0) e Foreign Keys que garantem que nenhum "Like" exista sem um utilizador ou mídia válida associada.

4. Considerações de UX e Performance
O projeto aplica várias heurísticas de Nielsen:
1.	Visibilidade do Estado do Sistema: Feedback visual imediato através de cores (Verde para Like, Amarelo para Favorito) e textos dinâmicos ("Curtido" vs "Curtir").
2.	Prevenção de Erros: Tratamento de exceções (Try-Catch) em todas as operações de banco de dados e entrada de utilizador.

5. Conclusão
O sistema FEI TV é uma solução completa que integra front-end Java de alto nível com um back-end robusto. A implementação bem-sucedida das listas de reações e favoritos demonstra a maturidade técnica do projeto em lidar com persistência de dados por utilizador e interfaces gráficas complexas.
