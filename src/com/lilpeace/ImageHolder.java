package com.lilpeace;

import javax.swing.*;
import java.awt.*;

public class ImageHolder extends JPanel {

    Image image;

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public void paint(Graphics g) {
//        super.paint(g);
//        Toolkit tk = Toolkit.getDefaultToolkit();
//        Image img = tk.getImage("pic1.jpg");
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
