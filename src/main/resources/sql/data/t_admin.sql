CREATE TABLE t_admin
(
    id INT(4) NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(50) NOT NULL,
    password VARCHAR(50),
    name VARCHAR(20),
    CONSTRAINT pk_t_admin_id PRIMARY KEY (id)
)

INSERT INTO t_admin VALUES(1, 'admin', 'admin', 'admin');
SELECT * FROM t_admin;

