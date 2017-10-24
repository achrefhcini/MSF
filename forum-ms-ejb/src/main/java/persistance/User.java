package persistance;

import java.io.Serializable;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Entity implementation class for Entity: User
 *
 */

@Entity
@Table(name="fms_member")
public class User implements Serializable {

	   
	@Id
	@GeneratedValue
	private Integer idMember;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER,mappedBy="groupMembres")
	private Set<Group> groupsAsMembre;
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER,mappedBy="creator")
	private Set<Group> groupsAsCreator;
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER,mappedBy="creator")
	private Set<Section> sectionsAsCreator;
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER,mappedBy="creator")
	private Set<Topic> topicsAsCreator;
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER,mappedBy="creator")
	private Set<SubSection> subSectionsAsCreator;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER,mappedBy="moderators")
	private Set<SubSection> subSectionsAsModerator;
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER,mappedBy="creator")
	private Set<Services> servicesAsCreator;
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER,mappedBy="administrator")
	private Section sectionAsAdministrator;
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER,mappedBy="participant")
	private Ticket ticket;
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER,mappedBy="administrator")
	private SubSection subSectionAsAdministrator;
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER,mappedBy="owner")
	private Set<ActivityHistory> myHistories;
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER,mappedBy="creator")
	private Set<Event> myEvents;
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER,mappedBy="reactedUser")
	private Set<RateTopic> myReactions;
	
	// -------------------------- Achref relations  ---------------------------
	/*@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY,mappedBy="concernedUser")
	private Set<SupportTicket> concernedSupptTicket;
	@JsonIgnore
	@OneToMany(mappedBy="userCreation")
	private Set<SupportResponse> concernedResponseTicket;
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY,mappedBy="userReply")
	private Set<SupportWorkFollow> replyWorkFollow;
	*/
	public User(Integer idMember,String email, String username, String password,UserRole role) {
		this.idMember=idMember;
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = role;
		this.isEnabled = Boolean.TRUE;
		this.creationDate = new Date();
	}
	
	
	// ---------------------------------- end relations Achref -------------------
	private static final long serialVersionUID = 1L;
	
	
	
	private String email;
	private String username;
	private String password;
	@Enumerated(EnumType.STRING)
	private UserRole role;
	private Boolean isEnabled;
	private String firstName;
	private String lastName;
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthDate;
	// account creation date
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	private String image;
	private String phoneNumber;
	private UserGender gender;
	
	
	
	public User() {
	}  
	
	public User(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = UserRole.MEMBER;
		this.isEnabled = Boolean.TRUE;
		this.creationDate = new Date();
	}
 
	public Integer getId() {
		return this.idMember;
	}

	public void setId(Integer id) {
		this.idMember = id;
	}
	public Integer getIdMember() {
		return idMember;
	}
	public void setIdMember(Integer idMember) {
		this.idMember = idMember;
	}
	public Set<Group> getGroupsAsMembre() {
		return groupsAsMembre;
	}
	public void setGroupsAsMembre(Set<Group> groupsAsMembre) {
		this.groupsAsMembre = groupsAsMembre;
	}
	public Set<Group> getGroupsAsCreator() {
		return groupsAsCreator;
	}
	public void setGroupsAsCreator(Set<Group> groupsAsCreator) {
		this.groupsAsCreator = groupsAsCreator;
	}
	public Set<Section> getSectionsAsCreator() {
		return sectionsAsCreator;
	}
	public void setSectionsAsCreator(Set<Section> sectionsAsCreator) {
		this.sectionsAsCreator = sectionsAsCreator;
	}
	public Set<Topic> getTopicsAsCreator() {
		return topicsAsCreator;
	}
	public void setTopicsAsCreator(Set<Topic> topicsAsCreator) {
		this.topicsAsCreator = topicsAsCreator;
	}
	public Set<SubSection> getSubSectionsAsCreator() {
		return subSectionsAsCreator;
	}
	public void setSubSectionsAsCreator(Set<SubSection> subSectionsAsCreator) {
		this.subSectionsAsCreator = subSectionsAsCreator;
	}
	public Set<SubSection> getSubSectionsAsModerator() {
		return subSectionsAsModerator;
	}
	public void setSubSectionsAsModerator(Set<SubSection> subSectionsAsModerator) {
		this.subSectionsAsModerator = subSectionsAsModerator;
	}
	public Set<Services> getServicesAsCreator() {
		return servicesAsCreator;
	}
	public void setServicesAsCreator(Set<Services> servicesAsCreator) {
		this.servicesAsCreator = servicesAsCreator;
	}
	public Section getSectionAsAdministrator() {
		return sectionAsAdministrator;
	}
	public void setSectionAsAdministrator(Section sectionAsAdministrator) {
		this.sectionAsAdministrator = sectionAsAdministrator;
	}
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public SubSection getSubSectionAsAdministrator() {
		return subSectionAsAdministrator;
	}
	public void setSubSectionAsAdministrator(SubSection subSectionAsAdministrator) {
		this.subSectionAsAdministrator = subSectionAsAdministrator;
	}
	public Set<ActivityHistory> getMyHistories() {
		return myHistories;
	}
	public void setMyHistories(Set<ActivityHistory> myHistories) {
		this.myHistories = myHistories;
	}
	public Set<Event> getMyEvents() {
		return myEvents;
	}
	public void setMyEvents(Set<Event> myEvents) {
		this.myEvents = myEvents;
	}
	public Set<RateTopic> getMyReactions() {
		return myReactions;
	}
	public void setMyReactions(Set<RateTopic> myReactions) {
		this.myReactions = myReactions;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public UserGender getGender() {
		return gender;
	}
	public void setGender(UserGender gender) {
		this.gender = gender;
	}
   
}
