

/*
 * Copyright 2010, CloudBees Inc.
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

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


/**
 * This is partly a demo class - and also contains the SSH code to push the configuration to a front end server. 
 */
public class HAProxyConfigurator {
    private Properties properties;
    private SSHConnection connection;
    private HAProxyConfiguration lbc;


    /** Path to the properties file of where to find the config */
    public HAProxyConfigurator(String parameterFile) throws Exception {
        properties = new Properties();
        FileInputStream in = new FileInputStream(parameterFile);
        properties.load(in);
        in.close();

        init();
    }

    private void init() throws IOException {
        String HAProxyDefaultsFile = properties.getProperty("HAProxy.defaultsFile");
        if (HAProxyDefaultsFile != null) {
            lbc = new HAProxyConfiguration(new FileInputStream(HAProxyDefaultsFile));
        } else {
            lbc = new HAProxyConfiguration(this.getClass().getResourceAsStream("/HAProxyDefaults.conf"));
        }

    }

    public HAProxyConfigurator(Properties props) throws Exception {
        properties = props;
        init();
    }

    public void addApplication(PaaSApplication app) throws MalformedURLException {
        lbc.addApplication(app);
    }

    private SSHConnection getConnection() throws IOException {
        if (connection == null)
        {
            /* Create a connection instance */
            String keyFileName = properties.getProperty("SSH.keyfile");
            String keyFilePath = properties.getProperty("SSH.keyfile.path", "");
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

    public boolean pushConfiguration() throws IOException {
        // Check HAProxy status
        boolean ok = execCommand(properties.getProperty("HAProxy.cmd.status"), properties.getProperty("HAProxy.cmd.status.result"));
        if (!ok) {
            System.err.println("haproxy not running, restarting");
            ok = execCommand(properties.getProperty("HAProxy.cmd.start"), properties.getProperty("HAProxy.cmd.start.result"));
            if (!ok) {
                System.err.println("Cannot start HAProxy");
                return false;
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
                return false;
            }
            return true;

        } else {
            System.err.println("Configuration not generated");
            return false;
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
