
## :books: Software documentation information

:sparkles: __Neste diretório contém a documentação de software para o gerenciamento de projeto com as seguintes especificações:__

1 - Nota de versão:

 - *__Padronização:__* Será utilizado o versionamento semântico, que utiliza um conjunto de padrões para nomear versões de software de forma que seja compreensível entender qual mudança foi realizada. Este sistema utilizará um formato como "programa.A.B.C", onde A, B e C são números inteiros que representam a versão do sistema, permitindo uma comunicação clara das mudanças e da necessidade de atualização para os usuários.

2 - Construção do banco de dados:

 - *__Análise de Requisitos:__* Esta é a fase inicial, onde o objetivo do banco de dados é identificado. É crucial entender a finalidade do banco de dados para informar as escolhas durante todo o processo de criação. Isso pode envolver entrevistas com usuários, análise de formulários corporativos e revisão de sistemas de dados existentes.
 - *__Estrutura de Banco de Dados:__* A estrutura do banco de dados é estabelecida, onde os dados relacionados são agrupados em tabelas. Cada tabela consiste em linhas (registros) e colunas (campos ou atributos), permitindo a organização dos dados de maneira semelhante a uma planilha. As tabelas são criadas para cada tipo de entidade, como produtos, vendas, clientes e pedidos.
 - *__Criação de Relações entre Entidades:__* As relações entre as tabelas são analisadas para entender como os dados estão interligados. As relações podem ser uma para uma, uma para muitas ou muitas para muitas, e cada uma tem implicações específicas na estrutura do banco de dados.
 - *__Normalização de Banco de Dados:__* Após ter um design preliminar, as regras de normalização são aplicadas para garantir que as tabelas estejam estruturadas corretamente. A normalização ajuda a eliminar dados redundantes, mantém a integridade dos dados e oferece acesso eficiente aos dados.
 - *__Regras de Integridade de Dados:__* O banco de dados é configurado para validar os dados de acordo com as regras apropriadas, garantindo que os dados sejam consistentes e precisos. Isso inclui regras de integridade da entidade, referencial e lógica de negócios.
 - *__Propriedades Estendidas:__* Após o layout básico, o banco de dados pode ser refinado com propriedades estendidas, como texto instrucional, máscaras de entrada e regras de formatação, para garantir uma apresentação consistente dos dados.
 - *__Dados Multidimensionais:__* Para bancos de dados OLAP, que favorecem a análise e a elaboração de relatórios, pode ser necessário criar tabelas multidimensionais para acessar várias dimensões de um único tipo de dados.
 - *__Atribuição de Tipos de Dados:__* Cada coluna em uma tabela deve ter um tipo de dados apropriado atribuído, como CHAR, VARCHAR, INT, FLOAT, DOUBLE, ou BLOB, para garantir a consistência e a eficiência dos dados.
 - *__Criação de Diagramas Entidade-Relacionamento (ER):__* Um diagrama ER é criado para fornecer uma visão geral do banco de dados, mostrando as tabelas e as relações entre elas. Isso ajuda a visualizar a estrutura do banco de dados antes de sua implementação.
 - *__Implementação e Testes:__* Finalmente, a estrutura de dados lógicos e físicos é implementada na linguagem de definição de dados suportada pelo sistema de gestão de banco de dados escolhido. Após a implementação, o banco de dados é testado para garantir que ele atenda aos requisitos e funcione conforme esperado.


