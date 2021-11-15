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
            log.info("Donnée l1 Sent : "+l1);

            // Creation of a document
            Document d1 = documentDAO.persist("Albert Camus - L'étrangé", l1.getId());
            log.info("Donnée d1 Sent : "+d1);

            Document d2 = documentDAO.persist("Madame de la Fayette - La princesse de Clèves", l1.getId());
            log.info("Donnée d2 Sent : "+d2);

            Magazine mag1 = magazineDAO.persist(42, "PMU - Les plus gros gagnants de l'histoire", l1.getId());
            log.info("Donnée m1 Sent : "+mag1);

            Magazine mag2 = magazineDAO.persist(42, "Euromillions - Une fortune controversée", l1.getId());
            log.info("Donnée m1 Sent : "+mag2);


            // Creation of 2 other document
            documentDAO.persist(Arrays.asList(
                    Document.documentBuilder()
                            .title("Moby Dick une oeuvre fascinante")
                            .idlibrary(l1.getId())
                            .build(),
                    Document.documentBuilder()
                            .title("L'autobiographie de Nabila")
                            .idlibrary(l1.getId())
                            .build()
                )
            );
            log.info("Document sent to database !");

            long[] ids = {
                    d1.getId(),
                    -1
            };

            log.info(documentDAO.findAll().toString());


        }
        catch (DataAccessException throwables) {
            throwables.printStackTrace();
        }

        // Init API server
        final HttpServer server = startServer();
        System.out.println(String.format("Le serveur a bien démarré => Pour fermer la Base appuyez sur la touche entrée", App.getProperty("api.baseurl")));

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("Le serveur va s'arreter..");
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
