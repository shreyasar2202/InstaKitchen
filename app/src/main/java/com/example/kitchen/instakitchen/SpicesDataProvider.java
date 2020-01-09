package com.example.kitchen.instakitchen;

import android.widget.ProgressBar;

/**
 * Created by Namitha on 03-07-2017.
 */

public class SpicesDataProvider {
    private int spice_image_resource;
    private String spice_name;
    private ProgressBar quantity_bar;
    private int quanTv;

    public SpicesDataProvider(int spice_image_resource, String spice_name, ProgressBar quantity_bar,int quanTv) {
        this.setSpice_image_resource(spice_image_resource);
        this.setSpice_name(spice_name);
        this.setQuantity_bar(quantity_bar);
        this.setQuanTv(quanTv);
    }

    /*public SpicesDataProvider(String spice_name, ProgressBar quantity_bar,int quanTv) {
        this.setSpice_name(spice_name);
        this.setQuantity_bar(quantity_bar);
        this.setQuanTv(quanTv);
    }*/
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

    public ProgressBar getQuantity_bar() {
        return quantity_bar;
    }

    public void setQuantity_bar(ProgressBar quantity_bar) {
        this.quantity_bar = quantity_bar;
    }

    public Integer getProgressValue() { return this.quantity_bar.getProgress(); }

    public void setQuanTv(int quanTv) {
        this.quanTv = quanTv;
    }

    public int getQuanTv(){
        return this.quanTv;
    }

}