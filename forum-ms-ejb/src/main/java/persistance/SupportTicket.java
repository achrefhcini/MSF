package persistance;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import util.StateTicket;
import util.TypeTicket;

@Entity
@Table(name = "fms_support_ticket")
public class SupportTicket implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int IdSupTicket;
	private String ticketNumber;
	private String universe;
	private String category;
	@Enumerated(EnumType.STRING)
	private TypeTicket typeTicket;
	private String subject;
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	private int state;
	@ManyToOne()
	@JoinColumn(name="concernedUser_fk")
	private User concernedUser;
   
	@JsonIgnore
	@OneToMany(mappedBy="support_ticket")
	private Set<SupportResponse> responseTickets;
	
	
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER,mappedBy="supportTickets",cascade=CascadeType.ALL)
	private Set<SupportWorkFollow> supportWFs;
	
	public SupportTicket(){
		
	}
	

	public SupportTicket( String universe, String category, TypeTicket typeTicket,String ticketNumber,
			String subject, User concernedUser) {
		
		  
		
		this.ticketNumber = ticketNumber;
		this.universe = universe;
		this.category = category;
		for(TypeTicket typeticketfield:TypeTicket.values()){
			if(typeTicket==typeticketfield){
				this.typeTicket = typeticketfield;
			}
			else if (typeTicket!=typeticketfield){
				this.typeTicket = null;
			}
		}
		
		this.subject = subject;
		this.creationDate = new Date();
		
		this.updateDate = null;
		this.state = StateTicket.OPEN;
		this.concernedUser = concernedUser;
		this.supportWFs = null;
	}


	


	public int getIdSupTicket() {
		return IdSupTicket;
	}




	public void setIdSupTicket(int idSupTicket) {
		IdSupTicket = idSupTicket;
	}



	@Override
	public String toString() {
		return "SupportTicket [IdSupTicket=" + IdSupTicket + ", ticketNumber=" + ticketNumber + ", universe=" + universe
				+ ", category=" + category + ", typeTicket=" + typeTicket + ", subject=" + subject + ", creationDate="
				+ creationDate + ", updateDate=" + updateDate + ", state=" + state + ", concernedUser=" + concernedUser
				+ ", supportWFs=" + supportWFs + "]";
	}


	public int getID() {
		return IdSupTicket;
	}




	public void setID(int iD) {
		IdSupTicket = iD;
	}




	public String getTicketNumber() {
		return ticketNumber;
	}




	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}




	public String getUniverse() {
		return universe;
	}




	public void setUniverse(String universe) {
		this.universe = universe;
	}




	public String getCategory() {
		return category;
	}




	public void setCategory(String category) {
		this.category = category;
	}




	public TypeTicket getTypeTicket() {
		return typeTicket;
	}




	public void setTypeTicket(TypeTicket typeTicket) {
		this.typeTicket = typeTicket;
	}




	public String getSubject() {
		return subject;
	}




	public void setSubject(String subject) {
		this.subject = subject;
	}




	public Date getCreationDate() {
		return creationDate;
	}




	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}




	public Date getUpdateDate() {
		return updateDate;
	}




	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}




	public int getState() {
		return state;
	}




	public void setState(int state) {
		this.state = state;
	}




	public User getConcernedUser() {
		return concernedUser;
	}




	public void setConcernedUser(User concernedUser) {
		this.concernedUser = concernedUser;
	}




	public Set<SupportWorkFollow> getSupportWFs() {
		return supportWFs;
	}




	public void setSupportWFs(Set<SupportWorkFollow> supportWFs) {
		this.supportWFs = supportWFs;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
