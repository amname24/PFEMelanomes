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
import weka.classifiers.lazy.kstar.KStarCache;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.JRip;
import weka.classifiers.rules.PART;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.HoeffdingTree;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.LMT;
import weka.classifiers.trees.M5P;
import weka.classifiers.trees.REPTree;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

/**
 * @author agus
 */
public class baseClassifiers {
    
    public static void run() throws IOException, Exception {
        
        
        String folder = "C:/Users/me/eclipse-workspace2/PFEjee/WebContent/DOCS";
        
        Instances datatrain = ReadFile(folder +"/dataTrain.arff");
        Instances datatest = ReadFile(folder  +"/dataTest.arff");
        int numDataTrain = datatrain.numInstances();
        int numDataTest = datatest.numInstances();
        String separator =";";
        
// base classifier
System.out.println("Define the base classifiers...");
        String folderOutput = folder + "/";
        int classifierNum = 7;
        Classifier learn[] = new Classifier[classifierNum];
        int x = 0;
        //Tree
        learn[x++] = new RandomForest();     
        //bayes
        learn[x++] = new NaiveBayes();     
        
        //lazy
        learn[x++] = new IBk();  
        learn[x++] = new KStar();   
        learn[x++] = new MultilayerPerceptron();   
        learn[x++] = new DecisionTable();   
        learn[x++] = new BayesNet();   

        
        classifierNum=x;
        
        
//set the classifier n train
        FilteredClassifier[] fc = new FilteredClassifier[classifierNum];
        System.out.println("Training time:");        
        long startTime = System.currentTimeMillis();        
        for(int p=0;p<classifierNum;p++){          
            fc[p] = new FilteredClassifier();
            fc[p].setClassifier(learn[p]); 
            fc[p].buildClassifier(datatrain);
            
            long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            startTime = endTime; 
            System.out.println((p+1)+ separator + totalTime);
        }
        
        System.out.println("\nTraining prediction:");   
        double [] Truth = new double[numDataTrain];
        int confMatrix[][]= new int[4][classifierNum];
        for(int i=0;i<4;i++)
            Arrays.fill(confMatrix[i], 0);
                       
        for (int i = 0; i < numDataTrain; i++) {        
            int Prediction[] = new int[classifierNum];
            Truth[i]=(int)datatrain.instance(i).value(datatrain.instance(i).numAttributes()-1);
            
            System.out.print("\n" + Truth[i] + separator);
            for(int p =0;p<classifierNum;p++){
                startTime = System.currentTimeMillis(); 
                Prediction[p]= (int)fc[p].classifyInstance(datatrain.instance(i));
                double[] TempProb = fc[p].distributionForInstance(datatrain.instance(i));
                long endTime   = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                
                if(Prediction[p]==Truth[i]){
                    if(Truth[i]==1) confMatrix[0][p]++;     //TP
                    else            confMatrix[1][p]++;     //TN
                }
                else{
                    if(Truth[i]==1) confMatrix[2][p]++;     //FN
                    else            confMatrix[3][p]++;     //FP
                }

                System.out.print(Prediction[p] + separator + String.format("%.3f", TempProb[Prediction[p]]) + separator + totalTime + separator + separator);
                
            }
        }
        
        System.out.println("\nTraining Confusion matrix:\n id"+ separator+  "TP" + separator+ "TN" + separator+ "FP" + separator+ "FN" 
                + separator + separator + "Accuracy" + separator + "Kappa" + separator 
                + "Sensitivity" + separator + "Specificity"+ separator + "KappaSensSpec");
        
        for(int p =0;p<classifierNum;p++){
            double TP= (double)confMatrix[0][p], TN= (double)confMatrix[1][p], 
                    FN= (double)confMatrix[2][p], FP= (double)confMatrix[3][p];            
            double Accuracy = (TP + TN)/(TP+TN+FP+FN); 
            double Po= (TP+TN)/(TP+TN+FP+FN);
            double Pe= ((TN+FP)*(FN+TN) + (FN+TP)*(FP+TP))/(numDataTrain*numDataTrain);           
            double F1 =(2*TP)/(2*TP+FP+FN);       
            double TPR=TP/(TP+FN), TNR=TN/(TN+FP);
            double Kappa= (Po-Pe)/(1-Pe);
            double KappaX= (2*Kappa+TPR+TNR)/4;
            
            System.out.println("" + (p+1) + separator + TP + separator + TN + separator + FP + separator + FN 
                    + separator + separator + Accuracy + separator + Kappa + separator + TPR + separator 
                    + TNR + separator + KappaX + separator);
        }
        
        int[] TruthTest = new int[datatest.numInstances()];        
        System.out.println("\nStart to predict: ");
        
//predicting
        for(int i=0;i<4;i++)
            Arrays.fill(confMatrix[i], 0);
        int numAttribute = datatest.numAttributes();
        for (int i = 0; i < datatest.numInstances(); i++) {
            TruthTest[i]=(int)datatest.instance(i).value(numAttribute-1);
            System.out.println("\n" + TruthTest[i] + separator);
            
            int Prediction[] = new int[classifierNum];
            for(int p =0;p<classifierNum;p++){
                startTime = System.currentTimeMillis(); 
                Prediction[p]= (int)fc[p].classifyInstance(datatest.instance(i));
                double[] TempProb = fc[p].distributionForInstance(datatest.instance(i));
                long endTime   = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                
                if(Prediction[p]==TruthTest[i]){
                    if(TruthTest[i]==1) confMatrix[0][p]++;     //TP
                    else            confMatrix[1][p]++;     //TN
                }
                else{
                    if(TruthTest[i]==1) confMatrix[2][p]++;     //FN
                    else            confMatrix[3][p]++;     //FP
                }

                System.out.println(Prediction[p] + separator + TempProb[Prediction[p]]);
                
            }
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
    
    private static double Prob(double[] Prob) {
          if(Prob[0]>Prob[1])
              return Prob[0];
          else
              return Prob[1];
    }
}
