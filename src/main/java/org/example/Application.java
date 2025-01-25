package org.example;

import org.example.entities.*;
import org.example.enums.GasType;
import org.example.enums.VehicleType;
import org.example.repositories.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.entities.Accounts.accountsTableHeader;
import static org.example.entities.Car.carTableHeader;


public class Application {
    public static void appStart() {
        //root admin addition
//        Accounts root = new Accounts();
//        Driver driver = new Driver();
//        AccountsRepository accountsRepository = new AccountsRepository();
//        root.setUsername("root");
//        root.setPassword("rootPassword");
//        root.setAdmin(true);
//        driver.setName("Tudor");
//        driver.setLicenseNumber("TCM01");
//        root.setDriver(driver);
//        driver.setAccounts(root);
//        accountsRepository.saveAccount(root);


        Scanner scannerInt = new Scanner(System.in);
        while (true) {
            displayMainMenu();
            int mainMenuOption = scannerInt.nextInt();

            switch (mainMenuOption) {
                case 1: {
                    Accounts account = accountSelection();
                    if (account == null) {
                        System.out.println("Username does not exist");
                    } else {
                        if (logInByUsername(account)) {
                            if (account.isAdmin()) {
                                adminMenu();
                            } else {
                                userMenu(account);
                            }
                        }
                    }
                    break;
                }
                case 2:
                    addAccount(false);
                    break;
                case 0:
                    System.exit(0);
            }
        }
    }


    /**
     * Display Menu
     */
    public static void displayMainMenu() {
        System.out.println("Rent a car App");
        System.out.println("-------------------");
        System.out.println("1. Log In");
        System.out.println("2. New account");
        System.out.println("\n0. Exit");
    }

    public static void displayLoggedInAdminMenu() {
        System.out.println("Admin menu ");
        System.out.println("-------------------");
        System.out.println("1. Fleet");
        System.out.println("2. Fleet Maintenance - COMING SOON");
        System.out.println("3. Accounts");
        System.out.println("4. Reservations - COMING SOON");
        System.out.println("5. Go back");
        System.out.println("\n0. Exit");
    }

    public static void displayAdminAccountsMenu() {
        System.out.println("Admin Menu / Accounts");
        System.out.println("-------------------");
        System.out.println("1. View");
        System.out.println("2. Add");
        System.out.println("3. Delete");
        System.out.println("4. Switch Role");
        System.out.println("5. Change Password");
        System.out.println("6. Back");
        System.out.println("\n0. Exit ");
    }

    public static void displayAdminFleetMenu() {
        System.out.println("Admin Menu / Fleet");
        System.out.println("-------------------");
        System.out.println("1. Add car");
        System.out.println("2. Delete car");
        System.out.println("3. Update car");
        System.out.println("4. Display all vehicles");
        System.out.println("5. Go Back");
        System.out.println("\n0. Exit");
    }

    public static void displayLoggedInUserMenu() {
        System.out.println("Menu");
        System.out.println("-------------------");
        System.out.println("1. Vehicles");
        System.out.println("2. Reservations");
        System.out.println("3. Back");
        System.out.println("\n0. Exit");
    }

    public static void displayUserVehicleMenu() {
        System.out.println("Menu / Vehicles");
        System.out.println("-------------------");
        System.out.println("1. View all vehicles");
        System.out.println("2. View based on availability");
        System.out.println("3. View based on price");
        System.out.println("4. View based on price and availability");
        System.out.println("5. Back ");
        System.out.println("\n0. Exit");
    }

    public static void displayUserReservationsMenu() {
        System.out.println("Menu / Reservations");
        System.out.println("-------------------");
        System.out.println("1. Make new reservation");
        System.out.println("2. View existing reservations");
        System.out.println("3. View upcoming reservations");
        System.out.println("4. Cancel a reservation");
        System.out.println("5. Back");
        System.out.println("\n0. Exit");
    }

    public static void displayAdminMaintenanceMenu(){
        System.out.println("Admin Menu / Fleet Maintenance");
        System.out.println("-------------------");
        System.out.println("1. Schedule car for Maintenance");
        System.out.println("2. Cancel Maintenance");
        System.out.println("3. View upcoming Maintenance");
        System.out.println("4. View all Maintenance");
        System.out.println("5. Back");
        System.out.println("\n0. Exit");
    }


    /**
     * Menu
     */
    public static void adminMenu() {
        Scanner scannerInt = new Scanner(System.in);
        boolean adminMenu = true;
        while (adminMenu) { // stays in loop until Back option
            displayLoggedInAdminMenu();
            int optionAdmin = scannerInt.nextInt();
            switch (optionAdmin) {
                case 1:
                    //  FLEET MENU
                    boolean fleetMenu = true;
                    while (fleetMenu) {
                        displayAdminFleetMenu();
                        int fleetMenuOption = scannerInt.nextInt();
                        switch (fleetMenuOption) {
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
                            case 5:
                                fleetMenu = false;
                                break;
                            case 0:
                                System.exit(0);
                        }
                    }
                    break;
                case 2:
                    //FLEET MAINTENANCE
                    boolean maintenanceMenu = true;
                    while(maintenanceMenu){
                        displayAdminMaintenanceMenu();
                        int maintenanceOption = scannerInt.nextInt();
                        switch (maintenanceOption){
                            case 1:
                                makeNewMaintenance();
                                break;
                            case 2:// CANCEL MAINTENANCE
                                break;
                            case 3:
                                showUpcomingMaintenance();
                                break;
                            case 4:
                                showAllMaintenance();
                                break;
                            case 5:
                                maintenanceMenu = false;
                                break;
                            case 0:
                                System.exit(0);
                        }
                    }
                    break;
                case 3:
                    //   ACCOUNT MENU
                    boolean accountMenu = true;
                    while (accountMenu) {
                        displayAdminAccountsMenu();
                        int optionAdminAccounts = scannerInt.nextInt();
                        switch (optionAdminAccounts) {
                            case 1:
                                viewAccountsDB();
                                break;
                            case 2:
                                addAccountsDB();
                                break;
                            case 3:
                                deleteAccountsDB();
                                break;
                            case 4:
                                switchOptionAccountsDB();
                                break;
                            case 5:
                                changePasswordDB();
                                break;
                            case 6:
                                accountMenu = false;
                                break;
                            case 0:
                                System.exit(0);
                        }
                    }
                    break;

                case 4:
                    //RESERVATIONS
                    System.out.println("Here we`ll have reservations menu ");
                    break;
                case 5: { // GO BACK
                    adminMenu = false;
                    break;
                }
                case 0:
                    System.exit(0);
            }
        }
    }

    public static void userMenu(Accounts account) {
        Scanner scannerInt = new Scanner(System.in);
        Scanner scannerString = new Scanner(System.in);
        boolean userMenu = true;
        while (userMenu) {
            displayLoggedInUserMenu();
            int userMenuOption = scannerInt.nextInt();
            switch (userMenuOption) {
                case 1:
                    boolean userVehicleMenu = true;
                    int userVehicleOption;
                    while (userVehicleMenu) {
                        displayUserVehicleMenu();
                        userVehicleOption = scannerInt.nextInt();
                        switch (userVehicleOption) {
                            case 1:
                                displayAllVehicles();
                                break;
                            case 2:
                                System.out.println("Enter the first day of rental (date format: yyyy/MM/dd e.g. 2024/01/10)");
                                String reserveFromString = scannerString.nextLine();
                                System.out.println("Enter the first day of rental (date format: yyyy/MM/dd e.g. 2024/01/10)");
                                String reserverToString = scannerString.nextLine();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                                LocalDate reserveFromDate = LocalDate.parse(reserveFromString, formatter);
                                LocalDate reserveToDate = LocalDate.parse(reserverToString, formatter);

                                displayVehicleBasedOnAvailability(reserveFromDate, reserveToDate);
                                break;
                            case 3:
                                System.out.println("Enter the minimum price per day");
                                int priceMin = scannerInt.nextInt();
                                System.out.println("Enter the maximum price per day");
                                int priceMax = scannerInt.nextInt();
                                displayVehicleBasedOnPrice(priceMin, priceMax);
                                break;
                            case 4:
                                System.out.println("Enter the first day of rental (date format: yyyy/MM/dd e.g. 2024/01/10)");
                                String reserveFromStringc4 = scannerString.nextLine();
                                System.out.println("Enter the first day of rental (date format: yyyy/MM/dd e.g. 2024/01/10)");
                                String reserverToStringc4 = scannerString.nextLine();
                                System.out.println("Enter the minimum price per day");
                                int priceMinc4 = scannerInt.nextInt();
                                System.out.println("Enter the maximum price per day");
                                int priceMaxc4 = scannerInt.nextInt();
                                DateTimeFormatter formatterc4 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                                LocalDate reserveFromDatec4 = LocalDate.parse(reserveFromStringc4, formatterc4);
                                LocalDate reserveToDatec4 = LocalDate.parse(reserverToStringc4, formatterc4);
                                displayVehicleBasedOnPriceAndAvailability(reserveFromDatec4, reserveToDatec4, priceMinc4, priceMaxc4);
                                break;
                            case 5:
                                userVehicleMenu = false;
                                break;
                            case 0:
                                System.exit(0);
                        }
                    }
                    break;
                case 2:
                    boolean userReservationsMenu = true;
                    int userReservationsOption;
                    while (userReservationsMenu) {
                        displayUserReservationsMenu();
                        userReservationsOption = scannerInt.nextInt();
                        switch (userReservationsOption) {
                            case 1: //Make new reservation for logged account
                                makeNewReservation(account);
                                break;
                            case 2:// show all reservations for logged in account
                                showAllReservations(account);
                                break;
                            case 3: //show upcoming reservations for logged in account
                                showUpcomingReservations(account);
                                break;
                            case 4: //cancel a reservation
                                cancelReservation(account);
                                break;
                            case 5:
                                userReservationsMenu = false;
                                break;
                            case 0:
                                System.exit(0);
                        }
                    }
                    break;
                case 3://back
                    userMenu = false;
                    break;
                case 0:
                    System.exit(0);
            }
        }
    }


    /**
     * Log In and account selection
     */
    public static boolean logInByUsername(Accounts account) {
        Scanner scannerString = new Scanner(System.in);

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

    public static Accounts accountSelection() {
        Scanner scannerString = new Scanner(System.in);
        System.out.println("Username:");
        String username = scannerString.next();

        AccountsRepository accountsRepository = new AccountsRepository();
        return accountsRepository.getAccountByUsername(username);
    }


    /**
     * Car operations
     */
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
            switch (filterType) {
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

    public static void displayAllVehicles() {
        CarRepository carRepository = new CarRepository();

        carTableHeader();
        carRepository.getAllCars().stream().forEach(car -> car.vehicleDisplayAll());
    }

    public static void displayByVehicleType() {
        Scanner scannerInt = new Scanner(System.in);
        CarRepository carRepository = new CarRepository();
        System.out.println("Choose the vehicle type you would like to see:");
        System.out.println("1. Micro Car\n2. Hatchback\n3. Sedan\n4. Combi\n" +
                "5. Pickup Truck\n6. Van\n7. SUV\n8. Sports Car\n9. Luxury Vehicle ");
        int vehicleType = 0;
        while (vehicleType <= 0 || vehicleType > 10) {
            vehicleType = scannerInt.nextInt();
        }
        switch (vehicleType) {
            case 1:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.MICRO).stream().forEach(car -> car.vehicleDisplayAll());
                break;
            case 2:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.HATCHBACK).stream().forEach(car -> car.vehicleDisplayAll());
                break;
            case 3:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.SEDAN).stream().forEach(car -> car.vehicleDisplayAll());
                break;
            case 4:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.COMBI).stream().forEach(car -> car.vehicleDisplayAll());
                break;
            case 5:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.PICKUP).stream().forEach(car -> car.vehicleDisplayAll());
                break;
            case 6:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.VAN).stream().forEach(car -> car.vehicleDisplayAll());
                break;
            case 7:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.SUV).stream().forEach(car -> car.vehicleDisplayAll());
                break;
            case 8:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.SPORT).stream().forEach(car -> car.vehicleDisplayAll());
                break;
            case 9:
                carTableHeader();
                carRepository.getCarsByVehicleType(VehicleType.LUXURY).stream().forEach(car -> car.vehicleDisplayAll());
                break;
        }
    }

    public static void displayByVehicleTransmission() {
        Scanner scannerInt = new Scanner(System.in);
        CarRepository carRepository = new CarRepository();
        System.out.println("Choose the transmission type you would like to see:");
        System.out.println("1. Automatic        2. Manual");
        int vehicleTransmission = 0;
        while (vehicleTransmission <= 0 || vehicleTransmission > 3) {
            vehicleTransmission = scannerInt.nextInt();
        }
        switch (vehicleTransmission) {
            case 1:
                carTableHeader();
                carRepository.getCarsByTransmission(true).stream().forEach(car -> car.vehicleDisplayAll());
                break;
            case 2:
                carTableHeader();
                carRepository.getCarsByTransmission(false).stream().forEach(car -> car.vehicleDisplayAll());
                break;
        }
    }

    public static void displayByVehicleFuelType() {
        Scanner scannerInt = new Scanner(System.in);
        CarRepository carRepository = new CarRepository();
        System.out.println("Choose the fuel type you would like to see:");
        System.out.println("1. Diesel\n2. Gas\n3. Hybrid\n4. Hydrogen\n5. Electric ");
        int fuelType = 0;
        while (fuelType <= 0 || fuelType > 6) {
            fuelType = scannerInt.nextInt();
        }
        switch (fuelType) {
            case 1:
                carTableHeader();
                carRepository.getCarsByFuelType(GasType.DIESEL).stream().forEach(car -> car.vehicleDisplayAll());
                break;
            case 2:
                carTableHeader();
                carRepository.getCarsByFuelType(GasType.GAS).stream().forEach(car -> car.vehicleDisplayAll());
                break;
            case 3:
                carTableHeader();
                carRepository.getCarsByFuelType(GasType.HYBRID).stream().forEach(car -> car.vehicleDisplayAll());
                break;
            case 4:
                carTableHeader();
                carRepository.getCarsByFuelType(GasType.HYDROGEN).stream().forEach(car -> car.vehicleDisplayAll());
                break;
            case 5:
                carTableHeader();
                carRepository.getCarsByFuelType(GasType.ELECTRIC).stream().forEach(car -> car.vehicleDisplayAll());
                break;
        }
    }

    public static void displayVehicleBasedOnPrice(int priceMin, int priceMax) {
        CarRepository carRepository = new CarRepository();

        carTableHeader();
        carRepository.getAllCars().stream().filter(car -> car.getPricePerDay() >= priceMin && car.getPricePerDay() <= priceMax).collect(Collectors.toList())
                .forEach(car -> car.vehicleDisplayAll());

    }

    public static void displayVehicleBasedOnAvailability(LocalDate reservedFrom, LocalDate reservedUntil) {
        CarRepository carRepository = new CarRepository();

        carTableHeader();
        carRepository.getAvailableCars(reservedFrom, reservedUntil).stream().forEach(car -> car.vehicleDisplayAll());
//                .filter(car -> car.getReservations().stream()
//                        .filter(reservation -> !(reservation.getReservedTo().isBefore(reservedFrom) || reservation.getReservedFrom().isAfter(reservedUntil)))
//                        .collect(Collectors.toList()).isEmpty()).collect(Collectors.toList()).forEach(car -> car.vehicleDisplayAll());
    }

    public static void displayVehicleBasedOnPriceAndAvailability(LocalDate reservedFrom, LocalDate reservedUntil, int priceMin, int priceMax) {
        CarRepository carRepository = new CarRepository();
        carTableHeader();
        carRepository.getAvailableCars(reservedFrom, reservedUntil).stream().filter(car -> car.getPricePerDay() >= priceMin && car.getPricePerDay() <= priceMax).collect(Collectors.toList())
                .forEach(car -> car.vehicleDisplayAll());
    }

    public static void displayVehicleWithMaintenance(Car car){
        String transmissionDispaly;
        if(car.isAutomatic())
            transmissionDispaly = "Automatic";
        else
            transmissionDispaly = "Manual";

        System.out.printf("%-10s |  %-20s  |  %-9s  |  %-4s  | %-12s  |  %-8s%n",  "Plate","Make and Model", "Type", "Year", "Transmission", "Fuel");
        System.out.printf("%-10s |  %-20s  |  %-9s  |  %-4s  | %-12s  |  %-8s%n",  car.getLicensePlate(),car.getMake() + " " +
                car.getModel(), car.getVehicleType(), car.getYear().getYear(), transmissionDispaly, car.getGasType());
        System.out.print("Maintenance Record: ");
        List<Maintenance> maintenanceList = car.getMaintenances();
        if(maintenanceList.isEmpty()){
            System.out.println("No Maintenance Record.\n");
        } else {
            System.out.printf("\n%-10s |  %-10s  |  %-30s%n",  "From","To", "Maintenance Details");
            for(Maintenance maintenance: maintenanceList){
                System.out.printf("%-10s |  %-10s  |  %-30s%n",  maintenance.getMaintenanceStart(),maintenance.getMaintenanceEnd(), maintenance.getMaintenanceDetails());
            }
            System.out.println();
        }
    }

    public static void displayVehicleWithUpcomingMaintenance(Car car){
        String transmissionDispaly;
        if(car.isAutomatic())
            transmissionDispaly = "Automatic";
        else
            transmissionDispaly = "Manual";

        System.out.printf("%-10s |  %-20s  |  %-9s  |  %-4s  | %-12s  |  %-8s%n",  "Plate","Make and Model", "Type", "Year", "Transmission", "Fuel");
        System.out.printf("%-10s |  %-20s  |  %-9s  |  %-4s  | %-12s  |  %-8s%n",  car.getLicensePlate(),car.getMake() + " " +
                car.getModel(), car.getVehicleType(), car.getYear().getYear(), transmissionDispaly, car.getGasType());
        System.out.print("Upcoming Maintenance: ");
        List<Maintenance> maintenanceList = car.getMaintenances();
        boolean upcomingMaintenance = false;
        for(Maintenance maintenance: maintenanceList){
            if(maintenance.getMaintenanceStart().isAfter(LocalDate.now())){
                upcomingMaintenance = true;
                break;
            } else {

            }
        }
        if(maintenanceList.isEmpty()){
            System.out.println("No Maintenance Record.\n");
        } else if(upcomingMaintenance) {
            System.out.printf("\n%-10s |  %-10s  |  %-30s%n",  "From","To", "Maintenance Details");
            for(Maintenance maintenance: maintenanceList){
                if(maintenance.getMaintenanceStart().isAfter(LocalDate.now())){
                    System.out.printf("%-10s |  %-10s  |  %-30s%n",  maintenance.getMaintenanceStart(),maintenance.getMaintenanceEnd(), maintenance.getMaintenanceDetails());
                }
            }
            System.out.println();
        } else {
            System.out.println("No Upcoming Maintenance\n");
        }
    }


    /**
     * Account Operations
     */
    public static void viewAccountsDB() {
        Scanner scannerInt = new Scanner(System.in);
        System.out.println("View: ");
        System.out.println("1. All accounts\n2. Admin accounts\n3. User Accounts");
        int viewAccountsOption = scannerInt.nextInt();
        AccountsRepository accountsRepository = new AccountsRepository();
        switch (viewAccountsOption) {
            case 1:
                accountsTableHeader();
                accountsRepository.getAllAccounts().stream().forEach(acc -> acc.viewAllAccounts());
                break;
            case 2:
                accountsTableHeader();
                accountsRepository.getAdminAccounts().stream().forEach(acc -> acc.viewAllAccounts());
                break;
            case 3:
                accountsTableHeader();
                accountsRepository.getUserAccounts().stream().forEach(acc -> acc.viewAllAccounts());
                break;
            // RESUME FROM HERE
        }
    }

    public static void addAccountsDB() {
        Scanner scannerInt = new Scanner(System.in);
        System.out.println("Would you like to add an admin or user?");
        System.out.println("1. Admin       2. User ");
        int adminOrUser = scannerInt.nextInt();
        switch (adminOrUser) {
            case 1:
                addAccount(true);
                break;
            case 2:
                addAccount(false);
                break;
            default:
                System.out.println("Invalid option");

        }
    }

    public static void deleteAccountsDB() {
        Scanner scannerString = new Scanner(System.in);
        AccountsRepository accountsRepository = new AccountsRepository();
        System.out.println("Enter the username of the account you would like to delete: ");
        String usernameDeletion = scannerString.nextLine();
        Accounts account = accountsRepository.getAccountByUsername(usernameDeletion);
        if (account != null) {
            accountsRepository.deleteAccount(account);
            System.out.println("Account deleted successfully!");
        } else {
            System.out.println("Could not find account with username " + usernameDeletion);
        }
    }

    public static void switchOptionAccountsDB() {
        Scanner scannerString = new Scanner(System.in);
        AccountsRepository accountsRepository = new AccountsRepository();
        System.out.println("Enter the username of the account that you would like to switch the permissions for:");
        String usernameSwitch = scannerString.nextLine();
        Accounts account = accountsRepository.getAccountByUsername(usernameSwitch);
        if (account != null) {
            account.setAdmin(!account.isAdmin());
            accountsRepository.updateAccount(account);
        } else {
            System.out.println("Could not find account with username " + usernameSwitch);
        }
    }

    public static void changePasswordDB() {
        Scanner scannerString = new Scanner(System.in);
        AccountsRepository accountsRepository = new AccountsRepository();
        System.out.println("Enter the username of the account that you would like to change the password for:");
        String usernamePRes = scannerString.nextLine();
        Accounts account = accountsRepository.getAccountByUsername(usernamePRes);
        if (account != null) {
            System.out.println("Enter the new password");
            String newPassword = scannerString.nextLine();
            account.setPassword(newPassword);
            accountsRepository.updateAccount(account);
        } else {
            System.out.println("Could not find account with username " + usernamePRes);
        }

    }

    public static void addAccount(boolean isAdmin) {
        Scanner scannerString = new Scanner(System.in);
        System.out.println("Please enter the username:");
        String username = scannerString.nextLine();

        AccountsRepository accountsRepository = new AccountsRepository();
        Accounts account;

        while (true) {
            account = accountsRepository.getAccountByUsername(username);
            if (account == null) {
                break;
            }
            System.out.println("Username already exists. Please enter a different username:");
            username = scannerString.nextLine();
        }
        System.out.println("Please enter the password:");
        String password = scannerString.nextLine();
        System.out.println("Please enter your full name:");
        String name = scannerString.nextLine();
        System.out.println("Please enter your driver license`s number");
        String licenseNumber = scannerString.nextLine();

        Driver driver = new Driver();
        account = new Accounts();

        driver.setName(name);
        driver.setLicenseNumber(licenseNumber);
        account.setUsername(username);
        account.setPassword(password);
        account.setAdmin(isAdmin);
        account.setDriver(driver);
        driver.setAccounts(account);
        accountsRepository.saveAccount(account);
        System.out.println("Account created successfully");

    }

    /**
     Reservation Operations
     */
    public static void showAllReservations(Accounts account){
        AccountsRepository accountsRepository = new AccountsRepository();
        Driver driver = accountsRepository.getDriverWithFullReservations(account);
        List<Reservation> reservs = driver.getReservations();
        Car car;

        if(reservs == null || reservs.isEmpty()){
            System.out.println("You have no reservations");
            return;
        }
        reservationsHeader();
        for(Reservation res: reservs){
            List<Driver> drivers = res.getDrivers();
            car = res.getCar();

            if(drivers.size() == 1){
                System.out.printf("%-10s |  %-20s  |  %-9s  |  %-14s  | %-14s  |  %-14s   | %-20s  | %-20s%n",
                        car.getLicensePlate(), car.getMake() + " " + car.getModel(), car.getVehicleType(), res.getReservedFrom(), res.getReservedTo(),
                        ChronoUnit.DAYS.between(res.getReservedFrom(), res.getReservedTo()), driver.getName(), "N/A");
            } else {
                String driver1 = "N/A";
                String driver2= "N/A";
                for(Driver driverForName : drivers){
                    if(driverForName.getName().equals(driver.getName())){
                        driver1 = driver.getName();
                    } else {
                        driver2 = driverForName.getName();
                    }
                }
                System.out.printf("%-10s |  %-20s  |  %-9s  |  %-14s  | %-14s  |  %-14s   | %-20s  | %-20s%n",
                        car.getLicensePlate(), car.getMake() + " " + car.getModel(), car.getVehicleType(), res.getReservedFrom(), res.getReservedTo(),
                        ChronoUnit.DAYS.between(res.getReservedFrom(), res.getReservedTo()), driver1, driver2);
            }
        }
    }

    public static void reservationsHeader(){
        System.out.printf("%-10s |  %-20s  |  %-9s  |  %-14s  | %-14s  |  %-14s   |  %-20s  | %-20s%n",
                "Plate","Make and Model", "Type", "Reserved From", "Reserved To", "Number of Days", "Driver1", "Driver2");
    }

    public static void makeNewReservation(Accounts account){
        Scanner scannerInt = new Scanner(System.in);
        Scanner scannerString = new Scanner(System.in);

        System.out.println("Please enter the license plate of the car you want to book:");
        String licensePlate = scannerString.nextLine();
        CarRepository carRepository = new CarRepository();
        Car car = carRepository.getCarByLicensePlateWithReservations(licensePlate);
        if(car == null){
            System.out.println("Could not find the mentioned license plate in the database");
            return;
        }
        System.out.println("Enter the first day of rental (date format: yyyy/MM/dd e.g. 2024/01/10)");
        String reserveFromString = scannerString.nextLine();
        System.out.println("Enter the first day of rental (date format: yyyy/MM/dd e.g. 2024/01/10)");
        String reserverToString = scannerString.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate reserveFromDate = LocalDate.parse(reserveFromString, formatter);
        LocalDate reserveToDate = LocalDate.parse(reserverToString, formatter);

        boolean isAvailableReserve = true;
        boolean isAvailableMaint = true;
        List<Reservation> reservations = car.getReservations();
        List<Maintenance> maintenances = car.getMaintenances();
        for(Reservation reservation : reservations){
            if (!(reservation.getReservedTo().isBefore(reserveFromDate) || reservation.getReservedFrom().isAfter(reserveToDate))) {
                isAvailableReserve = false;
                break;
            }
        }
        for(Maintenance maintenance : maintenances){
            if(!(maintenance.getMaintenanceStart().isBefore(reserveFromDate) || maintenance.getMaintenanceEnd().isAfter(reserveToDate))){
                isAvailableMaint = false;
                break;
            }
        }
        if(!isAvailableReserve){
            System.out.println("Vehicle is not available for the mentioned time frame.");
        } else if(!isAvailableMaint){
            System.out.println("Vehicle is not available for the mentioned time frame.");
        }
        else {
            DriverRepository driverRepository = new DriverRepository();
            Reservation reserve = new Reservation();
            reserve.setReservedFrom(reserveFromDate);
            reserve.setReservedTo(reserveToDate);
            reserve.setCar(car);

            AccountsRepository accountsRepository = new AccountsRepository();
            Driver driver1 = accountsRepository.getDriverWithFullReservations(account);
            List<Driver> drivers = new ArrayList<>();
            drivers.add(driver1);

            System.out.println("Would you like to add another driver?");
            System.out.println("1. Yes     2. No");
            int secondDriver = scannerInt.nextInt();
            if(secondDriver == 1){
                Driver driver2 = new Driver();

                String name2;
                String licenseNumber2;
                System.out.println("What is the name of the second driver?");
                name2 = scannerString.nextLine();
                System.out.println("What is the second driver`s license number?");
                licenseNumber2 = scannerString.nextLine();
                driver2.setName(name2);
                driver2.setLicenseNumber(licenseNumber2);
                driverRepository.saveDriver(driver2);
                drivers.add(driver2);
            }
            reserve.setDrivers(drivers);
            reservations.add(reserve);

            car.setReservations(reservations);
            ReservationRepository reservationRepository = new ReservationRepository();
            reservationRepository.saveReservation(reserve);

            List<Reservation> driverReserve =  driver1.getReservations();
            driverReserve.add(reserve);
            driver1.setReservations(driverReserve);
            driverRepository.updateDriver(driver1);
            System.out.println("Successfully created new reservation");
        }
    }

    public static void showUpcomingReservations(Accounts account){
        AccountsRepository accountsRepository = new AccountsRepository();
        Driver driver = accountsRepository.getDriverWithFullReservations(account);
        List<Reservation> reservs = driver.getReservations();
        if(reservs == null || reservs.isEmpty()){
            System.out.println("You have no reservations");
            return;
        }
        reservs = reservs.stream().filter(res -> res.getReservedFrom().isAfter(LocalDate.now())).collect(Collectors.toList());
        if(reservs == null || reservs.isEmpty()){
            System.out.println("You have no upcoming reservations");
            return;
        }
        Car car;

        reservationsHeader();

        for(Reservation res: reservs){
            List<Driver> drivers = res.getDrivers();
            car = res.getCar();

            if(drivers.size() == 1){
                System.out.printf("%-10s |  %-20s  |  %-9s  |  %-14s  | %-14s  |  %-14s   | %-20s  | %-20s%n",
                        car.getLicensePlate(), car.getMake() + " " + car.getModel(), car.getVehicleType(), res.getReservedFrom(), res.getReservedTo(),
                        ChronoUnit.DAYS.between(res.getReservedFrom(), res.getReservedTo()), driver.getName(), "N/A");
            } else {
                String driver1 = "N/A";
                String driver2= "N/A";
                for(Driver driverForName : drivers){
                    if(driverForName.getName().equals(driver.getName())){
                        driver1 = driver.getName();
                    } else {
                        driver2 = driverForName.getName();
                    }
                }
                System.out.printf("%-10s |  %-20s  |  %-9s  |  %-14s  | %-14s  |  %-14s   | %-20s  | %-20s%n",
                        car.getLicensePlate(), car.getMake() + " " + car.getModel(), car.getVehicleType(), res.getReservedFrom(), res.getReservedTo(),
                        ChronoUnit.DAYS.between(res.getReservedFrom(), res.getReservedTo()), driver1, driver2);
            }
        }
    }

    public static void cancelReservation(Accounts account){
        Scanner scannerInt = new Scanner(System.in);
        AccountsRepository accountsRepository = new AccountsRepository();
        Driver driver = accountsRepository.getDriverWithFullReservations(account);
        List<Reservation> reservs = driver.getReservations();
        if(reservs == null || reservs.isEmpty()){
            System.out.println("You have no reservations");
            return;
        }
        reservs = reservs.stream().filter(res -> res.getReservedFrom().isAfter(LocalDate.now())).toList();
        if(reservs.isEmpty()){
            System.out.println("You have no upcoming reservations");
            return;
        }
        Car car;
        System.out.printf("%-6s |  %-20s  |  %-9s  |  %-14s  | %-14s  |  %-14s   |  %-20s  | %-20s%n",
                "Res ID","Make and Model", "Type", "Reserved From", "Reserved To", "Number of Days", "Driver1", "Driver2");
        for(Reservation res: reservs){
            List<Driver> drivers = res.getDrivers();
            car = res.getCar();
            if(drivers.size() == 1){
                System.out.printf("%-6s |  %-20s  |  %-9s  |  %-14s  | %-14s  |  %-14s   | %-20s  | %-20s%n",
                        res.getId(), car.getMake() + " " + car.getModel(), car.getVehicleType(), res.getReservedFrom(), res.getReservedTo(),
                        ChronoUnit.DAYS.between(res.getReservedFrom(), res.getReservedTo()), driver.getName(), "N/A");
            } else {
                String driver1 = "N/A";
                String driver2= "N/A";
                for(Driver driverForName : drivers){
                    if(driverForName.getName().equals(driver.getName())){
                        driver1 = driver.getName();
                    } else {
                        driver2 = driverForName.getName();
                    }
                }
                System.out.printf("%-10s |  %-20s  |  %-9s  |  %-14s  | %-14s  |  %-14s   | %-20s  | %-20s%n",
                        car.getLicensePlate(), car.getMake() + " " + car.getModel(), car.getVehicleType(), res.getReservedFrom(), res.getReservedTo(),
                        ChronoUnit.DAYS.between(res.getReservedFrom(), res.getReservedTo()), driver1, driver2);
            }
        }
        System.out.println("Enter the ID of the reservation that you want to cancel: ");
        int reservationID = scannerInt.nextInt();

        Reservation reservCancel = null;
        for(Reservation res: reservs){
            if(res.getId() == reservationID){
                reservCancel = res;
                break;
            }
        }
        if(reservCancel == null){
            System.out.println("Unable to find reservation. Please try again");
            return;
        }

        List<Reservation> mutableReservs = new ArrayList<>(driver.getReservations());
        mutableReservs.remove(reservCancel);

        ReservationRepository reservationRepository = new ReservationRepository();
        reservationRepository.deleteReservation(reservCancel);

        Car carCancelRes = reservCancel.getCar();
        carCancelRes.setReservations(mutableReservs);
        CarRepository carRepository = new CarRepository();
        carRepository.updateCar(carCancelRes);

        driver.setReservations(mutableReservs);
        DriverRepository driverRepository = new DriverRepository();
        driverRepository.updateDriver(driver);

        account.setDriver(driver);
        accountsRepository.updateAccount(account);
        System.out.println("Reservation successfully canceled");
    }


    /**
     Maintenance Operations
     */
    public static void makeNewMaintenance(){
        Scanner scannerString = new Scanner(System.in);
        CarRepository carRepository = new CarRepository();

        System.out.println("Please enter the license plate of the vehicle you would like to schedule for maintenance:");
        String licensePlate = scannerString.nextLine();
        Car car = carRepository.getCarByLicensePlateWithReservations(licensePlate);

        System.out.println("When would you like to start the maintenance? e.g. 2024/01/24");
        String startMaintenance = scannerString.nextLine();
        System.out.println("When does the maintenance process end? e.g. 2024/01/24");
        String endMaintenance = scannerString.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate startMaintenanceDate = LocalDate.parse(startMaintenance, formatter);
        LocalDate endMaintenanceDate = LocalDate.parse(endMaintenance, formatter);

        boolean isAvailableReserver = true;
        boolean isAvailableMaint = true;
        List<Reservation> reservations = car.getReservations();
        List<Maintenance> carMaintenanceList = car.getMaintenances();

        for(Reservation reservation : reservations){
            if (!(reservation.getReservedTo().isBefore(startMaintenanceDate) || reservation.getReservedFrom().isAfter(endMaintenanceDate))) {
                isAvailableReserver = false;
                break;
            }
        }

        for(Maintenance maintenance : carMaintenanceList){
            if(!(maintenance.getMaintenanceStart().isBefore(startMaintenanceDate) || maintenance.getMaintenanceEnd().isAfter(endMaintenanceDate))){
                isAvailableMaint = false;
                break;
            }
        }

        if(!isAvailableReserver ){
            System.out.println("Vehicle has a reservation for that period. Please choose a different period for the maintenance");
        } else if (!isAvailableMaint){
            System.out.println("Vehicle has a maintenance scheduled for that period");
        }else {
            System.out.println("What will the maintenance be? e.g. Oil Change, Tire change, Inspection etc. ");
            String maintenanceOperations = scannerString.nextLine();
            List<String> maintenanceList = Arrays.asList(maintenanceOperations.split(","));

            Maintenance maintenance = new Maintenance();
            maintenance.setMaintenanceStart(startMaintenanceDate);
            maintenance.setMaintenanceEnd(endMaintenanceDate);
            maintenance.setMaintenanceDetailsList(maintenanceList);
            maintenance.setCar(car);
            MaintenanceRepository maintenanceRepository = new MaintenanceRepository();
            maintenanceRepository.addMaintenanceRepository(maintenance);

            List<Maintenance> carMaintenancesList = car.getMaintenances();
            carMaintenancesList.add(maintenance);
            car.setMaintenances(carMaintenancesList);
            carRepository.updateCar(car);
            System.out.println("Successfully created new maintenance record ");
        }
    }

    public static void showAllMaintenance(){
        MaintenanceRepository maintenanceRepository = new MaintenanceRepository();
        CarRepository carRepository = new CarRepository();
        List<Car> carList = carRepository.getAllCarsWithMaintenance();
        for(Car car : carList){
            if(car != null){
                displayVehicleWithMaintenance(car);
            }
        }
    }

    public static void showUpcomingMaintenance(){
        MaintenanceRepository maintenanceRepository = new MaintenanceRepository();
        CarRepository carRepository = new CarRepository();
        List<Car> carList = carRepository.getAllCarsWithMaintenance();
        for(Car car : carList){
            if(car != null){
                displayVehicleWithUpcomingMaintenance(car);
            }
        }
    }

}


