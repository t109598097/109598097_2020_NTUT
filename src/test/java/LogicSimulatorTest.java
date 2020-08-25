import org.junit.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class LogicSimulatorTest
{
    String file1Path;
    String file2Path;
    String file1Data;
    String wrongData;

    @Before
    public void setUp()
    {
        file1Path = "src/File1.lcf";
        file2Path = "src/File2.lcf";
        file1Data = "3\n" +
                    "3\n" +
                    "1 -1 2.1 3.1 0\n" +
                    "3 -2 0\n" +
                    "2 2.1 -3 0";
        wrongData = "2\n" +
                "3\n" +
                "1 -1 2.1 3.1 0\n" +
                "3 -2 0\n" +
                "2 2.1 -3 0";
    }

    @Test
    public void testDevicePolymorphism()
    {
        LogicSimulator logicSimulator = new LogicSimulator();
        assertEquals(LogicSimulator.class.getName(), logicSimulator.getClass().getName());
    }

    @Test
    public void testLoad()
    {
        //file not exist
        LogicSimulator logicSimulator = new LogicSimulator();

        assertEquals(false, logicSimulator.load(""));

        //file exist
        assertEquals(true, logicSimulator.load(file1Path));
    }


    @Test
    public void testConstructDevice()
    {
        LogicSimulator logicSimulator = new LogicSimulator();

        List<String> lcfStringList = new ArrayList<String>(Arrays.asList(file1Data.split("\\n")));

        logicSimulator.constructDevice(lcfStringList);

        assertEquals(3, logicSimulator.getiPins().size());
        assertEquals(1, logicSimulator.getoPins().size());
        assertEquals(3, logicSimulator.getCircuits().size());
    }

    @Test
    public void testConnectDevice()
    {
        LogicSimulator logicSimulator = new LogicSimulator();

        List<String> lcfStringList = new ArrayList<String>(Arrays.asList(file1Data.split("\\n")));

        logicSimulator.constructDevice(lcfStringList);

        logicSimulator.connectDevice(lcfStringList);

        assertEquals(3, logicSimulator.getCircuits().get(0).iPins.size());
        assertEquals(1, logicSimulator.getCircuits().get(1).iPins.size());
        assertEquals(2, logicSimulator.getCircuits().get(2).iPins.size());
        assertEquals(1, logicSimulator.getoPins().get(0).iPins.size());
    }

    @Test
    public void testGetSimulationResult()
    {
        LogicSimulator logicSimulator = new LogicSimulator();

        List<String> lcfStringList = new ArrayList<String>(Arrays.asList(file1Data.split("\\n")));

        logicSimulator.constructDevice(lcfStringList);

        logicSimulator.connectDevice(lcfStringList);

        Vector<Boolean> inputValues = new Vector<>();
        inputValues.add(false);
        inputValues.add(true);
        inputValues.add(true);

        assertEquals("Simulation Result:\n" +
                "i i i | o\n" +
                "1 2 3 | 1\n" +
                "------+--\n" +
                "0 1 1 | 0\n", logicSimulator.getSimulationResult(inputValues));
    }

    @Test
    public void testGetTruthTable()
    {
        LogicSimulator logicSimulator = new LogicSimulator();

        List<String> lcfStringList = new ArrayList<String>(Arrays.asList(file1Data.split("\\n")));

        logicSimulator.constructDevice(lcfStringList);

        logicSimulator.connectDevice(lcfStringList);

        assertEquals("Truth table:\n" +
                "i i i | o\n" +
                "1 2 3 | 1\n" +
                "------+--\n" +
                "0 0 0 | 0\n" +
                "0 0 1 | 0\n" +
                "0 1 0 | 0\n" +
                "0 1 1 | 0\n" +
                "1 0 0 | 1\n" +
                "1 0 1 | 1\n" +
                "1 1 0 | 0\n" +
                "1 1 1 | 0\n", logicSimulator.getTruthTable());
    }

    @Test
    public void testDetectLcfFormat()
    {
        //current
        LogicSimulator logicSimulator = new LogicSimulator();

        List<String> lcfStringList = new ArrayList<String>(Arrays.asList(file1Data.split("\\n")));

        assertEquals(true, logicSimulator.detectLcfFormat(lcfStringList));

        //wrong
        lcfStringList = new ArrayList<String>(Arrays.asList(wrongData.split("\\n")));

        assertEquals(false, logicSimulator.detectLcfFormat(lcfStringList));
    }

    @Test
    public void testClearSimulator()
    {
        LogicSimulator logicSimulator = new LogicSimulator();

        List<String> lcfStringList = new ArrayList<String>(Arrays.asList(file1Data.split("\\n")));

        logicSimulator.constructDevice(lcfStringList);

        logicSimulator.connectDevice(lcfStringList);

        logicSimulator.clearSimulator();

        assertEquals(0, logicSimulator.getCircuits().size());
        assertEquals(0, logicSimulator.getiPins().size());
        assertEquals(0, logicSimulator.getoPins().size());

    }
}
