package com.imbalancer.service;

import io.netty.channel.ChannelHandlerContext;

import java.util.Date;
import java.util.List;

import com.imapi.bean.balance.BalanceReqBean;
import com.imapi.bean.balance.BalanceRespBean;
import com.imapi.bean.balance.req.LoginerConnReqBean;
import com.imapi.bean.balance.resp.LoginnerConnRespBean;
import com.imapi.common.BalanceReqType;
import com.imbalancer.ClientChannel;
import com.imbalancer.QueueThread;
import com.imbalancer.SyncResponseFuture;
import com.imbalancer.annotation.ExecuteService;
import com.imbalancer.annotation.Resource;
import com.imbase.utils.JacksonUtil;
import com.imbase.utils.SeqUtil;

@ExecuteService(value=BalanceReqType.LOGINER_CONN)
public class LoginConnService implements IService{

	@Resource
	private QueueThread queueThread;
	public void execute(final ChannelHandlerContext ctx,
			BalanceReqBean reqBean) {
        System.out.println(queueThread+"=============LoginConnService==========");
		final LoginerConnReqBean loginerConnReqBean = JacksonUtil.resolve(
				reqBean.getReq(), LoginerConnReqBean.class);
		queueThread.offer(new Runnable() {
			public void run() {
				if (ClientChannel.size() == 0) {
					ClientChannel.addClient(
							ctx.channel().id().asLongText(),
							loginerConnReqBean.getIp() + ":"
									+ loginerConnReqBean.getPort());
					BalanceRespBean respBean = new BalanceRespBean();
					LoginnerConnRespBean subBuilder = new LoginnerConnRespBean();
					subBuilder.setSeq(-1);
					respBean.setType((byte) 1);
					respBean.setResp(JacksonUtil.getJsonStr(subBuilder));
					ctx.writeAndFlush(respBean);
				} else {
					Integer seq = SeqUtil.incrementSeq();
					BalanceRespBean respBean = new BalanceRespBean();
					LoginnerConnRespBean subBuilder = new LoginnerConnRespBean();
					subBuilder.setSeq(seq);

					List<String> coll = ClientChannel.getValues();
					subBuilder.setConnList(coll);
					System.out.println(coll + "--------------");
					respBean.setType((byte) 1);
					respBean.setResp(JacksonUtil.getJsonStr(subBuilder));
					SyncResponseFuture future = new SyncResponseFuture(seq);
					ctx.writeAndFlush(respBean);
					System.out.println("=========SyncResponseFuture====="
							+ new Date());
					Object obj = future.get(seq);
					if (obj != null) {
						ClientChannel.addClient(ctx.channel().id()
								.asLongText(), loginerConnReqBean.getIp()
								+ ":" + loginerConnReqBean.getPort());
					}
				}
			}
		});
	
	}

	
}
