package com.yada.ssp.mtnServer.view;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MsgInfo {

    private String versionNo; // 版本号
    private String timeStamp; // 时间戳
    private String orgId; // 机构ID

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return "MsgInfo{" + "versionNo='" + versionNo + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", orgId='" + orgId + '\'' +
                '}';
    }
}
