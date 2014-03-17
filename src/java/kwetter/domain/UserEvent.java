/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.domain;

import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 *
 * @author Toon
 */
public class UserEvent {

    private User u;
    private userOptions type;
    

    public enum userOptions {

        LOGIN, LOGOUT, NEW_FOLLOW, STOP_FOLLOW
    }

    public UserEvent(User u, userOptions type) {
        this.u = u;
        this.type = type;
    }

    public User getU() {
        return u;
    }

    public userOptions getType() {
        return type;
    }
}
