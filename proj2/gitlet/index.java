package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static gitlet.Repository.GITLET_DIR;
import static gitlet.Repository.blobsm;
import static gitlet.Utils.*;

public class index implements Serializable {
    public static final File Index = join(GITLET_DIR, "Index");
    // key = 文件名称,value = 文件内容
    private final Map<String, byte[]> added;

    private final Map<String, byte[]> tracked;

    private final Map<String, byte[]> removed;

    public index(){
        this.added = new HashMap<>();
        this.tracked = new HashMap<>();
        this.removed = new HashMap<>();
    }

    // 实施rm指令
    public void remove(String name){
        if(added.containsKey(name)){
            added.remove(name);
        }else if(tracked.containsKey(name)){
            removed.put(name, tracked.get(name));
            tracked.remove(name);
        }else{
            System.out.print("No reason to remove the file");
        }
        update();
    }

    // 清空缓冲区
    public void clear(){
        tracked.putAll(added);
        added.clear();
        removed.clear();
        update();
    }

    // 添加文件

    public void add(String key, byte[] content){
        added.put(key,content);
        update();
    }

    // 是否跟踪该文件

    public boolean contains(String name){
        if(tracked.containsKey(name)){
          return true;
        }
        return false;
    }


    public Set<String> Keyset(){
        return bloblism();
    }

    public Set<String> bloblism(){
        Set<String> sha1 = new HashSet<>();
        for(String key : added.keySet()){
            blobs newblob = new blobs(key, added.get(key));
            File newfile = join(blobsm, newblob.getSha_1());
            if(newfile.exists()){
                continue;
            }
            try {
                newfile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writeContents(newfile, newblob);
            sha1.add(newblob.getSha_1());
        }
        for(String key : tracked.keySet()){
            blobs newblob = new blobs(key, tracked.get(key));
            File newfile = join(blobsm, newblob.getSha_1());
            if(newfile.exists()){
                continue;
            }
            try {
                newfile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writeContents(newfile, newblob);
            sha1.add(newblob.getSha_1());
        }
        return sha1;
    }
    private void update(){
        writeContents(Index,this);
    }
}
