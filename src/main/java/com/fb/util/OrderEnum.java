package com.fb.util;

/**
 * 订单类型枚举类
 * RemoteUser: zp
 * Date: 2016/8/3
 * Time: 14:47
 * Project: hnjb
 * 版权所有(C) 2016，银信天下网信息科技服务有限公司
 */
public enum OrderEnum {
    /**
     * 充值
     */
    RECHARGE_ORDER("CZ"),
    /**
     * 提现
     */
    WITHDRAW_CASH_ORDER("TX"),
    /**
     * 充值
     */
    BILL_ORDER("BILL");

    private String start;
    public String getStart(){
        return start;
    }
    private OrderEnum(String start){
        this.start = start;
    }

}
