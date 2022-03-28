import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 COPYRIGHT (C) 2021 Indunil Aravinda(w1837776 / 20200839). All Rights Reserved.
 Extended Version program for a COVID-19 VACCINATION CENTER
 Solves CW1 Task #3
 @author Indunil Aravinda
 @version 1.00 2021-07-26
 **/

public class TaskThree {

    public static void main(String[] args) {

        ArrayList<String> allPatients = new ArrayList();
        int vaccines = 150;
        int bnum = 0;

        Scanner input = new Scanner(System.in);
        VaccinationCenter vaccenter = new VaccinationCenter();
        Booth[] allBooths = new Booth[6];
        for (int i = 0; i < allBooths.length; i++) {
            allBooths[i] = new Booth();
            allBooths[i].pat = null;
        }

        while (true) {
            //        task 3 Start

//        Console Menu
            System.out.println("Choose an option?\n");
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

            if (response.equals("100") || response.equals("VVB")) {
                for (int i = 0; i < allBooths.length; i++) {
                    if (allBooths[i].pat == null) {
                        System.out.println("Booth number " + (i + 1) + " is not occupied");
                    } else {
                        System.out.println("Booth number" + (i + 1) + " is occupied by " + allBooths[i].pat.FirstName);
                    }
                }

            } else if (response.equals("101") || response.equals("VEB")) {
                for (int i = 0; i < allBooths.length; i++) {
                    if (allBooths[i].pat == null) {
                        System.out.println("Booth number " + (i + 1) + " is empty");
                    }
                }

            } else if (response.equals("102") || response.equals("APB")) {
                String fName, sName, city, nic;
                int age;

                System.out.println("Please enter the patient details\n\n");
                System.out.println("First Name : ");
                fName = input.nextLine();
                System.out.println("Surname : ");
                sName = input.nextLine();
                System.out.println("City live in : ");
                city = input.nextLine();
                System.out.println("NIC / Passport : ");
                nic = input.nextLine();
                System.out.println("Age : ");
                age = Integer.parseInt(input.nextLine());
                System.out.println("Vaccine requested : (AstraZeneca - A,Sinopharm - S,Pfizer - P) ");
                String req = input.nextLine();

//            Choosing the booth

                if (req.equals("A")) {
                    System.out.println("Please choose the booth : 1 or 2");
                    String tmp = input.nextLine();

                    if (tmp.equals("1")) {
                        bnum = 0;
                    } else if (tmp.equals("2")) {
                        bnum = 1;
                    } else {
                        System.out.println("Wrong Input");
                        continue;
                    }
                } else if (req.equals("S")) {
                    System.out.println("Please choose the booth : 3 or 4");
                    String tmp = input.nextLine();

                    if (tmp.equals("3")) {
                        bnum = 2;
                    } else if (tmp.equals("4")) {
                        bnum = 3;
                    } else {
                        System.out.println("Wrong Input");
                        continue;
                    }

                } else if (req.equals("P")) {
                    System.out.println("Please choose the booth : 5 or 6");
                    String tmp = input.nextLine();

                    if (tmp.equals("5")) {
                        bnum = 4;
                    } else if (tmp.equals("6")) {
                        bnum = 5;
                    } else {
                        System.out.println("Wrong Input");
                        continue;
                    }

                } else {
                    System.out.println("Wrong input");
                    continue;
                }
                vaccines = allBooths[bnum].addPatientBooth(vaccines, fName, sName, age, city, nic, req, allPatients);

            } else if (response.equals("103") || response.equals("RPB")) {
                System.out.println("Which booth's patient is done? Booth number [0-5] -> 0 is 1"); // take user inputs and, remove the relevant patient from booth
                int boothNum = Integer.parseInt(input.nextLine());
                allBooths[boothNum].remPatientBooth();
                allBooths[bnum].remPatientBooth();

            } else if (response.equals("104") || response.equals("VPS")) {
                String[] sortedPatients = vaccenter.viewPatientsSorted(allPatients);
                for (int i = 0; i < sortedPatients.length; i++) {
                    System.out.println(sortedPatients[i]);
                }

            } else if (response.equals("105") || response.equals("SPD")) {
                storeProgramData(vaccines, allPatients);

            } else if (response.equals("106") || response.equals("LPD")) {
                loadProgramData();

            } else if (response.equals("107") || response.equals("VRV")) {
                vaccenter.viewVaccines(vaccines);

            } else if (response.equals("108") || response.equals("AVS")) {
                System.out.println("How many vaccine did we receive? ");
                int newVac = Integer.parseInt(input.nextLine());
                vaccines = vaccenter.addVaccines(vaccines, newVac);

            } else if (response.equals("999") || response.equals("EXT")) {
                exitProgram();

            } else {
                System.out.println("Please choose a option from the menu and Try Again");
            }
        }

//        Menu ations END
    }

    /**
     To exit the program
     */
    public static void exitProgram() {
        System.exit(0);
    }

    /**
     To store patients list and vaccines in a file
     @param vaccines Vaccines variable which has Number of Vaccines we already have
     @param ServiceCenter Booths array of 6 booths
     */
    public static void storeProgramData(int vaccines, ArrayList ServiceCenter) {
        try {
            File storeData = new File("TaskOneData.txt");
            String currentData = "Remaining Vaccines : " + String.valueOf(vaccines) + "\n";

            for (int i = 0; i < ServiceCenter.size(); i++) {
                currentData = currentData + "Vaccinated person " + (i + 1) + " is " + ServiceCenter.get(i) + "\n";
            }

            if (storeData.createNewFile()) {
                System.out.println("File Created");
                FileWriter dataFile = new FileWriter("TaskOneData.txt");
                dataFile.write(currentData);
                dataFile.close();
            } else {
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
    public static void loadProgramData() {
        try {
            File loadFile = new File("TaskOneData.txt");
            Scanner readFile = new Scanner(loadFile);
            while (readFile.hasNext()) {
                System.out.println(readFile.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

//    My Classes

//    ------------ Vaccine Center --------------------------

    static class VaccinationCenter {

        //        custom methods

        /**
         To view all vaccination booths [ Not used ]
         @param boothArray Array collection of booths
         @return print whether each booth is empty or occupied by anyone
         */
        public static void viewAllBooths(String[] boothArray) {
            for (int x = 0; x < 6; x++) {
                //                    To stop printing empty seats as occupied, if is placed
                if (boothArray[x] == "e") {
                    System.out.println("booth " + x + " is empty.");
                    ;
                } else {
                    System.out.println("booth " + x + " occupied by " + boothArray[x]);
                }
            }
        }

        /**
         To view all empty booths [Not used]
         @param boothArray Array collection of booths
         @return print all empty booth
         */
        public static void viewEmptyBooths(String[] boothArray) {
            for (int x = 0; x < 6; x++) {
                if (boothArray[x] == "e") {
                    System.out.println("booth " + x + " is empty.");
                    ;
                }
            }
        }

        /**
         To view remaining vaccines
         @param vaccine variable which represents the number of vaccines
         @return nothing
         */
        public static void viewVaccines(int vaccine) {
            System.out.println(vaccine);
        }

        /**
         To update newly added vaccines
         @param remVaccines Vaccines variable which has Number of Vaccines we already have
         @param newVaccines New number which has the number of vaccines newly added
         @return updated remaing vaccines
         */
        public static int addVaccines(int remVaccines, int newVaccines) {
            remVaccines = remVaccines + newVaccines;
            return remVaccines;
        }

        /**
         To view patients list sorted in alphabetical order
         @param patients Array collection of patient names
         @return sorted string array of patient namesd
         */
        public static String[] viewPatientsSorted(ArrayList patients) {
            String patientNow;
            String[] patientList = new String[patients.size()];

            for (int i = 0; i < patients.size(); i++) {
                patientList[i] = " ";
                patientList[i] = (String) patients.get(i);
            }

            for (int i = 0; i < patientList.length - 1; i++) {
                for (int j = (i + 1); j < patientList.length; j++) {
                    if (patientList[i].compareTo(patientList[j]) > 0) {
                        patientNow = patientList[i];
                        patientList[i] = patientList[j];
                        patientList[j] = patientNow;
                    }
                }
            }
            return patientList;
        }
    }

//-----------------------   BOOOTH --------------------------------------------------

    static class Booth {

        Patient pat;
//        custom methods

        /**
         To add new patients to a booth
         @param vaccine integer number of remaining vaccines
         @param firstName Patient first Name
         @param surname Patient surname
         @param age Patient age
         @param city Patient city
         @param nic Patient nic
         @param VacReq Patient Vaccine Requested
         @param allPatients Array collection of all Patient names
         @return updated vaccines number
         */
        public int addPatientBooth(int vaccine, String firstName, String surname, int age, String city, String nic, String VacReq, ArrayList allPatients) {
//            this.customerName=name;
            pat = new Patient(firstName, surname, age, city, nic, VacReq);
            if (allPatients.size() == 0) {
                allPatients.add(0, firstName);
            } else {
                allPatients.add(allPatients.size() - 1, firstName);
            }
            vaccine = vaccine - 1;
            return vaccine;
        }

        /**
         To remove a patient from the booth
         @return nothing
         */
        public void remPatientBooth() {
            pat = null;
        }

//    ------------------------------------- Patient ------------------------------------
        static class Patient {
            String FirstName = "";
            String Surname = "";
            int Age = 0;
            String City = "";
            String NIC = "";
            String VacReq = "";

//            Constructor which initialise patient instance variables

            /**
             To create a patient object with initial values
             @param fName Patient first Name
             @param sName Patient surname
             @param age Patient age
             @param city Patient city
             @param nic Patient nic
             @param vaccineReq Patient Vaccine Requested
             */
            Patient(String fName, String sName, int age, String city, String nic, String vaccineReq) {
                FirstName = fName;
                Surname = sName;
                Age = age;
                City = city;
                NIC = nic;
                VacReq = vaccineReq;
            }
        }
    }
}