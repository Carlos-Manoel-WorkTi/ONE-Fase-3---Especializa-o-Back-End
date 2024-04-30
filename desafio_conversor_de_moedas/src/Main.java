import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);

        String url_base = "https://v6.exchangerate-api.com/v6/8a36153f91a0d9c5ccb7fd37/pair/";

        while(true) {
            System.out.println(  "____________________________________________________\n" +
                    "Qual tipo de moeda voce possui?\n" +
                    "[1] Moeda: Real brasileiro\n" +
                    "[2] Moeda: Dólar americano\n" +
                    "[3] Moeda: Euro\n" +
                    "[4] Moeda: Libra esterlina\n" +
                    "[5] Moeda: Iene japonês\n" +
                    "[6] Moeda: Dólar australiano\n" +
                    "[0] Para sair!\n" +
                    "Digite o numero: ");

            String moeda_base = read.nextLine();

            System.out.println("____________________________________________________\n" +
                    "Para qual tipo de moeda deseja converter?\n" +
                    "\n" +
                    "[1] Moeda: Real brasileiro\n" +
                    "[2] Moeda: Dólar americano\n" +
                    "[3] Moeda: Euro\n" +
                    "[4] Moeda: Libra esterlina\n" +
                    "[5] Moeda: Iene japonês\n" +
                    "[6] Moeda: Dólar australiano\n" +
                    "[0] Para sair!\n" +
                    "Digite o numero: ");

            String moeda = read.nextLine();

            if (Integer.parseInt(moeda) == 0) break;

            Moeda current_moeda = new Moeda(Integer.parseInt(moeda),Integer.parseInt(moeda_base));

            System.out.println("Digite o valor desejado para ser convertido: ");

            String valor = read.nextLine();

            current_moeda.setValor(Float.parseFloat(valor));

            String url_finish = url_base + current_moeda.getTipo()  + "/" + current_moeda.getMoeda_base();

            try{
                URL url = new URL(url_finish);
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                request.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                StringBuilder content = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                reader.close();

                Gson gson = new Gson();


                ResponseMoeda resp = gson.fromJson(content.toString(), ResponseMoeda.class);

                current_moeda.setValue_base_code(resp.conversion_rate());

                System.out.println(current_moeda.toString());

                System.out.println("Deseja continuar? [1] SIM : [0] NÃO");

                String continuar = read.nextLine();

                if(continuar.equals("0")) {
                    break;
                }

            }catch (Exception e){
                System.out.println("error");
            }

        }
    }
}


