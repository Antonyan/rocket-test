package core.modules.library.models;

import com.jcraft.jsch.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.prefs.Preferences;

public class SshClient {
    private String host;
    private String user;
    private String password;
    private String absoluteFilePathPrivatekey;
    private Session session;

    public SshClient(Preferences config) {
        this(
                config.get("host", ""),
                config.get("user", ""),
                config.get("password", ""),
                config.get("absoluteFilePathPrivatekey", "")
        );
    }

    public SshClient(String host, String user, String password, String absoluteFilePathPrivatekey) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.absoluteFilePathPrivatekey = absoluteFilePathPrivatekey;
    }

    public void transferFile(String sourceFile, String destinationFile) {
        try {
            Channel channel = getSession().openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;

            sftpChannel.put(sourceFile, destinationFile);
            sftpChannel.exit();

        } catch (JSchException exception) {
            System.out.println("Can't connect to the channel");
        } catch (SftpException exception) {
            System.out.println(exception.getMessage());
        } finally {
            closeSession();
        }
        closeSession();
    }

    public void execRemoteCommand(String command) {

        try {
            ChannelExec channelExec = (ChannelExec) getSession().openChannel("exec");
            BufferedReader in = new BufferedReader(new InputStreamReader(channelExec.getInputStream()));
            channelExec.setCommand(command);
            channelExec.connect();
            String msg = null;
            while ((msg = in.readLine()) != null) {
                System.out.println(msg);
            }

            channelExec.disconnect();
        } catch (JSchException exception) {
            System.out.println("Can't connect to the channel" + exception.getMessage());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        } finally {
            closeSession();
        }
        closeSession();
    }

    private Session getSession() {

        if (session != null) {
            return session;
        }

        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");

            JSch jsch = new JSch();
            jsch.addIdentity(absoluteFilePathPrivatekey, password);
            session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();

        } catch (JSchException exception) {
            System.out.println("Can't create connection");
        }

        return session;
    }

    private void closeSession() {

        if (session == null) {
            return;
        }

        session.disconnect();
        session = null;
    }
}
