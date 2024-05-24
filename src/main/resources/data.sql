INSERT INTO supplier (name) VALUES ('Индия');
INSERT INTO supplier (name) VALUES ('Огурчик');
INSERT INTO supplier (name) VALUES ('Поставка');

INSERT INTO product (name,type) VALUES ('ГРУША 1','PEAR');
INSERT INTO product (name,type) VALUES ('ГРУША 2','PEAR');
INSERT INTO product (name,type) VALUES ('Бабушкины','APPLE');
INSERT INTO product (name,type) VALUES ('Эконом','APPLE');

INSERT INTO Delivery (supplier_id, date) VALUES (1, '2024-05-24');

INSERT INTO Price (supplier_id, product_id, price, start_date, end_date)
VALUES (1, 1, 5, '2024-05-24', '2024-05-24');
INSERT INTO Price (supplier_id, product_id, price, start_date, end_date)
VALUES (1, 3, 5, '2024-05-24', '2024-05-24');

