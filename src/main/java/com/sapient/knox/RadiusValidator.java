package com.sapient.knox;


import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Shubham A Gupta on 03-May-18.
 */
public class RadiusValidator {

    static final String URL = "http://localhost:80/api/authenticate";

    static final String USER_NAME = "sro_test@test.com";
    static final String PASSWORD = "UGFzc3dvcmQ0NTY=";


    public static void main(String[] args) {

    boolean result = new RadiusValidator().validate();
    System.out.println(result);

    }

    public boolean validate() {
        Client client = ClientBuilder.newClient();

        WebTarget webTarget = client.target("http://fdevtest020:80/api/authenticate");

//        User user = new User("sro_test@test.com","Password456");

        String input = "{ \"username\": \"" + USER_NAME + "\", \"password\": \"" + PASSWORD + "\" }";


        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(input, MediaType.APPLICATION_JSON));

        System.out.println(response.getStatus());
        System.out.println(response.readEntity(String.class));

        return true;
    }


   /* public boolean validate() {
        Client client = Client.create();
        WebResource webResource = client.resource(URL);

        String input = "{ \"username\": \"" + USER_NAME + "\", \"password\": \"" + PASSWORD + "\" }";

        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);

        if (response.getStatus() != 200) {
            System.out.println("Failed : HTTP error code : " + response.getStatus());
            return false;
        }

        String output = response.getEntity(String.class);
        return output.contains("tempToken");
    }*/
}
