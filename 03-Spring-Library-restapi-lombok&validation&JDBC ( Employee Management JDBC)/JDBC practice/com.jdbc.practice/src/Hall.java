public class Hall {

    private String name;
    private String contactDetail;
    private String owner;
    private long id;
    private double costPerDay;

    // Parameterized constructor
    public Hall( long id, String name, String contactDetail, double costPerDay ,  String owner) {
        this.name = name;
        this.contactDetail = contactDetail;
        this.owner = owner;
        this.id = id;
        this.costPerDay = costPerDay;
    }

    public Hall(String name, String contactDetail,  double costPerDay , String owner) {
        this.name = name;
        this.contactDetail = contactDetail;
        this.owner = owner;
        this.costPerDay = costPerDay;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(String contactDetail) {
        this.contactDetail = contactDetail;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(double costPerDay) {
        this.costPerDay = costPerDay;
    }
}
