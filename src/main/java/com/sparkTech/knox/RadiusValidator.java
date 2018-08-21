package com.sparkTech.knox;


import javax.net.ssl.SSLContext;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Shubham A Gupta on 03-May-18.
 */
public class RadiusValidator {

    static final String URL = "https://localhost:443/api/authenticate";
    static final String USER_NAME = "sro_test@test.com";
    static final String PASSWORD = "UGFzc3dvcmQ0NTY=";
    static final String IS_SSL_ENABLED = "true";


    public static void main(String[] args) {

        RadiusValidator radiusValidator = new RadiusValidator();

        Client client = null;
        try {
            client = getClient();
//            User user = new User("sro_test@test.com","Password456");
            User user = new User(USER_NAME,PASSWORD);

            WebTarget webTarget = client.target(URL);
            Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.post(Entity.entity(user.toJson(), MediaType.APPLICATION_JSON));

            System.out.println(response.getStatus());
            System.out.println(response.readEntity(String.class));


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }



    }

   private static Client getClient() throws NoSuchAlgorithmException, KeyManagementException {
        boolean isSslEnabled = Boolean.parseBoolean(IS_SSL_ENABLED);

        if(isSslEnabled){
            return sshClient();
        }else {
            return ClientBuilder.newClient();
        }
    }



    private static Client sshClient() throws KeyManagementException, NoSuchAlgorithmException {

        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, CaAuthority.getTrustManager(), null);

        Client client = ClientBuilder
                    .newBuilder()
                    .hostnameVerifier(new TrustHostNameVerifier(""))
                    .sslContext(sslContext)
                    .build();

            return  client;
    }


    }










