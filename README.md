# ES-2Sem-2021-Grupo47
Projeto Engenharia de Software 2ºSemestre Grupo 47

Elementos da equipa:             
  Alin Cojocaru  |  Nº 87745  | Turma IC5        
  Fernando Cruz  |  Nº 88725  | Turma IC2           
  Guy Turpin     |  Nº 87594  | Turma IC5        
  Rita Silva     |  Nº 87449  | Turma IC3     
  Tomás Mendes   |  Nº 82425  | Turma IC3   
  Vasco Fontoura |  Nº 88724  | Turma IC1           
  
Breve explicação das funcionalidades:           
  O nosso projeto inclui a possibilidade de:        
    1. Escolha de um projeto        
    2. Escolha de caminho para guardar ficheiro excel gerado     
    3. Extração de métricas para esse projeto           
    4. Cálculo,importação e visualização das características do projeto (número de linhas, número de classes, número de métodos e número de pacotes)           
    5. Geração de um ficheiro excel e visualização gráfica desse ficheiro       
    6. Escolha de uma regra           
    7. Alteração de uma regra         
    8. Guardar histórico de regras de forma persistente         
    9. Abrir histórico de regras       
    10. Apagar histórico de regras        
    11. Detetar code smells segundo a regra e projeto escolhidos     
    12. Visualização da deteção dos code smells          
    13. Avaliação e visualização da qualidade de deteção de code smells      
    14. Criação de um PieChart para ilustrar qualidade de deteção de code smells         

  Para além das funcionalidades principais referidas, demos alguma atenção a certos pormenores como:            
    1. Criação de um menu 'Ajuda' para auxiliar a utilização das GUIs                        
    2. Criação de um menu 'Ajuda' para explicar o cálculo das métricas               
    3. Impedimento de escolha de regras contraditórias, como por exemplo escolha de de uma regra com a métrica LOC_method e LOC_class            
    4. Impedimento de criação de regras com campos vazios ou mal preenchidos              
    5. Mensagens de erro ao carregar em botões quando a informação é insuficiente (por exemplo: carregar em 'Detetar code smells' sem projeto ou regra selecionados)       
    6. Possibilidade de utilização da aplicação para vários projetos diferentes simultâneamente     
    7. Nome da interface gráfica para deteção de code smells é alterado consoante o code smell que foi escolhido nas regras


Como executar a aplicação:
  1. Pode fazer download do projeto java dado neste repositório e executar a classe Main do pacote G47.Grupo47 no eclipse      
  2. Outra alternativa será apenas fazer download do ficheiro .jar presente neste repositório, mais precisamente associada à tag: CodeQualityAssessor-1.0, ao abrir o ficheiro terá de ter instalado o java devolpment kit (jdk).

Javadoc:
  Foram realizados comentários Javadoc para melhor compreensão do projeto, para tal basta aceder aos ficheiros presentes em: Grupo47 -> doc

Cálculo de métricas e características:
  O cálculo utilizado para calcular métricas e característica está explicado no menu 'Ajuda' na interface gráfica da aplicação

    
