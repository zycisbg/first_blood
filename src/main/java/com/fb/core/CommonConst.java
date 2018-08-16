package com.fb.core;

public class CommonConst {
	//活动
	public static class Activity{
		public static final Integer ACTIVITY_STATUS_DEL = 0;  
		public static final Integer ACTIVITY_STATUS_NOT_STARTED = 1;
		public static final Integer ACTIVITY_STATUS_STARTING = 2;
		public static final Integer ACTIVITY_STATUS_STOP = 3;
		public static final Integer ACTIVITY_STATUS_FINISH=4;
	}
	//规则
	public static class Rule{
		public static final Integer RULE_STATUS_STOP=0; //规则停用
		public static final Integer RULE_STATUS_START=1;//规则启用
		public static final Integer RULE_STATUS_WAIT=2;//规则待启用
		public static final Integer RULE_STATUS_OVER=3;//规则已结束
	}
	//触发条件
	public static class Condtion{
		//触发条件的数值类型
		public static final Integer CONDITION_VALUE_TYPE_TIME = 1;
		public static final Integer CONDITION_VALUE_TYPE_DATA = 2;
		public static final Integer CONDITION_VALUE_TYPE_BOOLEAN = 3;
		public static final Integer CONDITION_VALUE_TYPE_UNUSEPRODUCT = 4;
	}
	//福利券
	public static class Coupon {
		//福利卷状态
		public static final String COUPON_STATUS_ENABLE = "0";
		public static final String COUPON_STATUS_DISABLE = "1";
		
		//福利卷类型总类型(1.代金券,2.红包,3.提现券,4.流量券,5实体券)
		public static final Integer COUPON_TYPE_DAIJINQUAN=1;
		public static final Integer COUPON_TYPE_HONGBAO=2;
		public static final Integer COUPON_TYPE_TIXIANQUAN=3;
		public static final Integer COUPON_TYPE_LIULIANGQUAN=4;
		public static final Integer COUPON_TYPE_SHITIQUAN=5;
//		public static final Integer COUPON_TYPE_FANXIANXIANJIN=6;
		public static final Integer COUPON_TYPE_JIAXIQUAN=6;
		
	}
	//用户福利券
	public static class UserCoupon{
		public static final String USERCOUPON_STATUS_UNUSED="unused";
		public static final String USERCOUPON_STATUS_USED="used";
		public static final String USERCOUPON_STATUS_EXPIRE="expire";
	}
	//公共的标识
	public static class Common{
		//请求相应状态
		public static final String ACTIVITY_RESPONSE_ERROR = "0";
		public static final String ACTIVITY_RESPONSE_SUCCESS = "1";
		
		//开始时间结束时间标识
		public static final String ACTIVITY_PRE_TIME_KEY="pretime";
		public static final String ACTIVITY_START_TIME_KEY="starttime";
		public static final String ACTIVITY_END_TIME_KEY="endtime";
		
		//是否删除
		public static final int IS_DELETE_NO=1;
		public static final int IS_DELETE_YES=0;

		//badsql
		public static final String BAD_SQL_STRRING="bad_sql_string";
	}
	
	//批量插入
	public static class BatchInsert{
		//批量操作是否完成
		public static final int BATCH_INSERT_WAIT_SUMPAY_RECHECK=3;
		public static final int BATCH_INSERT_FINISH_NO=2;
		public static final int BATCH_INSERT_FINISH_YES=1;
		
		//批量插入现金
		public static final int BATCH_INSERT_TYPE_FULIQUAN=0;
		public static final int BATCH_INSERT_TYPE_XIANJIN=1;
		
		//批量插入状态
		public static final int BATCH_INSERT_STATUS_FAILURE=0;
		public static final int BATCH_INSERT_STATUS_SUCCESS=1;
		public static final int BATCH_INSERT_STATUS_UNSTART=2;
		public static final int BATCH_INSERT_STATUS_WAIT_SUMPAY_RECHECK=3;
	}

	//群发短信
	public static class MessageGroup{
		//群发短信状态
		//未发送
		public static final String NOT_SEND = "0";
		//成功
		public static final String SEND_SUCCESS = "1";
		//失败
		public static final String SEND_FAILED = "2";
	}

	public static class StatisticsData{
		public static final String TOTAL_MONEY = "累计投资额";
		public static final String TOTAL_COUNT = "累计投资笔数";
		public static final String TOTAL_REGIST_COUNT = "累计注册总人数";
		public static final String TOTAL_LOAN_MONEY = "累计交易总额(借款)";
		public static final String TOTAL_LOAN_COUNT = "累计交易笔数(借款)";
		public static final String TOTAL_REPING_MONEY = "借贷余额";
		public static final String TOTAL_REPING_COUNT = "借贷余额笔数";
		public static final String TOTAL_BORROWER_COUNT = "累计借款人数量";
		public static final String TOTAL_CURRENT_BORROWER_COUNT = "当前借款人数量";
		public static final String TOTAL_LENDER_COUNT = "累计出借人数量";
		public static final String TOTAL_CURRENT_LENDER_COUNT = "当前出借人数量";
		public static final String TOP_TEN_SCALE = "前十大借款人待还金额占比(%)";
		public static final String FIRST_SCALE = "最大单一借款人待还金额占比(%)";
		public static final String RELATION_LOAN_MONEY = "关联关系借款余额";
		public static final String RELATION_LOAN_COUNT = "关联关系借款笔数";
		public static final String BE_OVERDUE_SCALE = "逾期率";
		public static final String BE_OVERDUE_MONEY = "逾期金额";
		public static final String BE_OVERDUE_COUNT = "逾期笔数";
		public static final String BE_OVERDUE_NINETY_MONEY = "逾期90天以上金额";
		public static final String BE_OVERDUE_NINETY_COUNT = "逾期90天以上笔数";
		public static final String BE_OVERDUE_LOAN_COUNT = "逾期项目数";
		public static final String REPLACE_MONEY = "代偿金额";
		public static final String REPLACE_COUNT = "代偿笔数";
		public static final String TOTAL_LOAN_INTEREST = "待还借款利息";
		public static final String TOTAL_INVEST_INTEREST = "待还出借利息";

		public static final String DAY = "day";
		public static final String MONTH = "month";

        public static final Integer ALREADY_ISSUR = 1;
	}

	//群发短信
	public static class EntrustApply{
		//群发短信状态
		//未发送
		public static final Integer CONFIRMED = 1;

	}

	//字典
	public static class Dict{
		//用户渠道key
		public static final String USER_CHANNEL = "user_source_type";

	}

	//es
	public static class ES{
		//es 索引
		public static final String ES_INDEX = "yxtx";
		//es 类型
		public static final String ES_TYPE = "branchinformation";
	}

	
}
