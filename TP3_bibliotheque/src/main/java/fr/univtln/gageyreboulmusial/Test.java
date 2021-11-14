package fr.univtln.gageyreboulmusial;

import fr.univtln.gageyreboulmusial.entities.*;

import java.sql.*;
import java.util.*;

public class Test {
    public static void main(String[] args) {
//
//        /* Create two laptop - one for each os */

        Library library = Library.libraryBuilder()
                .id(1)
                .CollecMember(new HashSet<Member>())
                .CollecMaterial(new HashSet<Material>())
                .CollecDocument(new HashSet<Document>())
                .build();

        Material material_1 = Material.materialBuilder()
                .idlibrary(library.getId())
                .build();


        Member member_1 = Member.memberBuilder()
                .id(1)
                .name("GAGEY")
                .first_name("Fabien")
                .status(Member.Status.STUDENT)
                .idLibrary(library.getId())
                .borrowingObject(new HashSet<>())
                .build();

        Member member_2 = Member.memberBuilder()
                .id(2)
                .name("REBOUL")
                .first_name("Hugo")
                .status(Member.Status.STUDENT)
                .idLibrary(library.getId())
                .borrowingObject(new HashSet<>())
                .build();

        Member member_3 = Member.memberBuilder()
                .id(3)
                .name("MUSIAL")
                .first_name("Charles")
                .status(Member.Status.STUDENT)
                .idLibrary(library.getId())
                .borrowingObject(new HashSet<>())
                .build();

        Member member_4 = Member.memberBuilder()
                .id(4)
                .name("BRUNO")
                .first_name("Emmanuel")
                .status(Member.Status.TEACHER)
                .idLibrary(library.getId())
                .borrowingObject(new HashSet<>())
                .build();


        LaptopComputer linux_computer = LaptopComputer.laptopcomputerBuilder()
                .brand("DELL")
                .os(LaptopComputer.OS.LINUX)
                .idlibrary(library.getId())
                .build();

        LaptopComputer windows_computer = LaptopComputer.laptopcomputerBuilder()
                .brand("DELL")
                .os(LaptopComputer.OS.WINDOWS)
                .idlibrary(library.getId())
                .build();


        Document document_1 = Document.documentBuilder()
                .title("Harry Potter à l'école des sorciers")
                .idlibrary(library.getId())
                .build();

        Document document_2 = Document.documentBuilder()
                .title("Tara Duncan - Les Sortceliers")
                .idlibrary(library.getId())
                .build();


        Volume volume_1 = Volume.volumeBuilder()
                .title("Harry Potter et la chambre des secrets")
                .author("J.K Rowling")
                .build();

        Volume volume_2 = Volume.volumeBuilder()
                .title("Tara Duncan - Le livre interdit")
                .author("S. Ardouin-Mamikonian")
                .build();


        Book book_1 = Book.bookBuilder()
                .title("Harry Potter et le prisonnier d'Azkaban")
                .author("J.K Rowling")
                .idlibrary(library.getId())
                .build();

        Book book_2 = Book.bookBuilder()
                .title("Tara Duncan - Le sceptre maudit")
                .author("S. Ardouin-Mamikonian")
                .idlibrary(library.getId())
                .build();


        Magazine magazine_1 = Magazine.magazineBuilder()
                .title("COVID-19 : un reconfinement non-respecté ?")
                .number(156)
                .idlibrary(library.getId())
                .build();

        Magazine magazine_2 = Magazine.magazineBuilder()
                .title("COVID-19 : y'en a un peu marre")
                .number(301)
                .idlibrary(library.getId())
                .build();


        Dictionnary dictionnary_1 = Dictionnary.dictionnaryBuilder()
                .title("Larousse")
                .author("Edition Larousse")
                .theme("langue française")
                .build();

        Dictionnary dictionnary_2 = Dictionnary.dictionnaryBuilder()
                .title("Cambridge Dictionary")
                .author("Edition Cambridge")
                .theme("langue anglaise")
                .build();


        ComicBook comicBook_1 = ComicBook.comicbookBuilder()
                .title("Tintin au Tibet")
                .author("Hergé")
                .designer("Hergé")
                .build();

        ComicBook comicBook_2 = ComicBook.comicbookBuilder()
                .title("Astérix et Obélix")
                .author("J-Y. Ferri")
                .designer("D. Conrad")
                .build();



       // Implementation of Library list

        library.addMember(member_1);
        library.addMember(member_2);
        library.addMember(member_3);

        library.addMaterial(linux_computer);
        library.addMaterial(windows_computer);

        library.addDocument(document_1);
        library.addDocument(document_2);

        library.addDocument(volume_1);
        library.addDocument(volume_2);

        library.addDocument(book_1);
        library.addDocument(book_2);

        library.addDocument(magazine_1);
        library.addDocument(magazine_2);

        library.addDocument(dictionnary_1);
        library.addDocument(dictionnary_2);

        library.addDocument(comicBook_1);
        library.addDocument(comicBook_2);


        // Print attributes

        System.out.println(linux_computer.toString());
        System.out.println(windows_computer.toString());

        System.out.println(member_1.toString());
        System.out.println(member_2.toString());
        System.out.println(member_3.toString());

        System.out.println(book_1.toString());
        System.out.println(book_2.toString());

        System.out.println(magazine_1.toString());
        System.out.println(magazine_2.toString());

        System.out.println(dictionnary_1.toString());
        System.out.println(dictionnary_2.toString());

        System.out.println(comicBook_1.toString());
        System.out.println(comicBook_2.toString());



        // Function to search title
        /* wordInTitle - to search this word in your collection of books */
        String wordInTitle = "walk";
        System.out.println("TITLE WITH THE WORD : " + wordInTitle);
        Set<Document> SearchInLibrary = library.searchTitle(wordInTitle);
        for (Document document : SearchInLibrary) {
            System.out.println(document.getTitle());
        }


       // Borrowing Management
        member_1.borrow(book_1);
        member_1.borrow(book_2);
        member_1.displayBorrowing();
        member_1.restore(book_1);
        member_1.displayBorrowing();
        member_2.borrow(book_2);
        member_2.borrow(linux_computer);
        member_2.displayBorrowing();

        /* Member_1 try to borrow a computer not available and Member_2 try to borrow a 2nd computer */
        member_1.borrow(linux_computer);
        member_2.borrow(windows_computer);

        library.displayFunds();
    }
}
