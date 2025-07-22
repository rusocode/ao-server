package com.ao.service;

import com.ao.model.character.Character;
import com.ao.service.timedevents.TimedEvent;

/**
 * Timed events manager.
 */

public interface TimedEventsService {

    /**
     * Schedules a new event for repeated execution.
     *
     * @param chara     character to apply the event
     * @param event     event itself
     * @param delay     milliseconds to wait before the first event's execution
     * @param interval  milliseconds between each event's execution
     * @param repeatFor how long the event repetition will last, in milliseconds
     */
    void addEvent(Character chara, TimedEvent event, long delay, long interval, long repeatFor);

    /**
     * Schedules a new event.
     *
     * @param chara character to apply the event
     * @param event event itself
     * @param delay milliseconds to wait before the event's execution
     */
    void addEvent(Character chara, TimedEvent event, long delay);

    /**
     * Removes all character's events.
     *
     * @param chara character
     */
    void removeCharacterEvents(Character chara);

    /**
     * Removes the given event.
     *
     * @param event event to remove
     */
    void removeEvent(TimedEvent event);

}
