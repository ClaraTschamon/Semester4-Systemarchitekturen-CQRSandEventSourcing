package at.fhv.cts.readside.queries;

public class GetCustomersQuery {
    private String name;

    public GetCustomersQuery() {}

    public GetCustomersQuery(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
