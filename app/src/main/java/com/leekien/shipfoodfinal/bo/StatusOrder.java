package com.leekien.shipfoodfinal.bo;

public class StatusOrder {
    String status;
    String time;
    String shipinfo;
    String shipPhone;
    boolean check =false;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getShipinfo() {
        return shipinfo;
    }

    public void setShipinfo(String shipinfo) {
        this.shipinfo = shipinfo;
    }

    public String getShipPhone() {
        return shipPhone;
    }

    public void setShipPhone(String shipPhone) {
        this.shipPhone = shipPhone;
    }
}
