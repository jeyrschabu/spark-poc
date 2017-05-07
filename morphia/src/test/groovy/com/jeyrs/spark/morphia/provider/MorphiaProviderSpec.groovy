package com.jeyrs.spark.morphia.provider

import com.jeyrs.spark.morphia.model.MorphiaProduct
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.query.Query
import spock.lang.Specification


class MorphiaProviderSpec extends Specification {
  def 'should find all documents'() {
    given:
    def store = Mock(Datastore)
    def provider = new MorphiaProvider(store, MorphiaProduct.class)

    and:
    def query = Mock(Query)
    query.asList() >> [new MorphiaProduct(), new MorphiaProduct()]
    store.createQuery(MorphiaProduct.class) >> query

    when:
    def result = provider.findAll()

    then:
    result.size() == 2
  }

}
