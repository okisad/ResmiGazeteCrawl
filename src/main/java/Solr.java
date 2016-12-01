import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.common.SolrInputDocument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * Created by oktaysadoglu on 02/12/2016.
 */
public class Solr {

    public void indexDataToSolr() throws IOException, SolrServerException {

        if (getNewsFilesPath() != null && getSolrServerName() != null){

            SolrServer httpSolrServer = new HttpSolrServer("http://localhost:8983/solr/gazeteler");

            SolrInputDocument doc1 = new SolrInputDocument();

            List<String> filePaths = new ArrayList<>();

            try(Stream<Path> paths = Files.walk(Paths.get("yonetmelikler"))) {
                paths.forEach(filePath -> {
                    if (Files.isRegularFile(filePath)) {

                        filePaths.add(filePath.toString());

                    }
                });
            }

            System.out.println(filePaths.toString());

            for (String s:filePaths){

                String type = org.apache.commons.lang.StringUtils.substringAfterLast(s,".");

                String fileName = org.apache.commons.lang.StringUtils.substringAfterLast(s,"/");

                if (!fileName.equals(".DS_Store")){

                    ContentStreamUpdateRequest up = new ContentStreamUpdateRequest("/update/extract");

                    if (type.equals("pdf")){

                        up.addFile(new File(s),"application/pdf");

                    }else if (type.equals("htm")){

                        up.addFile(new File(s),"text/html");

                    }

                    up.setParam("literal.id", fileName);
                    up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
                    httpSolrServer.request(up);

                }


            }


        }


    }

    private String getSolrServerName(){

        Properties properties = new Properties();

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

            String solrServerName = properties.getProperty("solrServer");

            return solrServerName;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    private String getNewsFilesPath(){

        Properties properties = new Properties();

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

            String newsFilesPath = properties.getProperty("newsFilesPath");

            return newsFilesPath;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


}
