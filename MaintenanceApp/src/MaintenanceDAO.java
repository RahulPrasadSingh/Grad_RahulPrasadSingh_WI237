package MaintenanceApp.src;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceDAO implements CrudDAO<Maintenance> {

    private DBConnection db = DBConnectionFactory.getConnection("POSTGRES");

    @Override
    public void add(Maintenance m) {
        String sql = """
            INSERT INTO maintenance
            (maintenance_id, site_id, amount,balance,paid,requested, collected_by)
            VALUES (?,?,?,?,?,?,?)
        """;

        try (PreparedStatement ps = db.connect().prepareStatement(sql)) {
            ps.setString(1, m.getMaintenanceId());
            ps.setString(2, m.getSiteId());
            ps.setDouble(3, m.getAmount());
            ps.setDouble(4, m.getBalance());
            ps.setBoolean(5, m.isPaid());
            ps.setBoolean(6, m.isRequest());
            ps.setString(7, m.getCollectedBy());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Maintenance m) {
        String sql = """
            UPDATE maintenance
            SET amount=?,balance=?, paid=?, requested=?,collected_by=?, collected_on=CURRENT_DATE
            WHERE maintenance_id=?
        """;

        try (PreparedStatement ps = db.connect().prepareStatement(sql)) {
            ps.setDouble(1, m.getAmount());
            ps.setDouble(2, m.getBalance());
            ps.setBoolean(3, m.isPaid());
            ps.setBoolean(4, m.isRequest());
            ps.setString(5, m.getCollectedBy());
            ps.setString(6, m.getMaintenanceId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try (PreparedStatement ps =
                     db.connect().prepareStatement(
                             "DELETE FROM maintenance WHERE maintenance_id=?")) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Maintenance getById(String id) {
        String sql = "SELECT * FROM maintenance WHERE maintenance_id=?";

        try (PreparedStatement ps = db.connect().prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Maintenance(
                        rs.getString("maintenance_id"),
                        rs.getString("site_id"),
                        rs.getDouble("amount"),
                        rs.getDouble("balance"),
                        rs.getBoolean("paid"),
                        rs.getBoolean("requested"),
                        rs.getString("collected_by"),
                        rs.getDate("collected_on")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Maintenance> getAll() {
        List<Maintenance> list = new ArrayList<>();
        String sql = "SELECT * FROM maintenance";

        try (Statement st = db.connect().createStatement()) {
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                list.add(new Maintenance(
                        rs.getString("maintenance_id"),
                        rs.getString("site_id"),
                        rs.getDouble("amount"),
                        rs.getDouble("balance"),
                        rs.getBoolean("paid"),
                        rs.getBoolean("requested"),
                        rs.getString("collected_by"),
                        rs.getDate("collected_on")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
