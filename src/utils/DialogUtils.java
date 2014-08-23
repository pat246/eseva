package utils;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DialogUtils {
    public static JDialog WAIT_DIALOG;
    public static String  LPRD_MSG    = "Checking last password reset time, please wait.";
    public static String  RESETPW_MSG = "Trying to reset password, please wait.";
    public static String  MSG         = "please wait";
    public static JLabel  waitLabel;

    public DialogUtils() {
    }

    public static void init() {
        createWaitDialog();
    }

    private static void createWaitDialog() {
        String path = Constants.CONTEXT_ROOT_PATH + "/images/loadingAnimation.gif";
        System.out.print(path);
        javax.swing.ImageIcon waitImg = new javax.swing.ImageIcon(path);
        JLabel label = new JLabel(waitImg);
        waitLabel = new JLabel(MSG);

        JPanel panel = new JPanel();
        panel.add(waitLabel);
        panel.add(label);

        WAIT_DIALOG = new JDialog();
        WAIT_DIALOG.setTitle("Please wait..");
        WAIT_DIALOG.setSize(400, 80);
        WAIT_DIALOG.add(panel);
    }
}
