package exception;

import enums.ResultEnum;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{

    private Integer code;
    private String msg;

    public ServiceException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public ServiceException(Integer code, String message){
        super(message);
        this.code = code;
    }

    public ServiceException(String msg){
        this.msg = msg;
    }
}
