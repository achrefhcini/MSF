package persistance;

import java.io.Serializable;
import java.lang.Integer;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Ticket
 *
 */
@Entity
@Table(name="fms_ticket")
public class Ticket implements Serializable {

	   
	@Id
	@GeneratedValue
	private Integer idTicket;
	
	@OneToOne
	@JoinColumn(name="participant",referencedColumnName="idMember")
	private User participant;
	
	@ManyToOne
	@JoinColumn(name="event",referencedColumnName="idEvent")
	private Event event;
	
	private static final long serialVersionUID = 1L;

	public Ticket() {
		super();
	}   
	public Integer getIdTicket() {
		return this.idTicket;
	}

	public void setIdTicket(Integer idTicket) {
		this.idTicket = idTicket;
	}
	public User getParticipant() {
		return participant;
	}
	public void setParticipant(User participant) {
		this.participant = participant;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
   
}
