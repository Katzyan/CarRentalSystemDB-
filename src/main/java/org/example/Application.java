package org.example;

import org.example.entities.Accounts;
import org.example.entities.Car;
import org.example.enums.GasType;
import org.example.enums.VehicleType;
import org.example.repositories.AccountsRepository;
import org.example.repositories.CarRepository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.Scanner;

import static org.example.entities.Car.carTableHeader;

public class Application {
    public static void appStart() {
        //root admin addition
//        Accounts root = new Accounts();
//        AccountsRepository accountsRepository = new AccountsRepository();
//        root.setUsername("root");
//        root.setPassword("rootPassword");
//        root.setAdmin(true);
//        accountsRepository.saveAccount(root);


        Scanner scannerInt = new Scanner(System.in);
        Scanner scannerString = new Scanner(System.in);
        while (true) {
            displayMainMenu();
            int mainMenuOption = scannerInt.nextInt();

            switch (mainMenuOption) {
                case 1:// Log In
                {
                    displayLogInMenu();

                    int adminMenuOption = scannerInt.nextInt();

                    switch (adminMenuOption) {
                        case 1: // admin log in
                        {
                            if (logInByUsername()) {
                                //admin is logged in
                                boolean menu = true;
                                while (menu) { // stays in loop until Back option
                                    displayLoggedInAdminMenu();
                                    int optionAdmin = scannerInt.nextInt();
                                    switch (optionAdmin) {
                                        case 1:
                                            addCarDB();
                                            break;
                                        case 2:
                                            deleteCarDB();
                                            break;
                                        case 3:
                                            updateCarDB();
                                            break;
                                        case 4:
                                            displayAllVehicleByCriteriaDB();
                                            break;
                                        case 5: // add another admin

                                            break;
                                        case 6: { // GO BACK
                                            menu = false;
                                            break;
                                        }
                                        case 0:
                                            System.exit(0);
                                    }
                                }
                            }
                        }

                        case 2:
                            break;
                        case 0:
                            System.exit(0);
                    }
                    break;
                }

                case 2: // starting user menu
                    System.out.println("User menu");
                    break;
                case 0:
                    System.exit(0);

            }
        }


    }


    // Menu Displays

    public static void displayMainMenu() {
        System.out.println("Rent a car App");
        System.out.println("-------------------");
        System.out.println("1. Log In");
        System.out.println("2. New account");
        System.out.println("\n0. Exit");
    }

    public static void displayLogInMenu() {
        System.out.println("Admin menu ");
        System.out.println("-------------------");
        System.out.println("1. Log In");
        System.out.println("2. Go back");
        System.out.println("\n0. Exit");
    }

    public static void displayLoggedInAdminMenu() {
        System.out.println("Admin menu ");
        System.out.println("-------------------");
        System.out.println("1. Add car");
        System.out.println("2. Delete car");
        System.out.println("3. Update car");
        System.out.println("4. Display all vehicles");
        System.out.println("5. Accounts");
        System.out.println("6. Go back");
        System.out.println("\n0. Exit");
    }

    public static void displayAdminAccountsMenu(){
        System.out.println("Admin Menu / Accounts");
        System.out.println("-------------------");
        System.out.println("1. View");
        System.out.println("2. Add");
        System.out.println("3. Delete");
        System.out.println("4. Switch Role");
        System.out.println("5. Back");
        System.out.println("\n0. Exit ");
    }


    // Log Ins
    public static boolean logInByUsername() {
        Scanner scannerString = new Scanner(System.in);
        System.out.println("Username:");
        String username = scannerString.next();

        AccountsRepository accountsRepository = new AccountsRepository();
        Accounts account = accountsRepository.getAccountByUsername(username);

        if (account == null) {
            System.out.println("username doesn`t exist");
            return false;
        }

        System.out.println("password:");
        String adminPassword = scannerString.next();
        if (adminPassword.equals(account.getPassword())) {
            System.out.println("Log in successfully");
            return true;
        }
        System.out.println("Wrong password");
        return false;
    }

    // Car operations

    public static void addCarDB() {
        Scanner scannerInt = new Scanner(System.in);
        Scanner scannerString = new Scanner(System.in);
        Car car = new Car();
        System.out.println("Enter the car`s details as follows: ");
        while (car.getLicensePlate() == null || car.getLicensePlate().isBlank()) {
            System.out.println("License Plate:");
            String licensePlate = scannerString.nextLine();
            car.setLicensePlate(licensePlate);
        }
        while (car.getVin() == null || car.getVin().isBlank()) {
            System.out.println("VIN:");
            String vin = scannerString.nextLine();
            car.setVin(vin);
        }
        while (car.getVehicleType() == null) {
            System.out.println("What type of vehicle is this: ");
            System.out.println("1. Micro Car\n2. Hatchback\n3. Sedan\n4. Combi\n" +
                    "5. Pickup Truck\n6. Van\n7. SUV\n8. Sports Car\n9. Luxury Vehicle ");
            int carType = scannerInt.nextInt();
            switch (carType) {
                case 1:
                    car.setVehicleType(VehicleType.MICRO);
                    car.setPricePerDay(30);
                    break;
                case 2:
                    car.setVehicleType(VehicleType.HATCHBACK);
                    car.setPricePerDay(50);
                    break;
                case 3:
                    car.setVehicleType(VehicleType.SEDAN);
                    car.setPricePerDay(70);
                    break;
                case 4:
                    car.setVehicleType(VehicleType.COMBI);
                    car.setPricePerDay(80);
                    break;
                case 5:
                    car.setVehicleType(VehicleType.PICKUP);
                    car.setPricePerDay(90);
                    break;
                case 6:
                    car.setVehicleType(VehicleType.VAN);
                    car.setPricePerDay(100);
                    break;
                case 7:
                    car.setVehicleType(VehicleType.SUV);
                    car.setPricePerDay(110);
                    break;
                case 8:
                    car.setVehicleType(VehicleType.SPORT);
                    car.setPricePerDay(150);
                    break;
                case 9:
                    car.setVehicleType(VehicleType.LUXURY);
                    car.setPricePerDay(250);
                    break;
                default:
                    System.out.println("Please enter a valid option");
            }
        }
        while (car.getMake() == null || car.getMake().isBlank()) {
            System.out.println("Make:");
            String make = scannerString.nextLine();
            car.setMake(make);
        }
        while (car.getModel() == null || car.getModel().isBlank()) {
            System.out.println("Model:");
            String model = scannerString.nextLine();
            car.setModel(model);
        }
        while (car.getYear() == null || car.getYear().getYear() <= 1900) {
            System.out.println("Manufacturing Year: ");
            int year = scannerInt.nextInt();
            car.setYear(LocalDate.of(year, 1, 1));
        }
        //adding 10% to the price since the car is after 2018
        if (car.getYear().isAfter(LocalDate.of(2018, 1, 1))) {
            car.setPricePerDay(car.getPricePerDay() + car.getPricePerDay() / 10);
        }

        System.out.println("Is the transmision 1. Automatic  2. Manual");
        int transmision = scannerInt.nextInt();
        switch (transmision) {
            case 1:
                car.setAutomatic(true);
                // adding another 10% to the price since the car is automatic
                car.setPricePerDay(car.getPricePerDay() + car.getPricePerDay() / 10);
                break;
            case 2:
                car.setAutomatic(false);
                break;
            default:
                System.out.println("Car will default to manual");
        }
        while (car.getGasType() == null) {
            System.out.println("What type of fuel does the car use: \n1.Diesel\n2. Gas\n3. Hybrid\n4. Hydrogen\n5. Electric");
            int carFuel = scannerInt.nextInt();
            switch (carFuel) {
                case 1:
                    car.setGasType(GasType.DIESEL);
                    break;
                case 2:
                    car.setGasType(GasType.GAS);
                    break;
                case 3:
                    car.setGasType(GasType.HYBRID);
                    break;
                case 4:
                    car.setGasType(GasType.HYDROGEN);
                    break;
                case 5:
                    car.setGasType(GasType.ELECTRIC);
                    break;
                default:
                    System.out.println("Please enter a valid option");
            }
        }
        CarRepository carRepository = new CarRepository();
        try {
            carRepository.saveCar(car);
        } catch (Exception e) {
            System.out.println("Unable to add vehicle. Please check all of the entered data. ");
        }

    }

    public static void updateCarDB() {
        Scanner scannerInt = new Scanner(System.in);
        Scanner scannerString = new Scanner(System.in);
        System.out.println("Please choose how would you like to find the car: \n 1. License Plate\n 2. VIN");
        int searchBy = scannerInt.nextInt();
        Car car = null;
        CarRepository carRepository = new CarRepository();
        String message = null;
        switch (searchBy) {
            case 1: {
                System.out.println("Please enter the License Plate (e.g. IS01SDA)");
                String licensePlate = scannerString.next();
                car = carRepository.getCarByLicensePlate(licensePlate);
                message = "License plate not in DataBase";
                break;
            }
            case 2: {
                System.out.println("Please enter the VIN");
                String vin = scannerString.next();
                car = carRepository.getCarByVIN(vin);
                message = "VIN not in DataBase";
                break;
            }
        }
        if (car != null) {
            chooseCarUpdate(car);
            carRepository.updateCar(car);
            System.out.println("Car successfully updated");
        } else {
            System.out.println(message);
        }
    }

    public static void chooseCarUpdate(Car car) {
        System.out.println("1 vehicle found: ");
        car.vehicleDisplay();
        boolean stillUpdating = true;
        do {
            System.out.println("What would you like to update: ");
            System.out.println("1. License Plate\n2. VIN\n3. Make\n4. Model\n5. Manufacturing Year" +
                    "\n6. Vehicle Type\n7.Transmission\n8. Fuel Type");
            Scanner scannerInt = new Scanner(System.in);
            Scanner scannerString = new Scanner(System.in);
            int carUpdateOption = scannerInt.nextInt();

            switch (carUpdateOption) {
                case 1: {
                    do {
                        System.out.println("Please enter the new license plate:");
                        String newLicensePlate = scannerString.next();
                        car.setLicensePlate(newLicensePlate);
                    } while (car.getLicensePlate().isBlank());
                    break;
                }
                case 2: {
                    do {
                        System.out.println("Pleae enter the new VIN:");
                        String newVin = scannerString.next();
                        car.setVin(newVin);
                    } while (car.getVin().isBlank());
                    break;
                }
                case 3: {
                    do {
                        System.out.println("Please enter the new Make of the vehicle:");
                        String newMake = scannerString.next();
                        car.setMake(newMake);
                    } while (car.getMake().isBlank());
                    break;
                }
                case 4: {
                    do {
                        System.out.println("Please enter the new Model of the vehicle");
                        String newModel = scannerString.next();
                        car.setModel(newModel);
                    } while (car.getModel().isBlank());
                    break;
                }
                case 5: {
                    do {
                        System.out.println("Please enter the new manufacturing Year");
                        int newYear = scannerInt.nextInt();
                        car.setYear(LocalDate.of(newYear, 1, 1));
                    } while (car.getYear().getYear() <= 1900);
                    break;
                }
                case 6: {
                    System.out.println("What is the new vehicle type: ");
                    System.out.println("1. Micro Car\n2. Hatchback\n3. Sedan\n4. Combi\n" +
                            "5. Pickup Truck\n6. Van\n7. SUV\n8. Sports Car\n9. Luxury Vehicle ");
                    int newVehicleType = 0;
                    while (newVehicleType <= 0 || newVehicleType >= 10) {
                        newVehicleType = scannerInt.nextInt();
                    }
                    switch (newVehicleType) {
                        case 1:
                            car.setVehicleType(VehicleType.MICRO);
                            break;
                        case 2:
                            car.setVehicleType(VehicleType.HATCHBACK);
                            break;
                        case 3:
                            car.setVehicleType(VehicleType.SEDAN);
                            break;
                        case 4:
                            car.setVehicleType(VehicleType.COMBI);
                            break;
                        case 5:
                            car.setVehicleType(VehicleType.PICKUP);
                            break;
                        case 6:
                            car.setVehicleType(VehicleType.VAN);
                            break;
                        case 7:
                            car.setVehicleType(VehicleType.SUV);
                            break;
                        case 8:
                            car.setVehicleType(VehicleType.SPORT);
                            break;
                        case 9:
                            car.setVehicleType(VehicleType.LUXURY);
                            break;
                    }
                    break;
                }
                case 7: {
                    if (car.isAutomatic()) {
                        System.out.println("Changing transmission from Automatic to Manual");
                        car.setAutomatic(false);
                        break;
                    }
                    System.out.println("Changing transmission from Manual to Automatic");
                    car.setAutomatic(true);
                    break;
                }
                case 8: {
                    System.out.println("What is the vehicle`s new Fuel Type:\n1.Diesel\n2. Gas\n" +
                            "3. Hybrid\n4. Hydrogen\n5. Electric");
                    int newFuelType = 0;
                    while (newFuelType <= 0 || newFuelType > 5) {
                        newFuelType = scannerInt.nextInt();
                    }
                    switch (newFuelType) {
                        case 1:
                            car.setGasType(GasType.DIESEL);
                            break;
                        case 2:
                            car.setGasType(GasType.GAS);
                            break;
                        case 3:
                            car.setGasType(GasType.HYBRID);
                            break;
                        case 4:
                            car.setGasType(GasType.HYDROGEN);
                            break;
                        case 5:
                            car.setGasType(GasType.ELECTRIC);
                            break;
                    }
                    break;
                }
            }
            System.out.println("Would you like to update anything else?");
            System.out.println("1. Yes           2. No ");
            int moreUpdates = 0;
            do {
                moreUpdates = scannerInt.nextInt();
            } while (moreUpdates <= 0 || moreUpdates > 2);

            if (moreUpdates == 2) {
                stillUpdating = false;
            }
        } while (stillUpdating);

        switch (car.getVehicleType()) {
            case MICRO:
                car.setPricePerDay(30);
                break;
            case HATCHBACK:
                car.setPricePerDay(50);
                break;
            case SEDAN:
                car.setPricePerDay(70);
                break;
            case COMBI:
                car.setPricePerDay(80);
                break;
            case PICKUP:
                car.setPricePerDay(90);
                break;
            case VAN:
                car.setPricePerDay(100);
                break;
            case SUV:
                car.setPricePerDay(110);
                break;
            case SPORT:
                car.setPricePerDay(150);
                break;
            case LUXURY:
                car.setPricePerDay(250);
                break;
        }
        if (car.isAutomatic()) {
            car.setPricePerDay(car.getPricePerDay() + car.getPricePerDay() / 10);
        }
        if (car.getYear().isAfter(LocalDate.of(2018, 1, 1))) {
            car.setPricePerDay(car.getPricePerDay() + car.getPricePerDay() / 10);
        }
    }

    public static void deleteCarDB() {
        Scanner scannerInt = new Scanner(System.in);
        Scanner scannerString = new Scanner(System.in);
        System.out.println("Please choose how would you like to find the car: \n 1. License Plate\n 2. VIN");
        int searchBy = scannerInt.nextInt();
        Car car = null;
        CarRepository carRepository = new CarRepository();
        String message = null;
        switch (searchBy) {
            case 1: {
                System.out.println("Please enter the License Plate (e.g. IS01SDA)");
                String licensePlate = scannerString.next();
                car = carRepository.getCarByLicensePlate(licensePlate);
                message = "License plate not in DataBase";
                break;
            }
            case 2: {
                System.out.println("Please enter the VIN");
                String vin = scannerString.next();
                car = carRepository.getCarByVIN(vin);
                message = "VIN not in DataBase";
                break;
            }
        }
        if (car != null) {
            carRepository.deleteCar(car);
            System.out.println("Car successfully deleted");
        } else {
            System.out.println(message);
        }
    }

    public static void displayAllVehicleByCriteriaDB() {
        Scanner scannerInt = new Scanner(System.in);
        Scanner scannerString = new Scanner(System.in);
        CarRepository carRepository = new CarRepository();
        System.out.println("Would you like to apply a filter?\n1. Yes    2. No");
        int filter = 0;
        while (filter <= 0 || filter > 2) {
            filter = scannerInt.nextInt();
        }
        if (filter == 2) {
            // display all vehicles without filter
            carTableHeader();
            carRepository.getAllCars().stream().forEach(car -> car.vehicleDisplayAll());
        } else {
            System.out.println("What would you like to filter by: ");
            System.out.println("1. Vehicle Type    2. Transmission    3. Fuel Type  ");
            int filterType = 0;
            while (filterType <= 0 || filterType > 4) {
                filterType = scannerInt.nextInt();
            }
            switch (filterType){
                case 1:
                    displayByVehicleType();
                    break;
                case 2:
                    displayByVehicleTransmission();
                    break;
                case 3:
                    displayByVehicleFuelType();
                    break;
            }
        }
    }

    public static void displayByVehicleType(){
        Scanner scannerInt = new Scanner(System.in);
        CarRepository carRepository = new CarRepository();
        System.out.println("Choose the vehicle type you would like to see:");
        System.out.println("1. Micro Car\n2. Hatchback\n3. Sedan\n4. Combi\n" +
                "5. Pickup Truck\n6. Van\n7. SUV\n8. Sports Car\n9. Luxury Vehicle ");
        int vehicleType = 0;
        while (vehicleType <= 0 || vehicleType > 10) {
            vehicleType = scannerInt.nextInt();
        }
        switch (vehicleType){
            case 1:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.MICRO).stream().forEach(car ->car.vehicleDisplayAll());
                break;
            case 2:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.HATCHBACK).stream().forEach(car ->car.vehicleDisplayAll());
                break;
            case 3:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.SEDAN).stream().forEach(car ->car.vehicleDisplayAll());
                break;
            case 4:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.COMBI).stream().forEach(car ->car.vehicleDisplayAll());
                break;
            case 5:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.PICKUP).stream().forEach(car ->car.vehicleDisplayAll());
                break;
            case 6:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.VAN).stream().forEach(car ->car.vehicleDisplayAll());
                break;
            case 7:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.SUV).stream().forEach(car ->car.vehicleDisplayAll());
                break;
            case 8:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.SPORT).stream().forEach(car ->car.vehicleDisplayAll());
                break;
            case 9:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.LUXURY).stream().forEach(car ->car.vehicleDisplayAll());
                break;
        }
    }

    public static void displayByVehicleTransmission(){
        Scanner scannerInt = new Scanner(System.in);
        CarRepository carRepository = new CarRepository();
        System.out.println("Choose the transmission type you would like to see:");
        System.out.println("1. Automatic        2. Manual");
        int vehicleTransmission = 0;
        while (vehicleTransmission <= 0 || vehicleTransmission > 3) {
            vehicleTransmission = scannerInt.nextInt();
        }
        switch (vehicleTransmission){
            case 1:
                carTableHeader();
                carRepository.getCarsByTransmission(true).stream().forEach(car ->car.vehicleDisplayAll());
                break;
            case 2:
                carTableHeader();
                carRepository.getCarsByTransmission(false).stream().forEach(car ->car.vehicleDisplayAll());
                break;
        }
    }

    public static void displayByVehicleFuelType(){
        Scanner scannerInt = new Scanner(System.in);
        CarRepository carRepository = new CarRepository();
        System.out.println("Choose the fuel type you would like to see:");
        System.out.println("1. Diesel\n2. Gas\n3. Hybrid\n4. Hydrogen\n5. Electric ");
        int fuelType = 0;
        while (fuelType <= 0 || fuelType > 6) {
            fuelType = scannerInt.nextInt();
        }
        switch (fuelType){
            case 1:
                carTableHeader();
                carRepository.getCarsByFuelType(GasType.DIESEL).stream().forEach(car ->car.vehicleDisplayAll());
                break;
            case 2:
                carTableHeader();
                carRepository.getCarsByFuelType(GasType.GAS).stream().forEach(car ->car.vehicleDisplayAll());
                break;
            case 3:
                carTableHeader();
                carRepository.getCarsByFuelType(GasType.HYBRID).stream().forEach(car ->car.vehicleDisplayAll());
                break;
            case 4:
                carTableHeader();
                carRepository.getCarsByFuelType(GasType.HYDROGEN).stream().forEach(car ->car.vehicleDisplayAll());
                break;
            case 5:
                carTableHeader();
                carRepository.getCarsByFuelType(GasType.ELECTRIC).stream().forEach(car ->car.vehicleDisplayAll());
                break;
        }
    }

    // Account Operations



}


