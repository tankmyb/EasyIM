package com.imbase.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class SeqUtil {
	public static final int MAXVALUE = Integer.MAX_VALUE-10000000;
	 public static AtomicInteger seq = new AtomicInteger();
	  public static int incrementSeq(){
	  	int s =seq.incrementAndGet();
			if(s>=MAXVALUE){
				seq.set(0);
			}
	  	return s;
	  }
	  public static void main(String[] args) {
	       long start = System.currentTimeMillis();
	       for(int i=0;i<100000;i++){
	    	   SeqUtil.incrementSeq();
	       }
	       System.out.println(System.currentTimeMillis()-start);
		}
}
