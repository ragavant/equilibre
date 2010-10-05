

import com.cloudbees.management.PaaSFrontEnd.HAProxy.HAProxyConfiguration;
import com.cloudbees.management.PaaSFrontEnd.PaaSApplication;
import com.cloudbees.management.PaaSFrontEnd.PaaSNode;
import com.cloudbees.management.SSH.SSHConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Properties;


public class HAProxyConfigurator {
    private Properties properties;
    private SSHConnection connection;
    private HAProxyConfiguration lbc;

    public HAProxyConfigurator(String parameterFile) throws Exception {
        properties = new Properties();
        FileInputStream in = new FileInputStream(System.getProperty("user.dir") + "/" + parameterFile);
        properties.load(in);
        in.close();

        String HAProxyDefaultsFile = properties.getProperty("HAProxy.defaultsFile");
        lbc = new HAProxyConfiguration(HAProxyDefaultsFile);
    }

    public void addApplication(PaaSApplication app) throws MalformedURLException {
        lbc.addApplication(app);
    }

    private SSHConnection getConnection() throws IOException {
        if (connection == null)
        {
            /* Create a connection instance */
            String keyFileName = properties.getProperty("SSH.keyfile");
            String keyFilePath = properties.getProperty("SSH.keyfile.path", System.getProperty("user.dir") + "/../../.ec2/");
            File keyfile = new File(keyFilePath + keyFileName);

            String hostName = properties.getProperty("SSH.hostname");
            String userName = properties.getProperty("SSH.username");
            connection = new SSHConnection(hostName, userName, keyfile);
        }
        return connection;
    }

    private boolean execCommand(String command, String expectedResult)
    {
        List<String> output = null;
        try {
            output = getConnection().execCommand(command);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (expectedResult == null)
            return true;
        
        for (String result : output)
        {
            String r = result;
            if (result.indexOf(expectedResult) > -1)
                return true;
        }
        return false;
    }

    public void pushConfiguration() throws IOException {
        // Check HAProxy status
        boolean ok = execCommand(properties.getProperty("HAProxy.cmd.status"), properties.getProperty("HAProxy.cmd.status.result"));
        if (!ok) {
            System.err.println("haproxy not running, restarting");
            ok = execCommand(properties.getProperty("HAProxy.cmd.start"), properties.getProperty("HAProxy.cmd.start.result"));
            if (!ok) {
                System.err.println("Cannot start HAProxy");
                return;
            }
        }

        if (lbc != null)
        {
            String confFile = properties.getProperty("HAProxy.conf.file");
            String confDir = properties.getProperty("HAProxy.conf.dir");
            // cp current config to old
            execCommand("cp " + confDir + confFile + " " + confDir + confFile + ".old", null);

            System.out.println("Upload new configuration to " + confDir + confFile + ".new");
            byte[] buf = lbc.toString().getBytes();
            getConnection().upload(buf, confFile + ".new", confDir);

            // cp new config to current
            execCommand("cp " + confDir + confFile + ".new " + confDir + confFile, null);

            long start = System.currentTimeMillis();
            boolean failed = execCommand(properties.getProperty("HAProxy.cmd.reload"), properties.getProperty("HAProxy.cmd.reload.result"));
            long end = System.currentTimeMillis();
            System.out.println("HAProxy reload time:" + (end-start) + " ms");
            if (failed) {
                System.err.println("Cannot reload new haproxy configuration");
                System.err.println("Reloading old haproxy configuration");

                // revert old config
                execCommand("cp " + confDir + confFile + ".old " + confDir + confFile, null);
                execCommand(properties.getProperty("HAProxy.cmd.reload"), properties.getProperty("HAProxy.cmd.reload.result"));
            }

        } else {
            System.err.println("Configuration not generated");
        }

    }

    public void close()
    {
        if (connection != null)
        {
            connection.close();
            connection = null;
        }
    }

    public void displayConfiguration()
    {
        System.out.println(lbc);
    }

    public static void main(String[] args)
	{
		try
		{
            HAProxyConfigurator lbc = new HAProxyConfigurator(args[0]);

            for (int i=1; i<3; i++) {
                PaaSApplication app1 = new PaaSApplication("company"+i+".highstor.com/company"+i+"/", PaaSApplication.COOKIE | PaaSApplication.SOURCE_IP, PaaSApplication.HTTP | PaaSApplication.HTTPS);
                app1.addErrorRedirect("503", "http://www.google.com/503");
                app1.addErrorRedirect("504", "http://www.google.com/504");
                app1.addOption("reqadd\tFRONT_END_COMPANY:\\ company"+ i);
                PaaSNode node1 = new PaaSNode("node1", "10.205.11.191:8080", null);
                node1.setHealthCheck(true, true, null);
                app1.addNode(node1);
                PaaSNode node2 = new PaaSNode("node2", "10.205.11.191:9090", null);
                node2.setHealthCheck(true, true, null);
                app1.addNode(node2);
                lbc.addApplication(app1);
            }

            lbc.displayConfiguration();
            
            lbc.pushConfiguration();
            lbc.close();
		}
		catch (Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(2);
        }
    }
}
