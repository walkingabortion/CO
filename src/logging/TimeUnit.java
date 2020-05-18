package logging;

public class TimeUnit {

    private long multiplicand;

    public TimeUnit(String s) {
        if(s.equals("sec"))
            this.multiplicand = 1000000000;
        else
            if(s.equals("micro"))
                this.multiplicand = 1000000;
            else
                if(s.equals("mili"))
                    this.multiplicand = 1000;
                else
                    if(s.equals("nano"))
                        this.multiplicand = 1;

    }

    public double Convert(long x)
    {

        return (double)x / (this.multiplicand);
    }
}
