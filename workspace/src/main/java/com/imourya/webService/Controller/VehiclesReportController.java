package com.imourya.webService.Controller;

import com.imourya.webService.Entity.Vehicle;
import com.imourya.webService.Service.VehiclesReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by mourya on 5/13/17.
 * Controller class to handle http request and forward request to service
 */
@RestController
@RequestMapping("/vehicles")
public class VehiclesReportController {

    //Create vehicleReportService class object
    @Autowired
    private VehiclesReportService vehicleReports;

    //Get all vehicles
    @RequestMapping(method = RequestMethod.GET)
    public Collection<Vehicle> getAllVehicles(){
        return vehicleReports.getAllVehicles();
    }

    //Get vehicles by ID
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Vehicle getVehicleById(@PathVariable("id") int id)
    {
        return vehicleReports.getVehicleById(id);
    }

    //Get vehicles by price
    @RequestMapping(value="/price/{price}",method = RequestMethod.GET)
    public Collection<Vehicle> getVehiclesByPrice(@PathVariable("price") int price)
    {
        return vehicleReports.getVehiclesByPrice(price);
    }

    //Get vehicles by price
    @RequestMapping(value = "/type/{type}", method = RequestMethod.GET)
    public Collection<Vehicle> getVehicleByType(@PathVariable("type") String type)
    {
        return vehicleReports.getVehiclesByType(type);
    }

    //Get average price of vehicles by type
    @RequestMapping(value = "/average/type/{type}", method = RequestMethod.GET)
    public double getVehicleAveragePriceByType(@PathVariable("type") String type)
    {
        return vehicleReports.getVehiclesAveragePriceByType(type);
    }

    //Sort Cars based on attribute and Values {brand,color,engine}
    @RequestMapping(value = "/Car/sort/price/{attribute}/{value}", method = RequestMethod.GET)
    public Collection<Vehicle> getSortedByPrice(@PathVariable("attribute") String attribute , @PathVariable("value") String value)
    {
        return vehicleReports.getSortedByPrice(attribute,value);
    }

    //Sort Cars based on price
    @RequestMapping(value = "/Car/sort/price", method = RequestMethod.GET)
    public Collection<Vehicle> getVehicleSortedByPrice()
    {
        return vehicleReports.getCarsSortedByPrice();
    }

    //Sort Cars based on price and calculate average
    @RequestMapping(value = "/Car/sort/price/average", method = RequestMethod.GET)
    public double getVehicleAveragePriceByType()
    {
        return vehicleReports.getVehiclesAveragePriceByType("Car");
    }

    //Sort Cars based on attribute and Values {brand,color,engine} and calculate average for the given attribute
    @RequestMapping(value = "/Car/sort/price/{attribute}/{value}/average", method = RequestMethod.GET)
    public double getVehicleAveragePrice(@PathVariable("attribute") String attribute , @PathVariable("value") String value)
    {
        return vehicleReports.getCarAveragePrice(attribute,value);
    }
}