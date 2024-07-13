package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        final String createUsersTableQuery = """
                CREATE TABLE IF NOT EXISTS `users` (
                `id` BIGINT NOT NULL AUTO_INCREMENT,
                `name` VARCHAR(45) NULL,
                `last_name` VARCHAR(45) NULL,
                `age` TINYINT NULL,
                PRIMARY KEY (`id`),
                UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
                """;

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(createUsersTableQuery)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        final String dropUsersTableQuery = "DROP TABLE IF EXISTS `users`";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(dropUsersTableQuery)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        final String saveUserQuery = "INSERT INTO `users` (name, last_name, age) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(saveUserQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        final String removeUserByIdQuery = "DELETE FROM `users` WHERE id = ?";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(removeUserByIdQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        final List<User> users = new ArrayList<>();
        final String getAllUsersQuery = "SELECT * FROM `users`";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(getAllUsersQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        final String cleanUsersTable = "TRUNCATE TABLE `users`";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(cleanUsersTable)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
