DROP TABLE IF EXISTS LaptopComputer, Document, Book, ComicBook, Dictionnary, Library, Magazine, Material, Member, Volume;

-- Creation of LaptopComputer table
CREATE TABLE IF NOT EXISTS LaptopComputer
(
    id SERIAL PRIMARY KEY,
    brand VARCHAR(50),
    available BOOLEAN,
    os VARCHAR(10) CHECK (os IN ('LINUX', 'WINDOWS')),
    idMaterial INT NOT NULL,
    idMember INT NOT NULL
);

CREATE TABLE IF NOT EXISTS Member(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    first_name VARCHAR(50),
    status VARCHAR(10) CHECK (status IN ('STUDENT', 'TEACHER')),
    idLibrary INT
);

CREATE TABLE IF NOT EXISTS Library(
    id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS Document
(
    id SERIAL PRIMARY KEY,
    title VARCHAR,
    idLibrary INT
);

CREATE TABLE IF NOT EXISTS Material(
    id SERIAL PRIMARY KEY,
    outOfOrder BOOLEAN,
    idLibrary INT
);

CREATE TABLE IF NOT EXISTS Volume(
    id SERIAL PRIMARY KEY,
    author VARCHAR(50),
    idDocument INT NOT NULL
);

CREATE TABLE IF NOT EXISTS Magazine(
    id SERIAL PRIMARY KEY,
    number INT NOT NULL,
    idDocument INT NOT NULL
);

CREATE TABLE IF NOT EXISTS Dictionnary(
    id SERIAL PRIMARY KEY,
    theme VARCHAR(50),
    idVolume INT NOT NULL
);

CREATE TABLE IF NOT EXISTS ComicBook(
    id SERIAL PRIMARY KEY,
    designer VARCHAR(50),
    idVolume INT NOT NULL
);

CREATE TABLE IF NOT EXISTS Book(
     id SERIAL PRIMARY KEY,
     available BOOLEAN,
     idVolume INT NOT NULL
);

ALTER TABLE LaptopComputer
    ADD CONSTRAINT FK_idLP_Material FOREIGN KEY (idMaterial)
        REFERENCES Material(id);

ALTER TABLE LaptopComputer
    ADD CONSTRAINT FK_idLP_Member FOREIGN KEY (idMember)
        REFERENCES Member(id);

ALTER TABLE Member
    ADD CONSTRAINT FK_Member_Library FOREIGN KEY (idLibrary)
        REFERENCES Library(id);

ALTER TABLE Document
    ADD CONSTRAINT FK_Document_Library FOREIGN KEY (idLibrary)
        REFERENCES Library(id);

ALTER TABLE Material
    ADD CONSTRAINT FK_Material_Library FOREIGN KEY (idLibrary)
        REFERENCES Library(id);

ALTER TABLE Volume
    ADD CONSTRAINT FK_Volume_Document FOREIGN KEY (idDocument)
        REFERENCES Document(id);

ALTER TABLE Magazine
    ADD CONSTRAINT FK_Magazine_Document FOREIGN KEY (idDocument)
        REFERENCES Document(id);

ALTER TABLE Dictionnary
    ADD CONSTRAINT FK_Dictionnary_Volume FOREIGN KEY (idVolume)
        REFERENCES Volume(id);

ALTER TABLE ComicBook
    ADD CONSTRAINT FK_ComicBook_Volume FOREIGN KEY (idVolume)
        REFERENCES Volume(id);

ALTER TABLE Book
    ADD CONSTRAINT FK_Book_Volume FOREIGN KEY (idVolume)
        REFERENCES Volume(id);

-- -- Insertion
-- INSERT INTO LaptopComputer (brand, available, OS) VALUES ('DELL',true,'LINUX');
-- INSERT INTO LaptopComputer (brand, available, OS) VALUES ('ASUS',false,'WINDOWS');
--
-- -- ReadALL
-- SELECT * FROM LaptopComputer;
--
-- -- Update
-- UPDATE LaptopComputer SET available = true WHERE id = '2';
--
-- -- Read this LaptopComputer
-- SELECT * FROM LaptopComputer WHERE id = '2';
--
-- -- Delete this LaptopComputer
-- DELETE FROM LaptopComputer WHERE id = '2';

