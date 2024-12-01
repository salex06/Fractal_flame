package backend.academy.cli;

import backend.academy.transformations.variations.Variation;
import backend.academy.transformations.variations.impl.DiscVariation;
import backend.academy.transformations.variations.impl.SwirlVariation;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CliParamsTest {
    private final Map.Entry<String, String> demoWidth = Map.entry("--image-width", "1920");
    private final Map.Entry<String, String> demoHeight = Map.entry("--image-height", "1080");
    private final Map.Entry<String, String> demoAffines = Map.entry("--affines-number", "20");
    private final Map.Entry<String, String> demoVariations = Map.entry("--variations", "Sinusoidal,Swirl,Disc");
    private final Map.Entry<String, String> demoSamples = Map.entry("--samples-number", "100000");
    private final Map.Entry<String, String> demoIterPerSample = Map.entry("--iter-per-sample", "1920");
    private final Map.Entry<String, String> demoSymmetry = Map.entry("--symmetry-axes-number", "4");
    private final Map.Entry<String, String> demoMultiThreads = Map.entry("--multi-threads", "4");
    private final Map.Entry<String, String> demoImageFormat = Map.entry("--file-format", "PNG");

    private CliParams cliParams;

    @BeforeEach
    void setup() {
        cliParams = new CliParams();
    }

    @ParameterizedTest
    @CsvSource({"--image-width, 1920, --image-height, 390",
        "-w, 1000, -h, 215"})
    @DisplayName("Ensure reading size is working correctly")
    void ensureReadingWidthIsWorkingCorrectly(String width, String widthVal, String height, String heightVal) {
        String[] args = new String[] {width, widthVal, height, heightVal,
            demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(), demoIterPerSample.getValue(),
            demoVariations.getKey(), demoVariations.getValue(), demoImageFormat.getKey(), demoImageFormat.getValue()};
        JCommander.newBuilder().addObject(cliParams).build().parse(args);

        assertEquals(Integer.parseInt(widthVal), cliParams.width());
        assertEquals(Integer.parseInt(heightVal), cliParams.height());
    }

    @ParameterizedTest
    @CsvSource({"-w, asfasfa", "-w, -h", "--image-width, -132"})
    @DisplayName("Ensure reading incorrect width throws a Parameter Exception")
    void ensureReadingIncorrectWidthThrowsException(String fl, String val) {
        String[] args = new String[] {fl, val, demoHeight.getKey(), demoHeight.getValue(),
            demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(), demoIterPerSample.getValue(),
            demoVariations.getKey(), demoVariations.getValue(), demoImageFormat.getKey(), demoImageFormat.getValue()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @ParameterizedTest
    @CsvSource({"-h, asfasfa", "-h, -w", "--image-height, -132"})
    @DisplayName("Ensure reading incorrect width throws a Parameter Exception")
    void ensureReadingIncorrectHeightThrowsException(String fl, String val) {
        String[] args = new String[] {fl, val, demoWidth.getKey(), demoWidth.getValue(),
            demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(), demoIterPerSample.getValue(),
            demoVariations.getKey(), demoVariations.getValue(), demoImageFormat.getKey(), demoImageFormat.getValue()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @Test
    @DisplayName("Ensure the absence of width causes a Parameter Exception")
    void ensureAbsenceWidthCausesException() {
        String[] args = new String[] {demoHeight.getKey(), demoHeight.getValue(),
            demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(), demoIterPerSample.getValue(),
            demoVariations.getKey(), demoVariations.getValue(), demoImageFormat.getKey(), demoImageFormat.getValue()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @Test
    @DisplayName("Ensure the absence of height causes a Parameter Exception")
    void ensureAbsenceHeightCausesException() {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(),
            demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(), demoIterPerSample.getValue(),
            demoVariations.getKey(), demoVariations.getValue(), demoImageFormat.getKey(), demoImageFormat.getValue()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @ParameterizedTest
    @CsvSource({"--affines-number, 25", "-a, 13"})
    @DisplayName("Ensure reading affinesNumber is working correctly")
    void ensureReadingAffinesNumberIsWorkingCorrectly(String flag, String val) {
        String[] args = new String[] {flag, val, demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue()};
        JCommander.newBuilder().addObject(cliParams).build().parse(args);

        assertEquals(Integer.parseInt(val), cliParams.affinesNumber());
    }

    @ParameterizedTest
    @CsvSource({"--affines-number, -1", "-a, sfdlkfd"})
    @DisplayName("Ensure reading incorrect affinesNumber throws a Parameter Exception")
    void ensureReadingIncorrectAffinesNumberThrowsException(String flag, String val) {
        String[] args = new String[] {flag, val, demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @Test
    @DisplayName("Ensure setting the default value for affinesNumber works correctly")
    void ensureSettingDefaultValueWorksCorrectly() {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue()};
        JCommander.newBuilder().addObject(cliParams).build().parse(args);

        assertEquals(CliParams.DEFAULT_AFFINES_NUMBER(), cliParams.affinesNumber());
    }

    @ParameterizedTest
    @CsvSource({"--variation, 'Disc,Swirl'", "--variations, 'Disc,Swirl'",
        "-v, 'Disc,Swirl'"})
    @DisplayName("Ensure reading variations is working correctly")
    void ensureReadingVariationsWorkingCorrectly(String flag, String val) {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), flag, val,
            demoImageFormat.getKey(), demoImageFormat.getValue()};
        JCommander.newBuilder().addObject(cliParams).build().parse(args);

        List<Variation> expected = List.of(new DiscVariation(), new SwirlVariation());

        assertEquals(expected.size(), cliParams.variations().size());
        for (int i = 0; i < expected.size(); ++i) {
            assertEquals(expected.get(i).getClass(), cliParams.variations().get(i).getClass());
        }
    }

    @ParameterizedTest
    @CsvSource({"-v, -1", "--variations, 'Swirl,SomethingElse'", "--variation, 'wrongVariation'"})
    @DisplayName("Ensure reading incorrect variations throws a Parameter Exception")
    void ensureReadingIncorrectVariationsThrowsException(String flag, String val) {
        String[] args =
            new String[] {demoHeight.getKey(), demoHeight.getValue(), demoWidth.getKey(), demoWidth.getValue(),
                demoHeight.getKey(),
                demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
                demoIterPerSample.getValue(), flag, val,
                demoImageFormat.getKey(), demoImageFormat.getValue()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @Test
    @DisplayName("Ensure the absence of variations causes a Parameter Exception")
    void ensureAbsenceVariationsCausesException() {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(),
            demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(), demoIterPerSample.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @ParameterizedTest
    @CsvSource({"--samples-number, 100000, --iter-per-sample, 100",
        "-samples, 5000, -iter, 10000"})
    @DisplayName("Ensure reading samples config is working correctly")
    void ensureReadingSamplesConfigIsWorkingCorrectly(String sample, String sampleVal, String iter, String iterVal) {
        String[] args =
            new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(), demoHeight.getValue(),
                sample, sampleVal, iter, iterVal,
                demoVariations.getKey(), demoVariations.getValue(), demoImageFormat.getKey(),
                demoImageFormat.getValue()};
        JCommander.newBuilder().addObject(cliParams).build().parse(args);

        assertEquals(Integer.parseInt(sampleVal), cliParams.samplesNumber());
        assertEquals(Integer.parseInt(iterVal), cliParams.iterPerSample());
    }

    @ParameterizedTest
    @CsvSource({"-samples, asfasfa", "-samples, -h", "--samples-number, -132"})
    @DisplayName("Ensure reading incorrect samples number throws a Parameter Exception")
    void ensureReadingIncorrectSamplesNumberThrowsException(String fl, String val) {
        String[] args =
            new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(), demoHeight.getValue(),
                fl, val, demoIterPerSample.getKey(), demoIterPerSample.getValue(),
                demoVariations.getKey(), demoVariations.getValue(), demoImageFormat.getKey(),
                demoImageFormat.getValue()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @ParameterizedTest
    @CsvSource({"-iter, asfasfa", "-iter, -w", "--iter-per-sample, -132"})
    @DisplayName("Ensure reading incorrect iters number throws a Parameter Exception")
    void ensureReadingIncorrectItersNumberThrowsException(String fl, String val) {
        String[] args =
            new String[] {demoHeight.getKey(), demoHeight.getValue(), demoWidth.getKey(), demoWidth.getValue(),
                demoSamples.getKey(), demoSamples.getValue(), fl, val,
                demoVariations.getKey(), demoVariations.getValue(), demoImageFormat.getKey(),
                demoImageFormat.getValue()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @Test
    @DisplayName("Ensure the absence of samples causes a Parameter Exception")
    void ensureAbsenceSamplesCausesException() {
        String[] args =
            new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(), demoHeight.getValue(),
                demoIterPerSample.getKey(), demoIterPerSample.getValue(),
                demoVariations.getKey(), demoVariations.getValue(), demoImageFormat.getKey(),
                demoImageFormat.getValue()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @Test
    @DisplayName("Ensure the absence of iters number causes a Parameter Exception")
    void ensureAbsenceIterNumberCausesException() {
        String[] args =
            new String[] {demoHeight.getKey(), demoHeight.getValue(), demoWidth.getKey(), demoWidth.getValue(),
                demoSamples.getKey(), demoSamples.getValue(),
                demoVariations.getKey(), demoVariations.getValue(), demoImageFormat.getKey(),
                demoImageFormat.getValue()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @ParameterizedTest
    @CsvSource({"--symmetry-axes-number, 10", "-symmetry, 4"})
    @DisplayName("Ensure reading symmetry number is working correctly")
    void ensureReadingSymmetryNumberIsWorkingCorrectly(String flag, String val) {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue(), flag, val};
        JCommander.newBuilder().addObject(cliParams).build().parse(args);

        assertEquals(Integer.parseInt(val), cliParams.symmetry());
    }

    @ParameterizedTest
    @CsvSource({"--symmetry-axes-number, -1", "-symmetry, sfdlkfd"})
    @DisplayName("Ensure reading incorrect symmetry number throws a Parameter Exception")
    void ensureReadingIncorrectSymmetryNumberThrowsException(String flag, String val) {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue(), flag, val};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @Test
    @DisplayName("Ensure the value for --one-thread flag is set correctly")
    void ensureOneThreadIsSetCorrectly() {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue(), "--one-thread"};

        JCommander.newBuilder().addObject(cliParams).build().parse(args);
        assertThat(cliParams.useOneThread()).isTrue();
    }

    @Test
    @DisplayName("Ensure --one-thread is false by default")
    void ensureOneThreadFalseByDefault() {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue()};

        JCommander.newBuilder().addObject(cliParams).build().parse(args);
        assertThat(cliParams.useOneThread()).isFalse();
    }

    @Test
    @DisplayName("Ensure threadNumber is 1 by default")
    void ensureThreadNumberIs1(){
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue()};

        JCommander.newBuilder().addObject(cliParams).build().parse(args);
        assertEquals(1, cliParams.threadsNumber());
    }

    @ParameterizedTest
    @CsvSource({"--multi-threads, 10", "--multi-threads, 2"})
    @DisplayName("Ensure reading threads number is working correctly")
    void ensureReadingThreadsNumberIsWorkingCorrectly(String flag, String val) {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue(), flag, val};
        JCommander.newBuilder().addObject(cliParams).build().parse(args);

        assertEquals(Integer.parseInt(val), cliParams.threadsNumber());
    }

    @ParameterizedTest
    @CsvSource({"--multi-threads, -1", "--multi-threads, sfdlkfd"})
    @DisplayName("Ensure reading incorrect threads number throws a Parameter Exception")
    void ensureReadingIncorrectThreadsNumberThrowsException(String flag, String val) {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue(), flag, val};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @Test
    @DisplayName("Ensure throwing a Parameter Exception if no passed width value")
    void ensureThrowingExceptionIfNoPassedWidthValue() {
        String[] args = new String[] {demoWidth.getKey(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @Test
    @DisplayName("Ensure throwing a Parameter Exception if no passed height value")
    void ensureThrowingExceptionIfNoPassedHeightValue() {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @Test
    @DisplayName("Ensure throwing a Parameter Exception if no passed samples value")
    void ensureThrowingExceptionIfNoPassedSamplesValue() {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @Test
    @DisplayName("Ensure throwing a Parameter Exception if no passed iters value")
    void ensureThrowingExceptionIfNoPassedItersValue() {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @Test
    @DisplayName("Ensure throwing a Parameter Exception if no passed variations value")
    void ensureThrowingExceptionIfNoPassedVariationsValue() {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(),
            demoImageFormat.getKey(), demoImageFormat.getValue()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @Test
    @DisplayName("Ensure throwing a Parameter Exception if no passed imageFormat value")
    void ensureThrowingExceptionIfNoPassedImageFormatValue() {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @Test
    @DisplayName("Ensure throwing a Parameter Exception if no passed affinesNumber value")
    void ensureThrowingExceptionIfNoPassedAffinesNumberValue() {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue(), demoAffines.getKey()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @Test
    @DisplayName("Ensure throwing a Parameter Exception if no passed symmetry value")
    void ensureThrowingExceptionIfNoPassedSymmetryValue() {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue(), demoSymmetry.getKey()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }

    @Test
    @DisplayName("Ensure throwing a Parameter Exception if no passed treads value")
    void ensureThrowingExceptionIfNoPassedThreadsValue() {
        String[] args = new String[] {demoWidth.getKey(), demoWidth.getValue(), demoHeight.getKey(),
            demoHeight.getValue(), demoSamples.getKey(), demoSamples.getValue(), demoIterPerSample.getKey(),
            demoIterPerSample.getValue(), demoVariations.getKey(), demoVariations.getValue(),
            demoImageFormat.getKey(), demoImageFormat.getValue(), demoMultiThreads.getKey()};

        assertThrows(ParameterException.class,
            () -> JCommander.newBuilder().addObject(cliParams).build().parse(args));
    }
}
