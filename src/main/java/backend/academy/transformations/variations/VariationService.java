package backend.academy.transformations.variations;

import backend.academy.transformations.variations.impl.BentVariation;
import backend.academy.transformations.variations.impl.DiscVariation;
import backend.academy.transformations.variations.impl.HandkerchiefVariation;
import backend.academy.transformations.variations.impl.SinusoidalVariation;
import backend.academy.transformations.variations.impl.SphericalVariation;
import backend.academy.transformations.variations.impl.SwirlVariation;
import java.util.List;
import java.util.Map;

/**
 * The class contains methods for obtaining
 * objects of the Variation class
 */
public final class VariationService {
    private VariationService() {
    }

    private static final List<Map.Entry<String, Variation>> VARIATIONS = List.of(
        Map.entry("Handkerchief", new HandkerchiefVariation()),
        Map.entry("Sinusoidal", new SinusoidalVariation()),
        Map.entry("Spherical", new SphericalVariation()),
        Map.entry("Swirl", new SwirlVariation()),
        Map.entry("Bent", new BentVariation()),
        Map.entry("Disc", new DiscVariation())
    );

    /**
     * Returns the list of variations
     *
     * @return {@code List<Variation>} - all available variations
     */
    public static List<Variation> getVariationsList() {
        return VARIATIONS.stream().map(Map.Entry::getValue).toList();
    }

    /**
     * Returns the list of available variations in the form of string
     *
     * @return {@code String} - available variations
     */
    public static String getVariationsListAsString() {
        StringBuilder builder = new StringBuilder();
        int index = 1;
        for (Map.Entry<String, Variation> variation : VARIATIONS) {
            builder.append('[')
                .append(index++)
                .append("] - ")
                .append(variation.getKey())
                .append('\n');
        }
        return builder.toString();
    }

    /**
     * Returns the variation by its name
     * @param name name of the variation
     * @return an object of the variation class (or null if not found)
     */
    public static Variation getVariationByName(String name) {
        for (Map.Entry<String, Variation> variation : VARIATIONS) {
            if (variation.getKey().equals(name)) {
                return variation.getValue();
            }
        }
        return null;
    }
}
