package com.example.mechanic.repository;

import static com.example.mechanic.repository.ContractReaderTableColumns.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.mechanic.databaseApp.DatabaseMechanic;
import com.example.mechanic.dominio.objects.Car;
import com.example.mechanic.dominio.person.Employee;
import com.example.mechanic.dominio.person.TypeEmployee;
import com.example.mechanic.repository.ContractReaderTableColumns.TableEmployee;
import com.example.mechanic.util.UtilWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RepositoryEmployee {
    public static class FindByEmailAndPassword {
        HttpURLConnection con;
        Uri.Builder builder;
        URL url;

        public FindByEmailAndPassword(String email, String password){
            this.builder = new Uri.Builder();
            builder.appendQueryParameter("app","Mechanic");
            builder.appendQueryParameter("email",email);
            builder.appendQueryParameter("senha",password);
        }

        public static FindByEmailAndPassword build(String email, String password){return new FindByEmailAndPassword(email,password);}


        public Employee onPosExecute(){
            Employee employee = null;
                String result = UtilWebService.executeHttpUrl(url, con, builder, "Employee/APIFindByEmailAndPassword.php");
                Log.v("DevApp Fluxo1","Result: "+result);
            try {
                Log.v("DevApp Fluxo1","jesus");
                JSONArray jsonArray = new JSONArray(result);
                Log.v("DevApp Fluxo1", String.valueOf(jsonArray.length()));
                if(jsonArray.length() != 0){
                    Log.v("DevApp Fluxo1","foi1");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.v("DevApp Fluxo1","foi2");
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        LocalDate date = LocalDate.parse(jsonObject.getString(TableEmployee.BIRTHDAY_DATE), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        employee = Employee.EmployeeBuilder.builder()
                                .typeEmployee(TypeEmployee.valueOf(jsonObject.getString(TableEmployee.COLUMN_TYPE)))
                                .cpf(jsonObject.getString(TableEmployee.COLUMN_CPF))
                                .dataBirthday(date)
                                .fullName(jsonObject.getString(TableEmployee.COLUMN_NAME))
                                .email(jsonObject.getString(TableEmployee.COLUMN_EMAIL))
                                .password(jsonObject.getString(TableEmployee.COLUMN_PASSWORD))
                                .phoneNumber(Long.valueOf(jsonObject.getString(TableEmployee.COLUMN_PHONE)))
                                .car(new Car(jsonObject.getLong(ViewEmployeeCar.ID_CAR),jsonObject.getLong(ViewEmployeeCar.IDENTIFICATION_NUMBER_CAR)))
                                .build();

                        Log.v("DevApp Fluxo1", employee.toString());
                    }
                }
            } catch (JSONException e) {
                Log.v("DevApp Fluxo1",e.getMessage());
            }
            return employee;
        }

    }

    public static boolean saveEmployeeSQLite(Employee employee, Context context) throws SQLException {
        ContentValues values = new ContentValues();

        boolean isInserted = false;

        values.put(TableEmployee.COLUMN_EMAIL, employee.getEmail());
        values.put(ViewEmployeeCar.ID_CAR,employee.getCar().getId());
        values.put(ViewEmployeeCar.IDENTIFICATION_NUMBER_CAR,employee.getCar().getNumberIdentification());
        values.put(TableEmployee.COLUMN_TYPE, employee.getTypeEmployee().toString());
        values.put(TableEmployee.COLUMN_NAME, employee.getFullName());


        try (DatabaseMechanic databaseMechanic = new DatabaseMechanic(context);
             SQLiteDatabase db = databaseMechanic.getDb()) {
            databaseMechanic.onUpgrade(db,1,2);
            long rowId = db.insertOrThrow(TableEmployee.TABLE_NAME, null, values);
            Log.v("DevApp Fluxo1", String.format("Inserted the employee %s id = %s", employee.getFullName(), employee.getEmail(), employee.getTypeEmployee()));
            isInserted = true;
        }
        return isInserted;
    }

    public static Employee findEmployee(Context context) {
        Log.v("DevApp Fluxo1", "Find by Id Employee");

        Employee employee = null;

        String email = "";
        String[] selectArg = {email};
        String sortOrder = TableEmployee.COLUMN_EMAIL + " DESC";
        String selection = TableEmployee.COLUMN_EMAIL + " = ?";

        try (DatabaseMechanic databaseMechanic = new DatabaseMechanic(context);
             SQLiteDatabase db = databaseMechanic.getDb();
             Cursor cursor = db.query(TableEmployee.TABLE_NAME, null, null, null, null, null, sortOrder)) {

            while (cursor.moveToNext()) {
                employee = Employee.EmployeeBuilder.builder()
                        .email(cursor.getString(cursor.getColumnIndexOrThrow(TableEmployee.COLUMN_EMAIL)))
                        .fullName(cursor.getString(cursor.getColumnIndexOrThrow(TableEmployee.COLUMN_NAME)))
                        .car(new Car(
                                cursor.getLong(cursor.getColumnIndexOrThrow(ViewEmployeeCar.ID_CAR)),
                                cursor.getLong(cursor.getColumnIndexOrThrow(ViewEmployeeCar.IDENTIFICATION_NUMBER_CAR))
                        ))
                        .typeEmployee(TypeEmployee.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(TableEmployee.COLUMN_TYPE))))
                        .build();
                Log.v("DevApp Fluxo1","Employee Logged: "+ employee);
            }
        }
        return employee;
    }
}
