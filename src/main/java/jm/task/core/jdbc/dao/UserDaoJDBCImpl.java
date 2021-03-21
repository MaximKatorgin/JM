package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getMYSQLConnection().createStatement();) {
            statement.executeUpdate("create table if not exists `users` (" +
                    "`id` bigint(20) not null auto_increment, " +
                    "`name` varchar(45) null, " +
                    "`lastName` varchar(100) null, " +
                    "`age` tinyint(3) null, " +
                    "primary key (`id`))");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getMYSQLConnection().createStatement();) {
            statement.executeUpdate("drop TABLE if exists `users`");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.getMYSQLConnection()
                .prepareStatement("insert into `users` (`name`, `lastName`, `age`) values (?,?,?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        try (PreparedStatement preparedStatement = Util.getMYSQLConnection()
                .prepareStatement("delete from `users` where `id` = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = Util.getMYSQLConnection().createStatement();) {
            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                userList.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getMYSQLConnection().createStatement();) {
            statement.execute("truncate `users`");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
