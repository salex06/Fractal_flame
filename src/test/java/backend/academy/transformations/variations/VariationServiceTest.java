package backend.academy.transformations.variations;

import backend.academy.transformations.variations.impl.BentVariation;
import backend.academy.transformations.variations.impl.DiscVariation;
import backend.academy.transformations.variations.impl.HandkerchiefVariation;
import backend.academy.transformations.variations.impl.SinusoidalVariation;
import backend.academy.transformations.variations.impl.SphericalVariation;
import backend.academy.transformations.variations.impl.SwirlVariation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class VariationServiceTest {
    @Test
    @DisplayName("Ensure getVariationsList works")
    void ensureGetVariationsListWorks() {
        List<Variation> expected = List.of(
            new HandkerchiefVariation(),
            new SinusoidalVariation(),
            new SphericalVariation(),
            new SwirlVariation(),
            new BentVariation(),
            new DiscVariation()
        );

        List<Variation> actual = VariationService.getVariationsList();

        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); ++i) {
            assertEquals(expected.get(i).getClass(), actual.get(i).getClass());
        }
    }

    @Test
    @DisplayName("Ensure getVariationsListAsString works")
    void ensureGetVariationsListAsStringWorks() {
        String expected = """
            [1] - Handkerchief
            [2] - Sinusoidal
            [3] - Spherical
            [4] - Swirl
            [5] - Bent
            [6] - Disc
            """;

        String actual = VariationService.getVariationsListAsString();

        assertEquals(expected, actual);
    }

    private static List<Map.Entry<String, Variation>> data() {
        return List.of(
            Map.entry("Handkerchief", new HandkerchiefVariation()),
            Map.entry("Sinusoidal", new SinusoidalVariation()),
            Map.entry("Spherical", new SphericalVariation()),
            Map.entry("Swirl", new SwirlVariation()),
            Map.entry("Bent", new BentVariation()),
            Map.entry("Disc", new DiscVariation())
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    @DisplayName("Ensure getVariationByName works (correct name)")
    void ensureGetVariationByNameWorks_correctName(Map.Entry<String, Variation> current) {
        String name = current.getKey();
        Variation expected = current.getValue();

        Variation actual = VariationService.getVariationByName(name);

        assertNotNull(actual);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    @DisplayName("Ensure getVariationByName works (incorrect name)")
    void ensureGetVariationByNameWorks_incorrectName() {
        String name = "wrongVariation";

        Variation actual = VariationService.getVariationByName(name);

        assertNull(actual);
    }
}
