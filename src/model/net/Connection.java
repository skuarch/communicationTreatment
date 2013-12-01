package model.net;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;

/**
 * Communication client server. This class provides of communication between
 * client and server, in order to used this class<br/>
 * 1.- Open the connection<br/>
 * 2.- Used
 * <code>sendText()</code> and
 * <code>receiveTex()</code> (optional) <br/>
 * 3.- Finally close the connection.
 *
 * @author skuarch
 */
public class Connection {

    private String host = null;
    private int port = 0;
    private String context = null;
    private String uri = null;
    private String protocol = null;
    private URL url = null;
    private HttpURLConnection hurlc = null;
    private String stringUrl = null;
    private OutputStream outputStream = null;
    private InputStream inputStream = null;
    private DataInputStream dataInputStream = null;
    private int responseCode = 0;

    //==========================================================================
    /**
     * Create a instance.
     *
     * @param host String
     * @param port int
     * @param context String
     * @param uri String
     */
    public Connection(String host, int port, String context, String uri) {
        this.host = host;
        this.port = port;
        this.context = context;
        this.uri = uri;
        this.protocol = "http";
    } // end Connection

    //==========================================================================
    /**
     * Open the connection with the remote host.
     *
     * @throws MalformedURLException
     * @throws IOException
     */
    public void openConnection() throws MalformedURLException, IOException {

        if (host == null || host.length() < 1) {
            throw new IllegalArgumentException("host is null");
        }

        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("port is incorrect");
        }

        if (context == null || context.length() < 1) {
            throw new IllegalArgumentException("context is null");
        }

        if (uri == null || uri.length() < 1) {
            throw new IllegalArgumentException("uri is null");
        }

        stringUrl = protocol + "://" + host + ":" + port + "/" + context + "/" + uri;
        url = new URL(stringUrl);
        hurlc = (HttpURLConnection) url.openConnection();
        hurlc.setRequestProperty("access", "1");
        hurlc.setRequestMethod("POST");
        hurlc.setUseCaches(false);
        hurlc.setDoOutput(true);
        hurlc.setDoInput(true);        

    } // end openConnection

    //==========================================================================
    /**
     * Send text to the remote host.
     *
     * @param text String
     * @param requestProperty String
     * @throws IOException
     */
    public void sendText(String requestProperty, String value) throws IOException {
        hurlc.setRequestProperty(requestProperty, value);
        outputStream = hurlc.getOutputStream();
        outputStream.write(value.getBytes());
        outputStream.flush();
        outputStream.close();

        responseCode = hurlc.getResponseCode();

    } // end sendText

    //==========================================================================
    /**
     * Receive text from remote host.
     *
     * @return String
     */
    public String receiveText() throws IOException {

        String receiveString = null;
        StringBuilder sb = new StringBuilder();
        String tmp = null;

        inputStream = hurlc.getInputStream();
        dataInputStream = new DataInputStream(inputStream);

        while ((tmp = dataInputStream.readLine()) != null) {
            sb.append(tmp);
        }

        receiveString = sb.toString();
        //inputStream.close();
        //dataInputStream.close();

        return receiveString;

    } // end receiveText

    //==========================================================================
    /**
     * Close the connection with the remote host.
     */
    public void closeConnection() {

        try {

            //responseCode = hurlc.getResponseCode();

            if (responseCode != 200) {
                throw new UnexpectedException("the server is responding with " + responseCode);
            }

            if (hurlc != null) {
                hurlc.disconnect();
            }

            if (outputStream != null) {
                outputStream.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }

            if (dataInputStream != null) {
                dataInputStream.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            url = null;
            hurlc = null;
            inputStream = null;
            outputStream = null;
            dataInputStream = null;
        }

    } // end closeConnection

    //==========================================================================
    /**
     * Return a
     * <code>int</code> with the code of protocol.
     *
     * @return int
     */
    public int getResponseCode() {
        return responseCode;
    } // getResponseCode
} // end class