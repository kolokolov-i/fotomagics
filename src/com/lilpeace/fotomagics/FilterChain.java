package com.lilpeace.fotomagics;

import com.lilpeace.fotomagics.filters.Filter;

import java.awt.image.BufferedImage;

public class FilterChain {

    BufferedImage sourceImage, resultImage;

    public FilterChain(BufferedImage image) {
        sourceImage = image;
        resultImage = sourceImage;
    }

    public void add(Filter filter){
        resultImage = filter.apply(resultImage);
    }

    public BufferedImage getResultImage() {
        return resultImage;
    }
}
