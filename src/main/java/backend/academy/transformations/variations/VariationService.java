package backend.academy.transformations.variations;

import backend.academy.transformations.variations.impl.HandkerchiefVariation;
import backend.academy.transformations.variations.impl.SinusoidalVariation;
import backend.academy.transformations.variations.impl.SphericalVariation;
import backend.academy.transformations.variations.impl.SwirlVariation;
import java.util.List;

/**
 * The class contains methods for obtaining
 * objects of the Variation class
 */
public final class VariationService {
    private VariationService() {
    }

    private static final List<Variation> VARIATIONS = List.of(
        new HandkerchiefVariation(),
        new SinusoidalVariation(),
        new SphericalVariation(),
        new SwirlVariation()
    );

    /**
     * Returns the list of variations
     *
     * @return {@code List<Variation>} - all available variations
     */
    public static List<Variation> getVariationsList() {
        return VARIATIONS;
    }
}
