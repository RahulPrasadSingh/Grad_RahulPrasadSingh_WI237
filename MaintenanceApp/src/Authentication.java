package MaintenanceApp.src;
public interface Authentication {
       User validate(String id,String pass);
}

class AdminAuth implements Authentication{
    private static UserDAO client;
    private static AdminAuth instance;
   
    private AdminAuth(){
        client=new UserDAOImpl();
    }

    public static AdminAuth getInstance(){
        if(instance==null){
            instance=new AdminAuth();
        }
        return instance;
    }

    public User validate(String id,String pass){
        User user=client.login(id,pass);
        if(user.getRole() == Role.ADMIN){
            System.out.println("Valid User.....");
            return user;
        }
        return null;
    }

}

class OwnerAuth implements Authentication{
    private static UserDAO client;
    private static OwnerAuth instance;
   
    private OwnerAuth(){
        client=new UserDAOImpl();
    }

    public static OwnerAuth getInstance(){
        if(instance==null){
            instance=new OwnerAuth();
        }
        return instance;
    }

    public User validate(String id,String pass){
        User user=client.login(id,pass);
        if(user.getRole() == Role.OWNER){
            System.out.println("Valid User.....");
            return user;
        }
        return null;
    }

}
