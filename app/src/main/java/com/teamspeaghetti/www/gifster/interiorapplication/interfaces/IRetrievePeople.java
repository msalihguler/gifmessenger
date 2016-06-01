package com.teamspeaghetti.www.gifster.interiorapplication.interfaces;

import com.teamspeaghetti.www.gifster.interiorapplication.model.People;

import java.util.List;

/**
 * Created by msalihguler on 22.05.2016.
 */
public interface IRetrievePeople {
    public void getRetrievedPeople(List<People> peopleList);
    public void createList(People people);
}
