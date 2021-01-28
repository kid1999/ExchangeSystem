package io.kid1999.esystem.utils;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author kid1999
 * @create 2021-01-27 18:37
 * @description elasticsearch 工具类
 **/
@Slf4j
@Component
public class ESUtil {
    @Resource
    private RestHighLevelClient client;

    private static final String INDEX = "test";


    //创建索引
    public CreateIndexResponse createIndex(String index) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        CreateIndexResponse response = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        return response;
    }

    /**
     * 测试索引是否存在
     */
    public boolean existIndex(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        return exists;
    }

    /**
     * 删除索引
     */
    public AcknowledgedResponse deleteIndex(String index) throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
        AcknowledgedResponse delete = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        return delete;
    }

    /**
     * 添加文档
     */
    public IndexResponse createDocument(String index,Object object) throws IOException {
        IndexRequest request = new IndexRequest(index);
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");
        request.source(object);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        return response;
    }

    /**
     *     判断是否存在文档
     */
    public void isExist() throws IOException {
        GetRequest getRequest = new GetRequest(INDEX, "1");
        //不获取返回的source的上下文
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //获取文档信息
    public void getDocument() throws IOException {
        GetRequest getRequest = new GetRequest(INDEX, "1");
        GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
        //打印文档信息
        System.out.println(response.getSourceAsString());
        System.out.println(response);
    }

    //更新文档信息
    public void updateDocument() throws IOException {
        UpdateRequest request = new UpdateRequest(INDEX, "1");
        request.timeout("1s");
        HashMap<String,String> map = new HashMap<>();
        map.put("name","kid");
        map.put("password","asdasd");
        request.doc(map);
        UpdateResponse update = client.update(request, RequestOptions.DEFAULT);
        System.out.println(update);
        System.out.println(update.status());
    }
    //删除文档
    public void deleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest(INDEX, "1");
        request.timeout("10s");
        DeleteResponse update = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(update.status());
    }
    //批量插入数据
    public void BulkRequest() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        ArrayList<HashMap<String,String>> maps = new ArrayList<>();
        HashMap<String,String> map = new HashMap<>();
        map.put("name","kid");
        map.put("pass","dasda");
        for (int i = 0; i < maps.size(); i++) {
            bulkRequest.add(
                    new IndexRequest(INDEX)
                            .id("" + i + 1)
                            .source(maps.get(i))
            );
        }
        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk);

    }


}