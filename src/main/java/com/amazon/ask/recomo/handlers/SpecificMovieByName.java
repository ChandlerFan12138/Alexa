package com.amazon.ask.recomo.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.recomo.dao.SQLSyntax;
import com.amazon.ask.recomo.domain.movie;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;


import static com.amazon.ask.request.Predicates.intentName;

public class SpecificMovieByName implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("SpecificMovieByName"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        //step 1. Get the name of Movie input
        IntentRequest one = (IntentRequest)handlerInput.getRequest();
        Map<String, Slot> temp = one.getIntent().getSlots();
        String name ="";
        for(Slot slot:temp.values()){
            if(slot.getValue()!=null&&slot.getValue().length()>0){
                name = slot.getValue();
                break;
            }
        }

//        step 2. SQL process, search the name in the database.
        SQLSyntax newSQL = new SQLSyntax();
        movie ret = new movie();
        try {
            ret = newSQL.SelectByMovieName(name);
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }


        //design the speechtext of actor and actress
        String speech1 = "I am introducing " + ret.movieName+ " for you, this movie is played by ";
        String speech2 = "";
        if(ret.actor!=null&&ret.actress!=null){
            speech2 = ret.actor+" and "+ ret.actress;
        }
        else if(ret.actor!=null){
            speech2 = ret.actor;
        }
        else  speech2 = ret.actress;
        String speech3 = "... Here is the description of this movie: "+ret.description;
        String speech4 = "... This movie ranked "+ ret.rank + " in IMDB, hope u like it. Now you can say open new recomo to start it again";
        String speechText  = speech1+speech2+speech3+speech4;

        //Construct the response
        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Movie By Name", speechText)
                .withReprompt(speechText)
                .withShouldEndSession(true)
                .build();
    }
}
