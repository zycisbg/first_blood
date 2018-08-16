package com.fb.util;

/**
 * Created by zyc on 2017/11/15.
 */
public enum  TransTypeEnum {
    TIXIANZHIBANGKA("2616","提现至绑定卡"),
    RONGZIKOUKUAN("2780","P2P融资扣款"),
    DAOQIKOUKUAN("2781","P2P到期还款"),
    DAICHANGHAIKUAN("2788","P2P代偿还款"),
    ZIJINZHUANCHU("2789","P2P债权转让资金转出"),
    HONBGBAOFAFANGSHOUYIKOUKUAN("2792","P2P红包发放收益扣款"),
    PINGTAITIEXISHOUYIKOUKUAN("2793","P2P平台铁西收益扣款"),
    HANGNEIQUDAOZIJINZHUANCHU("2820","行内渠道资金转出"),
    ZHAIQUANZHUANRANGZIJINZHUANCHU("2831","P2P债权转让资金转出"),
    HONGBAOZHUANCHU("2833","红包转出"),
    KOUKUANSHOUXUFEI("4780","P2P融资扣款手续费"),
    DAOQIHAIKUANSHOUXUFEI("4781","P2P到期还款手续费"),
    DAICHANGHAIKUANSHOUXUFEI("4788","P2P代偿还款手续费"),
    ZHUANZHANGSHOUXUFEI("4616","转账手续费"),
    ZHUANCHUSHOUXUFEI("4820","转出手续费"),
    HUOQICUNKUANLIXI("5500","活期存款利息"),
    KAODANGCUNKUANLIXI("5504","靠档存款利息"),
    SHOUXUFEIRUZHANG("7722","手续费入账"),
    TIXIANSHOUXUFEIZHUANRU("7724","P2P提现手续费转入"),
    ZHAIQUANZHUANRANGSHOUXUFEIZHUANRU("7725","提现至绑定卡"),
    PILIANGRUZHANG("7777","批量入账（基金分红）"),
    RONGZI("7780","P2P融资"),
    DAOQISHOUYI("7781","P2P到期收益"),
    ZHANGHUPILIANGCHONGZHI("7782","PP账户批量充值"),
    ZHANGHUHONGBAOFAFANG("7783","P2P账户红包发放"),
    BATCHZHAIQUANZHUANRANGZIJINZHUANRU("7785","P2P债权转让资金转入"),
    DAICHANGHAIKUANDAOQISHOUYI("7788","P2P代偿还款到期收益"),
    HONGBAOFAFANGSHOUYI("7792","P2P红包发放收益"),
    HANGNEIQUDAOZIJINZHUANRU("7820","行内渠道资金转入"),
    ZHAIQUANZHUANRANGZIJINZHUANRU("7831","P2P债转转让资金转入"),
    HONGBAOZHUANRU("7833","红包转入"),
    ZHAIQUANZHUANRANGSHOUXUFEIZHUANRUHUIKUAN("7835","债权转让手续费转入"),
    ZHIXIAOYINHANGZHANGHUZIJINZHUANRU("7923","直销银行账户资金转入"),
    P2PRONGZI("9780","P2P融资手续费"),
    P2PDAOQISHOUYI("9781","P2P到期收益"),
    P2PDAICHANGSHOUYI("9788","P2P代偿收益"),
    P2PZHAIQUANZHUANRANGZIJINZHUANRU("9831","P2P债权转让资金转入"),
    ZHAIQUANZHUANRANGSHOUXUFEI("9785","批量债权转让手续费");

    private String code;
    private String remark;

    private TransTypeEnum(String code,String remark){
        this.code = code;
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static String getRemark(String code){
        for(TransTypeEnum type : TransTypeEnum.values()){
            if(type.getCode().equals(code)){
                return type.remark;
            }
        }
        return null;
    }

}
