public final class DBConnectionFactory {

    private DBConnectionFactory(){}

    public static DBConnection getConnection(String type) {

        if(type.equalsIgnoreCase("POSTGRES"))
            return PostgresConnection.getInstance();

        throw new IllegalArgumentException("Unsupported DB");
    }
}

