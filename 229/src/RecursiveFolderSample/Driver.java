package RecursiveFolderSample;

public class Driver {

	public static void main(String[] args) {
		FakeFolder root = new FakeFolder("C:", null);
		FakeFolder pictures = new FakeFolder("Pictures", root);
		FakeFolder videos = new FakeFolder("Videos", root);
		FakeFolder homework = new FakeFolder("Homework", root);
		FakeFolder java = new FakeFolder("Java", homework);
		FakeFolder hw3 = new FakeFolder("hw3", java);
		FakeFolder html = new FakeFolder("HTML", homework);
		
		FakeFile hw1 = new FakeFile("hw1", "This is hw1", java);
		FakeFile hw2 = new FakeFile("hw2", "This is hw2", java);
		FakeFile hw3main = new FakeFile("hw3main", "This is hw3", hw3);
		FakeFile hw3other = new FakeFile("hw3other", "This is hw3", hw3);
		FakeFile html1 = new FakeFile("html1", "This is html1", html);
		FakeFile html2 = new FakeFile("html2", "This is html2", html);
		FakeFile maymay = new FakeFile("img1.jpeg", "This is an image", pictures);
		FakeFile vid1 = new FakeFile("vid.mp4", "This is a video", videos);
		
//		root.showContents(0);
//		System.out.println();
//		
//		java.showContents(0);
//		System.out.println();
		
		root.showAllContents(0);
		System.out.println();
		
		root.deleteFile("hw3other");
		
		root.deleteFolderMoveFiles("Java");
		root.showAllContents(0);
		
		System.out.println();
	}

}
