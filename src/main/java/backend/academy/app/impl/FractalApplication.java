package backend.academy.app.impl;

import backend.academy.app.Application;
import backend.academy.format.ImageFormat;
import backend.academy.image.FractalImage;
import backend.academy.render.Renderer;
import backend.academy.transformations.variations.Variation;
import backend.academy.transformations.variations.VariationService;
import backend.academy.utils.IOHandler;
import backend.academy.utils.ImageUtils;
import backend.academy.utils.impl.IOHandlerImpl;
import java.io.IOException;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FractalApplication implements Application {
    private final IOHandler ioHandler;
    private final Renderer renderer;

    public FractalApplication() {
        ioHandler = new IOHandlerImpl();
        renderer = new Renderer();
    }

    @Override
    public void run() throws IOException {
        ioHandler.write("-Конфигурация изображения-\n");
        ioHandler.write("Введите ширину изображения: ");
        int width = Integer.parseUnsignedInt(ioHandler.read());
        ioHandler.write("Введите высоту изображения: ");
        int height = Integer.parseUnsignedInt(ioHandler.read());

        ioHandler.write("Укажите количество аффинных преобразований: ");
        int affinesCount = Integer.parseUnsignedInt(ioHandler.read());

        List<Variation> variationList = selectVariations().stream().toList();

        ioHandler.write("Введите количество сэмплов: ");
        int samples = Integer.parseUnsignedInt(ioHandler.read());

        ioHandler.write("Введите количество итераций на сэмпл: ");
        short itersPerSample = Short.parseShort(ioHandler.read());

        ioHandler.write("Укажите количество осей симметрии: ");
        int symmetry = Integer.parseUnsignedInt(ioHandler.read());

        ImageFormat format = selectFormat();

        showConfig(width, height, affinesCount, variationList, samples, itersPerSample, symmetry, format);

        ioHandler.write("Генерация изображения начата...\n");
        FractalImage image = renderer.render(
            affinesCount,
            new FractalImage(height, width),
            variationList,
            samples,
            itersPerSample,
            symmetry,
            new SecureRandom()
        );

        ImageUtils.save(image, Path.of("src/main/resources/out." + format.name().toLowerCase()), format);

        ioHandler.write("Генерация изображения завершена!\n");
    }

    private Set<Variation> selectVariations() throws IOException {
        ioHandler.write("Доступные вариации: \n");
        ioHandler.write(VariationService.getVariationsListAsString());

        List<Variation> available = VariationService.getVariationsList();
        Set<Variation> selected = new HashSet<>();
        int q;
        char command = 'y';
        do {
            ioHandler.write("Выберите вариацию: ");
            q = Integer.parseInt(ioHandler.read());
            if (q <= 0 || q > available.size()) {
                ioHandler.write("Неверный номер вариации!\n");
            } else if (selected.contains(available.get(q - 1))) {
                ioHandler.write("Вариация уже была выбрана!\n");
            } else {
                selected.add(available.get(q - 1));
            }

            if (!selected.isEmpty()) {
                ioHandler.write("Продолжить выбор вариаций? (y/n) ");
                command = ioHandler.read().charAt(0);
            }
        } while (command == 'y');

        return selected;
    }

    private ImageFormat selectFormat() throws IOException {
        ioHandler.write("Доступные форматы: \n");
        ioHandler.write(ImageFormat.getImageFormatsAsStrings());
        List<ImageFormat> available = ImageFormat.getImageFormats();
        ioHandler.write("Введите номер формата файла: ");
        return available.get(Integer.parseInt(ioHandler.read()) - 1);
    }

    @SuppressWarnings("ParameterNumber")
    private void showConfig(
        int width,
        int height,
        int affineCount,
        List<Variation> variations,
        int samples,
        short iterPerSamples,
        int symmetry,
        ImageFormat imageFormat
    ) throws IOException {
        ioHandler.write("-Текущая конфигурация-\n");
        ioHandler.write("Ширина изображения: " + width + '\n');
        ioHandler.write("Высота изображения: " + height + '\n');
        ioHandler.write("Количество аффинных преобразований: " + affineCount + '\n');
        ioHandler.write("Выбранные вариации: \n");
        for (Variation variation : variations) {
            ioHandler.write('\t' + variation.getClass().getSimpleName() + '\n');
        }
        ioHandler.write("Количество сэмплов: " + samples + '\n');
        ioHandler.write("Количество итераций на сэмпл: " + iterPerSamples + '\n');
        ioHandler.write("Количество осей симметрии: " + symmetry + '\n');
        ioHandler.write("Формат файла: " + imageFormat.name() + '\n');
    }
}
