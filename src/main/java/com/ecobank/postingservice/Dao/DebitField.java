package com.ecobank.postingservice.Dao;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class DebitField {
    String amttype;
    String txncurrency;
    int seqno;
    String txndesc;
    String trantype;
    String instrumentno;
    String accountnum;
    String accountbranch;
    String crdrflag;
    int txnamount;
    int exrate;


    public String getAmttype() {
        return amttype;
    }

    public void setAmttype(String amttype) {
        this.amttype = amttype;
    }

    public String getTxncurrency() {
        return txncurrency;
    }

    public void setTxncurrency(String txncurrency) {
        this.txncurrency = txncurrency;
    }

    public int getSeqno() {
        return seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

    public String getTxndesc() {
        return txndesc;
    }

    public void setTxndesc(String txndesc) {
        this.txndesc = txndesc;
    }

    public String getTrantype() {
        return trantype;
    }

    public void setTrantype(String trantype) {
        this.trantype = trantype;
    }

    public String getInstrumentno() {
        return instrumentno;
    }

    public void setInstrumentno(String instrumentno) {
        this.instrumentno = instrumentno;
    }

    public String getAccountnum() {
        return accountnum;
    }

    public void setAccountnum(String accountnum) {
        this.accountnum = accountnum;
    }

    public String getAccountbranch() {
        return accountbranch;
    }

    public void setAccountbranch(String accountbranch) {
        this.accountbranch = accountbranch;
    }

    public String getCrdrflag() {
        return crdrflag;
    }

    public void setCrdrflag(String crdrflag) {
        this.crdrflag = crdrflag;
    }

    public int getTxnamount() {
        return txnamount;
    }

    public void setTxnamount(int txnamount) {
        this.txnamount = txnamount;
    }

    public int getExrate() {
        return exrate;
    }

    public void setExrate(int exrate) {
        this.exrate = exrate;
    }

    @Override
    public String toString() {
        return "DebitField{" +
                "amttype='" + amttype + '\'' +
                ", txncurrency='" + txncurrency + '\'' +
                ", seqno=" + seqno +
                ", txndesc='" + txndesc + '\'' +
                ", trantype='" + trantype + '\'' +
                ", instrumentno='" + instrumentno + '\'' +
                ", accountnum='" + accountnum + '\'' +
                ", accountbranch='" + accountbranch + '\'' +
                ", crdrflag='" + crdrflag + '\'' +
                ", txnamount=" + txnamount +
                ", exrate=" + exrate +
                '}';
    }
}
