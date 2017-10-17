package persistance;

import java.io.Serializable;
import java.lang.Integer;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Entity implementation class for Entity: RateTopic
 *
 */
@Entity
@Table(name="fms_rate_topic")
public class RateTopic implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idRateTopic;
	
	@ManyToOne
	@JoinColumn(name="reactedUser",referencedColumnName="idMember")
	@JsonManagedReference
	private User reactedUser;
	
	@ManyToOne
	@JoinColumn(name="topic",referencedColumnName="idTopic")
	@JsonBackReference
	private Topic topic;
	
	private static final long serialVersionUID = 1L;
    int rateValue;
    
	public int getRateValue() {
		return rateValue;
	}
	public void setRateValue(int rateValue) {
		this.rateValue = rateValue;
	}
	public RateTopic() {
		super();
	}   
	public Integer getIdRateTopic() {
		return this.idRateTopic;
	}
	public void setIdRateTopic(Integer idRateTopic) {
		this.idRateTopic = idRateTopic;
	}
	public User getReactedUser() {
		return reactedUser;
	}
	public void setReactedUser(User reactedUser) {
		this.reactedUser = reactedUser;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idRateTopic == null) ? 0 : idRateTopic.hashCode());
		result = prime * result + rateValue;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RateTopic other = (RateTopic) obj;
		if (idRateTopic == null) {
			if (other.idRateTopic != null)
				return false;
		} else if (!idRateTopic.equals(other.idRateTopic))
			return false;
		if (rateValue != other.rateValue)
			return false;
		return true;
	}
	
}
