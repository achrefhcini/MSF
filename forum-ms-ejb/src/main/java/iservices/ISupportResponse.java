package iservices;

import javax.ejb.Local;

import persistance.SupportResponse;

@Local
public interface ISupportResponse {
  public Boolean addSupportResponse(SupportResponse sr);
  public Boolean uploadImage();
  public Boolean call();
}
