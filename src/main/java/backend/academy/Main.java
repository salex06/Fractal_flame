package backend.academy;

import backend.academy.image.FractalImage;
import backend.academy.render.Renderer;
import backend.academy.screen.Rect;
import backend.academy.transformations.variations.VariationService;
import backend.academy.utils.ImageUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.security.SecureRandom;
import lombok.experimental.UtilityClass;
import static backend.academy.format.ImageFormat.PNG;

@UtilityClass
@SuppressWarnings("MagicNumber")
public class Main {
    public static void main(String[] args) throws IOException {
        Renderer renderer = new Renderer();
        FractalImage image = renderer.render(
            5,
            new FractalImage(1080, 1920),
            new Rect(0, 0, 1920, 1080),
            VariationService.getVariationsList(),
            100_000,
            (short) 100,
            new SecureRandom()
        );
        ImageUtils.save(image, Path.of("src/main/resources/out.png"), PNG);
    }
}


