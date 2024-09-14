package com.github.olegushak.FTT.config;

import com.github.olegushak.FTT.statemachine.event.ParameterEvent;
import com.github.olegushak.FTT.statemachine.state.ParameterState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

import static com.github.olegushak.FTT.statemachine.event.ParameterEvent.DEP_DATE_SETTED;
import static com.github.olegushak.FTT.statemachine.event.ParameterEvent.FROM_SETTED;
import static com.github.olegushak.FTT.statemachine.event.ParameterEvent.RET_DATE_SETTED;
import static com.github.olegushak.FTT.statemachine.event.ParameterEvent.SET_DEP_DATE;
import static com.github.olegushak.FTT.statemachine.event.ParameterEvent.SET_FROM;
import static com.github.olegushak.FTT.statemachine.event.ParameterEvent.SET_RET_DATE;
import static com.github.olegushak.FTT.statemachine.event.ParameterEvent.SET_TO;
import static com.github.olegushak.FTT.statemachine.event.ParameterEvent.TO_SETTED;
import static com.github.olegushak.FTT.statemachine.state.ParameterState.READY_FOR_MODIFY;
import static com.github.olegushak.FTT.statemachine.state.ParameterState.SEARCH;
import static com.github.olegushak.FTT.statemachine.state.ParameterState.UPDATE_DEP_DATE;
import static com.github.olegushak.FTT.statemachine.state.ParameterState.UPDATE_FROM;
import static com.github.olegushak.FTT.statemachine.state.ParameterState.UPDATE_RET_DATE;
import static com.github.olegushak.FTT.statemachine.state.ParameterState.UPDATE_TO;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<ParameterState, ParameterEvent> {

    @Override
    public void configure(final StateMachineConfigurationConfigurer<ParameterState, ParameterEvent> config) throws Exception {
        config
                .withConfiguration()
                .listener(new StateMachineListenerAdapter<>() {
                    @Override
                    public void stateChanged(State<ParameterState, ParameterEvent> from, State<ParameterState, ParameterEvent> to) {
                        System.out.println("State changed from " + from + " to " + to);
                    }
                });
    }

    @Override
    public void configure(final StateMachineStateConfigurer<ParameterState, ParameterEvent> states) throws Exception {
        states
                .withStates()
                .initial(READY_FOR_MODIFY)
                .end(SEARCH)
                .states(EnumSet.allOf(ParameterState.class));

    }

    @Override
    public void configure(final StateMachineTransitionConfigurer<ParameterState, ParameterEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(READY_FOR_MODIFY)
                .target(UPDATE_FROM)
                .event(SET_FROM)

                .and()
                .withExternal()
                .source(UPDATE_FROM)
                .target(READY_FOR_MODIFY)
                .event(FROM_SETTED)

                .and()
                .withExternal()
                .source(READY_FOR_MODIFY)
                .target(UPDATE_TO)
                .event(SET_TO)

                .and()
                .withExternal()
                .source(UPDATE_TO)
                .target(READY_FOR_MODIFY)
                .event(TO_SETTED)

                .and()
                .withExternal()
                .source(READY_FOR_MODIFY)
                .target(UPDATE_DEP_DATE)
                .event(SET_DEP_DATE)

                .and()
                .withExternal()
                .source(UPDATE_DEP_DATE)
                .target(READY_FOR_MODIFY)
                .event(DEP_DATE_SETTED)

                .and()
                .withExternal()
                .source(READY_FOR_MODIFY)
                .target(UPDATE_RET_DATE)
                .event(SET_RET_DATE)

                .and()
                .withExternal()
                .source(UPDATE_RET_DATE)
                .target(READY_FOR_MODIFY)
                .event(RET_DATE_SETTED);
    }
}
