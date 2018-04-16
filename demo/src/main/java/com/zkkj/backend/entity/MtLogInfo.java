package com.zkkj.backend.entity;

import java.util.Date;

public class MtLogInfo {
    private Integer mtLogId;

    private Integer mtOperatorId;

    private String terminalUUID;

    private String sendType;

    private String authCode;

    private String authResult;

    private String sendResult;

    private String sendStatus;

    private Date addedTime;

    private String userMSISDN;

    private String content;

    public Integer getMtLogId() {
        return mtLogId;
    }

    public void setMtLogId(Integer mtLogId) {
        this.mtLogId = mtLogId;
    }

    public Integer getMtOperatorId() {
        return mtOperatorId;
    }

    public void setMtOperatorId(Integer mtOperatorId) {
        this.mtOperatorId = mtOperatorId;
    }

    public String getTerminalUUID() {
        return terminalUUID;
    }

    public void setTerminalUUID(String terminalUUID) {
        this.terminalUUID = terminalUUID == null ? null : terminalUUID.trim();
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType == null ? null : sendType.trim();
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode == null ? null : authCode.trim();
    }

    public String getAuthResult() {
        return authResult;
    }

    public void setAuthResult(String authResult) {
        this.authResult = authResult == null ? null : authResult.trim();
    }

    public String getSendResult() {
        return sendResult;
    }

    public void setSendResult(String sendResult) {
        this.sendResult = sendResult == null ? null : sendResult.trim();
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    public Date getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(Date addedTime) {
        this.addedTime = addedTime;
    }

    public String getUserMSISDN() {
        return userMSISDN;
    }

    public void setUserMSISDN(String userMSISDN) {
        this.userMSISDN = userMSISDN == null ? null : userMSISDN.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}