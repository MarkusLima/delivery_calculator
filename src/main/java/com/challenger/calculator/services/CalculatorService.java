package com.challenger.calculator.services;

import com.challenger.calculator.models.CEP;
import com.challenger.calculator.models.CalculatorModel;
import com.challenger.calculator.repositories.CalculatorRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class CalculatorService {

    @Autowired
    CalculatorRepository calculatorRepository;

    @Transactional
    public CalculatorModel save(CalculatorModel calculator) {
        return calculatorRepository.save(calculator);
    }

    public List<CalculatorModel> findAll() {
        return calculatorRepository.findAll();
    }

    public CEP getZipCode(String zipCode) throws Exception  {

        String urlParaChamada = "http://viacep.com.br/ws/" + zipCode + "/json";

        try {
            URL url = new URL(urlParaChamada);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            if (conexao.getResponseCode() != 200)
                throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

            BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
            String jsonEmString = this.converteJsonEmString(resposta);

            Gson gson = new Gson();
            CEP cep = gson.fromJson(jsonEmString, CEP.class);

            return cep;
        } catch (Exception e) {
            throw new Exception("ERRO: " + e);
        }
    }

    public String converteJsonEmString(BufferedReader buffereReader) throws IOException {
        String resposta, jsonEmString = "";
        while ((resposta = buffereReader.readLine()) != null) {
            jsonEmString += resposta;
        }
        return jsonEmString;
    }

    public int[] compareUFs(String uf_one,
                            String uf_two,
                            String ddd_one,
                            String ddd_two){
        int[] new_array = new int[2];

        //CEPs de estados diferentes não deve ser aplicado o desconto no valor do frete e entrega prevista de 10 dia
        if(!uf_one.contentEquals(uf_two)) {
            new_array[0] = 0;
            new_array[1] = 10;
        }

        //CEPs de estados iguais tem 75% de desconto no valor do frete e entrega prevista de 3 dias
        if(uf_one.contentEquals(uf_two)) {
            new_array[0] = 75;
            new_array[1] = 3;
        }

        //CEPs com DDDs iguais tem 50% de desconto no valor do frete e entrega prevista de 1 dia
        if(ddd_one.contentEquals(ddd_two)) {
            new_array[0] = 50;
            new_array[1] = 1;
        }

        return new_array;
    }

    public float calculatorKg(float weight, float desconto){
        //System.out.println("Percet: " + desconto/100.0);
        return (float) ((1.00 * weight)- ((1.00 * weight) * (desconto/100.0)));
    }
}
