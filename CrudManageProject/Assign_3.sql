-- Mange-til-en-til-mange-eksempel forelesning tirsdag 22. mars 2021.

-- MERK!!! DROP SCHEMA ... CASCADE sletter alt !!!
DROP SCHEMA IF EXISTS Assign_3 CASCADE;
CREATE SCHEMA Assign_3;
SET search_path TO Assign_3;

CREATE TABLE Ansatt
(
  Id         	 SERIAL,
  Initialer  	 VARCHAR(4),
  Fornavn    	 VARCHAR(30),
  Etternavn  	 VARCHAR(30),
  Stilling	 	 VARCHAR(30),
  AnsettelseDato Date,
  Lonn		 	 DECIMAL(10, 2),
  Avdeling_Id	 INTEGER NOT NULL,
  CONSTRAINT Ansatt_PK PRIMARY KEY (Id)
  Constraint avdelingid_fk FOREIGN KEY(Avdeling_Id) REFERENCES assign_3.Avdeling(Id) 
);

CREATE TABLE Prosjekt
(
  Id          SERIAL,
  Navn        VARCHAR(30),
  Beskrivelse VARCHAR(255),
  CONSTRAINT Prosjekt_PK PRIMARY KEY (Id)
);

CREATE TABLE Avdeling
(
  Id         SERIAL,
  Navn       VARCHAR(30),
  Sjef		 INTEGER NOT NULL,
  CONSTRAINT Avdeling_PK PRIMARY KEY (Id),
  CONSTRAINT Ansatt_FK FOREIGN KEY (Sjef) REFERENCES Ansatt(Id)

);

-- Koblingstabellen har i dette tilfellet egne data (timer)
-- Blir da en egen selvstendig entitet.
-- Nå med surrugatnøkkel for å forenkle kodingen.
CREATE TABLE Prosjektdeltagelse
(
  Prosjektdeltagelse_Id SERIAL,
  Ansatt_Id INTEGER,
  Prosjekt_Id INTEGER,
  Timer     INTEGER,
  CONSTRAINT Prosjektdeltagelse_PK PRIMARY KEY (Prosjektdeltagelse_Id),
  CONSTRAINT AnsattProsjekt_Unik UNIQUE (Ansatt_Id, Prosjekt_Id),
  CONSTRAINT Ansatt_FK FOREIGN KEY (Ansatt_Id) REFERENCES Ansatt(Id),
  CONSTRAINT Prosjekt_FK FOREIGN KEY (Prosjekt_Id) REFERENCES Prosjekt(Id)  
);

INSERT INTO
  Assign_3.Ansatt(Initialer,Fornavn, Etternavn,Stilling,AnsettelseDato, Lonn, Avdeling_Id)
VALUES
  ('TEST','Arne', 'Arnesen','Avisbud','2005-08-06',1230.30,1),
  ('BrBr','Brit', 'Britsen','Avisbud','2005-08-06',1230,1),
  ('CACA','Carl', 'Carlsen','Avisbud','2005-08-06',1230,1),
  ('DoDU','Donald', 'Duck', 'Avisbud','2005-08-06',1230,1);

INSERT INTO
  Assign_3.Prosjekt(Navn,Beskrivelse)
VALUES
  ('Trivselsprosjektet','bare for moro!'),
  ('Synergiprosjektet','Tester teamwork!'),
  ('Utviklingsprosjektet','Nye gode prosjekter for fremtiden!');

INSERT INTO
  Assign_3.Prosjektdeltagelse(Ansatt_Id, Prosjekt_Id, Timer)
VALUES
  (1, 1, 50),
  (2, 1, 100),
  (2, 2, 150),
  (3, 1, 200),
  (3, 2, 250),
  (4, 1, 300);
  
INSERT INTO
  Assign_3.Avdeling(Id,Navn,Sjef)
VALUES
  (1,'Utvikling',1),
  (2,'Marketing',2),
  (3,'Logistikk',3);


  