package com.fb.kit;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.jfinal.kit.PropKit;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * @function 阿里云OSS辅助类
 * @author gadzs
 * @since 2016-05-11
 */
public class OSSUtils {

	public static String endpoint = "";         //访问域名
	public static String endpointPublic = "";   //访问域名(公网)
	public static String accessKeyId = "";      //访问密钥
	public static String accessKeySecret = "";  //访问密钥
	
	public static String ossStaticPath = "";     //静态资源访问地址
	
	/*
	 * 存储空间 一个用户最多可创建10个Bucket，没有文件系统的目录概念。
	 * wap和pc是否用两个oss，为了可以分出多个bucket
	 */
	public static String hnjbBucket = "";  //

	
	private OSSClient ossClient ;
	private String bucketName = "";
    private String key = "";
	private static ExecutorService executorService = Executors.newFixedThreadPool(5);
    private static List<PartETag> partETags = Collections.synchronizedList(new ArrayList<PartETag>());
	
	static {
		try {
			endpoint = PropKit.use("config.properties").get("alioss.endpoint");
			endpointPublic = PropKit.use("config.properties").get("alioss.endpoint");
			accessKeyId = PropKit.use("config.properties").get("alioss.accesskey");
			accessKeySecret = PropKit.use("config.properties").get("alioss.secret");
			hnjbBucket = PropKit.use("config.properties").get("alioss.bucket");
			ossStaticPath = PropKit.use("config.properties").get("alioss.url");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取 OSSClient 实例
	 * @return ossClient
	 */
	public void getOSSClient() { 
		// 创建ClientConfiguration实例，按照您的需要修改默认参数
		ClientConfiguration conf = new ClientConfiguration();
		// 设置OSSClient使用的最大连接数，默认1024
		conf.setMaxConnections(1024);
		// 设置请求超时时间，默认50秒
		conf.setSocketTimeout(10000);
		// 设置失败请求重试次数，默认3次
		conf.setMaxErrorRetry(5);
		// 开启支持CNAME选项
		//conf.setSupportCname(true);
		ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret,conf);		
    }  
	
	
	/**
	 * 获取 OSSClient 实例
	 * @param bucketName
	 * @param key
	 * @return ossClient
	 */
	public void getOSSClient(String bucketName,String key) { 
		// 创建ClientConfiguration实例，按照您的需要修改默认参数
		ClientConfiguration conf = new ClientConfiguration();
		// 设置OSSClient使用的最大连接数，默认1024
		conf.setMaxConnections(1024);
		// 设置请求超时时间，默认50秒
		conf.setSocketTimeout(10000);
		// 设置失败请求重试次数，默认3次
		conf.setMaxErrorRetry(5);
		// 开启支持CNAME选项
		//conf.setSupportCname(true);
		ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret,conf);		
		this.bucketName = bucketName;
		this.key = key;
    }  
	
	/**
	 * 给bucketName和key赋值
	 * @param bucketName
	 * @param key
	 */
	public void setBuckNameAndKey(String bucketName,String key){
		this.bucketName = bucketName;
		this.key = key;
	}
	
	/**
	 * 简单文件上传
	 * @param inputStream
	 */
	public void simpleFileUpload(InputStream inputStream){		
		try{
			ossClient.putObject(bucketName, key, inputStream);
		}catch (OSSException oe) {
			oe.printStackTrace();
        } catch (ClientException ce) {
        	ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 简单文件上传（创建模拟文件夹）(文件地址：bucket.endpoint/objectname)
	 * 创建模拟文件夹本质上来说是创建了一个名字以“/”结尾的文件；
	 * @param directory
	 * @param inputStream
	 */
	public void fileUploadWithDirectory(String directory,InputStream inputStream){	
		try{
			ossClient.putObject(bucketName, directory+key, inputStream);
		}catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	/**
	 * 通过文件流上传文件内容流
	 * @param key
	 * @param input 使用 ByteArrayInputStream
	 * @throws Exception 
	 */
	public void fileContentUpload(String directory, InputStream inputUpload,String charset) throws Exception{	
		
		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();
//		meta.setContentLength(fileContent.length());
//		// 设置上传MD5校验
//		byte[] bytes = fileContent.getBytes(charset);
//		String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(bytes));
		// 设置上传内容类型
		meta.setContentType("text/plain");
		meta.setContentEncoding(charset);
		Map<String, String> map = new HashMap<>();
//		map.put("md5", md5);
		meta.setUserMetadata(map);
//		ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
		ossClient.putObject(bucketName, directory+key, inputUpload,meta);
	}
	/**
	 * 简单文件下载（创建模拟文件夹）
	 */
	public void fileDownloadWithDirectory(){
		OSSObject object = null;
		try{
			object = ossClient.getObject(new GetObjectRequest(bucketName, key));			
	        BufferedReader reader = new BufferedReader(new InputStreamReader(object.getObjectContent()));
	        while (true) {
	            String line = reader.readLine();
	            if (line == null) break;
	            System.out.println("\t" + line);
	        }
	        System.out.println();
	        reader.close();			
		}catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 简单文件下载
	 */
	public void simpleFileDownload(){
		OSSObject object = null;
		try{
			object = ossClient.getObject(new GetObjectRequest(bucketName, key));			
	        BufferedReader reader = new BufferedReader(new InputStreamReader(object.getObjectContent()));
	        while (true) {
	            String line = reader.readLine();
	            if (line == null) break;
	            System.out.println("\t" + line);
	        }
	        System.out.println();
	        reader.close();			
		}catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 简单文件上传
	 * @param file
	 */
	public void simpleFileUpload(File file){	
		try{
			ossClient.putObject(new PutObjectRequest(bucketName, key, file));
		}catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	

	/**
	 * 简单文件上传（创建模拟文件夹）
	 * 创建模拟文件夹本质上来说是创建了一个名字以“/”结尾的文件；
	 * @param directory
	 * @param file
	 */
	public void fileUploadWithDirectory(String directory,File file){	
		try{
			ossClient.putObject(bucketName, directory+key, file);
		}catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 断点续传
	 * @throws Throwable 
	 */
	public void uploadResume() throws Throwable{
		try{
			// 设置断点续传请求
			UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, key);
			// 指定上传的本地文件
			uploadFileRequest.setUploadFile("<yourLocalFile>");
			// 指定上传并发线程数
			uploadFileRequest.setTaskNum(5);
			// 指定上传的分片大小
			uploadFileRequest.setPartSize(1 * 1024 * 1024);
			// 开启断点续传
			uploadFileRequest.setEnableCheckpoint(true);
			// 断点续传上传
			ossClient.uploadFile(uploadFileRequest);
		}catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 删除存储的文件
	 */
	public void deleteObject(){
		try{
			ossClient.deleteObject(bucketName, key);	
		}catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 列举文件
	 * 默认列举100个object。
	 * @return
	 */
	public ObjectListing getObjectList(){
		ObjectListing objectListing = ossClient.listObjects(bucketName);
//		for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
//		    System.out.println(" - " + objectSummary.getKey() + "  " + 
//		            "(size = " + objectSummary.getSize() + ")");
//		}
		return objectListing;
	}
	
	/**
	 * 判断bucketName是否存在
	 * @param bucketName
	 * @return
	 */
	public boolean doesBucketExist(String bucketName){
		return ossClient.doesBucketExist(bucketName)?true:false;
	}
	
	/**
	 * 创建Bucket
	 * @param bucketName
	 */
	public void createBucket(String bucketName){
		try{
			if(!ossClient.doesBucketExist(bucketName)){
				ossClient.createBucket(bucketName);
			}
		}catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 大文件上传
	 */
	public void multipartUpload(final File sampleFile){
		String uploadId = claimUploadId();
		
		final long partSize = 5 * 1024 * 1024L;   // 5MB
        long fileLength = sampleFile.length();
        int partCount = (int) (fileLength / partSize);
        if (fileLength % partSize != 0) {
            partCount++;
        }
        if (partCount > 10000) {
            throw new RuntimeException("Total parts count should not exceed 10000");
        } else {                
            System.out.println("Total parts count " + partCount + "\n");
        }
		
        //上传multiparts到bucket
        System.out.println("Begin to upload multiparts to OSS from a file\n");
        for (int i = 0; i < partCount; i++) {
            long startPos = i * partSize;
            long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
            executorService.execute(new PartUploader(sampleFile, startPos, curPartSize, i + 1, uploadId));
        }
        
        //等待所有parts完成
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                executorService.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
       if (partETags.size() != partCount) {
           throw new IllegalStateException("Upload multiparts fail due to some parts are not finished yet");
       } else {
           System.out.println("Succeed to complete multiparts into an object named " + key + "\n");
       }

       listAllParts(uploadId);
       completeMultipartUpload(uploadId);		
	}
	
	/**
	 * 大文件上传，完成所有上传
	 * @param uploadId
	 */
	private void completeMultipartUpload(String uploadId) {
        // Make part numbers in ascending order
        Collections.sort(partETags, new Comparator<PartETag>() {

            @Override
            public int compare(PartETag p1, PartETag p2) {
                return p1.getPartNumber() - p2.getPartNumber();
            }
        });

        System.out.println("Completing to upload multiparts\n");
        CompleteMultipartUploadRequest completeMultipartUploadRequest = 
                new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
        ossClient.completeMultipartUpload(completeMultipartUploadRequest);
    }

	/**
	 * 大文件上传，列表所有的parts
	 * @param uploadId
	 */
    private void listAllParts(String uploadId) {
        System.out.println("Listing all parts......");
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, key, uploadId);
        PartListing partListing = ossClient.listParts(listPartsRequest);

        int partCount = partListing.getParts().size();
        for (int i = 0; i < partCount; i++) {
            PartSummary partSummary = partListing.getParts().get(i);
            System.out.println("\tPart#" + partSummary.getPartNumber() + ", ETag=" + partSummary.getETag());
        }
        System.out.println();
    }
	
	/**
	 * 大文件上传,获取uloadId
	 * @return
	 */
	private String claimUploadId() {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
        InitiateMultipartUploadResult result = ossClient.initiateMultipartUpload(request);
        return result.getUploadId();
    }
	
	/**
	 * 关闭ossClient
	 */
	public void shutDownOss(){
		if (ossClient != null) {
			ossClient.shutdown();
        }
	}
	
	private class PartUploader implements Runnable {

		private File localFile;
	    private long startPos;        

	    private long partSize;
	    private int partNumber;
	    private String uploadId;

	    public PartUploader(File localFile, long startPos, long partSize, int partNumber, String uploadId) {
	        this.localFile = localFile;
	        this.startPos = startPos;
	        this.partSize = partSize;
	        this.partNumber = partNumber;
	        this.uploadId = uploadId;
	    }

	    @Override
	    public void run() {
	        InputStream instream = null;
	        try {
	            instream = new FileInputStream(this.localFile);
	            instream.skip(this.startPos);

	            UploadPartRequest uploadPartRequest = new UploadPartRequest();
	            uploadPartRequest.setBucketName(bucketName);
	            uploadPartRequest.setKey(key);
	            uploadPartRequest.setUploadId(this.uploadId);
	            uploadPartRequest.setInputStream(instream);
	            uploadPartRequest.setPartSize(this.partSize);
	            uploadPartRequest.setPartNumber(this.partNumber);

	            UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
	            System.out.println("Part#" + this.partNumber + " done\n");
	            synchronized (partETags) {
	                partETags.add(uploadPartResult.getPartETag());
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (instream != null) {
	                try {
	                    instream.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    } 
	}
}
