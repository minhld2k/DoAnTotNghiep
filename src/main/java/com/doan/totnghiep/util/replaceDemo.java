package com.doan.totnghiep.util;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class replaceDemo {

	public static Map<String, String> mapFromFile = new HashMap<String, String>();
	public static Map<Integer, String> mapTrangThai = new HashMap<Integer, String>();
	public static List<Object[]> lsTrangThai = new ArrayList<Object[]>();
	
	public static void getHashMapFromTextFile(){
		if (0 == mapFromFile.size()) {
			try {
				Scanner in = new Scanner(new File("data.txt"));
				String line;
				while (in.hasNext())
				{
					line = in.nextLine();
					String[] keyvalue = line.split(":");
					mapFromFile.put(keyvalue[0].trim(), keyvalue[1].trim());
				} 
				in.close();
			} catch (Exception e) {
			}
		}
		
	}
	
	public static String replace(String st) {
		getHashMapFromTextFile();
		if (null == st || st.equals("")) {
			return "";
		}else {
			st = st.toLowerCase().trim();
			String[] arr = st.split(" ");
			String search = "";
			String result = "";
			for (int i = 0; i < arr.length; i++) {
				String temp = (search + " " + arr[i]).trim();
				if (mapFromFile.containsKey(temp)) {
					search = temp;
				}else {
					search = search.replace(" ", "&").trim();
					if (!search.isEmpty()) {
						result = result + "(" + search + ")" + "|";
					}
					search = arr[i];
				}
			}
			search = search.replace(" ", "&").trim();
			result = result + "(" + search + ")";	
			return result;
		}
	}
	
	public static String getMd5(String input) 
    { 
        try { 
        	input = "abc"+input+"xyz";
        	
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    }
    
    public static String convertFromListIntToString(List<Integer> ls) {
    	String str = ls.toString();
    	return str.substring(1, str.length()-1);
    }
    
    public static String convertFromListLongToString(List<Long> ls) {
    	String str = ls.toString();
    	return str.substring(1, str.length()-1);
    }
    
    public static String convertFromListByteToString(List<Byte> ls) {
    	String str = ls.toString();
    	return str.substring(1, str.length()-1);
    }
    
    public static void setmap() {
    	if (0 == mapTrangThai.size()) {
    		mapTrangThai.put(0, "Chưa thanh toán");
        	mapTrangThai.put(1, "Đang xử lý");
        	mapTrangThai.put(2, "Đã xử lý");
        	mapTrangThai.put(3, "Chờ giao hàng");
        	mapTrangThai.put(4, "Đã thanh toán");
		}
    }
    
    public static List<Object[]> convertMapToList(){
    	if (0 == lsTrangThai.size()) {
			for (int i = 0; i < mapTrangThai.size(); i++) {
				Object[] ob = new Object[2];
				ob[0] = i;
				ob[1] = mapTrangThai.get(i);
				lsTrangThai.add(ob);
			}
		}
    	return lsTrangThai;
    }	
    
    public static String getTrangThai(int trangthai) {
    	setmap();
    	return mapTrangThai.get(trangthai);
    }
    
}
