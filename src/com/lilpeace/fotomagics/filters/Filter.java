package com.lilpeace.fotomagics.filters;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public interface Filter {

    BufferedImage apply(BufferedImage src);

    static ArrayList<FilterFabric> getFabrics(){
        ArrayList<FilterFabric> registry = new ArrayList<>();
        registry.add(new GrayFilter.Fabric());
        return registry;
    }

}
