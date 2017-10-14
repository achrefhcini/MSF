package persistance;

import java.io.Serializable;
import java.lang.Integer;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ActivityHistory
 *
 */
@Entity
@Table(name="fms_activity_history")
public class ActivityHistory implements Serializable {

	   
	@Id
	@GeneratedValue
	private Integer idActivityHistory;
	
	@ManyToOne
	@JoinColumn(name="owner",referencedColumnName="idMember")
	private User owner;
	
	private static final long serialVersionUID = 1L;

	public ActivityHistory() {
		super();
	}   
	public Integer getIdActivityHistory() {
		return this.idActivityHistory;
	}

	public void setIdActivityHistory(Integer idActivityHistory) {
		this.idActivityHistory = idActivityHistory;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
   
}
