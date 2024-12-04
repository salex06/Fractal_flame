package backend.academy.processing;

import backend.academy.image.FractalImage;

/**
 * Provides process method which performs some image processing
 */
@FunctionalInterface
public interface ImageProcessor {
    /**
     * Process the image
     * @param image the image that needs to be processed
     */
    void process(FractalImage image);
}
