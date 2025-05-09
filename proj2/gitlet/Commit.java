package gitlet;
import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.Set;

/** Represents a gitlet commit object.
 *
 *  创建一个可以序列化的commit对象
 *
 *  仅限于此，关于commit文件夹的创建请看repository
 *
 *  我突然想到很不妙的事情，他妈的checkout的逻辑还没动
 *
 *  嗯，很有可能会大改，饶了我吧
 *
 *  @author nyx
 */

public class Commit implements Serializable {

    private String timestamp;
    /** The message of this Commit. */
    private String message;
    private String parent;
    // need？
    private String hash_code;

    public Set files;

    public Commit(String message, String parent, String timestamp, Set file){
        this.message = message;
        this.parent = parent;
        this.timestamp = timestamp;
        this.files = file;
        hash_code = Utils.sha1(this);
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
