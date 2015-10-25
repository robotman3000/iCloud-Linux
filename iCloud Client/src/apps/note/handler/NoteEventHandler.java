package apps.note.handler;

import apps.note.event.NoteEvent;
import icloud.handler.CloudEventHandler;

public interface NoteEventHandler extends CloudEventHandler {

	public void onNoteEvent(NoteEvent evt);
	public void onNoteCreateEvent(NoteEvent evt);
	public void onNoteDeleteEvent(NoteEvent evt);
	public void onNoteUpdateEvent(NoteEvent evt);

}
