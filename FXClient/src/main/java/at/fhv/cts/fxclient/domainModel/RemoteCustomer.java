package at.fhv.cts.fxclient.domainModel;

import java.util.UUID;

public class RemoteCustomer {
    private UUID id;
    private String name;
    private String address;
    private String dateOfBirth;

    public RemoteCustomer() {
        super();
    }  //necessary for jackson databind library

    public RemoteCustomer(UUID id, String name, String address, String dateOfBirth) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }

    //getter and setter needed for fx tableview

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return  "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'';
    }
}
