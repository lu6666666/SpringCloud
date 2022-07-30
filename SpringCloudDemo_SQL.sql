CREATE DATABASE SpringCloud

CREATE TABLE products(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(50) COMMENT '商品名称', 
	price DOUBLE COMMENT '商品价格',
	flag VARCHAR(2) COMMENT '上架状态 0 为上架 1 已上架', 
	goods_desc VARCHAR(100) COMMENT '商品描述', 
	images VARCHAR(400) COMMENT '商品图片', 
	goods_stock INT COMMENT '商品库存', 
	goods_type VARCHAR(20) COMMENT '商品类型' 
)COMMENT '商品信息表' ENGINE=INNODB DEFAULT CHARSET=utf8 

INSERT INTO products (
  NAME,
  price,
  flag,
  goods_desc,
  images,
  goods_stock,
  goods_type
)
VALUES
  (
    '荣耀V10',
    3699,
    1,
    '麒麟990 蓝色',
    '',
    100,
    '电子用品'
  )