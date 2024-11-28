package backend.academy.transformations.variations;

import backend.academy.screen.Point;
import backend.academy.transformations.variations.impl.BentVariation;
import backend.academy.transformations.variations.impl.DiscVariation;
import backend.academy.transformations.variations.impl.HandkerchiefVariation;
import backend.academy.transformations.variations.impl.SinusoidalVariation;
import backend.academy.transformations.variations.impl.SphericalVariation;
import backend.academy.transformations.variations.impl.SwirlVariation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static java.lang.Math.abs;
import static org.assertj.core.api.Assertions.assertThat;

class VariationTest {
    private static List<Map.Entry<Variation, List<Double>>> data() {
        return List.of(
            Map.entry(new SinusoidalVariation(), List.of(1.0, 1.0, 0.84147, 0.84147)),
            Map.entry(new SinusoidalVariation(), List.of(0.5, 0.1, 0.4794255, 0.09983)),
            Map.entry(new SinusoidalVariation(), List.of(-1.0, -1.5, -0.84147, -0.99749)),
            Map.entry(new SphericalVariation(), List.of(1.0, 1.0, 0.5, 0.5)),
            Map.entry(new SphericalVariation(), List.of(-1.0, -1.0, -0.5, -0.5)),
            Map.entry(new SphericalVariation(), List.of(0.5, 0.1, 1.923076, 0.38461)),
            Map.entry(new SwirlVariation(), List.of(1.0, 1.0, 1.325444, 0.493150)),
            Map.entry(new SwirlVariation(), List.of(0.5, 1.2, 0.639157, 1.13202)),
            Map.entry(new SwirlVariation(), List.of(1.5, 0.8, 1.148233, -1.25361)),
            Map.entry(new SwirlVariation(), List.of(-1.1, -1.7, -0.07709, 2.023377)),
            Map.entry(new HandkerchiefVariation(), List.of(1.0, 1.0, 1.14370, 1.14370)),
            Map.entry(new HandkerchiefVariation(), List.of(0.5, 0.1, 0.3313250734, 0.4852053516)),
            Map.entry(new HandkerchiefVariation(), List.of(1.5, -0.8, 1.590572811, -0.9865985898)),
            Map.entry(new BentVariation(), List.of(1.0, 1.2, 1.0, 1.2)),
            Map.entry(new BentVariation(), List.of(-0.5, 1.0, -1.0, 1.0)),
            Map.entry(new BentVariation(), List.of(1.4, -0.8, 1.4, -0.4)),
            Map.entry(new BentVariation(), List.of(-0.7, -1.2, -1.4, -0.6)),
            Map.entry(new DiscVariation(), List.of(1.0, 1.2, -0.2735764087, 0.05401572387)),
            Map.entry(new DiscVariation(), List.of(-0.5, 1.0, -0.23466, -0.60356)),
            Map.entry(new DiscVariation(), List.of(1.4, -0.8, 0.155044, -0.057172))
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    @DisplayName("Ensure variations works correctly")
    void ensureVariationsWorks(Map.Entry<Variation, List<Double>> curr) {
        Variation variation = curr.getKey();
        List<Double> points = curr.getValue();
        Point expected = new Point(points.get(2), points.get(3));
        Point point = new Point(points.get(0), points.get(1));

        Point actual = variation.apply(point);

        assertThat(abs(expected.x() - actual.x())).isLessThan(EPS);
        assertThat(abs(expected.y() - actual.y())).isLessThan(EPS);
    }

    private static final double EPS = 1e-4;
}
