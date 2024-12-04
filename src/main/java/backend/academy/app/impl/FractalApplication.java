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
import java.io.IOException;
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
        int threadNumber = (cliParams.useOneThread() ? 1 : cliParams.threadsNumber());

        ImageGenerationConfig config =
            new ImageGenerationConfig(width, height, affinesNumber, variations, samples, (short) itersPerSample,
                symmetry,
                format, threadNumber);

        processGeneration(new FractalImage(height, width), config);
    }

    private void processGeneration(
        FractalImage fractalImage,
        ImageGenerationConfig config
    ) throws IOException {
        ioHandler.write("Генерация изображения начата... [Количество потоков: " + config.threadNumber() + "]\n");
        long start = System.nanoTime();
        FractalImage image = renderer.render(
            config.affinesCount(),
            new FractalImage(fractalImage.height(), fractalImage.width()),
            config.variationList(),
            config.samples(),
            config.iterPerSample(),
            config.symmetry(),
            config.threadNumber()
        );
        long elapsedTime = System.nanoTime() - start;

        ImageUtils.saveData(image, config, elapsedTime);
        ioHandler.write("Генерация изображения завершена!\n");
    }
}
