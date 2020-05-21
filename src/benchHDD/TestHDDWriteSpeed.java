package benchHDD;

import java.io.IOException;



public class TestHDDWriteSpeed {

    private double score;

    public double getScore() {
        return score;
    }

    public TestHDDWriteSpeed() {
    }

    public void initialize(Object... params) {
    }


    public void warmUp() {

    }


    public void run() {
        throw new UnsupportedOperationException(
                "Method not implemented. Use run(Object) instead");
    }



    public void run(Object... options) {
        FileWriter writer = new FileWriter();
        // either "fs" - fixed size, or "fb" - fixed buffer
        String option = (String) options[0];
        // true/false whether the written files should be deleted at the end
        Boolean clean = (Boolean) options[1];

        String prefix = (String) options[2];

        String suffix = ".dat";
        int startIndex = 0;
        int endIndex = 7;
        long fileSize = 256 * 1000000 ;// 256 MB
        int bufferSize = 4096; // 4 KB

        try {
            if (option.equals("fs"))
            {writer.streamWriteFixedSize(prefix, suffix, startIndex,
                    endIndex, fileSize, clean);
                score=writer.getBenchScore();
            }
            else if (option.equals("fb")) {
                writer.streamWriteFixedBuffer(prefix, suffix, startIndex,
                        endIndex, bufferSize, clean);
                score=writer.getBenchScore();
            }
            else
                throw new IllegalArgumentException("Argument "
                        + options[0].toString() + " is undefined");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void clean() {
    }

    public void get_score()
    {

    }

    public String getResult() {
        return null;
    }
}