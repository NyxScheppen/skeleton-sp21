package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

public class checkout{

    public static void checkout(String filename){
        checkouthelper(filename, head);
    }

    public static void checkout(String commitid, String filename){
        File commitment = join(commits, commitid.substring(0,2));
        File commitmentt = join(commitment, commitid.substring(2));
        if(!commitmentt.exists()){
            System.out.print("No commit with that id exists");
        }
        checkouthelper(filename, commitmentt);
    }

    public static void checkouthelper(String filename,File finder) {
        Commit headm = readObject(finder, Commit.class);
        for (String sha_1 : headm.file) {
            File headcommit = join(blobsm, sha_1);
            blobs newblob = readObject(headcommit, blobs.class);
            if (filename.equals(newblob.getName())) {
                File wantted = join(CWD, filename);
                if (wantted.exists()) {
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
    }
    public static void checkout(String branch, int m){
        // 在文件夹里查找该分支
        File f = join(heads, branch);
        if(!f.exists()){
            System.out.print("No such branch exists.");
            return;
        }
        Commit n = readObject(f, Commit.class);
        if(n.getHash_code() == headof.getHash_code()){
            System.out.print("There is an untracked file in the way; delete it, or add and commit it first.");
        }
        // 将文件复制到工作区
        for(String s : n.file){
            File fs = join(blobsm, s);
            blobs ls = readObject(fs, blobs.class);
            File fn = join(CWD, ls.getName());
            writeContents(fn, ls.getContent());
        }
        // 替换head文件
        writeContents(head, Utils.serialize(n));
        stage.clear();
    }
}
