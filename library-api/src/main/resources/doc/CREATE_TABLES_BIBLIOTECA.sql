CREATE TABLE "usuarios" (
	"id" INTEGER NOT NULL UNIQUE GENERATED BY DEFAULT AS IDENTITY,
	"nome" VARCHAR(255) NOT NULL,
	"email" VARCHAR(255) NOT NULL,
	"data_cadastro" TIMESTAMP NOT NULL,
	"telefone" VARCHAR(11) NOT NULL,
	PRIMARY KEY("id")
);


CREATE TABLE "livros" (
	"id" INTEGER NOT NULL UNIQUE GENERATED BY DEFAULT AS IDENTITY,
	"titulo" VARCHAR(1000) NOT NULL,
	"autor" VARCHAR(255) NOT NULL,
	"isbn" VARCHAR(50) NOT NULL,
	"data_publicacao" DATE NOT NULL,
	"categoria" VARCHAR(100) NOT NULL,
	PRIMARY KEY("id")
);


CREATE TABLE "emprestimos" (
    "id" INTEGER NOT NULL UNIQUE GENERATED BY DEFAULT AS IDENTITY,
    "usuario_id" INTEGER NOT NULL,
    "livro_id" INTEGER NOT NULL,
    "data_emprestimo" TIMESTAMP NOT NULL,
    "data_devolucao" TIMESTAMP NOT NULL,
    "status" VARCHAR(50) NOT NULL,
    PRIMARY KEY("id"),
    FOREIGN KEY("usuario_id") REFERENCES "usuarios"("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY("livro_id") REFERENCES "livros"("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);
