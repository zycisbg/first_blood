package com.fb.pojos;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 债权预警参数封装对象
 * Created by liucongcong on 2017/1/4.
 */
public class DebtsWarningVO {

    private Integer seriaNum; //编号
    private Date date; //日期
    private BigDecimal dayDebtsMoney; //债权待匹总金额
    private BigDecimal dayEarlyExitMoney; //提前退出总额度
    private BigDecimal dayExpireExitMoney; //到期退出总额度
    private BigDecimal dayDqbLoanMoney; //发标总额度
    private BigDecimal dayInvestMoney;  //总投资额

    public Integer getSeriaNum() {
        return seriaNum;
    }

    public void setSeriaNum(Integer seriaNum) {
        this.seriaNum = seriaNum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getDayDebtsMoney() {
        return dayDebtsMoney;
    }

    public void setDayDebtsMoney(BigDecimal dayDebtsMoney) {
        this.dayDebtsMoney = dayDebtsMoney;
    }

    public BigDecimal getDayEarlyExitMoney() {
        return dayEarlyExitMoney;
    }

    public void setDayEarlyExitMoney(BigDecimal dayEarlyExitMoney) {
        this.dayEarlyExitMoney = dayEarlyExitMoney;
    }

    public BigDecimal getDayExpireExitMoney() {
        return dayExpireExitMoney;
    }

    public void setDayExpireExitMoney(BigDecimal dayExpireExitMoney) {
        this.dayExpireExitMoney = dayExpireExitMoney;
    }

    public BigDecimal getDayDqbLoanMoney() {
        return dayDqbLoanMoney;
    }

    public void setDayDqbLoanMoney(BigDecimal dayDqbLoanMoney) {
        this.dayDqbLoanMoney = dayDqbLoanMoney;
    }

    public BigDecimal getDayInvestMoney() {
        return dayInvestMoney;
    }

    public void setDayInvestMoney(BigDecimal dayInvestMoney) {
        this.dayInvestMoney = dayInvestMoney;
    }
}
