package it.polito.po.utility;

import java.util.*;
import java.util.stream.*;
public class Contracts implements Contract {
    String pdp;
    User user;
    ServicePoint servPoint;
    String id,S="%02d-%02d-%02d";TreeMap<String,Double>t=new TreeMap<>();boolean a(String m,String d,double v){return servPoint.getMeter().map(n->!n.getId().equals(m)||t.put(d,v)!=null).orElse(true);}long p(String d){return java.time.LocalDate.parse(d).toEpochDay();}Double e(String d){String x=t.floorKey(d),y=t.ceilingKey(d);if(x==null)return null;Double w=t.get(x);if(y==null){y=x;x=t.lowerKey(y);w=x==null?null:t.get(x);if(x==null)return null;}return x.equals(y)?w:w+(p(d)-p(x))*((t.get(y)-w)/(p(y)-p(x)));}double c(String i,String f){return e(f)-e(i);}List<String>b(int y,int i,int f){class D{String w,v;Double x,y,z;}return IntStream.range(i,f+1).mapToObj(m->{D d=new D();d.w=S.formatted(y,m,1);d.v=S.formatted(y,m+1,1);d.x=e(d.w);d.y=e(d.v);d.z=c(d.w,d.v);return d;}).map(d->d.w+".."+d.v+":"+d.x+" -> "+d.y+" = "+d.z).collect(Collectors.toList());}
    List<Reading> readings = new LinkedList<>();
    public Contracts(User user, String pdp, ServicePoint servPoint, String id) {
        this.user = user;
        this.pdp = pdp;
        this.servPoint = servPoint;
        this.id = id;
    }
    @Override
    public void addReading(Reading reading){
        readings.add(reading);
    }
    @Override
    public String getId() {
        return id;
    }
    @Override
    public User getUser() {
        return user;
    }
    @Override
    public ServicePoint getServicePoint() {
        return servPoint;
    }

}
