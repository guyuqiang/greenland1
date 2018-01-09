package hdcz.com.app.greenland1.util;

/**
 * Created by guyuqiang on 2018/1/4.10:33
 */

public class ReturnMessage {
    private Integer status;
    private String message;

    public ReturnMessage(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
    public ReturnMessage(){}
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ReturnMessage{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
