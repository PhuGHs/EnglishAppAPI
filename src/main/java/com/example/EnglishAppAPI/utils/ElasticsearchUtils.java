package com.example.EnglishAppAPI.utils;

import co.elastic.clients.elasticsearch._types.query_dsl.FuzzyQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.function.Supplier;

public class ElasticsearchUtils {
    public static Supplier<Query> createSupplier(String query, String field) {
        return () -> Query.of(q -> q.match(createFuzzyQuery(query, field)));
    }

    public static MatchQuery createFuzzyQuery(String query, String field) {
        return new MatchQuery.Builder()
                .field(field)
                .query(query)
                .fuzziness("AUTO")
                .build();
    }

    public static Supplier<Query> createExclusionQuery(Long value, String field) {
        return () -> Query.of(q -> q.bool(b -> b.must(Query.of(q2 -> q2.term(t -> t.field(field).value(value))))));
    }
}
