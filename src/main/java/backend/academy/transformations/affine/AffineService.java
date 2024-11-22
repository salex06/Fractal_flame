package backend.academy.transformations.affine;

import java.awt.Color;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import static backend.academy.transformations.affine.AffineFunction.COLOR_LIMIT;

public final class AffineService {
    private AffineService() {
    }

    @SuppressWarnings("MagicNumber")
    public static AffineFunction generateSingleAffineFunction(SecureRandom secureRandom) {
        double a;
        double b;
        double c;
        double d;
        double e;
        double f;
        do {
            do {
                a = secureRandom.nextDouble(-1, 1);
                d = secureRandom.nextDouble(-1, 1);
            } while ((a * a + d * d) > 1);
            do {
                b = secureRandom.nextDouble(-1, 1);
                e = secureRandom.nextDouble(-1, 1);
            } while ((b * b + e * e) > 1);
        } while ((a * a + b * b + d * d + e * e) > (1 + (a * e - d * b) * (a * e - d * b)));

        c = secureRandom.nextDouble(-2, 2);
        f = secureRandom.nextDouble(-2, 2);

        Color color = new Color(
            secureRandom.nextInt(COLOR_LIMIT),
            secureRandom.nextInt(COLOR_LIMIT),
            secureRandom.nextInt(COLOR_LIMIT)
        );

        return new AffineFunction(a, b, c, d, e, f, color);
    }

    public static AffineFunction generateSingleAffineFunction() {
        SecureRandom secureRandom = new SecureRandom();
        return generateSingleAffineFunction(secureRandom);
    }

    public static List<AffineFunction> generateListOfAffineFunctions(int count, SecureRandom random) {
        List<AffineFunction> affineFunctions = new ArrayList<>(count);
        for (int i = 0; i < count; ++i) {
            affineFunctions.add(i, generateSingleAffineFunction(random));
        }
        return affineFunctions;
    }

    public static List<AffineFunction> generateListOfAffineFunctions(int count) {
        List<AffineFunction> affineFunctions = new ArrayList<>(count);
        for (int i = 0; i < count; ++i) {
            affineFunctions.add(i, generateSingleAffineFunction());
        }
        return affineFunctions;
    }
}
