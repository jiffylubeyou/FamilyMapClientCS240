package Tasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.widget.Toast;

import kstrong3.familymap.DataCache;
import kstrong3.familymap.ServerProxy;
import requestresponse.LoginRequest;
import requestresponse.LoginResult;
import requestresponse.RegisterRequest;
import requestresponse.RegisterResult;

public class RegisterTask implements Runnable {

    private final Handler messageLoginTaskHandler;
    private final RegisterRequest request;
    private final String serverHost;
    private final String serverPort;

    public RegisterTask(Handler messageLoginTaskHandler, RegisterRequest request, String serverHost, String serverPort)
    {
        this.messageLoginTaskHandler = messageLoginTaskHandler;
        this.request = request;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    @Override
    public void run()
    {
        ServerProxy proxy = new ServerProxy(serverHost,serverPort);
        LoginResult result = proxy.register(request);
        if (result.success)
        {
            DataCache.getInstance().setAuthToken(result.getAuthtoken());
            DataCache.getInstance().setUsername(result.getUsername());
            DataCache.getInstance().setPersonID(result.getPersonID());
        }
        sendMessage(result);
    }

    private void sendMessage(LoginResult result)
    {
        Message message = Message.obtain();
        Bundle messageBundle = new Bundle();
        messageBundle.putString(DataCache.AUTH_TOKEN_KEY, result.getAuthtoken());
        if (!result.success)
        {
            messageBundle.putString(DataCache.MESSAGE_KEY, result.getMessage());
        }
        //add the message to the bundle
        message.setData(messageBundle);
        messageLoginTaskHandler.sendMessage(message);
    }
}
