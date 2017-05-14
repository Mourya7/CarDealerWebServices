package com.imourya.webService.Data;

import com.imourya.webService.Entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.* ;
import java.util.Map.*;

/**
 * Created by mourya on 5/13/17.
 * Data Layer class to fetch and wrap class
 */
@Repository("mysql")
public class Vehicles implements VehicleDao{

    //jdbc variable to connect with db
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //SQL statement to create class
    final String sql = "SELECT * FROM vehicles";

    //List of all vehicles
    private List<Vehicle> vehicleList;

    //Map of vehicles {key = VIN, Value = {Vehicle}}
    private HashMap<Integer,Vehicle> vehicles = new HashMap<Integer,Vehicle>();

    //Row mapper class to override its mapRow method
    private static class VehicleRowMapper implements RowMapper<Vehicle>
    {
        @Override
        public Vehicle mapRow(ResultSet resultSet,int i) throws SQLException
        {
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleID(resultSet.getInt("VIN"));
            vehicle.setVehicleType(resultSet.getString("type"));
            vehicle.setEngineType(resultSet.getString("engineType"));
            vehicle.setModelYear(resultSet.getInt("year"));
            vehicle.setVehicleColor(resultSet.getString("color"));
            vehicle.setVehicleBrand(resultSet.getString("brand"));
            vehicle.setPrice(resultSet.getInt("price"));
            return vehicle;
        }
    }

    //Initialize vehicles{key,value} map with data
    @PostConstruct
    public void initializeVehicleData() {

        vehicleList = jdbcTemplate.query(sql, new VehicleRowMapper());
        for(Vehicle v:vehicleList)
        {
            vehicles.put(v.getVehicleID(),v);
        }
    }

    //Clear on close of connection
    @PreDestroy
    public void clearVehicleData() {
       vehicles = null;
       vehicleList = null;
    }

    //Get all Vehicles
    @Override
    public Collection<Vehicle> getAllVehicles()
    {
        for(Vehicle v:vehicleList)
        {
            vehicles.put(v.getVehicleID(),v);
        }
        return vehicles.values();
    }

    //Get vehicles by VIN -> {id}
    @Override
    public Vehicle getVehicleById(int id)
    {
        for(Vehicle v:vehicleList)
        {
            vehicles.put(v.getVehicleID(),v);
        }
        for(int key:vehicles.keySet())
        {
            if(vehicles.get(key).getVehicleID() == id)
            {
                return vehicles.get(key);
            }
        }
        return null;
    }//getVehiclesById

    //Get vehicles by price
    @Override
    public Collection <Vehicle> getVehiclesByPrice(int price)
    {
        for(Vehicle v:vehicleList)
        {
            vehicles.put(v.getVehicleID(),v);
        }
        HashMap<Integer,Vehicle> vehicleResultsByPrice = new HashMap<Integer,Vehicle>();
        for(int key:vehicles.keySet())
        {
            if(vehicles.get(key).getPrice() == price)
            {
                vehicleResultsByPrice.put(key,vehicles.get(key));
            }
        }
        return vehicleResultsByPrice.values();
    }//getVehiclesByPrice

    //Get vehicles by type
    @Override
    public Collection <Vehicle> getVehiclesByType(String type)
    {
        for(Vehicle v:vehicleList)
        {
            vehicles.put(v.getVehicleID(),v);
        }
        HashMap<Integer,Vehicle> vehicleResultsByType = new HashMap<Integer,Vehicle>();
        int results = 0;
        int average = 0;
        for(int key:vehicles.keySet()) {
            if (vehicles.get(key).getVehicleType().compareTo(type)==0) {
                vehicleResultsByType.put(key, vehicles.get(key));
            }
        }
        if(!vehicleResultsByType.isEmpty())
        {
            return vehicleResultsByType.values();
        }
        else
        {
            return null;
        }
    }//getVehiclesByType

    //Get average of vehicles price by type
    @Override
    public double getVehiclesAveragePriceByType(String type)
    {
        for(Vehicle v:vehicleList)
        {
            vehicles.put(v.getVehicleID(),v);
        }
        int sum = 0;
        int numberOfVehicles = 0;
        for(int key:vehicles.keySet()) {
            if (vehicles.get(key).getVehicleType().compareTo(type)==0) {
                sum = sum + vehicles.get(key).getPrice();
                numberOfVehicles++;
            }
        }
        if(sum!=0)
        {
            return ((double)sum/numberOfVehicles);
        }
        return 0.0;
    }//getVehiclesAvgPriceByType

    //Get average of cars price
    @Override
    public double getCarAveragePrice(String attribute,String value)
    {
        for(Vehicle v:vehicleList)
        {
            vehicles.put(v.getVehicleID(),v);
        }
        HashMap<Integer,Vehicle> finalCarResultsByAttribute = getCarSortedByPrice(attribute,value,vehicles);
        int sum = 0;

        for(int key:finalCarResultsByAttribute.keySet())
        {
                sum = sum + finalCarResultsByAttribute.get(key).getPrice();
        }
        if(sum!=0)
        {
            return ((double)sum/finalCarResultsByAttribute.size());
        }
        return 0.0;
    }

    //sort cars price by {brand,color,engine}
    @Override
    public HashMap getSortedByPrice(String attribute,String value)
    {
        for(Vehicle v:vehicleList)
        {
            vehicles.put(v.getVehicleID(),v);
        }
        return getCarSortedByPrice(attribute,value,vehicles);
    }


    //sort cars by price
    @Override
    public Collection <Vehicle> getCarsSortedByPrice()
    {
        HashMap<Integer,Vehicle> carResultsByType = new HashMap<Integer,Vehicle>();
        for(int key:vehicles.keySet()) {
            if (vehicles.get(key).getVehicleType().compareTo("Car")==0) {
                carResultsByType.put(vehicles.get(key).getVehicleID(),vehicles.get(key));
            }
        }
        HashMap<Integer,Integer> carResultsByAttribute = new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> sortedCarResultsByAttribute = new HashMap<Integer,Integer>();
        HashMap finalCarResultsByAttribute = new LinkedHashMap();

        for(int key:carResultsByType.keySet())
        {
            carResultsByAttribute.put(carResultsByType.get(key).getVehicleID(),carResultsByType.get(key).getPrice());
        }
        sortedCarResultsByAttribute = sortByPrice(carResultsByAttribute);

        for(int key:sortedCarResultsByAttribute.keySet())
        {
            if(key==carResultsByType.get(key).getVehicleID())
            {
                finalCarResultsByAttribute.put(key,carResultsByType.get(key));
            }
        }
        return finalCarResultsByAttribute.values();
    }

    //sort helper method - 1 [Filter car results by attribute and value] {brand,color,engine}
    static HashMap getCarSortedByPrice(String attribute,String value,HashMap<Integer,Vehicle> vehicles)
    {
        HashMap<Integer,Vehicle> carResultsByType = new HashMap<Integer,Vehicle>();
        for(int key:vehicles.keySet()) {
            if (vehicles.get(key).getVehicleType().compareTo("Car")==0) {
                carResultsByType.put(vehicles.get(key).getVehicleID(),vehicles.get(key));
            }
        }

        HashMap<Integer,Integer> carResultsByAttribute = new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> sortedCarResultsByAttribute = new HashMap<Integer,Integer>();
        HashMap finalCarResultsByAttribute = new LinkedHashMap();

        double averagePrice = 0.0;
        switch (attribute)
        {
            case "brand" :
                for(int key:carResultsByType.keySet()) {
                    if (carResultsByType.get(key).getVehicleBrand().compareTo(value)==0) {
                        carResultsByAttribute.put(carResultsByType.get(key).getVehicleID(),carResultsByType.get(key).getPrice());
                    }
                }
                sortedCarResultsByAttribute = sortByPrice(carResultsByAttribute);

                for(int key:sortedCarResultsByAttribute.keySet())
                {
                    if(key==carResultsByType.get(key).getVehicleID())
                    {
                        finalCarResultsByAttribute.put(key,carResultsByType.get(key));
                    }
                }
                break;
            case "color" :
                for(int key:carResultsByType.keySet()) {
                    if (carResultsByType.get(key).getVehicleColor().compareTo(value)==0) {
                        carResultsByAttribute.put(carResultsByType.get(key).getVehicleID(),carResultsByType.get(key).getPrice());
                    }
                }
                sortedCarResultsByAttribute = sortByPrice(carResultsByAttribute);
                for(int key:sortedCarResultsByAttribute.keySet())
                {
                    if(key==carResultsByType.get(key).getVehicleID())
                    {
                        finalCarResultsByAttribute.put(key,carResultsByType.get(key));
                    }
                }
                break;
            case "engine" :
                for(int key:carResultsByType.keySet()) {
                    if (carResultsByType.get(key).getEngineType().compareTo(value)==0) {
                        carResultsByAttribute.put(carResultsByType.get(key).getVehicleID(),carResultsByType.get(key).getPrice());
                    }
                }
                sortedCarResultsByAttribute = sortByPrice(carResultsByAttribute);
                for(int key:sortedCarResultsByAttribute.keySet())
                {
                    if(key==carResultsByType.get(key).getVehicleID())
                    {
                        finalCarResultsByAttribute.put(key,carResultsByType.get(key));
                    }
                }
                break;
            default:
                return null;
        }

        return finalCarResultsByAttribute;
    }//getCarSortedByPrice

    //sort helper method - 2 [Sort Filtered car results]
    static HashMap<Integer,Integer> sortByPrice(HashMap<Integer,Integer> carResultsByAttribute)
    {

        if(carResultsByAttribute.size() == 1)
            return carResultsByAttribute;

        HashMap sortedCarResultsByAttribute = new LinkedHashMap();
        List<Entry<Integer,Integer>> list = new LinkedList<Entry<Integer,Integer>>(carResultsByAttribute.entrySet());
        Collections.sort(list, new Comparator<Entry<Integer, Integer>>() {
            @Override
            public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        for(Entry<Integer,Integer> listItem:list)
        {
            sortedCarResultsByAttribute.put(listItem.getKey(),listItem.getValue());
        }
        return sortedCarResultsByAttribute;
    }//sortByPrice


 } //class
