package com.imloginer.tcp.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import com.imapi.bean.loginer.LoginerReqBean;
import com.imloginer.tcp.client.loginer.LoginerClientDecoder;
import com.imloginer.tcp.client.loginer.LoginerClientEncoder;

public class TCPServerInitializer extends ChannelInitializer<SocketChannel> {
	Channel udpChannel;
	int port;
   public TCPServerInitializer(Channel udpChannel,int port){
	   this.port = port;
	   this.udpChannel = udpChannel;
   }
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

      //decoded
        p.addLast(new LoginerServerDecoder());
        p.addLast(new LoginerServerEncoder());
        
        p.addLast(new TCPServerHandler(udpChannel,this.port));
    }

}
