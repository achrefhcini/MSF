package persistance;

import java.io.Serializable;
import java.lang.Integer;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Services
 *
 */
@Entity
@Table(name="fms_service")
public class Services implements Serializable {

	   
	@Id
	@GeneratedValue
	private Integer idServices;
	
	@ManyToOne
	@JoinColumn(name="creator",referencedColumnName="idMember")
	private User creator;
	
	@ManyToOne
	@JoinColumn(name="parentSection",referencedColumnName="idSection")
	private Section parentSection;
	
	private static final long serialVersionUID = 1L;

	public Services() {
		super();
	}   
	public Integer getIdServices() {
		return this.idServices;
	}

	public void setIdServices(Integer idServices) {
		this.idServices = idServices;
	}
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	public Section getParentSection() {
		return parentSection;
	}
	public void setParentSection(Section parentSection) {
		this.parentSection = parentSection;
	}
   
}
