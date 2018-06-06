package com.xz.entity;

public class UserLogin {
    private String id;

    private String userName;

    private String userPassword;

    private Integer userRole;

    private Integer isDelete;

    private String parentId;

    private String type;

    private String createDate;

    private String updateDate;

    private Integer allowPhoneSize;
    
    private Integer isAllowWeblogin;
    
    private String realName;
    
    private String enableTime;
    
    private String disableTime;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate == null ? null : updateDate.trim();
    }

	public Integer getAllowPhoneSize() {
		return allowPhoneSize;
	}

	public void setAllowPhoneSize(Integer allowPhoneSize) {
		this.allowPhoneSize = allowPhoneSize;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getIsAllowWeblogin() {
		return isAllowWeblogin;
	}

	public void setIsAllowWeblogin(Integer isAllowWeblogin) {
		this.isAllowWeblogin = isAllowWeblogin;
	}

	public String getEnableTime() {
		return enableTime;
	}

	public void setEnableTime(String enableTime) {
		this.enableTime = enableTime;
	}

	public String getDisableTime() {
		return disableTime;
	}

	public void setDisableTime(String disableTime) {
		this.disableTime = disableTime;
	}
	
}