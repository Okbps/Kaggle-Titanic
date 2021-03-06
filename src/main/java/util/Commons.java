package util;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.*;

import static util.Globals.RESOURCES;

public class Commons {
    public static void classifyToCsv(Classifier classifier, Instances testData) throws Exception {
        StringBuffer text = new StringBuffer("PassengerId,Survived\n");

        int count = 892;
        for (Instance instance : testData) {
            double d = classifier.classifyInstance(instance);

            text
//                    .append(String.format("%d", (long)instance.value(0)))
                    .append(count++)
                    .append(",")
                    .append(String.format("%d", (long)d))
                    .append("\n");
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(RESOURCES + "csv/result.csv"));
        writer.write(text.toString());
        writer.flush();
        writer.close();
    }

    public static BufferedReader readDataFile(String filename){
        BufferedReader inputReader = null;
        try{
            inputReader = new BufferedReader(new FileReader(filename));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return inputReader;
    }

    public static  void csvToArff(String fileName) throws Exception {
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(RESOURCES + "csv/" + fileName + ".csv"));
        Instances data = loader.getDataSet();

        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(new File(RESOURCES + "arff/" + fileName + ".arff"));
        saver.writeBatch();
    }
}
