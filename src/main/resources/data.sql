CREATE TABLE IF NOT EXISTS lab2_da_countries
(
    countryId   SERIAL,
    countryName VARCHAR NOT NULL,
    CONSTRAINT lab2_da_countries_pk PRIMARY KEY (countryId),
    CONSTRAINT lab2_da_countries_name_uk UNIQUE (countryName)
);

CREATE TABLE IF NOT EXISTS lab2_da_producers
(
    producerId   SERIAL,
    producerName VARCHAR NOT NULL,
    countryId    INTEGER NOT NULL,
    CONSTRAINT lab2_da_producers_pk PRIMARY KEY (producerId),
    CONSTRAINT lab2_da_producers_countryId_fk FOREIGN KEY (countryId) REFERENCES lab2_da_countries (countryId)
);

CREATE TABLE IF NOT EXISTS lab2_da_products
(
    productId     SERIAL,
    productName   VARCHAR NOT NULL,
    producerId    INTEGER NOT NULL,
    price         NUMERIC NOT NULL,
    productAmount NUMERIC NOT NULL,
    CONSTRAINT lab2_da_products_pk PRIMARY KEY (productId),
    CONSTRAINT lab2_da_products_producerId_fk FOREIGN KEY (producerId) REFERENCES lab2_da_producers (producerId)
);

CREATE TABLE IF NOT EXISTS lab2_da_operationType
(
    typeId   SERIAL,
    typeName VARCHAR NOT NULL,
    CONSTRAINT lab2_da_operationType_pk PRIMARY KEY (typeId),
    CONSTRAINT lab2_da_operationType_name_uk UNIQUE (typeName)
);

CREATE TABLE IF NOT EXISTS lab2_da_operations
(
    operationId   SERIAL,
    operationDate TIMESTAMP NOT NULL,
    typeId        INTEGER   NOT NULL,
    invoiceNumber VARCHAR   NOT NULL,
    userId        INTEGER   NOT NULL,
    CONSTRAINT lab2_da_operations_pk PRIMARY KEY (operationId),
    CONSTRAINT lab2_da_operations_typeId_fk FOREIGN KEY (typeId) REFERENCES lab2_da_operationType (typeId),
    CONSTRAINT lab2_da_operations_userId_fk FOREIGN KEY (userId) REFERENCES lab2_da_users (userId)
);

CREATE TABLE IF NOT EXISTS lab2_da_roles
(
    roleId   SERIAL,
    roleName VARCHAR NOT NULL,
    CONSTRAINT lab2_da_roles_pk PRIMARY KEY (roleId),
    CONSTRAINT lab2_da_roles_name_uk UNIQUE (roleName)
);

CREATE TABLE IF NOT EXISTS lab2_da_users
(
    userId    SERIAL,
    firstName VARCHAR NOT NULL,
    lastName  VARCHAR NOT NULL,
    login     VARCHAR NOT NULL,
    password  VARCHAR NOT NULL,
    roleId    INTEGER NOT NULL,
    CONSTRAINT lab2_da_users_pk PRIMARY KEY (userId),
    CONSTRAINT lab2_da_users_name_uk UNIQUE (login),
    CONSTRAINT lab2_da_users_roleId_fk FOREIGN KEY (roleId) REFERENCES lab2_da_roles (roleId)
);

CREATE TABLE IF NOT EXISTS lab2_da_operationInfo
(
    operationId   INTEGER NOT NULL,
    productId     INTEGER NOT NULL,
    productAmount NUMERIC NOT NULL,
    CONSTRAINT lab2_da_operationInfo_operationId_fk FOREIGN KEY (operationId) REFERENCES lab2_da_operations (operationId),
    CONSTRAINT lab2_da_operationInfo_productId_fk FOREIGN KEY (productId) REFERENCES lab2_da_products (productId)
);

INSERT INTO lab2_da_roles (roleName)
SELECT 'Администратор'
WHERE NOT EXISTS(SELECT roleName FROM lab2_da_roles WHERE roleName = 'Администратор');

INSERT INTO lab2_da_roles (roleName)
SELECT 'Кладовщик'
WHERE NOT EXISTS(SELECT roleName FROM lab2_da_roles WHERE roleName = 'Кладовщик');

INSERT INTO lab2_da_roles (roleName)
SELECT 'Сотрудник'
WHERE NOT EXISTS(SELECT roleName FROM lab2_da_roles WHERE roleName = 'Сотрудник');

INSERT INTO lab2_da_operationType (typeName)
SELECT 'Получение'
WHERE NOT EXISTS(SELECT typeName FROM lab2_da_operationType WHERE typeName = 'Получение');

INSERT INTO lab2_da_operationType (typeName)
SELECT 'Отгрузка'
WHERE NOT EXISTS(SELECT typeName FROM lab2_da_operationType WHERE typeName = 'Отгрузка');

INSERT INTO lab2_da_operationType (typeName)
SELECT 'Списание'
WHERE NOT EXISTS(SELECT typeName FROM lab2_da_operationType WHERE typeName = 'Списание');

INSERT INTO lab2_da_users (firstName, lastName, login, password, roleId)
SELECT 'admin'
     , 'admin'
     , 'admin'
     , '$2a$12$2GY0V6hFtkNBvw3tAUzFxenA/31.yDGtEgbNvoBf0plHh3B0hmrNm'
     , (SELECT roleId FROM lab2_da_roles WHERE roleName = 'Администратор')
WHERE NOT EXISTS(SELECT login FROM lab2_da_users WHERE login = 'admin');