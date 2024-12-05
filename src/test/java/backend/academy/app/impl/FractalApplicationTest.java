package backend.academy.app.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class FractalApplicationTest {
    private final Map.Entry<String, String> demoWidth = Map.entry("--image-width", "1920");
    private final Map.Entry<String, String> demoHeight = Map.entry("--image-height", "1080");
    private final Map.Entry<String, String> demoAffines = Map.entry("--affines-number", "20");
    private final Map.Entry<String, String> demoVariations = Map.entry("--variations", "Sinusoidal,Swirl,Disc");
    private final Map.Entry<String, String> demoSamples = Map.entry("--samples-number", "10000");
    private final Map.Entry<String, String> demoIterPerSample = Map.entry("--iter-per-sample", "100");
    private final Map.Entry<String, String> demoSymmetry = Map.entry("--symmetry-axes-number", "4");
    private final Map.Entry<String, String> demoMultiThreads = Map.entry("--multi-threads", "4");
    private final Map.Entry<String, String> demoImageFormat = Map.entry("--file-format", "PNG");

    @Test
    @DisplayName("Ensure the application works correctly")
    void ensureApplicationWorksCorrectly() {
        String[] args =
            new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(), demoHeight.getValue(),
                demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(), demoIterPerSample.getValue(),
                demoVariations.getKey(), demoVariations.getValue(), demoImageFormat.getKey(),
                demoImageFormat.getValue(), demoMultiThreads.getKey(), demoMultiThreads.getValue(),
                demoSymmetry.getKey(), demoSymmetry.getValue(), demoAffines.getKey(), demoAffines.getValue()
            };

        assertDoesNotThrow(() -> new FractalApplication().run(args));
    }
}
