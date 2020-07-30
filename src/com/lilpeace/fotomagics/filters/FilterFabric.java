package com.lilpeace.fotomagics.filters;

public abstract class FilterFabric {

    public abstract Filter getInstance();
    public abstract String getName();

    @Override
    public String toString(){
        return getName();
    }
}
