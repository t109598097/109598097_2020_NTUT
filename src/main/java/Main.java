import Controller.SimulatorController;
import View.SimulatorView;

import java.util.Scanner;

public class Main
{
    public static void main(String args[])
    {
        SimulatorController simulatorController = new SimulatorController();
        simulatorController.processCommand();
    }
}
