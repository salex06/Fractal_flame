package backend.academy.app.impl;

import backend.academy.app.Application;
import backend.academy.cli.CliParams;
import backend.academy.format.ImageFormat;
import backend.academy.image.FractalImage;
import backend.academy.render.Renderer;
import backend.academy.transformations.variations.Variation;
import backend.academy.utils.IOHandler;
import backend.academy.utils.ImageUtils;
import backend.academy.utils.impl.IOHandlerImpl;
import com.beust.jcommander.JCommander;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FractalApplication implements Application {
    private final IOHandler ioHandler;
    private final Renderer renderer;

    public FractalApplication() {
        ioHandler = new IOHandlerImpl();
        renderer = new Renderer();
    }

    @Override
    public void run(String[] args) throws IOException {
        CliParams cliParams = new CliParams();
        JCommander.newBuilder().addObject(cliParams).build().parse(args);

        int width = cliParams.width();
        int height = cliParams.height();
        int affinesNumber = cliParams.affinesNumber();
        List<Variation> variations = cliParams.variations();
        int samples = cliParams.samplesNumber();
        int itersPerSample = cliParams.iterPerSample();
        int symmetry = cliParams.symmetry();
        ImageFormat format = cliParams.imageFormat();
        int threadNumber = cliParams.threadsNumber();

        if (cliParams.useOneThread()) {
            String config =
                getConfig(width, height, affinesNumber, variations, samples, (short) itersPerSample, symmetry,
                    1, format);
            processGeneration(affinesNumber, new FractalImage(height, width), variations, samples,
                (short) itersPerSample,
                symmetry, 1, config, format);
        }
        if (cliParams.threadsNumber() > 1) {
            String config =
                getConfig(width, height, affinesNumber, variations, samples, (short) itersPerSample, symmetry,
                    threadNumber, format);
            processGeneration(affinesNumber, new FractalImage(height, width), variations, samples,
                (short) itersPerSample,
                symmetry, threadNumber, config, format);
        }

    }

    @SuppressWarnings("ParameterNumber")
    private void processGeneration(
        int affinesNumber,
        FractalImage fractalImage,
        List<Variation> variations,
        int samples,
        short itersPerSample,
        int symmetry,
        int threadNumber,
        String config,
        ImageFormat format
    ) throws IOException {
        ioHandler.write("Генерация изображения начата... [" + threadNumber + " потоков]\n");
        long start = System.nanoTime();
        FractalImage image = renderer.render(
            affinesNumber,
            new FractalImage(fractalImage.height(), fractalImage.width()),
            variations,
            samples,
            itersPerSample,
            symmetry,
            threadNumber
        );
        long elapsedTime = System.nanoTime() - start;

        saveData(image, config, format, elapsedTime);
        ioHandler.write("Генерация изображения завершена!\n");
    }

    @SuppressFBWarnings({"PATH_TRAVERSAL_IN", "PATH_TRAVERSAL_OUT"})
    private void saveData(FractalImage image, String config, ImageFormat format, long elapsedTime) throws IOException {
        File dir =
            new File(PATH_TO_ROOT_DIR,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-LL-yyyy_HHmmss")));
        if (!dir.mkdir()) {
            ioHandler.write("Ошибка создания директории");
            return;
        }
        String logPath = dir + "/info.log";
        IOHandler fileHandler = new IOHandlerImpl(System.in, Files.newOutputStream(Path.of(logPath)));
        fileHandler.write(config);
        fileHandler.write("Затраченное время (в секундах): " + elapsedTime / NANOSECONDS_IN_SECOND + '\n');

        String imagePath = dir + "/image." + format.name().toLowerCase();
        ImageUtils.save(image, Path.of(imagePath), format);
    }

    @SuppressWarnings("ParameterNumber")
    private String getConfig(
        int width,
        int height,
        int affineCount,
        List<Variation> variations,
        int samples,
        short iterPerSamples,
        int symmetry,
        int threadNumber,
        ImageFormat imageFormat
    ) {
        StringBuilder config = new StringBuilder();
        config.append("Ширина изображения: ").append(width).append('\n');
        config.append("Высота изображения: ").append(height).append('\n');
        config.append("Количество аффинных преобразований: ").append(affineCount).append('\n');
        config.append("Выбранные вариации: \n");
        for (Variation variation : variations) {
            config.append('\t').append(variation.getClass().getSimpleName()).append('\n');
        }
        config.append("Количество сэмплов: ").append(samples).append('\n');
        config.append("Количество итераций на сэмпл: ").append(iterPerSamples).append('\n');
        config.append("Количество осей симметрии: ").append(symmetry).append('\n');
        config.append("Количество потоков: ").append(threadNumber).append('\n');
        config.append("Формат файла: ").append(imageFormat.name()).append('\n');
        return config.toString();
    }

    private static final long NANOSECONDS_IN_SECOND = 1_000_000_000;
    private static final String PATH_TO_ROOT_DIR = "src/main/resources/";
}
