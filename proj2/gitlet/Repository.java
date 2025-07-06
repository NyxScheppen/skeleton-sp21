package gitlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static gitlet.Utils.*;

public class Repository {

    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File head = join(GITLET_DIR, "head");
    public static final File Objects = join(GITLET_DIR, "Objects");
    public static final File Index = join(GITLET_DIR, "Index");
    public static final File refs = join(GITLET_DIR, "refs");
    public static File commits = join(refs, "commits");
    public static File heads = join(refs, "heads");
    private static index stage = readforstage();
    public static File blobsm = join(GITLET_DIR, "blobs");
    static Commit headof = readforheadof();


    private static index readforstage(){
        index newindex;
        if(Index.exists()){
            newindex = readObject(Index, index.class);
        }else{
            newindex = null;
        }
        return newindex;
    }

    private static Commit readforheadof(){
        Commit newindex;
        if(head.exists()){
            newindex = readObject(head, Commit.class);
        }else{
            newindex = null;
        }
        return newindex;
    }

    public static boolean initist(){
        if(!GITLET_DIR.exists()){
            return false;
        }
        return true;
    }

    public static void init(){
        Commit initial = new Commit("initial commit");
        if(initist()){
           System.out.print("A Gitlet version-control system already exists in the current directory.");
           return;
        }
        GITLET_DIR.mkdir();
        Objects.mkdir();
        refs.mkdir();
        heads.mkdir();
        commits.mkdir();
        blobsm.mkdir();

        try {
            head.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeContents(head, Utils.serialize(initial));
        headof = initial;
        branch("master");

        index dummy = new index();

        try {
            Index.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        writeContents(Index, Utils.serialize(dummy));

        writeContents(head, Utils.serialize(initial));
        String dir = initial.getHash_code().substring(0,2);
        File newdir = join(commits,dir);
        if(!newdir.exists()){
            newdir.mkdir();
        }
        File newcmt = join(newdir, initial.getHash_code().substring(2));
        try {
            newcmt.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeContents(newcmt, Utils.serialize(initial));
        blobs newblob = new blobs("initial", null);
        File newfile = join(blobsm, newblob.getSha_1());
        try {
            newfile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeContents(newfile, Utils.serialize(newblob));
    }

    public static void add(String filename){
        if(!initist()){
            System.out.print("Not in an initialized Gitlet directory.");
            return;
        }
        File input = join(CWD, filename);
        if(!input.exists()){
            System.out.print("File does not exist");
            return;
        }
        stage.add(filename, readContents(input));
    }

    public static void rm(String name){
        if(!initist()){
            return;
        }
        stage.remove(name);
    }

    public static void commit(String message){
        if(!initist()){
            System.out.print("Not in an initialized Gitlet directory.");
            return;
        }
        // keyset call bloblism
        Commit newcommit = new Commit(message, headof.getHash_code(), null, stage.Keyset());
        writeContents(head, Utils.serialize(newcommit));
        // 新的commits文件夹（若必要）
        String dir = newcommit.getHash_code().substring(0,2);
        File newdir = join(commits,dir);
        if(!newdir.exists()){
            newdir.mkdir();
        }
        // 新的commit
        File newcmt = join(newdir, newcommit.getHash_code().substring(2));
        try {
            newcmt.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeContents(newcmt, Utils.serialize(newcommit));
        stage.clear();
    }

    public static File branch(String name){
        if(!initist()){
            System.out.print("Not in an initialized Gitlet directory.");
            return null;
        }
        File newbranch = join(heads,name);
        try {
            newbranch.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeContents(newbranch, Utils.serialize(headof));
        return newbranch;
    }

    public static void rm_branch(String name){
        if(!initist()){
            System.out.print("Not in an initialized Gitlet directory.");
            return;
        }
        File rmbranch =join(heads, name);
        if(!rmbranch.exists()){
            System.out.print("A branch with that name does not exist.");
        }else if(name == "master"){
            System.out.print("Cannot remove the current branch.");
        }
    }

    public static void status(){
        if(!initist()){
            System.out.print("Not in an initialized Gitlet directory.");
            return;
        }
        System.out.print("=== Branches ===" + "\n");
        File[] collect = heads.listFiles();
        for(int i = 0; i < collect.length; i++){
            String name = collect[i].getName();
            if(name == "master"){
                System.out.print("*");
            }
            System.out.print(name);
        }
        System.out.print("=== Staged Files ===" + "\n");
        Set<String> m = stage.stagefile();
        for(String s : m){
            System.out.print(s + " ");
        }
        System.out.print("=== Removed Files ===" + "\n");
        Set<String> n = stage.removedfile();
        for(String s : n){
            System.out.print(s + " ");
        }
        System.out.print("=== Modifications Not Staged For Commit ===" + "\n");
        System.out.print("=== Untracked Files ===" + "\n");
    }

    public static void find(String message){
        List<String> name = fndcmt(message);
        if(name.isEmpty()){
            System.out.print("Found no commit with that message.");
        }else{
            for(String s : name){
                System.out.print(s);
            }
        }
    }

    public static void reset(String message){
        List<String> m = fndcmt(message);
        String s = m.get(0);
        File rsgt = join(commits,s);
        Commit cmt = readObject(rsgt, Commit.class);
        for(String ss : cmt.file){
           File f = join(blobsm, ss);
           blobs b = readObject(f, blobs.class);
           checkout.checkout(cmt.getHash_code(), b.getName());
           add(b.getName());
        }
        writeContents(head, Utils.serialize(cmt));
        stage.clear();
    }

    private static List<String> fndcmt(String message){
        List<String> ls = new ArrayList<>();
        File[] name = commits.listFiles();
        for(File f : name){
            File[] commit = commits.listFiles();
            for(File com : commit){
                Commit fd = Utils.readObject(com, Commit.class);
                if(fd.getMessage() == message){
                    ls.add(com.getName());
                }
            }
        }
        return ls;
    }


    public static void getlog(){
        if(!initist()){
            System.out.print("Not in an initialized Gitlet directory.");
            return;
        }
        prtlog(headof);
    }

    public static void global_log(){
        List<String> name = plainFilenamesIn(heads);
        for(String s : name){
            File f = join(heads, s);
            prtlog(readObject(f, Commit.class));
        }
    }

    private static void prtlog(Commit name){
        Commit nowcommit = name;
        while(nowcommit != null){
            System.out.println("===");
            System.out.println("commit " + nowcommit.getHash_code());
            SimpleDateFormat d = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z", Locale.ENGLISH);
            System.out.println("Date: " + d.format(nowcommit.getTimestamp()));
            System.out.println(nowcommit.getMessage() + "\n");
            if(nowcommit.getParent() != null){
                File newcommit = join(commits, nowcommit.getParent().substring(0,2));
                File newcommitagain = join(newcommit, nowcommit.getParent().substring(2));
                nowcommit = readObject(newcommitagain, Commit.class);
            }
            else{
                break;
            }
        }
    }
}
