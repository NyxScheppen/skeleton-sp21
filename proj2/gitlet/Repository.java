package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static gitlet.Utils.*;


/** Represents a gitlet repository.
 *  创建各个文件夹，其实我也不知道哪些文件夹是必须的，抄的git，干
 *  现在包含的文件：
 *  refs，objects,index,head
 *  @author nyx
 */
public class Repository {

    /** The current working directory. */

    public static final File CWD = new File(System.getProperty("user.dir"));

    /** The .gitlet directory. */

    public static final File GITLET_DIR = join(CWD, ".gitlet");

    // head

    public static final File head = join(GITLET_DIR, "head");

    // objects:以哈希表形式存储的提交文件夹（但是我还没想好怎么搞总之先建文件夹再说）

    public static final File Objects = join(GITLET_DIR, "Objects");

    // index：缓冲区文件（嗯内部以二叉树保存至于怎么能把二叉树写到文件里再读出来，我吃个饭再想想）

    public static final File Index = join(GITLET_DIR, "Index");

    // refs

    public static final File refs = join(GITLET_DIR, "refs");

    // commits

    public static File commits = join(refs, "commits");

    // heads

    public static File heads = join(refs, "heads");

    // index stage

    static index stage = readObject(Index, index.class);

    // head

    static Commit headof = readObject(head, Commit.class);

    public static void init(){
        // 命令：java gitlet.Main init

        Commit initial = new Commit("initial commit", null, "00:00:00 UTC, Thursday, 1 January 1970",null);

        //git_dir

        if(GITLET_DIR.exists()){
           System.out.print("A Gitlet version-control system already exists in the current directory.");
           return;
        }
        GITLET_DIR.mkdir();

        // index

        try {
            Index.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // objects

        Objects.mkdir();

        // initialize a master branch and have it point to initial

        refs.mkdir();

        branch("master",initial);

        // head

        try {
            head.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        writeContents(head, initial.getHash_code());

        // heads

        heads.mkdir();

        //commits

        commits.mkdir();

        File initialcommit = join(commits, sha1(initial));

        try {
            initialcommit.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        writeContents(initialcommit, initial);

        // UID?
    }

    public static void branch(String name){
        branch(name, headof);
    }

    public static File branch(String name,Commit head){
        File newbranch = join(heads,"name");
        try {
            newbranch.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeContents(newbranch, head.getHash_code());
        return newbranch;
    }


    public static void add(String filename){
        File input = join(CWD,filename);
        String inputsha1 = sha1(input);

        // 文件不存在

        if(!input.exists()){
            System.out.print("File does not exist");
            return;
        }

        // 检查提交的文件是否与头文件相同

        for(int i = 0;i < headof.files.size(); i++){
            if(headof.files.contains(inputsha1)){
                stage.remove(inputsha1);
                return;
            }
        }

        // 如果文件处于被删除状态

        if(stage.removed(inputsha1)){
            stage.anti_removed(filename);
        }

        // 如果文件已经被传过一次

        String n = stage.containsvalue(filename);

        if(n != null){
            stage.remove(n);
        }

        // 如果不符合以上情况

        stage.add(sha1(input),filename, serialize(input));
    }


    /**
     * 描述：保存当前提交和暂存区中被跟踪文件的快照，以便以后恢复，创建一个新的提交。
     * 该提交被称为跟踪已保存的文件。默认情况下，每个提交的文件快照将与其父提交的文件快照完全相同；
     * 它会保持文件版本不变，不进行更新。提交只会更新那些在提交时已暂存以供添加的被跟踪文件的内容，
     * 在这种情况下，提交将包含暂存的文件版本，而不是从其父提交继承的版本。
     * 提交还会保存并开始跟踪任何已暂存以供添加但其父提交未跟踪的文件。
     * 最后，当前提交中被跟踪的文件可能会因为被 rm 命令（见下文）暂存以供移除而在新提交中不再被跟踪。
     */
    public static void commit(String message){
        // 搞到head里的提交
        String currenthead = readObject(head, String.class);
        Commit currentcommit = readObject(join(commits, currenthead), Commit.class);
        // 搞到缓冲区里的文件
        Commit newcommit = new Commit(message,currentcommit.getHash_code(),null, stage.KeySet());
        // 对比
        // 写入
        // 覆写head
        stage.clear();
    }
}
