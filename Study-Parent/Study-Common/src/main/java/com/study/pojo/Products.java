package com.study.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "products")
public class Products {

  @Id
  private long id;
  private String name;
  private double price;
  private String flag;
  private String goodsDesc;
  private String images;
  private long goodsStock;
  private String goodsType;
}
