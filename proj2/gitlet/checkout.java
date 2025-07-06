package gitlet;

import java.io.File;
import java.io.IOException;

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
}
