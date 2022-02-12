package com.challenger.calculator.models;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_CALCULATOR")
public class CalculatorModel implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 70)
    private String weight;

    @Column(nullable = false, length = 70)
    private String zipcode_origin;

    @Column(nullable = false, length = 70)
    private String zipcode_destination;

    @Column(nullable = false, length = 70)
    private String name_user_destination;

    @Column(nullable = false, length = 70)
    private String value_total_delivery;

    @Column(nullable = false, length = 70)
    private String date_delivery;

    @Column(nullable = false, length = 70)
    private String query_delivery;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWeight() {return weight;}

    public void setWeight(String peso) {this.weight = weight;}

    public String getZipcode_origin() {return zipcode_origin;}

    public void setZipcode_origin(String zipcode_origin) {this.zipcode_origin = zipcode_origin;}

    public String getZipcode_destination() {return zipcode_destination;}

    public void setZipcode_destination(String zipcode_destination) {this.zipcode_destination = zipcode_destination;}

    public String getName_user_destination() {return name_user_destination;}

    public void setName_user_destination(String name_user_destination) {this.name_user_destination = name_user_destination;}

    public String getValue_total_delivery() {return value_total_delivery;}

    public void setValue_total_delivery(String value_total_delivery) {this.value_total_delivery = value_total_delivery;}

    public String getDate_delivery() {return date_delivery;}

    public void setDate_delivery(String date_delivery) {this.date_delivery = date_delivery;}

    public String getQuery_delivery() {return query_delivery;}

    public void setQuery_delivery(String query_delivery) {this.query_delivery = query_delivery;}

    public CalculatorModel(String weight,
                      String zipcode_origin,
                      String zipcode_destination,
                      String name_user_destination,
                      String value_total_delivery,
                      String date_delivery,
                      String query_delivery) {
        this.weight = weight;
        this.zipcode_origin = zipcode_origin;
        this.zipcode_destination = zipcode_destination;
        this.name_user_destination = name_user_destination;
        this.value_total_delivery = value_total_delivery;
        this.date_delivery = date_delivery;
        this.query_delivery = query_delivery;
    }

    public CalculatorModel(){}

}
