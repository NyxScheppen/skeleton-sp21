package gitlet;

/** 总而言之，搞个新的分支先
 * 额额，好麻烦啊真的
 */
public class branch {

    public static String name;
    public static Commit head;

    public branch(String name,Commit head){
        this.name = name;
        this.head = head;
    }
}
