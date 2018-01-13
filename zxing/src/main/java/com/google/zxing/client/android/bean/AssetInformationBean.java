package com.google.zxing.client.android.bean;

/**
 * Created by guyuqiang on 2018/1/3.11:40
 */

public class AssetInformationBean {
    //id
    private int id;
    //资产编码
    private String asset_code;
    //资产名称
    private String asset_name;
    //资产未盘点照片
    private String asset_wpdphoto;
    //资产盘点照片
    private String asset_pdphoto;
    //资产使用人
    private String asset_usename;
    //资产类型
    private String asset_type;
    //资产规格型号
    private String asset_spex;
    //资产计量单位
    private String asset_unit;
    //资产所属部门
    private String asset_belongdepart;
    //资产存放地点
    private String asset_savelocation;
    //资产使用地点
    private String asset_uselocation;
    //资产状态（报损或盘点）
    private String asset_status;
    //资产盘点状态（已盘点或未盘点）
    private String asset_pandstatus;
    //盘点编码
    private String asset_deffind1;
    //默认字段2
    private String asset_deffind2;

    public AssetInformationBean(int id, String asset_code, String asset_name, String asset_wpdphoto, String asset_pdphoto, String asset_usename, String asset_type, String asset_spex, String asset_unit, String asset_belongdepart, String asset_savelocation, String asset_uselocation, String asset_status, String asset_pandstatus, String asset_deffind1, String asset_deffind2) {
        this.id = id;
        this.asset_code = asset_code;
        this.asset_name = asset_name;
        this.asset_wpdphoto = asset_wpdphoto;
        this.asset_pdphoto = asset_pdphoto;
        this.asset_usename = asset_usename;
        this.asset_type = asset_type;
        this.asset_spex = asset_spex;
        this.asset_unit = asset_unit;
        this.asset_belongdepart = asset_belongdepart;
        this.asset_savelocation = asset_savelocation;
        this.asset_uselocation = asset_uselocation;
        this.asset_status = asset_status;
        this.asset_pandstatus = asset_pandstatus;
        this.asset_deffind1 = asset_deffind1;
        this.asset_deffind2 = asset_deffind2;
    }

    public AssetInformationBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsset_code() {
        return asset_code;
    }

    public void setAsset_code(String asset_code) {
        this.asset_code = asset_code;
    }

    public String getAsset_name() {
        return asset_name;
    }

    public void setAsset_name(String asset_name) {
        this.asset_name = asset_name;
    }

    public String getAsset_wpdphoto() {
        return asset_wpdphoto;
    }

    public void setAsset_wpdphoto(String asset_wpdphoto) {
        this.asset_wpdphoto = asset_wpdphoto;
    }

    public String getAsset_pdphoto() {
        return asset_pdphoto;
    }

    public void setAsset_pdphoto(String asset_pdphoto) {
        this.asset_pdphoto = asset_pdphoto;
    }

    public String getAsset_usename() {
        return asset_usename;
    }

    public void setAsset_usename(String asset_usename) {
        this.asset_usename = asset_usename;
    }

    public String getAsset_type() {
        return asset_type;
    }

    public void setAsset_type(String asset_type) {
        this.asset_type = asset_type;
    }

    public String getAsset_spex() {
        return asset_spex;
    }

    public void setAsset_spex(String asset_spex) {
        this.asset_spex = asset_spex;
    }

    public String getAsset_unit() {
        return asset_unit;
    }

    public void setAsset_unit(String asset_unit) {
        this.asset_unit = asset_unit;
    }

    public String getAsset_belongdepart() {
        return asset_belongdepart;
    }

    public void setAsset_belongdepart(String asset_belongdepart) {
        this.asset_belongdepart = asset_belongdepart;
    }

    public String getAsset_savelocation() {
        return asset_savelocation;
    }

    public void setAsset_savelocation(String asset_savelocation) {
        this.asset_savelocation = asset_savelocation;
    }

    public String getAsset_uselocation() {
        return asset_uselocation;
    }

    public void setAsset_uselocation(String asset_uselocation) {
        this.asset_uselocation = asset_uselocation;
    }

    public String getAsset_status() {
        return asset_status;
    }

    public void setAsset_status(String asset_status) {
        this.asset_status = asset_status;
    }

    public String getAsset_pandstatus() {
        return asset_pandstatus;
    }

    public void setAsset_pandstatus(String asset_pandstatus) {
        this.asset_pandstatus = asset_pandstatus;
    }

    public String getAsset_deffind1() {
        return asset_deffind1;
    }

    public void setAsset_deffind1(String asset_deffind1) {
        this.asset_deffind1 = asset_deffind1;
    }

    public String getAsset_deffind2() {
        return asset_deffind2;
    }

    public void setAsset_deffind2(String asset_deffind2) {
        this.asset_deffind2 = asset_deffind2;
    }

    @Override
    public String toString() {
        return "AssetInformationBean{" +
                "id=" + id +
                ", asset_code='" + asset_code + '\'' +
                ", asset_name='" + asset_name + '\'' +
                ", asset_wpdphoto='" + asset_wpdphoto + '\'' +
                ", asset_pdphoto='" + asset_pdphoto + '\'' +
                ", asset_usename='" + asset_usename + '\'' +
                ", asset_type='" + asset_type + '\'' +
                ", asset_spex='" + asset_spex + '\'' +
                ", asset_unit='" + asset_unit + '\'' +
                ", asset_belongdepart='" + asset_belongdepart + '\'' +
                ", asset_savelocation='" + asset_savelocation + '\'' +
                ", asset_uselocation='" + asset_uselocation + '\'' +
                ", asset_status='" + asset_status + '\'' +
                ", asset_pandstatus='" + asset_pandstatus + '\'' +
                ", asset_deffind1='" + asset_deffind1 + '\'' +
                ", asset_deffind2='" + asset_deffind2 + '\'' +
                '}';
    }
}
