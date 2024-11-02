#include <WiFi.h>
#include <HTTPClient.h>

// Defina o nome da sua rede Wi-Fi e a senha
const char* ssid = "viana.jpg";  // Nome da rede Wi-Fi que o ESP32 vai se conectar
const char* password = "13072404";  // Senha da rede Wi-Fi

// URL da API para onde os dados serão enviados
const char* serverUrl = "https://apimechanic.vercel.app/api/APICarrinho2.php";
//const char* serverUrl = "http://192.168.1.4/APICarrinho.php";  // Altere para a URL correta

// SSIDs das redes específicas para as quais você deseja capturar o RSSI
const char* targetSSIDs[3] = {"JDUBER_01", "JDUBER_02", "JDUBER_03"};

void setup() {
  Serial.begin(115200);

  // Conecte ao Wi-Fi
  Serial.println("Conectando ao WiFi...");
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Tentando conectar ao WiFi...");
  }

  Serial.println("Conectado ao WiFi!");
}

void loop() {
  Serial.println("Escaneando redes WiFi...");
  int n = WiFi.scanNetworks();

  if (n == 0) {
    Serial.println("Nenhuma rede encontrada");
    delay(5000);  // Aguardar antes de tentar novamente
    return;
  }

  // Inicializa arrays para armazenar os RSSIs das redes específicas
  int targetRSSIs[3] = {-999, -999, -999};  // Inicializando com valores muito baixos de RSSI

  // Procure as redes específicas
  for (int i = 0; i < n; ++i) {
    String currentSSID = WiFi.SSID(i);
    int rssi = WiFi.RSSI(i);

    // Verifique se o SSID atual é um dos especificados
    for (int j = 0; j < 3; j++) {
      if (currentSSID == targetSSIDs[j]) {
        targetRSSIs[j] = rssi;
        Serial.print("Encontrado: ");
        Serial.print(currentSSID);
        Serial.print(" RSSI: ");
        Serial.println(rssi);
      }
    }
  }

  // Verifique se todos os SSIDs especificados foram encontrados
  for (int i = 0; i < 3; i++) {
    if (targetRSSIs[i] == -999) {
      Serial.println("Nem todas as redes especificadas foram encontradas. Verifique novamente.");
      delay(5000);  // Aguardar antes de tentar novamente
      return;
    }
  }

// Supondo que você tenha uma variável 'carrinhoId' que contém o ID do carrinho
String carrinhoId = "2"; // Substitua pelo ID real do carrinho

// Cria o JSON para enviar para a API
String jsonPayload = "{\"carrinho_id\":\"" + carrinhoId + "\",\"access_points\":[";
for (int i = 0; i < 3; i++) {
    jsonPayload += "{\"ssid\":\"";
    jsonPayload += targetSSIDs[i];
    jsonPayload += "\",\"rssi\":";
    jsonPayload += targetRSSIs[i];
    jsonPayload += "}";
    if (i < 2) jsonPayload += ",";
}
jsonPayload += "]}";

// Agora você pode usar 'jsonPayload' para enviar à API


  Serial.println("Dados JSON a serem enviados:");
  Serial.println(jsonPayload);

  // Enviar os dados para a API
  HTTPClient http;
  http.begin(serverUrl);
  http.addHeader("Content-Type", "application/json");

  int httpResponseCode = http.POST(jsonPayload);

  if (httpResponseCode > 0) {
    String response = http.getString();
    Serial.println("Resposta do servidor:");
    Serial.println(response);
  } else {
    Serial.print("Erro na solicitação: ");
    Serial.println(httpResponseCode);
  }

  http.end();

  // Aguardar antes de fazer a próxima varredura e envio
  delay(10);  // Aguarde 10 segundos antes de repetir o processo
}
