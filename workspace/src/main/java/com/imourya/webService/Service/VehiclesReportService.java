package com.imourya.webService.Service;
import com.imourya.webService.Entity.Vehicle;
import com.imourya.webService.Data.Vehicles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
/**
 * Created by mourya on 5/13/17.
 * Service class to handle controller requests and access data methods
 */
@Service
public class VehiclesReportService {

    @Autowired
    @Qualifier("mysql")
    private Vehicles vehicle;

    public Collection<Vehicle> getAllVehicles()
    {
        return vehicle.getAllVehicles();
    }

    public Vehicle getVehicleById(int id)
    {
        return this.vehicle.getVehicleById(id);
    }

    public Collection <Vehicle> getVehiclesByPrice(int price)
    {
        return this.vehicle.getVehiclesByPrice(price);
    }

    public Collection <Vehicle> getVehiclesByType(String type)
    {
        return this.vehicle.getVehiclesByType(type);
    }

    public double getVehiclesAveragePriceByType(String type)
    {
        return this.vehicle.getVehiclesAveragePriceByType(type);
    }

    public Collection<Vehicle> getSortedByPrice(String attribute, String value)
    {
        return this.vehicle.getSortedByPrice(attribute,value).values();
    }

    public double getCarAveragePrice(String attribute,String value)
    {
        return this.vehicle.getCarAveragePrice(attribute,value);
    }

    public Collection<Vehicle> getCarsSortedByPrice()
    {
        return this.vehicle.getCarsSortedByPrice();
    }

}
