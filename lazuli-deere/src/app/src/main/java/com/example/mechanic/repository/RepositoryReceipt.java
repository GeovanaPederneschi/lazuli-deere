package com.example.mechanic.repository;

import static com.example.mechanic.repository.ContractReaderTableColumns.*;

import android.net.Uri;
import android.util.Log;

import com.example.mechanic.dominio.objects.Car;
import com.example.mechanic.dominio.objects.Part;
import com.example.mechanic.dominio.objects.Receipt;
import com.example.mechanic.dominio.objects.ReceiptPart;
import com.example.mechanic.dominio.objects.StatusDirection;
import com.example.mechanic.dominio.objects.StatusReceipt;
import com.example.mechanic.dominio.points.CollectionPoint;
import com.example.mechanic.dominio.points.StockPoint;
import com.example.mechanic.util.UtilWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RepositoryReceipt {
    public static class FindReceiptInProgressByIdCar {
        HttpURLConnection con;
        Uri.Builder builder;
        URL url;

        public FindReceiptInProgressByIdCar(Car car){
            this.builder = new Uri.Builder();
            builder.appendQueryParameter("app","Mechanic");
            builder.appendQueryParameter("id_carrinho",car.getId().toString());
        }

        public static RepositoryReceipt.FindReceiptInProgressByIdCar build(Car car){return new RepositoryReceipt.FindReceiptInProgressByIdCar(car);}


        public List<ReceiptPart> onPosExecute(){
            List<ReceiptPart> receiptPartList = new ArrayList<>();
            Log.v("DevApp Fluxo2","Find ReceiptInProgress ByIdCar");
            try {
                String result = UtilWebService.executeHttpUrl(url, con, builder, "NaturalPerson/APIFindReceiptInProgressByIdCar.php");
                Log.v("DevApp Fluxo2",result);
                JSONArray jsonArray = new JSONArray(result);
                if(jsonArray.length() != 0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        LocalDateTime parseDateTimeRegister = LocalDateTime.parse(jsonObject.getString((ViewReceiptPointPart.DATETIME_REGISTER)), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


                        CollectionPoint collectionPoint = new CollectionPoint(
                                jsonObject.getLong(ViewReceiptPointPart.ID_COLLECT_POINT),
                                jsonObject.getInt(ViewReceiptPointPart.X_LOCATION_COLLECT_POINT),
                                jsonObject.getInt(ViewReceiptPointPart.Y_LOCATION_COLLECT_POINT)
                        );

                        StockPoint stockPoint = new StockPoint(
                                jsonObject.getLong(ViewReceiptPointPart.ID_STOCK_POINT),
                                jsonObject.getInt(ViewReceiptPointPart.X_LOCATION_STOCK_POINT),
                                jsonObject.getInt(ViewReceiptPointPart.Y_LOCATION_STOCK_POINT)
                        );

                        Receipt receipt = Receipt.ReceiptBuilder.builder()
                                .registerReceipt(parseDateTimeRegister)
                                .statusReceipt(StatusReceipt.valueOf(jsonObject.getString(ViewReceiptPointPart.STATUS_PROGRESS)))
                                .idReceipt(jsonObject.getLong(ViewReceiptPointPart.ID_RECEIPT))
                                .car(new Car(jsonObject.getLong(ViewReceiptPointPart.ID_CAR)))
                                .collectionPoint(collectionPoint)
                                .stockPoint(stockPoint)
                                .statusDirection(StatusDirection.valueOf(jsonObject.getString(ViewReceiptPointPart.STATUS_DIRECTION)))
                                .build();


                        if (jsonObject.getString(ViewReceiptPointPart.DATETIME_DONE) != null){
                            LocalDateTime parseDateTimeDone = LocalDateTime.parse(jsonObject.getString((ViewReceiptPointPart.DATETIME_REGISTER)), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                            receipt.setDataTimeDone(parseDateTimeDone);
                        }

                        ReceiptPart receiptPart = new ReceiptPart(
                                receipt,
                                new Part(jsonObject.getLong(ViewReceiptPointPart.ID_PART)),
                                jsonObject.getInt(ViewReceiptPointPart.COUNT_PART)
                        );

                        receiptPartList.add(receiptPart);

                    }
                }
            } catch (JSONException e) {
                Log.w("DevApp Fluxo2","JSONException "+e.getMessage());
            }
            return receiptPartList;
        }

    }

    public static class UpdateStatusReceipt {
        HttpURLConnection con;
        Uri.Builder builder;
        URL url;

        public UpdateStatusReceipt(Receipt receipt){
            this.builder = new Uri.Builder();
            builder.appendQueryParameter("app","Mechanic");
            builder.appendQueryParameter("id_demanda", String.valueOf(receipt.getIdReceipt()));
            builder.appendQueryParameter("status", receipt.getStatusReceipt().toString());
            builder.appendQueryParameter("id_coleta", receipt.getCollectionPoint().getId().toString());
            builder.appendQueryParameter("id_entrega", receipt.getStockPoint().getId().toString());
            builder.appendQueryParameter("id_carrinho", receipt.getCar().getId().toString());
            builder.appendQueryParameter("status_direction", receipt.getStatusDirection().toString());
        }

        public static RepositoryReceipt.UpdateStatusReceipt build(Receipt receipt){return new RepositoryReceipt.UpdateStatusReceipt(receipt);}

        public boolean onPosExecute(){
            boolean isUpdated = false;
            Log.v("DevApp Fluxo1","Update Status Receipt");
            try {
                String result = UtilWebService.executeHttpUrl(url, con, builder, "ServiceProcedure/APIUpdateStatusReceipt.php");
                Log.v("DevApp Fluxo1","Result: "+result);
                JSONObject jsonObject = new JSONObject(result);
                if(jsonObject.length() != 0){
                    if(jsonObject.getInt("affect_nums")!=0){isUpdated=true;}
                }
            } catch (JSONException e) {
                Log.e("DevApp Fluxo1","Error while updating StatusReceipt in the database. JSONException "+e.getMessage());
            }
            return isUpdated;
        }


    }
}
