package com.zkkj.backend.entity.biz;

public class FlightInfo {
    private Integer id;

    private String flightNo;

    private String depCity;

    private String passCity;

    private String arrCity;

    private String takeoffTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo == null ? null : flightNo.trim();
    }

    public String getDepCity() {
        return depCity;
    }

    public void setDepCity(String depCity) {
        this.depCity = depCity == null ? null : depCity.trim();
    }

    public String getPassCity() {
        return passCity;
    }

    public void setPassCity(String passCity) {
        this.passCity = passCity == null ? null : passCity.trim();
    }

    public String getArrCity() {
        return arrCity;
    }

    public void setArrCity(String arrCity) {
        this.arrCity = arrCity == null ? null : arrCity.trim();
    }

    public String getTakeoffTime() {
        return takeoffTime;
    }

    public void setTakeoffTime(String takeoffTime) {
        this.takeoffTime = takeoffTime == null ? null : takeoffTime.trim();
    }
}