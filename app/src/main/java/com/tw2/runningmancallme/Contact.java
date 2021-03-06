package com.tw2.runningmancallme;

public class Contact {
    private String name;
    private String image;
    private String call;

    public Contact() {
    }

    public Contact(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public Contact(String name, String image, String call) {
        this.name = name;
        this.image = image;
        this.call = call;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
