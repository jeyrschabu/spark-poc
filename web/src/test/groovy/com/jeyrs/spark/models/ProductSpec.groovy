package com.jeyrs.spark.models

import com.jeyrs.spark.model.Product
import spock.lang.Specification


class ProductSpec extends Specification {
    def 'should have functioning getters and setters'() {
        given:
            def product = new Product(name: 'name', category: 'category', price: 1500)
        expect:
            product.getName() == 'name'
            product.getCategory == 'category'
            product.getPrice() > 0
    }
}
