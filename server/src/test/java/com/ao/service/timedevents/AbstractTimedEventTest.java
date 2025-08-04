package com.ao.service.timedevents;

import com.ao.model.character.Character;
import com.ao.service.TimedEventsService;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public abstract class AbstractTimedEventTest {

    protected TimedEvent event;

    @Test
    public void testRegisterLong() {
        final TimedEventsService service = mock(TimedEventsService.class);

        TimedEvent.service = service;
        event.register(1L);

        verify(service).addEvent(any(Character.class), event, anyLong());
    }

    @Test
    public void testRegisterLongLongLong() {
        final TimedEventsService service = mock(TimedEventsService.class);

        TimedEvent.service = service;
        event.register(1L, 1L, 1L);

        verify(service).addEvent(any(Character.class), event, anyLong(), anyLong(), anyLong());
    }

}
