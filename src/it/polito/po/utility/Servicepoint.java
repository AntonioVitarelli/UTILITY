package it.polito.po.utility;

import java.util.LinkedList;
import java.util.Optional;
import java.util.*;

public class Servicepoint implements ServicePoint {
    String municipality,ì;
    String address;
    double lat;
    double lon;
    String id;
    Point point;
    Optional<Meter> meter;
    public Servicepoint(String municipality, String address, double lat, double lon, String id, Point point, Optional<Meter> meter){
        this.municipality = municipality;
        this.address = address;
        this.lat = lat;
        this.lon= lon;
        this.id = id;
        this.point = point;
        if(meter == null){
            setMeter(null);
        } else {
            setMeter(meter);
        }
       
    }
    @Override
    public String getId(){
        return id;
    }
  
    @Override
    public String getMunicipality() {
        return municipality;
    }
    @Override
    public String getAddress() {
        return address;
    }
    @Override
    public Point getPosition() {
        return point;
    }
    @Override
    public Optional<Meter> getMeter() {
        if(meter.isPresent()){
            return meter;
        }
        return meter;
    }
   
    public void setMeter(Optional<Meter> meter) {
        this.meter = meter==null?Optional.empty():meter.map(m->((Meters)m).provide(this));
    }

}
