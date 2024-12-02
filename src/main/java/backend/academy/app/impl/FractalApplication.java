package backend.academy.app.impl;

import backend.academy.app.Application;
import backend.academy.cli.CliParams;
import backend.academy.format.ImageFormat;
import backend.academy.image.FractalImage;
import backend.academy.image.ImageGenerationConfig;
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
            ImageGenerationConfig config =
                new ImageGenerationConfig(width, height, affinesNumber, variations, samples, (short) itersPerSample,
                    symmetry,
                    format, 1);
            processGeneration(new FractalImage(height, width), 1, config);
        }
        if (cliParams.threadsNumber() > 1) {
            ImageGenerationConfig config =
                new ImageGenerationConfig(width, height, affinesNumber, variations, samples, (short) itersPerSample,
                    symmetry,
                    format, threadNumber);
            processGeneration(new FractalImage(height, width), threadNumber, config);
        }
    }

    @SuppressWarnings("ParameterNumber")
    private void processGeneration(
        FractalImage fractalImage,
        int threadNumber,
        ImageGenerationConfig config
    ) throws IOException {
        ioHandler.write("Генерация изображения начата... [Количество потоков: " + threadNumber + "]\n");
        long start = System.nanoTime();
        FractalImage image = renderer.render(
            config.affinesCount(),
            new FractalImage(fractalImage.height(), fractalImage.width()),
            config.variationList(),
            config.samples(),
            config.iterPerSample(),
            config.symmetry(),
            threadNumber
        );
        long elapsedTime = System.nanoTime() - start;

        saveData(image, config, elapsedTime);
        ioHandler.write("Генерация изображения завершена!\n");
    }

    @SuppressFBWarnings({"PATH_TRAVERSAL_IN", "PATH_TRAVERSAL_OUT"})
    private void saveData(FractalImage image, ImageGenerationConfig config, long elapsedTime) throws IOException {
        File dir =
            new File(PATH_TO_ROOT_DIR,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-LL-yyyy_HHmmss")));
        if (!dir.mkdir()) {
            ioHandler.write("Ошибка создания директории");
            return;
        }
        String logPath = dir + "/info.log";
        IOHandler fileHandler = new IOHandlerImpl(System.in, Files.newOutputStream(Path.of(logPath)));
        fileHandler.write(config.getConfig());
        fileHandler.write("Затраченное время (в секундах): " + elapsedTime / NANOSECONDS_IN_SECOND + '\n');

        String imagePath = dir + "/image." + config.imageFormat().name().toLowerCase();
        ImageUtils.save(image, Path.of(imagePath), config.imageFormat());
    }

    private static final long NANOSECONDS_IN_SECOND = 1_000_000_000;
    private static final String PATH_TO_ROOT_DIR = "src/main/resources/";
}
