package com.userManagementSystem.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class EmployeeDto {

    private Long id;

    @Size(min = 3,max = 16,message = "Username should be more than 3 and less than 16 letters")
    private String username;

    @Email(message = "Enter a valid email")
    private String email;

    @Size(min=5,max=12, message = "Enter a valid password")
    private String password;

    private String role;
}
