package Tasks;

import android.os.Bundle;
import android.os.Message;

import java.net.URL;

private static class DownLoadTask implements Runnable {
    private final Handler messageHandler;

    private final URL[] urls;

    public DownLoadTask(Handler messageHandler, URL... urls)
    {
        this.messageHandler = messageHandler;
        this.urls = urls;
    }

    @Override
    public void run()
    {
        sendMessage(totalSize);
    }

    private void sendMessage(long totalSize)
    {
        Message message = Message.obtain();
        Bundle messageBundle = new Bundle();
        messageBundle.putLong(TOTAL_SIZE_KEY, totalSize);
        message.setData(messageBundle)
        messageHandler.sendMessage(message);
    }
}
