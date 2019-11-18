package edu.yale.sass.pr.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;


public class IDPhoto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static final long internvalTimeToRefresh =30*1000;//milliseconds for 4 hours

	private byte[] imageBytes;
	
	private String netId;
	
	private boolean publicView;
	
	private boolean currentAvailable;
	
	private String bannerId;
	
	private Timestamp timeStamp;
	
	private String netIdMD5Sum;
	
	private static Log log = LogFactory.getLog(IDPhoto.class);

	public IDPhoto(byte[] imagetBytes,String netId){
		this.imageBytes = imagetBytes;
		this.netId = netId;
		this.currentAvailable=true;
		this.publicView=true;
		this.timeStamp = new Timestamp(System.currentTimeMillis());
		this.netIdMD5Sum = createCheckSum(netId);
	}

	private String createCheckSum(String netId) {
		try {
			return MD5CheckSum.getInstance().create(netId);
		} catch (NoSuchAlgorithmException e) {
			log.error("Could not find MD5 algorithm",e);
			return "";
		}
	}

	public byte[] getImageBytes() {
		return imageBytes;
	}

	public String getNetId() {
		return netId;
	}

	public boolean isCurrentAvailable() {
		return currentAvailable;
	}

	public void setCurrentAvailable(boolean currentAvailable) {
		this.currentAvailable = currentAvailable;
	}

	public boolean isPublicView() {
		return publicView;
	}

	public void setPublicView(boolean publicView) {
		this.publicView = publicView;
	}

	public String getBannerId() {
		return bannerId;
	}

	public void setBannerId(String bannerId) {
		this.bannerId = bannerId;
	}
	
	public boolean isRefreshCache(){
		if (isCurrentAvailable())
			return false;
		
		if ((System.currentTimeMillis() - getTimeStamp().getTime() > internvalTimeToRefresh ))
			return true;
		else
			return false;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getNetIdMD5Sum() {
		return netIdMD5Sum;
	}
	
}
