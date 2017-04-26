package com.jeyrs.spark.services

import com.jeyrs.spark.data.ModelProvider
import com.jeyrs.spark.model.BaseProduct
import spock.lang.Specification

class ProductServiceSpec extends Specification{

    def 'should return a list of products'() {
        given:
            def provider = Mock(ModelProvider)
            ProductService productService = new ProductService()
            productService.modelProvider =  provider

            provider.findAll() >> [new BaseProduct()]
        when:
            def result = productService.findAll()
        then:
            result.size() == 1
    }
}
