package za.ac.cput.factory;

import za.ac.cput.entity.Role;
import za.ac.cput.entity.User;
import za.ac.cput.util.Helper;
import java.time.LocalDateTime;
import java.util.List;

public class UserFactory {

    // Factory method to create a User with all details
    public static User buildUser(long userId, String firstName, String lastName, String userName, String password, List<Role> roles) {
        if (userId < 0 || Helper.isNullorEmpty(firstName) || Helper.isNullorEmpty(lastName) ||
                Helper.isNullorEmpty(userName) || roles == null || roles.isEmpty()) {
            return null;
        }

        // Using Lombok-generated builder
        return User.builder()
                .userId(userId)
                .firstName(firstName)
                .lastName(lastName)
                .userName(userName)
                .password(password)
                .roles(roles)
                .build();
    }

    // Factory method to create a User without an ID
    public static User buildUserWithoutId(String firstName, String lastName, String userName, String password, List<Role> roles) {
        if (Helper.isNullorEmpty(firstName) || Helper.isNullorEmpty(lastName) ||
                Helper.isNullorEmpty(userName) || roles == null || roles.isEmpty()) {
            return null;
        }

        // Using Lombok-generated builder
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .userName(userName)
                .password(password)
                .roles(roles)
                .build();
    }

    // Factory method to create a User for login
    public static User buildUserLogin(String userName, String password) {
        if (Helper.isNullorEmpty(userName) || Helper.isNullorEmpty(password)) {
            return null;
        }

        // Using Lombok-generated builder
        return User.builder()
                .userName(userName)
                .password(password)
                .build();
    }
}
