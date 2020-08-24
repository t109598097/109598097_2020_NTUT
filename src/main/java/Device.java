import java.util.Vector;

public class Device {

    protected Vector<Device> iPins;

    public Device()
    {
        iPins = new Vector<>();
    }

    public void addInputPin(Device IPin)
    {
        throw new RuntimeException("This is not allowed to addInputPin");
    }

    public void setInput(boolean value)
    {
        throw new RuntimeException("This is not allowed to setInput");
    }

    public boolean getOutput()
    {
        throw new RuntimeException("This is not allowed to getOutput");
    }
}
