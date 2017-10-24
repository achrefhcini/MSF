package iservices;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.Tuple;

import persistance.Section;
import utility.Statistics;

@Local
public interface ISection {
	/**
	 * sub sections are not included
	 * @param include_subsections removed param 
	 * @return
	 */
	public List<Section> getAllSections();
	/**
	 * allows the retrieval of a section by id
	 * @param sectionID
	 * @return
	 */
	public Section getSectionByID(int sectionID);
	/**
	 * only a master user can add sections
	 * @param section
	 */
	public boolean addSection(Section section);
	/**
	 * only a master user and the section section-administrator can edit sections
	 * this method does not handle changing the section administrator
	 * making sure the section administrator / master is the one editing is done on client side application (better than sending the user in the call)
	 * @param section
	 */
	public boolean editSection(Section section);
	/**
	 * only a master user can delete a section
	 * section must be locked
	 */
	public boolean deleteSection(int sectionID);
	/**
	 * only the section-administrator or a master user can lock/unlock section
	 * locked sections restrict what follow
	 * 		cannot add subsections
	 * 		can edit, delete sub sections
	 * 		cannot add new topics
	 * 		can still discuss in a topic
	 * this function works like a switch
	 */
	public boolean setSectionLock(int sectionID);
	/**
	 * only the section-administrator or a master user can check statistics
	 * section activity
	 * counts topics of the given section based on the years range
	 * @return a list of Statistics (year, value) for each year in the given years range 
	 */
	public List<Statistics> getPerYearPostStats(int startYearRange, int endYearRange, int targetSectionID);
	/**
	 * only the section-administrator or a master user can check statistics
	 * popular sections
	 * counts the number of topics in the given year of all sections
	 * @return a list of Statistics (sectionID, count-value)
	 */
	public List<Statistics> getSectionsSortedByPopularity(int year);
	/**
	 * return activity of each month of the given section 
	 */
	public List<Statistics> getPerMonthSectionActivity(int sectionID, int year);
	
}