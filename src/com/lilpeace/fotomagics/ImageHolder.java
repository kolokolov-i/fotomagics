package com.lilpeace.fotomagics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageHolder extends JPanel {

    BufferedImage image;

    public ImageHolder() {
        setBackground(Color.WHITE);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(image == null) return;
        int
                panelW = getWidth(),
                panelH = getHeight(),
                picW = image.getWidth(),
                picH = image.getHeight(),
                resX, resY, resW, resH;
        if(picW < panelW && picH < panelH){
            resW = picW;
            resH = picH;
            resX = panelW / 2 - resW / 2;
            resY = panelH / 2 - resH / 2;
        }
        else {
            float mW, mH, mR;
            mW = ((float) panelW) / picW;
            mH = ((float) panelH) / picH;
            mR = Math.min(mW, mH);
            resW = (int) (picW * mR);
            resH = (int) (picH * mR);
            if(mW < mH){
                resX = 0;
                resY = (int) (panelH / 2 - (picH * mR / 2));
            }
            else{
                resX = (int) (panelW / 2 - (picW * mR / 2));
                resY = 0;
            }
        }
        g.drawImage(image, resX, resY, resW, resH, this);
    }
}
