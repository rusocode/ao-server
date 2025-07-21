

package com.ao.service.timedevents;

import com.ao.context.ApplicationContext;
import com.ao.model.character.Character;
import com.ao.service.TimedEventsService;

/**
 * A timed event.
 */
public abstract class TimedEvent {

	protected static TimedEventsService service = ApplicationContext.getInstance(TimedEventsService.class);
	protected Character chara;
	
	/**
	 * Creates a new event.
	 * @param chara The character to apply the event
	 */
	public TimedEvent(Character chara) {
		this.chara = chara;
	}
	
	/**
	 * Register the event.
	 * @param delay Milliseconds to wait before event's execution.
	 */
	public void register(long delay) {
		service.addEvent(chara, this, delay);
	}

	/**
	 * Register the event with repeated execution.
	 * @param delay Milliseconds to wait before event's execution.
	 * @param interval Milliseconds between each event execution.
	 * @param repeatFor How long the event repetition will last, in milliseconds.
	 */
	public void register(long delay, long interval, long repeatFor) {
		service.addEvent(chara, this, delay, interval, repeatFor);
	}
	
	/**
	 * Retrieves the event's Character. 
	 * @return The character.
	 */
	public Character getCharacter() {
		return chara;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chara == null) ? 0 : chara.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimedEvent other = (TimedEvent) obj;
		if (chara == null) {
			if (other.chara != null)
				return false;
		} else if (!chara.equals(other.chara))
			return false;
		return true;
	}

	/**
	 * Executes the event.
	 */
	public abstract void execute();

}
