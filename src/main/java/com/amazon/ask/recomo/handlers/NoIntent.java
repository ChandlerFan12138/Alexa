package com.amazon.ask.recomo.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.recomo.domain.movie;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

import static com.amazon.ask.request.Predicates.intentName;

public class NoIntent implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("AMAZON.NoIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {

        Map<String,Object> Ret = handlerInput.getAttributesManager().getSessionAttributes();

        Object ret =  Ret.get("Ret");

        ObjectMapper mapper = new ObjectMapper();

        ArrayList<movie> ret1 = mapper.convertValue(ret, mapper.getTypeFactory().constructCollectionType(ArrayList.class,movie.class));

        String speechText= "";
        if(ret1==null){
            speechText = "Recomo have no related movies for you. We will update later, You can say open new recomo to start again";
            Map<String, Object> sessionAttributes = new HashMap<>();
            sessionAttributes.put("Ret",ret1);
            handlerInput.getAttributesManager().setSessionAttributes(sessionAttributes);
            return handlerInput.getResponseBuilder()
                    .withSpeech(speechText)
                    .withSimpleCard("Byebye", speechText)
                    .withReprompt(speechText)
                    .withShouldEndSession(true)
                    .build();
        }
        else {
            int random = new Random().nextInt(ret1.size());
            movie movieNow = ret1.remove(random);
            Map<String, Object> sessionAttributes = new HashMap<>();
            sessionAttributes.put("Ret",ret1);
            sessionAttributes.put("movieNow",movieNow);
            handlerInput.getAttributesManager().setSessionAttributes(sessionAttributes);

            String speech1 = "OK. New movie for u. I want to suggest " + movieNow.movieName+ " for you... this movie is played by ";
            String speech2 = "";
            if(movieNow.actor!=null&&movieNow.actress!=null){
                speech2 = movieNow.actor+" and "+ movieNow.actress;
            }
            else if(movieNow.actor!=null){
                speech2 = movieNow.actor;
            }
            else  speech2 = movieNow.actress;
            String speech3 = "... And this movie typed as:  "+movieNow.type;
            String speech4 = "...Do you want to see what will going on in this movie? If so, please say Yes. Otherwise, please say No. I can suggest another movie for you.  ";

            speechText= speech1+speech2+speech3+speech4;

        }
//
        //Construct the response
        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Movie now", speechText)
                .withReprompt(speechText)
                .build();
    }
}
