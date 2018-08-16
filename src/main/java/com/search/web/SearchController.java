package com.search.web;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jdk.nashorn.internal.objects.Global.println;

/**
 * @Title: SearchController
 * @Package com.search.web
 * @Description:
 * @Author duanke
 * @Date 2018/8/1 20:16
 * @Version V1.0
 */
@RestController
public class SearchController {


    @Autowired
    private TransportClient client;

    /**
     * 创建索引
     * @return
     * @throws IOException
     */
    @RequestMapping("index")
    @ResponseBody
    public String createIndex() throws IOException {

        CreateIndexRequestBuilder cib=client.admin().indices().prepareCreate("tag");
        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("properties") //设置之定义字段
                .startObject("author")
                .field("type","string") //设置数据类型
                .endObject()
                .startObject("title")
                .field("type","string")
                .endObject()
                .startObject("content")
                .field("type","string")
                .endObject()
                .startObject("price")
                .field("type","string")
                .endObject()
                .startObject("view")
                .field("type","string")
                .endObject()
                .startObject("tag")
                .field("type","string")
                .endObject()
                .startObject("date")
                .field("type","date")  //设置Date类型
                .field("format","yyyy-MM-dd HH:mm:ss") //设置Date的格式
                .endObject()
                .endObject()
                .endObject();
        cib.addMapping("con", mapping);

        CreateIndexResponse res=cib.execute().actionGet();
        return "";

//        BulkRequestBuilder bulkRequest = client.prepareBulk();
//        bulkRequest.add(this.client.prepareIndex("tdh", "wsfz")
//                .setSource(XContentFactory.jsonBuilder()
//                    .startObject()
//                        .startObject("properties")
//                            .startObject("title").field("type","text").field("analyzer","ik_max_word").endObject()
//                            .startObject("status").field("type","text").endObject()
//                            .startObject("content").field("type","text").endObject()
//                        .endObject()
//                    .endObject()
//                )
//        );
//        BulkResponse bulkResponse = bulkRequest.get();
//        return bulkResponse.getTook().toString();
    }

    /**
     * 删除所有
     */
    @RequestMapping("delete")
    @ResponseBody
    public void deleteAll() {
        DeleteByQueryAction.INSTANCE.newRequestBuilder(client).source("tdh").filter(QueryBuilders.matchAllQuery()).get();
    }


    /**
     * 查询所有数据
     * @throws Exception
     */
    @RequestMapping("findall")
    @ResponseBody
    public void search() throws Exception{
        SearchResponse response = client.prepareSearch("tdh").setTypes("wsfz").get();
        println(response);
        for (SearchHit  searchHit: response.getHits()) {
            println(searchHit);
        }
    }

    /**
     * 批量插入
     * @return
     * @throws Exception
     */
    @RequestMapping("bulk")
    @ResponseBody
    public String insetDate() throws Exception {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        //插入
        bulkRequest.add(this.client.prepareIndex("tdh", "wsfz")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("title", "优衣库官方旗舰店")
                        .field("status", "1")
                        .field("content", "优衣库官方旗舰店")
                        .endObject()
                )
        );
        bulkRequest.add(this.client.prepareIndex("tdh", "wsfz")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("title", "衣架")
                        .field("status", "1")
                        .field("content", "某知名网络小说作家已经完成了大话西游同名小说的出版")
                        .endObject()
                )
        );
        bulkRequest.add(this.client.prepareIndex("tdh", "wsfz")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("title", "衣服女夏2018新款")
                        .field("status", "1")
                        .field("content", "衣服女夏2018新款，正在火爆内测中")
                        .endObject()
                )
        );
        bulkRequest.add(this.client.prepareIndex("tdh", "wsfz")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("title", "衣柜")
                        .field("status", "2")
                        .field("content", "衣柜，正在火爆内测中")
                        .endObject()
                )
        );
        //批量执行
        BulkResponse bulkResponse = bulkRequest.get();
        client.close();
        return bulkResponse.getTook().toString();
    }


    /**
     * 搜索建议
     *
     * @param tmptitle
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("suggestion")
    @ResponseBody
    public Map<String, Object> querySuggestion(
            @RequestParam(value = "tmptitle", defaultValue = "") String tmptitle
    ) throws UnsupportedEncodingException {

        //返回的map，进行数据封装
        Map<String, Object> msgMap = new HashMap<>();
        //创建需要搜索的inde和type
        SearchRequestBuilder requestBuilder = client.prepareSearch("tdh").setTypes("wsfz");
        //设置搜索建议
        CompletionSuggestionBuilder completionSuggestionBuilder = new CompletionSuggestionBuilder("title.suggest")
                .text(tmptitle).size(10);
        SuggestBuilder suggestBuilder = new SuggestBuilder().addSuggestion("title.suggest", completionSuggestionBuilder);

        requestBuilder.suggest(suggestBuilder);
        //进行搜索
        SearchResponse suggestResponse = requestBuilder.execute().actionGet();

        //用来处理的接受结果
        List<String> result = new ArrayList<>();

        List<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> entries = suggestResponse.getSuggest()
                .getSuggestion("title.suggest").getEntries();
        //处理结果
        for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> op : entries) {
            List<? extends Suggest.Suggestion.Entry.Option> options = op.getOptions();
            for (Suggest.Suggestion.Entry.Option pp : options) {
                result.add(pp.getText().toString());
            }
        }
        msgMap.put("result", result);
        return msgMap;
    }

    /**
     * termQuery("key", obj) 完全匹配
     * termsQuery("key", obj1, obj2..)   一次匹配多个值
     * matchQuery("key", Obj)  单个匹配, field不支持通配符, 前缀具高级特性
     * multiMatchQuery("text", "field1", "field2"..);  匹配多个字段, field有通配符忒行
     * matchAllQuery();         匹配所有文件
     */
    @RequestMapping("must")
    @ResponseBody
    public List querymuch(@RequestParam(value = "keyword", defaultValue = "") String keyword) {
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(QueryBuilders.termQuery("status", "1"));
        boolBuilder.must(QueryBuilders.matchQuery("title", keyword));
        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch("tdh")
                .setTypes("wsfz")
                .setQuery(boolBuilder)
                .setFrom(0)
                .setSize(10);
        SearchResponse response = searchRequestBuilder.get();
        List result = new ArrayList<>();

        for (SearchHit hit : response.getHits()) {
            Map map = hit.getSource();
            System.out.println("===" + map);
            result.add(map);
        }
        return result;
    }


    /**
     * 进行搜索
     * 将结果进行分页
     *
     * @param title
     * @return
     */
    @RequestMapping("search")
    @ResponseBody
    public Map<String, Object> goSearch(
            @RequestParam(value = "title", defaultValue = "") String title
//            ,@RequestParam(value="current",defaultValue = "") int current,
//            @RequestParam(value="size",defaultValue = "") int size
    ) {
        //返回的map，进行数据封装
        Map<String, Object> msgMap = new HashMap<String, Object>();
        //建立bool查询，如果没有组合查询，直接写QueryBuilder
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        //使用should实现或者查询
        boolBuilder.must(QueryBuilders.matchQuery("title", title));
        //查询总记录数和当前页数
//        current = (current - 1) * size;
        //c查询
        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch("tdh")
                .setTypes("wsfz")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH) //设置查询类型：1.SearchType.DFS_QUERY_THEN_FETCH 精确查询； 2.SearchType.SCAN 扫描查询,无序
                .setQuery(boolBuilder)
                .setFrom(0)
                .setSize(10);

        //设置高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false);
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        searchRequestBuilder.highlighter(highlightBuilder);
        //执行结果
        SearchResponse response = searchRequestBuilder.get();
        //接受结果
        List<Map<String, Object>> result = new ArrayList<>();
        //遍历结果
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            //处理高亮片段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField nameField = highlightFields.get("title");
            if (nameField != null) {
                Text[] fragments = nameField.fragments();
                String nameTmp = "";
                for (Text text : fragments) {
                    nameTmp += text;
                }
                //将高亮片段组装到结果中去
                source.put("title", nameTmp);
            }
            result.add(source);
        }
        //封装数据返回
        msgMap.put("itemsList", result);     //搜索结果
        //msgMap.put("page","page");          //分页
        msgMap.put("took", response.getTook().getSecondsFrac()); //获取响应需要的时间
//        msgMap.put("total", totalCount);     //获得查询的总记录数
        return msgMap;
    }
}
