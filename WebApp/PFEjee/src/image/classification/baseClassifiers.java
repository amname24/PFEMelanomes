package image.classification;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.SimpleLogistic;
import weka.classifiers.lazy.IBk;
import weka.classifiers.lazy.KStar;
import weka.classifiers.lazy.LWL;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.JRip;
import weka.classifiers.rules.PART;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.HoeffdingTree;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.LMT;
import weka.classifiers.trees.REPTree;
import weka.classifiers.trees.RandomTree;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

/**
 * @author agus
 */
public class baseClassifiers {
    
    public static void run() throws IOException, Exception {
    	// load CSV
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("C:\\Users\\me\\eclipse-workspace2\\PFEjee\\WebContent\\DOCS\\dataTrain.csv"));
        
        Instances datatrain = loader.getDataSet();
     // setting class attribute in the last of attribute
        if(datatrain.classIndex() == -1)
        	datatrain.setClassIndex(datatrain.numAttributes() - 1);
       // Instances datatrain = ReadFile(folder +"/" + name +"-uncertrain.arff");
        
        // base classifier
        int classifierNum = 8;
        Classifier learn[] = new Classifier[classifierNum];
        int x = 0;
        //Tree
        learn[x++] = new J48();             //0    //Tree
        //bayes
        learn[x++] = new NaiveBayes();     //1     //bayes
        
        //Rules
        learn[x++] = new JRip();          //2      //Rules        
        learn[x++] = new DecisionTable();   //3  //table  de decision
        
        //function         
            SMO smo = new SMO();
            smo.setBuildCalibrationModels(true);
        learn[x++] = smo;              //4         //function        
         learn[x++] = new MultilayerPerceptron();    //5
        learn[x++] = new SimpleLogistic();      //6
        //lazy
        learn[x++] = new IBk();         //7        //lazy
        
        
//set the classifier n train
        FilteredClassifier[] fc = new FilteredClassifier[classifierNum];
        for(int p=0;p<classifierNum;p++){          
            fc[p] = new FilteredClassifier();
            fc[p].setClassifier(learn[p]); 
            fc[p].buildClassifier(datatrain);
        }
        
        //test 
        //Instances datatest = ReadFile(folder +"/" + name +"-uncertrain.arff");
     // load CSV
        CSVLoader loader1 = new CSVLoader();
        loader1.setSource(new File("C:\\Users\\me\\eclipse-workspace2\\\\PFEjee\\WebContent\\DOCS\\dataTest.csv"));
        Instances datatest = loader.getDataSet();
        if(datatest.classIndex() == -1)
        	datatest.setClassIndex(datatest.numAttributes() - 1);
        for(int p=0;p<classifierNum;p++){          
        	double result  = fc[p].classifyInstance(datatest.instance(0));
            String prediction=datatest.classAttribute().value((int)result );
        	System.out.println("Prediction : "+prediction);
            double[] TempProb = fc[p].distributionForInstance(datatest.instance(0));
            System.out.print("tempProb : ");
            for(double i : TempProb)
            	System.out.print(i+" ");
            System.out.println();
        }
        
            
    }
    
//read your file, p.s. weka is only for single label learning, that is why you must set the class index
    private static Instances ReadFile(String fileAddress) {
        Instances data = null;
        
        weka.core.converters.ConverterUtils.DataSource source;
        try {
            source = new weka.core.converters.ConverterUtils.DataSource(fileAddress);
            data = source.getDataSet();
            
            // setting class attribute in the last of attribute
            if(data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);
        } catch (Exception ex) {
            System.err.println("data can't be read because of this error :" + ex);
        }
        
        return data;
    }
}
