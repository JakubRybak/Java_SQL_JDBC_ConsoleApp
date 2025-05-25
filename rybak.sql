create database KLOCKI_36

create table HURTOWNIA_KLOCKOW(
	zestaw_id int Identity(1,1) not null,
	tytul varchar(70) not null,
	opis text not null,
	data_wydania date not null,
	cena_jednostkowa money not null,
	stan_magazynu int not null,
	wartosc_zapasow money not null
);
ALTER TABLE HURTOWNIA_KLOCKOW
ADD CONSTRAINT PK_HURTOWNIA_KLOCKOW PRIMARY KEY CLUSTERED (zestaw_id);


select * from HURTOWNIA_KLOCKOW;