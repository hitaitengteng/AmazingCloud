package com.whut.analyst.service;

import com.whut.analyst.domain.LineData;
import com.whut.analyst.domain.LineDataIterator;
import com.whut.analyst.util.ModelSaveUtil;
import org.apache.log4j.Logger;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.GravesLSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LSTM {

    private static final Logger logger = Logger.getLogger(LSTM.class);

    private static final int IN_NUM = 14;   //输入层
    private static final int OUT_NUM = 1;  //输出层
    private static final int Epochs = 100;   //迭代训练
    private static final int lstmLayer1Size = 512;  //隐藏层1
    private static final int lstmLayer2Size = 512;  //隐藏层2

    public static MultiLayerNetwork getNetModel(int nIn, int nOut) {

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .learningRate(0.01).iterations(1).regularization(true)
            .seed(12345)  
            .l2(0.001)
            .updater(Updater.RMSPROP)
            .list()  
            .layer(0, new GravesLSTM.Builder().nIn(nIn).nOut(lstmLayer1Size)
                .activation(Activation.SOFTSIGN).build())
            .layer(1, new GravesLSTM.Builder().nIn(lstmLayer1Size).nOut(lstmLayer2Size)  
                .activation(Activation.SOFTSIGN).build())
            .layer(2, new RnnOutputLayer.Builder(LossFunctions.LossFunction.MSE).activation(Activation.IDENTITY)
                .nIn(lstmLayer2Size).nOut(nOut).build())
            .pretrain(false).backprop(true)
            .build();
        MultiLayerNetwork net = new MultiLayerNetwork(conf);  
        net.init();  
        net.setListeners(new ScoreIterationListener(1));
        return net;  
    }  

    public static MultiLayerNetwork train (MultiLayerNetwork net, LineDataIterator iterator){
        //迭代训练  
        for(int i=0;i<Epochs;i++) {
            DataSet dataSet;
            while (iterator.hasNext()) {
                dataSet = iterator.next();  
                net.fit(dataSet);
            }
            iterator.reset();
            ModelSaveUtil.writeObjectToFile(net,"/home/ubuntu/model/diagnosis.model."+i);
            logger.info("--------------完成第"+(i+1)+"次完整训练----------------");
        }
        return net;
    }

    public static void predict(MultiLayerNetwork net,String testFile){
        try {
            List<INDArray> list = getINDArrayList(testFile);
            for (INDArray input : list){
                INDArray output = net.rnnTimeStep(input);

                System.out.println("输入:"+input + "输出:"+ output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        net.rnnClearPreviousState();
    }

    private static List<INDArray> getINDArrayList(String testFile) throws IOException {

        FileInputStream fis = new FileInputStream(testFile);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
        String line = in.readLine();
        List<INDArray> list = new ArrayList<>();
        while (line!=null){
            INDArray indArray = Nd4j.zeros(1,IN_NUM,1);
            LineData data = new LineData(line);
            for (int i = 0;i!=data.getFeatures().length;++i){
                indArray.putScalar(new int[]{0,i,0},data.getFeatures()[i]);
            }
            list.add(indArray);
            line = in.readLine();
        }
        return list;
    }

    public void run() {
        String trainFile = "service-analyst/src/main/resources/data/train.txt";
        String testFile= "service-analyst/src/main/resources/data/train.txt";
        //初始化深度神经网络
        LineDataIterator iterator = new LineDataIterator();
        iterator.loadFile(trainFile);
        MultiLayerNetwork net = getNetModel(IN_NUM,OUT_NUM);
        net = train(net, iterator);
//        net = (MultiLayerNetwork) ModelSaveUtil.readObjectFromFile("diagnosis.model");
        predict(net,testFile);
    }

    public static void main(String[] args) {
        String testFile= "service-analyst/src/main/resources/data/train.txt";
        MultiLayerNetwork net = (MultiLayerNetwork) ModelSaveUtil.readObjectFromFile("diagnosis.model.5");
        predict(net,testFile);
    }
}