package MaintenanceApp.src;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n========== Site Maintenance Management System ==========");
            System.out.println("1. Admin Login");
            System.out.println("2. Owner Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> handleAdminLogin(sc);
                case 2 -> handleOwnerLogin(sc);
                case 3 -> {
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void handleAdminLogin(Scanner sc) {
        System.out.print("\nEnter Admin ID: ");
        String adminId = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        Authentication auth = AdminAuth.getInstance();
        User admin = auth.validate(adminId, password);

        if (admin == null) {
            System.out.println("Invalid credentials or not an admin!");
            return;
        }

        AdminService adminService = AdminService.getInstance(admin);
        adminMenu(sc, adminService, admin);
    }

    private static void handleOwnerLogin(Scanner sc) {
        System.out.print("\nEnter Owner ID: ");
        String ownerId = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        Authentication auth = OwnerAuth.getInstance();
        User owner = auth.validate(ownerId, password);

        if (owner == null) {
            System.out.println("Invalid credentials or not an owner!");
            return;
        }

        OwnerService ownerService = OwnerService.getInstance(owner);
        ownerMenu(sc, ownerService, owner);
    }

    private static void adminMenu(Scanner sc, AdminService adminService, User admin) {
        while (true) {
            System.out.println("\n========== Admin Menu ==========");
            System.out.println("1. Add Owner");
            System.out.println("2. Edit Owner");
            System.out.println("3. Delete Owner");
            System.out.println("4. View All Owners");
            System.out.println("5. Add Site");
            System.out.println("6. Assign Site to Owner");
            System.out.println("7. Unassign Site from Owner");
            System.out.println("8. Update Site");
            System.out.println("9. Delete Site");
            System.out.println("10. View All Sites");
            System.out.println("11. View Pending Payment Requests");
            System.out.println("12. Approve/Reject Payment Request");
            System.out.println("13. Collect Maintenance");
            System.out.println("14. View All Maintenance Records");
            System.out.println("15. Logout");
            System.out.print("Enter choice: ");

            int adminChoice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (adminChoice) {
                case 1 -> adminService.addOwner();
                
                case 2 -> {
                    System.out.print("Enter Owner ID to edit: ");
                    String ownerId = sc.nextLine();
                    adminService.editOwner(ownerId);
                }
                
                case 3 -> {
                    System.out.print("Enter Owner ID to delete: ");
                    String ownerId = sc.nextLine();
                    adminService.deleteOwner(ownerId);
                }
                
                case 4 -> adminService.viewAllOwners();
                
                case 5 -> adminService.addSite();
                
                case 6 -> {
                    System.out.print("Enter Site ID: ");
                    String siteId = sc.nextLine();
                    System.out.print("Enter Owner ID: ");
                    String ownerId = sc.nextLine();
                    adminService.assignSite(siteId, ownerId);
                }
                
                case 7 -> {
                    System.out.print("Enter Site ID to unassign: ");
                    String siteId = sc.nextLine();
                    adminService.unassignSite(siteId);
                }
                
                case 8 -> {
                    System.out.print("Enter Site ID to update: ");
                    String siteId = sc.nextLine();
                    adminService.updateSite(siteId);
                }
                
                case 9 -> {
                    System.out.print("Enter Site ID to delete: ");
                    String siteId = sc.nextLine();
                    adminService.deleteSite(siteId);
                }
                
                case 10 -> adminService.viewAllSites();
                
                case 11 -> adminService.viewPendingRequests();
                
                case 12 -> {
                    System.out.print("Enter Maintenance ID: ");
                    String maintenanceId = sc.nextLine();
                    System.out.print("Enter paid amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine(); // consume newline
                    System.out.print("Approve? (Y/N): ");
                    boolean approve = sc.nextLine().equalsIgnoreCase("Y");
                    adminService.approvePaymentRequest(maintenanceId, amount, approve);
                }
                
                case 13 -> {
                    System.out.print("Enter Maintenance ID: ");
                    String maintenanceId = sc.nextLine();
                    System.out.print("Enter Site ID: ");
                    String siteId = sc.nextLine();
                    adminService.collectMaintenance(maintenanceId, siteId, admin.getUserId());
                }
                
                case 14 -> adminService.viewAllMaintenance();
                
                case 15 -> {
                    System.out.println("Logging out...");
                    return;
                }
                
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void ownerMenu(Scanner sc, OwnerService ownerService, User owner) {
        while (true) {
            System.out.println("\n========== Owner Menu ==========");
            System.out.println("1. View My Sites");
            System.out.println("2. View Specific Site Details");
            System.out.println("3. Request Payment Confirmation");
            System.out.println("4. View Payment History");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            int ownerChoice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (ownerChoice) {
                case 1 -> ownerService.viewMySites(owner.getUserId());
                
                case 2 -> {
                    System.out.print("Enter Site ID: ");
                    String siteId = sc.nextLine();
                    ownerService.viewMySite(siteId, owner.getUserId());
                }
                
                case 3 -> {
                    System.out.print("Enter Site ID: ");
                    String siteId = sc.nextLine();
                    System.out.print("Enter amount paid: ");
                    double amount = sc.nextDouble();
                    sc.nextLine(); // consume newline
                    ownerService.requestPaymentConfirmation(owner.getUserId(), siteId, amount);
                }
                
                case 4 -> {
                    System.out.print("Enter Site ID: ");
                    String siteId = sc.nextLine();
                    ownerService.viewPaymentHistory(siteId, owner.getUserId());
                }
                
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}