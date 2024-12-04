package backend.academy.cli;

import backend.academy.format.ImageFormat;
import backend.academy.transformations.variations.Variation;
import backend.academy.transformations.variations.VariationService;
import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IParametersValidator;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/**
 * The class manages CLI, stores and processes
 * necessary flags and parameters for user input
 */
@Getter
@Parameters(parametersValidators = CliParams.ValidateUsingTreads.class)
public class CliParams {
    /**
     * The width of the generated image
     */
    @Parameter(names = {"--image-width", "-w"}, description = "width of the image",
        required = true, validateWith = PositiveNumberValidator.class)
    private int width;

    /**
     * The height of the generated image
     */
    @Parameter(names = {"--image-height", "-h"}, description = "height of the image",
        required = true, validateWith = PositiveNumberValidator.class)
    private int height;

    /**
     * The number of affine transformations
     */
    @Parameter(names = {"--affines-number", "-a"}, description = "the number of affine transformations (default is 10)",
        validateWith = PositiveNumberValidator.class)
    private int affinesNumber = DEFAULT_AFFINES_NUMBER;

    /**
     * The list of selected variations
     */
    @Parameter(names = {"--variation", "--variations", "-v"}, description = "choosing variations for the image",
        required = true, converter = StringToVariationConverter.class)
    private List<Variation> variations;

    /**
     * The number of samples
     */
    @Parameter(names = {"--samples-number", "-samples"}, description = "the number of samples",
        required = true, validateWith = PositiveNumberValidator.class)
    private int samplesNumber;

    /**
     * The number of iterations per sample
     */
    @Parameter(names = {"--iter-per-sample", "-iter"}, description = "the number of iterations per sample",
        required = true, validateWith = PositiveNumberValidator.class)
    private int iterPerSample;

    /**
     * The number of axes of symmetry
     */
    @Parameter(names = {"--symmetry-axes-number", "-symmetry"},
        description = "number of axes of symmetry (default is 1)", validateWith = PositiveNumberValidator.class)
    private int symmetry = DEFAULT_SYMMETRY_NUMBER;

    /**
     * A logical variable that defines the generation of an image in a single thread
     */
    @Parameter(names = "--one-thread",
        description = "A setting indicating the use of a single thread to generate an image")
    private boolean useOneThread = false;

    /**
     * The number of threads in the case of image generation in multithreaded mode
     */
    @Parameter(names = "--multi-threads", validateWith = PositiveNumberValidator.class)
    private int threadsNumber;

    /**
     * The file format of the generated image
     */
    @Parameter(names = {"--file-format", "-f"}, description = "Image file format",
        required = true, converter = StringToImageFormatConverter.class)
    private ImageFormat imageFormat;

    @Getter
    private static final int DEFAULT_AFFINES_NUMBER = 10;
    @Getter
    private static final int DEFAULT_SYMMETRY_NUMBER = 1;

    /**
     * Integer parameter handler
     */
    public static class PositiveNumberValidator implements IParameterValidator {
        /**
         * Check that the number is greater than zero
         *
         * @param s  flag name
         * @param s1 flag value
         * @throws ParameterException if the parameter was not validated
         */
        @Override
        public void validate(String s, String s1) throws ParameterException {
            try {
                if (Integer.parseInt(s1) <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                throw new ParameterException("An integer value was expected for the flag " + s
                    + " (was received: " + s1 + ')', e);
            }
        }
    }

    /**
     * Converter of the string representation of a parameter to an object
     * of the variation class
     */
    public static class StringToVariationConverter implements IStringConverter<Variation> {
        /**
         * Returns an object of the Variation class found by name
         *
         * @param s name of the variation
         * @return an object of the variation class
         */
        @Override
        public Variation convert(String s) {
            Variation variation = VariationService.getVariationByName(s);
            if (variation == null) {
                throw new ParameterException("No such variation: " + s + '\n'
                    + "Available variations: " + '\n' + VariationService.getVariationsList());
            }
            return variation;
        }
    }

    /**
     * Converter of the string representation of a parameter to an object
     * of the ImageFormat class
     */
    private static class StringToImageFormatConverter implements IStringConverter<ImageFormat> {
        /**
         * Returns an object of the ImageFormat class found by name
         *
         * @param s name of the image format
         * @return an object of the ImageFormat class
         */
        @Override
        public ImageFormat convert(String s) {
            try {
                return ImageFormat.valueOf(s.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ParameterException("No such format: " + s + '\n'
                    + "Available formats: " + '\n' + ImageFormat.getImageFormatsAsStrings(), e);
            }
        }
    }

    /**
     * Validator for checking the use of only one mode
     * (single-threaded or multithreaded)
     */
    public static class ValidateUsingTreads implements IParametersValidator {
        /**
         * Checks the use of only one mode
         *
         * @param map parameter map
         */
        @Override
        public void validate(Map<String, Object> map) throws ParameterException {
            String oneThread = "--one-thread";
            String multiThreads = "--multi-threads";
            if (map.get(oneThread) != null && map.get(multiThreads) != null) {
                throw new ParameterException(
                    "It's possible to use only one mode (--one-thread or --multi-threads) within a single run");
            } else if (map.get(oneThread) == null && map.get(multiThreads) == null) {
                throw new ParameterException(
                    "Mode selection is expected (--one-thread or --multi-threads)"
                );
            }
        }
    }
}
