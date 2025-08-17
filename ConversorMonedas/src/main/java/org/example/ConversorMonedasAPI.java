package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class ConversorMonedasAPI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Llamada a la API
            String apiUrl = "https://v6.exchangerate-api.com/v6/3a4316a51baf9488a35007a3/latest/USD";
            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();

            // Parsear JSON
            JSONObject obj = new JSONObject(json.toString());
            JSONObject rates = obj.getJSONObject("rates");

            // Mostrar opciones
            System.out.println("Conversor desde USD");
            System.out.print("Ingresa el monto en USD: ");
            double usd = scanner.nextDouble();

            System.out.print("Ingresa el código de moneda destino (ej: PEN, EUR, GBP): ");
            String destino = scanner.next().toUpperCase();

            if (rates.has(destino)) {
                double tasa = rates.getDouble(destino);
                double convertido = usd * tasa;
                System.out.printf("$ %.2f USD = %.2f %s%n", usd, convertido, destino);
            } else {
                System.out.println("Moneda no encontrada.");
            }

        } catch (Exception e) {
            System.out.println("Error al obtener tasas de cambio: " + e.getMessage());
        }

        scanner.close();
    }
}
