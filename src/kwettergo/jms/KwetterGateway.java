package kwettergo.jms;

import com.google.gson.Gson;
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

    public KwetterGateway() {
        mg = new MessagingGateway(MessagingGateway.getDestination(JNDI_QUEUE));
        mg.setListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                processReply(true);
            }
        });
    }

    public abstract void processReply(boolean reply);

    public void sendRequest(String[] payload) {
        String jsonPayload = gson.toJson(payload);
        Message m = mg.createMsg(jsonPayload);
        mg.send(m, MessagingGateway.getDestination(JNDI_QUEUE));
    }
}
