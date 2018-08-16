package com.fb.util;


import com.fb.kit.ToolsUtils;

import java.io.*;
import java.util.List;


/**
 * Created by yiqing on 2017/4/26.
 */
public class FileOperaUtil {

    //文件路径+名称
    private static String filenameTemp;
    //后缀名称
    private final static String suffix="";
    /**
     * 创建文件
     * @param fileName  文件名称
     * @param filecontent   文件内容
     * @return  是否创建成功，成功则返回true
     * @throws IOException 
     */
    public static boolean createFile(String path, String fileName, List<String> filecontent) throws IOException{
        Boolean bool = false;
        filenameTemp = path+fileName+suffix;//文件路径+名称+文件类型
        try {
            /**
             * 没有则新建目录
             */
            ToolsUtils.mkdir(path);

            File file = new File(filenameTemp);
            //如果文件不存在，则创建新的文件
            if(!file.exists()){
                file.createNewFile();
                //创建文件成功后，写入内容到文件里
                bool=writeFileContent(filenameTemp, filecontent);
            }
        } catch (IOException e) {
            e.printStackTrace();
            bool=false;
            throw e;
        }
        return bool;
    }

    /**
     * 向文件中写入内容
     * @param filepath 文件路径与名称
     * @param newstr  写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(String filepath, List<String> newstrs) throws IOException {
    	if(newstrs==null||newstrs.size()<=0)//如果内容为空直接不需要再向文件中插入内容
    		return true;
        Boolean bool = false;
//        String filein = newstr+"\n";//新写入的行，换行
        String temp  = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();

            //文件原有内容,进行处理
            for(int i=0;(temp =br.readLine())!=null;i++){
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            for (String filein : newstrs) {
            	buffer.append(filein+"\n");
			}

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (IOException e) {
            e.printStackTrace();
            bool = false;
            throw e;
        }finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }
    /**
     * 转换成文件流
     * @return
     * @throws IOException
     */
    public static InputStream contentConvertToStream(List<String> newstrs) throws IOException {
    	StringBuffer buffer = new StringBuffer();
        for (String filein : newstrs) {
        	buffer.append(filein+"\n");
		}
        
        ByteArrayInputStream bais = null;
        try {
        	bais = new ByteArrayInputStream(buffer.toString().getBytes("GBK"));
        	
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }finally {
            //不要忘记关闭
            if (bais != null) {
            	bais.close();
            }
        }
        return bais;
    }
    public static void main(String[] args) throws IOException {
    	
    }
}
