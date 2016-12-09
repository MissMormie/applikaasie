/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.utility;

import java.io.IOException;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sonja
 */
public class AdresByPostcode {

    /**
     * 
     * @param postcode
     * @param huisnummer
     * @return String[]
     *         String[0] = straatnaam
     *         String[1] = woonplaats
     */
    public static String[] getAddress(String postcode, int huisnummer) { 
        String urlString = "https://www.postnl.nl/adres-zoeken/?postal-code=" + postcode + "&number=" + huisnummer;

        Document doc;
        try {
            doc = Jsoup.connect(urlString).get();
                
            Elements postcodeResult = doc.select("div.result p em");
            String[] adres = new String[2];
            
            // Geen bijpassend adres gevonden.
            if (postcodeResult.isEmpty())
                return null;
            
            for (int i = 0; i < postcodeResult.size(); i++) {
                adres[i] = postcodeResult.get(i).text();
            }
            adres[1] = adres[1].substring(1, 2).toUpperCase() + adres[1].substring(2).toLowerCase();
            return adres;
            /* 
                <div class="result">
                <p><em>Sterkenburg</em>&nbsp;86 <br />2402RC <em>&nbsp;ALPHEN AAN DEN RIJN</em></p>
                </div>
            */
            
        } catch (IOException ex) {
            org.slf4j.Logger logger = LoggerFactory.getLogger("AdresByPostcode.class");
            logger.error("can't connect to postnl postcode database. :" + ex);
            return null;
        }
    }
}
