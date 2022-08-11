package com.amazon.ask.recomo.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class FallbackIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.FallbackIntent"))||input.matches(intentName("AMAZON.FallbackIntent"));

    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Sorry, something is wrong. Please say open new recomo to start again.";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("HelloWorld", speechText)
                .withReprompt(speechText)
                .withShouldEndSession(true)
                .build();
    }
}