package com.encentral.imageconverter.impl;

import akka.actor.UntypedAbstractActor;
import com.encentral.imageconverter.model.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageActor extends UntypedAbstractActor {
    private final ImageImpl sender;
    private boolean running = false;
    private Image image = null;
    private final int id;

    public ImageActor(ImageImpl sender) {
        this.sender = sender;
        id = 0;
    }

    public ImageActor(ImageImpl sender, int id) {
        this.sender = sender;
        this.id = id;
    }
    @Override
    public void onReceive(Object message) throws Throwable {


        if (message instanceof Image) {

            if (running) return;
            running = true;

            image = ((Image) message);
            invert();

            running = false;
        } else unhandled(message);
    }

    public void invert() {
        try {

            File f = new File(System.getProperty("user.dir") + "/public/uploads/images/" + image.getIdentifier() + '.' + image.getMimeType());


            BufferedImage bufferedImage = null;
            System.out.println(f);
            bufferedImage = ImageIO.read(f);

            int h = bufferedImage.getHeight();
            int w = bufferedImage.getWidth();

            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int rgb = bufferedImage.getRGB(i, j);
                    int a = rgb >> 24;
                    int b = rgb & 0xff;
                    int g = (rgb & 0xff00) >> 8;
                    int r = (rgb & 0xff0000) >> 16;
                    int red = g, green = b, blue = r;

                    int newRgb = (a << 24) + (red << 16) + (green << 8) + blue;
                    bufferedImage.setRGB(i, j, newRgb);


                }


            }

            String saveTo = System.getProperty("user.dir") + "/public/uploads/images-inverted/" + image.getIdentifier() + '.' + image.getMimeType();

            File outputfile = new File(saveTo);

            ImageIO.write(bufferedImage, image.getMimeType(), outputfile);
            this.postStop();
            System.out.println("actor " + id + " Completed\n Save file =" + outputfile);
            sender.onReceive(image, this.getSelf());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
