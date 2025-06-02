package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static gitlet.Repository.GITLET_DIR;
import static gitlet.Utils.*;

public class index implements Serializable {
    public static final File Index = join(GITLET_DIR, "Index");
    // key = 文件名称,value = 文件内容
    private final Map<String, byte[]> added;
    public index(){
        this.added = new HashMap<>();
    }

    // 在缓冲区里移除文件
    public void remove(String name){
        if(added.containsKey(name)){
            added.remove(name);
        }
        update();
    }

    // 清空缓冲区
    public void clear(){
        writeContents(Index);
        update();
    }

    // 添加文件

    public void add(String key, byte[] content){
        added.put(key,content);
        update();
    }

    // 覆盖缓冲区

    public void update(){
        writeContents(Index,this);
    }

    // 是否包含该文件

    public boolean contains(String name){
        if(added.containsKey(name)){
          return true;
        }
        return false;
    }

    // 返回包含所有文件的sha_1值的一个集合

    public Set<String> Keyset(){
        Set<String> sha_1 = new HashSet<>();
        for(String key : added.keySet()){
            sha_1.add(sha1(added.get(key)));
        }
        return sha_1;
    }

    public void bloblism(File blob){
        for(String key : added.keySet()){
        }
    }
}
