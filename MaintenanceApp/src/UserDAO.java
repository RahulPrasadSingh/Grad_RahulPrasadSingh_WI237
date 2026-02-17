package MaintenanceApp.src;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface UserDAO extends CrudDAO<User> {
    User login(String userId, String password);
    // void raisePaymentRequest(String userId);
    // void approvePayment(String userId);
    // void getAllPendingPayments();
}

class UserDAOImpl implements UserDAO {

    private Connection conn;

    public UserDAOImpl() {
        DBConnection db = DBConnectionFactory.getConnection("POSTGRES");
        conn = db.connect();
    }

    @Override
    public void add(User user) {
        try {
            String sql = "INSERT INTO users (user_id, name, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole().name());
            int rows = ps.executeUpdate();
            System.out.println(rows + " user added successfully");
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try {
            String sql = "UPDATE users SET name=?, password=? WHERE user_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getUserId());
            
            int rows = ps.executeUpdate();
            System.out.println(rows + " user updated successfully");
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String id) {
        try {
            String sql = "DELETE FROM users WHERE user_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            
            int rows = ps.executeUpdate();
            System.out.println(rows + " user deleted successfully");
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getById(String id) {
        try {
            String sql = "SELECT * FROM users WHERE user_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                printUser(rs);
                return createUserFromResultSet(rs);
            } else {
                System.out.println("No user found with ID: " + id);
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            String sql = "SELECT * FROM users ORDER BY user_id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            System.out.println("\n========== All Users ==========");
            while (rs.next()) {
                printUser(rs);
                users.add(createUserFromResultSet(rs));
                System.out.println("--------------------");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User login(String userId, String password) {
        try {
            String sql = "SELECT * FROM users WHERE user_id=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                printUser(rs);
                return createUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        String userId = rs.getString("user_id");
        String name = rs.getString("name");
        String password = rs.getString("password");
        Role role = Role.valueOf(rs.getString("role"));
        
        if (role == Role.ADMIN) {
            return new Admin(userId, name, password);
        } else {
            return new Owner(userId, name, password);
        }
    }

    private void printUser(ResultSet rs) throws SQLException {
        System.out.println("User ID        : " + rs.getString("user_id"));
        System.out.println("Name           : " + rs.getString("name"));
        System.out.println("Role           : " + rs.getString("role"));
    }
}