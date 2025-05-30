package backend.academy.render;

import backend.academy.image.FractalImage;
import backend.academy.image.ImageGenerationConfig;
import backend.academy.image.Pixel;
import backend.academy.processing.GammaCorrection;
import backend.academy.processing.ImageProcessor;
import backend.academy.screen.Point;
import backend.academy.transformations.affine.AffineFunction;
import backend.academy.transformations.affine.AffineService;
import backend.academy.transformations.variations.Variation;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.Color;
import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * The class is used to perform generating
 * a fractal image
 */
public class Renderer {
    /**
     * Generates a fractal image and processes it with
     * gammaCorrection
     *
     * @param canvas "a blank canvas" on which the image will be applied
     * @param config image generation configuration
     * @return the generated fractal image
     */
    public FractalImage render(
        FractalImage canvas,
        ImageGenerationConfig config
    ) {
        ImageProcessor gammaCorrection = new GammaCorrection();
        List<AffineFunction> functions =
            AffineService.generateListOfAffineFunctions(config.affinesCount(), new SecureRandom());
        try (ExecutorService executorService = Executors.newFixedThreadPool(config.threadNumber())) {
            for (int num = 0; num < config.samples(); ++num) {
                executorService.execute(
                    () -> processSample(config.iterPerSample(), config.variationList(), functions,
                        config.affinesCount(), config.symmetry(), canvas));
            }
        }
        gammaCorrection.process(canvas);
        return canvas;
    }

    @SuppressFBWarnings("PREDICTABLE_RANDOM")
    private void processSample(
        int iterPerSample,
        List<Variation> variations,
        List<AffineFunction> functions,
        int countAffines,
        int symmetry,
        FractalImage canvas
    ) {
        double xMaxCoeff = (canvas.width() < canvas.height() ? 1.0 : (double) canvas.width() / canvas.height());
        double xMinCoeff = -xMaxCoeff;

        double yMaxCoeff = (abs(xMaxCoeff - 1.0) > EPS ? 1.0 : (double) canvas.height() / canvas.width());
        double yMinCoeff = -yMaxCoeff;

        ThreadLocalRandom random = ThreadLocalRandom.current();
        Point pw = new Point(
            random.nextDouble(xMinCoeff, xMaxCoeff),
            random.nextDouble(yMinCoeff, yMaxCoeff)
        ); //получаем стартовую точку

        for (short step = START_ITERATIONS; step < iterPerSample - START_ITERATIONS; ++step) {
            Variation variation = variations.get(
                random.nextInt(0, variations.size())
            ); // получаем функцию преобразования

            AffineFunction affine = functions.get(random.nextInt(countAffines)); //получаем аффинное преобразование

            pw = affine.doAffineTransformation(pw); //применяем аффинное преобразование
            pw = variation.apply(pw); //применяем функцию V преобразования к точке

            if (step > 0) {
                double theta = 0.0;
                for (int s = 0; s < symmetry; s++) {
                    theta += ((2 * Math.PI) / symmetry);
                    double xRot = pw.x() * cos(theta) - pw.y() * sin(theta);
                    double yRot = pw.x() * sin(theta) + pw.y() * cos(theta);
                    if (xRot >= xMinCoeff && xRot <= xMaxCoeff && yRot >= yMinCoeff && yRot <= yMaxCoeff) {
                        int x1 = canvas.width()
                            - (int) (((xMaxCoeff - xRot) / (xMaxCoeff - xMinCoeff)) * canvas.width());
                        int y1 = canvas.height()
                            - (int) (((yMaxCoeff - yRot) / (yMaxCoeff - yMinCoeff)) * canvas.height());

                        if (canvas.contains(x1, y1)) {
                            Pixel pix = canvas.pixel(y1, x1);
                            canvas.setPixel(y1, x1, calcNewPixel(pix, affine));
                        }
                    }
                }
            }
        }
    }

    private Pixel calcNewPixel(Pixel pixel, AffineFunction function) {
        Color newColor;
        if (pixel.hitCount() == 0) {
            newColor = new Color(
                function.color().getRed(),
                function.color().getGreen(),
                function.color().getBlue()
            );
        } else {
            newColor = new Color(
                (pixel.color().getRed() + function.color().getRed()) / 2,
                (pixel.color().getGreen() + function.color().getGreen()) / 2,
                (pixel.color().getBlue() + function.color().getBlue()) / 2
            );
        }
        return new Pixel(newColor, pixel.hitCount() + 1, 0);
    }

    private static final int START_ITERATIONS = -20;
    private static final double EPS = 1e-6;
}
