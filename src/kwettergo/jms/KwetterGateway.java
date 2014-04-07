package kwettergo.jms;

import com.google.gson.Gson;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Message;
import javax.jms.MessageListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Toon
 */
public abstract class KwetterGateway {

    private MessagingGateway mg;
    private static final String JNDI_QUEUE = "jms/KwetterGo/kwetter_request_queue";
    private Gson gson = new Gson();
    private final CountDownLatch countDownLatch;

    public KwetterGateway() {
        countDownLatch = new CountDownLatch(1);

        mg = new MessagingGateway(JNDI_QUEUE);
        mg.setListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                countDownLatch.countDown();
                processReply(true);
            }
        });
    }

    public abstract void processReply(boolean reply);

    public void sendRequest(String[] payload) {
        String jsonPayload = gson.toJson(payload);
        //Message m = mg.createMsg(jsonPayload);
        mg.send(jsonPayload, MessagingGateway.getDestination(JNDI_QUEUE));

        try {
            countDownLatch.await();
            System.out.println("looks like it worked.");
        } catch (InterruptedException ex) {
            System.out.println("nope...");
            Logger.getLogger(KwetterGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
