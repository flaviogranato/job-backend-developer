
## O desafio

Como você pode ver, nosso maior desafio está na manutenção e otimização de aplicações que estejam prontas para atender um altíssimo volume de dados e transações, por este motivo, todos os membros da nossa equipe estão altamente comprometidos com estes objetivos, de robustez, escalabilidade e performance, e é exatamente isso que esperamos de você através da resolução destes dois desafios:

1) Imagine que hoje tenhamos um sistema de login e perfis de usuários. O sistema conta com mais de 10 milhões de usuários, sendo que temos um acesso concorrente de cerca de 5 mil usuários. Hoje a tela inicial do sistema se encontra muito lenta. Nessa tela é feita uma consulta no banco de dados para pegar as informações do usuário e exibi-las de forma personalizada. Quando há um pico de logins simultâneos, o carregamento desta tela fica demasiadamente lento. Na sua visão, como poderíamos iniciar a busca pelo problema, e que tipo de melhoria poderia ser feita?

Hoje em dia é adotado muito o _cache_ como uma forma de acelerar o acesso aos dados, aplicações como memcached e redis são muito utilizados. Outra forma 
mas que consideramos muito onerosa pelo lado financeiro é o aumento de poder de processamento da máquina, aumentando processador e/ou velocidade de disco.

Utilizar o _cache_ na forma dentro da aplicação ou em um servidor dedicado nos permite muitas vantagens em relação
ao aumento de poder de processamento da máquina, pois antes de precisar aumentar o processamento podemos
replicar nossos dados de diversas formas, inclusive deixando o dado mais perto do destino final utilizando
as chamadas _cloud computing_. 

A solução escolhida para este desafio foi o Redis, por ser de fácil manipulação e visualização dos dados
bem como já ter uma grande base de usuários.

2) Com base no problema anterior, gostaríamos que você codificasse um novo sistema de login para muitos usuários simultâneos e carregamento da tela inicial. Lembre-se que é um sistema web então teremos conteúdo estático e dinâmico. Leve em consideração também que na empresa existe um outro sistema que também requisitará os dados dos usuários, portanto, este sistema deve expor as informações para este outro sistema de alguma maneira.


Utilizei docker e docker-compose para montar a infraestrutura do projeto e também criei um makefile para poder
ajudar na criação da imagem docker.

Portanto deve-se seguir os passos abaixo para poder executar o projeto:

 - TODOS os comandos devem ser executados no diretório do projeto.
 - executar `make getver` para identificar a versão do git com a qual será criado a imagem docker
   
   Ex.: `flavio@jupiter ~/projetos/job-backend-developer feature/writing-reademe-file : make getver 
        GIT_COMMIT=1.0.0-4-gab74ce-dirty`
    
   O conteúdo de *GIT_COMMIT* deverá substituir a tag da imagem docker do service application no docker-compose.yml
   Ex.: image: `login:1.0.0-4-gab74ce-dirty`
 - Executar `make build` e aguardar o término da execução.
 - Executar `docker-compose up` e poderá acessar no navegador a url `http://localhost/login` 
 ou a api url `http://localhost/users` ou `http://localhost/user/1`, lembro que as credenciais estão no 
 arquivo `ini.sql` no diretório do migrations do projeto.