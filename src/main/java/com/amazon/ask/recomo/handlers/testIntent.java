package com.amazon.ask.recomo.handlers;


import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;

import com.amazon.ask.model.Response;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.ArrayList;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class testIntent implements RequestHandler  {


    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("testIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {


        CancelandStopIntentHandler cancelandStopIntentHandler = new CancelandStopIntentHandler();

        //Construct the response
        return cancelandStopIntentHandler.handle(handlerInput);
    }

//    public void test(HandlerInput handlerInput){
//        ArrayList<movie> ret = new ArrayList<>();
//        ret.add(new movie());
//        Map<String, Object> persistentAttributes = new HashMap<>();
//        persistentAttributes.put("Ret",ret);
//        handlerInput.getAttributesManager().setSessionAttributes(persistentAttributes);
//
//        Map<String,Object> Ret = handlerInput.getAttributesManager().getSessionAttributes();
//
//        Object ret1 =  Ret.get("Ret");
//
//        System.out.println(ret1.getClass().toString());
//    }


}
