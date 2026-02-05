import java.util.List;

public class OwnerService {
    private UserDAO userDb;
    private SiteDAO siteDb;
    private MaintenanceDAO maintenanceDb;

    private static OwnerService os = new OwnerService();

    private OwnerService() {
        userDb = new UserDAOImpl();
        siteDb = new SiteDAO();
        maintenanceDb = new MaintenanceDAO();
    }

    public static OwnerService getInstance(User client) {
        return os;
    }

    // View owner's site details
    public void viewMySite(String siteId, String ownerId) {
        System.out.println("\n===== My Site Details =====");
        
        Site site = siteDb.getById(siteId);
        if (site == null) {
            System.out.println("Site not found");
            return;
        }

        // Verify ownership
        if (site.getUser() == null || !site.getUser().getUserId().equals(ownerId)) {
            System.out.println("You are not the owner of this site");
            return;
        }

        // Display site details
        System.out.println("Site No: " + site.getSiteNo());
        System.out.println("Type: " + site.getSiteType());
        System.out.println("Length: " + site.getLength() + " ft");
        System.out.println("Width: " + site.getWidth() + " ft");
        System.out.println("Area: " + site.getArea() + " sq ft");
        System.out.println("Occupied: " + site.getOccupied());
        System.out.println("Rate per sq ft: " + site.getRatePerSqft());
        System.out.println("Monthly Maintenance: " + site.calculateMaintenance());

        // Display maintenance status
        Maintenance maintenance = getMaintenanceBySiteId(siteId);
        if (maintenance != null) {
            System.out.println("\n--- Maintenance Status ---");
            System.out.println("Total Amount: " + maintenance.getAmount());
            System.out.println("Balance: " + maintenance.getBalance());
            System.out.println("Paid: " + (maintenance.isPaid() ? "Yes" : "No"));
            System.out.println("Pending Request: " + (maintenance.isRequest() ? "Yes" : "No"));
        }
    }

    // Request payment confirmation after owner pays
    public void requestPaymentConfirmation(String ownerId, String siteId, double paidAmount) {
        System.out.println("\n===== Request Payment Confirmation =====");
        
        // Verify site ownership
        Site site = siteDb.getById(siteId);
        if (site == null) {
            System.out.println("Site not found");
            return;
        }

        if (site.getUser() == null || !site.getUser().getUserId().equals(ownerId)) {
            System.out.println("You are not the owner of this site");
            return;
        }

        // Get maintenance record
        Maintenance maintenance = getMaintenanceBySiteId(siteId);
        if (maintenance == null) {
            System.out.println("No maintenance record found for this site");
            return;
        }

        if (maintenance.isPaid()) {
            System.out.println("Maintenance already fully paid");
            return;
        }

        if (maintenance.isRequest()) {
            System.out.println("Payment request already pending");
            return;
        }

        if (paidAmount <= 0 || paidAmount > maintenance.getBalance()) {
            System.out.println("Invalid payment amount. Current balance: " + maintenance.getBalance());
            return;
        }

        // Create payment request
        Maintenance updated = new Maintenance(
            maintenance.getMaintenanceId(),
            maintenance.getSiteId(),
            maintenance.getAmount(),
            maintenance.getBalance(),
            false,
            true, // Mark as requested
            maintenance.getCollectedBy(),
            maintenance.getCollectedOn()
        );

        maintenanceDb.update(updated);
        System.out.println("Payment confirmation request submitted successfully!");
        System.out.println("Amount claimed: " + paidAmount);
        System.out.println("Please wait for admin approval.");
    }

    // View all sites owned by this owner
    public void viewMySites(String ownerId) {
        System.out.println("\n===== My Sites =====");
        
        List<Site> allSites = siteDb.getAll();
        boolean found = false;

        for (Site site : allSites) {
            if (site.getUser() != null && site.getUser().getUserId().equals(ownerId)) {
                found = true;
                System.out.println("\nSite No: " + site.getSiteNo());
                System.out.println("Type: " + site.getSiteType());
                System.out.println("Area: " + site.getArea() + " sq ft");
                System.out.println("Occupied: " + site.getOccupied());
                
                // Show maintenance status
                Maintenance m = getMaintenanceBySiteId(site.getSiteNo());
                if (m != null) {
                    System.out.println("Maintenance Balance: " + m.getBalance());
                    System.out.println("Status: " + (m.isPaid() ? "Paid" : "Pending"));
                }
                System.out.println("--------------------");
            }
        }

        if (!found) {
            System.out.println("No sites assigned to you");
        }
    }

    // View payment history for a site
    public void viewPaymentHistory(String siteId, String ownerId) {
        System.out.println("\n===== Payment History =====");
        
        Site site = siteDb.getById(siteId);
        if (site == null) {
            System.out.println("Site not found");
            return;
        }

        if (site.getUser() == null || !site.getUser().getUserId().equals(ownerId)) {
            System.out.println("You are not the owner of this site");
            return;
        }

        Maintenance maintenance = getMaintenanceBySiteId(siteId);
        if (maintenance == null) {
            System.out.println("No maintenance record found");
            return;
        }

        System.out.println("Maintenance ID: " + maintenance.getMaintenanceId());
        System.out.println("Total Amount: " + maintenance.getAmount());
        System.out.println("Amount Paid: " + (maintenance.getAmount() - maintenance.getBalance()));
        System.out.println("Balance: " + maintenance.getBalance());
        System.out.println("Fully Paid: " + (maintenance.isPaid() ? "Yes" : "No"));
        
        if (maintenance.getCollectedBy() != null) {
            System.out.println("Last Collected By: " + maintenance.getCollectedBy());
            System.out.println("Last Collection Date: " + maintenance.getCollectedOn());
        }
    }

    // Helper method to get maintenance by site ID
    private Maintenance getMaintenanceBySiteId(String siteId) {
        List<Maintenance> allMaintenance = maintenanceDb.getAll();
        for (Maintenance m : allMaintenance) {
            if (m.getSiteId().equals(siteId)) {
                return m;
            }
        }
        return null;
    }
}