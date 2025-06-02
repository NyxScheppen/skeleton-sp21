package gitlet;

import java.io.File;

public class blobs {

    private String name;

    private String sha_1;

    private byte[] content;

    public blobs(String name, String sha_1, byte[]content){
        this.name = name;
        this.content = content;
        this.sha_1 = sha_1;
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
