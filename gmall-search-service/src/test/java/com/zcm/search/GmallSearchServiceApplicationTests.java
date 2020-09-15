package com.zcm.search;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.zcm.bean.PmsSearchSkuInfo;
import com.zcm.bean.PmsSkuAttValue;
import com.zcm.bean.PmsSkuInfo;
import com.zcm.service.PmsCommentService;
import com.zcm.service.SKUService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.log4j.Log4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.querydsl.QueryDslUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j
public class GmallSearchServiceApplicationTests {
    @Reference
    SKUService skuService;

    @Autowired
    JestClient jestClient;

    @Test
   public void contextLoads() throws IOException {
        //查询skuInfo的所有数据
         List<PmsSkuInfo> pmsSkuInfoList = skuService.getskuAll();
        //es的数据结构
        List<PmsSearchSkuInfo> pmsSearchSkuInfoList=new ArrayList<>();
        for(PmsSkuInfo pmsSkuInfo:pmsSkuInfoList){
            PmsSearchSkuInfo pmsSearchSkuInfo=new PmsSearchSkuInfo();
            //BeanUtils.copyProperties(pmsSkuInfo,pmsSearchSkuInfo);
            pmsSearchSkuInfo.setCatalog3Id(pmsSkuInfo.getCatalog3Id()==null?null:pmsSkuInfo.getCatalog3Id().toString());
            pmsSearchSkuInfo.setHotScore(new BigDecimal(0));
            pmsSearchSkuInfo.setPmsSkuAttValueList(pmsSkuInfo.getPmsSkuAttValueList());
            pmsSearchSkuInfo.setPrice(new BigDecimal(pmsSkuInfo.getPrice()));
            pmsSearchSkuInfo.setProductId(pmsSkuInfo.getProductId().longValue());
            pmsSearchSkuInfo.setSkuDefaultImg(pmsSkuInfo.getSkuDefaultImg());
            pmsSearchSkuInfo.setSkuName(pmsSkuInfo.getSkuName());
            pmsSearchSkuInfo.setSkuDesc(pmsSkuInfo.getSkuDesc());
            pmsSearchSkuInfoList.add(pmsSearchSkuInfo);
        }
        //导入es
        for(PmsSearchSkuInfo pmsSearchSkuInfo:pmsSearchSkuInfoList){
            //new Index 中.Builder为mysql中的数据，保存到es中为json格式
            Index index = new Index.Builder(pmsSearchSkuInfo).index("zcm_gmall").type("skuInfo").id(pmsSearchSkuInfo.getId()).build();
            jestClient.execute(index);
        }

    }
    @Test
    public void searchTest(){
        //jestde 的dsl工具
        // 构建搜索对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // bool
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        String pmsSkuAttValeuid="33,32,35";//商品的平台属性的多选
        String skuNmae="三叶";
        int pageSize=0;
        int pageNumber=10;

        boolQueryBuilder.filter(QueryBuilders.termsQuery("pmsSkuAttValueList.attrId",pmsSkuAttValeuid.split(",")));
        //分词查询
//        boolQueryBuilder.filter(QueryBuilders.termsQuery("skuName",skuNmae));
        boolQueryBuilder.should(QueryBuilders.commonTermsQuery("skuName",skuNmae));

        searchSourceBuilder.query(boolQueryBuilder);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("skuName");//高亮skuName
        highlightBuilder.preTags("<font color='red'>").postTags("</font>");//高亮样式
        searchSourceBuilder.highlight(highlightBuilder);

        searchSourceBuilder.sort("id",SortOrder.DESC);

        searchSourceBuilder.from(pageSize).size(pageNumber);

        SearchResult searchResult=searchSource(searchSourceBuilder, "zcm_gmall", "skuInfo");

        System.out.println("本次查询共查到："+searchResult.getTotal()+"件商品");
        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = searchResult.getHits(PmsSearchSkuInfo.class);
        System.out.println(hits.size());
        for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit:hits){
            PmsSearchSkuInfo source = hit.source;
            System.out.println(source);
            //获取高亮后的内容
            Map<String, List<String>> highlight = hit.highlight;
            if(highlight!=null){
                List<String> skuName1 = highlight.get("skuName");
                System.out.println(skuName1);
                if(skuName1!=null){
                    String s = skuName1.get(0);
                    System.out.println(s);
                }
            }
        }
        }
    @Test
    public void search(){
        //jestde 的dsl工具
        // 构建搜索对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // bool
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // filter-terms (类似于sql语句中的IN)
        String stockCode = "03377.XSHG,600160.XSHG,002298.XSHE";
        boolQueryBuilder.filter(QueryBuilders.termsQuery("stockCode", stockCode.split(",")));

        // must-term (类似于sql语句中的AND)
        boolQueryBuilder.must(QueryBuilders.termQuery("macdType", "底背离*"));
//        //分词查询，采用默认的分词器
//        QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("fieldName", "fieldlValue");
        // must-range (gte类似于sql语句中的>=)
        boolQueryBuilder.must(QueryBuilders.rangeQuery("time").gte("2020-03-27 00:00:00"));

        searchSourceBuilder.query(boolQueryBuilder);

        // sort (类似于sql语句中的ORDER BY,倒叙SortOrder.DESC)
        searchSourceBuilder.sort("time", SortOrder.DESC);

        //from-size (类似于sql语句中的LIMIT)
        searchSourceBuilder.from(0).size(12);
        //高亮
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.field("view");//高亮title
//        highlightBuilder.preTags("<em>").postTags("</em>");//高亮标签
//        highlightBuilder.fragmentSize(500);//高亮内容长度
//        searchSourceBuilder.highlighter(highlightBuilder);


        SearchResult searchResult = searchSource(searchSourceBuilder, "zcm_gmall", "skuInfo");

        boolean succeeded = searchResult.isSucceeded();
//        log.info("succeeded:{}", succeeded);
        Long total = searchResult.getTotal();
//        log.info("total:{}", total);
        if (!ObjectUtils.isEmpty(total) && total > 0) {
            System.out.println("true!!");
        }
        List<JSONObject> sourceAsObjectList = searchResult.getSourceAsObjectList(JSONObject.class, false);
//        log.info("sourceAsObjectList:{}", sourceAsObjectList);
    }

    public SearchResult searchSource(SearchSourceBuilder searchSourceBuilder, String index, String type) {
        try {
            String searchSourceBuilderStr = searchSourceBuilder.toString();
            if (ObjectUtils.isEmpty(type)) {
                Search search = new Search.Builder(searchSourceBuilderStr).addIndex(index).build();
                return jestClient.execute(search);
            } else {
                Search search = new Search.Builder(searchSourceBuilderStr).addIndex(index).addType(type).build();
                return jestClient.execute(search);
            }
        } catch (IOException e) {
//            log.error("searchSource failure!! error={}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
