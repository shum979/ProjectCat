package com.sapient.cat;

/**
 * Created by Shubham A Gupta on 04-May-18.
 */
public class TestClass {

    public static void main(String[] args) {

        AppContext.getInstance("D:\\Projects\\catj-dbc\\src\\main\\resources\\application.properties").getAllValuesContaining("sample.query").forEach(System.out::println);
    }

}
