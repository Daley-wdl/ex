package com.exhibition.enums;

import java.util.EnumSet;

/**
 * Created by final on 17-6-4.
 */
public enum RoleList {
    Admin("superadmin",1),
    Manager("admin",2),
    User("user",3),
    Exhibitor("exhibitor",4)
    ;

    private String roleName;

    private Integer roleId;

    RoleList(String roleName, Integer roleId) {
        this.roleName = roleName;
        this.roleId = roleId;
    }

    /**
     * 检查输入的role是否在该枚举的范围内
     * @param role
     * @return
     */
    public static boolean checkStatus(Integer role) {
        EnumSet<RoleList> enumSet = EnumSet.allOf(RoleList.class);
        for (RoleList rl : enumSet) {
            if (rl.getRoleId().equals(role)) {
                return true;
            }
        }
        return false;
    }
    public String getRoleName() {
        return roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    @Override
    public String toString() {
        return "RoleList{" + "roleName='" + roleName + '\'' + ", roleId=" + roleId + '}';
    }
}
