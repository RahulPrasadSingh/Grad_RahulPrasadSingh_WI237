package MaintenanceApp.src;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SiteDAO implements CrudDAO<Site> { 
    private DBConnection db;

    public SiteDAO(){
        System.out.println("Enter Db name:");
        String dbName = new Scanner(System.in).next();
        db = DBConnectionFactory.getConnection(dbName);
    }

    @Override
    public void add(Site obj){
        try{
            Connection con = db.connect();
            PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO sites (site_id, length, width, occupied, type, owner_id) VALUES(?,?,?,?,?,?)"
            );

            pstmt.setString(1, obj.getSiteNo());
            pstmt.setDouble(2, obj.getLength());
            pstmt.setDouble(3, obj.getWidth());
            pstmt.setBoolean(4, obj.getOccupied());
            pstmt.setString(5, obj.getSiteType().toString());
            pstmt.setString(6, obj.getUser() != null ? obj.getUser().getUserId() : null);

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " record inserted");
        }catch(SQLException e){
            System.err.println("Error adding site: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Site obj){
        try{
            Connection con = db.connect();
            PreparedStatement pstmt = con.prepareStatement(
                "UPDATE sites SET length=?, width=?, occupied=?, type=?, owner_id=? WHERE site_id=?"
            );

            pstmt.setDouble(1, obj.getLength());
            pstmt.setDouble(2, obj.getWidth());
            pstmt.setBoolean(3, obj.getOccupied());
            pstmt.setString(4, obj.getSiteType().toString());
            pstmt.setString(5, obj.getUser() != null ? obj.getUser().getUserId() : null);
            pstmt.setString(6, obj.getSiteNo());

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " record updated");
        }catch(SQLException e){
            System.err.println("Error updating site: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String id){
        try{
            Connection con = db.connect();
            
            // First check if site exists
            Site site = getById(id);
            if(site == null){
                System.out.println("Site no: " + id + " not found");
                return;
            }
            
            System.out.println("Enter Y/y to delete:");
            char ch = new Scanner(System.in).next().charAt(0);
            
            if(ch == 'Y' || ch == 'y'){
                PreparedStatement pstmt = con.prepareStatement(
                    "DELETE FROM sites WHERE site_id=?"
                );
                pstmt.setString(1, id);
                int rows = pstmt.executeUpdate();
                System.out.println(rows + " record deleted");
            } else {
                System.out.println("Site no: " + id + " not deleted...");
            }
            
        }catch(SQLException e){
            System.err.println("Error deleting site: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Site getById(String id){
        try{
            Connection con = db.connect();
            PreparedStatement pstmt = con.prepareStatement(
                "SELECT * FROM sites WHERE site_id=?"
            );

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                printRow(rs);
                return createSiteFromResultSet(rs);
            } else {
                System.out.println("No record found");
                return null;
            }
        }catch(SQLException e){
            System.err.println("Error fetching site: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Site> getAll(){
        List<Site> sites = new ArrayList<>();
        try{
            Connection con = db.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sites ORDER BY site_id");

            while(rs.next()){
                printRow(rs);
                sites.add(createSiteFromResultSet(rs));
                System.out.println("--------------------");
            }
        }catch(SQLException e){
            System.err.println("Error fetching sites: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return sites;
    }

    private Site createSiteFromResultSet(ResultSet rs) throws SQLException {
        String siteId = rs.getString("site_id");
        double length = rs.getDouble("length");
        double width = rs.getDouble("width");
        boolean occupied = rs.getBoolean("occupied");
        String typeStr = rs.getString("type");
        String ownerId = rs.getString("owner_id");
        
        SiteType type = SiteType.valueOf(typeStr);
        
        // Fetch user if owner_id is not null
        User owner = null;
        if(ownerId != null){
            UserDAO userDAO = new UserDAOImpl();
            owner = userDAO.getById(ownerId);
        }
        
        // Create appropriate site type using DAO constructor
        Site site;
        switch(type){
            case VILLA:
                site = new Villa(siteId, length, width, occupied, owner);
                break;
            case APARTMENT:
                site = new Apartment(siteId, length, width, occupied, owner);
                break;
            case INDEPENDENT_HOUSE:
                site = new IndependentHouse(siteId, length, width, occupied, owner);
                break;
            case OPEN_SITE:
                site = new OpenSite(siteId, length, width, occupied, owner);
                break;
            default:
                throw new IllegalArgumentException("Unknown site type: " + type);
        }
        
        return site;
    }

    private void printRow(ResultSet rs) throws SQLException {
        System.out.println("Site No : " + rs.getString("site_id"));
        System.out.println("Length  : " + rs.getDouble("length"));
        System.out.println("Width   : " + rs.getDouble("width"));
        System.out.println("Occupied: " + rs.getBoolean("occupied"));
        System.out.println("Type    : " + rs.getString("type"));
        System.out.println("Owner   : " + rs.getString("owner_id"));
    }
}