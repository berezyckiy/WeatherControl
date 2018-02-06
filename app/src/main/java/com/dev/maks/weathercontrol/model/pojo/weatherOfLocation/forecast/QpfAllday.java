
package com.dev.maks.weathercontrol.model.pojo.weatherOfLocation.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QpfAllday {

    @SerializedName("in")
    @Expose
    private double in;
    @SerializedName("mm")
    @Expose
    private double mm;

    public double getIn() {
        return in;
    }

    public void setIn(double in) {
        this.in = in;
    }

    public double getMm() {
        return mm;
    }

    public void setMm(double mm) {
        this.mm = mm;
    }

}
