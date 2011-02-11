/**
 * 
 */
package grutils.filemanager

import java.io.File;
import java.util.List;

/**
 * @author daniel
 *
 */
class LocalFileManager implements FileManger {
	
	public File getLocation(String location){
		File loc = new File(location)
		
		if(!(loc && loc.exists() && loc.canRead())){
			throw new FileManagerException("Cannot read local Location")
		}
		
		loc
	}
	
	/**
	 * Get directory in a manner that checks for its presence first
	 * @param directory
	 * @return
	 */
	public File getDirectory(String directory){
		File dir = getLocation(directory)
		
		if(!dir.isDirectory()){
			return null
		}
		
		dir
	}

	/**
	 * Get the file in a manner that checks for its presence first
	 * @param filename
	 * @return
	 */
	public File getFile(String filename){
		File file = getLocation(filename)
		
		if(!file.isFile()){
			return null
		}
		
		file
	}
	
	/* (non-Javadoc)
	 * @see grutils.filemanager.FileManger#cp(java.lang.String, java.lang.String)
	 */
	@Override
	public void cp(String origName, String copyName, boolean overwriteExisting) {
		mvCpChecks(origName, copyName, overwriteExisting)
		
		File file = new File(origName)
		
		//use ant task
		def ant = new AntBuilder()
		
		if(file.isDirectory()){
			ant.copy(todir:copyName, overwrite:overwriteExisting) {
				fileset(dir:origName)
			  }
		}else{
			ant.copy(
				file:origName,
				tofile:copyName,
				overwrite:overwriteExisting
			)
		}	
	}
	
	/* (non-Javadoc)
	 * @see grutils.filemanager.FileManger#ls(java.lang.String)
	 */
	@Override
	public List<String> ls(String directory) {
		File dir = getDirectory(directory)
		
		def files = dir.listFiles().collect{
			it.getName()
		}
	}

	private void mvCpChecks(String srcPath, String destPath, boolean overwriteExisting){
		//check new location doesn't already exist
		File newLoc = new File(destPath)
		if(newLoc.exists()){
			if(overwriteExisting){
				rm(destPath)
			}else{
				throw new FileManagerException("$destPath already exists, cannot move $srcPath to it")
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see grutils.filemanager.FileManger#mv(java.lang.String, java.lang.String)
	 */
	@Override
	public void mv(String origName, String newName, boolean overwriteExisting) {
		mvCpChecks(origName, newName, overwriteExisting)
		
		def file = getLocation(origName)
		file.renameTo(newName)
		
		def newFile = new File(newName)
		
		if(!newFile.exists()){
			throw new FileManagerException("$location could not be moved")
		}
	}

	/* (non-Javadoc)
	 * @see grutils.filemanager.FileManger#rm(java.lang.String)
	 */
	@Override
	public void rm(String location) {
		File file = getLocation(location)
		
		if(file){
			if(!file.canWrite()){
				throw new FileManagerException("No access to $location, cannot delete.")
			}
			
			if(file.isDirectory()){
				file.deleteDir()
			}else{
				file.delete()
			}
		}else{
			file = new File(location)
		}
		
		if(file.exists()){
			throw new FileManagerException("$location could not be deleted")
		}
		
	}

	/* (non-Javadoc)
	 * @see grutils.filemanager.FileManger#touch(java.lang.String)
	 */
	@Override
	public void touch(String fileName, boolean createIntermediateDirs) {
		def ant = new AntBuilder()
		ant.touch(
			file:fileName,
			mkdirs:createIntermediateDirs
		)
	}
	
	@Override
	void createDirectory(String dirName){
		throw new Exception("Not Implemented")
	}

}
