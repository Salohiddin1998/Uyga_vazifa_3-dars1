package uz.pdp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private int id;
    private String userName;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;

    public User(String userName, String firstName, String lastName, String phoneNumber, String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
