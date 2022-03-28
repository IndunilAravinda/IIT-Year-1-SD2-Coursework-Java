import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
   COPYRIGHT (C) 2021 Indunil Aravinda(w1837776 / 20200839). All Rights Reserved.
   Array Version program from the template given for a COVID-19 VACCINATION CENTER
   Solves CW1 Task #1
   @author Indunil Aravinda
   @version 1.00 2021-07-26
 **/

public class ServiceCenterExample {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String customerName;
        ArrayList <String> allPatients= new ArrayList();
        int vaccines=150;
        int boothNum = 0;
        String response;
        String[] ServiceCenter = new String[6];

//        initialising the booths with empty seats
        for (int x=0; x<6; x++){
            ServiceCenter[x]="e";
        }

//        Loop start
        boolean status=true;

        while (status){

//        Start vaccinating
            System.out.println("--------------------------------------------------");
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

            response=input.nextLine(); // taking user response

//            Actions with respective user choice

            if (response.equals("100")||response.equals("VVB")){
                viewAllBooths(ServiceCenter); //view all vaccination booths

            }else if(response.equals("101")||response.equals("VEB")){
                viewEmptyBooths(ServiceCenter); // view all empty booths

            }else if (response.equals("102")||response.equals("APB")){
                System.out.println("Which booth is assigned to this patient? "); // take user inputs and, add a patient to a booth
                boothNum=Integer.parseInt(input.nextLine());
                System.out.println("What is the patient's name? ");
                customerName=input.nextLine();
                vaccines=addPatientBooth(vaccines,boothNum,customerName,ServiceCenter,allPatients);

            }else if(response.equals("103")||response.equals("RPB")){
                System.out.println("Which booth's patient is done? "); // take user inputs and, remove the relevant patient from booth
                boothNum=Integer.parseInt(input.nextLine());
                remPatientBooth(boothNum,ServiceCenter);

            }else if (response.equals("104")||response.equals("VPS")){
                String[] sortedPatients = viewPatientsSorted(allPatients); // return sorted name array of the patients, and then print it
                for (Object name: sortedPatients) {
                    System.out.println(String.valueOf(name));
                }

            }else if (response.equals("105")||response.equals("SPD")){
                storeProgramData(vaccines,allPatients); // Store the patient names and vaccines left in a file named TaskOneData.txt

            }else if(response.equals("106")||response.equals("LPD")){
                loadProgramData(); // prints the data in the TaskOneData.txt File

            }else if(response.equals("107")||response.equals("VRV")){
                viewVaccines(vaccines); //remaining vaccines

            }else if(response.equals("108")||response.equals("AVS")){
                System.out.println("How many vaccines did we receive? "); // user inputs new vaccines amount, and then it is added
                int newVac= input.nextInt();
                vaccines=addVaccines(vaccines,newVac);

            } else if (response.equals("999")||response.equals("EXT")){
                status=false;
                exitProgram();
            } else{
                System.out.println("Please choose a option from the menu. Try Again \n");
            }

        }

//        Main method end
    }

//    My methods

/**
    To view all vaccination booths
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
     To view all empty booths
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
     To vadd new patients to a booth
     @param vaccine integer number of remaining vaccines
     @param booth booth number which the patient is in
     @param name Patient Name
     @param allPatients Array collection of all Patient names
     @return updated vaccines number
     */

    public static int  addPatientBooth(int vaccine,int booth, String name, String[] boothArray, ArrayList allPatients){
        boothArray[booth] = name;
        if (allPatients.size()==0){
            allPatients.add(0,name);
        }else {
            allPatients.add(allPatients.size() - 1, name);
        }
        vaccine=vaccine-1;
        return vaccine;
    }

    /**
     To remove a patient from the booth, remove the patient by renaming the existing booth position to 'e' which represents empty
     @param boothArray Array collection of booths
     @param booth Booth number
     @return nothing
     */

    public static void   remPatientBooth(int booth,String[] boothArray){
        boothArray[booth] = "e";
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
}

//        100 or VVB: View all Vaccination Booths = Done
//        101 or VEB: View all Empty Booths = Done
//        102 or APB: Add Patient to a Booth = Done
//        103 or RPB: Remove Patient from a Booth = Done
//        104 or VPS: View Patients Sorted in alphabetical order (Do not use library sort routine) = done
//        105 or SPD: Store Program Data into file = Done
//        106 or LPD: Load Program Data from file = Done
//        107 or VRV: View Remaining Vaccinations = done
//        108 or AVS: Add Vaccinations to the Stock = done
//        999 or EXT: Exit the Program = done