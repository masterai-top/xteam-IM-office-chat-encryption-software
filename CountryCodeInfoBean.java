package com.xmen.xteam.communication.bean;

/**
 * Created by zhangqi on 2018/7/19.
 */

public class CountryCodeInfoBean {
    private String countryName;
    private String countryCode;

    public String getCountryName() {
        return countryName;
    }

    public CountryCodeInfoBean(String countryName, String countryCode) {
        this.countryName = countryName;
        this.countryCode = countryCode;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "CountryCodeInfoBean{" +
                "countryName='" + countryName + '\'' +
                ", countryCode=" + countryCode +
                '}';
    }
}
