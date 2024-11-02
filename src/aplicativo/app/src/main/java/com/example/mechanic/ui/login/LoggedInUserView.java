package com.example.mechanic.ui.login;

import com.example.mechanic.dominio.person.TypeEmployee;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {

    private TypeEmployee typeEmployee;
    private String displayName;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    LoggedInUserView(TypeEmployee typeEmployee, String displayName) {
        this.typeEmployee = typeEmployee;
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }

    public TypeEmployee getTypeEmployee() {
        return typeEmployee;
    }
}