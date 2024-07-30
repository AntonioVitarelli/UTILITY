package it.polito.po.utility;

import java.util.Optional;

public class Meters implements Meter{
    String sn;
    String brand;
    String model;
    String unit;
    String id;

    public Meters(String sn, String brand, String model, String unit, String id) {
        this.sn = sn;
        this.brand = brand;
        this.model = model;
        this.unit = unit;
        this.id = id;
    }

    @Override
    public String getSN() {
        return sn;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public String getUnit() {
        return unit;
    }

    @Override
    public String getId() {
       return id;
    }

ServicePoint s=null;Meters provide(ServicePoint s){this.s=s;return this;}
    @Override
    public Optional<ServicePoint> getServicePoint() {
        // TODO Auto-generated method stub
        return Optional.ofNullable(s);//throw new UnsupportedOperationException("Unimplemented method 'getServicePoint'");
    }

   

   

}
