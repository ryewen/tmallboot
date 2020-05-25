DROP DATABASE IF EXISTS tmallboot;
CREATE DATABASE tmallboot DEFAULT CHARACTER SET utf8;
USE tmallboot;

CREATE TABLE user (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(100) NOT NULL,
    password varchar(100) NOT NULL,
    salt varchar(100),
    PRIMARY KEY (id),
    CONSTRAINT uc_user UNIQUE (name) //
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE role (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE userrole (
    id int NOT NULL AUTO_INCREMENT,
    uid int NOT NULL,
    rid int NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE category (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(100) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE property (
    id int NOT NULL AUTO_INCREMENT,
    cid int NOT NULL,
    name varchar(100) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_property_category FOREIGN KEY (cid) REFERENCES category (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE product (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(255) DEFAULT NULL,
    subTitle varchar(255) DEFAULT NULL,
    originalPrice float DEFAULT NULL,
    promotePrice float DEFAULT NULL,
    stock int(11) DEFAULT NULL,
    cid int(11) DEFAULT NULL,
    createDate datetime DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_product_category FOREIGN KEY (cid) REFERENCES category (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE propertyvalue (
    id int NOT NULL AUTO_INCREMENT,
    pid int(11) DEFAULT NULL,
    ptid int(11) DEFAULT NULL,
    value varchar(255) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_propertyvalue_property FOREIGN KEY (pid) REFERENCES property (id),
    CONSTRAINT fk_propertyvalue_product FOREIGN KEY (ptid) REFERENCES product (id),
    CONSTRAINT uc_propertyvalue_property_product UNIQUE (ptid, pid)  //
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE productimage (
    id int NOT NULL AUTO_INCREMENT,
    pid int(11) DEFAULT NULL,
    type varchar(255) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_productimage_product FOREIGN KEY (pid) REFERENCES product (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE categoryimage (
    id int NOT NULL AUTO_INCREMENT,
    cid int(11) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_categorytimage_category FOREIGN KEY (cid) REFERENCES category (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE review (
    id int NOT NULL AUTO_INCREMENT,
    content varchar(4000) DEFAULT NULL,
    uid int(11) DEFAULT NULL,
    pid int(11) DEFAULT NULL,
    createDate datetime DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_review_product FOREIGN KEY (pid) REFERENCES product (id),
    CONSTRAINT fk_review_user FOREIGN KEY (uid) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE order_ (
    id int NOT NULL AUTO_INCREMENT,
    orderCode varchar(255) DEFAULT NULL,
    address varchar(255) DEFAULT NULL,
    post varchar(255) DEFAULT NULL,
    receiver varchar(255) DEFAULT NULL,
    mobile varchar(255) DEFAULT NULL,
    userMessage varchar(255) DEFAULT NULL,
    createDate datetime DEFAULT NULL,
    payDate datetime DEFAULT NULL,
    deliveryDate datetime DEFAULT NULL,
    confirmDate datetime DEFAULT NULL,
    uid int(11) DEFAULT NULL,
    status varchar(255) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_user FOREIGN KEY (uid) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE orderitem (
    id int NOT NULL AUTO_INCREMENT,
    pid int(11) DEFAULT NULL,
    oid int(11) DEFAULT NULL,
    uid int(11) DEFAULT NULL,
    number int(11) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_orderitem_user FOREIGN KEY (uid) REFERENCES user (id),
    CONSTRAINT fk_orderitem_product FOREIGN KEY (pid) REFERENCES product (id),
    CONSTRAINT fk_orderitem_order FOREIGN KEY (oid) REFERENCES order_ (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;