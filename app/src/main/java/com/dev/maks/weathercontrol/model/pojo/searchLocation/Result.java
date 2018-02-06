
package com.dev.maks.weathercontrol.model.pojo.searchLocation;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("RESULTS")
    @Expose
    private List<Location> rESULTS = null;

    public List<Location> getRESULTS() {
        return rESULTS;
    }

    public void setRESULTS(List<Location> location) {
        this.rESULTS = location;
    }
}
