package it.polito.po.utility;

import java.rmi.server.UID;
import java.security.Provider.Service;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents the facade class for the utility company.
 */
public class Utility{

    /**
     * Defines a new service point.
     *
     * @param municipality the municipality of the service point
     * @param address      the address of the service point
     * @param lat          the latitude of the service point
     * @param lon          the longitude of the service point
     * @return the id of the service point
     */
    Map<String, ServicePoint> servicesMap = new HashMap<>();
    int nID = 0;
    public String defineServicePoint(String municipality, String address, double lat, double lon) {
        String id = "SP" + nID;
        Point newPoint = new Point(lon, lat);

        ServicePoint newServ = new Servicepoint(municipality, address, lat, lon, id, newPoint, null);
        servicesMap.put(id, newServ);
        nID ++;
        int oldID = nID -1;
        String retString = "SP" + oldID;
        return retString;
    }

    /**
     * Returns the list of service points.
     *
     * @return the list of service points
     */
    public Collection<String> getServicePoints() {
        return servicesMap.keySet();
    }

    /**
     * Returns the service point with the given id.
     *
     * @param spId the id of the service point
     * @return the service point with the given id
     */
    public ServicePoint getServicePoint(String spId) {
        return servicesMap.get(spId);
    }

    /**
     * Adds a new meter to the utility company.
     *
     * @param sn    serial number of the meter
     * @param brand brand of the meter
     * @param model model of the meter
     * @param unit  unit of measure
     * @return the assigned unique id of the meter
     */
    int mID = 0;
    Map<String, Meter> metersMap = new HashMap<>();
    public String addMeter(String sn, String brand, String model, String unit) {
        String id = "MT" + mID;
        Meter newMeter = new Meters(sn, brand, model, unit, id);
        metersMap.put(id, newMeter);
        mID++;
        int oldID = mID -1;
        String retString = "MT" + oldID;
        return retString;
    
    }

    /**
     * Connects a meter to a service point.
     *
     * @param spId    the id of the service point
     * @param meterId the id of the meter
     */
    public void installMeter(String spId, String meterId) {
        Meter meter = metersMap.get(meterId);
        Optional<Meter> met = Optional.ofNullable(meter);
        ServicePoint serv = servicesMap.get(spId);
        ServicePoint newServ = serv;((Servicepoint)serv).setMeter(met);
        servicesMap.put(spId, newServ);

        
    }

    /**
     * Returns the meter with the given id.
     *
     * @param mid the id of the meter
     * @return the meter with the given id
     */
    public Meter getMeter(String mid) {
        return metersMap.get(mid);
    }

    //----
    // R2 User and contracts

    /**
     * Adds a new user to the utility company.
     *
     * @param ssn      the social security number of the user
     * @param name    the name of the user
     * @param surname the surname of the user
     * @param address the address of the user
     * @param email   the email of the user
     * @return the id of the user
     */
    public String addUser(String ssn, String name, String surname, String address, String email) {
        String id = "U" + uID;
        String businessName = name + " "+surname;
        User newUser = new Users(ssn, businessName, address, email, id);
        userMap.put(id, newUser);
        uID++;
        int oldID = uID-1;
        String reString = "U" + oldID;
        return reString;
    }

    /**
     * Adds a new business user to the utility company.
     *
     * @param ssn           the social security number or tax code of the user
     * @param businessName the name of the business
     * @param address      the address of the business
     * @param email        the email of the business
     * @return the id of the user
     */
    Map<String, User> userMap = new HashMap<>();
    int uID = 0;
    public String addUser(String ssn, String businessName, String address, String email) {
        String id = "U" + uID;
        User newUser = new Users(ssn, businessName, address, email, id);
        userMap.put(id, newUser);
        uID++;
        int oldID = uID-1;
        String reString = "U" + oldID;
        return reString;
    }

    /**
     * Returns the user with the given id.
     *
     * @param uid the id of the user
     * @return the user with the given id
     */
    public User getUser(String uid) {
        return userMap.get(uid);
    }

    /**
     * Returns all users
     *
     * @return a collection of users' id
     */
    public Collection<String> getUsers() {
        return userMap.keySet();
    }

    /**
     * Signs a new contract with a user that is provided through a service point.
     *
     * @param user the id of the user
     * @param pdp  the id of the service point
     * @return the id of the contract
     */
    Map<String, Contract> contractsMap = new HashMap<>();
    int cID = 0;
    public String signContract(String user, String pdp) throws UtilityException {
        if(!userMap.containsKey(user)){
            throw new UtilityException("Ut non esiste");
        }
        ServicePoint s=servicesMap.get(pdp);if(s==null||!s.getMeter().isPresent()){
            throw new UtilityException("Punto di presa non esistente");
        }
        User userr = userMap.get(user);
        ServicePoint serv = servicesMap.get(pdp);
        String id = "C" + cID;
        Contract newContract = new Contracts(userr, pdp, serv, id);
        contractsMap.put(id, newContract);
        cID++;
        int oldID = cID-1;
        String reString = "C" + oldID;
        return reString;
        
    }

    /**
     * Returns the contract with the given id.
     *
     * @param contractId the id of the contract
     * @return the contract with the given id
     */
    public Contract getContract(String contractId) {
        return contractsMap.get(contractId);
    }

    //----
    // R3 Reading

    /**
     * Adds a new reading for a given meter.
     *
     * @param contractId
     * @param meterId
     * @param date
     * @param value
     * @throws UtilityException if the contract and meter do not match
     */
    public void addReading(String contractId, String meterId, String date, double value) throws UtilityException {
        if(!contractsMap.containsKey(contractId)){
            throw  new UtilityException("contratto no");
        }
        if(!metersMap.containsKey(meterId)||((Contracts)contractsMap.get(contractId)).a(meterId,date,value)){
            throw  new UtilityException("metro no");
        }
        Contract contra = contractsMap.get(contractId);
        Reading newReading = new Reading(meterId, date, value);
        contra.addReading(newReading);
    
}

    /**
     * Adds a new reading for a given meter.
     *
     * @param contractId id of the contract
     * @return a map that links dates and metering values
     */      
    public Map<String,Double> getReadings(String contractId) {
        return ((Contracts)contractsMap.get(contractId)).t;
    }

    /**
     * Read latest reading
     * 
     * @param contractId id of the contract
     * @return a metering value 
     */
    public double getLatestReading(String contractId) {
        return ((Contracts)contractsMap.get(contractId)).t.lastEntry().getValue();
    }

    //----
    // R4 Tariffe

    /**
     * Computes the estimated reading for a given contract and date.
     * The estimated reading is computed as the linear interpolation of the latest two readings.
     *
     * @param contractId the id of the contract
     * @param date       the date for which the reading is estimated
     * @return the estimated reading
     * @throws UtilityException if estimation cannot be computed
     */
    public double getEstimatedReading(String contractId, String date) throws UtilityException {
        Double r=((Contracts)contractsMap.get(contractId)).e(date);if(r==null)throw new UtilityException("");return r;
    }

    /**
     * Computes the consumption between two dates
     * 
     * @param contractId    the id of the contract
     * @param dateInitial   the initial date
     * @param dateFinal     the final date
     * @return  the total consumption between the two dates
     * @throws UtilityException if the contract id is not valid or a reading cannot be estimated for the dates
     */
    public double getConsumption(String contractId, String dateInitial, String dateFinal) throws UtilityException {
        return ((Contracts)contractsMap.get(contractId)).c(dateInitial,dateFinal);
    }

        /**
     * Returns the consumption breakdown (month by month) 
     * 
     * @param contractId    id of the contrac
     * @param monthStart    initial month
     * @param monthEnd      final month
     * @param year          year of reference
     * @return the breakdown
     * @throws UtilityException in case contract is not valid, or it is not possible to get reading estimates
     */
    public List<String> getBillBreakdown(String contractId, int monthStart, int monthEnd, int year) throws UtilityException {
        return ((Contracts)contractsMap.get(contractId)).b(year,monthStart,monthEnd);
    }

}
