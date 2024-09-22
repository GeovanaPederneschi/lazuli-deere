package com.example.mechanic.ui.login.model;

import com.example.mechanic.dominio.person.TypeEmployee;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;

    private TypeEmployee typeEmployee;

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public LoggedInUser(String userId, String displayName, TypeEmployee typeEmployee) {
        this.userId = userId;
        this.displayName = displayName;
        this.typeEmployee = typeEmployee;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public TypeEmployee getTypeEmployee() {
        return typeEmployee;
    }

    @Override
    public String toString() {
        return "LoggedInUser{" +
                "userId='" + userId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", typeEmployee=" + typeEmployee +
                '}';
    }
}