package iservices;

import java.util.List;

import javax.ejb.Local;

import persistance.SubSection;

@Local
public interface ISubSection {
	public List<SubSection> getAllSubSectionsOfSection(int sectionID);
	public SubSection getSubSectionByID(int id);
	/**
	 * only a master user and the section administrator can add sub sections
	 * section must not be locked
	 * @param subSection 
	 */
	public boolean addSubSecion(SubSection subSection);
	/**
	 * only a master user and the section and sub section administrator can edit sub sections
	 * @param subSection
	 */
	public boolean editSubSection(SubSection subSection);
	/**
	 * only a master user and a section administrator can set visibility
	 * hiding a sub section sets what follow
	 * 		cannot add new topics
	 * 		can still discuss in a topic
	 * this function works like a switch 
	 */
	public boolean setSubSectionVisibility(int subSectionID);
	/**
	 * only a master user and the section administrator can delete sub sections 
	 * @param subSectionID
	 * @return
	 */
	public boolean deleteSubSection(int subSectionID);
}
