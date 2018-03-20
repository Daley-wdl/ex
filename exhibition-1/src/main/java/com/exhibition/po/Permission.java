package com.exhibition.po;

/**
 * Created by final on 17-5-20.
 */
public class Permission {

    private Integer permissionId;
    private String permissionName;

    public Permission(String permissionName) {
        this.permissionName = permissionName;
    }

    public Permission() {
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public String toString() {
        return "Permission{" + "permissionId=" + permissionId + ", permissionName='" + permissionName + '\'' + '}';
    }
}
