package tail;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public final class FileUtils {
	private static Logger logger = Logger.getLogger(FileUtils.class);
	static final int CHARS_PER_LINE = 128;

	// tail -n n
	public static String[] tail(File f, int n) {
		byte[] buffer = new byte[n * CHARS_PER_LINE];
		String[] result = null;
		try {
			RandomAccessFile rf = new RandomAccessFile(f, "r");
			List<String> lines = getLines(rf, buffer, n);
			if (lines != null) {
				// get the last n lines of the array
				result = new String[n];
				for (int i = lines.size() - n; i < lines.size(); i++)
					result[i - (lines.size() - n)] = lines.get(i);
			}
		} catch (Exception e) {
			throw new LogParsingException("Error fetch the last " + n + "lines of file.", e);
		}
		return result;
	}

	/**
	 * 
	 * @param n
	 *            : line counts
	 * @return
	 * @throws Exception
	 */
	static List<String> getLines(RandomAccessFile rf, byte[] bytes, int n)
			throws Exception {
		if (bytes.length > rf.length())
			return null;
		// read the string
		rf.seek(rf.length() - bytes.length);
		if (!readLastBytes(rf, bytes))
			return null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(bytes)));
		List<String> result = new ArrayList<String>();
		String line = reader.readLine(); // drop the first fragment of line
		while ((line = reader.readLine()) != null) {
			result.add(line);
		}
		if (result.size() >= n)
			return result;
		else
			return getLines(rf, new byte[bytes.length * 2], n);
	}

	static boolean readLastBytes(RandomAccessFile rf, byte[] bytes)
			throws Exception {
		rf.seek(rf.length() - bytes.length);
		if (rf.read(bytes) != bytes.length) {// read error
			logger.error("can't read last bytes");
			return false;
		}
		return true;
	}
}
