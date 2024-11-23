package backend.academy.processing;

import backend.academy.image.FractalImage;
import backend.academy.image.Pixel;
import java.awt.Color;
import static java.lang.Math.log10;
import static java.lang.Math.pow;

public class GammaCorrection implements ImageProcessor {
    @Override
    public void process(FractalImage image) {
        int xRes = image.width();
        int yRes = image.height();

        double max = 0.0;
        double gamma = GAMMA_COEFFICIENT;
        for (int row = 0; row < yRes; row++) {
            for (int col = 0; col < xRes; col++) {
                Pixel pix = image.pixel(row, col);
                if (pix.hitCount() != 0) {
                    double newNormal = log10(pix.hitCount());
                    image.setPixel(row, col, new Pixel(pix.color(), pix.hitCount(), newNormal));

                    if (newNormal > max) {
                        max = newNormal;
                    }
                }
            }
        }

        for (int row = 0; row < yRes; row++) {
            for (int col = 0; col < xRes; col++) {
                Pixel pix = image.pixel(row, col);
                double newNormal = pix.normal() / max;
                int newR = (int) (pix.color().getRed() * pow(newNormal, (1.0 / gamma)));
                int newG = (int) (pix.color().getGreen() * pow(newNormal, (1.0 / gamma)));
                int newB = (int) (pix.color().getBlue() * pow(newNormal, (1.0 / gamma)));
                image.setPixel(row, col, new Pixel(
                    new Color(newR, newG, newB),
                    pix.hitCount(),
                    newNormal
                ));
            }
        }
    }

    private static final double GAMMA_COEFFICIENT = 2.2;
}
