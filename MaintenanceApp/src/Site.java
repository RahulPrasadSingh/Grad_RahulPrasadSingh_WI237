package MaintenanceApp.src;
import java.util.Scanner;

enum SiteType {
    VILLA,
    APARTMENT,
    INDEPENDENT_HOUSE,
    OPEN_SITE
}

public abstract class Site {

    private String siteNo;
    private double length;
    private double width;
    private boolean occupied;
    private SiteType type;
    private User ownerId;

    // Constructor for DAO usage (when reading from database)
    public Site(String siteNo, double length, double width, boolean occupied, SiteType type, User owner) {
        this.siteNo = siteNo;
        this.length = length;
        this.width = width;
        this.occupied = occupied;
        this.type = type;
        this.ownerId = owner;
    }

    // Constructor for interactive creation (when creating new sites)
    public Site(SiteType T, User owner) {
        Scanner sc = new Scanner(System.in);
        do{
           System.out.println("Enter SiteNo with S[VAIO][1-9]{4}:");
           this.siteNo = sc.next();
        } while(!siteNo.matches("^S[VAIO][1-9]{4}$"));
        
        System.out.println("Enter the length of the site:");
        this.length = sc.nextDouble();
        System.out.println("Enter the width of the site:");
        this.width = sc.nextDouble();
        System.out.println("If occupied enter Y/y:");
        char ch = sc.next().charAt(0);
        if(ch == 'Y' || ch == 'y'){
            this.occupied = true;
        }
        else{
            this.occupied = false;
        }
        this.type = T;
        this.ownerId = owner;
    }

    public boolean getOccupied(){
        return this.occupied;
    }

    public String getSiteNo(){
        return this.siteNo;
    }

    public double getLength(){
        return this.length;
    }

    public double getWidth(){
        return this.width;
    }

    public SiteType getSiteType(){
        return this.type;
    }

    public User getUser(){
        return this.ownerId;
    }

    public double getArea() {
        return length * width;
    }

    public abstract double getRatePerSqft();

    public final double calculateMaintenance() {
        return getArea() * getRatePerSqft();
    }

    public void setOwnerId(User owner){
        this.ownerId = owner;
    }
    
    public void setOccupied(boolean occupied){
        this.occupied = occupied;
    }

    public void setLength(double length){
        this.length = length;
    }

    public void setWidth(double width){
        this.width = width;
    }
}

final class Villa extends Site {
    // Constructor for interactive creation
    public Villa(User u) {
        super(SiteType.VILLA, u);
    }

    public Villa(){
        super(SiteType.VILLA, null);
    }

    // Constructor for DAO usage
    public Villa(String siteNo, double length, double width, boolean occupied, User owner) {
        super(siteNo, length, width, occupied, SiteType.VILLA, owner);
    }

    @Override
    public double getRatePerSqft() {
        return super.getOccupied() ? 9 : 6;
    }
}

final class Apartment extends Site {
    // Constructor for interactive creation
    public Apartment(User u) {
        super(SiteType.APARTMENT, u);
    }

    public Apartment(){
        super(SiteType.APARTMENT, null);
    }

    // Constructor for DAO usage
    public Apartment(String siteNo, double length, double width, boolean occupied, User owner) {
        super(siteNo, length, width, occupied, SiteType.APARTMENT, owner);
    }

    @Override
    public double getRatePerSqft() {
        return super.getOccupied() ? 9 : 6;
    }
}

final class IndependentHouse extends Site {
    // Constructor for interactive creation
    public IndependentHouse(User u) {
        super(SiteType.INDEPENDENT_HOUSE, u);
    }

    public IndependentHouse(){
        super(SiteType.INDEPENDENT_HOUSE, null);
    }

    // Constructor for DAO usage
    public IndependentHouse(String siteNo, double length, double width, boolean occupied, User owner) {
        super(siteNo, length, width, occupied, SiteType.INDEPENDENT_HOUSE, owner);
    }

    @Override
    public double getRatePerSqft() {
        return super.getOccupied() ? 9 : 6;
    }
}

final class OpenSite extends Site {
    // Constructor for interactive creation
    public OpenSite(User u) {
        super(SiteType.OPEN_SITE, u);
    }

    public OpenSite(){
        super(SiteType.OPEN_SITE, null);
    }

    // Constructor for DAO usage
    public OpenSite(String siteNo, double length, double width, boolean occupied, User owner) {
        super(siteNo, length, width, occupied, SiteType.OPEN_SITE, owner);
    }

    @Override
    public double getRatePerSqft() {
        return super.getOccupied() ? 9 : 6;
    }
}