package bench.hdd;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

import timing.Timer;

public class FileWriter {

    private static final int MIN_BUFFER_SIZE = 1024 * 1; // KB
    private static final int MAX_BUFFER_SIZE = 1024 * 1024 * 32; // MB
    private static final int MIN_FILE_SIZE = 1024 * 1024 * 1; // MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 512; // MB
    private Timer timer = new Timer();
    private double benchScore;

    /**
     * Writes files on disk using a variable write buffer and fixed file size.
     *
     * @param filePrefix
     *            - Path and file name
     * @param fileSuffix
     *            - file extension
     * @param minIndex
     *            - start buffer size index
     * @param maxIndex
     *            - end buffer size index
     * @param fileSize
     *            - size of the benchmark file to be written in the disk
     * @throws IOException
     */
    public void streamWriteFixedSize(String filePrefix, String fileSuffix,
                                     int minIndex, int maxIndex, long fileSize, boolean clean)
            throws IOException {

        System.out.println("Stream write benchmark with fixed file size");
        int currentBufferSize = MIN_BUFFER_SIZE;
        String fileName;
        int counter = 0;
        benchScore = 0;

        while (currentBufferSize <= MAX_BUFFER_SIZE
                && counter <= maxIndex - minIndex) {
            fileName = ... prefix + ? + suffix
            writeWithBufferSize(fileName, currentBufferSize, fileSize);
            // update buffer size
			...
            counter++;
        }

        benchScore /= (maxIndex - minIndex + 1);
        String partition = filePrefix.substring(0, filePrefix.indexOf(":\\"));
        System.out.println("File write score on partition " + partition + ": "
                + String.format("%.2f", benchScore) + " MB/sec");
    }

    /**
     * Writes files on disk using a variable file size and fixed buffer size.
     *
     * @param filePrefix
     *            - Path and file name
     * @param fileSuffix
     *            - file extension
     * @param minIndex
     *            - start file size index
     * @param maxIndex
     *            - end file size index
     * @param fileSize
     *            - size of the benchmark file to be written in the disk
     */
    public void streamWriteFixedBuffer(String filePrefix, String fileSuffix,
                                       int minIndex, int maxIndex, int bufferSize, boolean clean)
            throws IOException {

        System.out.println("Stream write benchmark with fixed buffer size");
        int currentFileSize = MIN_FILE_SIZE;

        while (currentFileSize <= MAX_FILE_SIZE
                && counter <= maxIndex - minIndex) {
			...
            // update fileSize instead of bufferSize
        }

        benchScore /= (maxIndex - minIndex + 1);
        String partition = filePrefix.substring(0, filePrefix.indexOf(":\\"));
        System.out.println("File write score on partition " + partition + ": "
                + String.format("%.2f", benchScore) + " MB/sec");
    }

    /**
     * Writes a file with random binary content on the disk, using a given file
     * path and buffer size.
     */
    private void writeWithBufferSize(String fileName, int myBufferSize,
                                     long fileSize, boolean clean) throws IOException {

        File folderPath = new File(fileName.substring(0,
                fileName.lastIndexOf(File.separator)));

        // create folder path to benchmark output
        if (!folderPath.isDirectory())
            folderPath.mkdirs();

        final File file = new File(fileName);
        // create stream writer with given buffer size
        final BufferedOutputStream outputStream = new BufferedOutputStream(file stream, buffer size);

        byte[] buffer = new byte[?];
        int i = 0;
        long toWrite = fileSize / myBufferSize;
        Random rand = new Random();

        timer.start();
        while (i < toWrite) {
            // generate random content to write
            rand.nextBytes(buffer);

            outputStream.write(buffer);
            i++;
        }
        printStats(fileName, fileSize, myBufferSize);

        outputStream.close();
        if(clean)
            delete file on exit ?
    }

    private void printStats(String fileName, long totalBytes, int myBufferSize) {
        NumberFormat nf = new DecimalFormat("#.00");
        final long time = timer.stop();
        double seconds = ...; // calculated from timer's 'time'
        double megabytes = totalBytes / ...; //
        double rate = ... MB/s; // calculated from the previous two variables
        System.out.println("Done writing " + totalBytes + " bytes to file: "
                + fileName + " in " + nf.format(seconds) + " ms ("
                + nf.format(rate) + "MB/sec)" + " with a buffer size of "
                + myBufferSize / 1024 + " kB");

        // actual score is write speed (MB/s)
        benchScore += rate;
    }
}