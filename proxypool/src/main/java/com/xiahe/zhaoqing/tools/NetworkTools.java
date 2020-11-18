package com.xiahe.zhaoqing.tools;

import javax.net.ssl.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Scanner;

public class NetworkTools {

    // ForString (map,proxy,url,method,charset,data,time)
    public static String networkForString(Map<String, String> map, Proxy proxy, String... args) throws Exception {
        //URLConnection
        URLConnection urlConnection = networkForURLConnection(map, proxy, args);

        // Result
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(urlConnection.getInputStream(), args.length > 2 && args[2] != null ? args[2] : "UTF-8");
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine() + "\n");
        }

        // Close
        scanner.close();
        if (args[0].startsWith("https")) {
            ((HttpsURLConnection) urlConnection).disconnect();
            return stringBuilder.toString();
        }
        ((HttpURLConnection) urlConnection).disconnect();
        return stringBuilder.toString();
    }

    // ForURLConnection
    public static URLConnection networkForURLConnection(Map<String, String> map, Proxy proxy, String... args) throws Exception {
        //Proxy
        URLConnection urlConnection = proxy == null ? new URL(args[0]).openConnection() : new URL(args[0]).openConnection(proxy);

        //SSL
        if (args[0].startsWith("https")) {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new MyX509TrustManager()}, new SecureRandom());
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlConnection;
            httpsURLConnection.setSSLSocketFactory(sslContext.getSocketFactory());
            httpsURLConnection.setHostnameVerifier(new MyHostnameVerifier());
            httpsURLConnection.setRequestMethod(args.length > 1 && args[1] != null ? args[1] : "GET");
        }

        //Method
        if (!args[0].startsWith("https")) {
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod(args.length > 1 && args[1] != null ? args[1] : "GET");
        }

        //Time
        urlConnection.setReadTimeout((args.length > 4 && args[4] != null ? Integer.valueOf(args[4]) : 8) * 1000);

        //Property
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        // Data
        if (args.length > 3 && args[3] != null) {
            urlConnection.setDoOutput(true);
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(args[3].getBytes(args.length > 2 && args[2] != null ? args[2] : "UTF-8"));
            outputStream.flush();
            outputStream.close();
        }
        return urlConnection;
    }

    // ////////////////////////////////////////Other//////////////////////////////////////////////////////
    public static class MyHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String arg0, SSLSession arg1) {
            return false;
        }

    }

    public static class MyX509TrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }

}
