package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

public class checkout{

    public static void checkout(String filename){
        checkouthelper(filename, head);
    }

    public static void checkout(String commitid, String filename){
        File commitment = join(commits, commitid.substring(0,2));
        File commitmentt = join(commitment, commitid.substring(commitid.length() - 2));
        if(!commitmentt.exists()){
            System.out.print("No commit with that id exists");
        }
        checkouthelper(filename, commitmentt);
    }

    // need to fix
    // TODO
    public static void checkouthelper(String filename,File finder){
        Commit headm = readObject(finder, Commit.class);
        // 传过来的是文件名字的情况
        for(String sha_1 : headm.files){
            File headcommit = join(blobsm, sha_1);
            blobs newblob = readObject(headcommit, blobs.class);
            if(filename.equals(newblob.getName())){
                File wantted = join(CWD, filename);
                if(wantted.exists()){
                    writeContents(wantted, newblob.getContent());
                    return;
                }
                try {
                    wantted.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                writeContents(wantted, newblob.getContent());
                return;
            }
        }
        // 传过来的是整个分支的情况
        // TODO
        /*
        File branchhead = join(heads, filename);
        Commit currcomit = readObject(branchhead, Commit.class);
        Commit headcommit = readObject(head, Commit.class);
        if(!branchhead.exists()){
            System.out.print("No such branch exists.");
            return;
        }
        if(currcomit.equals(headcommit)){
            System.out.print("No need to checkout the current branch.");
            return;
        }
        for(File m : CWD.listFiles()) {
            if (stage.contains(m.getName())) {
                System.out.print("There is an untracked file in the way; delete it, or add and commit it first.");
            }
        }
        writeContents(head, branchhead);
        */
    }
}
