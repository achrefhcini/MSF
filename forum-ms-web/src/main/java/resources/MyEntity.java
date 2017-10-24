package resources;
import javax.ws.rs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;
 
public class MyEntity {
 
    public MyEntity() {
    }
 
    private byte[] data;
 
    public byte[] getData() {
        return data;
    }
 
    @FormParam("uploadedFile")
    @PartType("application/octet-stream")
    public void setData(byte[] data) {
        this.data = data;
    }
 
}