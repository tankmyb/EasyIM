package com.imapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

import org.omg.CORBA.portable.InputStream;

public class GenerateProto {
	/**
	 * Windows版本
	 * 调用protoc.exe生成java数据访问类
	 * */
	public static void main(String[] args) throws Exception {
		String cdPath = "cd E:\\tank\\workspace\\EasyIM\\IMApi\\proto";
		String javaPath = "E:\\tank\\workspace\\EasyIM\\IMApi\\src\\main\\java\\";
		String protoFile = "balancer/req/LoginerConnReqBean.proto";//
		System.out.println("===============");
		String strCmd = "protoc.exe --java_out="+javaPath+" " + protoFile;
		Process process = Runtime.getRuntime().exec(  
				  "cmd /c cd /e & "+cdPath+" & "+  strCmd);
		String s=null;
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		  while((s=bufferedReader.readLine()) != null)
		 System.out.println(s);
	  process.waitFor();
	}
}