package resources;

import iservices.ISubSectionLocal;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import persistance.Section;
import persistance.SubSection;
import persistance.UserRole;

/**
 * Session Bean implementation class SubSectionBusiness
 */
@Stateless
@Local(ISubSectionLocal.class)
@LocalBean
public class SubSectionBusiness implements ISubSectionLocal {

	@PersistenceContext
	private EntityManager entityManager;
	
    /**
     * Default constructor. 
     */
    public SubSectionBusiness() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ISubSectionLocal#getAllSubSectionsOfSection(int)
     */
    public List<SubSection> getAllSubSectionsOfSection(int sectionID) {
    	try {
	    	return entityManager.createQuery("Select s from SubSection s where s.parentSection = :targetSection", SubSection.class)
	    			.setParameter("targetSection", entityManager.find(Section.class, sectionID))
	    			.getResultList();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }

	/**
     * @see ISubSectionLocal#addSubSecion(SubSection)
     */
    public boolean addSubSecion(SubSection subSection) {
    	try {
    		if (subSection.getCreator().getRole().equals(UserRole.MASTER) ||
    				subSection.getCreator().getRole().equals(UserRole.Section_ADMIN)) {
    			Section targetSection = entityManager.find(Section.class, subSection.getParentSection().getIdSection());
	    		subSection.setCreationDate(new Date());
	    		
	    		if (!targetSection.isLocked()) {
	    			entityManager.persist(subSection);
	    			return true;
	    		}
    		}
    		
    		return false;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }

	/**
     * @see ISubSectionLocal#deleteSubSection(int)
     */
    public boolean deleteSubSection(int subSectionID) {
        try {
        	entityManager.remove(entityManager.find(SubSection.class, subSectionID));
        	return true;
        }
        catch (Exception e) {
        	return false;
        }
    }

	/**
     * @see ISubSectionLocal#getSubSectionByID(int)
     */
    public SubSection getSubSectionByID(int id) {
    	return entityManager.find(SubSection.class, id);
    }

	/**
     * @see ISubSectionLocal#editSubSection(SubSection)
     */
    public boolean editSubSection(SubSection subSection) {
    	try {
    		SubSection targetSubSection = entityManager.find(SubSection.class, subSection.getIdSubSection());
    		
    		targetSubSection.setTitle(subSection.getTitle());
    		
    		return true;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }

	/**
     * @see ISubSectionLocal#setSubSectionVisibility(int)
     */
    public boolean setSubSectionVisibility(int subSectionID) {
    	try {
    		SubSection targetSubSection = entityManager.find(SubSection.class, subSectionID);
    		
    		targetSubSection.setHidden(!targetSubSection.isHidden());
    		
    		return true;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }

}