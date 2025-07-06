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
                Repository.global_log();
                break;
            case "find":
                printerror(args);
                Repository.find(args[1]);
                break;
            case "rm":
                printerror(args);
                Repository.rm(args[1]);
                break;
            case "rm-branch":
                printerror(args);
                Repository.rm_branch(args[1]);
                break;
            case "status":
                Repository.status();
                break;
            case "checkout":
                if(args.length <= 1 || args.length > 4){
                    System.out.print("Incorrect operands.");
                } else if(args.length == 2){
                    checkout.checkout(args[1]);
                }else if(args.length == 3){
                    checkout.checkout(args[2]);
                }
                else{
                    checkout.checkout(args[1],args[3]);
                }
                break;
            case "branch":
                printerror(args);
                Repository.branch(args[1]);
                break;
            case "reset":
                // 命令：java gitlet.Main reset [commit id]
                printerror(args);
                Repository.reset(args[1]);
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
