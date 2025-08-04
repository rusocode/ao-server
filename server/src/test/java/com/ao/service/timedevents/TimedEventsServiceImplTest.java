package com.ao.service.timedevents;

import com.ao.mock.MockFactory;
import com.ao.model.character.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class TimedEventsServiceImplTest {

    private TimedEvent event;
    private TimedEventsServiceImpl service;

    @BeforeEach
    public void setUp() throws Exception {
        service = new TimedEventsServiceImpl();
    }

    @Test
    public void testAddEventCharacterTimedEventLongLongLong() throws Exception {
        final Character chara = MockFactory.mockCharacter();
        event = MockFactory.mockTimedEvent(chara);

        // This one shouldn't be executed, the next is supposed to replace it and renew the timing.
        service.addEvent(chara, event, 100L, 10L, 20L);
        service.addEvent(chara, event, 20L, 10L, 40L);

        // Wait for the events to be executed.
        Thread.sleep(70L);

        verify(event, times(4)).execute();
    }

    @Test
    public void testAddEventCharacterTimedEventLong() throws Exception {
        final Character chara = MockFactory.mockCharacter();
        event = MockFactory.mockTimedEvent(chara);
        service.addEvent(chara, event, 10L);

        // Wait for the event to be executed.
        Thread.sleep(15L);

        verify(event).execute();
    }

    @Test
    public void testRemoveCharacterEvents() throws Exception {
        final Character chara = MockFactory.mockCharacter();
        final TimedEvent event2 = MockFactory.mockTimedEvent(chara);
        final TimedEvent event3 = MockFactory.mockTimedEvent(chara);

        event = MockFactory.mockTimedEvent(chara);

        service.addEvent(chara, event, 30L, 30L, 300L); // Ejecuta cada 30ms
        service.addEvent(chara, event2, 150L);          // Ejecuta a los 150ms
        service.addEvent(chara, event3, 250L);          // Ejecuta a los 250ms

        // Espera tiempo suficiente para que el primer evento se ejecute varias veces pero antes de que event2 y event3 tengan oportunidad
        Thread.sleep(100L); // event se ejecuta a 30ms, 60ms, 90ms

        service.removeCharacterEvents(chara);

        // El primer evento debería haberse ejecutado al menos 3 veces
        verify(event, atLeast(2)).execute();
        // Estos no deberian haberse ejecutado (cancelados antes de tiempo)
        verifyNoInteractions(event2, event3);
    }

    @Test
    public void testRemoveEvent() throws InterruptedException {
        final Character chara = MockFactory.mockCharacter();
        event = MockFactory.mockTimedEvent(chara);

        // Schedule for execution in 50ms, but cancel before.
        service.addEvent(chara, event, 50L);
        service.removeEvent(event);

        Thread.sleep(55L);

        verify(event, never()).execute();
    }

}
