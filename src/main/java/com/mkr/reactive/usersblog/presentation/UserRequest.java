package com.mkr.reactive.usersblog.presentation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest {
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Schema(example = "John", description = "The user's first name", minLength = 2, maxLength = 50,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Schema(example = "Cena", description = "The user's last name", minLength = 2, maxLength = 50,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @Email
    @NotBlank(message = "Email is required")
    @Schema(example = "acme@lootunes.com", description = "The user's email address",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 16, message = "Password must be at least 6 characters and at most 16 characters")
    @Schema(example = "P@ssw0rd!", description = "The user's password", minLength = 8, maxLength = 16,
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    public UserRequest() {
    }

    public UserRequest(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
