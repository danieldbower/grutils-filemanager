package grutils.filemanager

class FileManagerException extends IOException{

	FileManagerException(){
		super()
	}
	
	FileManagerException(String message){
		super(message)
	}
	
	FileManagerException(String message, Throwable throwable){
		super(message, throwable)
	}
}
