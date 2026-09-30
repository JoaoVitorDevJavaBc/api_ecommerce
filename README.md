API E-Commerce Full-Stack
Esta é uma API REST robusta e escalável para gerenciamento de um e-commerce full-stack, desenvolvida com Java e Spring Boot. O sistema integra persistência de dados relacional com MySQL e um ecossistema completo de pagamentos online automatizados via Stripe API, tudo protegido por uma camada rígida de segurança utilizando Spring Security e tokens JWT.
🛠️ Tecnologias Utilizadas
• Java 17 & Spring Boot 3
• Spring Data JPA & Hibernate (Persistência de dados)
• MySQL (Banco de dados relacional)
• Stripe Java SDK (Processamento de pagamentos dinâmicos)
• Spring Security & JJWT (Java JWT) (Autenticação Stateless e Autorização)
• Maven (Gerenciamento de dependências)
Funcionalidades Principais
• Carrinho de Compras Dinâmico: Fluxo preparado para receber múltiplos itens e quantidades reais em uma única requisição.
• Integração Avançada com Stripe: Geração automatizada de Checkout Sessions enviando metadados de controle internos.
• Webhooks Automatizados: Endpoint exclusivo e seguro focado em escutar o evento checkout.session.completed do Stripe para atualizar o status do pedido em tempo real.
• Segurança Baseada em Tokens (JWT): Filtro customizado que intercepta requisições HTTP, valida as assinaturas criptográficas e garante acesso controlado aos recursos protegidos.
• Persistência Segura (JPA @Transactional): Garante a integridade dos dados no MySQL realizando rollback automático em caso de falhas durante o fluxo de checkout.
 Como Configurar o Projeto
Antes de rodar a aplicação, adicione as variáveis de ambiente necessárias no seu arquivo src/main/resources/application.properties:
properties
# Banco de Dados MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/meu_ecommerce
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

# Configurações do Stripe API
stripe.api.key=sk_test_sua_chave_secreta_aqui
stripe.webhook.secret=whsec_seu_secret_aqui

# Configuração de Segurança do JWT
api.security.token.secret=SuaFraseSuperSecretaComMaisDe32Caracteres
Use o código com cuidado.
 Endpoints Principais da API
Autenticação 
• POST /api/auth/login - Realiza a autenticação do usuário e retorna o Token JWT. (Público)
Produtos 
• GET /api/produtos - Lista todos os produtos cadastrados. (Público)
• GET /api/produtos-fisicos - Lista os produtos físicos disponíveis. (Público)
• POST /api/produtos-fisicos - Cadastro de novos itens de estoque. (Protegido)
Pedidos & Checkout 
• POST /api/pedidos/checkout - Recebe o carrinho de compras, cria o pedido no MySQL e gera o link de pagamento do Stripe. (Protegido)
Webhooks 
• POST /api/webhooks/stripe - Endpoint utilizado pelo Stripe para confirmar pagamentos aprovados e atualizar o status do pedido para PAGO. (Público)
