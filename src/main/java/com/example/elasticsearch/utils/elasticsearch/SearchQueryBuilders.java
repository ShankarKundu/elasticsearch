package com.example.elasticsearch.utils.elasticsearch;

import com.example.elasticsearch.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
public class SearchQueryBuilders {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public List<Book> getOnlyTitle(String url){
 QueryBuilder query = QueryBuilders.boolQuery()
                .should(QueryBuilders.multiMatchQuery(url, "title")
                        .lenient(true)
                        .field("title")
                        .boost(2.0f)
                        .type( MultiMatchQueryBuilder.Type.PHRASE)
                );

 log.info("******** given data query: \n{}", query);
  SearchQuery build = new NativeSearchQueryBuilder().withQuery(query).build();
    List< Book > listBook= elasticsearchTemplate.queryForList(build, Book.class);
        return listBook;
    }




}
