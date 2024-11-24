package backend.academy.transformations.variations;

import backend.academy.transformations.variations.impl.HandkerchiefVariation;
import backend.academy.transformations.variations.impl.SinusoidalVariation;
import backend.academy.transformations.variations.impl.SphericalVariation;
import backend.academy.transformations.variations.impl.SwirlVariation;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VariationServiceTest {
    @Test
    @DisplayName("Ensure getVariationsList works")
    void ensureGetVariationsListWorks() {
        List<Variation> expected = List.of(
            new HandkerchiefVariation(),
            new SinusoidalVariation(),
            new SphericalVariation(),
            new SwirlVariation()
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
            [1] - HandkerchiefVariation
            [2] - SinusoidalVariation
            [3] - SphericalVariation
            [4] - SwirlVariation
            """;

        String actual = VariationService.getVariationsListAsString();

        assertEquals(expected, actual);
    }
}
