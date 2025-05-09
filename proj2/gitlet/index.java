package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static gitlet.Repository.GITLET_DIR;
import static gitlet.Utils.join;
import static gitlet.Utils.writeContents;

/** 总而言之这是缓冲区啦，以及我在认真考虑要不要把注释全都转换成英文
 *
 * 缓冲区里的东西用哈希表形式存储
 *
 * 暂时只考虑了这么多
 *
 * 其实我一直不会写java的注释...
 */

public class index implements Serializable {

    public static final File Index = join(GITLET_DIR, "Index");

    // 加入的文件
    // key = sha1, value = 文件名称

    private final Map<String, String> added;

    // key = sha1, value = 文件内容

    private final Map<String, byte[]> content;

    // 被暂时标记为删除的文件，存储的是名字

    private final Set<String> removed;

    // 在当前头指针里的文件，存储的是名字

    private final Set<String> tracked;

    public index(){
        this.added = new HashMap<>();
        this.removed = new HashSet<>();
        this.tracked = new HashSet<>();
        this.content = new HashMap<>();
    }

    // 在缓冲区里移除文件
    public void remove(String name){
        if(KeySet().contains(name)){
            added.remove(name);
            removed.add(added.get(name));
        }
        update();
    }

    // 清空缓冲区
    public void clear(){
        writeContents(Index);
        update();
    }

    // 返回缓冲区里所有文件的sha1值
    public Set KeySet(){
        return added.keySet();
    }

    // 添加文件
    public void add(String key, String value, byte[] content){
        added.put(key,value);
        this.content.put(key,content);
        update();
    }

    // 覆盖缓冲区
    public void update(){
        writeContents(Index,this);
    }


    // 文件被移除
    public boolean removed(String name) {
        if (removed.contains(name)) {
            return true;
        }
        return false;
    }

    public void anti_removed(String name){
        removed.remove(name);
    }

    // 文件被追踪
    public boolean tracked(String name){
        if(tracked.contains(name)){
            return true;
        }
        return false;
    }

    // 缓冲区里包括该文件
    public String containsvalue(String name){
        for(String key: added.keySet()){
            if(name.equals(get_value(key))){
                return key;
            }
        }
        return null;
    }

    // 取得key对应的值
    public String get_value(String key){
        if(!added.containsKey(key)){
            return null;
        }
        return added.get(key);
    }
}
