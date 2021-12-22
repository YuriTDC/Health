Você está recebendo um exercício para avaliarmos sua habilidade em transformar um requisito de negócio em código legível e funcional. Esperamos que a solução tenha a qualidade que você julgue que esteja pronto para entrar em produção. Você é livre para utilizar a linguagem de programação que preferir. Você pode usar qualquer framework, bibliotecas e ferramentas que achar mais adequados para realizar o problema proposto. Para o versionamento pedimos que seja usado o git, apenas na máquina local, sem fazer o upload do código em nenhum serviço (Github, Bitbucket…). Mais a frente há algumas instruções sobre como compartilhar o código conosco dessa forma.

Se você tiver alguma dúvida sobre o que deve ser feito no problema, você pode entrar em contato com a gente ou você pode decidir por algum caminho que achar mais adequado.

Para conseguirmos avaliar a solução apresentada incluir na raiz do projeto um arquivo README com instruções de como executar a solução, bem como instalar todas as dependências necessárias.
Nesse mesmo arquivo incluir decisões que tenha tomado sobre a solução adotada.

Exemplo do README:
```
Requisitos
==========
Para esse projeto é necessário instalar o node versão 14 - https://nodejs.org/en/download/

Para executar
=============
npm run start

Para fazer ação X 
curl http://localhost:3000/X

Para fazer ação Y 
curl http://localhost:3000/Y


Para rodar os testes
npm run test


Decisões tomadas
================

- No problema não estava claro como deveriam ser executadas as operações, decidi por usar uma API REST.
- Optei por criar alguns personagens já no momento da iniciação da aplicação, para poder testar a funcionalidade de lista sem precisar criar novos personagens
```

Um dos critérios que vamos avaliar é como são os commits. Abaixo estão as instruções de como enviar a sua solução.

Para enviar o seu projeto do git você deve gerar um [git bundle|https://git-scm.com/docs/git-bundle]. O nome do arquivo bundle deve ser
`nome_sobrenome.bundle`. Para gerar o bundle basta executar o seguinte comando

```
git bundle create nome_sobrenome.bundle HEAD <nome_branch>
```

Por exemplo, se os commits estiverem na branch main:
```
git bundle create nome_sobrenome.bundle HEAD main
```

Depois basta enviar o arquivo para o e-mail da pessoa que tiver te enviado o Take Home Assignment

Se você quiser conferir que o bundle foi gerado com sucesso, basta copiá-lo para um nova pasta e executar
```
git clone nome_sobrenome.bundle meu_projeto
```

Uma cópia do seu repositório vai ser feita na pasta meu_projeto.

Qualquer dúvida não hesite em entrar em contato.