package com.example.mechanic.util;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *
 */
public class UtilWebService {
    public static final String URL_WEB_SERVICE_MECHANIC_OFICINA = "http://192.168.0.59/webService/";
    public static final String URL_WEB_SERVICE_MECHANIC_HOUSE = "http://192.168.0.101/webService/";
    public static final String URL_WEB_SERVICE_MECHANIC_HOUSE2 = "http://192.168.1.55/webService/";
    public static final String URL_WEB_SERVICE_MECHANIC_VERCEL = "https://apimechanic.vercel.app/api/";
    public static final int CONNECTION_TIMEOUT  = 10000;
    public static final int READ_TIMEOUT = 30000;

    public static String executeHttpUrl(URL url, HttpURLConnection con, Uri.Builder builder, String path) {
        final int MAX_ATTEMPTS = 3; // Número máximo de tentativas
        int attempt = 0;

        while (attempt < MAX_ATTEMPTS) {
            try {
                attempt++;

                // Construir a URL com base no caminho fornecido
                String[] pathSplit = path.split("/");
                url = new URL(UtilWebService.URL_WEB_SERVICE_MECHANIC_VERCEL + pathSplit[1]);
                //url = new URL(UtilWebService.URL_WEB_SERVICE_MECHANIC_HOUSE + path);

                // Configurar a conexão HTTP
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(UtilWebService.CONNECTION_TIMEOUT);
                con.setReadTimeout(UtilWebService.READ_TIMEOUT);
                con.setRequestMethod("POST");
                con.setRequestProperty("charset", "utf-8");
                con.setDoInput(true);
                con.setDoOutput(true);

                // Construir a consulta
                String query = builder.build().getEncodedQuery();
                try (OutputStream outputStream = con.getOutputStream();
                     BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
                    bufferedWriter.write(query);
                    bufferedWriter.flush(); // Descarrega o buffer
                } catch (IOException e) {
                    Log.e("DevApp Fluxo1", "IOException Error BufferedWriter " + e.getMessage());
                }

                // Conectar
                con.connect();

                // Ler a resposta
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream input = con.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder resultReader = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        resultReader.append(line);
                    }
                    return resultReader.toString();
                } else {
                    return "Error Connection";
                }
            } catch (MalformedURLException e) {
                Log.e("DevApp Fluxo1", "WebService MalformedURLException " + e.getMessage());
                return e.getMessage();
            } catch (IOException e) {
                Log.w("DevApp Fluxo1", "IOException Error Connect/BufferedReader " + e.getMessage());
                // Verificar se é um timeout e tentar novamente se ainda houver tentativas restantes
                if (e.getMessage().contains("timeout") && attempt < MAX_ATTEMPTS) {
                    Log.w("DevApp Fluxo1", "Timeout occurred, retrying... Attempt " + attempt + " of " + MAX_ATTEMPTS);
                    continue; // Tentar novamente
                }
                return e.getMessage();
            } catch (Exception e) {
                Log.e("DevApp Fluxo1", "WebService Exception " + e.getMessage());
                return e.getMessage();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
        }

        // Se todas as tentativas falharem, retornar uma mensagem de erro
        return "Failed after " + MAX_ATTEMPTS + " attempts";
    }

    public static String executeHttpUrl(URL url, HttpURLConnection con, Uri.Builder builder, String path, List<byte[]> fileDataList, List<String> fileFieldNames) {
        final int MAX_ATTEMPTS = 3;
        int attempt = 0;
        String boundary = "----BoundaryString";

        while (attempt < MAX_ATTEMPTS) {
            try {
                attempt++;

                String[] pathSplit = path.split("/");
                url = new URL(UtilWebService.URL_WEB_SERVICE_MECHANIC_VERCEL + pathSplit[1]);

                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(UtilWebService.CONNECTION_TIMEOUT);
                con.setReadTimeout(UtilWebService.READ_TIMEOUT);
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                con.setDoInput(true);
                con.setDoOutput(true);

                try (OutputStream outputStream = con.getOutputStream();
                     BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {

                    // Escrever os parâmetros do formulário
                    for (String key : builder.build().getQueryParameterNames()) {
                        String value = builder.build().getQueryParameter(key);
                        bufferedOutputStream.write(("--" + boundary + "\r\n").getBytes());
                        bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
                        bufferedOutputStream.write((value + "\r\n").getBytes());
                    }

                    if(fileDataList!=null){
                        // Escrever os arquivos de imagem
                        for (int i = 0; i < fileDataList.size(); i++) {
                            byte[] fileData = fileDataList.get(i);
                            String fileFieldName = fileFieldNames.get(i);
                            String fileName = "image" + (i + 1) + ".jpg";

                            bufferedOutputStream.write(("--" + boundary + "\r\n").getBytes());
                            bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + fileFieldName + "\"; filename=\"" + fileName + "\"\r\n").getBytes());
                            bufferedOutputStream.write(("Content-Type: image/jpeg\r\n\r\n").getBytes());
                            bufferedOutputStream.write(fileData);
                            bufferedOutputStream.write("\r\n".getBytes());
                        }
                    }

                    bufferedOutputStream.write(("--" + boundary + "--\r\n").getBytes());
                    bufferedOutputStream.flush();
                }

                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream input = con.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder resultReader = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        resultReader.append(line);
                    }
                    return resultReader.toString();
                } else {
                    return "Error Connection: " + responseCode;
                }
            } catch (MalformedURLException e) {
                Log.e("DevApp Fluxo1", "WebService MalformedURLException " + e.getMessage());
                return e.getMessage();
            } catch (IOException e) {
                Log.w("DevApp Fluxo1", "IOException Error Connect/BufferedReader " + e.getMessage());
                if (e.getMessage().contains("timeout") && attempt < MAX_ATTEMPTS) {
                    Log.w("DevApp Fluxo1", "Timeout occurred, retrying... Attempt " + attempt + " of " + MAX_ATTEMPTS);
                    continue;
                }
                return e.getMessage();
            } catch (Exception e) {
                Log.e("DevApp Fluxo1", "WebService Exception " + e.getMessage());
                return e.getMessage();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
        }

        return "Failed after " + MAX_ATTEMPTS + " attempts";
    }

    public static String executeHttpUrl(URL url, HttpURLConnection con, Uri.Builder builder, String path, File file) {
        try {
            String[] pathSplit = path.split("/");
            url = new URL(UtilWebService.URL_WEB_SERVICE_MECHANIC_VERCEL + pathSplit[1]);
        } catch (MalformedURLException e) {
            Log.e("DevApp Fluxo1", "WebService MalformedURLException " + e.getMessage());
        } catch (Exception e) {
            Log.e("DevApp Fluxo1", "WebService Exception " + e.getMessage());
        }

        try {
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(UtilWebService.CONNECTION_TIMEOUT);
            con.setReadTimeout(UtilWebService.READ_TIMEOUT);
            con.setRequestMethod("POST");
            con.setRequestProperty("charset", "utf-8");
            con.setDoInput(true);
            con.setDoOutput(true);

            // Adiciona parâmetros da consulta ao corpo da solicitação
            String query = builder.build().getEncodedQuery();
            try (OutputStream outputStream = con.getOutputStream();
                 BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
                bufferedWriter.write(query);
                bufferedWriter.flush();
            } catch (IOException e) {
                Log.e("DevApp Fluxo1", "IOException Error BufferedWriter " + e.getMessage());
            }

            // Adiciona o arquivo ao corpo da solicitação
            try (OutputStream fileOutputStream = con.getOutputStream();
                 FileInputStream fileInputStream = new FileInputStream(file)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                Log.e("DevApp Fluxo1", "IOException Error writing file to output stream " + e.getMessage());
            }

            con.connect();
        } catch (IOException e) {
            Log.e("DevApp Fluxo1", "IOException Error Connect " + e.getMessage());
        }

        try {
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream input = con.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                StringBuilder resultReader = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    resultReader.append(line);
                }
                return resultReader.toString();
            } else {
                return "Error Connection 3";
            }
        } catch (IOException e) {
            Log.v("DevApp Fluxo1", "IOException Error BufferedReader " + e.getMessage());
            return e.getMessage();
        } finally {
            con.disconnect();
        }
    }

}
