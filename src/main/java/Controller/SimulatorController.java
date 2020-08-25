package Controller;

import Model.LogicSimulator;
import View.SimulatorView;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Scanner;
import java.util.Vector;

public class SimulatorController {

    private SimulatorView view;
    private LogicSimulator model;

    public SimulatorController()
    {
        view = new SimulatorView();
        model = new LogicSimulator();
    }


    public void processCommand(){
        Scanner sc = new Scanner(System.in);
        String command = "0";
        while(!command.equals("4")){
            view.displayMenu();
            command = sc.nextLine();

            switch(command){
                case "1":
                    System.out.println(("Please key in a file path:"));
                    command = sc.nextLine();
                    if(this.model.load(command)) {
                        System.out.format(
                                "Circuit: %d input pins, %d output pins and %d gates"
                                ,this.model.getiPins().size()
                                ,this.model.getoPins().size()
                                ,this.model.getCircuits().size());
                    } else {
                        System.out.println("File not found or file format error!!");
                    }
                    break;

                case "2":
                    Vector<Boolean> inputValues = new Vector<>();
                    if(model.getSimulatorExist()){
                        for (int i=0; i<this.model.getiPins().size(); i++) {
                            System.out.format("Please key in the value of input pin "+ i +":");
                            command = sc.nextLine();
                            while (!command.equals("0") && !command.equals("1")) {
                                System.out.println("The value of input pin must be 0/1");
                                System.out.format("Please key in the value of input pin "+ i +":");
                                command = sc.nextLine();
                            }
                            inputValues.add(command.equals("1"));
                        }
                        System.out.println(model.getSimulationResult(inputValues));
                    } else {
                        System.out.println("Please load an lcf file, before using this operation.");
                    }
                    break;

                case "3":
                    if(model.getSimulatorExist()){
                        System.out.println(model.getTruthTable());
                    } else {
                        System.out.println("Please load an lcf file, before using this operation.");
                    }
                    break;

                case "4":
                    System.out.println("Goodbye, thanks for using LS.");
                    break;

                default:
                    System.out.println("Please enter corrent command!!");
                    break;
            }

        }
    }
}
