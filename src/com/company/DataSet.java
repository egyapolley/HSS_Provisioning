package com.company;

public class DataSet {

    private String msisdn;
    private String imsi;
    private String iccid;
    private String keys;
    private String profileID;

    public DataSet() {
    }

    public DataSet(String msisdn, String imsi, String iccid, String keys) {
        this.msisdn = msisdn;
        this.imsi = imsi;
        this.iccid = iccid;
        this.keys = keys;
    }


    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getProfileID() {
        return profileID;
    }

    public void setProfileID(String profileID) {
        this.profileID = profileID;
    }
}
