package com.jeyrs.spark.models

import com.jeyrs.spark.model.BaseProduct
import spock.lang.Specification


class ProductSpec extends Specification {
    def 'should have functioning getters and setters'() {
        given:
            def product = new BaseProduct(name: 'name', category: 'category', price: 1500)
        expect:
            product.getName() == 'name'
            product.getCategory == 'category'
            product.getPrice() > 0
    }
}
