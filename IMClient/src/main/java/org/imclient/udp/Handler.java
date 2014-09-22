package org.imclient.udp;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

import org.imclient.bean.Ack;
import org.imclient.util.ReSendScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imbase.utils.DebugUtil;

public class Handler extends ChannelHandlerAdapter  {
	private final Logger logger = LoggerFactory.getLogger("order");
	private InetSocketAddress address;
	public Handler(InetSocketAddress address){
		this.address = address;
	}

    // 连接成功后，向server发送消息

    @Override

    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //System.out.println("==============channel--active==============");

    }


    @Override

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        cause.printStackTrace();

        ctx.close();

    }
    @Override
	 public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
    	if(obj instanceof String){
    		String s = (String)obj;
    		if(s.length()>10){
    			//logger.info(s.substring(0,10)+"==="+s.length());
    		}
    		
    		DebugUtil.print("str:"+obj);
    	}else if(obj instanceof Ack){
    		Ack ack = (Ack)obj; 
    		DebugUtil.print(ack.getOid()+"==ack");
    		//logger.info(ack.getOid()+"-"+System.currentTimeMillis());
    		ReSendScheduler.cancel(ack.getOid());
    	}
    }
	

}
