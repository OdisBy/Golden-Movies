# Golden Tomatoes

Você já quis buscar por filmes aleatórios mas sempre cai na mesma lista padrão de filmes?

 O Golden Tomatoes resolve isso. Oferecemos uma lista aleatória que você pode buscar por filmes completamente aleatórios!

Mas e se você eu quiser salvar para assistir o filme só mais tarde? Não se preocupe, o Golden Tomatoes tem um sistema que permita que você salve seus filmes aleatórios favoritos localmente, e você ainda receberá uma notificação lembrando você de assistir!



### Tech

O app foi feito em Compose, seguindo o padrão Clean Architecture e MVVM.

O app foi separado em módulos conforme recomendação do Android, utilizando o :app :feature :core e :data

##### Sistema de modularização:

- :app
  - Ponto de entrada para o aplicativo em dispositivos móveis Android
  - Configurações gerais, Activity principal e NavHost
- :feature: Módulos responsáveis pelas partes do app, contém tanto o UI quanto o bussines logic atráves dos use cases
  - :feature:home
    - Contém a tela de home e o Search Bar

  - :feature:details
    - Contém a tela de detalhes

  - :feature:movielist

    - Contém as listas de filmes, que se pode acessar pelo "Descobrir novos filmes" quanto pelos "Filmes agendados"
- :data: Módulo responsável por conter as lógicas de Repository
  - :data:data: Contém as principais configurações dos Repositories e os principais módulos. Contém também as interfaces dos Repositórios que serão consumidas pelos uses cases nos módulos de feature
  - :data:remote: Contém as implementação dos repositories que necessitam de conexão externa
  - :data:local: Contém as implementações dos respositories que necessitam de conexão internal (Database)
- :core: Módulo com recursos uteis para todos os módulos
  - :core:ui: Módulo com principais recursos de UI que são utilizados em vários módulos
  - :core:network: Módulo com principais recursos para acesso a network
- :work-managers: Módulo responsável por lidar com os WorkManagers do projeto, no nosso caso um WorkManager para notificações e um para rodar uma action em um repository
- testutils: Módulo com classes úteis para testes em todo o projeto

###### Libs Principais

- Compose, Retrofit, Android Geral, Timber, Hilt, Coil, Chucker





### Arquitetura + System Design

Esses arquivos foram feitos em um estágio inicial, antes de iniciar realmente, então teve algumas alterações conforme necessário.

Pode encontrar tudo nesse link do excalidraw https://excalidraw.com/#json=CDJ4n7U7t9gt8OqCA9xvk,Qwcx9a921ordHBETYh2C1Q

Pode encontrar algumas escritas também nesse projeto do Notion https://www.notion.so/Golden-Tomatoes-2bd879e700c341b2b034f6f4d66ba732?pvs=4

Postman consumindo a API: https://www.postman.com/grey-comet-197365/workspace/golden-tomatoes




