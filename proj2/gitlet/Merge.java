package gitlet;

import java.io.File;

import static gitlet.Repository.commits;
import static gitlet.Repository.headof;
import static gitlet.Utils.join;
import static gitlet.Utils.readObject;

public class Merge {

    public Commit current;
    public Commit todo;

    public Merge(String branch){
        File f = join(commits, branch);
        if(!f.exists()){
            System.out.print("A branch with that name does not exist.");
        }
        this.todo = readObject(f, Commit.class);
        this.current = headof;
    }
    // 接下来是dfs时间喵:),找共同祖先
    // 对比文件，用哈希表存储
    //

}
