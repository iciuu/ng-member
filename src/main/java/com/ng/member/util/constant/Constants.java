package com.ng.member.util.constant;

/**
 * @author niuguang
 * @date 18-1-17
 */
public interface Constants {

    /**
     * 接口返回编码
     */
    interface CommonCode {
        /**
         * 成功
         */
        int SUCCESS = 0;

        /**
         * 失败
         */
        int FAIL = 1;

        /**
         * 重新登录
         */
        int NO_LOGIN = 999;
    }

    /**
     * 用户常量
     */
    interface UserConstants {
        /**
         * 用户状态：启用
         */
        String STATUS_ENABLED = "10";
        /**
         * 用户状态：禁用
         */
        String STATUS_DISABLED = "00";
        /**
         * 用户状态：锁定
         */
        String STATUS_LOCKED = "20";
    }

    interface DeleteFlagConstants {
        /**
         * 未删除
         */
        String DELETE_TRUE = "Y";
        /**
         * 已删除
         */
        String DELETE_FALSE = "N";
    }

    interface UserRoleConstants {
        /**
         * 管理员
         */
        Long ADMIN = 0L;
        /**
         * 用户
         */
        Long USER = 1L;
    }

    interface MoneyTrans {

        /**
         * 元转换分
         */
        int CENT = 100;
    }

    interface UserGroupRoleConstants {
        /**
         * 管理员
         */
        String ADMIN = "0";
        /**
         * 成员
         */
        String MEMBER = "1";
    }
}
