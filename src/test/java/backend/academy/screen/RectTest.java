package backend.academy.screen;

import java.security.SecureRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RectTest {
    private static final double EPS = 1e-6;

    @ParameterizedTest
    @CsvSource({"0,0,2500, 3000", "10, 10, 35, 35", "0,0, 500, 10", "-1, -1, -300, -500"})
    @DisplayName("Ensure getRandomPoint works")
    void ensureGetRandomPointWorks(int x, int y, int width, int height) {
        Rect rect = new Rect(x, y, width, height);
        SecureRandom mockedRandom = mock(SecureRandom.class);
        when(mockedRandom.nextDouble(x, x + width)).thenReturn((double) (x + width) / 2);
        when(mockedRandom.nextDouble(y, y + height)).thenReturn((double) (y + height) / 2);
        Point expected = new Point((double) (x + width) / 2, (double) (y + height) / 2);

        Point actual = rect.getRandomPoint(mockedRandom);

        assertThat(Math.abs(expected.x() - actual.x())).isLessThan(EPS);
        assertThat(Math.abs(expected.y() - actual.y())).isLessThan(EPS);
    }

    @ParameterizedTest
    @CsvSource({"0.52, 0.11, true", "10, 15, true", "3500, 10000, false", "-1.7, 5.4, false", "500, 500, true",
        "500, 0, true"})
    @DisplayName("Ensure contains method works")
    void ensureContainsMethodWorks(double x, double y, boolean expected) {
        Point p = new Point(x, y);
        Rect rect = new Rect(0, 0, 500, 500);

        boolean actual = rect.contains(p);

        assertEquals(expected, actual);
    }

}
