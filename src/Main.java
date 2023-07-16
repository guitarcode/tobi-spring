import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new MySqlUserDao();

        userDao.deleteAll();

        User newUser = new User();
        newUser.setId("semin");
        newUser.setUserName("최세민");
        newUser.setPassword("semin");

        userDao.add(newUser);
        
        User getUser = userDao.getById("semin");
        System.out.println("getUser.getUserName() = " + getUser.getUserName());
    }
}