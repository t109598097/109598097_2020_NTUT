import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LogicSimulator
{
    private Vector<Device> circuits;
    private Vector<Device> iPins;
    private Vector<Device> oPins;
    private List<String> lcfStringList;

    public LogicSimulator()
    {
        circuits = new Vector<>();
        iPins = new Vector<>();
        oPins = new Vector<>();
    }

    public Vector<Device> getoPins(){
        return this.oPins;
    }

    public Vector<Device> getiPins(){
        return this.iPins;
    }

    public Vector<Device> getCircuits(){
        return this.circuits;
    }

    public Boolean load(String filePath){
        boolean loadResult = false;

        try {
            this.lcfStringList = new ArrayList<String>();
            File lcf = new File(filePath);
            Scanner scanner = new Scanner(lcf);
            while (scanner.hasNextLine()) {
                this.lcfStringList.add(scanner.nextLine());
            }
            scanner.close();

            loadResult = true;
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        }

        return loadResult;
    }

    public String getSimulationResult(Vector<Boolean> inputValues){
        String simulationResult = "";

        StringBuilder result = new StringBuilder();
        for(int i=0; i<inputValues.size(); i++){

        }

        return simulationResult;
    }

    public String getTruthTable(){
        String truthTable = "";
        return truthTable;
    }

    public void constructDevice(List<String> lcfStringList){
        int iPinAmount = Integer.parseInt(lcfStringList.get(0));
        int oPinAmount = 1;
        int circuitsAmount = Integer.parseInt(lcfStringList.get(1));

        for(int i=0; i<iPinAmount; i++){
            this.iPins.add(new IPin());
        }

        for(int i=0; i<oPinAmount; i++){
            this.oPins.add(new OPin());
        }

        for(int i=0; i<circuitsAmount; i++){
            Device circuit;
            String circuitInfo = lcfStringList.get(i+2);
            Character type = circuitInfo.charAt(0);
            switch (type) {
                case '1':
                    circuit = new GateAND();
                    break;
                case '2':
                    circuit = new GateOR();
                    break;
                case '3':
                    circuit = new GateNOT();
                    break;
                default:
                    throw new RuntimeException("constructDevice type error");
            }
            this.circuits.add(circuit);
        }
    }

    public void connectDevice(List<String> lcfStringList){

        int circuitsAmount = Integer.parseInt(lcfStringList.get(1));

        boolean oPinGateNum[] = new boolean[circuitsAmount];//true: oPinGate, false: otherGate
        for(int i=0; i<circuitsAmount; i++){
            oPinGateNum[i] = true;
        }

        for(int circuitsNum=0; circuitsNum<circuitsAmount; circuitsNum++){

            Device circuit = this.circuits.get(circuitsNum);
            String circuitInfo = lcfStringList.get(circuitsNum+2);
            List<String> circuitIPinsInfo = new ArrayList<String>(Arrays.asList(circuitInfo.split(" ")));

            for(int circuitIPinsNum=1; circuitIPinsNum<circuitIPinsInfo.size(); circuitIPinsNum++){

                String iPinString = circuitIPinsInfo.get(circuitIPinsNum);

                if(iPinString.equals("0")) {
                    break;
                } else if(iPinString.contains("-")) {

                    int iPinNum = Integer.parseInt(iPinString
                            .substring(iPinString.indexOf('-')+1));

                    iPinNum = iPinNum - 1;

                    circuit.addInputPin(this.iPins.get(iPinNum));

                } else if(iPinString.contains(".")) {

                    int circuitNum = Integer.parseInt(iPinString
                                .substring(0, iPinString.indexOf('.')));

                    circuitNum = circuitNum - 1;

                    oPinGateNum[circuitNum] = false;

                    circuit.addInputPin(this.circuits.get(circuitNum));
                }
            }
        }

        for(int i=0; i<circuitsAmount; i++){
            if(oPinGateNum[i]) {
                this.oPins.get(0).addInputPin(this.circuits.get(i));
            }
        }

    }

}
