
package com.alonsorios.myapplication.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseVinculo {

    @SerializedName("result")
    @Expose
    private String result;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseVinculo() {
    }

    /**
     * 
     * @param result
     */
    public ResponseVinculo(String result) {
        super();
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
