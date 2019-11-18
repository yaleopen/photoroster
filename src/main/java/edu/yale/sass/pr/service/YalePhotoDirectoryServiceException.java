package edu.yale.sass.pr.service;

public class YalePhotoDirectoryServiceException extends Exception{
	public YalePhotoDirectoryServiceException(Throwable t){
		super(t);
	}

	public YalePhotoDirectoryServiceException(String message) {
		super(message);
	}

}
