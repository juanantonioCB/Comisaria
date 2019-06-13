
create database Comisaria;
use Comisaria;
create table Sospechosos(
	id int auto_increment,
	nombre varchar(255),
	apellido1 varchar(255),
	apellido2 varchar(255),
	dni varchar(255),
	antecedentes varchar(255),
	hechos varchar(255),
	primary key(id)
	ON DELETE CASCADE
);

create table Fotos(
	id int auto_increment primary key,
	foto blob,
	idSospechoso int,
	foreign key (idSospechoso) references Sospechosos(id)
	ON DELETE CASCADE
)

create table Matriculas(
	id int auto_increment primary key,
	matricula varchar(255),
	idSospechoso int,
	foreign key (idSospechoso) references Sospechosos(id)
	ON DELETE CASCADE
);

create table Residencias(
	id int auto_increment primary key,
	idSospechoso int,
	residencia varchar(255),
	foreign key (idSospechoso) references Sospechosos(id)
	ON DELETE CASCADE
);

create table Telefonos(
	id int auto_increment primary key,
	idSospechoso int,
	telefono varchar(255),
	foreign key (idSospechoso) references Sospechosos(id)
	ON DELETE CASCADE
);

create table Acompañantes(
	id1 int,
	id2 int,
	primary key(id1,id2),
	foreign key (id1) references Sospechosos(id),
	foreign key (id2) references Sospechosos(id)
	ON DELETE CASCADE
);



create table emails(
	id int auto_increment primary key,
	idSospechoso int,
	email varchar(255),
	foreign key (idSospechoso) references Sospechosos(id)
	ON DELETE CASCADE
);