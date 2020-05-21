package benchRAM;

import java.io.IOException;
import java.util.Random;
import timer.Timer;


/**
 * Maps a large file into RAM triggering the virtual memory mechanism. Performs
 * reads and writes to the respective file.<br>
 * The access speeds depend on the file size: if the file can fit the available
 * RAM, then we are measuring RAM speeds.<br>
 * Conversely, we are measuring the access speed of virtual memory, implying a
 * mixture of RAM and HDD access speeds (i.e., lower).
 */
public class VirtualMemoryBenchmark  {

	MemoryMapper core=null;

	private String result1 = "";
	private String result2="";

	public void initialize(Object... params) {
	}

	public void warmUp() {
	}

	public void run() {
		throw new UnsupportedOperationException("Use run(Object[]) instead");
	}

	public void run(Object... options) {
		// expected example: {fileSize, bufferSize}
		Object[] params = (Object[]) options;
		long fileSize = Long.parseLong(params[0].toString()); // e.g. 2-8GB
		int bufferSize = Integer.parseInt(params[1].toString()); // e.g. 4KB

		try {
			core = new MemoryMapper("src/Core/_core", fileSize); // change path as needed
			byte[] buffer = new byte[bufferSize];
			Random rand = new Random();

			Timer timer = new Timer();
			long time_taken=0;

			// write to VM
			timer.start();
			for (long i = 0; i < fileSize; i += bufferSize) {
				// generate random content (see assignments 9,11)
				rand.nextBytes(buffer);
				// write to memory mapper
				core.put(i,buffer);
			}
			time_taken=timer.stop();

			double speed =Double.valueOf(fileSize)/Double.valueOf(time_taken); /* fileSize/time MB/s */

			result1 = "Wrote " + (fileSize / 1024 / 1024L)
					+ " MB to virtual memory at " +String.format("%.2f",speed)+/*speed, with exactly 2 decimals*/  " MB/s";

			// read from VM
			timer.start();
			for (long i = 0; i < fileSize; i += bufferSize) {
				// get from memory mapper
				core.get(i,bufferSize);
			}
			time_taken=timer.stop();
			speed = Double.valueOf(fileSize)/Double.valueOf(time_taken); /* MB/s */

			// append to previous 'result' string
			result2 += "Read " + (fileSize / 1024 / 1024L)
					+ " MB from virtual memory at "+String.format("%.2f",speed)+ /*speed, with exactly 2 decimals*/  " MB/s";

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (core != null)
				core.purge();
		}
	}

	public void clean() {
		if (core != null)
			core.purge();
	}

	public String getResult1() {
		return /*(1 == 1) ? "result" :*/ result1;
	}
	public String getResult2() {
		return /*(1 == 1) ? "result" :*/ result2;
	}

}
