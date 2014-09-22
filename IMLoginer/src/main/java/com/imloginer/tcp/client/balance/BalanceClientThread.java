package com.imloginer.tcp.client.balance;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListSet;

import com.imapi.bean.balance.BalanceReqBean;
import com.imapi.bean.balance.req.LoginerConnReqBean;
import com.imapi.common.BalanceReqType;
import com.imbase.utils.BaseUtil;
import com.imbase.utils.DebugUtil;
import com.imbase.utils.JacksonUtil;

public class BalanceClientThread 
{
	private int sport;
	private ChannelFuture f;
	Channel udpChannel;
	private ConcurrentSkipListSet<String> connSet = new ConcurrentSkipListSet<String>();
	public BalanceClientThread(int sport,String host,int port,Channel udpChannel){
		this.sport =sport;
		this.udpChannel = udpChannel;
		init(sport,host,port);
		
	}
	public int getSport(){
		return sport;
	}
	public void init(int sport,String host,int port) {
		EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap b = new Bootstrap();
            b.group(group)
            .option(ChannelOption.SO_KEEPALIVE, true)
             .channel(NioSocketChannel.class)
             .handler(new BalanceClientInitializer(udpChannel,this));

            try {
				f = b.connect(host, port).sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			LoginerConnReqBean builder = new LoginerConnReqBean();
			DebugUtil.print1(BaseUtil.getHostIp()+"===============BaseUtil.getHostIp()");
			builder.setIp(BaseUtil.getHostIp());
			builder.setPort(sport);
			BalanceReqBean  reqBean =  new BalanceReqBean();
			reqBean.setType(BalanceReqType.LOGINER_CONN);
			reqBean.setReq(JacksonUtil.getJsonStr(builder));
			
				final LoginerConnReqBean loginerConnReqBean = JacksonUtil.resolve(reqBean.getReq(), LoginerConnReqBean.class);
				DebugUtil.print1(loginerConnReqBean.getIp()+"=2222222=="+loginerConnReqBean.getPort());
			
			write(reqBean);
            //f.channel().closeFuture().syncUninterruptibly();
	}
	public void write(BalanceReqBean reqBean){
		f.channel().writeAndFlush(reqBean);
	}
	public void add(Collection<String> list){
		connSet.addAll(list);
	}
	public synchronized boolean remove(String s){
		connSet.remove(s);
		return connSet.isEmpty();
	}

}
