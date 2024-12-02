package backend.academy.transformations.affine;

import java.awt.Color;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import static backend.academy.transformations.affine.AffineFunction.COLOR_LIMIT;
import static backend.academy.transformations.affine.AffineFunction.MAX_COEFF;
import static backend.academy.transformations.affine.AffineFunction.MAX_COEFF_TRANSLATION;
import static backend.academy.transformations.affine.AffineFunction.MIN_COEFF;
import static backend.academy.transformations.affine.AffineFunction.MIN_COEFF_TRANSLATION;

/**
 * Service for working with the affine function model
 */
public final class AffineService {
    private AffineService() {
    }

    /**
     * Generates an affine function
     *
     * @param secureRandom randomizer
     * @return affine function
     */
    public static AffineFunction generateSingleAffineFunction(SecureRandom secureRandom) {
        double a;
        double b;
        double c;
        double d;
        double e;
        double f;
        do {
            do {
                a = secureRandom.nextDouble(MIN_COEFF, MAX_COEFF);
                d = secureRandom.nextDouble(MIN_COEFF, MAX_COEFF);
            } while ((a * a + d * d) > 1);
            do {
                b = secureRandom.nextDouble(MIN_COEFF, MAX_COEFF);
                e = secureRandom.nextDouble(MIN_COEFF, MAX_COEFF);
            } while ((b * b + e * e) > 1);
        } while ((a * a + b * b + d * d + e * e) > (1 + (a * e - d * b) * (a * e - d * b)));

        c = secureRandom.nextDouble(MIN_COEFF_TRANSLATION, MAX_COEFF_TRANSLATION);
        f = secureRandom.nextDouble(MIN_COEFF_TRANSLATION, MAX_COEFF_TRANSLATION);

        Color color = new Color(
            secureRandom.nextInt(COLOR_LIMIT),
            secureRandom.nextInt(COLOR_LIMIT),
            secureRandom.nextInt(COLOR_LIMIT)
        );

        return new AffineFunction(a, b, c, d, e, f, color);
    }

    /**
     * Returns list of the affine functions
     *
     * @param count        affine functions number
     * @param secureRandom randomizer for generating coefficients
     * @return list of the affine functions
     */
    public static List<AffineFunction> generateListOfAffineFunctions(int count, SecureRandom secureRandom) {
        List<AffineFunction> affineFunctions = new ArrayList<>(count);
        for (int i = 0; i < count; ++i) {
            affineFunctions.add(i, generateSingleAffineFunction(secureRandom));
        }
        return affineFunctions;
    }
}
