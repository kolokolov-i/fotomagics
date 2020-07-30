package com.lilpeace.fotomagics.filters;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class GrayFilter implements Filter {

    private GrayFilter() {}

    @Override
    public BufferedImage apply(BufferedImage src) {
        int
                w = src.getWidth(),
                h = src.getHeight(),
                tp = src.getType();
        BufferedImage result = new BufferedImage(w, h,tp);
        Raster srcData = src.getData();
        WritableRaster resultRaster = result.getRaster();
        int[] srcBuf = new int[3];
        int[] resBuf = new int[3];
        for(int i = 0; i<h; i++){
            for(int j = 0; j<w; j++){
                srcData.getPixel(j, i, srcBuf);
                double m = srcBuf[0] * 0.072 + srcBuf[1] * 0.715 + srcBuf[2] * 0.213;
                int t = (int) (m);
                resBuf[0] = t;
                resBuf[1] = t;
                resBuf[2] = t;
                resultRaster.setPixel(j, i, resBuf);
            }
        }
        return result;
    }

    public static class Fabric extends FilterFabric {

        @Override
        public Filter getInstance() {
            return new GrayFilter();
        }

        @Override
        public String getName() {
            return "Оттенки серого";
        }
    }
}
