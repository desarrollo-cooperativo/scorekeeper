package com.transition.scorekeeper.data.entity.mapper.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.transition.scorekeeper.data.entity.MatchEntity;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class MatchEntityJsonMapper {

    private final Gson gson;

    @Inject
    public MatchEntityJsonMapper() {
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    public MatchEntity transformMatchEntity(String matchJsonResponse) throws JsonSyntaxException {
        Type matchEntityType = new TypeToken<MatchEntity>() {
        }.getType();
        return this.gson.fromJson(matchJsonResponse, matchEntityType);
    }

    public List<MatchEntity> transformMatchEntityCollection(String matchesJsonResponse)
            throws JsonSyntaxException {
        Type listOfMatchEntityType = new TypeToken<List<MatchEntity>>() {
        }.getType();
        return this.gson.fromJson(matchesJsonResponse, listOfMatchEntityType);
    }
}
