package org.imclient.bean;

import java.util.ArrayList;
import java.util.List;

public class Pack {

	private int subLen;
	private List<SubPack> subList=new ArrayList<SubPack>();
	public int getSubLen() {
		return subLen;
	}
	public void setSubLen(int subLen) {
		this.subLen = subLen;
	}
	public List<SubPack> getSubList() {
		return subList;
	}
	public void setSubList(List<SubPack> subList) {
		this.subList = subList;
	}
	public void addSubPack(SubPack subPack){
		this.subList.add(subPack);
	}
	public boolean isFull(){
		return subList.size()==subLen;
	}
}
