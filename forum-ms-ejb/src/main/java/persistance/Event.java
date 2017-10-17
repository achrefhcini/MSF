package persistance;

import java.io.Serializable;

import java.lang.Integer;
import java.util.Date;


import javax.persistence.*;

/**
 * Entity implementation class for Entity: Event
 *
 */
@Entity
@Table(name="fms_event")
public class Event implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idEvent;
	
	@ManyToOne
	@JoinColumn(name="creator",referencedColumnName="idMember")
	private User creator;
	
	/*@OneToMany(fetch = FetchType.EAGER,mappedBy="event")
	private Set<Ticket> listTicket;*/
	
	private boolean Enable;
	private Integer NbPlace;
	private String Title;
	@Temporal(TemporalType.TIMESTAMP)
	private Date StartDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date EndDate;
	private double price;
	private String image;
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
	/*public Set<Ticket> getListTicket() {
		return listTicket;
	}
	public void setListTicket(Set<Ticket> listTicket) {
		this.listTicket = listTicket;*/
	//}
	public boolean isEnable() {
		return Enable;
	}
	public void setEnable(boolean enable) {
		Enable = enable;
	}
	public Integer getNbPlace() {
		return NbPlace;
	}
	public void setNbPlace(Integer nbPlace) {
		NbPlace = nbPlace;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	
	public Date getStartDate() {
		return StartDate;
	}
	
	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}
	public Date getEndDate() {
		return EndDate;
	}
	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "Event [idEvent=" + idEvent + ", Enable=" + Enable + ", NbPlace=" + NbPlace + ", Title=" + Title
				+ ", StartDate=" + StartDate + ", EndDate=" + EndDate + ", price=" + price + ", image=" + image + "]";
	}
   
}
