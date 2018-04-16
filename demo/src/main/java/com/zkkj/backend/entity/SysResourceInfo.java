package com.zkkj.backend.entity;

import java.util.Date;

public class SysResourceInfo {
    private Integer resourceId;

    private Integer operatorId;

    private Integer businessId;

    private String businessType;

    private String resourceType;

    private Integer resourceSeq;

    private String name;

    private String originalName;

    private Long fileSize;

    private String status;

    private String existMiddle;

    private String defaultMiddle;

    private Integer duration;

    private Date addedTime;

    private String resourcePath;

    private String resourceDesc;

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType == null ? null : resourceType.trim();
    }

    public Integer getResourceSeq() {
        return resourceSeq;
    }

    public void setResourceSeq(Integer resourceSeq) {
        this.resourceSeq = resourceSeq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName == null ? null : originalName.trim();
    }


    public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getExistMiddle() {
        return existMiddle;
    }

    public void setExistMiddle(String existMiddle) {
        this.existMiddle = existMiddle == null ? null : existMiddle.trim();
    }

    public String getDefaultMiddle() {
        return defaultMiddle;
    }

    public void setDefaultMiddle(String defaultMiddle) {
        this.defaultMiddle = defaultMiddle == null ? null : defaultMiddle.trim();
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(Date addedTime) {
        this.addedTime = addedTime;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath == null ? null : resourcePath.trim();
    }

    public String getResourceDesc() {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc == null ? null : resourceDesc.trim();
    }
}