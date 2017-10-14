package persistance;

import java.io.Serializable;
import java.lang.Integer;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Event
 *
 */
@Entity
@Table(name="fms_event")
public class Event implements Serializable {

	   
	@Id
	@GeneratedValue
	private Integer idEvent;
	
	@ManyToOne
	@JoinColumn(name="creator",referencedColumnName="idMember")
	private User creator;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="event")
	private Set<Ticket> listTicket;
	
	private static final long serialVersionUID = 1L;

	public Event() {
		super();
	}   
	public Integer getIdEvent() {
		return this.idEvent;
	}

	public void setIdEvent(Integer idEvent) {
		this.idEvent = idEvent;
	}
	
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	public Set<Ticket> getListTicket() {
		return listTicket;
	}
	public void setListTicket(Set<Ticket> listTicket) {
		this.listTicket = listTicket;
	}
   
}
