package com.whut.collector.config;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Field;
import java.util.logging.Logger;


public class CollectorConfig {

    private static final Logger logger = Logger.getLogger(CollectorConfig.class.getName());

    private static final boolean TEST_MODE = true;

    public static String pSpace_host;
    public static int pSpace_port;
    public static String pSpace_username;
    public static String pSpace_password;
    private static boolean isStartAtLogon;
    private static String JRE_PATH ;

    public static String SERVERS;
    public static String TOPIC;


    public static boolean loadConfigFile(String name){

        if (TEST_MODE)
        {
            SERVERS = "59.69.101.206:49092,59.69.101.206:49093,59.69.101.206:49094";
            TOPIC = "DCS-TOPIC";
            return false;
        }
        try {
            File file = new File(name);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList names = doc.getElementsByTagName("name");
            NodeList values = doc.getElementsByTagName("value");
            if(names.getLength() != values.getLength())
            {
                return true;
            }
            for (int i = 0; i < names.getLength(); i++) {
                Node node = names.item(i);
                if(node.getTextContent().equals("pSpace.address"))
                {
                    pSpace_host = values.item(i).getTextContent();
                }
                if(node.getTextContent().equals("pSpace.port"))
                {
                    pSpace_port = Integer.parseInt(values.item(i).getTextContent());
                }
                if(node.getTextContent().equals("pSpace.username"))
                {
                    pSpace_username = values.item(i).getTextContent();
                }
                if(node.getTextContent().equals("pSpace.password"))
                {
                    pSpace_password = values.item(i).getTextContent();
                }
                if(node.getTextContent().equals("self.start"))
                {
                    isStartAtLogon = Boolean.parseBoolean(values.item(i).getTextContent());
                    String appName = "collector";
                    File directory = new File("");
                    String courseFile = directory.getCanonicalPath() ;
                    //开机启动程序本地目录
                    String exePath = String.format("%s\\collector.exe", courseFile);
                    String add = "reg add " +
                            "\"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Run\" " +
                            "/v %s " +
                            "/t reg_sz " +
                            "/d \"%s\" " +
                            "/f";
                    String delete = "reg delete " +
                            "\"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Run\" " +
                            "/v %s " +
                            "/f";
                    String command = isStartAtLogon?
                            String.format(add, appName,exePath):
                            String.format(delete, appName,exePath);
                    Runtime.getRuntime().exec(command);
                }
                if(node.getTextContent().equals("java.library.path"))
                {
                    JRE_PATH = values.item(i).getTextContent();
                }
                if(node.getTextContent().equals("bootstrap.servers"))
                {
                    SERVERS = values.item(i).getTextContent();
                }
                if(node.getTextContent().equals("kafka.topic"))
                {
                    TOPIC = values.item(i).getTextContent();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            addLibraryDir(CollectorConfig.JRE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void showConfig(){
        logger.info("showConfig:");
        if (TEST_MODE)
        {
            logger.info("This is in the test mode, not connect to pspace!");
            logger.info(String.format("1.kafka地址:%s", CollectorConfig.SERVERS));
            logger.info(String.format("2.kafka主题:%s", CollectorConfig.TOPIC));
            return;
        }
        logger.info(String.format("1.开机自启动:%b", CollectorConfig.isStartAtLogon));
        logger.info(String.format("2.实时库地址:%s", CollectorConfig.pSpace_host));
        logger.info(String.format("3.实时库端口:%d", CollectorConfig.pSpace_port));
        logger.info(String.format("4.实时库用户名:%s", CollectorConfig.pSpace_username));
        logger.info(String.format("5.实时库密码:%s", CollectorConfig.pSpace_password));
        logger.info(String.format("6.JRE路径:%s", CollectorConfig.JRE_PATH));
        logger.info(String.format("7.kafka地址:%s", CollectorConfig.SERVERS));
        logger.info(String.format("8.kafka主题:%s", CollectorConfig.TOPIC));
    }

    private static void addLibraryDir(String libraryPath) throws Exception {
        Field userPathsField = ClassLoader.class.getDeclaredField("usr_paths");
        userPathsField.setAccessible(true);
        String[] paths = (String[]) userPathsField.get(null);
        StringBuilder sb = new StringBuilder();
        sb.append(libraryPath).append(';');
        for (String path : paths) {
            if (libraryPath.equals(path)) {
                continue;
            }
            sb.append(path).append(';');
        }
        System.setProperty("java.library.path", sb.toString());
        final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
        sysPathsField.setAccessible(true);
        sysPathsField.set(null, null);
    }
}
