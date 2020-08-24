import org.junit.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class LogicSimulatorTest
{
    String file1Path;
    String file2Path;
    String file3Path;
    List<String> LcfString;

    @Before
    public void setUp()
    {
        file1Path = "src/File1.lcf";
        file2Path = "src/File2.lcf";
        file3Path = "";
    }

    @Test
    public void testLoad()
    {
        //file not exist
        LogicSimulator logicSimulator = new LogicSimulator();

        assertEquals(false, logicSimulator.load(file3Path));

        //file exist
        assertEquals(true, logicSimulator.load(file1Path));
    }

    @Test
    public void testVerifyLcfFormat()
    {

    }

    @Test
    public void splitLcfString()
    {

    }

    @Test
    public void constructLcf()
    {

    }

    @Test
    public void testGetSimulationResult()
    {
        LogicSimulator logicSimulator = new LogicSimulator();

        logicSimulator.load(file1Path);

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
    public void getTruthTable()
    {

    }
}
