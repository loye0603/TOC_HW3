import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.*;
public class TocHw3 {
        private static Object obj;
        public static void main(String[] args) throws Exception {
                try
                {
                String city,roadname,price,address,URLNAME;
				int year,year2 = 0,count=0;
                double price1=0.0;
               URLNAME=args[0];
                city=args[1];
               roadname=args[2];
                year=Integer.parseInt(args[3]);
               /*  city="大安區";
                 roadname="復興南路";
                 year=103;
                */
                
                
                
                
               // connect to URL
                HttpURLConnection conn = null;
                /*URL url = new URL(
                        "http://www.datagarage.io/api/5365dee31bc6e9d9463a0057");*/  
                URL url = new URL(URLNAME);  
                conn = (HttpURLConnection) url.openConnection();
               // conn.setReadTimeout(10000);
               // conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.connect();
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
                // 讀取資料
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream(), "UTF-8"));//or UTF-8?
                JSONTokener tokener = new JSONTokener(reader);
                JSONArray jsonRealPrice = new JSONArray(tokener);
                
                
                
                
                
                for(int counter =0; counter<jsonRealPrice.length();counter++)
                {
                 JSONObject a =  jsonRealPrice.getJSONObject(counter);
                 
                 address=a.get("土地區段位置或建物區門牌").toString();
                 String time=a.get("交易年月").toString();
                 if(time.length()==5){
                     year2=Integer.parseInt(time.substring(0,3));

                    }
                    else if (time.length()==4){
                   	  year2=Integer.parseInt(time.substring(0,2));
                       
                    }
    
                 if(year2>=year)
                 {				
                	 
                              price=a.get("總價元").toString();   
                              Pattern patterncity = Pattern.compile(city);
                              Pattern patternroad = Pattern.compile(roadname);
                              Matcher matcher1 = patterncity.matcher(address);
                              Matcher matcher2 = patternroad.matcher(address);
                              if(matcher1.find())
                              {
                               if (matcher2.find())
                             {
                               price1+=Double.parseDouble(price);
                              System.out.println(city+"  "+address+"   "+time+"  "+price);
                              count++; }
                         }
                      
                 }     
                        
                        
                }
                
                 price1/=count;
                System.out.println("Average:"+(int)price1);
                }
                catch(Exception e)  {
                e.printStackTrace();
            }
        }
}
