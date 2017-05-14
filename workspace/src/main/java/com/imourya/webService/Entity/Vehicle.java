package com.imourya.webService.Entity;

import java.util.HashMap;

/**
 * Created by mourya on 5/13/17.
 * Instance class to declare all the attributes of vehicles
 */

public class Vehicle {

    private int VIN;
    private String type;
    private String brand;
    private String color;
    private String engine_type;
    private int price;
    private int year;

    //constructor to define a vehicle instance
    public Vehicle(int VIN,String type,String brand, String color, String engine_type,int price,int year)
    {
        this.VIN = VIN;
        this.type = type;
        this.brand = brand;
        this.color = color;
        this.engine_type = engine_type;
        this.price = price;
        this.year = year;
    }

    //empty constructor
    public Vehicle()
    {

    }

    //getter methods for {VIN,price,year,type,brand,color,engine}
    public int getVehicleID()
    {
        return VIN;
    }

    public int getPrice()
    {
        return price;
    }

    public int getModelYear()
    {
        return year;
    }

    public String getVehicleType()
    {
        return type;
    }

    public String getVehicleBrand()
    {
        return brand;
    }

    public String getVehicleColor()
    {
        return color;
    }

    public String getEngineType()
    {
        return engine_type;
    }

    //setter methods for {VIN,price,year,type,brand,color,engine}
    public void setVehicleID(int id)
    {
        VIN = id;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public void setModelYear(int year)
    {
        this.year = year;
    }

    public void setVehicleType(String type)
    {
         this.type = type;
    }

    public void setVehicleBrand(String brand)
    {
        this.brand = brand;
    }

    public void setVehicleColor(String color)
    {
        this.color =  color;
    }

    public void setEngineType(String engine_type)
    {
        this.engine_type =  engine_type;
    }
}
