import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 COPYRIGHT (C) 2021 Indunil Aravinda(w1837776 / 20200839). All Rights Reserved.
 Class Version program for a COVID-19 VACCINATION CENTER
 Solves CW1 Task #2
 @author Indunil Aravinda
 @version 1.00 2021-07-26
 **/

public class TaskTwo {

    public static void main(String[] args) {

        ArrayList <String> allPatients= new ArrayList();
        int vaccines=150;
        int bnum=0;

        Scanner input = new Scanner(System.in);
        VaccinationCenter vaccenter=new VaccinationCenter();
        Booth[] allBooths = new Booth[6];
        for (int i = 0; i < allBooths.length; i++) {
            allBooths[i]=new Booth();
            allBooths[i].customerName="none";
        }

        while (true){
            System.out.println("\nChoose what you want to do? \n");

//        Console Menu
            System.out.println("100 or VVB:View all Vaccination Booths");
            System.out.println("101 or VEB: View all Empty Booths");
            System.out.println("102 or APB: Add Patient to a Booth");
            System.out.println("103 or RPB: Remove Patient from a Booth");
            System.out.println("104 or VPS: View Patients Sorted in alphabetical order");
            System.out.println("105 or SPD: Store Program Data into file");
            System.out.println("106 or LPD: Load Program Data from file");
            System.out.println("107 or VRV: View Remaining Vaccinations");
            System.out.println("108 or AVS: Add Vaccinations to the Stock");
            System.out.println("999 or EXT: Exit the Program");

            String response = input.nextLine();

//        MENU actions

            if (response.equals("100")||response.equals("VVB")){
                for (int i = 0; i < allBooths.length; i++) {
                    if (allBooths[i].customerName.equals("")){
                        System.out.println("Booth number "+(i+1)+" is not occupied");
                    }else{
                        System.out.println("Booth number"+(i+1)+" is occupied by "+allBooths[i].customerName);
                    }
                }

            }else if(response.equals("101")||response.equals("VEB")){
                for (int i = 0; i < allBooths.length; i++) {
                    if (allBooths[i].customerName.equals("none")){
                        System.out.println("Booth number "+(i+1)+" is empty");
                    }
                }

            }else if (response.equals("102")||response.equals("APB")){
                System.out.println("Booth number? Booth number [0-5] -> 0 is 1");
                bnum= Integer.parseInt(input.nextLine());

                System.out.println("-----------------------------------");
                System.out.println("Booth number "+bnum+" MENU\n");
                System.out.println("Enter patient's Name : ");
                String name=input.nextLine();
                vaccines=allBooths[bnum].addPatientBooth(vaccines,name,allPatients);

            }else if(response.equals("103")||response.equals("RPB")){
                System.out.println("Booth number? [0-5] ");
                bnum= Integer.parseInt(input.nextLine());
                vaccenter.remPatientBooth(bnum,allBooths);

            }else if (response.equals("104")||response.equals("VPS")){
                String[] sortedPatients = vaccenter.viewPatientsSorted(allPatients);
                for (int i = 0; i < sortedPatients.length; i++) {
                    System.out.println(sortedPatients[i]);
                }

            }else if (response.equals("105")||response.equals("SPD")){
                storeProgramData(vaccines,allPatients);

            }else if(response.equals("106")||response.equals("LPD")){
                loadProgramData();

            }else if(response.equals("107")||response.equals("VRV")){
                vaccenter.viewVaccines(vaccines);

            }else if(response.equals("108")||response.equals("AVS")){
                System.out.println("How many vaccine did we receive? ");
                int newVac=input.nextInt();
                vaccines=vaccenter.addVaccines(vaccines,newVac);

            } else if (response.equals("999")||response.equals("EXT")){
                exitProgram();

            } else{
                System.out.println("Please choose a option from the menu and Try Again");
            }
        }

//        Menu ations END
    }

    /**
     To exit the program
     */

    public static void exitProgram(){
        System.exit(0);
    }

    /**
     To store patients list and vaccines in a file
     @param vaccines Vaccines variable which has Number of Vaccines we already have
     @param ServiceCenter Booths array of 6 booths
     */

    public static void storeProgramData(int vaccines, ArrayList ServiceCenter ) {
        try {
            File storeData = new File("TaskOneData.txt");
            String currentData = "Remaining Vaccines : "+vaccines+"\n";

            for (int i = 0; i < ServiceCenter.size(); i++) {
                currentData=currentData+"Vaccinated person "+(i+1)+" is "+ ServiceCenter.get(i) +"\n";
            }

            if (storeData.createNewFile()) {
                System.out.println("File Created");
                FileWriter dataFile = new FileWriter("TaskOneData.txt");
                dataFile.write(currentData);
                dataFile.close();
            }else{
                FileWriter dataFile = new FileWriter("TaskOneData.txt");
                dataFile.write(currentData);
                dataFile.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     To print data stored in 'TaskOneData.txt' file
     */

    public static void loadProgramData(){
        try{
            File loadFile = new File("TaskOneData.txt");
            Scanner readFile = new Scanner(loadFile);
            while (readFile.hasNext()){
                System.out.println(readFile.nextLine());
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

//    My classes

//    ----------------------------Vaccine Center -----------------------------------

    static class VaccinationCenter{

//        custom methods

        /**
         To view all vaccination booths [ Not used ]
         @param boothArray Array collection of booths
         @return print whether each booth is empty or occupied by anyone
         */
        public static void viewAllBooths(String[]boothArray){
            for (int x = 0; x < 6; x++) {
                //                    To stop printing empty seats as occupied, if is placed
                if (boothArray[x]=="e"){
                    System.out.println("booth " + x + " is empty.");
                }else{
                    System.out.println("booth " + x + " occupied by " + boothArray[x]);
                }
            }
        }

        /**
         To view all empty booths [Not used]
         @param boothArray Array collection of booths
         @return print all empty booth
         */
        public static void viewEmptyBooths(String[]boothArray){
            for (int x = 0; x < 6; x++) {
                if (boothArray[x]=="e"){
                    System.out.println("booth " + x + " is empty.");
                }
            }
        }

        /**
         To view remaining vaccines
         @param vaccine variable which represents the number of vaccines
         @return nothing
         */
        public static void viewVaccines(int vaccine){
            System.out.println(vaccine);
        }

        /**
         To update newly added vaccines
         @param remVaccines Vaccines variable which has Number of Vaccines we already have
         @param newVaccines New number which has the number of vaccines newly added
         @return updated remaing vaccines
         */
        public static int addVaccines(int remVaccines, int newVaccines){
            remVaccines = remVaccines+newVaccines;
            return remVaccines;
        }

        /**
         To view patients list sorted in alphabetical order
         @param patients Array collection of patient names
         @return sorted string array of patient namesd
         */
        public static String[] viewPatientsSorted(ArrayList patients){
            String patientNow;
            String[] patientList=new String[patients.size()];

            for (int i = 0; i < patients.size(); i++) {
                patientList[i]=" ";
                patientList[i]=(String) patients.get(i);
            }

            for(int i = 0; i < patientList.length - 1; i++) {
                for(int j = (i+ 1); j < patientList.length; j++)            {
                    if(patientList[i].compareTo(patientList[j]) > 0)                {
                        patientNow = patientList[i];
                        patientList[i] = patientList[j];
                        patientList[j] = patientNow;
                    }
                }
            }
            return patientList;
        }
        /**
         To remove a patient from the booth
         @param booth booth number
         @param allBooths Array collection of booths
         @return nothing
         */
        public void remPatientBooth(int booth, Booth[] allBooths) {
            allBooths[booth].customerName="none";
        }
    }
//-----------------------   BOOOTH --------------------------------------------------

    static class Booth{

        String customerName="none";

//        custom methods

        /**
         To add new patients to a booth
         @param vaccine integer number of remaining vaccines
         @param name Patient Name
         @param allPatients Array collection of all Patient names
         @return updated vaccines number
         */
        public int  addPatientBooth(int vaccine, String name, ArrayList allPatients){
            this.customerName=name;
            if (allPatients.size()==0){
                allPatients.add(0,name);
            }else {
                allPatients.add(allPatients.size() - 1, name);
            }
            vaccine=vaccine-1;
            return vaccine;
        }
    }
}
