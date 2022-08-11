package com.amazon.ask.recomo.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.recomo.dao.SQLSyntax;
import com.amazon.ask.recomo.domain.movie;

import java.sql.SQLException;
import java.util.*;

import static com.amazon.ask.request.Predicates.intentName;

public class SpecificMovieByPerson implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("SpecificMovieByPerson"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        //step 1. Get the name of Actor or Actress
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
        ArrayList<movie> ret = new ArrayList<>();
        try {
            ret = newSQL.SelectByPeople(name);
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



        //design the speechtext
        String speech1 = "I want to suggest  " + movieNow.movieName + "  for you. ";
        String speech2 = "This movie is played by ";
        String speech3 = "";
        if(movieNow.actor!=null&&movieNow.actress!=null){
            speech3 = movieNow.actor+" and "+ movieNow.actress;
        }
        else if(ret.get(random).actor!=null){
            speech3 = movieNow.actor;
        }
        else  speech3 = movieNow.actress;

        String speech4 = ". Do you want to see what will going on in this movie? If so, please say Yes. Otherwise, please say No. I can suggest another movie for you. ";
        String speechText  = speech1+speech2+speech3+speech4;


        //design the speechtext of actor and actress
//        int random = new Random().nextInt(ret.size());
//        String speech1 = "I am suggesting" + ret.get(random).movieName+ " for you... this movie is played by ";
//        String speech2 = "";
//        if(ret.get(random).actor!=null&&ret.get(random).actress!=null){
//            speech2 = ret.get(random).actor+" and "+ ret.get(random).actress;
//        }
//        else if(ret.get(random).actor!=null){
//            speech2 = ret.get(random).actor;
//        }
//        else  speech2 = ret.get(random).actress;
//        String speech3 = "... Here is the description of this movie:  "+ret.get(random).description;
//        String speech4 = "... This movie ranked "+ ret.get(random).rank + " in IMDB, hope u like it";
//        String speechText  = speech1+speech2+speech3+speech4;

        //Construct the response
        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Movie by Person", speechText)
                .withReprompt(speechText)
                .build();
    }
}
