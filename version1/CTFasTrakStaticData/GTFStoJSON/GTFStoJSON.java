
/**
 * Purpose: convert the static GTFS data from https://www.cttransit.com/about/developers
 */

import java.io.*;
import java.util.Scanner;

public class GTFStoJSON
{  
    public static void main()throws IOException{
        String[] files = {"agency", "calendar", "calendar_dates", "routes",   //all of the file names from CTtransit website
            "shapes", "stop_times", "stops", "trips"};

        String folderPath = "";   //where the files named above are stored
        
        for (String fileName : files)
           convertGTFStoJSON(folderPath + "/" + fileName + ".txt", folderPath + "/" + fileName + "JSON.txt");
    }

    public static void convertGTFStoJSON(String inputPath, String outputPath) throws IOException{
        Scanner scanFile = new Scanner(new File(inputPath));
        String[] val;
        PrintWriter f = new PrintWriter(new FileWriter(outputPath));
        
        String headerString = scanFile.nextLine();
        String[] header = headerString.split(",");
        int n = header.length; 
        f.write("[");   //begin the array
        
        while(scanFile.hasNextLine()){
            val = scanFile.nextLine().split(",");  //get all the data values from that line into an String array
            f.print("{");          //"open" the object
            for (int i = 0; i < n; i++) {
                f.print("\"" + header[i] + "\":");         //write the attribute name surrounded by quotes and followed by a colon
                
                if (val[i].equals(""))  //if there is nothing
                  f.print("null");    //write null for javascript to interpret
                else
                  f.print("\"" + val[i] + "\"");  //else write the attribute value surrounded by quotes
                
                if (i+1 < n)   //if this isn't the last attribute
                  f.print(",");    //write a comma to separate the attribute: value pair        
                else            //if it is the last attribute
                  f.print("}");  //close the object
            }
            
            if (scanFile.hasNextLine())  //if there is another line of data
              f.print(",");              //write a comma
        }
        f.print("]");             //close the array
        f.close();
        scanFile.close();    
    }
    
    
}
