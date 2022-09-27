package RecursiveFolderSample;

public class FakeFile extends Record {
	
	String content;
	FakeFolder parent;
	
	public FakeFile(String name, String content, FakeFolder parent) {
		super(name, parent, false);
		this.content = content;
		parent.files.add(this);
	}

}
