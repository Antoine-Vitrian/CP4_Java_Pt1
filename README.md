# CP4 — DAO, Factory e Singleton (Jogadores)

Aplicação Java da disciplina Domain Driven Design with Java (FIAP) que integra os padrões **DAO**, **Factory** e **Singleton** em um CRUD de jogadores persistido no Oracle da FIAP.

## Entidade escolhida

**Jogador**, com os atributos:

| Coluna   | Tipo          | Descrição              |
|----------|---------------|------------------------|
| ID       | NUMBER (PK)   | Identificador único    |
| NOME     | VARCHAR2(100) | Nome do jogador        |
| POSICAO  | VARCHAR2(50)  | Posição em campo       |
| NUMERO   | NUMBER(3)     | Número da camisa       |
| CLUBE    | VARCHAR2(80)  | Clube atual            |

## Estrutura de pacotes

```
com.fiap.model       → entidade Jogador
com.fiap.singleton   → ConexaoSingleton (uma única Connection)
com.fiap.dao         → JogadorDAO (interface) e JogadorDAOImpl (CRUD)
com.fiap.factory     → DAOFactory (cria o DAO e injeta a conexão)
com.fiap.view        → Main (demonstra o CRUD integrado)
```

A Factory obtém a conexão pelo Singleton e entrega um `JogadorDAO` pronto para uso. O DAO implementa: salvar, buscar por id, listar todos, atualizar e excluir.

## Configuração

1. Edite `src/main/java/com/fiap/singleton/ConexaoSingleton.java` e troque `RMXXXXXX` e `XXXXXX` pelo seu RM e senha do Oracle FIAP.
2. O driver JDBC está em `lib/jdbc17.jar`.
3. A tabela `JOGADOR` e a sequence `SEQ_JOGADOR` são criadas automaticamente na primeira conexão. O script manual está em `sql/jogador.sql`.

## Como executar

No IntelliJ, marque `lib/jdbc17.jar` como dependência do módulo e execute `com.fiap.view.Main`.

Pelo terminal (Windows), a partir da raiz do projeto:

```powershell
javac -encoding UTF-8 -cp lib/jdbc17.jar -d out (Get-ChildItem -Recurse src/main/java/*.java).FullName
java -cp "out;lib/jdbc17.jar" com.fiap.view.Main
```

Para rodar o teste Java do CRUD:

```powershell
javac -encoding UTF-8 -cp "out;lib/jdbc17.jar" -d out src/test/java/com/fiap/dao/JogadorDAOImplTest.java
java -cp "out;lib/jdbc17.jar" com.fiap.dao.JogadorDAOImplTest
```

É necessário estar na rede/VPN da FIAP para alcançar `oracle.fiap.com.br`.
