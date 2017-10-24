package util;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class ResponseTmp {
       private String message;
       private String type;
       private int code;
       private String supportTrace_id;
       
       public ResponseTmp(){
    	   
       }
       
       
	public ResponseTmp(String message, String type, int code) {
		super();
		this.message = message;
		this.type = type;
		this.code = code;
		this.supportTrace_id = SupportUtil.getResponseProcess();
	}
	

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getSupportTrace_id() {
		return supportTrace_id;
	}
	public void setSupportTrace_id(String supportTrace_id) {
		this.supportTrace_id = supportTrace_id;
	}
       
    
       
}
