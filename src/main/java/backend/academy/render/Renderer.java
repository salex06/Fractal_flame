package backend.academy.render;

import backend.academy.image.FractalImage;
import backend.academy.image.Pixel;
import backend.academy.processing.GammaCorrection;
import backend.academy.processing.ImageProcessor;
import backend.academy.screen.Point;
import backend.academy.screen.Rect;
import backend.academy.transformations.affine.AffineFunction;
import backend.academy.transformations.affine.AffineService;
import backend.academy.transformations.variations.Variation;
import java.awt.Color;
import java.security.SecureRandom;
import java.util.List;
import static backend.academy.image.FractalImage.XMAX_COEFF;
import static backend.academy.image.FractalImage.XMIN_COEFF;
import static backend.academy.image.FractalImage.YMAX_COEFF;
import static backend.academy.image.FractalImage.YMIN_COEFF;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

@SuppressWarnings("ParameterNumber")
public class Renderer {
    public FractalImage render(
        int countAffines,
        FractalImage canvas,
        Rect world,
        List<Variation> variations,
        int samples,
        short iterPerSample,
        int symmetry,
        SecureRandom random
    ) {
        ImageProcessor gammaCorrection = new GammaCorrection();
        List<AffineFunction> functions = AffineService.generateListOfAffineFunctions(countAffines);
        double xMin = XMIN_COEFF;
        double yMin = YMIN_COEFF;
        double yMax = YMAX_COEFF;
        double xMax = XMAX_COEFF;
        int xRes = canvas.width();
        int yRes = canvas.height();
        double ranx = xMax - xMin;
        double rany = yMax - yMin;
        for (int num = 0; num < samples; ++num) {
            double newX = random.nextDouble(XMIN_COEFF, XMAX_COEFF);
            double newY = random.nextDouble(YMIN_COEFF, YMAX_COEFF);

            Point pw = new Point(newX, newY); //получаем стартовую точку

            for (short step = START_ITERATIONS; step < iterPerSample - START_ITERATIONS; ++step) {
                Variation variation = variations.get(
                    random.nextInt(0, variations.size())
                ); // получаем функцию преобразования

                AffineFunction affine = functions.get(random.nextInt(countAffines));
                pw = affine.doAffineTransformation(pw); //применяем аффинное преобразование

                pw = variation.apply(pw); //применяем функцию V преобразования к точке
                newX = pw.x();
                newY = pw.y();

                if (step > 0) {
                    double theta = 0.0;
                    for (int s = 0; s < symmetry; s++) {
                        theta += ((2 * Math.PI) / symmetry);
                        double xRot = newX * cos(theta) - newY * sin(theta);
                        double yRot = newX * sin(theta) + newY * cos(theta);
                        if (xRot >= xMin && xRot <= xMax && yRot >= yMin && yRot <= yMax) {
                            int x1 = xRes - (int) (((xMax - xRot) / ranx) * xRes);
                            int y1 = yRes - (int) (((yMax - yRot) / rany) * yRes);

                            if (x1 >= 0 && x1 < xRes && y1 >= 0 && y1 < yRes) {
                                Pixel pix = canvas.pixel(y1, x1);
                                canvas.setPixel(y1, x1, calcNewPixel(pix, affine));
                            }
                        }
                    }
                }
            }
        }
        gammaCorrection.process(canvas);
        return canvas;
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
}
