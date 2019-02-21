package com.jk.controller;/**
 * &lt;pre&gt;(这里用一句话描述这个方法的作用)
 *
 * @Author：陈斌 创建时间：
 * &lt;/pre&gt;
 */

import com.alibaba.fastjson.JSONObject;
import com.jk.bean.Mall_Sp;
import com.jk.utils.RestClientFactory;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** &lt;pre&gt;(这里用一句话描述这个方法的作用)
 * @Author：陈斌
 * 创建时间：     
 * &lt;/pre&gt;    
 */
@RestController
public class searchController {
 @RequestMapping("getProduct")
 public List<Mall_Sp> getSku(@RequestParam(value = "queryString") String queryString) throws IOException {
  List<Mall_Sp> list=new ArrayList<>();
  HighlightBuilder highlightBuilder = new HighlightBuilder();
  highlightBuilder.preTags("<span style='color:blue'>");
  highlightBuilder.postTags("</span>");
  highlightBuilder.field("shpmch");

  SearchRequest searchRequest = new SearchRequest("mall_sp");
  SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
  BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
  MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("shpmch", queryString);
  boolQuery.must(matchQuery);
  searchSourceBuilder.query(boolQuery);

  searchSourceBuilder.highlighter(highlightBuilder);

  searchRequest.source(searchSourceBuilder);
  SearchResponse search = RestClientFactory.getHighLevelClient().search(searchRequest);
  System.out.println(search);
  SearchHit[] hits = search.getHits().getHits();
  for (SearchHit hit : hits) {
   String string = hit.getSourceAsString();
   Mall_Sp product = JSONObject.parseObject(string, Mall_Sp.class);
   Map<String, HighlightField> fields = hit.getHighlightFields();
   HighlightField nameField = fields.get("shpmch");
   if (nameField != null) {
    product.setShpmch(nameField.fragments()[0].toString());
   }
   list.add(product);
  }
  return list;
 }
}
