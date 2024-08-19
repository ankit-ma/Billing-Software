package com.janta.billing.document;


import org.apache.solr.client.solrj.beans.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {

    @Field("productId")
    private Object productId;

    @Field("productName")
    private Object productName;

    @Field("brandName")
    private Object brandName;

    @Field("additionalInfo")
    private Object additionalInfo;

    @Field("quantity")
    private Object quantity;

    @Field("availableQuantity")
    private Object availableQuantity;

    @Field("mrp")
    private Object mrp;

    @Field("sgst")
    private Object sgst;

    @Field("cgst")
    private Object cgst;

    @Field("discount")
    private Object discount;


       
}

