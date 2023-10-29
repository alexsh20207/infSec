package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class ImageProcess {
    private final String fileName;
    public ImageProcess(String fileName) {
        this.fileName = fileName;
    }
    BufferedImage fetchImage() {
        File f = new File(fileName);
        try {
            return ImageIO.read(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    void hideText(BufferedImage img , byte[] txt) {
        int i = 0, j = 0;
        for (byte b : txt) {
            for (int k = 7; k >= 0; k--) {
                Color c = new Color(img.getRGB(j, i));
                byte red = (byte)c.getRed();
                byte green = (byte)c.getGreen();
                byte blue = (byte)c.getBlue();
                int bitVal = (b >>> k) & 1;
                blue = (byte)((blue & 0xFE)| bitVal);
                green = (byte)((green & 0xFE)| bitVal);
                red = (byte)((red & 0xFE)| bitVal);
                Color newColor = new Color((red & 0xFF), (green & 0xFF), (blue & 0xFF));
                img.setRGB(j, i, newColor.getRGB());
                j++;
            }
            i++;
        }
        System.out.println("Text Hidden");
        createImgWithMsg(img);
    }

    void createImgWithMsg(BufferedImage img) {
        File file = new File("new" + fileName);
        try {
            ImageIO.write(img, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    BufferedImage newImageFetch() {
        File f = new File("new" + fileName);
        BufferedImage img;
        try {
            img = ImageIO.read(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    String revealMsg(int msgLen) {
        BufferedImage img = newImageFetch();
        byte[] msgBytes = extractHiddenBytes(img, msgLen);
        if(msgBytes == null)
            return null;
        return new String(msgBytes);
    }

    byte[] extractHiddenBytes(BufferedImage img, int size) {
        int i = 0;
        int j = 0;
        byte [] hiddenBytes = new byte[size];
        for (int l = 0; l < size; l++) {
            for (int k = 0; k < 8; k++) {
                Color c = new Color(img.getRGB(j, i));
                byte blue = (byte)c.getBlue();
                int red = (byte)c.getRed();
                int green = (byte)c.getGreen();
                hiddenBytes[l] = (byte) ((hiddenBytes[l] << 1) | (blue & 1) | (green & 1) | (red & 1));
                j++;
            }
            i++;
        }
        return hiddenBytes;
    }
}