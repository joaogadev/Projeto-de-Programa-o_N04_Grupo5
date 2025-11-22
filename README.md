# Projeto-de-Programa-o_N04_Grupo5

Um parágrafo da descrição do projeto vai aqui


### 📋 Pré-requisitos

De que coisas você precisa para instalar o software e como instalá-lo?

✅ Java 17 LTS ou superior  
✅ JavaFX (módulos: javafx-controls, javafx-fxml, javafx-graphics, javafx-base)  
✅ MySQL Server (versão 8 ou superior)  
✅ MySQL Connector/J (mysql-connector-j, versão 8.0.33+)  
✅ IDE recomendada: IntelliJ IDEA  
✅ Driver JDBC disponível no classpath  

### 🔧 Instalação

Execute no terminal do projeto
```
git clone https://github.com/joaogadev/Projeto-de-Programa-o_N04_Grupo5.git
```
Se estiver utilizando o intellij, execute as bibliotecas, seguindo o passo a passo
Vá em file -> Project Structure -> Modules -> Dependencies -> Clique no sinal de mais e adicione o MySQL connector e o jcalendar -> Marque a checkbox -> Apply\
<br>
<br>
Na classe 'src/repository/MyJDBC.java', no atributo password, dentro das aspas, altere para a sua password do MySQL WorkBench.
<br>
<br>
Utilize o script em 'src/resources/SCHEMA.sql' no seu MySQL WorkBench e rode-o. 
Após isso va até a classe 'src/Main.java' e rode o programa.


## 🛠️ Construído com

* Java 17
* JavaFX
* JavaCalendar
* JDBC
* MySQL Connector/J
* IntelliJ IDEA


## Funcionalidades

### Atualização de eventos
Quando o usuário clicka em um evento, o JTextArea é atualizado com as informações dos eventos baseado no dia clickado.
<br>
<br>
<img width="855" height="606" alt="Captura de tela 2025-11-22 180316" src="https://github.com/user-attachments/assets/eb324001-3f5c-4a40-976a-31fcf7043f50" />
<br>

### Relatórios
Função que opera apenas quando o usuário está cadastrado no sistema. Ela exibe um txt com as informações de eventos que o usuário visitou nos últimos 7 dias
<br>
<br>
<img width="729" height="603" alt="Captura de tela 2025-11-22 181539" src="https://github.com/user-attachments/assets/ced0cd00-aa31-46ef-9466-4d775b6e6e65" />

### Ativar Notificações
Esta função assim como a função de gerar relatório somente funciona caso um usuário esteja logado. Ela mostra ao usuário os eventos mais próximos em que ele esteja inscrito em um período de 24 horas
<br>
Primeiro ativamos as notificações 
<br>
<br>
<img width="847" height="616" alt="Captura de tela 2025-11-22 182025" src="https://github.com/user-attachments/assets/93d385e1-e01f-40c2-9f2e-561ad3ec3be4" />


E, após isso as notificações serão exibidas de forma automática sempre que o usuário abrir o sistema 
<br>
<br>
<img width="845" height="613" alt="Captura de tela 2025-11-22 182110" src="https://github.com/user-attachments/assets/231ccd31-d4cd-4c72-8c02-cf63de8bf4b2" />
<br>
<br>

## ✒️ Autores

Mencione todos aqueles que ajudaram a levantar o projeto desde o seu início

* **Abraao** - [Abraao](https://github.com/Abraao-works).
* **João Gabriel** - [Joao-Gabriel](https://github.com/joaogadev).
* **João Pedro Weber** - [João Pedro](https://github.com/JoaoPedroWeber).
* **João Guilherme** - [João Guilherme](https://github.com/joa576).
* **Antonio Calu** - [Antonio Calu](https://github.com/antoniocalu).
  
  
Você também pode ver a lista de todos os [colaboradores](https://github.com/joaogadev/Projeto-de-Programa-o_N04_Grupo5/graphs/contributors/) que participaram deste projeto.



