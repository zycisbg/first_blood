package com.fb.kit;

public final class UserBillConstants {
	private UserBillConstants() {
	}
	
	/**
	 * 造成资金转移的操作类型信息
	 * @author Administrator
	 *
	 */
	public final static class OperatorInfo {
		/**
		 * 投资成功
		 */
		public final static String INVEST_SUCCESS = "invest_success";
		
		/**管理员干预*/	
		public final static String ADMIN_OPERATION = "admin_operation";
		
		/**充值成功*/
		public final static String RECHARGE_SUCCESS = "recharge_success";
		
		/**申请借款*/
		public final static String APPLY_LOAN = "apply_loan";
		
		/**借款申请未通过*/
		public final static String REFUSE_APPLY_LOAN = "refuse_apply_loan";
		
		/**申请提现*/
		public final static String APPLY_WITHDRAW = "apply_withdraw";
		
		/**提现手续费冻结*/
		public final static String APPLY_WITHDRAW_FEE_FROZEN = "apply_withdraw_fee_fee_frozen";
		
		/**提现手续费扣除*/
		public final static String APPLY_WITHDRAW_FEE_DEDUCTION = "apply_withdraw_fee_deduction";
		
		/**提现手续费撤回*/
		public final static String APPLY_WITHDRAW_FEE_CANCEL = "apply_withdraw_fee_cancel";
		
		/**提现手续费解冻*/
		public final static String APPLY_WITHDRAW_FEE_THAW = "apply_withdraw_fee_thaw";
		
		
		/**提现罚金冻结*/
		public final static String APPLY_WITHDRAW_PENALTY_FROZEN = "apply_withdraw_penalty_frozen";
		
		/**提现罚金扣除*/
		public final static String APPLY_WITHDRAW_PENALTY_DEDUCTION = "apply_withdraw_penalty_deduction";
		
		/**提现罚金扣除*/
		public final static String APPLY_WITHDRAW_PENALTY_CANCEL = "apply_withdraw_penalty_cancel";
		
		/**提现罚金解冻*/
		public final static String APPLY_WITHDRAW_PENALTY_THAW = "apply_withdraw_penalty_thaw";
		
		/**使用优惠券抵用提现手续费*/
		public final static String APPLY_WITHDRAW_FEE_USECOUPON = "apply_withdraw_fee_usecoupon";
		
		/**体现审核不通过返回优惠券*/
		public final static String APPLY_WITHDRAW_FEE_BACKCOUPON = "apply_withdraw_fee_backcoupon";
		
		/**管理员充值*/
		public final static String ADMIN_RECHARGE = "admin_recharge";
		
		/**管理员发放红包*/
		public final static String RECEIVE_RED = "receive_red";
		/**管理员发放红包*/
		public final static String RECEIVE_RED_MESSAGE = "红包返现";
		
		/**注册或其他活动送红包*/
		public final static String RECEIVE_REG = "receive_reg";
		/**注册或其他活动送红包*/
		public final static String RECEIVE_REG_MESSAGE = "元红包返现";

		/**使用代金券*/
		public final static String RECEIVE_VOUCHER = "receive_voucher";


		/**提现申请未通过*/
		public final static String REFUSE_APPLY_WITHDRAW = "refuse_apply_withdraw";

		/**正常还款*/
		public final static String NORMAL_REPAY = "normal_repay";
		
		/**提前还款*/
		public final static String ADVANCE_REPAY = "advance_repay";
		
		/**逾期还款*/
		public final static String OVERDUE_REPAY = "overdue_repay";

		/**借款流标*/
		public final static String CANCEL_LOAN = "cancel_loan";

		/**借款撤标*/
		public final static String WITHDRAW_LOAN = "withdraw_loan";
		
		/**借款放款*/
		public final static String GIVE_MONEY_TO_BORROWER = "give_money_to_borrower";
		
		/**提现成功*/
		public final static String WITHDRAW_SUCCESS = "withdraw_success";

		/**提现撤回*/
		public final static String WITHDRAW_CANCEL = "withdraw_cancel";
		
		/**投资流标*/
		public static final String CANCEL_INVEST = "cancel_invest";
		
		/**caijinmin 增加债权转让成功状态 201501222046 begin*/
		/**债权转让成功*/
		public static final String TRANSFER = "transfer";
		
		/**债权购买成功*/
		public static final String TRANSFER_BUY = "transfer_buy";
		/**caijinmin 增加债权转让成功状态 201501222046 end*/
		
		/**众筹投资*/
		public static final String RAISE_INVEST = "raise_invest";
		
		/**众筹放款*/
		public static final String RAISE_GIVE_MONEY_TO_BORROWER = "raise_give_money_to_borrower";
		
		/**众筹流标*/
		public final static String RAISE_CANCEL_LOAN = "raise_cancel_loan";
		
		/**红包使用*/
		public final static String USE_COUPON = "use_coupon";
		
		/** 私房钱投资成功**/
		public final static String PERSONAL_MONEY_INVEST_SUCCESS = "personal_money_invest_success";
		
		/**私房钱*/
		public final static String PERSONAL_MONEY = "personal_money";
		/**私房钱*/
		public final static String PERSONAL_MONEY_MESSAGE = "收取私房钱";
		/**私房钱投资未达到提现门槛扣除私房钱及其收益*/
		public final static String PERSONAL_MONEY_DEDUCTION = "personal_money_deduction";
		
		/**私房钱还款*/
		public final static String PERSONAL_MONEY_REPAY = "personal_money_repay";
		
		/**提前退出扣款*/
		public final static String EXIT_DEBIT = "exit_debit";
		
		/**提前退出加钱*/
		public final static String EXIT_ADD_MONEY = "exit_add_money";
		
		/**提前退出手续费*/
		public final static String EXIT_FEE = "exit_fee";

		/**提前退出冻结资金*/
		public final static String EXIT_MONEY_FROZEN = "exit_money_frozen";

		/**债权到期还款冻结资金*/
		public final static String INVEST_REPAY_FROZEN = "invest_repay_frozen";

		/**逾期垫付本金划入/垫付*/
		public final static String OVERDUE_PAY_CORPUS = "overdue_pay_corpus";

		/**逾期垫付利息划入/垫付*/
		public final static String OVERDUE_PAY_INTERSET = "overdue_pay_interset";

		/**债转垫付利息划入/垫付*/
		public final static String IFTHE_PAY_INTERSET = "ifthe_pay_interset";

		/**按月付息利息划入/垫付*/
		public final static String MONTH_PAY_INTERSET = "month_pay_interset";

		/**债权募集期利息垫付*/
		public final static String LOAN_COLLECTION_INTERSET = "loan_collection_interset";

		/**提前还款申请拒绝解冻*/
		public final static String EARLY_REPAY_REJECT_UNFREEZE = "early_repay_reject_unfreeze";
	}

	
	public final static class Type{
		
		/**
		 * 冻结
		 */
		public final static String FREEZE = "freeze";

		/**
		 * 解冻
		 */
		public final static String UNFREEZE = "unfreeze";
		
		/**
		 * 从余额转出 transfer out from balance
		 */
		public final static String TO_BALANCE = "to_balance";

		/**
		 * 转入到余额 tansfer into balance
		 */
		public final static String TI_BALANCE = "ti_balance";
		
		/**
		 * 从冻结金额中转出 transfer out frome frozen money
		 */
		public final static String TO_FROZEN = "to_frozen";
		/**
		 * 私房钱投资冻结
		 */
		public final static String PM_FROZEN = "pm_frozen";
		
		/**
		 * 从在投本金转出 transfer out from corpus
		 */
		public final static String TO_CORPUS = "to_corpus";

		/**
		 * 转入到在投本金 tansfer into corpus
		 */
		public final static String TI_CORPUS = "ti_corpus";
		
	}
	
}
