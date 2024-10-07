package com.example.mechanic.service;

import com.example.mechanic.dominio.objects.Car;
import com.example.mechanic.dominio.objects.Receipt;
import com.example.mechanic.dominio.objects.ReceiptPart;
import com.example.mechanic.repository.RepositoryReceipt;

import java.util.List;

public class ServiceReceipt {
    public static List<ReceiptPart> findReceiptInProgressByIdCar(Car car){
        return RepositoryReceipt.FindReceiptInProgressByIdCar.build(car).onPosExecute();
    }

    public static boolean updateStatusReceipt(Receipt receipt){
        return RepositoryReceipt.UpdateStatusReceipt.build(receipt).onPosExecute();
    }
}
