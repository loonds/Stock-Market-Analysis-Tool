package com.loonds;

import java.time.LocalDate;

public class Stock{
    private String name;
    private double price;
    private  int volume;
    private LocalDate date;


    public Stock(String name, double price, int volume,LocalDate date   ) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.volume = volume;
    }

    // Getters, Setters, toString method
    public String getName() { return name; }
    public double getPrice() { return price; }
    public LocalDate getDate() { return date; }
    public int getVolume() { return volume; }

    @Override
    public String toString() {
        return "Stock{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", volume=" + volume +
                '}';
    }
}