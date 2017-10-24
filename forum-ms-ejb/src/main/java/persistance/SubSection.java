package persistance;

import java.io.Serializable;
import java.lang.Integer;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

/**
 * Entity implementation class for Entity: SubSection
 *
 */
@Entity
@Table(name="fms_sub_section")
public class SubSection implements Serializable {

	   
	@Id
	@GeneratedValue
	private Integer idSubSection;
	
	private String Title;
	private Date creationDate;
	private boolean isHidden;
	
	@ManyToOne
	@JoinColumn(name="parentSection",referencedColumnName="idSection")
	private Section parentSection;
	
	@ManyToOne
	@JoinColumn(name="creator",referencedColumnName="idMember")
	private User creator;
	
	@OneToOne
	@JoinColumn(name="administrator",referencedColumnName="idMember")
	private User administrator;
	
	@ManyToMany
	@JsonIgnore
	@JoinColumn(name="moderators",referencedColumnName="idMember")
	private Set<User> moderators;
	
	@JsonIgnore
	@OneToMany(mappedBy="parentSubSection")
	private Set<Topic> topicsList;
	
	private static final long serialVersionUID = 1L;

	public SubSection() {
		super();
	}   
	public Integer getIdSubSection() {
		return this.idSubSection;
	}

	public void setIdSubSection(Integer idSubSection) {
		this.idSubSection = idSubSection;
	}
	public Section getParentSection() {
		return parentSection;
	}
	public void setParentSection(Section parentSection) {
		this.parentSection = parentSection;
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
	public Set<User> getModerators() {
		return moderators;
	}
	public void setModerators(Set<User> moderators) {
		this.moderators = moderators;
	}
	public Set<Topic> getTopicsList() {
		return topicsList;
	}
	public void setTopicsList(Set<Topic> topicsList) {
		this.topicsList = topicsList;
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
	public boolean isHidden() {
		return isHidden;
	}
	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}
}
