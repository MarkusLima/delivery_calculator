package com.challenger.calculator.controllers;

import com.challenger.calculator.models.CEP;
import com.challenger.calculator.models.CalculatorModel;
import com.challenger.calculator.services.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class CalculatorController {

    @Autowired
    CalculatorService calculatorService;

    @GetMapping(value="/")
    public String index(Model model) {
        List<CalculatorModel> calculators = calculatorService.findAll();
        model.addAttribute("calculators", calculators);
        return "index";
    }

    @PostMapping(value="/save")
    public String saveCalculator(@RequestParam MultiValueMap form, RedirectAttributes atts) {
        String name_user_destination = (String) form.getFirst("name_user_destination");
        String weight = (String) form.getFirst("weight");
        String zipcode_origin = (String) form.getFirst("zipcode_origin");
        String zipcode_destination = (String) form.getFirst("zipcode_destination");
        System.out.println("user_destination: "+name_user_destination+ "/weight: "+weight+ "/zipcode_origin: "+zipcode_origin+"/zipcode_destination: "+zipcode_destination);

        try {
            //Service service = new Service();
            CEP cep_zipcode_origin = calculatorService.getZipCode(zipcode_origin);
            CEP cep_zipcode_destination = calculatorService.getZipCode(zipcode_destination);

            if(cep_zipcode_origin.getCep() == null){
                atts.addFlashAttribute("NOTES_SESSION", "Cep de origin não encontrado!");
                return "redirect:/";
            }

            if(cep_zipcode_destination.getCep() == null){
                atts.addFlashAttribute("NOTES_SESSION", "Cep de destino não encontrado!");
                return "redirect:/";
            }

            int[] time_desconto = calculatorService.compareUFs(cep_zipcode_origin.getUf(),
                    cep_zipcode_destination.getUf(),
                    cep_zipcode_origin.getDdd(),
                    cep_zipcode_destination.getDdd());

            Float value_delivery = calculatorService.calculatorKg(Float.valueOf(weight).floatValue(), time_desconto[0]);

            Date date_before = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date_before );
            cal.add(Calendar.DATE, time_desconto[1]);
            date_before = cal.getTime();

            System.out.println("-----------------------------------");
            System.out.println("Peso: " + weight);
            System.out.println("Cep origem: " + zipcode_origin);
            System.out.println("Cep destino: " + zipcode_destination);
            System.out.println("Nome: " + name_user_destination);
            System.out.println("Valor do frete: " + value_delivery);
            System.out.println("Entrega em: " + DateFormat.getDateInstance(DateFormat.MEDIUM).format(date_before));
            System.out.println("Data da consulta: " + DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date()));
            System.out.println("-----------------------------------");

            CalculatorModel calculatorModel = new CalculatorModel(
                    weight,
                    zipcode_origin,
                    zipcode_destination,
                    name_user_destination,
                    Float.toString(value_delivery),
                    DateFormat.getDateInstance(DateFormat.MEDIUM).format(date_before),
                    DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date())
            );

            System.out.println("--------------calculator-------------------");
            System.out.println(calculatorModel);
            System.out.println("--------------calculator-------------------");

            calculatorService.save(calculatorModel);


        } catch (Exception e) {
            e.printStackTrace();
        }


        atts.addFlashAttribute("NOTES_SESSION", "Registro criado com sucesso!");
        return "redirect:/";
    }

}
