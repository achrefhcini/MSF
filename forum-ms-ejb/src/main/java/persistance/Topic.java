package persistance;

import java.io.Serializable;
import java.lang.Integer;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Entity implementation class for Entity: Topic
 *
 */
@Entity
@Table(name="fms_topic")
public class Topic implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idTopic;
	
    private String Titre_topic;
    private String Description;
    private float Moyenne;
    @Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
    @Temporal(TemporalType.TIMESTAMP)
	private Date blockDate;
    @Temporal(TemporalType.TIMESTAMP)
	private Date deBlockDate;
    
	private int periode;
    private int isBlocked;
	
	@ManyToOne
	@JoinColumn(name="parentSubSection",referencedColumnName="idSubSection")
	private SubSection parentSubSection;
	
	@ManyToOne
	@JoinColumn(name="creator",referencedColumnName="idMember")
	@JsonManagedReference
	private User creator;
	
	
    @OneToMany(fetch = FetchType.EAGER,mappedBy="topic")
    @JsonManagedReference
	private Set<RateTopic> reactions;
	
	private static final long serialVersionUID = 1L;

	public Topic(String titre_topic, String description) {
		super();
		Titre_topic = titre_topic;
		Description = description;
		this.isBlocked = 0;
		this.creationDate = new Date();
	}
	public Topic() {
		super();
	} 
	
	public Integer getIdTopic() {
		return this.idTopic;
	}

	public void setIdTopic(Integer idTopic) {
		this.idTopic = idTopic;
	}
	public String getTitre_topic() {
		return Titre_topic;
	}

	public void setTitre_topic(String titre_topic) {
		Titre_topic = titre_topic;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
	public Topic(String titre_topic, String description, User creator) {
		Titre_topic = titre_topic;
		Description = description;
		this.creator = creator;
		this.isBlocked = 0;
		this.creationDate = new Date();
	}
	public SubSection getParentSubSection() {
		return parentSubSection;
	}
	public void setParentSubSection(SubSection parentSubSection) {
		this.parentSubSection = parentSubSection;
	}
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	public Set<RateTopic> getReactions() {
		return reactions;
	}
	public void setReactions(Set<RateTopic> reactions) {
		this.reactions = reactions;
	}
	 public float getMoyenne() {
		return Moyenne;
	}
	public void setMoyenne(float moyenne) {
		Moyenne = moyenne;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public int getIsBlocked() {
		return isBlocked;
	}
	public void setIsBlocked(int isBlocked) {
		this.isBlocked = isBlocked;
	}
	public Date getBlockDate() {
		return blockDate;
	}
	public void setBlockDate(Date blockDate) {
		this.blockDate = blockDate;
	}
	public int getPeriode() {
		return periode;
	}
	public void setPeriode(int periode) {
		this.periode = periode;
	}
	 public Date getDeBlockDate() {
		return deBlockDate;
	}
	public void setDeBlockDate(Date deBlockDate) {
		this.deBlockDate = deBlockDate;
	}
}
