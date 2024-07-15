
package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Boris", "Ivanov", (byte) 43);
        userService.saveUser("Aleksey", "Petrov", (byte) 21);
        userService.saveUser("Vladimir", "Sidorov", (byte) 12);
        userService.saveUser("Sergey", "Kuznetsov", (byte) 32);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();

        try {
            Util.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
