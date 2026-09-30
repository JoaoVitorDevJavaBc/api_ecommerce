#  Java Full-Stack E-Commerce API

<p align="center">
  <img src="https://shields.io" alt="Java" />
  <img src="https://shields.io" alt="Spring Boot" />
  <img src="https://shields.io" alt="MySQL" />
  <img src="https://shields.io" alt="Stripe" />
  <img src="https://shields.io" alt="JWT" />
</p>

An API RESTful de alta performance desenvolvida para gerenciar o ecossistema completo de um e-commerce moderno. O sistema unifica o controle de inventário físico, persistência relacional automatizada, segurança baseada em tokens e um fluxo completo de pagamentos com conciliação automática.

---

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

##  Funcionalidades de Destaque

###  Gateway de Pagamento Inteligente (Stripe)
* **Carrinho de Compras Dinâmico:** Processamento de múltiplos itens, preços e quantidades em lote em uma única requisição.
* **Rastreabilidade por Metadata:** Vinculação do ID do pedido gerado no MySQL diretamente aos metadados da sessão do Stripe.
* **Escuta Ativa (Webhooks):** Endpoint blindado com verificação de assinatura digital (`Stripe-Signature`), responsável por escutar o evento `checkout.session.completed` e realizar a baixa do pedido de forma assíncrona.

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
