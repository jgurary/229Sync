package RecursiveFolderSample;

public abstract class Record {
	
	String name;
	String path;
	/**
	 * The folder that this Record is within
	 */
	Record parent;
	boolean isFolder;
	
	public Record(String name, Record parent, boolean isFolder) {
		this.name = name;
		if(parent==null) {
			this.path = "/";
		}else {
			this.path = parent.path + "/" + parent.name;
		}
		this.parent = parent;
		this.isFolder = isFolder;
	}

}
