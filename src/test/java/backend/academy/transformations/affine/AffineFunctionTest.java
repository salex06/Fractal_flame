package backend.academy.transformations.affine;

import backend.academy.screen.Point;
import java.awt.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AffineFunctionTest {
    @Test
    @DisplayName("Ensure fields are initialized correctly")
    void ensureFieldsAreInitializedCorrectly() {
        double expectedA = -0.75;
        double expectedB = 1.25;
        double expectedC = 0.8;
        double expectedD = 0.11;
        double expectedE = 1.67;
        double expectedF = 1.9;
        Color expectedColor = new Color(0, 0, 0);

        AffineFunction affineFunction =
            new AffineFunction(expectedA, expectedB, expectedC, expectedD, expectedE, expectedF, expectedColor);

        assertEquals(expectedA, affineFunction.a());
        assertEquals(expectedB, affineFunction.b());
        assertEquals(expectedC, affineFunction.c());
        assertEquals(expectedD, affineFunction.d());
        assertEquals(expectedE, affineFunction.e());
        assertEquals(expectedF, affineFunction.f());

        assertEquals(expectedColor.getRGB(), affineFunction.color().getRGB());
    }

    @Test
    @DisplayName("Ensure doAffineTransformation works")
    void ensureDoAffineTransformationWorks() {
        Point point = Mockito.mock(Point.class);
        AffineFunction affineFunction = new AffineFunction(1.2, 1.25, -1.75, 1.5, -0.2, 0, Color.BLACK);
        when(point.x()).thenReturn(2.0);
        when(point.y()).thenReturn(5.0);

        Point actual = affineFunction.doAffineTransformation(point);
        Mockito.reset(point);

        assertEquals(6.9, actual.x());
        assertEquals(2.0, actual.y());
    }
}
