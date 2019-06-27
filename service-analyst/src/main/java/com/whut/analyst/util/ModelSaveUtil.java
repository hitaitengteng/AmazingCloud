package com.whut.analyst.util;

import com.whut.analyst.config.HDFSConfig;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.deeplearning4j.nn.api.Model;
import org.deeplearning4j.util.ModelSerializer;

import java.io.*;

/**
 * @author yy
 * @describe 保存和读取模型
 * @time 18-10-13 上午8:34
 */
public class ModelSaveUtil {

    public static void writeObjectToFile(Object obj,String filename)
    {
        File file =new File(filename);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut=new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            objOut.close();
            System.out.println("write object success!");
        } catch (IOException e) {
            System.out.println("write object failed");
            e.printStackTrace();
        }
    }

    public static Object readObjectFromFile(String filename)
    {
        Object temp=null;
        File file =new File(filename);
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn=new ObjectInputStream(in);
            temp=objIn.readObject();
            objIn.close();
            System.out.println("read object success!");
        } catch (IOException e) {
            System.out.println("read object failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static void writeModelToHDFS(Model model, String path){

        FileSystem fileSystem = HDFSConfig.getFileSystem();
        Path modelPath = new Path(path);
        try {
            OutputStream os = fileSystem.create(modelPath);
            BufferedOutputStream stream = new BufferedOutputStream(os);
            ModelSerializer.writeModel(model, stream, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Model readModelFromHDFS(String path){
        FileSystem fileSystem = HDFSConfig.getFileSystem();
        Path modelPath = new Path(path);
        Model model= null;
        try {
            InputStream is = fileSystem.open(modelPath);
            model = ModelSerializer.restoreMultiLayerNetwork(is,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }
}
