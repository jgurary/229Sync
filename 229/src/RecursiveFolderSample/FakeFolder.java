package RecursiveFolderSample;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class FakeFolder extends Record{
	
	//Could have done this, but then Record would need some 
	//functionality to differentiate between folders and files
//	ArrayList<Record> records = new ArrayList<Record>();
	
	ArrayList<FakeFolder> subfolders = new ArrayList<FakeFolder>();
	ArrayList<FakeFile> files = new ArrayList<FakeFile>();
	
	/**
	 * Creates a new FakeFolder. 
	 * 
	 * For the root folder, parent is null
	 * @param name
	 * @param parent
	 */
	public FakeFolder(String name, FakeFolder parent) {
		super(name, parent, true);
		if(parent==null) {
			// Do nothing
		}else {
			parent.subfolders.add(this);
		}
	}
	
	/**
	 * Prints the files and subfolders inside this folder
	 * @param depth - determines spacing before printout.
	 */
	public void showContents(int depth) {
		String spacing = "";
		for(int i=0; i<depth; i++) {
			spacing += " ";
		}
		
		System.out.println(spacing + "Contents of " + this.name);
		for(FakeFolder f: subfolders) {
			System.out.println(spacing + f.name + " " + f.path + " (folder)");
		}
		for(FakeFile f: files) {
			System.out.println(spacing + f.name+ " " + f.path);
		}
	}
	
	/**
	 * Prints the files and subfolders inside this folder, 
	 * and inside all those subfolders, and so forth
	 * @param depth - determines spacing before printout.
	 */
	public void showAllContents(int depth) {
		showContents(depth);
		for(FakeFolder f : subfolders) {
			f.showAllContents(depth+1);
		}
	}
	
	/**
	 * Deletes the first file it finds with the given name, in this folder
	 * or any of the subfolders inside it, or the subfolders inside those, etc
	 * @param name
	 * @return
	 */
	public boolean deleteFile(String name) {
		Iterator<FakeFile> itr = files.iterator();
		while(itr.hasNext()) {
			FakeFile file = itr.next();
			if(file.name.equals(name)) {
				itr.remove();
				return true;
			}
		}
		
		for(FakeFolder f: subfolders) {
			f.deleteFile(name);
		}
		return false;
	}
	
	/**
	 * Deletes the first folder with the given name, and all its subfolders
	 * and files. Searches from the given folder and all its subfolders,
	 * and their subfolders, etc
	 * @param name
	 * @return
	 */
	public boolean deleteFolder(String name) {
		//Handles special case where the given folder's name matches
		if(this.name.equals(name)) {
			//Special case where removing the root
			if(this.parent == null) {
				subfolders.clear();
				files.clear();
			}else { //All other folders besides the root
				((FakeFolder) this.parent).subfolders.remove(this);
			}
			return true;
		}
		
		//Searches for a match inside subfolders
		Iterator<FakeFolder> itr = subfolders.iterator();
		while(itr.hasNext()) {
			FakeFolder folder = itr.next();
			if(folder.name.equals(name)) {
				itr.remove();
				return true;
			}else {
				folder.deleteFolder(name);
			}
		}

		return false;
	}
	
	/**
	 * Deletes a folder without deleting its contents. All contents are now moved
	 * up one directory
	 * @param name
	 * @return
	 */
	public boolean deleteFolderMoveFiles(String name) {
		//Handles special case where the given folder's name matches
				if(this.name.equals(name)) {
					//Special case where removing the root
					if(this.parent == null) {
						subfolders.clear();
						files.clear();
					}else { //All other folders besides the root
						((FakeFolder) this.parent).subfolders.addAll(this.subfolders);
						((FakeFolder) this.parent).files.addAll(this.files);
						((FakeFolder) this.parent).subfolders.remove(this);
					}
					return true;
				}
				
				//Searches for a match inside subfolders
				Iterator<FakeFolder> itr = this.subfolders.iterator();
				while(itr.hasNext()) {
					FakeFolder folder = itr.next();
					if(folder.name.equals(name)) {
						//Can't simply add the folders because we are currently
						//iterating over them!
						ArrayList<FakeFolder> folders = folder.subfolders;
						this.files.addAll(folder.files);
						itr.remove();
						//We can add them here as long as we return immediately
						//If this loop continued, the next itr call would error
						this.subfolders.addAll(folders);
						return true;
					}else {
						folder.deleteFolderMoveFiles(name);
					}
				}

				return false;
	}

}
