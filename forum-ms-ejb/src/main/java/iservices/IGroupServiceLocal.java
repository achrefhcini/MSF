package iservices;


import javax.ejb.Local;
import javax.json.JsonObject;

import persistance.Group;


@Local
public interface IGroupServiceLocal 
{
	public JsonObject addGroup(Group group,int creator );
	

}
