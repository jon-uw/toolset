package tail;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.List;

public class FileUtilsTest {
	static String filename = "logs/test.txt";
	
	//@Test
    public void readLastBytes() throws Exception {
    	RandomAccessFile rf = new RandomAccessFile(filename, "r");
    	byte[] buffer = new byte[9];
    	FileUtils.readLastBytes(rf, buffer);
    	String s = new String(buffer);
    	System.out.println("result is: " + s);
    	Assert.assertTrue("\ngood dog".equals(s));
    }
	
	//@Test
	public void getLines() throws Exception {
		RandomAccessFile rf = new RandomAccessFile("logs/test.txt", "r");
		byte[] buffer = new byte[5];
		List<String> lines = FileUtils.getLines(rf, buffer, 5);
		for (String line : lines) {
			System.out.println("Line: " + line);
		}
		Assert.assertTrue(lines == null || lines.size() >= 5);
	}
	
	@Test
	public void tail() {
		int nLines = 10;
		String[] lines = FileUtils.tail(new File(filename), nLines);
		System.out.println("tail result:");
		for (String line : lines) {
			System.out.println("##" + line);
		}
		Assert.assertTrue(lines == null || lines.length == nLines);
	}
}
