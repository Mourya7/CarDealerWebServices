package com.imourya.webService.Data;

import com.imourya.webService.Entity.Vehicle;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by mourya on 5/14/17.
 * Interface for Vehicles class
 */
public interface VehicleDao {

    Collection<Vehicle> getAllVehicles();

    Vehicle getVehicleById(int id);

    Collection <Vehicle> getVehiclesByPrice(int price);

    Collection <Vehicle> getVehiclesByType(String type);

    double getVehiclesAveragePriceByType(String type);

    double getCarAveragePrice(String attribute,String value);

    HashMap getSortedByPrice(String attribute, String value);

    Collection<Vehicle> getCarsSortedByPrice();
}
