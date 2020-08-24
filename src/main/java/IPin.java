import java.util.Vector;

public class IPin extends Device
{
    private boolean value = false;

    @Override
    public void setInput(boolean value)
    {
        this.value = value;
    }

    @Override
    public boolean getOutput()
    {
        return this.value;
    }
}
