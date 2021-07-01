package images;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class ImageHandler {
    private static Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();

    public static void addImage(String name, BufferedImage image) {
        images.put(name, image);
    }

    public static void addImageFromDirectory(String dirName) throws IOException {
        Files.list(new File(dirName).toPath()).forEach(path -> {
            try {
                String fileName = path.getFileName().toString();
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
                BufferedImage inputImage = ImageIO.read(new File(path.toString()));
                images.put(fileName, inputImage);
            } catch (Exception err) {
                err.printStackTrace();
            }
        });
    }
    
    public static BufferedImage getImage(String name) {
        return images.get(name);
    }

    public static BufferedImage getOpacityImage(String name, double opacity){
        return setImageOpacity(images.get(name), (int)(opacity*256));
    }

    public static BufferedImage setImageOpacity(BufferedImage img, int alpha) throws RuntimeException{
        BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        try {
            for (int i = 0;i < img.getWidth();i++) {
                for (int j = 0;j < img.getHeight();j++) {
                    Color color = new Color(img.getRGB(i, j));
                    Color newColor = new Color(color.getRed(), color.getGreen(),color.getBlue(), alpha);
                    newImage.setRGB(i,j,newColor.getRGB());
                }
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return newImage;
    }
}
