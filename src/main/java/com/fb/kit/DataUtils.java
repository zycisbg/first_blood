package com.fb.kit;

public class DataUtils {
    /**
     * 数组转换为字符串类型，并用逗号隔开
     *
     * @param paras
     * @return
     */
    public static String arrayToString(String[] paras) {
        if (paras == null)
            return "";
        String para = "";
        for (int j = 0; j < paras.length; j++) {
            String endStr = "";
            if ((j + 1) == paras.length) {
                endStr = paras[j];

            } else {
                endStr = paras[j] + ",";
            }
            para += endStr;
        }
        return para;
    }

}