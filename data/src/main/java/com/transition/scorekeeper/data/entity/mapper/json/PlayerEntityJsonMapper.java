package com.transition.scorekeeper.data.entity.mapper.json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.transition.scorekeeper.data.entity.PlayerEntity;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class PlayerEntityJsonMapper {

    private final Gson gson;

    @Inject
    public PlayerEntityJsonMapper() {
        this.gson = new Gson();
    }

    public PlayerEntity transformPlayerEntity(String playerJsonResponse) throws JsonSyntaxException {
        Type playerEntityType = new TypeToken<PlayerEntity>() {
        }.getType();
        return this.gson.fromJson(playerJsonResponse, playerEntityType);
    }

    public List<PlayerEntity> transformPlayerEntityCollection(String playersJsonResponse)
            throws JsonSyntaxException {
        Type listOfPlayerEntityType = new TypeToken<List<PlayerEntity>>() {
        }.getType();
        return this.gson.fromJson(playersJsonResponse, listOfPlayerEntityType);
    }
}
