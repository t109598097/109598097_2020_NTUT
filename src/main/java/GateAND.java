public class GateAND  extends Device
{
    @Override
    public void addInputPin(Device IPin)
    {
        this.iPins.add(IPin);
    }

    @Override
    public boolean getOutput()
    {
        boolean outValue = this.iPins.get(0).getOutput();

        for(int i=0; i<this.iPins.size(); i++){
            outValue = outValue & this.iPins.get(i).getOutput();
        }

        return outValue;
    }
}
