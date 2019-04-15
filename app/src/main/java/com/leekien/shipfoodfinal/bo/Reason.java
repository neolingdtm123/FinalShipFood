package com.leekien.shipfoodfinal.bo;

public class Reason {
    boolean check;
    String text;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Reason(boolean check, String text) {
        this.check = check;
        this.text = text;
    }
}
