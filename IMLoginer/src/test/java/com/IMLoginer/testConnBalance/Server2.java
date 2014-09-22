package com.IMLoginer.testConnBalance;

import io.netty.channel.Channel;

import java.util.Date;

import com.imloginer.redis.RedisService;
import com.imloginer.tcp.client.balance.BalanceClientThread;
import com.imloginer.tcp.client.loginer.LoginerChannel;
import com.imloginer.tcp.server.TCPServer;
import com.imloginer.udp.server.UDPServer;

public class Server2 {

	public static void main(String[] args) throws Exception {
		int i = 2;
		int tcpPport = 10000 + i;
		int udpPort = 9000 + i;
		System.out.println(tcpPport + "===" + udpPort);
		RedisService redisService = new RedisService();
		UDPServer server = new UDPServer();
		final Channel ch = server.start(udpPort, redisService, tcpPport);
		new TCPServer().init(ch, tcpPport);
		new BalanceClientThread(tcpPport, "127.0.0.1", 7005, ch);
		new Thread(new Runnable() {

			public void run() {
					try {
						Thread.sleep(15  * 1000);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					System.out.println("================================1===="
							+ new Date());
                    System.out.println(LoginerChannel.getKeys());
			}
		}).start();
	}
}
