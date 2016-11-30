import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Created by oktaysadoglu on 30/11/2016.
 */
public class App {


    public static void main(String[] args) throws IOException {

        App app = new App();

        String url = "http://www.resmigazete.gov.tr/eskiler";

        int numberOfLastDays = app.getNumberOfLastDays();

        try {
            Map<Calendar,List<String>> yonetmelikBundle = app.getYonetmeliklerBundle(numberOfLastDays,url);

            for (Map.Entry<Calendar,List<String>> entry : yonetmelikBundle.entrySet()) {

                File file = constructFilePath(entry.getKey());

                if (entry.getValue()!= null){

                    for (String s:entry.getValue()){

                        URL filledUrl = new URL(url + "/"+entry.getKey().get(Calendar.YEAR)+"/"+app.getProperMonth(entry.getKey().get(Calendar.MONTH))+"/"+s);

                        File file1 = new File(file.getPath()+"/"+app.getProperDay(entry.getKey().get(Calendar.DAY_OF_MONTH))+"/"+s);

                        FileUtils.copyURLToFile(filledUrl,file1);

                    }


                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Map<Calendar,List<String>> getYonetmeliklerBundle(int numberOfLastDays, String url) throws IOException {


        Map<Calendar,List<String>> pairsOfYonetmelikler = new HashMap<Calendar, List<String>>();

        for(int i = 0 ; i < numberOfLastDays ; i++){

            Calendar calendar = Calendar.getInstance();

            calendar.add(Calendar.DAY_OF_MONTH,-i);

            String filledUrl = url + "/"+calendar.get(Calendar.YEAR)+"/"+getProperMonth(calendar.get(Calendar.MONTH))+"/"+calendar.get(Calendar.YEAR)+getProperMonth(calendar.get(Calendar.MONTH))+getProperDay(calendar.get(Calendar.DAY_OF_MONTH))+".htm";

            pairsOfYonetmelikler.put(calendar,getYonetmelikler(filledUrl));


        }

        return pairsOfYonetmelikler;

    }

    private List<String> getYonetmelikler(String url) throws IOException {

        boolean inYonetmelikler = false;

        List<String> yonetmelikler = new ArrayList<String>();

        Document document = Jsoup.connect(url).get();

        Elements tableElements = document.select("table#AutoNumber1");

        if (tableElements.size() == 0)
            return null;

        Element reqElement = tableElements.select("tbody>tr>td").first();

        Elements elements = reqElement.select("p.MsoNormal");

        for(Element element:elements){

            if (element.select("font").text().equals("YÖNETMELİKLER")||element.select("font").text().equals("YÖNETMELİK")){

                inYonetmelikler = true;

                System.out.println("geldi");

            }

            if (inYonetmelikler){

                Elements e = element.select("a");

                if (e.size() < 1){
                    inYonetmelikler = false;
                }

                if (element.select("font").text().equals("YÖNETMELİKLER")||element.select("font").text().equals("YÖNETMELİK")){

                    inYonetmelikler = true;

                }

            }

            if (inYonetmelikler){

                if (!element.select("a").attr("href").equals("")){

                    yonetmelikler.add(element.select("a").attr("href"));

                }

            }

        }

        System.out.println(yonetmelikler);

        return yonetmelikler;

    }

    private static File constructFilePath(Calendar calender) {

        int year = calender.get(Calendar.YEAR);
        int month = calender.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = calender.get(Calendar.DAY_OF_MONTH);

        File theDir = new File(year + File.separator + month);

        if (!theDir.exists()) {

            boolean result = theDir.mkdirs();
            if (!result) {
                //  LOG.error("mkdirs cannot create folders/directories : " + theDir.getAbsolutePath());
                return null;
            }
        }
        return theDir;
    }

    private String getProperMonth(int month){

        month = month + 1;

        String properMonth = "";

        if (month < 10){

            properMonth = "0"+month;

        }else {

            properMonth = String.valueOf(month);

        }

        return properMonth;

    }

    private String getProperDay(int day){

        String properDay = "";

        if (day<10){

            properDay = "0"+day;

            return properDay;

        }else {

            return String.valueOf(day);

        }

    }

    private int getNumberOfLastDays(){

        Properties properties = new Properties();

        /*InputStream inputStream = null;*/

        try {
            /*inputStream = new FileInputStream("/src/main/resources/config.properties");*/

            properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

            String days = properties.getProperty("numberOfLastDays");

            return Integer.parseInt(days);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            /*if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
        }

        return 1;

    }

}
