package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            throw new Exception("Nieprawidłowa ilość parametrów");
        }
        String in = new File(args[0]).getAbsolutePath();
        String out = new File(args[1]).getAbsolutePath();

        int[] threads = {1, 4, 6};
        try {
            List<Path> files;
            Path source = Path.of(in);
            Stream<Path> stream = Files.list(source);
            files = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int threadCount : threads) {
            System.out.println(threadCount);
            long time = System.currentTimeMillis();


            System.out.println(System.currentTimeMillis() - time);
        }
    }

    private static BufferedImage transformImage(BufferedImage image) {
        BufferedImage transformedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = transformedImage.getRGB(x, y);
                Color color = new Color(rgb);
                int red = color.getRed();
                int blue = color.getBlue();
                int green = color.getGreen();
                Color outColor = new Color(red, blue, green);
                int outRgb = outColor.getRGB();

                transformedImage.setRGB(x, y, outRgb);
            }
        }
        return transformedImage;
    }

    private static void saveImage(BufferedImage image, String outputDirectory, String fileName) {
        try {
            File outputDir = new File(outputDirectory);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            String outputPath = outputDirectory + File.separator + fileName;
            ImageIO.write(image, "jpg", new File(outputPath));
        } catch (IOException e) {
            throw new RuntimeException("Error saving image: " + e.getMessage());
        }
    }
}
