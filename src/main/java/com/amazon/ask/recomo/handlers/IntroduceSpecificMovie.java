package com.amazon.ask.recomo.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Optional;

public class IntroduceSpecificMovie implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("IntroduceSpecificMovie"));
    }
    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechText = "Dear movie fan, please say the movie name using the get syntax, for example get Interstellar.";
        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Please say one Movie Name", speechText)
                .withReprompt(speechText)
                .build();
    }
}
