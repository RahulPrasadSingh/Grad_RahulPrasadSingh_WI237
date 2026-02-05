public class Maintenance {

    private String maintenanceId;
    private String siteId;
    private double amount;
    private double balance;
    private boolean paid;
    private boolean requested;
    private String collectedBy;
    private java.sql.Date collectedOn;

    public Maintenance(String maintenanceId, String siteId, double amount,double balance,
                       boolean paid, boolean requested,String collectedBy, java.sql.Date collectedOn) {
        this.maintenanceId = maintenanceId;
        this.siteId = siteId;
        this.amount = amount;
        this.balance=balance;
        this.paid = paid;
        this.requested=requested;
        this.collectedBy = collectedBy;
        this.collectedOn = collectedOn;
    }

    public String getMaintenanceId() { return maintenanceId; }
    public String getSiteId() { return siteId; }
    public double getAmount() { return amount; }
    public double getBalance() {return balance;}
    public boolean isPaid() { return paid; }
    public boolean isRequest(){return requested;}
    public String getCollectedBy() { return collectedBy; }
    public java.sql.Date getCollectedOn() { return collectedOn; }

    public void updateBalance(double amt){this.balance-=amt;}
}
