CREATE TABLE IF NOT EXISTS mobilesubscriber (
    id int not NULL AUTO_INCREMENT PRIMARY KEY,
    msisdn varchar(20) not NULL,
    customer_id_owner int not NULL,
    customer_id_user int not NULL,
    service_type varchar(20) not NULL,
    service_start_date timestamp not NULL
);

CREATE TABLE IF NOT EXISTS customer (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_card varchar(8) NOT NULL,
    name varchar(50) NOT NULL,
    surname varchar(100) NOT NULL,
    address varchar(300)
)