package com.weather.WeatherForecast.services.common;

public class WeatherForecast {

    private String zipCode;
    private Double maxTemp;
    private Double minTemp;
    private Double latitude;
    private Double longitude;
    private Double currTemp;
    private boolean fromCache;

    public WeatherForecast() {}

    public WeatherForecast(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getCurrTemp() {
        return currTemp;
    }

    public void setCurrTemp(Double currTemp) {
        this.currTemp = currTemp;
    }

    public boolean isFromCache() {
        return fromCache;
    }

    public void setFromCache(boolean fromCache) {
        this.fromCache = fromCache;
    }
}
