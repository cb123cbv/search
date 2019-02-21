package com.jk.bean;/**
 * &lt;pre&gt;(这里用一句话描述这个方法的作用)
 *
 * @Author：陈斌 创建时间：
 * &lt;/pre&gt;
 */

import lombok.Data;

/** &lt;pre&gt;(这里用一句话描述这个方法的作用)
 * @Author：陈斌
 * 创建时间：     
 * &lt;/pre&gt;    
 */
@Data
public class  Mall_Sku {
 Integer id;               //(编号)          PKInteger
 Integer shp_id;          //(商品id)      Integer
 Integer kc;               //(库存)          Integer
 Double jg;              //(价格)          decimal
 String chjshj;           //(创建时间)  Date
 String sku_mch;          //(sku名称)  String(100)
 Integer sku_xl;           //(sku销量)   Integer
 String kcdz;             //(库存地址)    String(500)
}
