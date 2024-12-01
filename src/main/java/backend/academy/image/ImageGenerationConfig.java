package backend.academy.image;

import backend.academy.format.ImageFormat;
import backend.academy.transformations.variations.Variation;
import java.util.List;

/**
 * A model that stores the parameters of the generated image
 *
 * @param width         width of the image
 * @param height        height of the image
 * @param affinesCount  number of affine transformations
 * @param variationList list of variations
 * @param samples       number of samples
 * @param iterPerSample number of iterations
 * @param symmetry      symmetry axes number
 * @param imageFormat   format of the image
 * @param threadNumber  number of threads
 */
@SuppressWarnings("RecordComponentNumber")
public record ImageGenerationConfig(int width, int height, int affinesCount, List<Variation> variationList, int samples,
                                    short iterPerSample, int symmetry, ImageFormat imageFormat, int threadNumber) {
    public String getConfig() {
        StringBuilder config = new StringBuilder();
        config.append("Ширина изображения: ").append(width).append('\n');
        config.append("Высота изображения: ").append(height).append('\n');
        config.append("Количество аффинных преобразований: ").append(affinesCount).append('\n');
        config.append("Выбранные вариации: \n");
        for (Variation variation : variationList) {
            config.append('\t').append(variation.getClass().getSimpleName()).append('\n');
        }
        config.append("Количество сэмплов: ").append(samples).append('\n');
        config.append("Количество итераций на сэмпл: ").append(iterPerSample).append('\n');
        config.append("Количество осей симметрии: ").append(symmetry).append('\n');
        config.append("Количество потоков: ").append(threadNumber).append('\n');
        config.append("Формат файла: ").append(imageFormat.name()).append('\n');
        return config.toString();
    }
}
