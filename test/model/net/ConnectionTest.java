package model.net;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author skuarch
 */
public class ConnectionTest {

    public ConnectionTest() throws MalformedURLException, IOException {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of openConnection method, of class Connection.
     */
    @Test
    public void testOpenConnection() throws Exception {

        System.out.println("openConnection");
        Connection instance = null;
        HashMap hm = new HashMap();
        JSONObject jsono = null;

        try {

            //for (int i = 0; i < 10; i++) {
                hm.put("name", "mocos");
                hm.put("ip", "192.168.208.9");
                hm.put("type", 1);
                hm.put("port", 8080);
                hm.put("description", "123");
                hm.put("status", 1);                

                jsono = new JSONObject(hm);

                instance = new Connection("192.168.208.9", 8080, "sam5", "registerDispatcher");
                //instance = new Connection("192.168.208.9", 8080, "sam5", "alarmDispatcher");
                instance.openConnection();
                instance.sendText("register", jsono.toString());
                System.out.println("texto " + instance.receiveText());
                
                instance.closeConnection();
                
            //}

            System.out.println("se termino");

        } catch (Exception e) {
            throw e;
        } finally {
            instance.closeConnection();
        }

    }
}