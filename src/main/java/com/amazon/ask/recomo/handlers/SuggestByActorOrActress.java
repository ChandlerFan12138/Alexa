package com.amazon.ask.recomo.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Optional;

public class SuggestByActorOrActress implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("SuggestByActororActress"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Dear movie fan, please say the name of actor or actress you are interested. You should say like Search plus name of person, like Search Anne Hathaway ";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Please say one name", speechText)
                .withReprompt(speechText)
                .build();
    }
}
