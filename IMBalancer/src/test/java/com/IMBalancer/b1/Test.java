package com.IMBalancer.b1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.IMBalancer.b1.FirstExample.ReqBean;
import com.IMBalancer.b1.req.Req;
import com.google.protobuf.InvalidProtocolBufferException;

public class Test {

	private static void write2Disk(byte[] buf) throws IOException{
		//把序列化后的数据写入本地磁盘  
        ByteArrayInputStream stream = new ByteArrayInputStream(buf);  
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("/home/bo/protobuf.txt"));//设置输出路径  
        BufferedInputStream bis = new BufferedInputStream(stream);  
        int b = -1;  
        while ((b = bis.read()) != -1) {  
            bos.write(b);  
        }  
        bis.close();  
        bos.close(); 
	}
	private static void read1(byte[] buf){
		//读取序列化后的数据  
        try {  
        	FirstExample.ReqBean person01 = FirstExample.ReqBean.parseFrom(buf);  
            System.out.println(person01.getName());
            System.out.println(person01.getRequestData());
            Req.ReqData reqData = Req.ReqData.parseFrom(person01.getRequestData());
            System.out.println(reqData.getData());
            System.out.println(reqData.getData2());
        } catch (InvalidProtocolBufferException e) {  
            e.printStackTrace();  
        }  
	}
	public static void main(String[] args) throws IOException {
		Req.ReqData req = Req.ReqData.newBuilder().setData("content").setData2("21d2").build();
		
		FirstExample.ReqBean.Builder builder = FirstExample.ReqBean.newBuilder();
		builder.setName("aaaaaaaaa");
		builder.setRequestData(req.toByteString());
		
		ReqBean person = builder.build();  
		
        byte[] buf = person.toByteArray();  
        read1(buf);
         
	}
}
