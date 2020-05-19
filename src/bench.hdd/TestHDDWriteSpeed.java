package bench.hdd;

import java.io.IOException;

import bench.IBenchmark;

public class HDDWriteSpeed implements IBenchmark {

    @Override
    public void initialize(Object... params) {
    }

    @Override
    public void warmUp() {
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException(
                "Method not implemented. Use run(Object) instead");
    }

    @Override
    public void run(Object... options) {
        FileWriter writer = new FileWriter();
        // either "fs" - fixed size, or "fb" - fixed buffer
        String option = (String) options[0];
        // true/false whether the written files should be deleted at the end
        Boolean clean = (Boolean) options[1];

        String prefix = "path on disk + file name";
        String suffix = ".dat";
        int startIndex = 0;
        int endIndex = 8;
        long fileSize = ... // 256 MB
        int buffersize = ... // 4 KB

        try {
            if (option.equals("?"))
                writer.streamWriteFixedSize(prefix, suffix, startIndex,
                        endIndex, fileSize, clean);
            else if (option.equals("?"))
                writer.streamWriteFixedBuffer(prefix, suffix, startIndex,
                        endIndex, bufferSize, clean);
            else
                throw new IllegalArgumentException("Argument "
                        + options[0].toString() + " is undefined");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clean() {
    }

    @Override
    public String getResult() {
        return null;
    }
}
