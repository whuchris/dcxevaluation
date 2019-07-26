CREATE TABLE t_expert
(
    id INT(4) NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    department VARCHAR(50),
    telephone VARCHAR(50),
    name VARCHAR(20),
    title VARCHAR(20),
    CONSTRAINT pk_t_expert_id PRIMARY KEY (id)
)

INSERT INTO t_expert VALUES(1,'test1','test1','test1','test1','test1','test1');
INSERT INTO t_expert VALUES(2,'test2','test2','test2','test2','test2','test2');

SELECT * FROM t_expert;
