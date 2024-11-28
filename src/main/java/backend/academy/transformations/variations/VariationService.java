package backend.academy.transformations.variations;

import backend.academy.transformations.variations.impl.BentVariation;
import backend.academy.transformations.variations.impl.DiscVariation;
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
        new SwirlVariation(),
        new BentVariation(),
        new DiscVariation()
    );

    /**
     * Returns the list of variations
     *
     * @return {@code List<Variation>} - all available variations
     */
    public static List<Variation> getVariationsList() {
        return VARIATIONS;
    }

    /**
     * Returns the list of available variations in the form of string
     *
     * @return {@code String} - available variations
     */
    public static String getVariationsListAsString() {
        StringBuilder builder = new StringBuilder();
        int index = 1;
        for (Variation variation : VARIATIONS) {
            builder.append('[').append(index++).append("] - ").append(
                    variation.getClass().getSimpleName())
                .append('\n');
        }
        return builder.toString();
    }
}
