

package com.ao.service.timedevents;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import com.ao.model.character.Character;
import com.ao.service.TimedEventsService;

/**
 * Implementation of TimedEventService using a simple daemon Timer thread.
 */
public class TimedEventsServiceImpl implements TimedEventsService {

	protected ConcurrentHashMap<Character, Map<TimedEvent, TimerTaskAdapter>> events = new ConcurrentHashMap<Character, Map<TimedEvent, TimerTaskAdapter>>();
	protected Timer timer = new Timer(true);
	
	/**
	 * Adapts TimedEvent as TimerTask to be used by Timer.
	 */
	protected class TimerTaskAdapter extends TimerTask {
		
		protected final TimedEvent event;
		protected int executionTimes;
		
		/**
		 * Creates a new adapter.
		 * 
		 * @param event The event to adapt.
		 * @param interval Delay in milliseconds to wait before event's execution.
		 * @param repeatFor How long the event repetition will last, in milliseconds.
		 */
		public TimerTaskAdapter(TimedEvent event, long interval, long repeatFor) {
			this.event = event;
			
			// How many times the event should be executed?
			executionTimes = (int) (repeatFor / interval);
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.TimerTask#run()
		 */
		@Override
		public void run() {
			event.execute();
			
			// Stop the event repetition once event's life time ends.
			if (--executionTimes == 0) {
				getOuterType().removeEvent(event);
			}
		}

		/**
		 * Retrieves the outer type of this inner class.
		 * @return The outer type of this inner class.
		 */
		private TimedEventsServiceImpl getOuterType() {
			return TimedEventsServiceImpl.this;
		}

		/**
		 * Retrieves the number of executions the task has to perform before completing.
		 * @return The number of executions the task has to perform before completing.
		 */
		public int getExecutionTimes() {
			return executionTimes;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.service.TimedEventsService#addEvent(ao.model.character.Character, com.ao.service.timedevents.TimedEvent, long, long, long)
	 */
	@Override
	public void addEvent(Character chara, TimedEvent event, long delay, long interval, long repeatFor) {
		Map<TimedEvent, TimerTaskAdapter> characterEvents = events.get(chara);
		
		// If the character had no previous events, create his events container.
		if (characterEvents == null) {
			// The putIfAbsent prevents further synchronization
			events.putIfAbsent(chara, new HashMap<TimedEvent, TimerTaskAdapter>());
			characterEvents = events.get(chara);
		}
		
		TimerTaskAdapter adaptedEvent = new TimerTaskAdapter(event, interval, repeatFor);
		
		synchronized (characterEvents) {
			if (events.get(chara) != characterEvents) {
				return;	// This means a concurrent call to removeCharacterEvents has occurred. "Remove" this one too.
			}
			
			// Single execution or repeated?
			if (adaptedEvent.getExecutionTimes() == 1) {
				timer.schedule(adaptedEvent, delay);
			} else {
				timer.scheduleAtFixedRate(adaptedEvent, delay, interval);
			}
			
			TimerTaskAdapter previous = characterEvents.put(event, adaptedEvent);
			
			// The same event was already filed for the character, renew the execution delay. 
			if (previous != null) {
				previous.cancel();
				timer.purge();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.service.TimedEventsService#addEvent(ao.model.character.Character, com.ao.service.timedevents.TimedEvent, long)
	 */
	@Override
	public void addEvent(Character chara, TimedEvent event, long delay) {
		addEvent(chara, event, delay, delay, delay);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ao.service.TimedEventsService#removeCharacterEvents(ao.model.character.Character)
	 */
	@Override
	public void removeCharacterEvents(Character chara) {
		Map<TimedEvent, TimerTaskAdapter> characterEvents = events.remove(chara);
		
		if (characterEvents == null) {
			return;
		}
		
		// This is synchronized to prevent race conditions against addEvent and RemoveEvent
		synchronized (characterEvents) {
			Iterator<TimedEvent> it = characterEvents.keySet().iterator();
			
			// Remove them all!
			while (it.hasNext()) {
				characterEvents.get(it.next()).cancel();
				it.remove();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.ao.service.TimedEventsService#removeEvent(ao.service.timedevents.TimedEvent)
	 */
	@Override
	public void removeEvent(TimedEvent event) {
		Map<TimedEvent, TimerTaskAdapter> characterEvents = events.get(event.getCharacter());
		
		if (characterEvents != null) {
			TimerTaskAdapter task;
			
			// Prevent race condition against removeCharacterEvents
			synchronized (characterEvents) {
				task = characterEvents.remove(event);
			}
			
			if (task != null) {
				task.cancel();
			}
		}
	}

}
