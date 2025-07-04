import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== CONVERSOR DE MONEDAS ===");
        System.out.print("Ingrese la moneda de origen (ej: USD): ");
        String fromCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Ingrese la moneda de destino (ej: EUR): ");
        String toCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Ingrese la cantidad a convertir: ");
        double amount = scanner.nextDouble();

        try {
            // Construir URL con los datos ingresados
            String urlStr = "https://api.exchangerate.host/convert?from=" + fromCurrency + "&to=" + toCurrency + "&amount=" + amount;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Leer respuesta JSON
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder json = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();

            // Extraer el resultado de la conversión desde el JSON
            String jsonString = json.toString();
            int indexStart = jsonString.indexOf("\"result\":") + 9;
            int indexEnd = jsonString.indexOf(",", indexStart);
            String result = jsonString.substring(indexStart, indexEnd);

            // Mostrar resultado
            System.out.println("\nResultado: " + amount + " " + fromCurrency + " = " + result + " " + toCurrency);

        } catch (Exception e) {
            System.out.println("Error al conectar con la API: " + e.getMessage());
        }

        scanner.close();
    }
}
