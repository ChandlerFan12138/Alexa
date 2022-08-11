package com.amazon.ask.recomo.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;

import java.util.Map;
import java.util.Optional;

public class SuggestByTypeHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("suggestbytype"));
    }
    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechText = "Dear movie fan, please say the type you are interested using choose syntax. For example, choose science fiction";
        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Please choose one type", speechText)
                .withReprompt(speechText)
                .build();
    }


}
