package com.whut.analyst.domain;


import java.util.Arrays;

public class LineData {

    private double label;
    private double[] features;

    public LineData(String line) {
        String[] strings = line.split(" ");
        features = new double[strings.length-1];
        for (int i = 0; i<features.length; ++i){
            features[i] = Double.parseDouble(strings[i+1].split(":")[1]);
        }
        label = Double.parseDouble(strings[0]);
    }

    public double[] getFeatures() {
        return features;
    }

    public double getLabel(){
        return label;
    }

    @Override
    public String toString() {
        return "LineData{" +
                "label=" + label +
                ", features=" + Arrays.toString(features) +
                '}';
    }
}