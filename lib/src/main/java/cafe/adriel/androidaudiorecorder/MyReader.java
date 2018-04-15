package cafe.adriel.androidaudiorecorder;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import android.os.Environment;
import android.util.Log;

public class MyReader {

    static String[] celebs = { "Jordan" , "Kush" , "Varun" , "Ethan", "Beyonce" , "Justin Bieber" , "Taylor Swift" , "Kanye West" , "Eminem" , "Camilla Cabello" };

    static double[] jordan = {1124.6549919224735, 1124.6549919224733, 1087.2038705851498, 1087.2038705851494, 1009.6456595480445, 1009.6456595480443, 945.6186883138336, 945.6186883138336, 755.0665930827921, 755.066593082792};
    static double[] kush =   {4876.624343924, 4876.624343924, 3650.7303275066847, 3650.7303275066847, 3031.236879739499, 3031.2368797394984, 2977.4680269897594, 2977.468026989759, 2638.2199584840314, 2638.219958484031};
    static double[] varun =  {7021.197006625442, 7021.197006625441, 6734.01621685986, 6734.01621685986, 4056.1526097104297, 4056.1526097104297, 3862.766132660692, 3862.7661326606913, 3621.2152173448694, 3621.215217344869};
    static double[] ethan = {6423.457520715134, 6423.457520715134, 4759.356994628906, 4294.306520062511, 4294.306520062511, 4082.9368329547156, 4082.936832954714, 3915.8911248221402, 3915.8911248221402, 3871.013248583574};
    static double[] beyonce = {2006.348205524248, 2006.3482055242478, 1761.0277471877125, 1761.0277471877125, 1726.5061231451539, 1726.5061231451539, 1691.2000954295445, 1691.2000954295443, 1585.635288872284, 1585.635288872284};
    static double[] justin = {2967.8697244291593, 2967.869724429159, 2527.5553003261166, 2527.5553003261166, 1775.9958725680317, 1775.9958725680317, 1664.4524520436373, 1664.452452043637, 1357.708179063616, 1357.7081790636155};
    static double[] taylor = {1523.0362124660492, 1523.0362124660492, 1511.157648901422, 1511.1576489014215, 1312.798201863143, 1312.7982018631426, 1258.5165335092556, 1258.5165335092556, 1223.6384779109947, 1223.6384779109947};
    static double[] kanye = {3124.071123915198, 3124.071123915198, 1830.696166217737, 1830.6961662177364, 1655.1207522839925, 1655.1207522839923, 1515.7459265179389, 1515.7459265179389, 1432.5377508425804, 1432.5377508425804};
    static double[] eminem = {2798.7074901044348, 2798.7074901044346, 2068.223420033517, 1768.223420033517, 1586.8322908279715, 1586.8322908279713, 1516.437269756182, 1516.4372697561819, 1490.70745059342, 1490.70745059342};
    static double[] camilla= {2018.8562122515552, 2018.8562122515548, 1984.0186783720637, 1984.0186783720635, 1959.5620508128704, 1959.5620508128702, 1894.6333846265827, 1894.6333846265823, 1837.460675249339, 1837.4606752493387};
    static int len = 32768;
    static double[][] cele = {jordan , kush , varun, ethan, beyonce, justin, taylor, kanye, eminem , camilla};

    public static void main(String[] args)
    {


        //System.out.println(mostLike("kush2"));

    }

    public static String mostLike(File input)
    {
        double mindist = 1000000000;
        int mindex = -1;
        double [] vals  = new double[0];
        double [] bestest = best(input);

        for(int i  = 0 ; i < cele.length ; i++)
        {
            double temp = dist(findComp(bestest) , cele[i]);
            if(temp < mindist)
            {
                mindist = temp;
                mindex = i;
            }
        }
        return (celebs[mindex]);
    }

    public static Complex makeComp(double num)
    {
        return new Complex(num , 0);
    }

    public static double[] findComp(double[] buffer )
    {
        double[] arr = new double[10];


           // Log.d("lol" , " "  +file.getParent());
            //WavFile wavFile = WavFile.openWavFile(filename);


            //Log.d("lol" , " " + wavFile.getNumFrames());

            //Log.d("Number of Channels" , " " + wavFile.getNumChannels());
          //  Log.d("Number of Channels" , " " + wavFile.getSampleRate());


            // Get the number of audio channels in the wav file

            Log.d("lol" , " " + buffer.length);
            //Log.d("lol" , " " + wavFile.getFramesRemaining());

//            int framesRead = wavFile.readFrames(buffer, len/2);

           // Log.d("lol" , Arrays.toString(buffer));






            Complex[] x = new Complex[len];
            for(int  i = 0; i < len ; i++)
            {
                x[i] = makeComp(buffer[i]);
            }

        Log.d("lol2" , " " + buffer.length);
            Complex[] y = FFT.fft(x);

            double[] lengths = new double[y.length];

        Log.d("lol3" , " " + buffer.length);
            double max = 0;
            for(int  i = 0; i < len; i++)
            {
                lengths[i] = y[i].abs();
            }
            Arrays.sort(lengths );

            for(int i = 0; i < lengths.length / 2; i++)
            {
                double temp = lengths[i];
                lengths[i] = lengths[lengths.length - i - 1];
                lengths[lengths.length - i - 1] = temp;
            }

            for(int  i = 0 ; i < 10 ; i++)
            {
                arr[i]= lengths[i];
            }
            Log.d("lol4" , Arrays.toString(arr));

            return arr;



        //return arr;
    }




    public static double dist(double[] arr1 , double[] arr2)
    {
        double dist = 0;

        for(int  i = 0 ; i < 10;i++)
        {
            dist += Math.pow(arr1[i] - arr2[i] , 2);
        }
        return dist;
    }

    public static double[] best(File f)
    {

        double[] ret;
        try {
            WavFile wavFile = WavFile.openWavFile(f);

            int frames = (int)wavFile.getNumFrames();
            double[] buffer = new double[(int)wavFile.getNumFrames()];


            int framesRead = wavFile.readFrames(buffer,  (int)wavFile.getNumFrames()/2);

            System.out.println(buffer.length);


            int mindex = 0;

            double val =avg(buffer , 0 , len);
            double max = val;
            for(int  i = 1 ;  frames > (len + 2000) + i ; i++)
            {
                val -= Math.pow(buffer[i],2);
                val+= Math.pow(buffer[i + len],2);

                if(val > max)
                {
                    max = val;
                    mindex = i;

                }
            }


            ret = new double[len];

            for(int  i = mindex ; i < mindex +  len ; i++)
            {
                ret[i-mindex] = buffer[i];
            }


            Log.d("pls" , Arrays.toString(ret));
            return ret;


        } catch (IOException e) {
            e.printStackTrace();
        } catch (WavFileException e) {
            e.printStackTrace();
        }
        ret = new double[1];
        return ret;
    }

    public static double avg(double[] arr , int start, int end)
    {
        double val = 0;
        for(int i  = start ; i < end ; i++)
        {
            val+= Math.pow(arr[i] , 2);
        }
        return val;

    }





}
