package backend.academy.transformations.affine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.security.SecureRandom;
import java.util.List;
import static backend.academy.transformations.affine.AffineFunction.MAX_COEFF;
import static backend.academy.transformations.affine.AffineFunction.MAX_COEFF_TRANSLATION;
import static backend.academy.transformations.affine.AffineFunction.MIN_COEFF;
import static backend.academy.transformations.affine.AffineFunction.MIN_COEFF_TRANSLATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AffineServiceTest {
    @RepeatedTest(10)
    @DisplayName("Ensure generateSingleAffineFunction method works")
    void ensureGenerateSingleAffineFunctionWorks(){
        AffineFunction actual = AffineService.generateSingleAffineFunction(new SecureRandom());

        assertThat(actual.a()).isBetween((double) MIN_COEFF, (double) MAX_COEFF);
        assertThat(actual.b()).isBetween((double) MIN_COEFF, (double) MAX_COEFF);
        assertThat(actual.d()).isBetween((double) MIN_COEFF, (double) MAX_COEFF);
        assertThat(actual.e()).isBetween((double) MIN_COEFF, (double) MAX_COEFF);

        assertThat(actual.c()).isBetween((double) MIN_COEFF_TRANSLATION, (double) MAX_COEFF_TRANSLATION);
        assertThat(actual.f()).isBetween((double) MIN_COEFF_TRANSLATION, (double) MAX_COEFF_TRANSLATION);

        assertThat(actual.color()).isNotNull();
    }

    @Test
    @DisplayName("Ensure generateListOfAffineFunctions method works")
    void ensureGenerateListOfAffineFunctionsWorks(){
        int expectedSize = 3;

        List<AffineFunction> actual = AffineService.generateListOfAffineFunctions(3, new SecureRandom());

        assertEquals(expectedSize, actual.size());

        for(AffineFunction function: actual){
            assertThat(function.a()).isBetween((double) MIN_COEFF, (double) MAX_COEFF);
            assertThat(function.b()).isBetween((double) MIN_COEFF, (double) MAX_COEFF);
            assertThat(function.d()).isBetween((double) MIN_COEFF, (double) MAX_COEFF);
            assertThat(function.e()).isBetween((double) MIN_COEFF, (double) MAX_COEFF);

            assertThat(function.c()).isBetween((double) MIN_COEFF_TRANSLATION, (double) MAX_COEFF_TRANSLATION);
            assertThat(function.f()).isBetween((double) MIN_COEFF_TRANSLATION, (double) MAX_COEFF_TRANSLATION);

            assertThat(function.color()).isNotNull();
        }
    }
}
