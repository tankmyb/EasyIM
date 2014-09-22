package org.IMClient;

import java.util.UUID;

import org.imclient.udp.UDPClient;

import com.imapi.bean.udp.UDPReqBean;
import com.imapi.common.UDPServiceType;
import com.imbase.utils.JacksonUtil;

public class Client2 {

	public static void main(String[] args) throws Exception {
		UDPClient client = new UDPClient("127.0.0.1", 9002);
		// client.connect("14.17.126.212", 9008);
		client.connect();
		UDPReqBean reqBean = new UDPReqBean();
		reqBean.setType(UDPServiceType.LOGIN);
		reqBean.setReq("b");
		client.req(JacksonUtil.getJsonStr(reqBean));
		Thread.sleep(2000);

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < 3000; i++) {
			sb.append(i).append(",");
		}
		//int j=0;
		for (int j = 0; j < 1500; j++) {
			client.chat(UUID.randomUUID().toString(),"a", j+"=="+sb.toString());
			
			 //Thread.sleep(100);
		}
		Thread.sleep(20000L);
	}
}
