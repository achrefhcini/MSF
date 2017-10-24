package persistance;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import util.ResponseTypeContent;

@Entity
@Table(name = "fms_support_response")
public class SupportResponse implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne()
	@JoinColumn(name="responseTicket_fk")
	private SupportTicket support_ticket;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateResponse;
	@Enumerated(EnumType.STRING)
	private ResponseTypeContent typeResponse;
	private String contentResponse;
	private Boolean readState;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdateState;
	private boolean fixedState;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateFixedState;
	@ManyToOne()
	@JoinColumn(name="createdUser_fk")
	private User userCreation;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public SupportTicket getSupport_ticket() {
		return support_ticket;
	}
	public void setSupport_ticket(SupportTicket support_ticket) {
		this.support_ticket = support_ticket;
	}
	public Date getDateResponse() {
		return dateResponse;
	}
	public void setDateResponse(Date dateResponse) {
		this.dateResponse = dateResponse;
	}
	public ResponseTypeContent getTypeResponse() {
		return typeResponse;
	}
	public void setTypeResponse(ResponseTypeContent typeResponse) {
		this.typeResponse = typeResponse;
	}
	public String getContentResponse() {
		return contentResponse;
	}
	public void setContentResponse(String contentResponse) {
		this.contentResponse = contentResponse;
	}
	public Boolean getReadState() {
		return readState;
	}
	public void setReadState(Boolean readState) {
		this.readState = readState;
	}
	public Date getDateUpdateState() {
		return dateUpdateState;
	}
	public void setDateUpdateState(Date dateUpdateState) {
		this.dateUpdateState = dateUpdateState;
	}
	public boolean isFixedState() {
		return fixedState;
	}
	public void setFixedState(boolean fixedState) {
		this.fixedState = fixedState;
	}
	public Date getDateFixedState() {
		return dateFixedState;
	}
	public void setDateFixedState(Date dateFixedState) {
		this.dateFixedState = dateFixedState;
	}
	
	
	
	
	
	
	
	
	
	
	

}
