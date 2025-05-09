package gitlet;

import java.io.File;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author nyx
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */

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
                // 命令：java gitlet.Main add [file name]
                printerror(args);
                Repository.add(args[1]);
                break;
            case "commit":
                // 命令：java gitlet.Main commit [message]
                printerror(args);
                Repository.commit(args[1]);
                break;
            case "log":
                //包括提交 ID、提交时间和提交信息
                // 命令：java gitlet.Main log
                //TODO
                break;
            case "global-log":
                // 命令：java gitlet.Main global-log
                //TODO
                break;
            case "find":
                // 命令：java gitlet.Main find [commit message]
                printerror(args);
                //TODO
                break;
            case "rm":
                // 命令：java gitlet.Main rm [file name]
                printerror(args);
                // TODO
                break;
            case "rm-branch":
                // 命令：java gitlet.Main rm-branch [branch name]
                printerror(args);
                //TODO
                break;
            case "status":
                // 命令：java gitlet.Main status
                //TODO
                break;
            case "checkout":
                // 命令：java gitlet.Main checkout -- [file name]
                //      java gitlet.Main checkout [commit id] -- [file name]
                //      java gitlet.Main checkout [branch name]
                //TODO
                break;
            case "branch":
                // 命令：java gitlet.Main branch [branch name]
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
