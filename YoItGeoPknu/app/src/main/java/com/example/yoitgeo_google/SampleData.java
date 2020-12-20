package com.example.yoitgeo_google;

public class SampleData {
    private String roomNum;
    private String roomName;

    public SampleData(String roomNum, String roomName) {
        this.roomNum = roomNum;
        this.roomName = roomName;
    }

    public String getRoomNum() {
        return this.roomNum;
    }

    public String getRoomName() {
        return this.roomName;
    }
}
