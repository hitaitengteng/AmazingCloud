package com.whut.analyst.domain;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.DataSetPreProcessor;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author 杨赟
 * @describe
 * @since 18-10-16 下午8:34
 */
public class LineDataIterator implements DataSetIterator {


    //每批次的训练数据组数
    private static final int batch = 10;
    //每组训练数据长度
    private static final int length = 1000;

    //每组训练数据长度
    private static final int label_length = 1;
    //每组训练数据长度
    private static final int feature_length = 14;

    //数据集
    private List<LineData> dataList;
    //存放剩余数据组的index信息
    private List<Integer> indexRecord;

    /**
     * 构造方法
     * */
    public LineDataIterator(){
        indexRecord = new ArrayList<>();
    }

    /**
     * 加载数据并初始化
     * */
    public boolean loadFile(String fileName){
        //加载文件中的股票数据
        try {
            readDataFromFile(fileName);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        //重置训练批次列表
        resetIndexRecord();
        return true;
    }


    /**
     * 重置训练批次列表
     * */
    private void resetIndexRecord(){
        indexRecord.clear();
        int total = dataList.size()/length+1;
        for( int i=0; i<total; i++ ){
            indexRecord.add(i * length);
        }
    }

    /**
     * 从文件中读取温度数据
     * */
    private void readDataFromFile(String fileName) throws IOException {
        dataList = new ArrayList<>();
        FileInputStream fis = new FileInputStream(fileName);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
        System.out.println("start load data...");
        String line = in.readLine();
        while(line!=null) {
            dataList.add(new LineData(line));
            line = in.readLine();
        }
        in.close();
        fis.close();
    }


    public void reset(){
        resetIndexRecord();
    }

    public boolean hasNext(){
        return indexRecord.size() >= batch();
    }

    public DataSet next(){
        return next(batch());
    }

    /**
     * 获得接下来一次的训练数据集
     * */
    public DataSet next(int num){
        if(indexRecord.size() <= 0) {
            throw new NoSuchElementException();
        }
        int actualBatchSize = Math.min(num, indexRecord.size());
        int actualLength = Math.min(length,dataList.size()-indexRecord.get(0)-1);
        INDArray input = Nd4j.create(new int[]{actualBatchSize,feature_length,actualLength}, 'f');
        INDArray label = Nd4j.create(new int[]{actualBatchSize,label_length,actualLength}, 'f');
        LineData nextData = null,curData = null;
        //获取每批次的训练数据和标签数据
        for(int i=0;i<actualBatchSize;i++){
            int index = indexRecord.remove(0);
            int endIndex = Math.min(index+length,dataList.size()-1);
            curData = dataList.get(index);
            for(int j=index;j<endIndex;j++){
                //获取数据信息
                nextData = dataList.get(j+1);
                int c = j - index;
                int length = curData.getFeatures().length;
                for (int k = 0; k!= length; ++k){
                    //构造训练向量
                    input.putScalar(new int[]{i, k, c}, curData.getFeatures()[k]);
                }
                //构造label向量
                label.putScalar(new int[]{i, 0, c}, nextData.getLabel());
                //后移一位
                curData = nextData;
            }
            if(indexRecord.size()<=0) {
                break;
            }
        }
        return new DataSet(input, label);
    }

    public int batch() {
        return batch;
    }

    public int cursor() {
        return totalExamples() - indexRecord.size();
    }

    public int numExamples() {
        return totalExamples();
    }

    public void setPreProcessor(DataSetPreProcessor preProcessor) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public DataSetPreProcessor getPreProcessor() {
        return null;
    }

    public int totalExamples() {
        return (dataList.size()) / length;
    }

    public int inputColumns() {
        return dataList.size();
    }

    public int totalOutcomes() {
        return 1;
    }

    @Override
    public boolean resetSupported() {
        return false;
    }

    @Override
    public boolean asyncSupported() {
        return false;
    }

    @Override
    public List<String> getLabels() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
