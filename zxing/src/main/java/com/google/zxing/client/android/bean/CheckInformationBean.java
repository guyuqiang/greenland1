package com.google.zxing.client.android.bean;

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
    @Override
    public String toString() {
        return "CheckInformationBean{" +
                "code='" + code + '\'' +
                ", fqr='" + fqr + '\'' +
                ", fqsj='" + fqsj + '\'' +
                ", pdsj='" + pdsj + '\'' +
                ", pdlx='" + pdlx + '\'' +
                '}';
    }
}
