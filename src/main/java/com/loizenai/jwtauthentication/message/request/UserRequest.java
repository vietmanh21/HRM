package com.loizenai.jwtauthentication.message.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public class UserRequest {
    @NotBlank
    @Size(min=3, max = 60)
    private String username;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private LocalDate validUtil;

    public LocalDate getValidUtil() {
        return validUtil;
    }

    public void setValidUtil(LocalDate validUtil) {
        this.validUtil = validUtil;
    }

    public @NotBlank @Size(min = 6, max = 40) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 6, max = 40) String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public @NotBlank @Size(min = 3, max = 60) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(min = 3, max = 60) String username) {
        this.username = username;
    }
}
