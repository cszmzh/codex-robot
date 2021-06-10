package enums;

import config.RobotConfig;
import lombok.Getter;

@Getter
public enum ResultEnum {

    /**
     * 天气服务请求异常
     */
    WEATHER_SERVER_EXCEPTION(1000, RobotConfig.SERVER_NAME + "的天气服务异常，请稍后再试试哦！");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
