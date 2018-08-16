package com.fb.util;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.*;

import java.io.*;
import java.net.SocketException;

/**
 * Created by shi.g.s on 2017/5/13.
 */
public class FtpFileDownload {

    //protected static final Logger LOG = Logger.getLogger(Loan.class);
    public FTPClient mFTPClient = new FTPClient();

    public FtpFileDownload() {
        mFTPClient.addProtocolCommandListener(new PrintCommandListener(
                new PrintWriter(System.out)));
    }

    /**
     * 连接ftp服务起
     *
     * @param host
     *            ip地址
     * @param port
     *            端口号
     * @param account
     *            账号
     * @param pwd
     *            密码
     * @return 是否连接成功
     * @throws SocketException
     * @throws IOException
     */
    public boolean openConnection(String host, int port, String account,
                                  String pwd) throws SocketException, IOException {
        mFTPClient.setControlEncoding("UTF-8");
        mFTPClient.connect(host, port);

        if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {
            mFTPClient.login(account, pwd);
            if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {
                //LOG.debug(mFTPClient.getSystemType());
                FTPClientConfig config = new FTPClientConfig(mFTPClient
                        .getSystemType().split(" ")[0]);
                config.setServerLanguageCode("zh");
                mFTPClient.configure(config);
                return true;
            }
        }
        disConnection();
        return false;
    }

    /**
     * 登出并断开连接
     */
    public void logout() {
        if (mFTPClient.isConnected()) {
            try {
                mFTPClient.logout();
                disConnection();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 断开连接
     */
    private void disConnection() {
        if (mFTPClient.isConnected()) {
            try {
                mFTPClient.disconnect();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载文件到本地地址
     *
     * @param remotePath
     *            远程地址
     * @param localDir
     *            本地地址
     * @param remoteFileName
     *              远程文件名
     * @throws IOException
     */
    public void downLoad(String remotePath,String remoteFileName, String localDir) throws IOException {
        // 进入被动模式
        mFTPClient.enterLocalPassiveMode();
        // 以二进制进行传输数据
        mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
        FTPFile[] ftpFiles = mFTPClient.listFiles(remotePath+remoteFileName);
        if (ftpFiles == null || ftpFiles.length == 0) {
            //LOG.error("远程文件不存在");
            return;
        } else if (ftpFiles.length > 1) {
            //LOG.error("远程文件是文件夹");
            return;
        }
        long lRemoteSize = ftpFiles[0].getSize();
        // 本地文件的地址
        File localFileDir = new File(localDir);
        if (!localFileDir.exists()) {
            localFileDir.mkdirs();
        }
        File localFile = new File(localFileDir, ftpFiles[0].getName());
        long localSize = 0;
        FileOutputStream fos = null;
        if (localFile.exists()) {
            if (localFile.length() == lRemoteSize) {
                //LOG.error("已经下载完毕");
                return;
            } else if (localFile.length() < lRemoteSize) {
                // 要下载的文件存在，进行断点续传
                localSize = localFile.length();
                mFTPClient.setRestartOffset(localSize);
                fos = new FileOutputStream(localFile, true);
            }
        }
        if (fos == null) {
            fos = new FileOutputStream(localFile);
        }
        InputStream is = mFTPClient.retrieveFileStream(remotePath+remoteFileName);
        byte[] buffers = new byte[1024];
        long step = lRemoteSize / 100;
        long process = localSize / step;
        int len = -1;
        while ((len = is.read(buffers)) != -1) {
            fos.write(buffers, 0, len);
            localSize += len;
            long newProcess = localSize / step;
            if (newProcess > process) {
                process = newProcess;
                //LOG.debug("下载进度:" + process);
            }
        }
        is.close();
        fos.close();
        boolean isDo = mFTPClient.completePendingCommand();
        if (isDo) {
           // LOG.debug("下载成功" );
        } else {
           // LOG.error("下载失败");
        }

    }


    /**
     * 下载转换字符串
     *
     * @param remotePath
     *            远程地址
     * @param remoteFileName
     *              远程文件名
     * @throws IOException
     */
    public String downLoad(String remotePath,String remoteFileName) throws IOException {
        // 进入被动模式
        mFTPClient.enterLocalPassiveMode();
        // 以二进制进行传输数据
        mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
        FTPFile[] ftpFiles = mFTPClient.listFiles(remotePath+remoteFileName);
        if (ftpFiles == null || ftpFiles.length == 0) {
            //LOG.error("远程文件不存在");
            //throw new IllegalArgumentException("the remote file does not exist");
        	return null;
        } else if (ftpFiles.length > 1) {
            //LOG.error("远程文件是文件夹");
            throw new IllegalArgumentException("the remote file is not a folder");
        }
        InputStream is = mFTPClient.retrieveFileStream(remotePath+remoteFileName);
        InputStreamReader isr=new InputStreamReader(is);
        BufferedReader buffer=new BufferedReader(isr);
        StringBuffer sb = new StringBuffer();

        String temp  = "";
        for(int i=0;(temp =buffer.readLine())!=null;i++){
            sb.append(temp);
            sb.append("\n");
        }
        //不要忘记关闭
        if (is != null) {
            is.close();
        }
        if (isr != null) {
            isr.close();
        }
        if (buffer != null) {
            buffer.close();
        }
        if(mFTPClient!=null){
            mFTPClient.completePendingCommand();
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException{
        FtpFileDownload ftp = new FtpFileDownload();
        try {
            boolean isConnection = ftp.openConnection("fbtest.sumapay.com",21,"fbp100097","fbp100097");
            if (isConnection) {
                String test=ftp.downLoad("/batchFileResult","/3004-B8-Z01-FINTRAN-RESULT-000019-20160515");
                System.out.println("---------1-----------"+test);
                String test1=ftp.downLoad("/batchFileResult","/3004-B8-Z01-FINTRAN-RESULT-000020-20160515");
                System.out.println("---------2---------"+test1);
                ftp.logout();
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
