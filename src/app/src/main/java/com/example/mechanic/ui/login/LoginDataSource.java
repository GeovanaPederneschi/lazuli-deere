package com.example.mechanic.ui.login;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.example.mechanic.repository.RepositoryEmployee;
import com.example.mechanic.service.ServiceEmployee;
import com.example.mechanic.ui.login.model.LoggedInUser;
import com.example.mechanic.dominio.person.Employee;
import com.example.mechanic.dominio.person.TypeEmployee;
import com.example.mechanic.util.AsyncTaskExecutorService;
import com.example.mechanic.util.UtilSystem;

import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    Context context;
    Employee employee;

    public Result<LoggedInUser> login(String username, String password, Context context) {

        Callable<Employee> callable = () -> ServiceEmployee.findByEmailAndPassword(username,password);
        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        ExecutorCompletionService<Employee> completionService = new ExecutorCompletionService<>(threadPool);
        completionService.submit(callable);
        Result.Error error = null;
        try {
            employee = completionService.take().get();
        } catch (ExecutionException | InterruptedException e) {
            error = new Result.Error(new IOException("Error logging in", e));
        }
        threadPool.shutdown();
        if(employee!=null){
            try {
                RepositoryEmployee.saveEmployeeSQLite(employee,context);
                LoggedInUser user =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                employee.getFullName(),
                                employee.getTypeEmployee());
                Log.v("DevApp",user.toString());
                return new Result.Success<>(user);
            } catch (SQLException e) {
                return error;
            }
        }else{
            return error;
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

}