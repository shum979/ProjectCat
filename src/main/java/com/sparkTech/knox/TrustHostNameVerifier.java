package com.sparkTech.knox;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.util.regex.Pattern;

/**
 * Created by Shubham A Gupta on 23-May-18.
 */
public class TrustHostNameVerifier implements HostnameVerifier {

    private String hostnames;
    public final String DELIMITER = ";";

    private TrustHostNameVerifier(){ }

    public TrustHostNameVerifier(String hostNames){
        this.hostnames = hostnames;
    }

    @Override
    public boolean verify(String s, SSLSession sslSession) {
        String[] hosts = hostnames.split(DELIMITER);

        Pattern pattern = null;

        /*for (int i = 0; i < hosts.length; i++) {
            pattern.compile(hosts[i]).matcher("").matches();
        }*/
        return true;
    }
}
