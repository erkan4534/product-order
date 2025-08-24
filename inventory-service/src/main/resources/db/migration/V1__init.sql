create table `t_inventory`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `sku_code` varchar(255) default null,
    `quantity` int(11) default null,
     PRIMARY KEY (`id`)
)
