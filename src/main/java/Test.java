import org.apache.commons.codec.binary.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by oktaysadoglu on 02/12/2016.
 */
public class Test {

    public static void main(String[] args){



    /*public static void main(String[] args) throws IOException, SolrServerException {

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


        }*/

        /*assertNotNull("Couldn't upload mailing_lists.pdf", result);
        rsp = server.query( new SolrQuery( "*:*") );
        Assert.assertEquals( 1, rsp.getResults().getNumFound() );
*/
        /*File file = new File("~/Desktop/artiwise/Spark_Training_Exercise_Instructions.pdf");*/
        /*doc1.addField( "id", "id1", 1.0f );
        doc1.addField( "name", "doc1", 1.0f );
        doc1.addField( "price", 10 );*/

       /* Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

        docs.add(doc1);

        httpSolrServer.commit();

        UpdateRequest req = new UpdateRequest();
        req.setAction( UpdateRequest.ACTION.COMMIT, false, false );
        req.add( docs );
        UpdateResponse rsp = req.process( httpSolrServer);*/


    }
}
