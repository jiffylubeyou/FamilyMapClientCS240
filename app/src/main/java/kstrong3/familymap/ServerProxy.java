package kstrong3.familymap;

import android.app.DownloadManager;
import android.hardware.biometrics.BiometricManager;
import android.provider.ContactsContract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import requestresponse.EventRequest;
import requestresponse.EventResultArray;
import requestresponse.LoginRequest;
import requestresponse.LoginResult;
import requestresponse.PersonRequest;
import requestresponse.PersonResultArray;
import requestresponse.RegisterRequest;
import requestresponse.RegisterResult;

public class ServerProxy {

    private String serverHost;
    private String serverPort;

    public ServerProxy (String serverHost, String serverPort)
    {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public LoginResult login(LoginRequest request)
    {
        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");


            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();


            // Specify that we are sending an HTTP POST request
            http.setRequestMethod("POST");

            // Indicate that this request will contain an HTTP request body
            http.setDoOutput(true);	// There is a request body

            // Specify that we would like to receive the server's response in JSON
            // format by putting an HTTP "Accept" header on the request (this is not
            // necessary because our server only returns JSON responses, but it
            // provides one more example of how to add a header to an HTTP request).
            http.addRequestProperty("Accept", "application/json");

            // Connect to the server and send the HTTP request
            http.connect();

            // This is the JSON string we will send in the HTTP request body
            GsonSerializer gsonSerializer = new GsonSerializer();
            String reqData = gsonSerializer.toJson(request);

            try(OutputStream os = http.getOutputStream()) {
                byte[] input = reqData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            LoginResult result;
            try
            {
                if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"));
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    result = gsonSerializer.fromJson(response.toString(), LoginResult.class);
                    return result;
                }
                else
                {
                    BufferedReader br = new BufferedReader(new InputStreamReader(http.getErrorStream(), "utf-8"));
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    result = gsonSerializer.fromJson(response.toString(), LoginResult.class);
                    return result;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return null;
    }

    public LoginResult register(RegisterRequest request)
    {
        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");


            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();


            // Specify that we are sending an HTTP POST request
            http.setRequestMethod("POST");

            // Indicate that this request will contain an HTTP request body
            http.setDoOutput(true);	// There is a request body

            // Specify that we would like to receive the server's response in JSON
            // format by putting an HTTP "Accept" header on the request (this is not
            // necessary because our server only returns JSON responses, but it
            // provides one more example of how to add a header to an HTTP request).
            http.addRequestProperty("Accept", "application/json");

            // Connect to the server and send the HTTP request
            http.connect();

            // This is the JSON string we will send in the HTTP request body
            GsonSerializer gsonSerializer = new GsonSerializer();
            String reqData = gsonSerializer.toJson(request);

            try(OutputStream os = http.getOutputStream()) {
                byte[] input = reqData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            LoginResult result;
            try
            {
                if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"));
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    result = gsonSerializer.fromJson(response.toString(), LoginResult.class);
                    return result;
                }
                else
                {
                    BufferedReader br = new BufferedReader(new InputStreamReader(http.getErrorStream(), "utf-8"));
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    result = gsonSerializer.fromJson(response.toString(), LoginResult.class);
                    return result;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return null;
    }

    public EventResultArray getEvents(EventRequest request)
    {
        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/event");


            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();


            // Specify that we are sending an HTTP GET request
            http.setRequestMethod("GET");

            // Indicate that this request will not contain an HTTP request body
            http.setDoOutput(false);


            // Add an auth token to the request in the HTTP "Authorization" header
            http.addRequestProperty("Authorization", DataCache.getInstance().getAuthToken());

            // Specify that we would like to receive the server's response in JSON
            // format by putting an HTTP "Accept" header on the request (this is not
            // necessary because our server only returns JSON responses, but it
            // provides one more example of how to add a header to an HTTP request).
            http.addRequestProperty("Accept", "application/json");


            // Connect to the server and send the HTTP request
            http.connect();

            GsonSerializer gsonSerializer = new GsonSerializer();
            EventResultArray result;
            try {
                if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"));
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    result = gsonSerializer.fromJson(response.toString(), EventResultArray.class);
                    return result;
                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(http.getErrorStream(), "utf-8"));
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    result = gsonSerializer.fromJson(response.toString(), EventResultArray.class);
                    return result;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return null;
    }

    public PersonResultArray getPeople(PersonRequest request)
    {
        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person");


            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();


            // Specify that we are sending an HTTP GET request
            http.setRequestMethod("GET");

            // Indicate that this request will not contain an HTTP request body
            http.setDoOutput(false);


            // Add an auth token to the request in the HTTP "Authorization" header
            http.addRequestProperty("Authorization", DataCache.getInstance().getAuthToken());

            // Specify that we would like to receive the server's response in JSON
            // format by putting an HTTP "Accept" header on the request (this is not
            // necessary because our server only returns JSON responses, but it
            // provides one more example of how to add a header to an HTTP request).
            http.addRequestProperty("Accept", "application/json");


            // Connect to the server and send the HTTP request
            http.connect();

            GsonSerializer gsonSerializer = new GsonSerializer();
            PersonResultArray result;
            try {
                if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"));
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    result = gsonSerializer.fromJson(response.toString(), PersonResultArray.class);
                    return result;
                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(http.getErrorStream(), "utf-8"));
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    result = gsonSerializer.fromJson(response.toString(), PersonResultArray.class);
                    return result;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return null;
    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /*
		The writeString method shows how to write a String to an OutputStream.
	*/
    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
