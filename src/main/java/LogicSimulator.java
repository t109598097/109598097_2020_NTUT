import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class LogicSimulator
{
    private Vector<Device> circuits;
    private Vector<Device> iPins;
    private Vector<Device> oPins;
    private List<String> LcfString;


    public Boolean load(String filePath){
        boolean loadResult = false;

        try {
            this.LcfString = new ArrayList<String>();
            File lcf = new File(filePath);
            Scanner scanner = new Scanner(lcf);
            while (scanner.hasNextLine()) {
                LcfString.add(scanner.nextLine());
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
        return simulationResult;
    }

    public String getTruthTable(){

        String truthTable = "";
        return truthTable;
    }

}
