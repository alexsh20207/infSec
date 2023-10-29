package org.example;

import java.io.BufferedReader;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

class Main {
    public static void main(String [] args) {
        try {
            System.out.println("Enter message to encode: ");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String message = in.readLine();
            byte [] txtBytes = message.getBytes(StandardCharsets.UTF_8);
            ImageProcess impro = new ImageProcess("2.png");
            BufferedImage img = impro.fetchImage();
            impro.hideText(img, txtBytes);
            String decodedMsg = impro.revealMsg(txtBytes.length);
            System.out.println("Decoded message: " + decodedMsg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

