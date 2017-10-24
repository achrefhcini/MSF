package persistance;

import java.io.Serializable;
import java.lang.Integer;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
/**
 * Entity implementation class for Entity: Section
 *
 */
@Entity
@Table(name="fms_section")
public class Section implements Serializable {

	   
	@Id
	@GeneratedValue
	private Integer idSection;
	
	private String Title;
	private Date creationDate;
	private boolean isLocked;
	
	@ManyToOne
	@JoinColumn(name="creator",referencedColumnName="idMember")
	private User creator;
	
	@OneToOne
	@JoinColumn(name="administrator",referencedColumnName="idMember")
	private User administrator;
	
	@JsonIgnore
	@OneToMany(mappedBy="parentSection")
	private Set<Services> servicesAsParent;
	
	@JsonIgnore
	@OneToMany(mappedBy="parentSection", orphanRemoval=true)
	private Set<SubSection> subSectionsAsParent;
	
	private static final long serialVersionUID = 1L;

	public Section() {
		super();
	}   
	public Integer getIdSection() {
		return this.idSection;
	}

	public void setIdSection(Integer idSection) {
		this.idSection = idSection;
	}
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	public User getAdministrator() {
		return administrator;
	}
	public void setAdministrator(User administrator) {
		this.administrator = administrator;
	}
	public Set<Services> getServicesAsParent() {
		return servicesAsParent;
	}
	public void setServicesAsParent(Set<Services> servicesAsParent) {
		this.servicesAsParent = servicesAsParent;
	}
	public Set<SubSection> getSubSectionsAsParent() {
		return subSectionsAsParent;
	}
	public void setSubSectionsAsParent(Set<SubSection> subSectionsAsParent) {
		this.subSectionsAsParent = subSectionsAsParent;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public boolean isLocked() {
		return isLocked;
	}
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
}
