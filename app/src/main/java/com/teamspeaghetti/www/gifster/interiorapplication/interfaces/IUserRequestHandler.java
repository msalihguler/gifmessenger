package com.teamspeaghetti.www.gifster.interiorapplication.interfaces;

/**
 * Created by Salih on 21.05.2016.
 */
public interface IUserRequestHandler {

    public void sendRequest(String id,String latitude,String longitude,String token,String gender,String language);
    public void getPeople(String id);
    public void sendLikeStatus(String m_id,String o_id,String type);
    public void revealProfile(String id);
    public void getRevealedProfiles(String id);
    public void deleteProfile(String id);
}
