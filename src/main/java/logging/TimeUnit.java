package logging;

public class TimeUnit {

    private long multiplicand;

    public TimeUnit(String s) {
        switch (s) {
            case "sec":
                this.multiplicand = 1000000000;
                break;
            case "micro":
                this.multiplicand = 1000000;
                break;
            case "mili":
                this.multiplicand = 1000;
                break;
            case "nano":
                this.multiplicand = 1;
                break;
        }
    }

    public double Convert(long x)
    {

        return (double)x / (this.multiplicand);
    }
}
