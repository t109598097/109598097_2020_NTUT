import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LogicSimulator
{
    private Vector<Device> circuits;
    private Vector<Device> iPins;
    private Vector<Device> oPins;
    private List<String> lcfStringList;
    private Boolean simulatorExist;


    public LogicSimulator()
    {
        circuits = new Vector<>();
        iPins = new Vector<>();
        oPins = new Vector<>();
        lcfStringList = new ArrayList<>();
        simulatorExist = false;
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

    public List<String> getLcfStringList(){return this.lcfStringList;}

    public Boolean load(String filePath){
        boolean loadResult = false;

        try {
            File lcf = new File(filePath);
            Scanner scanner = new Scanner(lcf);

            if(this.simulatorExist) {
                clearSimulator();
            }

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
        String simulationResult;

        StringBuilder stringBuilder = getTableHead("Simulation Result:", this.iPins.size());

        stringBuilder.append(getSimulationString(inputValues));

        simulationResult = stringBuilder.toString();

        return simulationResult;
    }

    public String getTruthTable(){
        String truthTable = "";

        int binarySize = this.iPins.size();
        int binaryAmount = (int) Math.pow(2,binarySize);

        StringBuilder stringBuilder = getTableHead("Truth table:", this.iPins.size());

        for(int i=0; i<binaryAmount; i++){
            Vector<Boolean> inputValues = new Vector<>();
            String binaryStr = String.format("%" + binarySize + "s",
                    Integer.toBinaryString(i)).replaceAll(" ", "0");
            for(Character c: binaryStr.toCharArray()){
                inputValues.add(c=='1');
            }

            stringBuilder.append(getSimulationString(inputValues));
        }

        truthTable = stringBuilder.toString();

        return truthTable;
    }

    public StringBuilder getTableHead(String title, int size){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(title).append("\n");

        for(int i=0; i<size; i++){
            if(i<9){
                stringBuilder.append("i").append(" ");
            } else {
                stringBuilder.append("i").append("  ");
            }
        }

        stringBuilder.append("|");
        for(int i=0; i<this.oPins.size(); i++){
            stringBuilder.append(" ").append("o");
        }
        stringBuilder.append("\n");

        for(int i=0; i<size; i++){
            if(i<9){
                stringBuilder.append(i+1).append(" ");
            } else {
                stringBuilder.append(i+1).append("  ");
            }
        }

        stringBuilder.append("|");
        for(int i=0; i<this.oPins.size(); i++){
            stringBuilder.append(" ").append(i+1);
        }
        stringBuilder.append("\n");

        for(int i=0; i<size; i++){
            if(i<9){
                stringBuilder.append("-").append("-");
            } else {
                stringBuilder.append("-").append("--");;
            }
        }

        stringBuilder.append("+");
        for(int i=0; i<this.oPins.size(); i++){
            stringBuilder.append("-").append("-");
        }
        stringBuilder.append("\n");

        return stringBuilder;
    }

    public String getSimulationString(Vector<Boolean> inputValues){
        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0; i<inputValues.size(); i++){
            this.iPins.get(i).setInput(inputValues.get(i));
        }

        for(int i=0; i<inputValues.size(); i++){
            if(i<9){
                stringBuilder.append(inputValues.get(i)?"1":"0").append(" ");
            } else {
                stringBuilder.append(inputValues.get(i)?"1":"0").append("  ");;
            }
        }

        stringBuilder.append("|");
        for(int i=0; i<this.oPins.size(); i++){
            stringBuilder.append(" ").append(this.oPins.get(i).getOutput()?"1":"0");
        }
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    public void constructDevice(List<String> lcfStringList){
        int iPinAmount = Integer.parseInt(lcfStringList.get(0));
        int circuitsAmount = Integer.parseInt(lcfStringList.get(1));

        for(int i=0; i<iPinAmount; i++){
            this.iPins.add(new IPin());
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
                this.oPins.add(new OPin());
                this.oPins.get(this.oPins.size()-1).addInputPin(this.circuits.get(i));
            }
        }
    }

    public boolean detectLcfFormat(List<String> lcfData) {
        String iPinsAmount = lcfData.get(0);
        String gatesAmount = lcfData.get(1);
        int iPinsCount = 0;

        if(!isPositiveInteger(iPinsAmount)) return false;
        if(!isPositiveInteger(gatesAmount)) return false;
        //檢查設定gate數量與輸入數量是否一致
        if(lcfData.size()-2 != Integer.parseInt(gatesAmount)) return false;

        for(int i=2; i<lcfData.size(); i++){
            List<String> cricuitInfo = new LinkedList<String>(Arrays.asList(lcfData.get(i).split(" ")));
            //gate type
            if(isPositiveInteger(cricuitInfo.get(0))){
                int type = Integer.parseInt(cricuitInfo.get(0));
                if(type<1 || type>3) {
                    return false;
                }
            } else {
                return false;
            }

            for(int j=1; j<cricuitInfo.size(); j++){
                String cricuitIPin = cricuitInfo.get(j);
                if(cricuitIPin.equals("0")) {
                    //end with 0
                    if(j != cricuitInfo.size()-1) return false;
                } else if(cricuitIPin.matches("^-\\d{1,2}$")) {
                    if(isPositiveInteger(cricuitIPin.substring(cricuitIPin.indexOf('-')+1))){
                        int num = Integer.parseInt(cricuitIPin.substring(cricuitIPin.indexOf('-')+1));
                        if(num<1 || num>16) return false;

                        iPinsCount++;
                    }
                    else {
                        return false;
                    }
                } else if(cricuitIPin.matches("^\\d{1,4}\\.1$")) {
                    if(isPositiveInteger(cricuitIPin.substring(0, cricuitIPin.indexOf('.')))){
                        int num = Integer.parseInt(cricuitIPin.substring(0, cricuitIPin.indexOf('.')));
                        if(num<1 || num>1000) return false;
                    }
                    else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }

        if(iPinsCount!=Integer.parseInt(iPinsAmount)) return false;

        return true;
    }

    public void clearSimulator(){
        this.iPins.clear();
        this.oPins.clear();
        this.circuits.clear();
        this.simulatorExist = false;
    }

    public Boolean isPositiveInteger(String s) {
        try {
            if(Integer.parseInt(s)>0) {
                return true;
            } else {
                return false;
            }
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
    }


}
