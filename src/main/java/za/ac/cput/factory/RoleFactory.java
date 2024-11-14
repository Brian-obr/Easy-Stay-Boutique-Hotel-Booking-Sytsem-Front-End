package za.ac.cput.factory;

import za.ac.cput.entity.Role;
import za.ac.cput.util.Helper;

public class RoleFactory {

    // Factory method to create a Role with all details
    public static Role buildRole(String roleName) {
        if (Helper.isNullorEmpty(roleName)) {
            return null;
        }
        return Role.builder()
                .roleName(roleName)
                .build();
    }

}
