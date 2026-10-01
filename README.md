
# E-commerce API

API REST de back-end para uma loja virtual, com autenticação JWT e pagamentos pelo Stripe.
**Status: em desenvolvimento.**

## Tecnologias
Java 17, Spring Boot 3, Spring Security, JWT, Spring Data JPA/Hibernate, MySQL, Stripe Java SDK

## O que a API faz
- **Autenticação:** login com JWT. O catálogo é público; cadastro de produtos e checkout exigem token.
- **Checkout:** recebe o carrinho com vários itens, salva o pedido no MySQL e cria uma Checkout Session no Stripe, com o ID do pedido nos metadados.
- **Webhook:** valida a assinatura do Stripe e, ao receber `checkout.session.completed`, muda o status do pedido para PAGO.
- **Transação:** a criação do pedido usa `@Transactional`.


##  Arquitetura & Fluxo do Sistema

[Cliente Front-End]
│
( Token JWT ) ──► [Spring Security Filter] (Validação de Sessão Stateless)
│
[PedidoController] ──► Salva Pedido & Itens (MySQL via JPA Transactional)
│
[StripeService] ──► Cria Checkout Session Dinâmica ──► [Página de Pagamento Stripe]
│
[WebhookController] ◄── Notificação de Sucesso (Aprovado) ◄──────┘
│
(Muda Status para PAGO) ──► [Atualiza Banco MySQL]

###  Segurança de Nível Comercial (Spring Security + JWT)
* **Autenticação Stateless:** Controle de sessão totalmente sem estado utilizando tokens **JWT (JSON Web Tokens)** criptografados com o algoritmo `HS256`.
* **Filtro Customizado Interceptador:** Filtro que estende `OncePerRequestFilter` para capturar, decodificar e injetar o contexto de autenticação do usuário a cada requisição na API.
* **Acesso Granular:** Separação rígida de rotas públicas de consulta de catálogo e rotas privadas protegidas que exigem validação cadastral.

###  Persistência de Dados e Integridade
* **Operações Atômicas:** Uso da anotação `@Transactional` para garantir que, caso ocorra qualquer erro de comunicação com o gateway de pagamentos, o banco de dados realize o *rollback* automático das tabelas de histórico.

---

##  Stack Tecnológica

* **Core:** Java 17, Spring Boot 3.x
* **Data:** Spring Data JPA, Hibernate, MySQL Driver
* **Security:** Spring Boot Starter Security, JJWT (Java JWT API)
* **Integration:** Stripe Java SDK

---

##  Configuração do Ambiente (`application.properties`)

```properties
# Nome da Aplicação
spring.application.name=api_ecommerce

# Configuração de Persistência Relacional (MySQL)
spring.datasource.url=jdbc:mysql://localhost:3306/meu_ecommerce
spring.datasource.username=seu_usuario_aqui
spring.datasource.password=${DB_PASSWORD:sua_senha_local_aqui}
spring.jpa.hibernate.ddl-auto=update

# Configurações Oficiais da Stripe API
stripe.api.key=${STRIPE_API_KEY:sk_test_sua_chave_secreta_aqui}
stripe.webhook.secret=${STRIPE_WEBHOOK_SECRET:whsec_seu_secret_aqui}

# Criptografia do Ecossistema de Segurança (JWT)
api.security.token.secret=${JWT_SECRET_KEY:SuaFraseSecretaComMaisDe32CaracteresParaOJWT}
```

---

##  Matriz de Endpoints (API REST)

| Módulo | Método | Endpoint | Permissão | Descrição |
| :--- | :--- | :--- | :--- | :--- |
| **Auth** | `POST` | `/api/auth/login` | **Público** | Valida credenciais e emite o token de acesso JWT. |
| **Catalog** | `GET` | `/api/produtos` | **Público** | Retorna o catálogo unificado de itens. |
| **Catalog** | `GET` | `/api/produtos-fisicos` | **Público** | Lista especificamente os produtos com controle de estoque. |
| **Catalog** | `POST` | `/api/produtos-fisicos` | **Autenticado** | Insere novos produtos físicos no estoque. |
| **Checkout** | `POST` | `/api/pedidos/checkout` | **Autenticado** | Processa o carrinho, salva no banco e gera o link do Stripe. |
| **Stripe** | `POST` | `/api/webhooks/stripe` | **Público** | Recebe confirmações do Stripe e atualiza status para PAGO. |

---

<p align="center">Desenvolvido com foco em boas práticas de engenharia de software e padrões de mercado RESTful.</p>
