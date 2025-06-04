package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.sha1;

public class blobs implements Serializable {

    private String name;

    private String sha_1;

    private byte[] content;

    public blobs(String name, byte[]content){
        this.name = name;
        this.content = content;
        this.sha_1 = sha1(name, content);
    }

    public byte[] getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getSha_1() {
        return sha_1;
    }
}
