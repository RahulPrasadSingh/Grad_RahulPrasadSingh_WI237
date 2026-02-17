package MaintenanceApp.src;
import java.util.Scanner;

enum Role {
    ADMIN, OWNER
}

public abstract class User {
    private String userId;
    private String name;
    private String password;
    private Role role;

    // Constructor for DAO usage
    public User(String userId, String name, String password, Role role) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    // Constructor for interactive creation
    public User(Role role) {
        Scanner sc = new Scanner(System.in);
        
        do {
            System.out.println("Enter User Id (A[0-9]{4} for Admin, O[0-9]{4} for Owner):");
            userId = sc.nextLine().trim();
        } while (!userId.matches("^(A[0-9]{4}|O[0-9]{4})$"));
        
        System.out.println("Enter Name:");
        name = sc.nextLine().trim();
        
        // do {
        //     System.out.println("Enter email:");
        //     email = sc.nextLine().trim();
        // } while (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
        
        do {
            System.out.println("Enter password (min 6 characters):");
            password = sc.nextLine().trim();
        } while (password.length() < 6);
        
        this.role = role;
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    // public void setEmail(String email) {
    //     this.email = email;
    // }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                '}';
    }
}

final class Admin extends User {
    public Admin() {
        super(Role.ADMIN);
    }

    public Admin(String userId, String name, String password) {
        super(userId, name, password, Role.ADMIN);
    }
}

final class Owner extends User {
    public Owner() {
        super(Role.OWNER);
    }

    public Owner(String userId, String name, String password) {
        super(userId, name, password, Role.OWNER);
    }
}