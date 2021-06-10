package entity;

import lombok.Data;

@Data
public class Weather {
    private Integer id;
    private String cityX;
    private String cityY;
    private String cityName;
    private String centerName;
    private String pyName;
    private String state1;
    private String state2;
    private String stateDetailed;
    private String tem1;
    private String tem2;
    private String temNow;
    private String windState;
    private String windDir;
    private String windPower;
    private String humidity;
    private String time;
    private String url;
}
