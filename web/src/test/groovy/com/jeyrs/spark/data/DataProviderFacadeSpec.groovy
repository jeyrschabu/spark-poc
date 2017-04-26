package com.jeyrs.spark.data

import com.mongodb.MongoClient
import com.jeyrs.spark.model.Model
import spock.lang.Specification


class DataProviderFacadeSpec extends Specification{
    def 'should create morphia provider'() {
        given:
            def config = new MongoConnectionConfig(new MongoClient(), 'database')
        when:
            def morphiaProvider = new DataProviderFacade<Model>().get(config, Model.class )
        then:
            noExceptionThrown()
            morphiaProvider != null
    }
}
