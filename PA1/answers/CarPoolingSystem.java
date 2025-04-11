package ceng.ceng351.carpoolingdb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarPoolingSystem implements ICarPoolingSystem {

    private static String url = "jdbc:h2:mem:carpoolingdb;DB_CLOSE_DELAY=-1"; // In-memory database
    private static String user = "sa";          // H2 default username
    private static String password = "";        // H2 default password

    private Connection connection;

    public void initialize(Connection connection) {
        this.connection = connection;
    }

    //Given: getAllDrivers()
    //Testing 5.16: All Drivers after Updating the Ratings
    @Override
    public Driver[] getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();

        //uncomment following code slice
        String query = "SELECT PIN, rating FROM Drivers ORDER BY PIN ASC;";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int PIN = rs.getInt("PIN");
                double rating = rs.getDouble("rating");

                // Create a Driver object with only PIN and rating
                Driver driver = new Driver(PIN, rating);
                drivers.add(driver);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return drivers.toArray(new Driver[0]);
    }


    //5.1 Task 1 Create tables
    @Override
    public int createTables() {
        int tableCount = 0;

        String createParticipantsTable = "CREATE TABLE IF NOT EXISTS Participants (" +
                "PIN INT PRIMARY KEY," +
                "p_name VARCHAR (50)," +
                "age INT" +
                ");";

        String createPassengersTable = "CREATE TABLE IF NOT EXISTS Passengers (" +
                "PIN INT PRIMARY KEY," +
                "membership_status VARCHAR(50)," +
                "FOREIGN KEY (PIN) REFERENCES Participants(PIN)" +
                ");";

        String createDriversTable = "CREATE TABLE IF NOT EXISTS Drivers (" +
                "PIN INT PRIMARY KEY," +
                "rating DOUBLE," +
                "FOREIGN KEY (PIN) REFERENCES Participants(PIN)" +
                ");";

        String createCarsTable = "CREATE TABLE IF NOT EXISTS Cars (" +
                "CarID INT PRIMARY KEY," +
                "PIN INT," +
                "color VARCHAR(50)," +
                "brand VARCHAR(50)," +
                "FOREIGN KEY (PIN) REFERENCES Drivers(PIN)" +
                ");";

        String createTripsTable = "CREATE TABLE IF NOT EXISTS Trips (" +
                "TripID INT PRIMARY KEY," +
                "CarId INT," +
                "date DATE," +
                "departure VARCHAR(50)," +
                "destination VARCHAR(50)," +
                "num_seats_available INT," +
                "FOREIGN KEY (CarID) REFERENCES Cars(CarID)" +
                ");";

        String createBookingsTable = "CREATE TABLE IF NOT EXISTS Bookings (" +
                "TripID INT," +
                "PIN INT," +
                "booking_status VARCHAR(50)," +
                "PRIMARY KEY (TripID, PIN)," +
                "FOREIGN KEY (TripID) REFERENCES Trips(TripID)," +
                "FOREIGN KEY (PIN) REFERENCES Passengers(PIN)" +
                ");";

        try (Statement statement = connection.createStatement()) {
            try {
                statement.executeUpdate(createParticipantsTable);
                tableCount++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.executeUpdate(createPassengersTable);
                tableCount++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.executeUpdate(createDriversTable);
                tableCount++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.executeUpdate(createCarsTable);
                tableCount++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.executeUpdate(createTripsTable);
                tableCount++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.executeUpdate(createBookingsTable);
                tableCount++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/

        return tableCount;
    }


    //5.17 Task 17 Drop tables
    @Override
    public int dropTables() {
        int tableCount = 0;

        try (Statement statement = connection.createStatement()) {
            try {
                statement.executeUpdate("DROP TABLE IF EXISTS Bookings;");
                tableCount++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.executeUpdate("DROP TABLE IF EXISTS Trips;");
                tableCount++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.executeUpdate("DROP TABLE IF EXISTS Cars;");
                tableCount++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.executeUpdate("DROP TABLE IF EXISTS Drivers;");
                tableCount++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.executeUpdate("DROP TABLE IF EXISTS Passengers;");
                tableCount++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.executeUpdate("DROP TABLE IF EXISTS Participants;");
                tableCount++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/

        return tableCount;
    }


    //5.2 Task 2 Insert Participants
    @Override
    public int insertParticipants(Participant[] participants) {
        int rowsInserted = 0;

        String query = "INSERT INTO Participants (PIN, p_name, age) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (Participant participant : participants) {
                pstmt.setInt(1, participant.getPIN());
                pstmt.setString(2, participant.getP_name());
                pstmt.setInt(3, participant.getAge());
                rowsInserted += pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/

        return rowsInserted;
    }


    //5.2 Task 2 Insert Passengers
    @Override
    public int insertPassengers(Passenger[] passengers) {
        int rowsInserted = 0;

        String query = "INSERT INTO Passengers (PIN, membership_status) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (Passenger passenger : passengers) {
                pstmt.setInt(1, passenger.getPIN());
                pstmt.setString(2, passenger.getMembership_status());
                rowsInserted += pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/

        return rowsInserted;
    }


    //5.2 Task 2 Insert Drivers
    @Override
    public int insertDrivers(Driver[] drivers) {
        int rowsInserted = 0;

        String query = "INSERT INTO Drivers (PIN, rating) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (Driver driver : drivers) {
                pstmt.setInt(1, driver.getPIN());
                pstmt.setDouble(2, driver.getRating());
                rowsInserted += pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/

        return rowsInserted;
    }


    //5.2 Task 2 Insert Cars
    @Override
    public int insertCars(Car[] cars) {
        int rowsInserted = 0;

        String query = "INSERT INTO Cars (CarID, PIN, color, brand) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (Car car : cars) {
                pstmt.setInt(1, car.getCarID());
                pstmt.setInt(2, car.getPIN());
                pstmt.setString(3, car.getColor());
                pstmt.setString(4, car.getBrand());
                rowsInserted += pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/

        return rowsInserted;
    }


    //5.2 Task 2 Insert Trips
    @Override
    public int insertTrips(Trip[] trips) {
        int rowsInserted = 0;

        String query = "INSERT INTO Trips (TripID, CarID, date, departure, destination, num_seats_available) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (Trip trip : trips) {
                pstmt.setInt(1, trip.getTripID());
                pstmt.setInt(2, trip.getCarID());

                String date_as_string = trip.getDate();
                java.sql.Date date_as_date = java.sql.Date.valueOf(date_as_string);
                pstmt.setDate(3, date_as_date);

                pstmt.setString(4, trip.getDeparture());
                pstmt.setString(5, trip.getDestination());
                pstmt.setInt(6, trip.getNum_seats_available());
                rowsInserted += pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/

        return rowsInserted;
    }

    //5.2 Task 2 Insert Bookings
    @Override
    public int insertBookings(Booking[] bookings) {
        int rowsInserted = 0;

        String query = "INSERT INTO Bookings (TripID, PIN, booking_status) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (Booking booking : bookings) {
                pstmt.setInt(1, booking.getTripID());
                pstmt.setInt(2, booking.getPIN());
                pstmt.setString(3, booking.getBooking_status());
                rowsInserted += pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/

        return rowsInserted;
    }


    //5.3 Task 3 Find all participants who are recorded as both drivers and passengers
    @Override
    public Participant[] getBothPassengersAndDrivers() {
        List<Participant> result = new ArrayList<>();

        String query = "SELECT p.PIN, p.p_name, p.age " +
                "FROM Participants p " +
                "WHERE p.PIN IN (SELECT d.PIN FROM Drivers d) " +
                "AND p.PIN IN (SELECT pa.PIN FROM Passengers pa) " +
                "ORDER BY p.PIN ASC;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int pin = rs.getInt("PIN");
                String name = rs.getString("p_name");
                int age = rs.getInt("age");

                Participant participant = new Participant(pin, name, age);
                result.add(participant);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/

        return result.toArray(new Participant[0]);
    }


    //5.4 Task 4 Find the PINs, names, ages, and ratings of drivers who do not own any cars
    @Override
    public QueryResult.DriverPINNameAgeRating[] getDriversWithNoCars() {
        List<QueryResult.DriverPINNameAgeRating> result = new ArrayList<>();

        String query = "SELECT d.PIN, p.p_name, p.age, d.rating " +
                "FROM Drivers d " +
                "JOIN Participants p ON d.PIN = p.PIN " +
                "WHERE d.PIN NOT IN (SELECT c.PIN FROM Cars c) " +
                "ORDER BY d.PIN ASC;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int pin = rs.getInt("PIN");
                String name = rs.getString("p_name");
                int age = rs.getInt("age");
                double rating = rs.getDouble("rating");

                QueryResult.DriverPINNameAgeRating driver = new QueryResult.DriverPINNameAgeRating(pin, name, age, rating);
                result.add(driver);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/

        return result.toArray(new QueryResult.DriverPINNameAgeRating[0]);
    }


    //5.5 Task 5 Delete Drivers who do not own any cars
    @Override
    public int deleteDriversWithNoCars() {
        int rowsDeleted = 0;

        String query = "DELETE FROM Drivers d " +
                "WHERE d.PIN NOT IN (SELECT c.PIN FROM Cars c);";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            rowsDeleted = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/

        return rowsDeleted;
    }


    //5.6 Task 6 Find all cars that are not taken part in any trips
    @Override
    public Car[] getCarsWithNoTrips() {
        List<Car> result = new ArrayList<>();

        String query = "SELECT c.CarID, c.PIN, c.color, c.brand " +
                "FROM Cars c " +
                "WHERE c.CarID NOT IN (SELECT t.CarID FROM Trips t) " +
                "ORDER BY c.CarID ASC;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int carid = rs.getInt("CarID");
                int pin = rs.getInt("PIN");
                String color = rs.getString("color");
                String brand = rs.getString("brand");

                Car car = new Car(carid, pin, color, brand);
                result.add(car);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/
    	
        return result.toArray(new Car[0]);
    }
    
    
    //5.7 Task 7 Find all passengers who didn't book any trips
    @Override
    public Passenger[] getPassengersWithNoBooks() {
        List<Passenger> result = new ArrayList<>();

        String query = "SELECT p.PIN, p.membership_status " +
                "FROM Passengers p " +
                "WHERE p.PIN NOT IN (SELECT b.PIN FROM Bookings b) " +
                "ORDER BY p.PIN ASC;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int pin = rs.getInt("PIN");
                String membership_status = rs.getString("membership_status");

                Passenger passenger = new Passenger(pin, membership_status);
                result.add(passenger);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/
    	
        return result.toArray(new Passenger[0]);
    }


    //5.8 Task 8 Find all trips that depart from the specified city to specified destination city on specific date
    @Override
    public Trip[] getTripsFromToCitiesOnSpecificDate(String departure, String destination, String date) {
        List<Trip> result = new ArrayList<>();

        String query = "SELECT t.TripID, t.CarID, t.Date, t.departure, t.destination, t.num_seats_available " +
                "FROM Trips t " +
                "WHERE t.departure = ? " +
                    "AND t.destination = ? " +
                    "AND t.date = ? " +
                "ORDER BY t.TripID ASC;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, departure);
            pstmt.setString(2, destination);
            pstmt.setDate(3, java.sql.Date.valueOf(date));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int tripid = rs.getInt("TripID");
                int carid = rs.getInt("CarID");
                java.sql.Date my_date = rs.getDate("date");
                String my_departure = rs.getString("departure");
                String my_destination = rs.getString("destination");
                int num_seats_available = rs.getInt("num_seats_available");

                Trip trip = new Trip(tripid, carid, my_date.toString(), my_departure, my_destination, num_seats_available);
                result.add(trip);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/
    	
        return result.toArray(new Trip[0]);
    }


    //5.9 Task 9 Find the PINs, names, ages, and membership_status of passengers who have bookings on all trips destined at a particular city
    @Override
    public QueryResult.PassengerPINNameAgeMembershipStatus[] getPassengersWithBookingsToAllTripsForCity(String city) {
        List<QueryResult.PassengerPINNameAgeMembershipStatus> result = new ArrayList<>();

        String query = "SELECT pa.PIN, p.p_name, p.age, pa.membership_status " +
                "FROM Passengers pa " +
                "JOIN Participants p ON pa.PIN = p.PIN " +
                "WHERE NOT EXISTS ( " +
                    "SELECT t.TripID " +
                    "FROM Trips t " +
                    "WHERE t.destination = ? " +
                        "AND t.TripID NOT IN ( " +
                            "SELECT b.TripID " +
                            "FROM Bookings b " +
                            "WHERE b.PIN = pa.PIN " +
                        ") " +
                ") " +
                "ORDER BY pa.PIN ASC;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, city);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int pin = rs.getInt("PIN");
                String name = rs.getString("p_name");
                int age = rs.getInt("age");
                String membership_status = rs.getString("membership_status");

                QueryResult.PassengerPINNameAgeMembershipStatus passenger = new QueryResult.PassengerPINNameAgeMembershipStatus(pin, name, age, membership_status);
                result.add(passenger);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/

        return result.toArray(new QueryResult.PassengerPINNameAgeMembershipStatus[0]);
    }

    
    //5.10 Task 10 For a given driver PIN, find the CarIDs that the driver owns and were booked at most twice.    
    @Override
    public Integer[] getDriverCarsWithAtMost2Bookings(int driverPIN) {
        List<Integer> result = new ArrayList<>();

        String query = "SELECT c.CarID " +
                "FROM Cars c " +
                "JOIN Drivers d ON d.PIN = c.PIN " +
                "JOIN Trips t ON c.CarID = t.CarID " +
                "JOIN Bookings b ON t.TripID = b.TripID " +
                "WHERE d.PIN = ? " +
                "GROUP BY c.CarID " +
                "HAVING COUNT(c.CarID) <= 2 " +
                "ORDER BY c.CarID ASC;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, driverPIN);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int carid = rs.getInt("CarID");
                result.add(carid);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    	/*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/

        return result.toArray(new Integer[0]); // Convert list to array
    }


    //5.11 Task 11 Find the average age of passengers with "Confirmed" bookings (i.e., booking_status is ”Confirmed”) on trips departing from a given city and within a specified date range
    @Override
    public Double getAvgAgeOfPassengersDepartFromCityBetweenTwoDates(String city, String start_date, String end_date) {
        Double averageAge = null;

        String query = "SELECT AVG(p.age) AS avg_age " +
                "FROM Passengers pa " +
                "JOIN Participants p ON pa.PIN = p.PIN " +
                "JOIN Bookings b ON pa.PIN = b.PIN " +
                "JOIN Trips t ON b.TripID = t.TripID " +
                "WHERE b.booking_status = 'Confirmed' " +
                "AND t.departure = ? " +
                "AND t.date BETWEEN ? AND ?;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, city);
            pstmt.setDate(2, java.sql.Date.valueOf(start_date));
            pstmt.setDate(3, java.sql.Date.valueOf(end_date));

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                averageAge = rs.getDouble("avg_age");
                if (rs.wasNull()) {
                    averageAge = null;
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/

        return averageAge;
    }


    //5.12 Task 12 Find Passengers in a Given Trip.
    @Override
    public QueryResult.PassengerPINNameAgeMembershipStatus[] getPassengerInGivenTrip(int TripID) {
        List<QueryResult.PassengerPINNameAgeMembershipStatus> result = new ArrayList<>();

        String query = "SELECT p.PIN, p.p_name, p.age, pa.membership_status " +
                "FROM Participants p " +
                "JOIN Passengers pa ON pa.PIN = p.PIN " +
                "JOIN Bookings b ON b.PIN = p.PIN " +
                "JOIN Trips t ON t.TripID = b.TripID " +
                "WHERE t.TripID = ?" +
                "ORDER BY p.PIN ASC;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, TripID);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int pin = rs.getInt("PIN");
                String name = rs.getString("p_name");
                int age = rs.getInt("age");
                String membership_status = rs.getString("membership_status");

                QueryResult.PassengerPINNameAgeMembershipStatus passenger = new QueryResult.PassengerPINNameAgeMembershipStatus(pin, name, age, membership_status);
                result.add(passenger);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/
    	
        return result.toArray(new QueryResult.PassengerPINNameAgeMembershipStatus[0]);
    }


    //5.13 Task 13 Find Drivers’ Scores
    @Override
    public QueryResult.DriverScoreRatingNumberOfBookingsPIN[] getDriversScores() {
        List<QueryResult.DriverScoreRatingNumberOfBookingsPIN> result = new ArrayList<>();

        String query = "SELECT d.PIN AS dPIN, " +
                "d.rating, " +
                "COUNT(b.TripID) AS number_of_bookings, " +
                "(d.rating * COUNT(b.TripID)) AS score " +
                "FROM Drivers d " +
                "JOIN Cars c ON d.PIN = c.PIN " +
                "JOIN Trips t ON c.CarID = t.CarID " +
                "JOIN Bookings b ON t.TripID = b.TripID " +
                "GROUP BY d.PIN, d.rating " +
                "HAVING COUNT(b.TripID) > 0 " +
                "ORDER BY score DESC, dPIN ASC;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int pin = rs.getInt("dPIN");
                double rating = rs.getDouble("rating");
                int number_of_bookings = rs.getInt("number_of_bookings");
                double score = rs.getDouble("score");

                QueryResult.DriverScoreRatingNumberOfBookingsPIN driver = new QueryResult.DriverScoreRatingNumberOfBookingsPIN(score, rating, number_of_bookings, pin);
                result.add(driver);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    	/*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/
    	
        return result.toArray(new QueryResult.DriverScoreRatingNumberOfBookingsPIN[0]);
    }

    
    //5.14 Task 14 Find average ratings of drivers who have trips destined to each city
    @Override
    public QueryResult.CityAndAverageDriverRating[] getDriversAverageRatingsToEachDestinatedCity() {
        List<QueryResult.CityAndAverageDriverRating> result = new ArrayList<>();

        String query = "SELECT t.destination AS city, " +
                "AVG(d.rating) AS avg_rating " +
                "FROM Trips t " +
                "JOIN Cars c ON t.CarID = c.CarID " +
                "JOIN Drivers d ON c.PIN = d.PIN " +
                "GROUP BY t.destination " +
                "ORDER BY city ASC;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String city = rs.getString("city");
                double avg_rating = rs.getDouble("avg_rating");

                QueryResult.CityAndAverageDriverRating driver = new QueryResult.CityAndAverageDriverRating(city, avg_rating);
                result.add(driver);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/
    	
        return result.toArray(new QueryResult.CityAndAverageDriverRating[0]);
    }


    //5.15 Task 15 Find total number of bookings of passengers for each membership status
    @Override
    public QueryResult.MembershipStatusAndTotalBookings[] getTotalBookingsEachMembershipStatus() {
        List<QueryResult.MembershipStatusAndTotalBookings> result = new ArrayList<>();

        String query = "SELECT pa.membership_status AS membership_status, " +
                "COUNT(b.TripID) AS total_bookings " +
                "FROM Passengers pa " +
                "JOIN Bookings b ON pa.PIN = b.PIN " +
                "GROUP BY pa.membership_status " +
                "ORDER BY membership_status ASC;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String membership_status = rs.getString("membership_status");
                int total_bookings = rs.getInt("total_bookings");

                QueryResult.MembershipStatusAndTotalBookings booking = new QueryResult.MembershipStatusAndTotalBookings(membership_status, total_bookings);
                result.add(booking);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    	/*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/
    	
        return result.toArray(new QueryResult.MembershipStatusAndTotalBookings[0]);
    }

    
    //5.16 Task 16 For the drivers' ratings, if rating is smaller than 2.0 or equal to 2.0, update the rating by adding 0.5.
    @Override
    public int updateDriverRatings() {
        int rowsUpdated = 0;

        String query = "UPDATE Drivers d " +
                "SET d.rating = d.rating + 0.5 " +
                "WHERE d.rating <= 2.0;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            rowsUpdated = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*****************************************************/
        /*****************************************************/
        /*********************  TODO  ***********************/
        /*****************************************************/
        /*****************************************************/
    	
        return rowsUpdated;
    }
    

    //6.1 (Optional) Task 18 Find trips departing from the given city
    @Override
    public Trip[] getTripsFromCity(String city) {
        
    	/*****************************************************/
        /*****************************************************/
        /*****************  TODO  (Optional)   ***************/
        /*****************************************************/
        /*****************************************************/
    	
        return new Trip[0];
    }
    
    
    //6.2 (Optional) Task 19 Find all trips that have never been booked
    @Override
    public Trip[] getTripsWithNoBooks() {
        
    	/*****************************************************/
        /*****************************************************/
        /*****************  TODO  (Optional)   ***************/
        /*****************************************************/
        /*****************************************************/
    	
        return new Trip[0];
    }
    
    
    //6.3 (Optional) Task 20 For each driver, find the trip(s) with the highest number of bookings
    @Override
    public QueryResult.DriverPINandTripIDandNumberOfBookings[] getTheMostBookedTripsPerDriver() {
        
    	/*****************************************************/
        /*****************************************************/
        /*****************  TODO  (Optional)   ***************/
        /*****************************************************/
        /*****************************************************/
    	
        return new QueryResult.DriverPINandTripIDandNumberOfBookings[0];
    }
    
    
    //6.4 (Optional) Task 21 Find Full Cars
    @Override
    public QueryResult.FullCars[] getFullCars() {
        
    	/*****************************************************/
        /*****************************************************/
        /*****************  TODO  (Optional)   ***************/
        /*****************************************************/
        /*****************************************************/
    	
        return new QueryResult.FullCars[0];
    }

}
