package com.novieq.learning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
    public static void main(String args[]) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://www.wayfair.com/Andrew-Philips-Jewelry-Pouch-4640-L503-K~IPS1065.html?refid=DTCPA49-IPS1065_4464646_4464653&PiID%5B%5D=4464646");
        CloseableHttpResponse response = null;
        InputStream instream = null;
        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                instream = entity.getContent();
                InputStreamReader is = new InputStreamReader(instream);
                StringBuilder sb=new StringBuilder();
                BufferedReader br = new BufferedReader(is);
                String read = br.readLine();

                while(read != null) {
                    //System.out.println(read);
                    sb.append(read);
                    read =br.readLine();
                }

                System.out.println(sb.toString());
                
                Document document = Jsoup.parse(sb.toString());
                Elements els =  document.select("tbody > tr > td");
                Elements links = document.select("link[href]");
                Elements media = document.select("[src]");
                //Elements imports = document.select("link[href]");
                Elements imports = document.select("link[href][rel=canonical]");
                
                System.out.println("\nImports: (%d)" + imports.size());
                for (Element link : imports) {
                    System.out.println(" * %s <%s> (%s)" + link.tagName() +" Ho"+ link.attr("abs:href") + " Hi" + link.attr("rel"));
                }

             
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
                try {
                    instream.close();
                    response.close();
                    
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
    }
}
