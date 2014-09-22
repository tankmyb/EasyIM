package com.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

public class Test {
	public static void runCMD(String path) throws Exception 
    { 
            Process p = Runtime.getRuntime().exec("cmd /c cmd.exe /c " + path+" exit"); 
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));   
            String readLine = br.readLine();   
            while (readLine != null) { 
                readLine = br.readLine(); 
                System.out.println(readLine); 
            } 
            if(br!=null){ 
                br.close(); 
            } 
            p.destroy(); 
            p=null; 
    } 
    public static void runCMDShow(String path) throws Exception 
    { 
        Process p = Runtime.getRuntime().exec("cmd /c start cmd.exe /c " + path+" exit"); 
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));   
        String readLine = br.readLine();   
        while (readLine != null) { 
            readLine = br.readLine(); 
            System.out.println(readLine); 
        } 
        if(br!=null){ 
            br.close(); 
        } 
        p.destroy(); 
        p=null; 
    } 
    public static void main(String[] args) {   
        String path = "E:\\tank\\workspace\\EasyIM\\IMApi\\proto\\";  
        System.out.println(new Date()); 
        try { 
            runCMDShow(path); 
            runCMD(path);
        } catch (Exception e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 
        System.out.println(new Date()); 
    }
}
