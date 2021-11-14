package fr.univtln.gageyreboulmusial;

import fr.univtln.gageyreboulmusial.daos.*;
import fr.univtln.gageyreboulmusial.entities.*;
import fr.univtln.gageyreboulmusial.exceptions.DataAccessException;
import lombok.extern.java.Log;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

/**
 * Hello world!
 *
 */
@Log
public class App 
{
    public static Properties properties = new Properties();

    private static void loadProperties(String propFileName) throws IOException {
        InputStream inputStream = App.class.getClassLoader().getResourceAsStream(propFileName);
        if (inputStream == null)
            throw new FileNotFoundException();
        properties.load(inputStream);
    }

    private static void configureLogger() {
        //Regarder src/main/ressources/logging.properties pour fixer le niveau de log
        String path;
        path = Objects.requireNonNull(App.class
                .getClassLoader()
                .getResource("logging.properties"))
                .getFile();
        System.setProperty("java.util.logging.config.file", path);
    }

    public static String getProperty(String s) {
        return properties.getProperty(s);
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        final ResourceConfig rc = new ResourceConfig().packages("fr.univtln.gageyreboulmusial");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(App.getProperty("api.baseurl")), rc);
    }

    public static void main( String[] args ) throws IOException {
        // Load properties
        loadProperties("app.properties");
        configureLogger();

        try {
            LibraryDAO libraryDAO = new LibraryDAO();
            DocumentDAO documentDAO = new DocumentDAO();
            MagazineDAO magazineDAO = new MagazineDAO();
            VolumeDAO volumeDAO = new VolumeDAO();
            ComicBookDAO comicBookDAO = new ComicBookDAO();

            magazineDAO.clean();
            comicBookDAO.clean();
            volumeDAO.clean();
            documentDAO.clean();
            libraryDAO.clean();

            // Creation of a library
            Library l1 = libraryDAO.persist();
            log.info("l1 persisted "+l1);

            // Creation of a document
            Document d1 = documentDAO.persist("Harry Potter - A l'école des sorciers", l1.getId());
            log.info("d1 persisted "+d1);

            Magazine mag1 = magazineDAO.persist(42, "Voici - Le clash en Maeva Ghenam et Martine", l1.getId());
            log.info("mag1 persisted "+mag1);

            // Creation of 2 other document
            documentDAO.persist(Arrays.asList(
                    Document.documentBuilder()
                            .title("Harry Potter et la Chambre des Secrets")
                            .idlibrary(l1.getId())
                            .build(),
                    Document.documentBuilder()
                            .title("Harry Potter et le prisonnier d'Azkaban")
                            .idlibrary(l1.getId())
                            .build()
                )
            );
            log.info("Document list persisted !");

            // Reading of some documents by id with an missing one
            long[] ids = {
                    d1.getId(),
                    -1
            };

//            // For each id, I look for the document in the table
//            for(long id : ids) {
//                // We use Optional because the result can be null
//                Optional<Document> optionalDocument = documentDAO.find(id);
//                log.info(
//                        "Document " + id + " : " + (optionalDocument.isPresent()
//                                ? optionalDocument.get()
//                                : "NOT FOUND")
//                );
//            }

            // Reading all documents in the table
            log.info(documentDAO.findAll().toString());

//            // Updating of an document
//            d1 = Document.builder()
//                    .id(d1.getId())
//                    .title("Harry Potter à l'école des sorciers")
//                    .build();
//
//            documentDAO.update(d1);
//            Optional<Document> optionalDocument = documentDAO.find(d1.getId());
//            log.info(
//                    "Document 1 MAJ : " + (optionalDocument.isPresent()
//                            ? optionalDocument.get()
//                            : "NOT FOUND")
//            );
//
//            Volume v1 = volumeDAO.persist("J.K. Rowling", d1.getId());
//            ComicBook cb1 = comicBookDAO.persist("James Cameron", v1.getId());

            // Deleting a document by reference
            //documentDAO.remove(d1);

            // Deleting a document by id
            //documentDAO.remove(3);

        }
        catch (DataAccessException throwables) {
            throwables.printStackTrace();
        }

        // Init API server
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", App.getProperty("api.baseurl")));

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("Stopping server..");
                server.shutdown();
            }
        }, "shutdownHook"));

        try {
            server.start();
            Thread.currentThread().join();
        } catch (Exception ioe) {
            System.err.println(ioe);
        } finally {
            server.shutdown();
        }
    }
}
