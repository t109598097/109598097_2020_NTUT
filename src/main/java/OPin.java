public class OPin extends Device
{
    @Override
    public void addInputPin(Device IPin)
    {
        this.iPins.add(IPin);
    }

    @Override
    public boolean getOutput()
    {
        return this.iPins.get(0).getOutput();
    }
}
