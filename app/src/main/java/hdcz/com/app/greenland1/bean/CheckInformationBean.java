package hdcz.com.app.greenland1.bean;

/**
 * Created by guyuqiang on 2017/12/31.16:57
 */

public class CheckInformationBean {
    //盘点编号
    private String code;
    //发起人
    private String fqr;
    //申请时间
    private String fqsj;
    //开始时间到结束时间
    private String pdsj;
    //盘点类型
    private String pdlx;
    //盘点人
    private String pdr;
    private String deffind1;
    private String deffind2;
    public CheckInformationBean() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFqr() {
        return fqr;
    }

    public void setFqr(String fqr) {
        this.fqr = fqr;
    }

    public String getFqsj() {
        return fqsj;
    }

    public void setFqsj(String fqsj) {
        this.fqsj = fqsj;
    }

    public String getPdsj() {
        return pdsj;
    }

    public void setPdsj(String pdsj) {
        this.pdsj = pdsj;
    }

    public String getPdlx() {
        return pdlx;
    }

    public void setPdlx(String pdlx) {
        this.pdlx = pdlx;
    }

    public String getPdr() {
        return pdr;
    }

    public void setPdr(String pdr) {
        this.pdr = pdr;
    }

    public String getDeffind1() {
        return deffind1;
    }

    public void setDeffind1(String deffind1) {
        this.deffind1 = deffind1;
    }

    public String getDeffind2() {
        return deffind2;
    }

    public void setDeffind2(String deffind2) {
        this.deffind2 = deffind2;
    }

    @Override
    public String toString() {
        return "CheckInformationBean{" +
                "code='" + code + '\'' +
                ", fqr='" + fqr + '\'' +
                ", fqsj='" + fqsj + '\'' +
                ", pdsj='" + pdsj + '\'' +
                ", pdlx='" + pdlx + '\'' +
                ", pdr='" + pdr + '\'' +
                ", deffind1='" + deffind1 + '\'' +
                ", deffind2='" + deffind2 + '\'' +
                '}';
    }
}
