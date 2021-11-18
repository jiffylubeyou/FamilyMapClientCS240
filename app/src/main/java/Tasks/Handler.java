package Tasks;

import android.os.Bundle;
import android.os.Message;

public class Handler extends android.os.Handler {
    @Override
    public void handleMessage(Message msg) {
        Bundle bundle = msg.getData();
        long totalSize = bundle.getLong(TOTAL_SIZE_KEY,0);

    }
}
