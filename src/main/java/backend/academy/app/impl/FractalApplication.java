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
import static backend.academy.utils.ImageUtils.PATH_TO_ROOT_DIR;

@SuppressFBWarnings("PATH_TRAVERSAL_IN")
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
        int threadNumber = (cliParams.useOneThread() ? 1 : cliParams.threadsNumber());

        ImageGenerationConfig config = new ImageGenerationConfig(
            width, height, affinesNumber, variations, samples, (short) itersPerSample, symmetry,
            format, threadNumber
        );

        ioHandler.write("Генерация изображения начата... [Количество потоков: " + config.threadNumber() + "]\n");

        long start = System.nanoTime();
        FractalImage image = renderer.render(
            config.affinesCount(),
            new FractalImage(height, width),
            config.variationList(),
            config.samples(),
            config.iterPerSample(),
            config.symmetry(),
            config.threadNumber()
        );
        long elapsedTimeInSeconds = (System.nanoTime() - start) / NANOSECONDS_IN_SECOND;

        File dir =
            new File(PATH_TO_ROOT_DIR,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-LL-yyyy_HHmmss")));
        if (!dir.mkdir()) {
            throw new IOException("Ошибка создания директории");
        }

        String logPath = dir + "/info.log";
        String imagePath = dir + "/image." + config.imageFormat().name().toLowerCase();
        IOHandler fileHandler = new IOHandlerImpl(System.in, Files.newOutputStream(Path.of(logPath)));

        ImageUtils.saveData(fileHandler, config, image, imagePath, elapsedTimeInSeconds);

        ioHandler.write("Генерация изображения завершена!\n");
    }

    public static final long NANOSECONDS_IN_SECOND = 1_000_000_000;
}
