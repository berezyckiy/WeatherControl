
package com.dev.maks.weathercontrol.model.pojo.weatherOfLocation.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SnowDay {

    @SerializedName("in")
    @Expose
    private double in;
    @SerializedName("cm")
    @Expose
    private double cm;

    public double getIn() {
        return in;
    }

    public void setIn(double in) {
        this.in = in;
    }

    public double getCm() {
        return cm;
    }

    public void setCm(double cm) {
        this.cm = cm;
    }

}
