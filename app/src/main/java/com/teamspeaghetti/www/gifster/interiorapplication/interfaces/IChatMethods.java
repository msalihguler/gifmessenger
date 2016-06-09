package com.teamspeaghetti.www.gifster.interiorapplication.interfaces;

/**
 * Created by Salih on 4.06.2016.
 */
public interface IChatMethods {
    public void getMatches();
    public void sendMessage(String otherID,String url);
    public void getMessages(String otherID);
}
