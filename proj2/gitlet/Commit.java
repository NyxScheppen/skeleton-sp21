package gitlet;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Commit  implements Serializable{

    private Date timestamp;
    private String message;
    private String parent;
    private String hash_code;

    public  Set<String> file;

    public Commit(String message){
        this.message = message;
        this.timestamp = new Date(0);
        this.parent = null;
        this.hash_code = Utils.sha1(Utils.serialize(this));
    }

    public Commit(String message, String parent, String timestamp, Set<String> file){
        this.message = message;
        this.parent = parent;
        this.timestamp = new Date();
        this.file = file;
        hash_code = Utils.sha1(Utils.serialize(this));
    }

    public String getParent() {
        return this.parent;
    }

    public String getMessage() {
        return this.message;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public String getHash_code() {
        return hash_code;
    }
}
