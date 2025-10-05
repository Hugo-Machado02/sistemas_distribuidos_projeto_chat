## 🚀 1º Projeto - Aula 2: Chatbot 🚀
Implementação de um serviço de chatbot assíncrono que oferece comunicação ininterrupta. A aplicação foi desenvolvida com um robusto mecanismo de persistência de dados, responsável por:
- Armazenamento de Histórico: Salvar a totalidade das mensagens trocadas em todas as interações.
- Gestão de Usuários: Manter registros de metadados do usuário (como nome e identificação) para rastreamento e personalização.


## 🏋️Processo de Desenvolvimento:
- ☕ Processo
    - **Reuniões:** Encontros essenciais realizados para coletar requisitos funcionais e não funcionais do chatbot. Discussões aprofundadas sobre a seleção de tecnologias e sessões de desenvolvimento colaborativo para o avanço contínuo do projeto.
    - **Andamento do Projeto:** Utilizando a metodologia ágil ```Kanban``` pelo site [Trello](https://trello.com/)


## 📂 Estrutura:
- 📂 Backend-Processor: Irá processar as mensagens e Salvar o histórico. Também tem como função entregar todo o histórico para novos usuários (Comunica com o Backend-gateway).
- 📂 Backend-Gateway: Vai ser o responsável pela comunicação entre o Processor (Comunicação via Server Socket TCP) e entre o Frontend (Comunicação via WebSocket). Ele também é o responsável pela lógica de sessões conectadas e de enviar os dados para serem salvos.
- 📂 Frontend: Responsável pela comunicação com o Backend-Gateway e por mostrar as interações na tela do usuário.


## ⚙️Funcionamento:
1. Com os serviços baixados siga o passo a passo abaixo
2. Colete o IP do servidor que irá ficar com o serviço **Backend-Processor**
3. Acesse o arquivo MessageController.java dentro do serviço **Backend-Gateway** (Caminho: Backend-Gateway\src\main\java\com\projeto_01_gateway\chat\gateway\MessageController.java)
4. Altere a variavel ```private final String ip = "localhost"``` para ```private final String ip = "IP_do_servico_processor"```
5. Colete o IP do servidor que irá ficar com o serviço **Backend-Gateway**
6. acesse o arquivo script.js dentro do serviço **Frontend** (Caminho: frontend\public\static\script.js)
7. Altere o IP da String na linha 19, ```ws = new WebSocket("ws://localhost:6082/chat");``` para ```ws = new WebSocket("ws://IP_do_servico_gateway:6082/chat");```
8. Abra o terminal do servidor do **Backend-Processor** e navege até sua pasta: Backend-Processor/ e digitar o seguinte comando ```mvn spring-boot:run```
9. Abra o terminal do servidor do **Backend-Processor** e navege até sua pasta: Backend-Gateway/ e digitar o seguinte comando ```mvn spring-boot:run```
10. Abrir o terminal do servidor do **Frontend** e navege até sua pasta: Frontend/ e digitar o seguinte comando ```npm start```
11. Dessa forma basta acessar o link que o node retornou que o sistema irá ser acessado corretamente.


## 🚀 Execução:
1. Tela para entrar com o nome de usuário
<img width="1867" height="916" alt="Chat 01" src="https://github.com/user-attachments/assets/96ab2c2e-d70a-4d89-83b6-90f35bc67c97" />

2. Usuário logado _(Se houver histórico, será puxado)_
<img width="1871" height="914" alt="Chat 02" src="https://github.com/user-attachments/assets/8b606e1a-e2f7-482a-8601-55ee2a395531" />

3. Mensagens Enviadas por várias Partes
<img width="1875" height="915" alt="Chat 03" src="https://github.com/user-attachments/assets/8f40c48f-ce15-4b62-a0d2-e2b285edd86b" />

4. Usuário Logando com histórico de mensagens já registrado
<img width="1863" height="912" alt="Chat 04" src="https://github.com/user-attachments/assets/0868712a-ed6a-4273-905d-de6b2bbfa5e4" />


## 💡Possíveis melhorias futuras:
1. Criação de Grupos
2. Restrição de Acessos
3. Edição de Mensagens


## 💻Tecnologias Utilizadas:
[![My Skills](https://skillicons.dev/icons?i=java,vscode,html,css,js,git,github)](https://skillicons.dev)


## 📜Licença de Uso:
Este é um projeto educacional desenvolvido com o intuito de promover a aprendizagem e colaboração. Sinta-se à vontade para explorar, modificar e contribuir!


## ✒️Autores:
- [Hugo M. M. Silva](https://github.com/Hugo-Machado02)
- [Joselio F. S. Júnior](https://github.com/JoselioJr)
- [Shayra Kelly E. Silva](https://github.com/ShayraKelly)
