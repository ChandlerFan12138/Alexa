package com.amazon.ask.recomo.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.recomo.dao.SQLSyntax;
import com.amazon.ask.recomo.domain.movie;
import com.amazon.ask.request.Predicates;
import java.sql.SQLException;
import java.util.*;

public class SpecificMovieType implements IntentRequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return handlerInput.matches(Predicates.intentName("SpecificMovieType"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        //get the slotvalue returned by the Json
        IntentRequest one = (IntentRequest)handlerInput.getRequest();
        Map<String, Slot> temp = one.getIntent().getSlots();
        String type ="";
        for(Slot slot:temp.values()){
            if(slot.getValue()!=null&&slot.getValue().length()>0){
                type = slot.getValue();
                break;
            }
        }

        //SQL: get the movie
        SQLSyntax newSQL = new SQLSyntax();
        ArrayList<movie> ret = new ArrayList<>();
        try {
            ret = newSQL.SelectByType(type);
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }

        //store the result, in case of re-use
        int random = new Random().nextInt(ret.size());
        movie movieNow = ret.remove(random);
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("Ret",ret);
        sessionAttributes.put("movieNow",movieNow);
        handlerInput.getAttributesManager().setSessionAttributes(sessionAttributes);

        String speech1 = "I want to suggest  " + movieNow.movieName + "  for you. ";
        String speech2 = "This movie is typed as ";
        String speech3 = movieNow.type;
        String speech4 = ". Do you want to see what will going on in this movie? If so, please say Yes. Otherwise, please say No. I can suggest another movie for you. ";
        String speechText  = speech1+speech2+speech3+speech4;
        //Construct the response
        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Movie by Type", speechText)
                .withReprompt(speechText)
                .build();
    }
}
