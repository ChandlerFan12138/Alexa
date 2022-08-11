package com.amazon.ask.recomo;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.amazon.ask.recomo.handlers.*;

public class RecomoHandler extends SkillStreamHandler {


    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new CancelandStopIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler(),
                        new SuggestByTypeHandler(),
                        new SuggestByActorOrActress(),
                        new IntroduceSpecificMovie(),
                        new SpecificMovieType(),
                        new SpecificMovieByPerson(),
                        new SpecificMovieByName(),
                        new testIntent(),
                        new YesIntent(),
                        new NoIntent(),
                        new FallbackIntentHandler())
                .build();
    }

    public RecomoHandler() {
        super(getSkill());
    }

}