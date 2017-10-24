package persistance;

import java.io.Serializable;
import java.lang.Integer;
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
   
}
