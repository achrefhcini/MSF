package persistance;

import java.io.Serializable;
import java.lang.Integer;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Group
 *
 */
@Entity
@Table(name="fms_group")
public class Group implements Serializable {

	@Id
	@GeneratedValue
	private Integer idGroup;
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	private String name;
	@ManyToMany
	@JoinColumn(name="groupMembres",referencedColumnName="idMember")
	private Set<User> groupMembres;
	@ManyToOne
	@JoinColumn(name="creator",referencedColumnName="idMember")
	private User creator;
	
	private static final long serialVersionUID = 1L;

	public Group() {
		super();
	} 
	public Group(String name, User creator) {
		this.name = name;
		this.creator = creator;
		this.creationDate=new Date();
	}
	public Integer getIdGroup() {
		return this.idGroup;
	}

	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}
	public Set<User> getGroupMembres() {
		return groupMembres;
	}
	public void setGroupMembres(Set<User> groupMembres) {
		this.groupMembres = groupMembres;
	}
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
   
}
