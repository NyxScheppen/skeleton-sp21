package gitlet;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Commit  implements Serializable{

    private String timestamp;
    private String message;
    private String parent;
    private String hash_code;

    public Set<String> file;

    public Commit(String message, String parent, String timestamp, Set<String> file){
        this.message = message;
        this.parent = parent;
        if(timestamp == null){
            Date time = new Date();
            this.timestamp = time.toString();
        }else{
            this.timestamp = timestamp;
        }
        this.file = file;
        hash_code = Utils.sha1(this.timestamp + this.message + this.parent);
        for(String s : file){
            hash_code += Utils.sha1(s);
        }
    }

    public String getParent() {
        return this.parent;
    }

    public String getMessage() {
        return this.message;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getHash_code() {
        return hash_code;
    }
}
