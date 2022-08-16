package com.amazon.ask.recomo.handlers;

import com.amazon.ask.AlexaSkill;
import com.amazon.ask.attributes.persistence.PersistenceAdapter;
import com.amazon.ask.builder.SkillBuilder;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.exception.PersistenceException;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;
import com.amazon.ask.recomo.domain.movie;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

import static com.amazon.ask.request.Predicates.intentName;

public class YesIntent implements RequestHandler{
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("AMAZON.YesIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {

        Map<String,Object> Ret = handlerInput.getAttributesManager().getSessionAttributes();
        Object ret1 =  Ret.get("movieNow");
        ObjectMapper mapper = new ObjectMapper();
        movie ret = mapper.convertValue(ret1, movie.class);
        handlerInput.getAttributesManager().getSessionAttributes().clear();

        String speech1 = "I am suggesting" + ret.movieName+ " for you... this movie is played by ";
        String speech2 = "";
        if(ret.actor!=null&&ret.actress!=null){
            speech2 = ret.actor+" and "+ ret.actress;
        }
        else if(ret.actor!=null){
            speech2 = ret.actor;
        }
        else  speech2 = ret.actress;
        String speech3 = "... Here is the description of this movie:  "+ret.description;
        String speech4 = "... This movie ranked "+ ret.rank + " in IMDB, hope u like it. Now you can say open new recomo to start again.";

        String speechText= speech1+speech2+speech3+speech4;

        //Construct the response
        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Movie now", speechText)
                .withReprompt(speechText)
                .withShouldEndSession(true)
                .build();
    }
}
