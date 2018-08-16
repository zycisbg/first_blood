package com.fb.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;


public class NumUtils {
	
	public static final String PATTERN = "0.00";
	private static DecimalFormat df = new DecimalFormat(PATTERN); 
	public static Integer getNumInt(Double num) {
		if(num!=null){
			if(num==0){
				return 0;
			}
			return num.intValue();
		}else{
			return 0;
		}
	}

	public static void main(String args[]){
		
		/*Double s = 80.0; 
		Double dd = ArithUtil.sub(s,new Double(s.intValue()));
		Double ff = ArithUtil.mul(ArithUtil.sub(s, new Double(s.intValue())),new Double(100));
		System.out.println(""+ff.toString());*/
		
		//double s = 999999999999999d;
		double s = 10000000000000.0000011d;
		//double s = 01.225d;
		//double s = 0.00000000000009d;
		System.out.println(s);
		System.out.println(NumUtils.getPlainDecimal(s));
		
	}
	

	public static double getArrivalMoney(Double money,Double fee ,Double cashFine){
		BigDecimal sub1 = new BigDecimal(Double.toString(cashFine));
		BigDecimal sub2 = new BigDecimal(Double.toString(fee));
		BigDecimal sub3 = new BigDecimal(Double.toString(money));
		double monry = sub3.subtract(sub2).subtract(sub1).doubleValue();
		return monry;
	}
	
	/**
	 * 获取正常的小数
	 * 根治科学计数法 返回该数字的String串
	 * 10000000000000.0000011 参数
	 * 1.0E13 使用前
	 * 10000000000000.00 使用后
	 * @param money
	 * @return
	 */
	public static String getPlainDecimal(Double money){
		if(money!=null){
			return df.format(money);
		}else{
			return "";
		}
	}
	
}
