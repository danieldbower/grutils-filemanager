package grutils.filemanager;

interface FileManger {
	
	/**
	 * Copy A File or Directory
	 * @param origName
	 * @param copyName
	 */
	void cp(String origName, String copyName, boolean overwriteExisting) throws FileManagerException;
	
	/**
	 * List the contents of a directory
	 * @param directory
	 * @return
	 */
	List<String> ls(String directory) throws FileManagerException;
	
	/**
	 * Move a file or directory
	 * @param origName
	 * @param newName
	 */
	void mv(String origName, String newName, boolean overwriteExisting) throws FileManagerException;
	
	/**
	 * Remove a file or Directory
	 * @param location
	 */
	void rm(String location) throws FileManagerException;
	
	/**
	 * Touch a file or directory
	 * @param fileName
	 */
	void touch(String fileName, boolean createIntermediateDirs) throws FileManagerException;
	
	void createDirectory(String dirName) throws FileManagerException;
}
