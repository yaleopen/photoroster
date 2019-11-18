package edu.yale.sass.pr.service;

import java.util.List;
import java.util.Map;

//import org.sakaiproject.site.api.Site;

public interface YalePhotoDirectoryService {

	String CLEAR_CACHE_PERMISSION = "roster.clear_cache";

	void clearCache();

	boolean checkYalePhotoClearCachePermission(String permission);

	byte[] loadPhotoFromCache(String netId) throws YalePhotoDirectoryServiceException;

	List loadPhotos(Map<String,String> ids, String roles, String userId) throws YalePhotoDirectoryServiceException;

	boolean isShowMyPhotoStatus(String netId) throws YalePhotoDirectoryServiceException;

	boolean isShowPublicViewPhotoOption(String netId) throws YalePhotoDirectoryServiceException;
	
	String md5CheckSum(String netId);

}
