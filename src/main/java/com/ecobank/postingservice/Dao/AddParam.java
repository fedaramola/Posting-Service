package com.ecobank.postingservice.Dao;

import java.sql.Date;

public class AddParam {

    String platformid;
    String branch;
    String batchno;
    String reference;
    Date initdate;
    java.sql.Date valuedate;
    String errcode;
    String errparam;
    String event;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    String clientIp;

    private CreditField creditField;
    private  DebitField debitField;

    public CreditField getCreditField() {
        return creditField;
    }

    public void setCreditField(CreditField creditField) {
        this.creditField = creditField;
    }

    public DebitField getDebitField() {
        return debitField;
    }

    public void setDebitField(DebitField debitField) {
        this.debitField = debitField;
    }

    public String getPlatformid() {
        return platformid;
    }

    public void setPlatformid(String platformid) {
        this.platformid = platformid;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Date getInitdate() {
        return initdate;
    }

    public void setInitdate(Date initdate) {
        this.initdate = initdate;
    }

    public Date getValuedate() {
        return valuedate;
    }

    public void setValuedate(Date valuedate) {
        this.valuedate = valuedate;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrparam() {
        return errparam;
    }

    public void setErrparam(String errparam) {
        this.errparam = errparam;
    }

    @Override
    public String toString() {
        return "AddParam{" +
                "platformid='" + platformid + '\'' +
                ", branch='" + branch + '\'' +
                ", batchno='" + batchno + '\'' +
                ", reference='" + reference + '\'' +
                ", initdate=" + initdate +
                ", valuedate=" + valuedate +
                ", errcode='" + errcode + '\'' +
                ", errparam='" + errparam + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", creditField=" + creditField +
                ", debitField=" + debitField +
                '}';
    }
}
