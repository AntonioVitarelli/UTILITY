package it.polito.po.utility;

public class Users implements User{
    String ssn;
    String businessName;
    String address;
    String email;
    String id;
    String name; 
    String surname;
    
    public Users(String ssn, String businessName, String address, String email, String id) {
        this.ssn = ssn;
        this.businessName = businessName;
        this.address = address;
        this.email = email;
        this.id = id;
    
    }
    @Override
    public Type getType() {
        try{
            if(ssn.startsWith("PIVA")){Integer id = Integer.parseInt(ssn);}
        } catch (NumberFormatException e){
            return Type.BUSINESS;
        }
        return Type.RESIDENTIAL;
    
    }
    @Override
    public String getId() {
        return id;
    }
    @Override
    public String getCF() {
        return ssn;
    }
    @Override
    public String getName() {
        if(getType() == Type.BUSINESS){
            return businessName;
        } else {
            String parts[] = businessName.split(" ");
            return parts[0].trim();
    
        }
    }
    @Override
    public String getSurname() {
        if(getType() == Type.BUSINESS){
            return null;
        } else {
            String parts[] = businessName.split(" ");
            return parts[1].trim();
            
        }

        
    }
    @Override
    public String getAddress() {
        return address;
    }
    @Override
    public String getEmail() {
        return email;
    }
     
}
