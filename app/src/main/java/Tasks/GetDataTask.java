package Tasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import kstrong3.familymap.DataCache;
import kstrong3.familymap.ServerProxy;
import requestresponse.EventRequest;
import requestresponse.EventResultArray;
import requestresponse.PersonRequest;
import requestresponse.PersonResultArray;

public class GetDataTask implements Runnable {

    private final Handler messageLoginTaskHandler;
    private final String username;
    private final String serverHost;
    private final String serverPort;

    public GetDataTask(Handler messageLoginTaskHandler, String username, String serverHost, String serverPort)
    {
        this.messageLoginTaskHandler = messageLoginTaskHandler;
        this.username = username;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    @Override
    public void run()
    {
        PersonRequest personRequest = new PersonRequest(username);
        EventRequest eventRequest = new EventRequest(username);
        ServerProxy proxy = new ServerProxy(serverHost,serverPort);
        PersonResultArray personResultArray = proxy.getPeople(personRequest);
        EventResultArray eventResultArray = proxy.getEvents(eventRequest);
        DataCache.getInstance().setPeopleArray(personResultArray.getPeople());
        DataCache.getInstance().setEventsArray(eventResultArray.getEvents());
        DataCache.getInstance().populateMaps();
        sendMessage();
    }

    private void sendMessage()
    {
        Message message = Message.obtain();
        Bundle messageBundle = new Bundle();

        //add stuff to bundle here
        message.setData(messageBundle);
        messageLoginTaskHandler.sendMessage(message);
    }
}
