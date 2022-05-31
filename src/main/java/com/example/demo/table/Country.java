package com.example.demo.table;

import javafx.beans.property.SimpleStringProperty;

public class Country {
    private final SimpleStringProperty continent;
    private final SimpleStringProperty country;

    public Country(String continent, String country) {
        this.continent = new SimpleStringProperty(continent);
        this.country = new SimpleStringProperty(country);
    }

    public String getContinent() {
        return continent.get();
    }


    public void setContinent(String continent) {
        this.continent.set(continent);
    }

    public String getCountry() {
        return country.get();
    }


    public void setCountry(String country) {
        this.country.set(country);
    }
}
