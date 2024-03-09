package com.mandacarubroker;

import com.mandacarubroker.domain.Role;
import com.mandacarubroker.domain.user.User;
import java.time.LocalDate;

public class UserFactory {
    private static final double NORMAL_USER_BALANCE = 100;
    public static User createAdminUser(String encryptedPassword){

        User adminUser = new User("admin",encryptedPassword,"admin@email.com","","", LocalDate.parse("2000-05-04"),0.0);
        adminUser.setRole(Role.ADMIN);
        return adminUser;
    }

    public static User createUserWithNormalRole(String encryptedPassword){

        User normalUser = new User("paulo",encryptedPassword,"paulo@email.com","Paulo","Herbert", LocalDate.parse("2000-05-04"),NORMAL_USER_BALANCE);
        normalUser.setRole(Role.NORMAL);
        return normalUser;
    }
    public static User createUserDTO(String noEncryptedPassword){
        return new User("normal",noEncryptedPassword,"normal@email.com","Paulo","Herbert", LocalDate.parse("2000-05-04"),NORMAL_USER_BALANCE);
    }
    public static User createUserWithInvalidData(){
        return new User("","","normal@.com","Paulo","Herbert", LocalDate.parse("2000-05-04"),-5.0);
    }
}
