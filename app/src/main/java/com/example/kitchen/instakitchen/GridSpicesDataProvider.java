package com.example.kitchen.instakitchen;

/**
 * Created by Namitha on 15-08-2017.
 */

public class GridSpicesDataProvider {
    private int spice_image_resource;
    private String spice_name;


    public GridSpicesDataProvider(int spice_image_resource, String spice_name) {
        this.setSpice_image_resource(spice_image_resource);
        this.setSpice_name(spice_name);
    }

    public int getSpice_image_resource() {
        return spice_image_resource;
    }

    public void setSpice_image_resource(int spice_image_resource) {
        this.spice_image_resource = spice_image_resource;
    }

    public String getSpice_name() {
        return spice_name;
    }

    public void setSpice_name(String spice_name) {
        this.spice_name = spice_name;
    }

}

