package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author nyx
 */
public class Main {


    private static void printerror(String[] args) {
        if(args.length != 2){
            System.out.print("Incorrect operands.");
        }
     }

    public static void main(String[] args) {
        if(args == null){
            System.out.print("Please enter a command.");
            return;
        }

        String firstArg = args[0];

        switch(firstArg) {
            case "init":
                Repository.init();
                break;
            case "add":
                printerror(args);
                Repository.add(args[1]);
                break;
            case "commit":
                printerror(args);
                Repository.commit(args[1]);
                break;
            case "log":
                Repository.getlog();
                break;
            case "global-log":
                // 命令：java gitlet.Main global-log
                // TODO
                // 讲道理这是要干嘛
                break;
            case "find":
                // 命令：java gitlet.Main find [commit message]
                printerror(args);
                // TODO
                break;
            case "rm":
                printerror(args);
                Repository.rm(args[1]);
                break;
            case "rm-branch":
                // 命令：java gitlet.Main rm-branch [branch name]
                printerror(args);
                //TODO
                break;
            case "status":
                // 命令：java gitlet.Main status
                // TODO
                break;
            case "checkout":
                if(args.length <= 1 || args.length > 3){
                    System.out.print("Incorrect operands.");
                } else if(args.length == 2){
                    checkout.checkout(args[1]);
                }else{
                    checkout.checkout(args[1],args[2]);
                }
                break;
            case "branch":
                printerror(args);
                Repository.branch(args[1]);
                break;
            case "reset":
                // 命令：java gitlet.Main reset [commit id]
                printerror(args);
                // TODO
                break;
            case "merge":
                // 命令：java gitlet.Main merge [branch name]
                printerror(args);
                // TODO
                break;
            default:
                System.out.print("No command with that name exists.");
        }
    }
}
