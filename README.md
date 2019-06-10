# Trabalho de MC322

Link para o binder: [![Binder](https://mybinder.org/badge_logo.svg)](https://mybinder.org/v2/gh/gab-mafra/trabalho-mc322-parte-1/master?urlpath=lab)

Esse e o nosso trabalho final de MC322 - Programação Orientada a Objetos, ele consiste em uma interação de um médico e um paciente,
de tal forma que o médico, conhecendo as possíveis doenças e seus possíveis sintomas, possa realizar o diagnóstico certeiro desse paciente.

## Integrantes do grupo:

#### Parte 1:
	Gabriel de Souza Mafra                - RA: 197272
	Manuela Rafael Onofre de Souza        - RA: 202534

#### Parte 2:
	Gabriel de Souza Mafra                - RA: 197272
	Manuela Rafael Onofre de Souza        - RA: 202534
	Victor Toon de Araújo                 - RA: 225231
	Gabriel Batista Moura                 - RA: 216101 
	Augusto Piato Amstalden               - RA: 213364
	Daniel Cardoso Custodio de Oliveira   - RA: 169400

## Componetes

### Gráfico

Caminho no arquivo JAR - "Drauziumbi.jar/drauziumbi/Grafico.java"

Este componente cria dois gráficos a sua escolha, um de frequência de sintomas num dado conjunto de pacientes, o outro de frequência de doenças num dado conjunto de pacientes, utilizando da biblioteca do Jfree, e cria arquivos PNG para cada grafico.

*Métodos:*

* *construtorGraficoDoencas* - constrói um grafico de doenças e o torna visível
* *construtorGraficoSintomas* - constrói um gráfico de sintomas e o torna visível

*Observações:*

Esse componente é 100% generalizado, você pode adicionar qualquer sintoma e doença que ele mostrará todas elas no gráfico.
Esse componente recebe um vetor da classe "Patient", caso seu paciente não seja dessa classe, há duas opções, que seu paciente herde do nosso "Patient", caso eles sejam próximos ou que você crie um vetor com o nosso próprio Patient, que está no arquivo JAR.

*FAQ*

* Precisamos adicionar alguma biblioteca adicional?

    Não, o pacote do jfree ja está incluso no nosso arquivo JAR, basta incluí-lo no seu projeto.

### Protocolo de Urgência

Caminho no arquivo JAR - "Drauziumbi.jar/draziumbi/protocoloUrgencia.java" & "Drauziumbi.jar/draziumbi/MergeSort.java"

Este componente analisa os sintomas dos pacientes e os organiza para um atendimento utilizando os padrões de protocolo de urgência usados nos pronoto socorros do Brasil, utilizando um Merge Sort adaptado para o problema para a ordenação.

*Métodos:*

* *urgencia* - recebe a lista de sintomas do paciente e retorna sua urgência (0 - urgência baixa, 1 - urgência média, 2 - urgência alta, 3 - urgência máxima)
* *getListaSintomas* - a partir do vetor de sintomas com trues e falses de um paciente esse método retorna um vetor de strings com o nome de cada sintoma
* *listaAtndimento* - utilizando do Merge Sort, esse método recebe um vetor de pacientes e retorna um vetor ordenado por urgencia decressiva, com idade sendo o critério de desempate (3 - idoso, 1 - criança, 2 - adulto), se o empate persistir, é levado em conta quão paciente chegou primiro ao pronto socorro (a ordem em que eles se encontram no vetor original)

*Observações:*

Esse componente depende muito de cada sintoma no data set utilizado, portanto caso queira adicionar mais sintomas, basta modificar o método 'urgencia' adicionando o nome do sintoma e retornando a urgência associada. Além disso são necessários duas novas variaveis a classe Patient: idade e urgencia, para armazenar as informações para a ordenação.

*FAQ*

* Por quê Merge Sort?

    Como estamos ordenandos objetos complexos (e não apenas valores inteiros) precisamos de um sort estável, e de todos os sorts estáveis esse seria o mais eficiente.

* Precisamos de instalar algum pacote extra?

    Não, tanto a interface desse componente, quanto as classes protcoloUrgencia e o MergeSort estão no nosso pacote.

### Prontuário

Este componente serializa as informações de um paciente, incluindo Nome, CPF e o histórico de doenças, em ordem e com um identificador de frquência de cada doença, podendo facilmente identificar qual a doença mais recorrente do paciente.

*Métodos:*

* *serializar* - esse método serializa as informações do paciente criando seu prontuário médico com o nome "<nome>_<CPF>.txt" em uma pasta chamada "prontuarios"
* *maisRecorrente* - retorna qual a doeça mais recorrente no prontuário de um paciente
* *print* - imprime no terminal o prontuário do paciente

*Observações:*

Esse componente possui duas novas variaveis na classe Patient: nome e cpf, para a identificação do prontuário.
O paciente, quando criado, tem os campos de nome e cpf vazios, lembre de utilizar os métodos setCPF e setNome para colocar os mesmos.

*FAQ*

* Precisamos de alguma biblioteca adicional?

    Não, tudo esta incluso no arquivo JAR.

* Preciso criar a pasta "prontuarios"?

    Sim, esta pasta precisa estar no diretório do projeto.

## Como importar nossos componentes?

### Tutorial do eclipse

Com o arquivo JAR ("Drauviumbi.jar") baixado, clique com o botão direito no projeto > propriedades > Java Bulid Path > Libraries > Classpath > Add external JARs > escolha o arquivo > Apply e Ok.

No seu codigo, utilize 'import drauziumbi.*'

### Tutorial do IntelliJ

Ctrl + Shift + Alt + S (Command + ; no Mac) > Libraries > + no canto superior esquerdo > Java > escolha o arquivo > Apply e Ok.

No seu codigo, utilize 'import drauziumbi.*'
