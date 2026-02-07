🏗️ Estrutura de Entidades

# O sistema é dividido em grandes domínios de infraestrutura:

- Usuários (Users): Gerencia a identidade e o plano (Pro/Free).

- Sistema de Arquivos (FileSystem):

- Folder: Estrutura recursiva que permite módulos dentro de módulos. Armazena metadados de progresso.

- Document: Representa o PDF físico no Storage (S3). Possui a flag vectorized, indicando se a IA já processou o conteúdo.

## Avaliação (Proofs & Questions):

- Proof: O "Simulado". Agrega uma lista de questões geradas.

- Question (Abstract): Utiliza a estratégia de Single Table Inheritance.

- ClosedQuestion: Múltipla escolha com OptionQuestionEntity.

- OpenQuestion: Questões dissertativas com gabarito de IA (rubrica).

- QuestionSource: Embeddable que vincula cada questão ao trecho exato do PDF original (Página e Excerpt).

## 🔄 Fluxo de Dados do Usuário

1. Ingestão e Organização

- O usuário faz upload de um PDF.

- O sistema cria um DocumentEntity vinculado a uma FolderEntity.

- O backend dispara um evento assíncrono para Vetorização (Processamento de IA para busca semântica).

2. Seleção e Contexto

- No Dashboard do Módulo ou Sidebar, o usuário seleciona um ou mais DocumentEntity.

- O frontend envia uma lista de IDs de documentos para o comando de criação de prova.

3. Geração de Prova (O "Cérebro")

- O Domain Service orquestra a chamada à IA (LLM).

- A IA recebe o conteúdo dos documentos e gera um JSON estruturado.

- O adaptador de banco de dados persiste a ProofEntity vinculando-a aos DocumentEntity originais através da tabela de associação proof_source_documents.

4. Execução e Feedback

- O usuário responde as questões.

- Em ClosedQuestions, a correção é automática via correct_option_id.

- Em OpenQuestions, a resposta do usuário é enviada para a IA comparar com a modelAnswer e a rubric salvas na entidade, gerando o studentScore.

🛠️ Tecnologias Utilizadas

Java 21 + Quarkus: Core do sistema.

Hibernate Panache: Facilita o acesso a dados.

PostgreSQL: Com suporte a campos JSONB para flexibilidade nos modelos de IA.

S3: Armazenamento dos arquivos PDF originais.
