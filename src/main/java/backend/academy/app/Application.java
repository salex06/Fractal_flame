package backend.academy.app;

import java.io.IOException;

/**
 * A class that has only one run method
 * and is required to run the application.
 * This class represents the general logic
 * of the program, interacts with its components
 * to obtain a result
 */
public interface Application {
    /**
     * Starts the application
     *
     * @param args the command line parameters
     * @throws IOException is any i/o errors occur
     */
    void run(String[] args) throws IOException;
}
