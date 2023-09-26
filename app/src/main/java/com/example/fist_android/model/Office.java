package com.example.fist_android.model;

import com.google.gson.annotations.SerializedName;

public class Office {
    @SerializedName("officeId")
    private String officeId;
    @SerializedName("officeName")
    private String officeName;
    @SerializedName("officeAddr")
    private String officeAddr;
    @SerializedName("officeTel")
    private String officeTel;

    public void setOfficeId(String officeId){
        this.officeId = officeId;
    }
    public String getOfficeId(){
        return this.officeId;
    }
    public void setOfficeName(String officeName){
        this.officeName = officeName;
    }
    public String getOfficeName(){
        return this.officeName;
    }
    public void setOfficeAddr(String officeAddr){
        this.officeAddr = officeAddr;
    }
    public String getOfficeAddr(){
        return this.officeAddr;
    }
    public void setOfficeTel(String officeTel){
        this.officeTel = officeTel;
    }
    public String getOfficeTel(){
        return this.officeTel;
    }

    public void printOfficeData(){
        System.out.println("===========Office===============");
        System.out.println("OfficeId : " + this.officeId);
        System.out.println("OfficeName : " + this.officeName);
        System.out.println("OfficeAddr : " + this.officeAddr);
        System.out.println("OfficeTel : " + this.officeTel);
        System.out.println("===============================");
    }

}
