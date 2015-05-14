#Projeto Transparência
Olá!

Para implantar este projeto, você vai precisar:
	1. Muita vontade de fazer deste mundo um lugar melhor. E a Transparência ajuda nisso;
	2. Java 8;
	3. Eclipse Luna 4.4;
	4. Wildfly 8.1;
	5. MySql Workbench 6.2;

Obs.: Essas foram as versões que utilizamos e funcionou. Pode ser que outras versões da mesma ferramenta (ou até outras ferramentas) funcionem também :)

Feito o mise en place, você deverá configurar as ferramentas. Para isso tem um tutorial muito bom aqui: https://github.com/nemo-ufes/nemo-utils/wiki

Agora, crie uma nova pasta no diretório de Workspaces do Eclipse que você já configurou durante o tutorial, baixe os arquivos do GitHub para este diretório e, no Eclipse, vá em File->Import...->General->Existing Projects into Workspaces e siga o Wizard.

Depois, você deve criar um novo esquema no MySql, chamado transparencia. Crie, também, um usuário chamado 'transparencia', com senha 'transparencia' e poderes para ler e escrever dados neste banco. Se quiser mudar a senha e nome do usuário, vá em frente, só lembre de fazer as devidas alteraçõs nos arquivos de configuração do JPA e correlatos.

Finalmente, execute o script dados.sql neste banco que você acabou de criar. Estes dados foram retirados do Portal da Transparência do Governo do Estado do Espírito Santo, mas alguns registros foram excluídos para garantir a integridade referencial e possibilitar a inclusão de chaves, já que algumas se repetiam. Logo os dados possuem aproximadamente 91% de simetria com os valores disponibilizados pelo Governo.

Agora basta fazer o deploy e, se não tiver erros, navegar com sangue nos olhos para conhecer bem esses dados.






