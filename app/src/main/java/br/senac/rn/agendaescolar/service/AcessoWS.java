package br.senac.rn.agendaescolar.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AcessoWS {

    public String httpGet(String endereco) {
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(endereco);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStreamReader streamReader = new InputStreamReader(url.openStream());
            BufferedReader bufferedReader = new BufferedReader(streamReader);

            String linha = null;
            while ((linha = bufferedReader.readLine()) != null) {
                builder.append(linha + "\n");
            }
            bufferedReader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

}
