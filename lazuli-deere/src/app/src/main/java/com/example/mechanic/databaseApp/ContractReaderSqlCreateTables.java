package com.example.mechanic.databaseApp;

import static com.example.mechanic.repository.ContractReaderTableColumns.*;

public class ContractReaderSqlCreateTables {

    public static final String SQL_CREATE_TABLE_EMPLOYEE =
            "CREATE TABLE " + TableEmployee.TABLE_NAME + "(" +
                    TableEmployee.COLUMN_EMAIL + " TEXT NOT NULL," +
                    ViewEmployeeCar.ID_CAR + " INT NOT NULL," +
                    ViewEmployeeCar.IDENTIFICATION_NUMBER_CAR + " INT NOT NULL," +
                    TableEmployee.COLUMN_TYPE + " TEXT CHECK(" + TableEmployee.COLUMN_TYPE + " IN ('WORKER','MANAGER')) NOT NULL," +
                    TableEmployee.COLUMN_NAME + " TEXT NOT NULL)";

    public static final String SQL_DELETE_TABLE_EMPLOYEE =
            "DROP TABLE IF EXISTS " + TableEmployee.TABLE_NAME;

}
