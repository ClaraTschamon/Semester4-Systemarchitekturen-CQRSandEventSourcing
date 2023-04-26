package share.commands;

import java.time.LocalDate;

public class CreateCustomerCommand {
    private String name;
    private String address;
    private LocalDate birthdate;

    public CreateCustomerCommand () {

    }

    public CreateCustomerCommand (String name, String address, LocalDate birthdate) {
        this.name = name;
        this.address = address;
        this.birthdate = birthdate;
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
}
