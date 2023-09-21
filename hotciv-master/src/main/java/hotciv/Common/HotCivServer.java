package hotciv.Common;

public class HotCivServer {

    public static void main(String[] args) throws Exception {
        new ServerMainSocket(args[0]); // No error handling!
    }

}
