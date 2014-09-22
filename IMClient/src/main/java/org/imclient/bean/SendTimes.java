package org.imclient.bean;

/**
 * 发送次数
 * @author bo
 *
 */
public class SendTimes {

	private int time;
	private String chatId;
	
	public SendTimes(int time,String chatId){
		this.time = time;
		this.chatId = chatId;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
}
