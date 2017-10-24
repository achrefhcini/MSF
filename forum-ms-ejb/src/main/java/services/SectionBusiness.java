package services;

import iservices.ISectionLocal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import persistance.Section;
import persistance.UserRole;
import utility.Statistics;

/**
 * Session Bean implementation class SectionBusiness
 */
@Stateless
@Local(ISectionLocal.class)
@LocalBean
public class SectionBusiness implements ISectionLocal {

	@PersistenceContext
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public SectionBusiness() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ISectionLocal#getAllSections()
     */
    public List<Section> getAllSections() {
    	List<Section> sections = entityManager.createQuery("Select s from Section s", Section.class).getResultList();
    	
//    	if (include_subsections)
//    		for (Section s : sections) {
//    			s.getSubSectionsAsParent().addAll(new HashSet<>(entityManager.createQuery("Select s from SubSection s where s.parentSection = :targetSection", SubSection.class)
//				    			.setParameter("targetSection", entityManager.find(Section.class, s.getIdSection()))
//				    			.getResultList()));
//    		}
    		
    	return sections;
    }

	/**
     * @see ISectionLocal#setSectionLock(int)
     * @see https://stackoverflow.com/questions/8307578/what-is-the-best-way-to-update-the-entity-in-jpa
     */
    public boolean setSectionLock(int sectionID) {
//    	entityManager.createQuery("UPDATE Section s SET s.isLocked = :lock WHERE ", Section.class)
//    		.executeUpdate();
//    	entityManager.refresh(section);
    	try {
	    	Section targetSection = (Section)entityManager.find(Section.class, sectionID);
	    		targetSection.setLocked(!targetSection.isLocked());
	    	
	    	return true;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }

	/**
     * @see ISectionLocal#addSection(Section)
     */
    public boolean addSection(Section section) {
    	try {
    		// the user that is going to add a section is the creator therefore check is on him
    		if (section.getCreator().getRole().equals(UserRole.MASTER)) {
	    		section.setCreationDate(new Date());
		    	entityManager.persist(section);
				return true;
    		}

    		// TODO add custom exception when user not allowed
    		return false;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }

	/**
     * @see ISectionLocal#editSection(Section)
     */
    public boolean editSection(Section section) {
    	try {
	    	Section targetSection = (Section)entityManager.find(Section.class, section.getIdSection());
	    		targetSection.setTitle(section.getTitle());
	    		
	    	return true;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }

	/**
     * @see ISectionLocal#getSectionByID(int)
     */
    public Section getSectionByID(int sectionID) {
    	Section foundSection = entityManager.find(Section.class, sectionID);
    	
        return foundSection;
    }

	/**
     * @see ISectionLocal#deleteSection(int)
     */
    public boolean deleteSection(int sectionID) {
    	try {
    		Section targetSection = getSectionByID(sectionID); 
    		if (targetSection.isLocked()) {
    			entityManager.remove(entityManager.find(Section.class, targetSection.getIdSection()));
        		return true;
    		}
    		
    		return false;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }

	
    @Override
	public List<Statistics> getPerYearPostStats(int startYearRange, int endYearRange, int targetSectionID) {
    	try {
    	
	    	List<Statistics> perYearTopicStats = new ArrayList<>();
	    	
	    	List results = entityManager.createQuery("Select extract(year from t.creationDate) as year, COUNT(t) as count_value "
		    								+ "from Section s, SubSection sub, Topic t "
		    								+ "where s.idSection = sub.parentSection "
		    								+ "and sub.idSubSection = t.parentSubSection "
		    								+ "and s.idSection = :sectionID "
		    								+ "and extract(year from t.creationDate) >= :startYear "
		    								+ "and extract(year from t.creationDate) <= :endYear "
		    								+ "group by extract(year from t.creationDate) ")
		    							.setParameter("sectionID", targetSectionID)
		    							.setParameter("startYear", startYearRange)
		    							.setParameter("endYear", endYearRange)
		    							.getResultList();
    	
	    	for (Object row : results) {
	    		Object[] row_conversion = (Object[]) row;
	    		
	    		String valueFirstCol = row_conversion[0].toString();
	    		String valueSecondCol = row_conversion[1].toString();
	    		
	    		int year = Integer.valueOf(valueFirstCol);
	    		int count_value = Integer.valueOf(valueSecondCol);
	    		
	    		Statistics stat = new Statistics();
	    			stat.initPerYearPostStats(year, count_value);
	    		
	    		perYearTopicStats.add(stat);
	    	}

	    	return perYearTopicStats;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return new ArrayList<Statistics>();
    	}
	}

	@Override
	public List<Statistics> getSectionsSortedByPopularity(int year) {
		try {
			List<Statistics> sectionsSortedByPopularity = new ArrayList<>();
			
			List results = entityManager.createQuery("Select s.idSection, COUNT(t) as count_value "
					+ "from Section s, SubSection sub, Topic t "
					+ "where s.idSection = sub.parentSection "
					+ "and sub.idSubSection = t.parentSubSection "
					+ "and extract (year from t.creationDate) = :targetYear "
					+ "group by s.idSection "
					+ "order by COUNT(t) DESC ")
					.setParameter("targetYear", year)
				.getResultList();
			
			for (Object row : results) {
				Object[] row_conversion = (Object[]) row;
	    		
	    		String valueFirstCol = row_conversion[0].toString();
	    		String valueSecondCol = row_conversion[1].toString();
	    		
	    		int sectionID = Integer.valueOf(valueFirstCol);
	    		int count_value = Integer.valueOf(valueSecondCol);
	    		
	    		Statistics stat = new Statistics();
	    			stat.initSectionsSortedByPopularity(sectionID, count_value);
	    		
	    		sectionsSortedByPopularity.add(stat);
			}
	
			return sectionsSortedByPopularity;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	
	@Override
	public List<Statistics> getPerMonthSectionActivity(int sectionID, int year) {
		try {
			List<Statistics> sectionActivityPerMonth = new ArrayList<>();
			
			List results = entityManager.createQuery("select extract(month from t.creationDate) as month, count(t) as count_value " + 
					"from Topic t, Section s, SubSection sub " + 
					"where t.parentSubSection = sub.idSubSection " + 
					"and sub.parentSection = s.idSection " + 
					"and extract(year from t.creationDate) = :targetYear " + 
					"and s.idSection = :sectionID " + 
					"group by extract(month from t.creationDate) " + 
					"order by extract(month from t.creationDate) ASC ")
					.setParameter("targetYear", year)
					.setParameter("sectionID", sectionID)
				.getResultList();
			
			for (Object row : results) {
				Object[] row_conversion = (Object[]) row;
	    		
	    		String valueFirstCol = row_conversion[0].toString();
	    		String valueSecondCol = row_conversion[1].toString();
	    		
	    		int month = Integer.valueOf(valueFirstCol);
	    		int count_value = Integer.valueOf(valueSecondCol);
	    		
	    		Statistics stat = new Statistics();
	    			stat.initPerMonthSectionActivity(month, count_value);
	    		
	    		sectionActivityPerMonth.add(stat);
			}
	
			return sectionActivityPerMonth;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
