/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.net;

import java.util.HashMap;
import org.json.JSONObject;
import org.junit.Test;

/**
 *
 * @author skuarch
 */
public class tro {

    @Test
    public void algo() throws Exception {
        System.out.println("openConnection");
        Connection instance = null;
        HashMap hm = new HashMap();
        JSONObject jsono = null;

        try {

             hm.put("text", "mocos");
             hm.put("level", 3);
             hm.put("serverType", 2);
             hm.put("date", "2013-11-15 10:00:00");
             hm.put("description", "123");
             hm.put("code", 2);
             hm.put("serverName", "skuarch-lap url monitor");

             jsono = new JSONObject(hm);

             instance = new Connection("192.168.208.9", 8080, "sam5", "alarmDispatcher");
             //instance = new Connection("192.168.208.9", 8080, "sam5", "alarmDispatcher");
             instance.openConnection();
             instance.sendText("alarm", jsono.toString());                

             instance.closeConnection();



        } catch (Exception e) {
            throw e;
        } finally {
            instance.closeConnection();
        }

    }
}
