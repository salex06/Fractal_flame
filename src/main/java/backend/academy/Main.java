package backend.academy;

import backend.academy.app.Application;
import backend.academy.app.impl.FractalApplication;
import java.io.IOException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) throws IOException {
        Application application = new FractalApplication();
        application.run();
    }
}


