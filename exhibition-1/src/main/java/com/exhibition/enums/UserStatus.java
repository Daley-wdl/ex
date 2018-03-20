package com.exhibition.enums;

import java.util.EnumSet;

/**
 * 用来表示用户或者展品等status字段的枚举
 * -1代表审核未通过，0代表待审核,1代表审核通过
 */
public enum UserStatus {
    Failed("-1"),//审核未通过
    Waiting("0"),//待审核
    Passed("1")//已审核通过
    ;

    private String status;

    UserStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    /**
     * 检查输入的status是否在该枚举的范围内
     * @param status
     * @return
     */
    public static boolean checkStatus(String status) {
        EnumSet<UserStatus> enumSet = EnumSet.allOf(UserStatus.class);
        for (UserStatus userStatus : enumSet) {
            if (userStatus.getStatus().equals(status)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "status='" + status + '\'' +
                '}';
    }
}
