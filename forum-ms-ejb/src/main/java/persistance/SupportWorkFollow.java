package persistance;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "fms_support_workfollow")
public class SupportWorkFollow implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idReply;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="f_sT_sWF_join",joinColumns=@JoinColumn(name="supportWF_fk"),
	inverseJoinColumns=@JoinColumn(name="supportTicket_fk"))
	private Set<SupportTicket> supportTickets;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userReply_fk")
	private User userReply;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateReply;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEndReply;
	private String noticeWF;
	private String causeWF;
	private Boolean responseWF;
	private int stateWF;
	public int getIdReply() {
		return idReply;
	}
	public void setIdReply(int idReply) {
		this.idReply = idReply;
	}
	public Set<SupportTicket> getSupportTickets() {
		return supportTickets;
	}
	public void setSupportTickets(Set<SupportTicket> supportTickets) {
		this.supportTickets = supportTickets;
	}
	public User getUserReply() {
		return userReply;
	}
	public void setUserReply(User userReply) {
		this.userReply = userReply;
	}
	public Date getDateReply() {
		return dateReply;
	}
	public void setDateReply(Date dateReply) {
		this.dateReply = dateReply;
	}
	public Date getDateEndReply() {
		return dateEndReply;
	}
	public void setDateEndReply(Date dateEndReply) {
		this.dateEndReply = dateEndReply;
	}
	public String getNoticeWF() {
		return noticeWF;
	}
	public void setNoticeWF(String noticeWF) {
		this.noticeWF = noticeWF;
	}
	public String getCauseWF() {
		return causeWF;
	}
	public void setCauseWF(String causeWF) {
		this.causeWF = causeWF;
	}
	public Boolean getResponseWF() {
		return responseWF;
	}
	public void setResponseWF(Boolean responseWF) {
		this.responseWF = responseWF;
	}
	public int getStateWF() {
		return stateWF;
	}
	public void setStateWF(int stateWF) {
		this.stateWF = stateWF;
	}
	
	
	
}
