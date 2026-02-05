import java.util.List;
import java.util.Scanner;

public class AdminService {
    private UserDAO userDb;
    private SiteDAO siteDb;
    private MaintenanceDAO maintenanceDb;

    private static AdminService as = new AdminService();

    private AdminService() {
        userDb = new UserDAOImpl();
        siteDb = new SiteDAO();
        maintenanceDb = new MaintenanceDAO();
    }

    public static AdminService getInstance(User client) {
        return as;
    }

    // 1. Add Owner
    public void addOwner() {
        System.out.println("\n===== Add Owner =====");
        Owner owner = new Owner();
        userDb.add(owner);
    }

    // 2. Edit Owner
    public void editOwner(String ownerId) {
        System.out.println("\n===== Edit Owner =====");
        User user = userDb.getById(ownerId);
        
        if (user == null || user.getRole() != Role.OWNER) {
            System.out.println("Owner not found with ID: " + ownerId);
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new name (current: " + user.getName() + "):");
        String name = sc.nextLine().trim();
        
        System.out.println("Enter new password (min 6 characters):");
        String password = sc.nextLine().trim();

        if (password.length() >= 6) {
            user.setName(name);
            user.setPassword(password);
            userDb.update(user);
        } else {
            System.out.println("Password must be at least 6 characters");
        }
    }

    // 3. Delete Owner
    public void deleteOwner(String ownerId) {
        System.out.println("\n===== Delete Owner =====");
        User user = userDb.getById(ownerId);
        
        if (user == null || user.getRole() != Role.OWNER) {
            System.out.println("Owner not found with ID: " + ownerId);
            return;
        }

        // Check if owner has any sites assigned
        List<Site> allSites = siteDb.getAll();
        boolean hasSites = allSites.stream()
            .anyMatch(site -> site.getUser() != null && site.getUser().getUserId().equals(ownerId));

        if (hasSites) {
            System.out.println("Cannot delete owner. Please unassign all sites first.");
            return;
        }

        userDb.delete(ownerId);
    }

    // 4. Add Site
    public void addSite() {
        System.out.println("\n===== Add Site =====");
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Select Site Type:");
        System.out.println("1. Villa");
        System.out.println("2. Apartment");
        System.out.println("3. Independent House");
        System.out.println("4. Open Site");
        
        int choice = sc.nextInt();
        Site site;
        
        switch (choice) {
            case 1:
                site = new Villa();
                break;
            case 2:
                site = new Apartment();
                break;
            case 3:
                site = new IndependentHouse();
                break;
            case 4:
                site = new OpenSite();
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }
        
        siteDb.add(site);
        
        // Create maintenance record for the new site
        createMaintenanceRecord(site);
    }

    // 5. Assign Site to Owner
    public void assignSite(String siteId, String ownerId) {
        System.out.println("\n===== Assign Site =====");
        
        Site site = siteDb.getById(siteId);
        if (site == null) {
            System.out.println("Site not found");
            return;
        }

        User owner = userDb.getById(ownerId);
        if (owner == null || owner.getRole() != Role.OWNER) {
            System.out.println("Owner not found");
            return;
        }

        if (site.getUser() != null) {
            System.out.println("Site already assigned to: " + site.getUser().getUserId());
            return;
        }

        site.setOwnerId(owner);
        siteDb.update(site);
        System.out.println("Site " + siteId + " assigned to owner " + ownerId);
    }

    // 6. Unassign Site from Owner
    public void unassignSite(String siteId) {
        System.out.println("\n===== Unassign Site =====");
        
        Site site = siteDb.getById(siteId);
        if (site == null) {
            System.out.println("Site not found");
            return;
        }

        if (site.getUser() == null) {
            System.out.println("Site is not assigned to any owner");
            return;
        }

        // Check if there's pending maintenance
        Maintenance maintenance = getMaintenanceBySiteId(siteId);
        if (maintenance != null && maintenance.getBalance() > 0) {
            System.out.println("Cannot unassign site. Pending maintenance balance: " + maintenance.getBalance());
            return;
        }

        site.setOwnerId(null);
        siteDb.update(site);
        System.out.println("Site " + siteId + " unassigned successfully");
    }

    // 7. Update Site
    public void updateSite(String siteId) {
        System.out.println("\n===== Update Site =====");
        
        Site site = siteDb.getById(siteId);
        if (site == null) {
            System.out.println("Site not found");
            return;
        }

        Scanner sc = new Scanner(System.in);
        
        System.out.println("Update length? (current: " + site.getLength() + ") [Y/N]:");
        if (sc.next().equalsIgnoreCase("Y")) {
            System.out.println("Enter new length:");
            site.setLength(sc.nextDouble());
        }

        System.out.println("Update width? (current: " + site.getWidth() + ") [Y/N]:");
        if (sc.next().equalsIgnoreCase("Y")) {
            System.out.println("Enter new width:");
            site.setWidth(sc.nextDouble());
        }

        System.out.println("Update occupied status? (current: " + site.getOccupied() + ") [Y/N]:");
        if (sc.next().equalsIgnoreCase("Y")) {
            System.out.println("Is occupied? [Y/N]:");
            site.setOccupied(sc.next().equalsIgnoreCase("Y"));
        }

        siteDb.update(site);
        
        // Recalculate maintenance if dimensions or occupancy changed
        updateMaintenanceAmount(site);
    }

    // 8. Delete Site
    public void deleteSite(String siteId) {
        System.out.println("\n===== Delete Site =====");
        
        Site site = siteDb.getById(siteId);
        if (site == null) {
            System.out.println("Site not found");
            return;
        }

        // Check if site has pending maintenance
        Maintenance maintenance = getMaintenanceBySiteId(siteId);
        if (maintenance != null && maintenance.getBalance() > 0) {
            System.out.println("Cannot delete site. Clear pending maintenance first.");
            return;
        }

        // Delete associated maintenance records
        if (maintenance != null) {
            maintenanceDb.delete(maintenance.getMaintenanceId());
        }

        siteDb.delete(siteId);
    }

    // 9. View Pending Payment Requests
    public void viewPendingRequests() {
        System.out.println("\n===== Pending Payment Requests =====");
        
        List<Maintenance> allMaintenance = maintenanceDb.getAll();
        boolean found = false;

        for (Maintenance m : allMaintenance) {
            if (m.isRequest() && !m.isPaid()) {
                found = true;
                System.out.println("\nMaintenance ID: " + m.getMaintenanceId());
                System.out.println("Site ID: " + m.getSiteId());
                System.out.println("Total Amount: " + m.getAmount());
                System.out.println("Balance: " + m.getBalance());
                System.out.println("Status: Pending Approval");
                System.out.println("--------------------");
            }
        }

        if (!found) {
            System.out.println("No pending payment requests");
        }
    }

    // 10. Approve/Reject Payment Request
    public void approvePaymentRequest(String maintenanceId, double paidAmount, boolean approve) {
        System.out.println("\n===== Process Payment Request =====");
        
        Maintenance maintenance = maintenanceDb.getById(maintenanceId);
        if (maintenance == null) {
            System.out.println("Maintenance record not found");
            return;
        }

        if (!maintenance.isRequest()) {
            System.out.println("No pending request for this maintenance");
            return;
        }

        if (approve) {
            if (paidAmount <= 0 || paidAmount > maintenance.getBalance()) {
                System.out.println("Invalid payment amount. Balance: " + maintenance.getBalance());
                return;
            }

            // Update balance
            maintenance.updateBalance(paidAmount);
            
            // If fully paid, mark as paid
            if (maintenance.getBalance() == 0) {
                maintenance = new Maintenance(
                    maintenance.getMaintenanceId(),
                    maintenance.getSiteId(),
                    maintenance.getAmount(),
                    0,
                    true,
                    false,
                    maintenance.getCollectedBy(),
                    maintenance.getCollectedOn()
                );
                System.out.println("Payment approved. Maintenance fully paid!");
            } else {
                maintenance = new Maintenance(
                    maintenance.getMaintenanceId(),
                    maintenance.getSiteId(),
                    maintenance.getAmount(),
                    maintenance.getBalance(),
                    false,
                    false,
                    maintenance.getCollectedBy(),
                    maintenance.getCollectedOn()
                );
                System.out.println("Payment approved. Remaining balance: " + maintenance.getBalance());
            }
            
            maintenanceDb.update(maintenance);
        } else {
            // Reject - reset request flag
            maintenance = new Maintenance(
                maintenance.getMaintenanceId(),
                maintenance.getSiteId(),
                maintenance.getAmount(),
                maintenance.getBalance(),
                maintenance.isPaid(),
                false,
                maintenance.getCollectedBy(),
                maintenance.getCollectedOn()
            );
            maintenanceDb.update(maintenance);
            System.out.println("Payment request rejected");
        }
    }

    // 11. Collect Maintenance (Admin initiates maintenance collection)
    public void collectMaintenance(String maintenanceId, String siteId, String adminId) {
        System.out.println("\n===== Collect Maintenance =====");
        
        // Verify admin
        User admin = userDb.getById(adminId);
        if (admin == null || admin.getRole() != Role.ADMIN) {
            System.out.println("Invalid admin ID");
            return;
        }

        // Verify site
        Site site = siteDb.getById(siteId);
        if (site == null) {
            System.out.println("Site not found");
            return;
        }

        Maintenance maintenance = maintenanceDb.getById(maintenanceId);
        if (maintenance == null) {
            System.out.println("Maintenance record not found");
            return;
        }

        if (maintenance.isPaid()) {
            System.out.println("Maintenance already paid");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Current balance: " + maintenance.getBalance());
        System.out.println("Enter amount collected:");
        double amount = sc.nextDouble();

        if (amount <= 0 || amount > maintenance.getBalance()) {
            System.out.println("Invalid amount");
            return;
        }

        maintenance.updateBalance(amount);
        
        Maintenance updated = new Maintenance(
            maintenance.getMaintenanceId(),
            maintenance.getSiteId(),
            maintenance.getAmount(),
            maintenance.getBalance(),
            maintenance.getBalance() == 0,
            false,
            adminId,
            new java.sql.Date(System.currentTimeMillis())
        );

        maintenanceDb.update(updated);
        
        if (updated.isPaid()) {
            System.out.println("Maintenance collected successfully. Fully paid!");
        } else {
            System.out.println("Partial payment collected. Remaining: " + updated.getBalance());
        }
    }

    // Helper: Create maintenance record for a new site
    private void createMaintenanceRecord(Site site) {
        String maintenanceId = "M" + site.getSiteNo().substring(1);
        double amount = MaintenanceCalculator.calculateAmount(site);
        
        Maintenance maintenance = new Maintenance(
            maintenanceId,
            site.getSiteNo(),
            amount,
            amount,
            false,
            false,
            null,
            null
        );
        
        maintenanceDb.add(maintenance);
        System.out.println("Maintenance record created with amount: " + amount);
    }

    // Helper: Update maintenance amount when site details change
    private void updateMaintenanceAmount(Site site) {
        Maintenance maintenance = getMaintenanceBySiteId(site.getSiteNo());
        if (maintenance != null) {
            double newAmount = MaintenanceCalculator.calculateAmount(site);
            
            Maintenance updated = new Maintenance(
                maintenance.getMaintenanceId(),
                maintenance.getSiteId(),
                newAmount,
                newAmount,
                false,
                false,
                null,
                null
            );
            
            maintenanceDb.update(updated);
            System.out.println("Maintenance amount updated to: " + newAmount);
        }
    }

    // Helper: Get maintenance by site ID
    private Maintenance getMaintenanceBySiteId(String siteId) {
        List<Maintenance> allMaintenance = maintenanceDb.getAll();
        for (Maintenance m : allMaintenance) {
            if (m.getSiteId().equals(siteId)) {
                return m;
            }
        }
        return null;
    }

    // View all owners
    public void viewAllOwners() {
        System.out.println("\n===== All Owners =====");
        List<User> allUsers = userDb.getAll();
        for (User u : allUsers) {
            if (u.getRole() == Role.OWNER) {
                System.out.println(u);
                System.out.println("--------------------");
            }
        }
    }

    // View all sites
    public void viewAllSites() {
        System.out.println("\n===== All Sites =====");
        siteDb.getAll();
    }

    // View all maintenance records
    public void viewAllMaintenance() {
        System.out.println("\n===== All Maintenance Records =====");
        List<Maintenance> allMaintenance = maintenanceDb.getAll();
        for (Maintenance m : allMaintenance) {
            System.out.println("Maintenance ID: " + m.getMaintenanceId());
            System.out.println("Site ID: " + m.getSiteId());
            System.out.println("Amount: " + m.getAmount());
            System.out.println("Balance: " + m.getBalance());
            System.out.println("Paid: " + m.isPaid());
            System.out.println("Requested: " + m.isRequest());
            System.out.println("--------------------");
        }
    }
}